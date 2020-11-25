package com.jf.plus.core.setting.controller;

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

import com.jf.plus.common.core.enums.MsgType;
import com.jf.plus.core.setting.entity.MsgTemplate;
import com.jf.plus.core.setting.service.MsgTemplateService;

/**
* 消息模板表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/setting/msgTemplate")
public class MsgTemplateController extends BaseController {

    @Autowired
    private MsgTemplateService msgTemplateService;

    @ModelAttribute
    public MsgTemplate get(@RequestParam(required = false) String id) {
        MsgTemplate entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = msgTemplateService.get(id);
        }
        if (entity == null) {
            entity = new MsgTemplate();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<MsgTemplate> page) {
        model.addAttribute("page", page.setList(msgTemplateService.findPage(page)));
        return "setting/msgTemplate/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MsgTemplate msgTemplate,Model model) {
    	model.addAttribute("msgType", MsgType.values());
        model.addAttribute("msgTemplate", msgTemplate);
        return "setting/msgTemplate/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MsgTemplate msgTemplate, RedirectAttributes redirectAttributes) {
    	
        msgTemplateService.save(msgTemplate);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/setting/msgTemplate/update?id="+msgTemplate.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MsgTemplate msgTemplate, Model model) {
    	model.addAttribute("msgType", MsgType.values());
    	
        model.addAttribute("msgTemplate", msgTemplate);
        return "setting/msgTemplate/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MsgTemplate msgTemplate, RedirectAttributes redirectAttributes) {
    	
        msgTemplateService.save(msgTemplate);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/msgTemplate/update?id="+msgTemplate.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        msgTemplateService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/setting/msgTemplate?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
