package com.jf.plus.core.mallSetting.controller;

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
import com.jf.plus.core.mallSetting.entity.MallSiteRule;
import com.jf.plus.core.mallSetting.service.MallSiteRuleService;

/**
* 商城分销配置表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/mallSiteRule")
public class MallSiteRuleController extends BaseController {

    @Autowired
    private MallSiteRuleService mallSiteRuleService;

    @ModelAttribute
    public MallSiteRule get(@RequestParam(required = false) String id) {
        MallSiteRule entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = mallSiteRuleService.get(id);
        }
        if (entity == null) {
            entity = new MallSiteRule();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<MallSiteRule> page) {
        model.addAttribute("page", page.setList(mallSiteRuleService.findPage(page)));
        return "mallSetting/mallSiteRule/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MallSiteRule mallSiteRule,Model model) {
        model.addAttribute("mallSiteRule", mallSiteRule);
        return "mallSetting/mallSiteRule/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MallSiteRule mallSiteRule, RedirectAttributes redirectAttributes) {
        mallSiteRuleService.save(mallSiteRule);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/mallSiteRule/update?id="+mallSiteRule.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MallSiteRule mallSiteRule, Model model) {
        model.addAttribute("mallSiteRule", mallSiteRule);
        return "mallSetting/mallSiteRule/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MallSiteRule mallSiteRule, RedirectAttributes redirectAttributes) {
        mallSiteRuleService.save(mallSiteRule);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/mallSiteRule/update?id="+mallSiteRule.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        mallSiteRuleService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/mallSiteRule?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
