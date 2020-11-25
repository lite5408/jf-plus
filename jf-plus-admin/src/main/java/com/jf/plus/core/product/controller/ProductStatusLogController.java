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

import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.entity.ProductStatusLog;
import com.jf.plus.core.product.service.ProductStatusLogService;

/**
* 商品状态变更表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productStatusLog")
public class ProductStatusLogController extends BaseController {

    @Autowired
    private ProductStatusLogService productStatusLogService;

    @ModelAttribute
    public ProductStatusLog get(@RequestParam(required = false) String id) {
        ProductStatusLog entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productStatusLogService.get(id);
        }
        if (entity == null) {
            entity = new ProductStatusLog();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<ProductStatusLog> page) {
        model.addAttribute("page", page.setList(productStatusLogService.findPage(page)));
        return "product/productStatusLog/list";
    }
    
    @RequestMapping(value={"/view"})
    public String view(Model model, ProductStatusLog productStatusLog,Page<ProductStatusLog> page) {
    	page.setEntity(productStatusLog);
        model.addAttribute("page", page.setList(productStatusLogService.findPage(page)));
        return "product/productStatusLog/view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductStatusLog productStatusLog,Model model) {
        model.addAttribute("productStatusLog", productStatusLog);
        return "product/productStatusLog/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductStatusLog productStatusLog, RedirectAttributes redirectAttributes) {
        productStatusLogService.save(productStatusLog);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productStatusLog/update?id="+productStatusLog.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductStatusLog productStatusLog, Model model) {
        model.addAttribute("productStatusLog", productStatusLog);
        return "product/productStatusLog/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductStatusLog productStatusLog, RedirectAttributes redirectAttributes) {
        productStatusLogService.save(productStatusLog);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productStatusLog/update?id="+productStatusLog.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productStatusLogService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productStatusLog?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
