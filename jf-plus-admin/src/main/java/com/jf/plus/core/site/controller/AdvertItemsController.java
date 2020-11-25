package com.jf.plus.core.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.core.site.entity.AdvertItems;
import com.jf.plus.core.site.service.AdvertItemsService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 商城广告专题商品表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/entity/advertItems")
public class AdvertItemsController extends BaseController {

    @Autowired
    private AdvertItemsService advertItemsService;

    @ModelAttribute
    public AdvertItems get(@RequestParam(required = false) String id) {
        AdvertItems entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = advertItemsService.get(id);
        }
        if (entity == null) {
            entity = new AdvertItems();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<AdvertItems> page) {
        model.addAttribute("page", page.setList(advertItemsService.findPage(page)));
        return "entity/advertItems/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(AdvertItems advertItems,Model model) {
        model.addAttribute("advertItems", advertItems);
        return "entity/advertItems/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(AdvertItems advertItems, RedirectAttributes redirectAttributes) {
        advertItemsService.save(advertItems);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/entity/advertItems/update?id="+advertItems.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(AdvertItems advertItems, Model model) {
        model.addAttribute("advertItems", advertItems);
        return "entity/advertItems/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(AdvertItems advertItems, RedirectAttributes redirectAttributes) {
        advertItemsService.save(advertItems);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/entity/advertItems/update?id="+advertItems.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        advertItemsService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/entity/advertItems?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
