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
import com.jf.plus.core.product.entity.ProductSaleRule;
import com.jf.plus.core.product.service.ProductSaleRuleService;

/**
* 销售规则 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productSaleRule")
public class ProductSaleRuleController extends BaseController {

    @Autowired
    private ProductSaleRuleService productSaleRuleService;

    @ModelAttribute
    public ProductSaleRule get(@RequestParam(required = false) String id) {
        ProductSaleRule entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productSaleRuleService.get(id);
        }
        if (entity == null) {
            entity = new ProductSaleRule();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<ProductSaleRule> page) {
        model.addAttribute("page", page.setList(productSaleRuleService.findPage(page)));
        return "product/productSaleRule/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductSaleRule productSaleRule,Model model) {
        model.addAttribute("productSaleRule", productSaleRule);
        return "product/productSaleRule/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductSaleRule productSaleRule, RedirectAttributes redirectAttributes) {
        productSaleRuleService.save(productSaleRule);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productSaleRule/update?id="+productSaleRule.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductSaleRule productSaleRule, Model model) {
        model.addAttribute("productSaleRule", productSaleRule);
        return "product/productSaleRule/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductSaleRule productSaleRule, RedirectAttributes redirectAttributes) {
        productSaleRuleService.save(productSaleRule);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productSaleRule/update?id="+productSaleRule.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productSaleRuleService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productSaleRule?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
