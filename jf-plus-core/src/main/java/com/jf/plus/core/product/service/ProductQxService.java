//package com.jf.plus.core.product.service;
//
//import java.util.List;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.jf.plus.core.product.dao.ProductQxDao;
//import com.jf.plus.core.product.entity.ProductQx;
//import cn.iutils.common.service.CrudService;
//
///**
// *  Service层
// * @author Tng
// * @version 1.0
// */
//@Service
//@Transactional(readOnly = true)
//public class ProductQxService extends CrudService<ProductQxDao, ProductQx> {
//
//	@Transactional(readOnly = false)
//	public int saveBatch(List<ProductQx> toPrePickList) {
//		return dao.insertAll(toPrePickList);
//	}
//
//}
