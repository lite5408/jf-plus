package com.jf.plus.common.vo;

public class DistVo {

	private String mobile;

	private Long memberId;

	private Long distOrgId;

	private Long distUserId;

	private double blance;

	private String remarks;

	public DistVo() {

	}

	public DistVo(String mobile, Long distOrgId, Long distUserId,String remarks) {
		super();
		this.mobile = mobile;
		this.distOrgId = distOrgId;
		this.distUserId = distUserId;
		this.remarks = remarks;
	}

	public DistVo(String mobile, Long distOrgId, Long distUserId, double blance ,String remarks) {
		super();
		this.mobile = mobile;
		this.distOrgId = distOrgId;
		this.distUserId = distUserId;
		this.blance = blance;
		this.remarks = remarks;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getDistOrgId() {
		return distOrgId;
	}

	public void setDistOrgId(Long distOrgId) {
		this.distOrgId = distOrgId;
	}

	public Long getDistUserId() {
		return distUserId;
	}

	public void setDistUserId(Long distUserId) {
		this.distUserId = distUserId;
	}

	public double getBlance() {
		return blance;
	}

	public void setBlance(double blance) {
		this.blance = blance;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
