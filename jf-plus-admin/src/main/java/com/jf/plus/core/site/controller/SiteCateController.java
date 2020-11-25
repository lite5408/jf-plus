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
import cn.iutils.common.utils.UserUtils;
import cn.iutils.common.BaseController;
import com.jf.plus.core.site.entity.SiteCate;
import com.jf.plus.core.site.service.SiteCateService;

/**
* 站点上架商品分类 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/site/siteCate")
public class SiteCateController extends BaseController {

    @Autowired
    private SiteCateService siteCateService;

    @ModelAttribute
    public SiteCate get(@RequestParam(required = false) String id) {
        SiteCate entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = siteCateService.get(id);
        }
        if (entity == null) {
            entity = new SiteCate();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, Page<SiteCate> page) {
        model.addAttribute("page", page.setList(siteCateService.findPage(page)));
        return "site/siteCate/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(SiteCate siteCate,Model model) {
        model.addAttribute("siteCate", siteCate);
        return "site/siteCate/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SiteCate siteCate, RedirectAttributes redirectAttributes) {
        siteCateService.save(siteCate);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/site/siteCate/update?id="+siteCate.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(SiteCate siteCate, Model model) {
        model.addAttribute("siteCate", siteCate);
        return "site/siteCate/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SiteCate siteCate, RedirectAttributes redirectAttributes) {
        siteCateService.save(siteCate);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/site/siteCate/update?id="+siteCate.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        siteCateService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/site/siteCate?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping(value="/select")
    public String select(Model model,Page<SiteCate> page){
    	page.setPageSize(Integer.MAX_VALUE);
    	page.setOrderBy("mpc.cat_pid");
    	page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());
    	model.addAttribute("mallProductCateList", siteCateService.findSiteCate(page));
    	return "select/siteCate/select";
    }
}
