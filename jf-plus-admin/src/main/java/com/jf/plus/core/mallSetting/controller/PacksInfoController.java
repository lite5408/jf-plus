package com.jf.plus.core.mallSetting.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.core.account.service.BlanceService;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.mallSetting.entity.PacksInfo;
import com.jf.plus.core.mallSetting.entity.PacksProduct;
import com.jf.plus.core.mallSetting.service.PacksAccCashService;
import com.jf.plus.core.mallSetting.service.PacksInfoService;
import com.jf.plus.core.mallSetting.service.PacksProductService;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.service.OrganizationService;

/**
* 礼包信息表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/packsInfo")
public class PacksInfoController extends BaseController {
	
//	/**
//	 * 排序字段映射
//	 */
//	@SuppressWarnings("serial")
//	private static Map<String, String> sortMap = new HashMap<String, String>() {
//		{
//			put("id", "id");
//		}
//	};

    @Autowired
    private PacksInfoService packsInfoService;
    
    @Autowired
    private OrganizationService organizationService;
    
    @Autowired
    private SiteProductService siteProductService;
    
    @Autowired
    private PacksProductService packsProductService;
    
    @Autowired
    private BlanceService blanceService;
    
    @Autowired
    private MallSiteService mallSiteService;
    
    @ModelAttribute
    public PacksInfo get(@RequestParam(required = false) String id) {
        PacksInfo entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = packsInfoService.get(id);
        }
        if (entity == null) {
            entity = new PacksInfo();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<PacksInfo> page,PacksInfo packsInfo) {
    	packsInfo.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	packsInfo.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
    	page.setEntity(packsInfo);
        model.addAttribute("page", page.setList(packsInfoService.findPage(page)));
        return "mallSetting/packsInfo/list";
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(PacksInfo packsInfo,Model model) {
        model.addAttribute("packsInfo", packsInfo);
        return "mallSetting/packsInfo/form";
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PacksInfo packsInfo, RedirectAttributes redirectAttributes) {
    	if(UserUtils.getMySite() == null){
			addMessage(redirectAttributes,"您没有站点管理权限");
			return "error/400";
		}
    	Integer marketRatio = UserUtils.getLoginUser().getOrganization().getMarketRatio();
    	if(marketRatio==null){
    		addMessage(redirectAttributes,"当前机构积分比例参数缺失，请联系系统管理员完善该配置后操作！");
			return "redirect:"+ adminPath +"/mallSetting/packsInfo/list";
    	}
    	packsInfo.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	packsInfo.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
    	packsInfo.setRatio(marketRatio);
    	packsInfo.setValidStartDate(new Date());
    	packsInfo.setCreateBy(UserUtils.getLoginUser().getId());
    	packsInfo.setCreateDate(new Date());
    	packsInfo.setStatus(Status.NORMAL.getType() + "");
        packsInfoService.save(packsInfo);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/packsInfo/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(PacksInfo packsInfo, Model model) {
        model.addAttribute("packsInfo", packsInfo);
        return "mallSetting/packsInfo/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PacksInfo packsInfo, RedirectAttributes redirectAttributes) {
        packsInfoService.save(packsInfo);
        
//        //修改礼包下对应礼包卡券的失效时间
//        PacksAccCash packsAccCash = new PacksAccCash();
//        packsAccCash.setPacksId(Long.valueOf(packsInfo.getId()));
//        List<PacksAccCash> packsAccCashList = packsAccCashService.findList(packsAccCash);
//        if (CollectionUtil.isNotEmpty(packsAccCashList)) {
//			for (PacksAccCash p : packsAccCashList) {
//				long changeTime = packsInfo.getValidEndDate().getTime();
//				long accEndTime = p.getValidEndDate().getTime();
//				long realAccEndTime = accEndTime + (1000 * 60 * 60 * 24 * 10);
//				if (realAccEndTime > new Date().getTime() && changeTime>accEndTime){
//					p.setValidEndDate(packsInfo.getValidEndDate());
//					packsAccCashService.save(p);
//				}
//			}
//		}
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/packsInfo/list";
    }
    
    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public String changeStatus(PacksInfo packsInfo) {
    	if(PacksInfo.STATUS_NORMAL.equals(packsInfo.getOperStatus() + "")){
    		packsInfo.setOperStatus(Integer.valueOf(PacksInfo.STATUS_DELETE));
    	}else {
    		packsInfo.setOperStatus(Integer.valueOf(PacksInfo.STATUS_NORMAL));
		}
    	packsInfoService.save(packsInfo);
        return "redirect:"+ adminPath + "/mallSetting/packsInfo/list";
    }
    
    @RequestMapping(value={"/selectProduct",""})
    public String selectProduct(Model model, SiteProduct siteProduct, String packsId, Page<SiteProduct> page) {
    	page.getCondition().put("orgId", UserUtils.getLoginUser().getOrganizationId());
		page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());

		// 查询条件
		page.getCondition().put("operStatus", siteProduct.getOperStatus());
		page.getCondition().put("source", siteProduct.getSource());
		page.getCondition().put("itemCode", siteProduct.getItemCode());
		page.getCondition().put("itemName", siteProduct.getItemName());

		// 过滤掉已经存在商品
		String items = getItems(packsId);
		if (StringUtils.isNotBlank(items)) {
			page.getCondition().put("inz", "not in");
			page.getCondition().put("items", items.split(","));
		}
		List<SiteProduct> productList = siteProductService.findPage(page);
		model.addAttribute("page", page.setList(productList));
		model.addAttribute("packsId", packsId);
		return "mallSetting/packsInfo/select";
    }
    
    @RequestMapping(value = "/pick")
	@ResponseBody
	public Result pick(String packsId, SiteProduct siteProduct) {
		Result result = new Result();

		try {
			ProductStatus productStatus = ProductStatus.getByType(siteProduct.getOperStatus());
			switch (productStatus) {
			case SHELVES:
				pickInSpecial(packsId, siteProduct);
				break;
			case OUT_SHELVES:
				pickOutSpecial(packsId, siteProduct);
				break;
			default:
				break;
			}

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			logger.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
    
    /**
	 * 礼包商品
	 * @param advertId
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/myProduct",""})
	public String mySpecialProduct(String packsId,String itemCode,String itemName,String supplyName,Model model){
		Page<SiteProduct> page = new Page<>();
		page.getCondition().put("orgId", UserUtils.getLoginUser().getOrganizationId());
		page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());
		
		//查询条件
		page.getCondition().put("itemCode", itemCode);
		page.getCondition().put("itemName", itemName);
		page.getCondition().put("supplyName", supplyName);
		
		String items = getItems(packsId);
		if(StringUtils.isNotBlank(items)){
			page.getCondition().put("inz", "in");
			page.getCondition().put("items", items.split(","));
			List<SiteProduct> productList = siteProductService.findPage(page);
			model.addAttribute("page", page.setList(productList));
		}else {
			page.setTotal(0);
			page.setList(null);
		}
		
		model.addAttribute("packsId", packsId);
		
		return "mallSetting/packsInfo/my_product";
	}
    
    @RequestMapping(value = "/bindUser")
    public String bindUser(String packsInfoId, String mobile,String remarks, RedirectAttributes redirectAttributes) {
    	Long orgId = mallSiteService.get(UserUtils.getMySite().getSiteId()).getOrgId();
    	if(blanceService.getOrgBlance(orgId + "").doubleValue() > packsInfoService.get(packsInfoId).getSalePrice().doubleValue()){
    		int ret = packsInfoService.bindUser(packsInfoId,mobile,remarks);
    		if(ret == 1){
				addMessage(redirectAttributes,"礼包分发成功");
			}else{
				addMessage(redirectAttributes, "礼包分发失败，系统异常，请联系管理员");
			}
    	}else {
    		addMessage(redirectAttributes,"礼包分发失败，用户余额不足");
		}
    	return "redirect:"+ adminPath +"/mallSetting/packsInfo/list";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        packsInfoService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/packsInfo?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    private void pickOutSpecial(String packsId, SiteProduct siteProduct) {
		PacksProduct packsProduct = new PacksProduct();
		packsProduct.setPacksId(Long.valueOf(packsId));
		packsProduct.setItemId(Long.valueOf(siteProduct.getId()));
		packsProductService.deleteEntity(packsProduct);
	}
    
	private void pickInSpecial(String packsId, SiteProduct siteProduct) {
		PacksProduct packsProduct = new PacksProduct();
		packsProduct.setPacksId(Long.valueOf(packsId));
		packsProduct.setItemId(Long.valueOf(siteProduct.getId()));
		packsProduct.setItemSource(siteProduct.getSource());
		packsProduct.setSort(0);
		packsProduct.setCreateBy(UserUtils.getLoginUser().getId());
		packsProduct.setCreateDate(new Date());
		packsProduct.setStatus(Status.NORMAL.getType() + "");
		packsProductService.save(packsProduct);

	}
	
	private String getItems(String packsId){
    	PacksProduct packsProduct = new PacksProduct();
		packsProduct.setPacksId(Long.valueOf(packsId));
		List<PacksProduct> packsProductList = packsProductService.findList(packsProduct);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < packsProductList.size(); i++) {
			sb.append(packsProductList.get(i).getItemId());
			if (i < packsProductList.size()-1) {
				sb.append(",");
			}
		}
    	return sb.toString();
    }
}
