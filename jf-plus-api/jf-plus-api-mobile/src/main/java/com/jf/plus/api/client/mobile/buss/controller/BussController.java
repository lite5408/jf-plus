package com.jf.plus.api.client.mobile.buss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.api.client.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.AbstractType;
import com.jf.plus.common.core.enums.AccountType;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.RoleType;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.vo.DistVo;
import com.jf.plus.core.account.entity.Voucher;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.entity.VoucherFlow;
import com.jf.plus.core.account.service.BussDistService;
import com.jf.plus.core.account.service.VoucherAccCashService;
import com.jf.plus.core.account.service.VoucherFlowService;
import com.jf.plus.core.account.service.VoucherService;
import com.jf.plus.core.mallSetting.entity.PacksInfo;
import com.jf.plus.core.mallSetting.service.PacksInfoService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;

@Controller
@RequestMapping("/api/client/buss")
public class BussController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(BussController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private VoucherAccCashService voucherAccCashService;

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private PacksInfoService packsInfoService;

	@Autowired
	private BussDistService bussDistService;

	@Autowired
	private VoucherFlowService voucherFlowService;

	/**
	 * 业务经理登录
	 *
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public Result login(@RequestParam(required = true) String username, @RequestParam(required = true) String password,
			HttpServletRequest request) {
		Result result = Result.newInstance();
		try {
			// 校验用户名
			User buss = userService.getByUserName(username, UserSource.SYS.getType());
			if (buss == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("用户不存在");
				return result;
			}
			// 校验密码
			String pwd = buss.getPassword();
			buss.setPassword(password);
			if (!pwd.equals(new PasswordHelper().getPassword(buss))) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("密码错误");
				return result;
			}
			buss.setRole(RoleType.BUSS_DIST.getType());
			if (userService.getEntity(buss) == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("权限不足");
				return result;
			}
			UserUtils.setLoginUser(buss); // 设置当前用户
			ResultObj resultObj = new ResultObj();
			resultObj.put("token", createToken(buss));
			resultObj.put("user", buss);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("登录异常：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 查询业务经理分发额度
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/findDistAmount", method = { RequestMethod.POST })
	@ResponseBody
	public Result findDistAmount(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			Page<VoucherAccCash> page = new Page<>();
			page.getCondition().put("userId", result.getObj());
			page.getCondition().put("accountType", AccountType.FF.getType());
			page.getCondition().put("operStatus", Status.NORMAL.getType());
			List<VoucherAccCash> vList = voucherAccCashService.findVoucherAccCashList(page);
			double availablePoints = 0;
			for (VoucherAccCash voucherAccCash : vList) {
				availablePoints += voucherAccCash.getBlance() * voucherAccCash.getRatio();
			}
			ResultObj resultObj = new ResultObj();
			resultObj.put("availablePoints", availablePoints);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取分发额度失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 查询业务经理分发券
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/findDistVoucher", method = { RequestMethod.POST })
	@ResponseBody
	public Result findDistVoucher(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			User buss = userService.get(String.valueOf(result.getObj()));
			Long distOrgId = Long.valueOf(buss.getOrganizationIds().split(",").length > 1
					? buss.getOrganizationIds().split(",")[0] : buss.getOrganizationIds());
			Page<Voucher> page = new Page<>();
			page.getCondition().put("orgId", distOrgId);
			page.getCondition().put("isMobile", BoolStatus.YES.getType());
			page.getCondition().put("operStatus", Status.NORMAL.getType());
			List<Voucher> distVoucherList = voucherService.findDistVoucherList(page);
			ResultObj resultObj = new ResultObj();
			resultObj.put("distVoucherList", distVoucherList);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取分发券列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 业务经理分发券
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/distVoucher", method = { RequestMethod.POST })
	@ResponseBody
	public Result distVoucher(@RequestParam String token, @RequestParam String voucherId, @RequestParam String mobile,
			@RequestParam(defaultValue = "0") double blance, @RequestParam String remarks) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			User buss = userService.get(String.valueOf(result.getObj()));
			Long distOrgId = Long.valueOf(buss.getOrganizationIds().split(",").length > 1
					? buss.getOrganizationIds().split(",")[0] : buss.getOrganizationIds());

			Voucher voucher = voucherService.get(voucherId);
			if (null == voucher) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数错误");
				return result;
			}

			if (voucher.getPrice() >= 0)
				blance = voucher.getPrice();
			else {
				if (blance % voucher.getRatio() != 0) {
					result.setCode(ResultCode.RETURN_FAILURE);
					result.setMsg("请输入等同比例的额度，当前比例:" + voucher.getRatio());
					return result;
				}
				blance /= voucher.getRatio();
			}
			DistVo distVo = new DistVo(mobile, distOrgId, Long.valueOf(buss.getId()), blance, remarks);
			if (blance > 0)
				result = bussDistService.distVoucher(distVo, voucher); // 分发电子券
			else
				result = bussDistService.negativeRecharge(distVo, voucher); // 负充值
			if (!result.isSuccess())
				return result;
			result.setMsg("分发成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("分发失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 查询业务经理分发礼包
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/findDistPacks", method = { RequestMethod.POST })
	@ResponseBody
	public Result findDistPacks(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			User buss = userService.get(String.valueOf(result.getObj()));
			Long distOrgId = Long.valueOf(buss.getOrganizationIds().split(",").length > 1
					? buss.getOrganizationIds().split(",")[0] : buss.getOrganizationIds());
			Page<PacksInfo> page = new Page<>();
			page.getCondition().put("orgId", distOrgId);
			page.getCondition().put("isMobile", BoolStatus.YES.getType());
			page.getCondition().put("operStatus", Status.NORMAL.getType());
			List<PacksInfo> distPacksList = packsInfoService.findDistPacksList(page);
			ResultObj resultObj = new ResultObj();
			resultObj.put("distPacksList", distPacksList);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取分发礼包列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 业务经理分发礼包
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/distPacks", method = { RequestMethod.POST })
	@ResponseBody
	public Result distPacks(@RequestParam String token, @RequestParam String packsId, @RequestParam String mobile,
			@RequestParam String remarks) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			User buss = userService.get(String.valueOf(result.getObj()));
			Long distOrgId = Long.valueOf(buss.getOrganizationIds().split(",").length > 1
					? buss.getOrganizationIds().split(",")[0] : buss.getOrganizationIds());

			PacksInfo packsInfo = packsInfoService.get(packsId);
			if (null == packsInfo) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数错误");
				return result;
			}
			DistVo distVo = new DistVo(mobile, distOrgId, Long.valueOf(buss.getId()), remarks);
			result = bussDistService.distPacks(distVo, packsInfo); // 分发礼包
			if (!result.isSuccess())
				return result;

			result.setMsg("分发成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("分发失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 分发记录查询
	 *
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/distRecordsList", method = { RequestMethod.POST })
	@ResponseBody
	public Result distRecordsList(@RequestParam String token, String mobile, Page<VoucherFlow> page) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			page.getCondition().put("userId", result.getObj());
			page.getCondition().put("userName", mobile);
			page.getCondition().put("accountType", AccountType.JF.getType());
			page.getCondition().put("abstractType", AbstractType.AMOUNT_NEGATIVE.getType());
			page.setOrderBy("a.create_date desc");
			List<VoucherFlow> flowList = voucherFlowService.findFlowList(page);
			page.setList(flowList);
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取分发记录失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

}
