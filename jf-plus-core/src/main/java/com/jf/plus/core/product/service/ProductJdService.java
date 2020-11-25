//package com.jf.plus.core.product.service;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import cn.iutils.common.service.CrudService;
//import com.jf.plus.core.product.dao.ProductJdDao;
//import com.jf.plus.core.product.entity.ProductJd;
//import com.jf.plus.core.product.entity.ProductQx;
//
///**
//*  Service层
//* @author Tng
//* @version 1.0
//*/
//@Service
//@Transactional(readOnly = true)
//public class ProductJdService extends CrudService<ProductJdDao, ProductJd> {
//
//	@Transactional(readOnly = false)
//	public int saveBatch(List<ProductJd> toPrePickList) {
//		return dao.insertAll(toPrePickList);
//	}
//	
//}
