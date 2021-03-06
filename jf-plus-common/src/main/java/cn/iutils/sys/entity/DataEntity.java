package cn.iutils.sys.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.common.BaseEntity;
import cn.iutils.common.utils.UserUtils;

/**
 * 数据实体
 * Created by CC on 16/9/24.
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	@JSONField(serialize=false)
	private User user;//当前用户
	@JSONField(serialize=false)
	private User createUser;//创建人
	@JSONField(serialize=false)
	private User updateUser;//更新人

	public DataEntity() {
		this.status = STATUS_NORMAL;
	}

	public DataEntity(String id) {
		this();
		this.id = id;
	}


	/**
	 * 插入之前执行方法，子类实现
	 */
	@Override
	public void preInsert() {
		User user = UserUtils.getLoginUser();
		if (user != null) {
			this.updateBy = user.getId();
			this.createBy = user.getId();
		}
		this.createDate = new Date();
		this.updateDate = new Date();
	}

	/**
	 * 更新之前执行方法，子类实现
	 */
	@Override
	public void preUpdate() {
		User user = UserUtils.getLoginUser();
		if (user != null) {
			this.updateBy = user.getId();
		}
		this.updateDate = new Date();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}
}

