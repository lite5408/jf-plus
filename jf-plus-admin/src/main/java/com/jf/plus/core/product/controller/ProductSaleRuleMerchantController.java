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
import com.jf.plus.core.product.entity.ProductSaleRuleMerchant;
import com.jf.plus.core.product.service.ProductSaleRuleMerchantService;

/**
* 销售规则分组机构表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productSaleRuleMerchant")
public class ProductSaleRuleMerchantController extends BaseController {

    @Autowired
    private ProductSaleRuleMerchantService productSaleRuleMerchantService;

    @ModelAttribute
    public ProductSaleRuleMerchant get(@RequestParam(required = false) String id) {
        ProductSaleRuleMerchant entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productSaleRuleMerchantService.get(id);
        }
        if (entity == null) {
            entity = new ProductSaleRuleMerchant();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<ProductSaleRuleMerchant> page) {
        model.addAttribute("page", page.setList(productSaleRuleMerchantService.findPage(page)));
        return "product/productSaleRuleMerchant/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductSaleRuleMerchant productSaleRuleMerchant,Model model) {
        model.addAttribute("productSaleRuleMerchant", productSaleRuleMerchant);
        return "product/productSaleRuleMerchant/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductSaleRuleMerchant productSaleRuleMerchant, RedirectAttributes redirectAttributes) {
        productSaleRuleMerchantService.save(productSaleRuleMerchant);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productSaleRuleMerchant/update?id="+productSaleRuleMerchant.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductSaleRuleMerchant productSaleRuleMerchant, Model model) {
        model.addAttribute("productSaleRuleMerchant", productSaleRuleMerchant);
        return "product/productSaleRuleMerchant/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductSaleRuleMerchant productSaleRuleMerchant, RedirectAttributes redirectAttributes) {
        productSaleRuleMerchantService.save(productSaleRuleMerchant);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productSaleRuleMerchant/update?id="+productSaleRuleMerchant.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productSaleRuleMerchantService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productSaleRuleMerchant?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
