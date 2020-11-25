package com.jf.plus.common.config;

import java.util.List;

/**
 * 
 * @ClassName: AppSingle
 *
 */
public class AppSingle {
	
	
	private List districts;
	
	private static AppSingle appSingle;
	
	private AppSingle(){
		
	}
	
	public static synchronized AppSingle getInstance(){
		if(appSingle == null){
			appSingle = new AppSingle();
		}
		return appSingle;
	}

	public List getDistricts() {
		return districts;
	}

	public void setDistricts(List districts) {
		this.districts = districts;
	}
	
	
	
}
