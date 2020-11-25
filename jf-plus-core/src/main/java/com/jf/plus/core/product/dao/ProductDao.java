package com.jf.plus.core.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.product.entity.Product;
import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 商品表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface ProductDao extends ICrudDao<Product> {

	int insertAll(List<Product> list);

	public List<Product> findbrandNamePage(@Param("page") Page<Product> page);

	int outShelves(@Param("source") int source, @Param("itemCode") String itemCode);

	int shelvesProduct(@Param("source") int source, @Param("itemCode") String itemCode);

	int syncPriceProduct(@Param("source") Integer source, @Param("itemCode") String itemCode,@Param("supplyPrice") Double supplyPrice,@Param("markPrice") Double markPrice);

	int countSupply(@Param("page") Page<Product> page);

	List<Product> findSupplyPage(@Param("page") Page<Product> page);

	Product getTopProduct(Product product);

	List<Product> findTopList(Product product);
	
	List<Product> findTopPage(@Param("page") Page<Product> page);
	
	int countTop(@Param("page") Page<Product> page);

	int productMsg(@Param("productId") String productId, @Param("orgGroupId") String orgGroupId);
	
	int orderMsg(@Param("productId") String productId);

	Double calcMgrPrice(@Param("cateName") String cateName, @Param("saleType") String saleType, @Param("cbPrice") Double cbPrice);

	Integer getSaleNum(@Param("productId") String productId);

	int sumProdCount(@Param("page") Page<Order> page);

	Map<String, Object> buyerProdReport(@Param("page") Page<Map<String, Object>> page);

	Map<String, Object> buyerOrderReport(@Param("page") Page<Map<String, Object>> page);
}
