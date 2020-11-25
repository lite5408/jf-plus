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
import com.jf.plus.core.product.entity.ProductSupplyPriceLog;
import com.jf.plus.core.product.service.ProductSupplyPriceLogService;

/**
*  控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productSupplyPriceLog")
public class ProductSupplyPriceLogController extends BaseController {

    @Autowired
    private ProductSupplyPriceLogService productSupplyPriceLogService;

    @ModelAttribute
    public ProductSupplyPriceLog get(@RequestParam(required = false) String id) {
        ProductSupplyPriceLog entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productSupplyPriceLogService.get(id);
        }
        if (entity == null) {
            entity = new ProductSupplyPriceLog();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<ProductSupplyPriceLog> page) {
        model.addAttribute("page", page.setList(productSupplyPriceLogService.findPage(page)));
        return "product/productSupplyPriceLog/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductSupplyPriceLog productSupplyPriceLog,Model model) {
        model.addAttribute("productSupplyPriceLog", productSupplyPriceLog);
        return "product/productSupplyPriceLog/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductSupplyPriceLog productSupplyPriceLog, RedirectAttributes redirectAttributes) {
        productSupplyPriceLogService.save(productSupplyPriceLog);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productSupplyPriceLog/update?id="+productSupplyPriceLog.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductSupplyPriceLog productSupplyPriceLog, Model model) {
        model.addAttribute("productSupplyPriceLog", productSupplyPriceLog);
        return "product/productSupplyPriceLog/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductSupplyPriceLog productSupplyPriceLog, RedirectAttributes redirectAttributes) {
        productSupplyPriceLogService.save(productSupplyPriceLog);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productSupplyPriceLog/update?id="+productSupplyPriceLog.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productSupplyPriceLogService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productSupplyPriceLog?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
