package cn.iutils.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.common.config.JConfig;

/**
 * 用户表
 *
 * @author cc
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;

	private String no;// 编号
	private String organizationIds; // 组织机构编号
	private String username; // 用户名
	@JSONField(serialize=false)
	private String password; // 密码
	@JSONField(serialize=false)
	private String salt; // 加密密码的盐
	@JSONField(serialize=false)
	private List<String> roleIds; // 拥有的角色列表
	@JSONField(serialize=false)
	private String roleIdsStr;
	private Boolean locked = Boolean.FALSE;// 是否锁定
	private Boolean isDept = Boolean.FALSE;// 是否部门管理员
	private String name;// 姓名或昵称
	private String email;// 电子邮件
	private String phone;// 电话
	private String mobile;// 手机
	private String photo;// 头像
	@JSONField(serialize=false)
	private Integer type;// 用户类型
	@JSONField(serialize=false)
	private String roleGroupIds;// 用户角色组ids
	@JSONField(serialize=false)
	private String source;//用户来源：sys：后台，mem：前台会员
	@JSONField(serialize=false)
	private String openId;//用户第三方标识，默认是手机号


	/** 自定义属性 **/
	private String organizationId;// 当前登录的部门
	private Organization organization;// 当前登录的部门
	private String organizationNames;// 所属部门多个
	private Double blance;// 用户集采额度
	private Double distributeBlance; // 用户积分额度
	private String photoUrl;// 图片路径
	private String roleGroupNames;// 用户角色组名称
	@JSONField(serialize=false)
	private String loginType; // 登录方式
	@JSONField(serialize=false)
	private String role; // 角色查询

	public User() {
		super();
	}

	public User(String id) {
		super(id);
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationIds() {
		return organizationIds;
	}

	public void setOrganizationIds(String organizationIds) {
		this.organizationIds = organizationIds;
	}

	public String getOrganizationNames() {
		return organizationNames;
	}

	public void setOrganizationNames(String organizationNames) {
		this.organizationNames = organizationNames;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@JSONField(serialize=false)
	public String getCredentialsSalt() {
		return username + salt;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<String> getRoleIds() {
		if (roleIds == null) {
			roleIds = new ArrayList<String>();
		}
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleIdsStr() {
		if (CollectionUtils.isEmpty(roleIds)) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		for (String roleId : roleIds) {
			s.append(roleId);
			s.append(",");
		}
		return s.toString();
	}

	public void setRoleIdsStr(String roleIdsStr) {
		if (StringUtils.isEmpty(roleIdsStr)) {
			return;
		}
		String[] roleIdStrs = roleIdsStr.split(",");
		for (String roleIdStr : roleIdStrs) {
			if (StringUtils.isEmpty(roleIdStr)) {
				continue;
			}
			getRoleIds().add(roleIdStr);
		}
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getIsDept() {
		return isDept;
	}

	public void setIsDept(Boolean isDept) {
		this.isDept = isDept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isAdmin() {
		return isAdmin(this.id);
	}

	public static boolean isAdmin(String id) {
		return id != null && "1".equals(id);
	}

	public Double getBlance() {
		return blance;
	}

	public void setBlance(Double blance) {
		this.blance = blance;
	}

	public String getPhotoUrl() {
		if (StringUtils.isEmpty(getPhoto()))
			return null;

		return JConfig.getConfig("url.image") + "/" + getPhoto();
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getRoleGroupIds() {
		return roleGroupIds;
	}

	public void setRoleGroupIds(String roleGroupIds) {
		this.roleGroupIds = roleGroupIds;
	}

	public String getRoleGroupNames() {
		return roleGroupNames;
	}

	public void setRoleGroupNames(String roleGroupNames) {
		this.roleGroupNames = roleGroupNames;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Double getDistributeBlance() {
		return distributeBlance;
	}

	public void setDistributeBlance(Double distributeBlance) {
		this.distributeBlance = distributeBlance;
	}
	
}
