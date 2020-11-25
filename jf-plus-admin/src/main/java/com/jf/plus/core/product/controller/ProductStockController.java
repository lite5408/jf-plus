package com.jf.plus.core.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.vo.SkuStockVo;
import com.jf.plus.core.channel.service.ProductAPIService;
import com.jf.plus.core.product.entity.ProductStock;
import com.jf.plus.core.product.service.ProductStockService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 商品库存表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/productStock")
public class ProductStockController extends BaseController {

    @Autowired
    private ProductStockService productStockService;
    
    @Autowired
    private ProductAPIService productAPIService;

    @ModelAttribute
    public ProductStock get(@RequestParam(required = false) String id) {
        ProductStock entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = productStockService.get(id);
        }
        if (entity == null) {
            entity = new ProductStock();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, Page<ProductStock> page) {
        model.addAttribute("page", page.setList(productStockService.findPage(page)));
        return "product/productStock/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ProductStock productStock,Model model) {
        model.addAttribute("productStock", productStock);
        return "product/productStock/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ProductStock productStock, RedirectAttributes redirectAttributes) {
        productStockService.save(productStock);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/productStock/update?id="+productStock.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(ProductStock productStock, Model model) {
        model.addAttribute("productStock", productStock);
        return "product/productStock/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductStock productStock, RedirectAttributes redirectAttributes) {
        productStockService.save(productStock);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/productStock/update?id="+productStock.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        productStockService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/productStock?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping(value = "/getStock")
    @ResponseBody
    public Result getStock(SkuStockVo skuStockVo){
		try {
			List<SkuStockVo> skus = new ArrayList<>();
			skus.add(skuStockVo);
			return productAPIService.checkStock(skus );
		} catch (Exception e) {
			logger.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
    }
}
