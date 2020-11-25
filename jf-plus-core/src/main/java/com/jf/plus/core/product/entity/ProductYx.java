//package com.jf.plus.core.product.entity;
//
//import java.util.List;
//
//import com.jf.plus.prod.channel.yx.api.entity.CategoryVO;
//import com.jf.plus.prod.channel.yx.api.entity.ItemAttrVO;
//import com.jf.plus.prod.channel.yx.api.entity.ItemDetailVO;
//import com.jf.plus.prod.channel.yx.api.entity.SkuVO;
//
//import cn.iutils.sys.entity.DataEntity;
//
///**
// *
// * @author Tng
// * @version 1.0
// */
//public class ProductYx extends DataEntity<ProductYx>{
//
//	private static final long serialVersionUID = 1L;
//
//	private String name;//
//	private String listPicUrl;//
//	private String simpleDesc;//
//	private String skuJson;//
//	private String itemDetailJson;//
//	private String attrJson;//
//	private String categoryPathJson;//
//
//	private List<SkuVO> skuList;
//	private ItemDetailVO itemDetail;
//	private List<ItemAttrVO> attrList;
//	private List<List<CategoryVO>> categoryPathList;
//
//	public ProductYx() {
//		super();
//	}
//	public ProductYx(String id){
//		super(id);
//	}
//
//	public String getName(){
//		return name;
//	}
//
//	public void setName(String name){
//		this.name = name;
//	}
//	public String getListPicUrl(){
//		return listPicUrl;
//	}
//
//	public void setListPicUrl(String listPicUrl){
//		this.listPicUrl = listPicUrl;
//	}
//	public String getSimpleDesc(){
//		return simpleDesc;
//	}
//
//	public void setSimpleDesc(String simpleDesc){
//		this.simpleDesc = simpleDesc;
//	}
//	public String getSkuJson(){
//		return skuJson;
//	}
//
//	public void setSkuJson(String skuJson){
//		this.skuJson = skuJson;
//	}
//	public String getItemDetailJson(){
//		return itemDetailJson;
//	}
//
//	public void setItemDetailJson(String itemDetailJson){
//		this.itemDetailJson = itemDetailJson;
//	}
//	public String getAttrJson(){
//		return attrJson;
//	}
//
//	public void setAttrJson(String attrJson){
//		this.attrJson = attrJson;
//	}
//	public String getCategoryPathJson(){
//		return categoryPathJson;
//	}
//
//	public void setCategoryPathJson(String categoryPathJson){
//		this.categoryPathJson = categoryPathJson;
//	}
//	public List<SkuVO> getSkuList() {
//		return skuList;
//	}
//	public void setSkuList(List<SkuVO> skuList) {
//		this.skuList = skuList;
//	}
//	public ItemDetailVO getItemDetail() {
//		return itemDetail;
//	}
//	public void setItemDetail(ItemDetailVO itemDetail) {
//		this.itemDetail = itemDetail;
//	}
//	public List<ItemAttrVO> getAttrList() {
//		return attrList;
//	}
//	public void setAttrList(List<ItemAttrVO> attrList) {
//		this.attrList = attrList;
//	}
//	public List<List<CategoryVO>> getCategoryPathList() {
//		return categoryPathList;
//	}
//	public void setCategoryPathList(List<List<CategoryVO>> categoryPathList) {
//		this.categoryPathList = categoryPathList;
//	}
//}
