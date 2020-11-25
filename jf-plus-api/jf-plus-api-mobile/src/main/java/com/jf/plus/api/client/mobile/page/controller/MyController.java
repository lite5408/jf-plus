package com.jf.plus.api.client.mobile.page.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.api.client.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.AccountType;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.UseStatus;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.common.vo.PersentVo;
import com.jf.plus.core.account.entity.Voucher;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.service.VoucherAccCashService;
import com.jf.plus.core.account.service.VoucherService;
import com.jf.plus.core.mallSetting.entity.PacksAccCash;
import com.jf.plus.core.mallSetting.entity.PacksInfo;
import com.jf.plus.core.mallSetting.service.PacksAccCashService;
import com.jf.plus.core.mallSetting.service.PacksInfoService;
import com.jf.plus.core.setting.entity.MsgSendRecord;
import com.jf.plus.core.setting.service.MsgSendRecordService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

@Controller("cMyController")
@RequestMapping("/api/client")
public class MyController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(MyController.class);

	@Autowired
	private VoucherAccCashService voucherAccCashService;

	@Autowired
	private PacksAccCashService packsAccCashService;

	@Autowired
	private PacksInfoService packsInfoService;

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private MsgSendRecordService msgSendRecordService;

	/**
	 * 用户余额查询
	 *
	 * @param token
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/findUserVoucher", method = { RequestMethod.POST })
	@ResponseBody
	public Result findUserVoucher(@RequestParam String token, @RequestParam Long siteId) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			Page<VoucherAccCash> page = new Page<>();
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("accountType", AccountType.JF.getType());
			page.getCondition().put("userId", result.getObj());
			page.getCondition().put("operStatus", Status.NORMAL.getType());
			List<VoucherAccCash> vList = voucherAccCashService.findVoucherAccCashList(page);
			double ye = 0;
			for (VoucherAccCash voucherAccCash : vList) {
				ye += voucherAccCash.getBlance() * voucherAccCash.getRatio();
			}
			ResultObj resultObj = new ResultObj();
			resultObj.put("ye", ye);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户余额失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 查询用户卡券列表
	 *
	 * @param token
	 * @param siteId
	 * @param status
	 *            -1：已过期 0：未使用 1：已使用
	 * @return
	 */
	@RequestMapping(value = "/findVoucherList", method = { RequestMethod.POST })
	@ResponseBody
	public Result findVoucherList(@RequestParam String token, @RequestParam Long siteId, @RequestParam Integer status,
			Page<Voucher> page) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("accountType", AccountType.JF.getType());
			page.getCondition().put("userId", result.getObj());
			page.getCondition().put("userStatus", status);
			page.setOrderBy("a.valid_end_date desc");
			List<Voucher> voucherList = voucherService.findVoucherList(page);
			page.setList(voucherList);
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户卡券列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 查询用户礼包列表
	 *
	 * @param token
	 * @param siteId
	 * @param status
	 *            -1：已过期 0：未使用 1：已使用 2：已送出
	 * @return
	 */
	@RequestMapping(value = "/findPackList", method = { RequestMethod.POST })
	@ResponseBody
	public Result findPackList(@RequestParam String token, @RequestParam Long siteId, @RequestParam Integer status,
			Page<PacksInfo> page) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("userId", result.getObj());
			page.getCondition().put("useStatus", status);
			page.setOrderBy("a.valid_end_date desc");
			List<PacksInfo> packList = null;
			if (UseStatus.PRESENT.getType() == status.intValue())
				packList = packsInfoService.findPresentPackList(page);
			else
				packList = packsInfoService.findPackList(page);
			page.setList(packList);
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户礼包列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 用户礼包赠送
	 *
	 * @param persentVo
	 * @return
	 */
	@RequestMapping(value = "/packsPresent", method = { RequestMethod.POST })
	@ResponseBody
	public Result packsPresent(@RequestBody PersentVo persentVo) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(persentVo.getToken()); // 验证token有效性
			if (!result.isSuccess())
				return result;
			PacksAccCash packsAccCash = new PacksAccCash();
			packsAccCash.setId(persentVo.getAccId().toString());
			packsAccCash.setBindUser(Long.valueOf(String.valueOf(result.getObj())));
			packsAccCash.setIsCash(BoolStatus.NO.getType());
			packsAccCash.setIsValidity(BoolStatus.YES.getType());
			packsAccCash = packsAccCashService.getEntity(packsAccCash);
			if (null == packsAccCash) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg("礼包已失效");
				return result;
			}
			persentVo.setFromId(Long.valueOf(String.valueOf(result.getObj())));
			result = packsAccCashService.packsPresent(packsAccCash, persentVo); // 礼包赠送
			if (!result.isSuccess())
				return result;
			ResultObj resultObj = new ResultObj();
			resultObj.put("presentId", result.getObj());
			result.setObj(resultObj);
			result.setMsg("礼包赠送成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户可用礼包失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 绑定电子券
	 *
	 * @param token
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/bindVoucherAcc", method = { RequestMethod.POST })
	@ResponseBody
	public Result bindVoucherAcc(@RequestParam String token, @RequestParam Long siteId, @RequestParam String account) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			VoucherAccCash voucherAccCash = new VoucherAccCash();
			voucherAccCash.setCouponCode(account);
			voucherAccCash.setSiteId(siteId);
			voucherAccCash = voucherAccCashService.getEntityByBind(voucherAccCash);
			if (voucherAccCash == null) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg("电子券已失效");
				return result;
			}
			voucherAccCash.setBindUser(Long.valueOf(String.valueOf(result.getObj())));
			voucherAccCash.setBindDate(new Date());
			voucherAccCash.setOperStatus(Status.NORMAL.getType());
			voucherAccCashService.save(voucherAccCash);
			result.setMsg("电子券绑定成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("电子券绑定失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 绑定礼包券
	 *
	 * @param persentVo
	 * @return
	 */
	@RequestMapping(value = "/bindPacksAcc", method = { RequestMethod.POST })
	@ResponseBody
	public Result bindPacksAcc(@RequestParam String token, @RequestParam Long siteId, @RequestParam String account) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			PacksAccCash packsAccCash = new PacksAccCash();
			packsAccCash.setCouponCode(account);
			packsAccCash.setSiteId(siteId);
			packsAccCash = packsAccCashService.getEntityByBind(packsAccCash);
			if (packsAccCash == null) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg("礼包券已失效");
				return result;
			}
			packsAccCash.setBindUser(Long.valueOf(String.valueOf(result.getObj())));
			packsAccCash.setBindDate(new Date());
			packsAccCash.setOperStatus(Status.NORMAL.getType());
			packsAccCashService.save(packsAccCash);
			result.setMsg("礼包券绑定成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("礼包券绑定失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 消息列表
	 *
	 */
	@RequestMapping("/msgList")
	@ResponseBody
	public Result msgList(@RequestParam String token, MsgSendRecord msgSendRecord, Page<MsgSendRecord> page) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token);
			if (!result.isSuccess())
				return result;

			msgSendRecord.setToUserId(Long.valueOf(result.getObj().toString()));
			page.setEntity(msgSendRecord);
			page.setList(msgSendRecordService.findPage(page));

			ResultObj resObj = new ResultObj();
			resObj.put("page", MPageInfo.transform(page));
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 修改msg状态
	 */
	@RequestMapping("/msgStatus")
	@ResponseBody
	public Result msgStatus(MsgSendRecord msgSendRecord) {
		Result result = new Result();
		try {
			if (msgSendRecord.getId() == null) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("缺少参数");
				return result;
			}

			if(StringUtils.isNotBlank(msgSendRecord.getIsRead())){
				msgSendRecord.setReadDate(new Date());
			}
			msgSendRecordService.save(msgSendRecord);

			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

}
