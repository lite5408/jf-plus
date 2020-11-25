package com.jf.plus.core.site.controller;

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
import com.jf.plus.core.site.entity.SiteOrg;
import com.jf.plus.core.site.service.SiteOrgService;

/**
* 站点组织关系表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/site/siteOrg")
public class SiteOrgController extends BaseController {

    @Autowired
    private SiteOrgService siteOrgService;

    @ModelAttribute
    public SiteOrg get(@RequestParam(required = false) String id) {
        SiteOrg entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = siteOrgService.get(id);
        }
        if (entity == null) {
            entity = new SiteOrg();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<SiteOrg> page) {
        model.addAttribute("page", page.setList(siteOrgService.findPage(page)));
        return "site/siteOrg/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(SiteOrg siteOrg,Model model) {
        model.addAttribute("siteOrg", siteOrg);
        return "site/siteOrg/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SiteOrg siteOrg, RedirectAttributes redirectAttributes) {
        siteOrgService.save(siteOrg);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/site/siteOrg/update?id="+siteOrg.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(SiteOrg siteOrg, Model model) {
        model.addAttribute("siteOrg", siteOrg);
        return "site/siteOrg/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SiteOrg siteOrg, RedirectAttributes redirectAttributes) {
        siteOrgService.save(siteOrg);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/site/siteOrg/update?id="+siteOrg.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        siteOrgService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/site/siteOrg?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
