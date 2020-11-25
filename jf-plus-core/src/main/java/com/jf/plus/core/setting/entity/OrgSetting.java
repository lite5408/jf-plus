package com.jf.plus.core.setting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 机构配置表
 * 
 * @author Tng
 * @version 1.0
 */
public class OrgSetting extends DataEntity<OrgSetting> {

	private static final long serialVersionUID = 1L;

	private String channel;// 上架的渠道
	private String welcomePage;// 主页
	private Long orgId;// 机构ID

	public OrgSetting() {
		super();
	}

	public OrgSetting(String id) {
		super(id);
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getWelcomePage() {
		return welcomePage;
	}

	public void setWelcomePage(String welcomePage) {
		this.welcomePage = welcomePage;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
