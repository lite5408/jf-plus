package com.jf.plus.core.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.core.account.entity.OrgAccount;
import com.jf.plus.core.account.service.OrgAccountService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.enums.DataScopeEnum;
import cn.iutils.sys.service.OrganizationService;

/**
* 组织资金账户表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/account/orgAccount")
public class OrgAccountController extends BaseController {

    @Autowired
    private OrgAccountService orgAccountService;
    
    @Autowired
    private OrganizationService organizationService;

    @ModelAttribute
    public OrgAccount get(@RequestParam(required = false) String id) {
        OrgAccount entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orgAccountService.get(id);
        }
        if (entity == null) {
            entity = new OrgAccount();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, OrgAccount orgAccount,Page<OrgAccount> page) {
    	
    	page.setEntity(orgAccount);
    	page.setOrderBy("org_p.id asc");
    	orgAccount.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgChild, "org", "a"));
    	page.setEntity(orgAccount);
    	
        model.addAttribute("page", page.setList(orgAccountService.findPage(page)));
        return "account/orgAccount/list";
    }
    
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public String recharge(OrgAccount orgAccount,Model model) {
        model.addAttribute("orgAccount", orgAccount);
        return "account/orgAccount/recharge";
    }
    
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public String recharge(OrgAccount orgAccount, RedirectAttributes redirectAttributes) {
    	orgAccount.setOrgId(orgAccount.getOrganizationId());
    	
    	int ret = orgAccountService.recharge(orgAccount);
    	if(ret == 0){
    		addMessage(redirectAttributes, "充值失败，余额不足");
    	}else{
    		addMessage(redirectAttributes, "充值成功");
    	}
    	
    	return "redirect:"+ adminPath +"/account/orgAccount/recharge?id="+orgAccount.getId();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrgAccount orgAccount,Model model) {
        model.addAttribute("orgAccount", orgAccount);
        return "account/orgAccount/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrgAccount orgAccount, RedirectAttributes redirectAttributes) {
        orgAccountService.save(orgAccount);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/account/orgAccount/update?id="+orgAccount.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrgAccount orgAccount, Model model) {
        model.addAttribute("orgAccount", orgAccount);
        return "account/orgAccount/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrgAccount orgAccount, RedirectAttributes redirectAttributes) {
        orgAccountService.save(orgAccount);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/account/orgAccount/update?id="+orgAccount.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orgAccountService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/account/orgAccount?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping(value={"/setRatio",""})
    public String setRatio(Model model, Organization organization,Page<Organization> page) {
		organization.setParentIds(UserUtils.getLoginUser().getOrganizationId());
		page.setEntity(organization);
		List<Organization> organizationList = organizationService.findAllLowList(organization);
        model.addAttribute("organizationList", organizationList);
        return "account/orgAccount/setRatio";
    }
    
    @ResponseBody
	@RequestMapping(value = "/getRatio")
	public Result batchOutShevlesProduct(String orgId) {
		Result r = Result.newInstance();
		try{
			Organization organization = organizationService.get(orgId);
			r.setObj(organization.getMarketRatio());
			r.setSuccess(true);
			return r;
		} catch (Exception e) {
			logger.error("获取积分比例失败：{}", e);
			return Result.newExceptionInstance();
		}
	}
    
	
	@RequestMapping(value={"/changeRetio",""})
    public String changeRetio(String orgId, String setAll, String marketRatio,Page<Organization> page, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(setAll) && setAll.equals("1")) {
			//统一设置
			Organization organization = new Organization();
			organization.setParentIds(UserUtils.getLoginUser().getOrganizationId());
			organization.setMarketRatio(Integer.valueOf(marketRatio));
			organizationService.changeRatioByParentIds(organization);
		}else {
			//设置对应的orgId
			Organization organization = organizationService.get(orgId);
			organization.setMarketRatio(Integer.valueOf(marketRatio));
			organizationService.save(organization);
		}
		addMessage(redirectAttributes, "设置成功");
        return "redirect:" + adminPath + "/account/orgAccount/list";
    }
}
