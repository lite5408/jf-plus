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

import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.service.BlanceService;
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
@RequestMapping("${adminPath}/account/userAccount")
public class UserAccountController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private VoucherAccCashService voucherAccCashService;

    @Autowired
    private BlanceService blanceService;

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
		return "account/userAccount/list_tree";
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
		}
		model.addAttribute("page", page.setList(userList));
		return "account/userAccount/list";
	}

	private Double findMyBlance(VoucherAccCash entity) {
		return voucherAccCashService.findMyBlance(entity);
	}

	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	public String recharge(User user,Model model) {
		user.setOrganizationId(UserUtils.getLoginUser().getOrganizationId());
		model.addAttribute("user", user);
		return "account/userAccount/recharge";
	}

	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public String recharge(VoucherAccCash voucherAccCash ,RedirectAttributes redirectAttributes) {
		//验证机构可用余额
		String orgId = UserUtils.getLoginUser().getOrganizationId();
    	if(voucherAccCash.getTotalBlance() > 0 && blanceService.getOrgBlance(orgId).doubleValue() < voucherAccCash.getTotalBlance().doubleValue()){
			addMessage(redirectAttributes, "充值失败，机构余额不足");
			return "redirect:"+ adminPath +"/account/userAccount/recharge?id="+voucherAccCash.getBindUser();
		}
    	if(voucherAccCash.getTotalBlance() < 0 && 
    			(blanceService.getJCBlance(voucherAccCash.getBindUser() + "").doubleValue() + voucherAccCash.getTotalBlance().doubleValue() < 0D)){
    		addMessage(redirectAttributes, "充值失败，用户余额不满足负充值需要");
			return "redirect:"+ adminPath +"/account/userAccount/recharge?id="+voucherAccCash.getBindUser();
    	}
    	
    	int ret = 0;
    	if(voucherAccCash.getTotalBlance() >= 0){
    		ret = voucherAccCashService.recharge(voucherAccCash);
    	}else{
    		ret = voucherAccCashService.negativeRecharge(voucherAccCash);
    	}
    	
		if(ret == 0){
			addMessage(redirectAttributes, "充值失败，余额不足");
		}else{
			addMessage(redirectAttributes, "充值成功");
		}

		return "redirect:"+ adminPath +"/account/userAccount/recharge?id="+voucherAccCash.getBindUser();
	}


	public static void main(String[] args) {
		Double d = 200D;

		Double d1 = 893250D;
		if(d1 < d){
			System.out.println(1);
		}
	}
}
