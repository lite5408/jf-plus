package com.jf.plus.core.setting.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.core.setting.entity.AuditSettings;
import com.jf.plus.core.setting.service.AuditSettingsService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;

/**
* 审批设置 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/setting/auditSettings")
public class AuditSettingsController extends BaseController {

    @Autowired
    private AuditSettingsService auditSettingsService;

    @ModelAttribute
    public AuditSettings get(@RequestParam(required = false) String id) {
        AuditSettings entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = auditSettingsService.get(id);
        }
        if (entity == null) {
            entity = new AuditSettings();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<AuditSettings> page) {
        model.addAttribute("page", page.setList(auditSettingsService.findPage(page)));
        return "setting/auditSettings/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(AuditSettings auditSettings,Model model) {
        model.addAttribute("auditSettings", auditSettings);
        return "setting/auditSettings/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(AuditSettings auditSettings, RedirectAttributes redirectAttributes) {
        auditSettingsService.save(auditSettings);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/setting/auditSettings/update?id="+auditSettings.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(AuditSettings auditSettings, Model model) {
        model.addAttribute("auditSettings", auditSettings);
        return "setting/auditSettings/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(AuditSettings auditSettings, RedirectAttributes redirectAttributes) {
        auditSettingsService.save(auditSettings);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/auditSettings/update?id="+auditSettings.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        auditSettingsService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/setting/auditSettings?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping(value = "/formSetting",method = RequestMethod.GET)
    public String form(AuditSettings auditSettings,Model model){
    	auditSettings.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	auditSettings = auditSettingsService.getEntity(auditSettings);
    	
    	model.addAttribute("auditSettings", auditSettings);
    	return "/setting/auditSettings/form_setting";
    }
    
    @RequestMapping(value = "/formSetting", method = RequestMethod.POST)
    public String form(AuditSettings auditSettings, RedirectAttributes redirectAttributes) {
    	if(auditSettings.getAuditEndTime() != null){
    		auditSettings.setAuditEndTime(DateUtils.parseDate(DateUtils.formatDate(auditSettings.getAuditEndTime()) + " 23:59:59"));
    	}
        if(StringUtils.isBlank(auditSettings.getId()))
        	auditSettingsService.save(auditSettings);
        else
        	auditSettingsService.updateFull(auditSettings);
        
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/auditSettings/formSetting?id="+auditSettings.getId();
    }
}
