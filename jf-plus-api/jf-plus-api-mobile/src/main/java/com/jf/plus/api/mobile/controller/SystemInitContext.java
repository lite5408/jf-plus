//package com.jf.plus.api.mobile.controller;
//
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.jf.plus.common.config.AppSingle;
//import com.jf.plus.core.setting.entity.District;
//import com.jf.plus.core.setting.service.DistrictService;
//
//public class SystemInitContext {
//	
//	@Autowired
//	private DistrictService districtService;
//	
//	@PostConstruct
//	public void loadAddress(){
//		District entity = new District();
//		Integer source = 2;
//		entity.setSource(source );
//		entity.setParentId("0");
//		List<District> districts = districtService.findList(entity );
//		for(District province : districts){
//			List<District> cities = getDistricts(province.getChannelId(), source);
//			province.setDistricts(cities);
//			for(District city: cities){
//				List<District> counties = getDistricts(city.getChannelId(), source);
//				city.setDistricts(counties);
//			}
//		}
//		AppSingle.getInstance().setDistricts(districts);
//	}
//	
//	private List<District> getDistricts(String parentId,Integer source){
//		District entity = new District();
//		entity.setSource(source);
//		entity.setParentId(parentId);
//		return districtService.findList(entity);
//	}
//}
