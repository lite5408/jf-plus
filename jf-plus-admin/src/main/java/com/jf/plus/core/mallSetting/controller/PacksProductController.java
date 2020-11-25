package com.jf.plus.core.mallSetting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.core.mallSetting.entity.PacksProduct;
import com.jf.plus.core.mallSetting.service.PacksProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 礼包商品表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/packsProduct")
public class PacksProductController extends BaseController {

    @Autowired
    private PacksProductService packsProductService;

    @ModelAttribute
    public PacksProduct get(@RequestParam(required = false) String id) {
        PacksProduct entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = packsProductService.get(id);
        }
        if (entity == null) {
            entity = new PacksProduct();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<PacksProduct> page) {
        model.addAttribute("page", page.setList(packsProductService.findPage(page)));
        return "mallSetting/packsProduct/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(PacksProduct packsProduct,Model model) {
        model.addAttribute("packsProduct", packsProduct);
        return "mallSetting/packsProduct/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PacksProduct packsProduct, RedirectAttributes redirectAttributes) {
        packsProductService.save(packsProduct);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/packsProduct/update?id="+packsProduct.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(PacksProduct packsProduct, Model model) {
        model.addAttribute("packsProduct", packsProduct);
        return "mallSetting/packsProduct/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PacksProduct packsProduct, RedirectAttributes redirectAttributes) {
        packsProductService.save(packsProduct);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/packsProduct/update?id="+packsProduct.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        packsProductService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/packsProduct?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
