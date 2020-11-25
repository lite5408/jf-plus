package com.jf.plus.common.vo;

import java.io.Serializable;

public class SiteVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String siteId;
	private String siteName;
	
	public SiteVo(){
		super();
	}
	
	public SiteVo(String siteId){
		this.siteId = siteId;
	}
	
	public SiteVo(String siteId, String siteName) {
		super();
		this.siteId = siteId;
		this.siteName = siteName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}
