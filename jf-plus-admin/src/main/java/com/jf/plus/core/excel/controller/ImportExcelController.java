package com.jf.plus.core.excel.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.OrderTrackState;
import com.jf.plus.common.core.enums.OrgType;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.common.utils.ExcelUtils;
import com.jf.plus.core.account.entity.OrgAccount;
import com.jf.plus.core.account.service.OrgAccountService;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDelivery;
import com.jf.plus.core.order.entity.OrderDeliveryDetail;
import com.jf.plus.core.order.entity.OrderDeliveryDetailSku;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.service.OrderDeliveryDetailService;
import com.jf.plus.core.order.service.OrderDeliveryDetailSkuService;
import com.jf.plus.core.order.service.OrderDeliveryService;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.product.entity.OrgGroupMerchant;
import com.jf.plus.core.product.service.OrgGroupMerchantService;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.setting.entity.RoleGroup;
import com.jf.plus.core.setting.service.RoleGroupService;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.utils.JUploadUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.OrganizationService;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;

/**
 * 导入控制层
 *
 * @author Tng
 *
 */
@Controller
@RequestMapping("${adminPath}/import")
public class ImportExcelController extends BaseController {
	public final static ExcelUtils EXCEL_UTILS = new ExcelUtils();

	@Autowired
	private OrgAccountService orgAccountService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private RoleGroupService roleGroupService;

	@Autowired
	private PasswordHelper passwordHelper;

//	@Autowired
//	private OrderBatchService orderBatchService;
	
	@Autowired
	private OrgGroupMerchantService orgGroupMerchantService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	
	@Autowired
	private OrderDeliveryDetailService orderDeliveryDetailService;
	
	@Autowired
	private OrderDeliveryDetailSkuService orderDeliveryDetailSkuService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SiteProductService siteProductService;
	
	

	@RequestMapping("/orgaccount/excel")
	public String orgaccountExcel(@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request) {
		Result result = new Result();
		try {

			InputStream in = file.getInputStream();
			List<List<Object>> importList = EXCEL_UTILS.getBankListByExcel(in, file.getOriginalFilename());
			List<List<Object>> validFailList = new ArrayList<List<Object>>();
			List<OrgAccount> successList = new ArrayList<OrgAccount>();
			Set<Long> outOrgSet = new HashSet<Long>();
			double rechargeAmount = 0;

			for (List<Object> rowList : importList) {
				if (rowList.size() < 3) {
					result.setMsg("excel导入模板不正确");
					request.setAttribute("result", result);
					return "/excel/impOrgAccountResult";
				}
				String no = rowList.get(0) == null ? "" : rowList.get(0).toString().trim();
				String name = rowList.get(1) == null ? "" : rowList.get(1).toString().trim();
				String purchase_blance = rowList.get(2) == null ? "" : rowList.get(2).toString().trim();

				// 备注非必填
				String remarks = "";
				if (rowList.size() >= 4) {
					remarks = rowList.get(3) == null ? "" : rowList.get(3).toString().trim();
				}

				OrgAccount orgAccount = new OrgAccount();
				orgAccount.setOrgNo(no);
				orgAccount.setOrgName(name);
				orgAccount.setPurchaseBlance(Double.valueOf(purchase_blance));
				orgAccount.setRemarks(remarks);

				rechargeAmount += Double.valueOf(purchase_blance);

				OrgAccount dbOrgAccount = orgAccountService.getEntity(orgAccount);
				if (dbOrgAccount == null) {
					String fail = "机构不存在";
					rowList.add(fail);
					validFailList.add(rowList);
					continue;
				}

				outOrgSet.add(orgAccount.getParentOrgId());

				// 设置id
				orgAccount.setId(dbOrgAccount.getId());
				orgAccount.setOrgId(dbOrgAccount.getOrgId());
				orgAccount.setOrganizationId(dbOrgAccount.getOrgId());
				successList.add(orgAccount);
			}

			if (outOrgSet != null && outOrgSet.size() > 1) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("批量充值机构的上级机构必须是同一个机构");
				request.setAttribute("result", result);

				return "/excel/impOrgAccountResult";
			}

			if (CollectionUtils.isNotEmpty(validFailList)) {
				result.setObj(validFailList);
				result.setCode(ResultCode.ILLEGAL_ARGUMENT_ERROR);
				result.setMsg("数据校验不通过");
			} else {

				for (Long parentOrgId : outOrgSet) {
					OrgAccount parentOrgAccount = new OrgAccount();
					parentOrgAccount.setOrgId(parentOrgId);
					parentOrgAccount = orgAccountService.getEntity(parentOrgAccount);
					if (parentOrgAccount.getPurchaseBlance() < rechargeAmount) {
						result.setCode(ResultCode.RETURN_FAILURE);
						result.setMsg("上级机构额度不足");
						request.setAttribute("result", result);

						return "/excel/impOrgAccountResult";
					}
				}

				result = orgAccountService.batchSave(successList);
				result.setObj(successList);
				try {
					JUploadUtils.save(file, request);// 存档
				} catch (Exception e) {
					// 上传文件有异常不捕获
				}
			}
		} catch (Exception e) {
			logger.error("导入异常：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg(Constants.EXCEPTION_MSG);
		}

		request.setAttribute("result", result);

		return "/excel/impOrgAccountResult";
	}
	
