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
import com.jf.plus.core.product.entity.CategoryAttrInfo;
import com.jf.plus.core.product.service.CategoryAttrInfoService;

/**
* 分类属性值表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/categoryAttrInfo")
public class CategoryAttrInfoController extends BaseController {

    @Autowired
    private CategoryAttrInfoService categoryAttrInfoService;

    @ModelAttribute
    public CategoryAttrInfo get(@RequestParam(required = false) String id) {
        CategoryAttrInfo entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = categoryAttrInfoService.get(id);
        }
        if (entity == null) {
            entity = new CategoryAttrInfo();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<CategoryAttrInfo> page) {
        model.addAttribute("page", page.setList(categoryAttrInfoService.findPage(page)));
        return "product/categoryAttrInfo/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(CategoryAttrInfo categoryAttrInfo,Model model) {
        model.addAttribute("categoryAttrInfo", categoryAttrInfo);
        return "product/categoryAttrInfo/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(CategoryAttrInfo categoryAttrInfo, RedirectAttributes redirectAttributes) {
        categoryAttrInfoService.save(categoryAttrInfo);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/categoryAttrInfo/update?id="+categoryAttrInfo.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(CategoryAttrInfo categoryAttrInfo, Model model) {
        model.addAttribute("categoryAttrInfo", categoryAttrInfo);
        return "product/categoryAttrInfo/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(CategoryAttrInfo categoryAttrInfo, RedirectAttributes redirectAttributes) {
        categoryAttrInfoService.save(categoryAttrInfo);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/categoryAttrInfo/update?id="+categoryAttrInfo.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        categoryAttrInfoService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/categoryAttrInfo?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
