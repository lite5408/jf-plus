package com.jf.plus.core.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.BaseController;
import com.jf.plus.core.account.entity.OrgAccountRecharge;
import com.jf.plus.core.account.service.OrgAccountRechargeService;

/**
* 组织资金账户交易流水表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/account/orgAccountRecharge")
public class OrgAccountRechargeController extends BaseController {

    @Autowired
    private OrgAccountRechargeService orgAccountRechargeService;

    @ModelAttribute
    public OrgAccountRecharge get(@RequestParam(required = false) String id) {
        OrgAccountRecharge entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orgAccountRechargeService.get(id);
        }
        if (entity == null) {
            entity = new OrgAccountRecharge();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, OrgAccountRecharge orgAccountRecharge,Page<OrgAccountRecharge> page) {
    	if (orgAccountRecharge.getAccountId() == null || orgAccountRecharge.getAccountId() == 0L) {
            model.addAttribute("page", page.setList(null));
		}else {
			page.setEntity(orgAccountRecharge);
	        model.addAttribute("page", page.setList(orgAccountRechargeService.findPage(page)));
		}
        return "account/orgAccountRecharge/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrgAccountRecharge orgAccountRecharge,Model model) {
        model.addAttribute("orgAccountRecharge", orgAccountRecharge);
        return "account/orgAccountRecharge/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrgAccountRecharge orgAccountRecharge, RedirectAttributes redirectAttributes) {
        orgAccountRechargeService.save(orgAccountRecharge);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/account/orgAccountRecharge/update?id="+orgAccountRecharge.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrgAccountRecharge orgAccountRecharge, Model model) {
        model.addAttribute("orgAccountRecharge", orgAccountRecharge);
        return "account/orgAccountRecharge/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrgAccountRecharge orgAccountRecharge, RedirectAttributes redirectAttributes) {
        orgAccountRechargeService.save(orgAccountRecharge);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/account/orgAccountRecharge/update?id="+orgAccountRecharge.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orgAccountRechargeService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/account/orgAccountRecharge?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
