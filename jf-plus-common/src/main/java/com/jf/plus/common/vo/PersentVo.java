package com.jf.plus.common.vo;

public class PersentVo {

	private String token;

	private String mobile;

	private Long fromId; //

	private Long toId; //

	private Long packsId; //

	private Long accId;

	private String presentText;

	private String presentPhoto;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public Long getPacksId() {
		return packsId;
	}

	public void setPacksId(Long packsId) {
		this.packsId = packsId;
	}

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public String getPresentText() {
		return presentText;
	}

	public void setPresentText(String presentText) {
		this.presentText = presentText;
	}

	public String getPresentPhoto() {
		return presentPhoto;
	}

	public void setPresentPhoto(String presentPhoto) {
		this.presentPhoto = presentPhoto;
	}

}
