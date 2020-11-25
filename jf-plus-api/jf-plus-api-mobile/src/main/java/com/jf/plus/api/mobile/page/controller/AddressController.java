package com.jf.plus.api.mobile.page.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.config.AppSingle;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.vo.AddressVo;
import com.jf.plus.core.setting.entity.District;
import com.jf.plus.core.setting.entity.UserAddress;
import com.jf.plus.core.setting.service.DistrictService;
import com.jf.plus.core.setting.service.UserAddressService;

@Controller
public class AddressController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	private UserAddressService userAddressService;

	@Autowired
	private DistrictService districtService;

	/**
	 * 获取地区列表
	 *
	 * @param source
	 * @param parentId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/districtList", method = { RequestMethod.POST })
	@ResponseBody
	public Result districtList(@RequestParam Integer source) {
		Result result = Result.newInstance();
		try {
			List<District> districts = AppSingle.getInstance().getDistricts();
			//缓存下
			if(districts == null){
				District entity = new District();
				entity.setSource(source);
				entity.setParentId("0");
				districts = districtService.findList(entity );
				for(District province : districts){
					List<District> cities = getDistricts(province.getChannelId(), source);
					province.setDistricts(cities);
					for(District city: cities){
						List<District> counties = getDistricts(city.getChannelId(), source);
						city.setDistricts(counties);
					}
				}
				AppSingle.getInstance().setDistricts(districts);
			}
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("districtList", districts);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取地区列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	
	private List<District> getDistricts(String parentId,Integer source){
		District entity = new District();
		entity.setSource(source);
		entity.setParentId(parentId);
		return districtService.findList(entity);
	}

	/**
	 * 获取用户地址信息
	 *
	 * @param token
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/api/getAddress", method = { RequestMethod.POST })
	@ResponseBody
	public Result getAddress(@RequestParam String token, @RequestParam Long siteId, Integer source) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			UserAddress userAddress = new UserAddress();
			userAddress.setUserId((Long) result.getObj());
			userAddress.setSiteId(siteId);
			userAddress.setIsDefault(1);
			userAddress = userAddressService.getEntity(userAddress);
			ResultObj resultObj = new ResultObj();
			resultObj.put("entity", userAddress);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户地址信息失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	
	/**
	 * 获取用户地址信息
	 *
	 * @param token
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/api/getAddressInfo", method = { RequestMethod.POST })
	@ResponseBody
	public Result getAddressInfo(@RequestParam String token, @RequestParam String addressId) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			UserAddress userAddress = userAddressService.get(addressId);
			ResultObj resultObj = new ResultObj();
			resultObj.put("entity", userAddress);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户地址信息失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	
	/**
	 * 获取用户地址集合信息
	 *
	 * @param token
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/api/getAddressList", method = { RequestMethod.POST })
	@ResponseBody
	public Result getAddressList(@RequestParam String token, @RequestParam Long siteId) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			UserAddress userAddress = new UserAddress();
			userAddress.setUserId((Long) result.getObj());
			userAddress.setSiteId(siteId);
			List<UserAddress> addressList = userAddressService.findList(userAddress);
			for(UserAddress address : addressList){
				List<AddressVo> voList = JSON.parseArray(address.getAddress(), AddressVo.class);
				address.setVoList(voList);
			}
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("addressList", addressList);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户地址信息失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 更新地址
	 *
	 * @param token
	 * @param userAddress
	 * @return
	 */
	@RequestMapping(value = "/api/addressSave", method = { RequestMethod.POST })
	@ResponseBody
	public Result addressSave(@RequestBody UserAddress userAddress) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(userAddress.getToken()); // 验证token有效性
			if (!result.isSuccess())
				return result;
			userAddress.setUserId((Long) result.getObj());
			List<AddressVo> voList = userAddress.getVoList();
			userAddress.setAddress(JSON.toJSONString(voList));
			
			//查找是否有默认地址，没有则设置当前地址为默认地址
			UserAddress entity = new UserAddress();
			entity.setUserId(userAddress.getUserId());
			entity.setIsDefault(1);
			if(CollectionUtils.isEmpty(userAddressService.findList(entity))){
				userAddress.setIsDefault(1);
			}

			userAddressService.save(userAddress);
			result.setMsg("更新地址成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("更新地址失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	/**
	 * 删除地址
	 *
	 * @param token
	 * @param addrId
	 * @return
	 */
	@RequestMapping(value = "/api/addressDelete", method = { RequestMethod.POST })
	@ResponseBody
	public Result addressDelete(@RequestParam String token, @RequestParam Long addrId) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			UserAddress userAddress = userAddressService.get(addrId.toString());
			if (null == userAddress) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg("参数错误");
				return result;
			}
			if (((Long) result.getObj()).longValue() != userAddress.getUserId().longValue()) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg("非法操作");
				return result;
			}
			userAddressService.delete(addrId.toString());
			result.setMsg("删除地址成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("删除地址失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	
	/**
	 * 设置默认地址
	 *
	 * @param token
	 * @param addrId
	 * @return
	 */
	@RequestMapping(value = "/api/address/setDefault", method = { RequestMethod.POST })
	@ResponseBody
	public Result setDefault(@RequestParam String token, @RequestParam Long addrId) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			UserAddress userAddress = userAddressService.get(addrId.toString());
			if (null == userAddress) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg("参数错误");
				return result;
			}
			if (((Long) result.getObj()).longValue() != userAddress.getUserId().longValue()) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg("非法操作");
				return result;
			}
			userAddressService.setDefault(addrId, userAddress.getSiteId());
			result.setMsg("设置默认地址成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("设置默认地址失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
}
