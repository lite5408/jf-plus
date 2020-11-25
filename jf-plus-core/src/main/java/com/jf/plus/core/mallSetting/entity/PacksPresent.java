package com.jf.plus.core.mallSetting.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
 * 礼包赠送记录
 * @author Tng
 * @version 1.0
 */
public class PacksPresent extends DataEntity<PacksPresent>{

	private static final long serialVersionUID = 1L;

	private Long fromId;//赠送人
	private Long toId;//被赠送人
	private Long packsId;//礼包ID
	private Long accId;//分发记录ID
	private Date presentDate;//赠送日期
	private String presentText;//赠送语
	private String presentPhoto;//赠送封面图

	public PacksPresent() {
		super();
	}
	public PacksPresent(String id){
		super(id);
	}

	public Long getFromId(){
		return fromId;
	}

	public void setFromId(Long fromId){
		this.fromId = fromId;
	}
	public Long getToId(){
		return toId;
	}

	public void setToId(Long toId){
		this.toId = toId;
	}
	public Long getPacksId(){
		return packsId;
	}

	public void setPacksId(Long packsId){
		this.packsId = packsId;
	}
	public Long getAccId(){
		return accId;
	}

	public void setAccId(Long accId){
		this.accId = accId;
	}
	public Date getPresentDate(){
		return presentDate;
	}

	public void setPresentDate(Date presentDate){
		this.presentDate = presentDate;
	}
	public String getPresentText(){
		return presentText;
	}

	public void setPresentText(String presentText){
		this.presentText = presentText == null ? null : presentText.trim();
	}
	public String getPresentPhoto(){
		return presentPhoto;
	}

	public void setPresentPhoto(String presentPhoto){
		this.presentPhoto = presentPhoto == null ? null : presentPhoto.trim();
	}
}
