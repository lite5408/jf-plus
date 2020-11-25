package com.jf.plus.core.account.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jf.plus.core.account.entity.Voucher;
import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 电子券信息表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface VoucherDao extends ICrudDao<Voucher> {

	public List<Voucher> findDistVoucherList(@Param("page") Page<Voucher> page);

	public int findVoucherCount(@Param("page") Page<Voucher> page);

	public List<Voucher> findVoucherList(@Param("page") Page<Voucher> page);

}