	@RequestMapping("/orggroup/excel")
	public String orgGroupExcel(@RequestParam(value = "file", required = true) MultipartFile file,
			String orgGroupId,
			HttpServletRequest request) {
		Result result = new Result();
		try {

			InputStream in = file.getInputStream();
			List<List<Object>> importList = null;
			try{
				importList = EXCEL_UTILS.getBankListByExcel(in, file.getOriginalFilename());
			}catch(Exception e){
				result.setMsg("请删掉Excel内容后面的空行");
				request.setAttribute("result", result);
				return "/excel/impOrgGroupResult";
			}
			List<List<Object>> validFailList = new ArrayList<>();
			List<OrgGroupMerchant> orgGroupMerchants = new ArrayList<>();
			int sort = 0;
			for (List<Object> rowList : importList) {
				if (rowList.size() < 2) {
					result.setMsg("excel导入模板不正确");
					request.setAttribute("result", result);
					return "/excel/impOrgGroupResult";
				}
				String no = rowList.get(0) == null ? "" : rowList.get(0).toString().trim();
				String name = rowList.get(1) == null ? "" : rowList.get(1).toString().trim();
				
				Organization organization = new Organization();
				organization.setNo(no);
				organization.setType(OrgType.COMP_TX.getType());
				organization = organizationService.getEntity(organization);
				if(organization == null){
					String fail = "代理商不存在";
					rowList.add(fail);
					validFailList.add(rowList);
					continue;
				}
				
				sort++;
				OrgGroupMerchant orgGroupMerchant = new OrgGroupMerchant();
				orgGroupMerchant.setGroupId(Long.valueOf(orgGroupId));
				orgGroupMerchant.setMerchantId(Long.valueOf(organization.getId()));
				orgGroupMerchant.setSort(sort);
				
				orgGroupMerchants.add(orgGroupMerchant);
			}


			if (CollectionUtils.isNotEmpty(validFailList)) {
				result.setObj(validFailList);
				result.setCode(ResultCode.ILLEGAL_ARGUMENT_ERROR);
				result.setMsg("数据校验不通过");
			} else {
				OrgGroupMerchant orgGroupMerchant = new OrgGroupMerchant();
				orgGroupMerchant.setGroupId(Long.valueOf(orgGroupId));
				orgGroupMerchantService.deleteEntity(orgGroupMerchant);
				
				for(OrgGroupMerchant orgGroupMerchant2 : orgGroupMerchants){
					orgGroupMerchantService.save(orgGroupMerchant2);
				}
				result.setCode(ResultCode.SUCCESS);
				result.setMsg("导入成功");
				result.setObj(orgGroupMerchants);
			}
				
			try {
				JUploadUtils.save(file, request);// 存档
			} catch (Exception e) {
				// 上传文件有异常不捕获
			}
		} catch (Exception e) {
			logger.error("导入异常：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg(Constants.EXCEPTION_MSG);
		}

		request.setAttribute("result", result);

		return "/excel/impOrgGroupResult";
	}
	
	@RequestMapping("/orderDelivery/excel")
	public String orderDeliveryExcel(@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request) {
		Result result = new Result();
		try {

			InputStream in = file.getInputStream();
			List<List<Object>> importList = null;
			try{
				importList = EXCEL_UTILS.getBankListByExcel(in, file.getOriginalFilename());
			}catch(Exception e){
				result.setMsg("请删掉Excel内容后面的空行");
				request.setAttribute("result", result);
				return "/excel/impDeliveryResult";
			}
			List<List<Object>> validFailList = new ArrayList<>();
			List<OrderDelivery> orderDeliverys = new ArrayList<>();
			for (List<Object> rowList : importList) {
				if(CollectionUtils.isEmpty(rowList) || rowList.get(0) == null || StringUtils.isBlank(rowList.get(0).toString()))//空行忽略掉
					continue;

				String itemCode = rowList.get(0) == null ? "" : rowList.get(0).toString().trim();
				String itemName = rowList.get(1) == null ? "" : rowList.get(1).toString().trim();
				String orgName = rowList.get(2) == null ? "" : rowList.get(2).toString().trim();
				String specColorText = rowList.get(3) == null ? "" : rowList.get(3).toString().trim();
				String specSizeText = rowList.get(4) == null ? "" : rowList.get(4).toString().trim();
				String distNum = rowList.get(5) == null ? "" : rowList.get(5).toString().trim();
				String orderNo = rowList.get(6) == null ? "" : rowList.get(6).toString().trim();
				String deliveryExpressNo = rowList.get(7) == null ? "" : rowList.get(7).toString().trim();
				String deliveryNum = rowList.get(8) == null ? "" : rowList.get(8).toString().trim();
				String deliveryDate = rowList.get(9) == null ? "" : rowList.get(9).toString().trim();
				
				
				if(StringUtils.isBlank(itemCode) || StringUtils.isBlank(itemName) || StringUtils.isBlank(orgName) || StringUtils.isBlank(orderNo)
						|| StringUtils.isBlank(deliveryExpressNo) || StringUtils.isBlank(deliveryNum)){
					String fail = "参数不能为空：商品编码，商品名称，机构名称，订单号，包裹号，发货数量";
					rowList.add(fail);
					validFailList.add(rowList);
					continue;
				}
				
				OrderDelivery orderDelivery = new OrderDelivery();
				orderDelivery.setOrderNo(orderNo);
				orderDelivery.setDeliveryType(2);
				orderDelivery.setDeliveryDate(DateUtils.parseDate(deliveryDate));
				orderDelivery.setDeliveryOperator(UserUtils.getLoginUser().getName());
				orderDelivery.setDeliveryExpressType(1);
				orderDelivery.setDeliveryExpress("物流");
				orderDelivery.setDeliveryExpressNo(deliveryExpressNo);
				orderDelivery.setOperStatus(0);
				orderDelivery.setCkNo(deliveryExpressNo);
				
				
				OrderDeliveryDetail orderDeliveryDetail = new OrderDeliveryDetail();
				
				
				orderDeliveryDetail.setOrderNo(orderNo);
				orderDeliveryDetail.setItemCode(itemCode);
				orderDeliveryDetail.setItemName(itemName);
				orderDeliveryDetail.setDeliveryNum(Integer.valueOf(deliveryNum));
				orderDeliveryDetail.setDistNum(Integer.valueOf(distNum));
				
				
				List<OrderDeliveryDetailSku> orderDeliveryDetailSkus = new ArrayList<>();
				if(StringUtils.isNotBlank(specColorText) || StringUtils.isNotBlank(specSizeText)){
					OrderDeliveryDetailSku orderDeliveryDetailSku = new OrderDeliveryDetailSku();
					orderDeliveryDetailSku.setDeliveryNum(Integer.valueOf(deliveryNum));
					orderDeliveryDetailSku.setOrderNo(orderDeliveryDetail.getOrderNo());
					orderDeliveryDetailSku.setSpecColorText(specColorText);
					orderDeliveryDetailSku.setSpecSizeText(specSizeText);
					orderDeliveryDetailSkus.add(orderDeliveryDetailSku);
				}
				orderDeliveryDetail.setOrderDeliveryDetailSkus(orderDeliveryDetailSkus);
					
				
				if(!orderDeliverys.contains(orderDelivery)){//不包含
					List<OrderDeliveryDetail> orderDeliveryDetails = new ArrayList<>();
					orderDelivery.setOrderDeliveryDetails(orderDeliveryDetails);
					orderDeliverys.add(orderDelivery);
				}
				
				orderDelivery = orderDeliverys.get(orderDeliverys.indexOf(orderDelivery));
				orderDelivery.getOrderDeliveryDetails().add(orderDeliveryDetail);
					
				
				
			}


			if (CollectionUtils.isNotEmpty(validFailList)) {
				result.setObj(validFailList);
				result.setCode(ResultCode.ILLEGAL_ARGUMENT_ERROR);
				result.setMsg("数据校验不通过");
			} else {
				for(OrderDelivery orderDelivery : orderDeliverys){
					
					orderDeliveryService.save(orderDelivery);
					for(OrderDeliveryDetail orderDeliveryDetail : orderDelivery.getOrderDeliveryDetails()){
						if(orderDeliveryDetail == null){
							continue;
						}
						
						orderDeliveryDetail.setDeliveryId(Long.valueOf(orderDelivery.getId()));
						
						orderDeliveryDetailService.save(orderDeliveryDetail);
						
						for(OrderDeliveryDetailSku orderDeliveryDetailSku : orderDeliveryDetail.getOrderDeliveryDetailSkus()){
							orderDeliveryDetailSku.setDeliveryId(Long.valueOf(orderDelivery.getId()));
							orderDeliveryDetailSku.setDeliveryDetailId(Long.valueOf(orderDeliveryDetail.getId()));
							
							orderDeliveryDetailSkuService.save(orderDeliveryDetailSku);
						}
						
						//更新订单明细物流状态
						OrderDetail orderDetail = new OrderDetail();
						orderDetail.setDistOrderNo(orderDelivery.getOrderNo());
						orderDetail.setItemCode(orderDeliveryDetail.getItemCode());
						orderDetail.setTrackState(OrderTrackState.DELIVERED.getType());
						orderDetailService.trackDetail(orderDetail);
					}
					
					//更新订单发货状态
					Order toUpdateOrder = new Order();
					toUpdateOrder.setOrderNo(orderDelivery.getOrderNo());
					toUpdateOrder.setTrackState(OrderTrackState.DELIVERED.getType());
					orderService.updateEntity(toUpdateOrder);
				}
				
				result.setCode(ResultCode.SUCCESS);
				result.setMsg("导入成功");
				result.setObj(orderDeliverys);
			}
				
			try {
				JUploadUtils.save(file, request);// 存档
			} catch (Exception e) {
				// 上传文件有异常不捕获
			}
		} catch (Exception e) {
			logger.error("导入异常：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg(Constants.EXCEPTION_MSG);
		}

		request.setAttribute("result", result);

		return "/excel/impDeliveryResult";
	}

	@RequestMapping("/user/excel")
	public String userExcel(@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request) {

		final String USER_LOCKED = "离职", DEFAULT_PWD = "123456";

		Result result = new Result();
		try {

			InputStream in = file.getInputStream();
			List<List<Object>> importList = EXCEL_UTILS.getBankListByExcel(in, file.getOriginalFilename());
			List<List<Object>> validFailList = new ArrayList<List<Object>>();
			List<User> successList = new ArrayList<>();
			for (List<Object> rowList : importList) {
				String no = rowList.get(0) == null ? "" : rowList.get(0).toString();
				String name = rowList.get(1) == null ? "" : rowList.get(1).toString();
				String mobile = rowList.get(2) == null ? "" : rowList.get(2).toString();
				String orgNo = rowList.get(3) == null ? "" : rowList.get(3).toString();
				String orgName = rowList.get(4) == null ? "" : rowList.get(4).toString();
				String roleGroupNames = rowList.get(5) == null ? "" : rowList.get(5).toString();
				String status = rowList.get(6) == null ? "" : rowList.get(6).toString();

				User user = userService.getByUserName(no);
				if (user == null) {
					user = new User();
				}

				String fail = "";
				Organization orgEntity = new Organization();
				orgEntity.setNo(orgNo);
				orgEntity = organizationService.getEntity(orgEntity);
				if (orgEntity == null) {
					fail += "[机构不存在]";
				}

				List<String> roleGroupIdList = new ArrayList<>();
				String[] roleGroupNameArray = roleGroupNames.split(",");
				for (String roleGroupName : roleGroupNameArray) {
					RoleGroup roleGroup = new RoleGroup();
					roleGroup.setGroupName(roleGroupName);
					roleGroup.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
					roleGroup = roleGroupService.getEntity(roleGroup);
					if (roleGroup == null) {
						fail += "[角色不存在]";
						break;
					}
					roleGroupIdList.add(roleGroup.getId());
				}

				// 验证错误返回
				if (fail.length() > 0) {
					rowList.add(fail);
					validFailList.add(rowList);

					continue;
				}

				user.setPhone(mobile);
				user.setMobile(mobile);
				user.setNo(no);
				user.setUsername(no);
				user.setIsDept(false);
				user.setEmail(mobile + "@abc.com");
				user.setLocked(status.equals(USER_LOCKED));
				user.setName(name);
				user.setOrganizationIds(orgEntity.getId());
				user.setRoleGroupIds(Joiner.on(",").join(roleGroupIdList));
				user.setType(UserType.ADMIN.getType());
				// 新用户设置密码
				if (StringUtils.isBlank(user.getId())) {
					user.setPassword(DEFAULT_PWD);
					passwordHelper.encryptPassword(user);
				}

				successList.add(user);
			}

			if (CollectionUtils.isNotEmpty(validFailList)) {
				result.setObj(validFailList);
				result.setCode(ResultCode.ILLEGAL_ARGUMENT_ERROR);
				result.setMsg("数据校验不通过");
			} else {
				result = userService.batchSave(successList);
				result.setObj(successList);
				try {
					JUploadUtils.save(file, request);// 存档
				} catch (Exception e) {
					// 上传文件有异常不捕获
				}
			}
		} catch (Exception e) {
			logger.error("导入异常：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg(Constants.EXCEPTION_MSG);
		}

		request.setAttribute("result", result);

		return "/excel/impUserResult";
	}

//	@RequestMapping(value = { "/order/batchConfirmImport" })
//	public String orderBatchImport(@RequestParam(value = "file", required = true) MultipartFile file, String siteId,
//			Integer source, final Integer supplyId, String remarks, final HttpServletRequest request,
//			RedirectAttributes redirectAttributes) {
//		Result result = new Result();
//
//		User user = UserUtils.getLoginUser();
//
//		final String batchNo = CodeGen.getBatchNo();
//		try {
//			InputStream in = file.getInputStream();
//			List<List<Object>> importList = null;
//			try {
//				importList = EXCEL_UTILS.getBankListByExcel(in, file.getOriginalFilename());
//			} catch (Exception e) {
//				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
//				result.setMsg("Excel文件中存在空缺的单元格，请排查后再下单。");
//				result.setObj(0);
//				request.setAttribute("result", result);
//				return "/excel/impOrderBatchResult";
//			}
//			for (List<Object> rowList : importList) {
//				if (rowList.size() < 10) {
//					result.setCode(ResultCode.ILLEGAL_ARGUMENT_ERROR);
//					result.setMsg("Excel导入模板不正确");
//					result.setObj(0);
//					request.setAttribute("result", result);
//					return "/excel/impOrderBatchResult";
//				}
//				String receiveName = rowList.get(0) == null ? "" : rowList.get(0).toString();
//				String provinceName = rowList.get(1) == null ? "" : rowList.get(1).toString();
//				String cityName = rowList.get(2) == null ? "" : rowList.get(2).toString();
//				String countyName = rowList.get(3) == null ? "" : rowList.get(3).toString();
//				String townName = rowList.get(4) == null ? "" : rowList.get(4).toString();
//				String address = rowList.get(5) == null ? "" : rowList.get(5).toString();
//				String mobile = rowList.get(6) == null ? "" : rowList.get(6).toString();
//				String itemCode = rowList.get(7) == null ? "" : rowList.get(7).toString();
//				String itemName = rowList.get(8) == null ? "" : rowList.get(8).toString();
//				String itemNum = rowList.get(9) == null ? "" : rowList.get(9).toString();
//
//				OrderBatch orderBatch = new OrderBatch();
//				orderBatch.setReceiveName(receiveName);
//				orderBatch.setProvinceName(provinceName);
//				orderBatch.setCityName(cityName);
//				orderBatch.setCountyName(countyName);
//				orderBatch.setTownName(townName);
//				orderBatch.setAddress(address);
//				orderBatch.setMobile(mobile);
//				orderBatch.setItemCode(itemCode);
//				orderBatch.setItemNum(Double.valueOf(itemNum).intValue());
//				orderBatch.setItemName(itemName);
//				orderBatch.setBatchNo(batchNo);
//				orderBatch.setSource(source);
//				orderBatch.setSupplyId(supplyId);
//				orderBatch.setOperStatus("0");
//				orderBatch.setTradePerson(user.getName() + "_" + user.getNo());
//				orderBatchService.save(orderBatch);
//			}
//
//			try {
//				JUploadUtils.save(file, request);// 存档
//			} catch (Exception e) {
//				// 上传文件有异常不捕获
//			}
//			// 将下单状态存入session
//			request.getSession().setAttribute(batchNo, "0");
//
//			final User u = user;
//			final Integer s = source;
//			final String si = siteId;
//			final String bn = batchNo;
//			final String r = remarks;
//
//			Executor excutor = Executors.newSingleThreadExecutor();
//			excutor.execute(new Runnable() {
//
//				@Override
//				public void run() {
//					OrderBatch entity = new OrderBatch();
//					entity.setBatchNo(batchNo);
//					entity.setSource(s);
//					entity.setSupplyId(supplyId);
//					orderBatchService.updateAreaAndStatus(entity);
//
//					Result ret = orderBatchService.orderBatchConfirm(u, s, si, bn, r);
//					// 更新下单状态为完成下单
//					request.getSession().setAttribute(bn, "1");
//				}
//			});
//			result.setCode(ResultCode.SUCCESS);
//			result.setObj(batchNo);
//		} catch (Exception e) {
//			logger.error("导入异常：{}", e);
//			result.setCode(ResultCode.SERVICE_EXCEPTION);
//			result.setMsg(Constants.EXCEPTION_MSG);
//			result.setObj(0);
//		}
//		addMessage(redirectAttributes, "批量订单下单完成");
//		request.setAttribute("result", result);
//		return "/excel/impOrderBatchResult";
//	}
	
	

}
