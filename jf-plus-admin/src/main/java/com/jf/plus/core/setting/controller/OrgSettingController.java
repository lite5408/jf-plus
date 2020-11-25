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

import com.jf.plus.core.setting.entity.OrgSetting;
import com.jf.plus.core.setting.service.OrgSettingService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 机构配置表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/sys/orgSetting")
public class OrgSettingController extends BaseController {

    @Autowired
    private OrgSettingService orgSettingService;

    @ModelAttribute
    public OrgSetting get(@RequestParam(required = false) String id) {
        OrgSetting entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orgSettingService.get(id);
        }
        if (entity == null) {
            entity = new OrgSetting();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<OrgSetting> page) {
        model.addAttribute("page", page.setList(orgSettingService.findPage(page)));
        return "sys/orgSetting/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrgSetting orgSetting,Model model) {
        model.addAttribute("orgSetting", orgSetting);
        return "sys/orgSetting/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrgSetting orgSetting, RedirectAttributes redirectAttributes) {
        orgSettingService.save(orgSetting);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/sys/orgSetting/update?id="+orgSetting.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrgSetting orgSetting, Model model) {
        model.addAttribute("orgSetting", orgSetting);
        return "sys/orgSetting/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrgSetting orgSetting, RedirectAttributes redirectAttributes) {
        orgSettingService.save(orgSetting);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/sys/orgSetting/update?id="+orgSetting.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orgSettingService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/sys/orgSetting?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
