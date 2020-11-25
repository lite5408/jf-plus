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
import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.product.service.ProductSkuService;

/**
*  控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productSku")
public class ProductSkuController extends BaseController {

    @Autowired
    private ProductSkuService productSkuService;

    @ModelAttribute
    public ProductSku get(@RequestParam(required = false) String id) {
        ProductSku entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productSkuService.get(id);
        }
        if (entity == null) {
            entity = new ProductSku();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<ProductSku> page) {
        model.addAttribute("page", page.setList(productSkuService.findPage(page)));
        return "product/productSku/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductSku productSku,Model model) {
        model.addAttribute("productSku", productSku);
        return "product/productSku/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductSku productSku, RedirectAttributes redirectAttributes) {
        productSkuService.save(productSku);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productSku/update?id="+productSku.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductSku productSku, Model model) {
        model.addAttribute("productSku", productSku);
        return "product/productSku/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductSku productSku, RedirectAttributes redirectAttributes) {
        productSkuService.save(productSku);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productSku/update?id="+productSku.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productSkuService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productSku?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
