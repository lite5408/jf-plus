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
import com.jf.plus.core.product.entity.SpecLib;
import com.jf.plus.core.product.service.SpecLibService;

/**
* 机构规格库 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/specLib")
public class SpecLibController extends BaseController {

    @Autowired
    private SpecLibService specLibService;

    @ModelAttribute
    public SpecLib get(@RequestParam(required = false) String id) {
        SpecLib entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = specLibService.get(id);
        }
        if (entity == null) {
            entity = new SpecLib();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, SpecLib entity,Page<SpecLib> page) {
    	entity.setStatus("1");
    	page.setEntity(entity);
    	page.setOrderBy(" a.spec_type ,a.sort ");//规格排序
        model.addAttribute("page", page.setList(specLibService.findPage(page)));
        return "product/specLib/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(SpecLib specLib,Model model) {
        model.addAttribute("specLib", specLib);
        return "product/specLib/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SpecLib specLib, RedirectAttributes redirectAttributes) {
        specLibService.save(specLib);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/specLib/update?id="+specLib.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(SpecLib specLib, Model model) {
        model.addAttribute("specLib", specLib);
        return "product/specLib/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SpecLib specLib, RedirectAttributes redirectAttributes) {
        specLibService.save(specLib);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/specLib/update?id="+specLib.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        specLibService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/specLib?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
