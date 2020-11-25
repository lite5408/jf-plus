package com.jf.plus.core.setting.controller;

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
import com.jf.plus.core.setting.entity.Appcode;
import com.jf.plus.core.setting.service.AppcodeService;

/**
* 用户token表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/setting/appcode")
public class AppcodeController extends BaseController {

    @Autowired
    private AppcodeService appcodeService;

    @ModelAttribute
    public Appcode get(@RequestParam(required = false) String id) {
        Appcode entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = appcodeService.get(id);
        }
        if (entity == null) {
            entity = new Appcode();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, Page<Appcode> page) {
        model.addAttribute("page", page.setList(appcodeService.findPage(page)));
        return "setting/appcode/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Appcode appcode,Model model) {
        model.addAttribute("appcode", appcode);
        return "setting/appcode/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Appcode appcode, RedirectAttributes redirectAttributes) {
        appcodeService.save(appcode);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/setting/appcode/update?id="+appcode.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Appcode appcode, Model model) {
        model.addAttribute("appcode", appcode);
        return "setting/appcode/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Appcode appcode, RedirectAttributes redirectAttributes) {
        appcodeService.save(appcode);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/appcode/update?id="+appcode.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        appcodeService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/setting/appcode?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
