package com.jf.plus.core.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.service.OrgAccountService;
import com.jf.plus.core.account.service.VoucherAccCashService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.DataScopeEnum;
import cn.iutils.sys.service.OrganizationService;
import cn.iutils.sys.service.UserService;

/**
 * 组织资金账户表 控制器
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/account/userPointAccount")
public class UserPointAccountController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private VoucherAccCashService voucherAccCashService;

	@Autowired
	private OrgAccountService orgAccountService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		User entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = userService.get(id);
		}
		if (entity == null) {
			entity = new User();
		}
		return entity;
	}

	@RequestMapping(value = "/listTree")
	public String listTree(User user,Model model, Page<User> page) {
		Organization organization1 = new Organization();
		organization1.setUser(UserUtils.getLoginUser());
		organization1.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "a", "a"));
		organization1.setOrderBy("a.parent_ids asc");
		List<Organization> organizationList = organizationService.findList(organization1);
		model.addAttribute("organizationList", organizationList);
		//初始化加载第一个
		if(JStringUtils.isBlank(user.getOrganizationId()) && organizationList.size()>0){
			user.setOrganizationId(organizationList.get(0).getId());
		}
		page.setEntity(user);
		List<User> userList = userService.findPage(page);
		for(User userItem : userList){
			VoucherAccCash entity = new VoucherAccCash();
			entity.setBindUser(Long.valueOf(userItem.getId()));
			entity.setDistOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
			userItem.setBlance(findMyBlance(entity));
		}
		model.addAttribute("page", page.setList(userList));
		return "account/userPointAccount/list_tree";
	}

	@RequestMapping(value = "/list")
	public String list(User user,Model model, Page<User> page) {
		user.setUser(UserUtils.getLoginUser());
		user.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.org, "b", "a"));
		page.setEntity(user);
		List<User> userList = userService.findPage(page);
		for(User userItem : userList){
			VoucherAccCash entity = new VoucherAccCash();
			entity.setBindUser(Long.valueOf(userItem.getId()));
			entity.setDistOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
			userItem.setBlance(findMyBlance(entity));
			userItem.setDistributeBlance(findMyDistributeBlance(entity));
		}
		model.addAttribute("page", page.setList(userList));
		return "account/userPointAccount/list";
	}

	private Double findMyBlance(VoucherAccCash entity) {
		return voucherAccCashService.findMyBlance(entity);
	}
	
	private Double findMyDistributeBlance(VoucherAccCash entity) {
		return voucherAccCashService.findMyDistributeBlance(entity);
	}

	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	public String recharge(User user,Model model) {
		user.setOrganizationId(UserUtils.getLoginUser().getOrganizationId());
		model.addAttribute("user", user);
		return "account/userPointAccount/recharge";
	}

	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public String recharge(VoucherAccCash voucherAccCash, Integer changeType,RedirectAttributes redirectAttributes) {
		voucherAccCash.setDistOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		if (changeType == 1) {
			//集采转分发
			if (findMyBlance(voucherAccCash) >= voucherAccCash.getTotalBlance().doubleValue()) {
				int ret = voucherAccCashService.changeIntoFF(voucherAccCash);
				if(ret == 0){
					addMessage(redirectAttributes, "拨化失败，用户集采额度不足");
				}else if(ret == -1){
					//没有配置机构的积分比例
					addMessage(redirectAttributes,"当前机构积分比例参数缺失，请联系系统管理员完善该配置后操作！");
				}else{
					addMessage(redirectAttributes, "拨化成功");
				}
			}else {
				addMessage(redirectAttributes, "拨化失败，用户集采额度不足");
				return "redirect:"+ adminPath +"/account/userPointAccount/recharge?id="+voucherAccCash.getBindUser();
			}
		}else if(changeType == 2){
			//分发转集采
			if (findMyDistributeBlance(voucherAccCash) >= voucherAccCash.getTotalBlance().doubleValue()) {
				int ret = voucherAccCashService.changeIntoJC(voucherAccCash);
				if(ret == 0){
					addMessage(redirectAttributes, "拨化失败，用户积分额度不足");
				}else if(ret == -1){
					//没有配置机构的积分比例
					addMessage(redirectAttributes,"当前机构积分比例参数缺失，请联系系统管理员完善该配置后操作！");
				}else{
					addMessage(redirectAttributes, "拨化成功");
				}
			}else {
				addMessage(redirectAttributes, "拨化失败，用户积分额度不足");
				return "redirect:"+ adminPath +"/account/userPointAccount/recharge?id="+voucherAccCash.getBindUser();
			}
		}
		return "redirect:"+ adminPath +"/account/userPointAccount/list";
	}


	public static void main(String[] args) {
		Double d = 200D;

		Double d1 = 893250D;
		if(d1 < d){
			System.out.println(1);
		}
	}
}
