package com.jf.plus.core.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.account.entity.VoucherFlow;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 电子券卡号交易记录表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface VoucherFlowDao extends ICrudDao<VoucherFlow> {

	public VoucherFlow findAccRecentOrderFlow(VoucherFlow voucherFlow);

	public List<VoucherFlow> findFlowList(@Param("page") Page<VoucherFlow> page);

	public int findFlowCount(@Param("page") Page<VoucherFlow> page);

}
