//package com.jf.plus.core.product.service;
//
//import java.util.List;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.jf.plus.core.product.dao.ProductYxDao;
//import com.jf.plus.core.product.entity.ProductYx;
//import cn.iutils.common.service.CrudService;
//
///**
// *  Service层
// * @author Tng
// * @version 1.0
// */
//@Service
//@Transactional(readOnly = true)
//public class ProductYxService extends CrudService<ProductYxDao, ProductYx> {
//
//	@Transactional(readOnly = false)
//	public int saveBatch(List<ProductYx> toPrePickList) {
//		return dao.insertAll(toPrePickList);
//	}
//
//}
