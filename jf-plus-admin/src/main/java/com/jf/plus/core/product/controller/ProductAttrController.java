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
import com.jf.plus.core.product.entity.ProductAttr;
import com.jf.plus.core.product.service.ProductAttrService;

/**
* 商品属性表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productAttr")
public class ProductAttrController extends BaseController {

    @Autowired
    private ProductAttrService productAttrService;

    @ModelAttribute
    public ProductAttr get(@RequestParam(required = false) String id) {
        ProductAttr entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productAttrService.get(id);
        }
        if (entity == null) {
            entity = new ProductAttr();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<ProductAttr> page) {
        model.addAttribute("page", page.setList(productAttrService.findPage(page)));
        return "product/productAttr/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductAttr productAttr,Model model) {
        model.addAttribute("productAttr", productAttr);
        return "product/productAttr/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductAttr productAttr, RedirectAttributes redirectAttributes) {
        productAttrService.save(productAttr);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productAttr/update?id="+productAttr.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductAttr productAttr, Model model) {
        model.addAttribute("productAttr", productAttr);
        return "product/productAttr/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductAttr productAttr, RedirectAttributes redirectAttributes) {
        productAttrService.save(productAttr);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productAttr/update?id="+productAttr.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productAttrService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productAttr?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
