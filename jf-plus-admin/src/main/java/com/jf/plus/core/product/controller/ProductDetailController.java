package com.jf.plus.core.product.controller;

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
import com.jf.plus.core.product.entity.ProductDetail;
import com.jf.plus.core.product.service.ProductDetailService;

/**
* 商品详情表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productDetail")
public class ProductDetailController extends BaseController {

    @Autowired
    private ProductDetailService productDetailService;

    @ModelAttribute
    public ProductDetail get(@RequestParam(required = false) String id) {
        ProductDetail entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productDetailService.get(id);
        }
        if (entity == null) {
            entity = new ProductDetail();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, Page<ProductDetail> page) {
        model.addAttribute("page", page.setList(productDetailService.findPage(page)));
        return "product/productDetail/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductDetail productDetail,Model model) {
        model.addAttribute("productDetail", productDetail);
        return "product/productDetail/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductDetail productDetail, RedirectAttributes redirectAttributes) {
        productDetailService.save(productDetail);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productDetail/update?id="+productDetail.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductDetail productDetail, Model model) {
        model.addAttribute("productDetail", productDetail);
        return "product/productDetail/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductDetail productDetail, RedirectAttributes redirectAttributes) {
        productDetailService.save(productDetail);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productDetail/update?id="+productDetail.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productDetailService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productDetail?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
