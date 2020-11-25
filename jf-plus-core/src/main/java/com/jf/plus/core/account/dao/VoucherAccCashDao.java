package com.jf.plus.core.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.account.entity.VoucherAccCash;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 电子券卡号信息表 DAO接口
 *
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface VoucherAccCashDao extends ICrudDao<VoucherAccCash> {

	public List<VoucherAccCash> findVoucherAccCashList(@Param("page") Page<VoucherAccCash> page);

	public List<VoucherAccCash> findVoucherAccCashListByJc(@Param("page") Page<VoucherAccCash> page);

	public int findVoucherCount(@Param("page") Page<VoucherAccCash> page);

	public List<VoucherAccCash> findVoucherList(@Param("page") Page<VoucherAccCash> page);

	public int findVoucherAccCashCount(@Param("page") Page<VoucherAccCash> page);

	public Double findBlance(VoucherAccCash entity);

	public Double findDistributeBlance(VoucherAccCash entity);

	public List<VoucherAccCash> findPointPage(@Param("page") Page<VoucherAccCash> page);

	public int countPointPage(@Param("page") Page<VoucherAccCash> page);

	public VoucherAccCash getEntityByBind(VoucherAccCash entity);

}
