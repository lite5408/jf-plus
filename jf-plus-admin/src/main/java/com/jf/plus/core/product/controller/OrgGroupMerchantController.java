package com.jf.plus.core.product.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jf.plus.core.product.entity.OrgGroupMerchant;
import com.jf.plus.core.product.service.OrgGroupMerchantService;

/**
* 供应商分组表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/orgGroupMerchant")
public class OrgGroupMerchantController extends BaseController {

    @Autowired
    private OrgGroupMerchantService OrgGroupMerchantService;

    @ModelAttribute
    public OrgGroupMerchant get(@RequestParam(required = false) String id) {
        OrgGroupMerchant entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = OrgGroupMerchantService.get(id);
        }
        if (entity == null) {
            entity = new OrgGroupMerchant();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<OrgGroupMerchant> page) {
        model.addAttribute("page", page.setList(OrgGroupMerchantService.findPage(page)));
        return "product/orgGroupMerchant/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrgGroupMerchant OrgGroupMerchant,Model model) {
        model.addAttribute("OrgGroupMerchant", OrgGroupMerchant);
        return "product/orgGroupMerchant/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrgGroupMerchant OrgGroupMerchant, RedirectAttributes redirectAttributes) {
        OrgGroupMerchantService.save(OrgGroupMerchant);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/orgGroupMerchant/update?id="+OrgGroupMerchant.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrgGroupMerchant OrgGroupMerchant, Model model) {
        model.addAttribute("OrgGroupMerchant", OrgGroupMerchant);
        return "product/orgGroupMerchant/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrgGroupMerchant OrgGroupMerchant, RedirectAttributes redirectAttributes) {
        OrgGroupMerchantService.save(OrgGroupMerchant);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/orgGroupMerchant/update?id="+OrgGroupMerchant.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        OrgGroupMerchantService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/orgGroupMerchant?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
