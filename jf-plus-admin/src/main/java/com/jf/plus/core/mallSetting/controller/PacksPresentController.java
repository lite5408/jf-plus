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

import com.jf.plus.core.mallSetting.entity.PacksPresent;
import com.jf.plus.core.mallSetting.service.PacksPresentService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 礼包赠送记录 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/packsPresent")
public class PacksPresentController extends BaseController {

    @Autowired
    private PacksPresentService packsPresentService;

    @ModelAttribute
    public PacksPresent get(@RequestParam(required = false) String id) {
        PacksPresent entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = packsPresentService.get(id);
        }
        if (entity == null) {
            entity = new PacksPresent();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<PacksPresent> page) {
        model.addAttribute("page", page.setList(packsPresentService.findPage(page)));
        return "mallSetting/packsPresent/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(PacksPresent packsPresent,Model model) {
        model.addAttribute("packsPresent", packsPresent);
        return "mallSetting/packsPresent/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PacksPresent packsPresent, RedirectAttributes redirectAttributes) {
        packsPresentService.save(packsPresent);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/packsPresent/update?id="+packsPresent.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(PacksPresent packsPresent, Model model) {
        model.addAttribute("packsPresent", packsPresent);
        return "mallSetting/packsPresent/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PacksPresent packsPresent, RedirectAttributes redirectAttributes) {
        packsPresentService.save(packsPresent);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/packsPresent/update?id="+packsPresent.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        packsPresentService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/packsPresent?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
