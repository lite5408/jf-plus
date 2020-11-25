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
import com.jf.plus.core.product.entity.ProductPriceRecord;
import com.jf.plus.core.product.service.ProductPriceRecordService;

/**
*  控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productPriceRecord")
public class ProductPriceRecordController extends BaseController {

    @Autowired
    private ProductPriceRecordService productPriceRecordService;

    @ModelAttribute
    public ProductPriceRecord get(@RequestParam(required = false) String id) {
        ProductPriceRecord entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productPriceRecordService.get(id);
        }
        if (entity == null) {
            entity = new ProductPriceRecord();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<ProductPriceRecord> page) {
        model.addAttribute("page", page.setList(productPriceRecordService.findPage(page)));
        return "product/productPriceRecord/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductPriceRecord productPriceRecord,Model model) {
        model.addAttribute("productPriceRecord", productPriceRecord);
        return "product/productPriceRecord/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductPriceRecord productPriceRecord, RedirectAttributes redirectAttributes) {
        productPriceRecordService.save(productPriceRecord);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productPriceRecord/update?id="+productPriceRecord.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductPriceRecord productPriceRecord, Model model) {
        model.addAttribute("productPriceRecord", productPriceRecord);
        return "product/productPriceRecord/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductPriceRecord productPriceRecord, RedirectAttributes redirectAttributes) {
        productPriceRecordService.save(productPriceRecord);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productPriceRecord/update?id="+productPriceRecord.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productPriceRecordService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productPriceRecord?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
