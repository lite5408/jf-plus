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

import com.jf.plus.core.mallSetting.entity.MallSiteRuleRecord;
import com.jf.plus.core.mallSetting.service.MallSiteRuleRecordService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
*  控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/mallSiteRuleRecord")
public class MallSiteRuleRecordController extends BaseController {

    @Autowired
    private MallSiteRuleRecordService mallSiteRuleRecordService;

    @ModelAttribute
    public MallSiteRuleRecord get(@RequestParam(required = false) String id) {
        MallSiteRuleRecord entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = mallSiteRuleRecordService.get(id);
        }
        if (entity == null) {
            entity = new MallSiteRuleRecord();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<MallSiteRuleRecord> page) {
        model.addAttribute("page", page.setList(mallSiteRuleRecordService.findPage(page)));
        return "product/mallSiteRuleRecord/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MallSiteRuleRecord mallSiteRuleRecord,Model model) {
        model.addAttribute("mallSiteRuleRecord", mallSiteRuleRecord);
        return "product/mallSiteRuleRecord/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MallSiteRuleRecord mallSiteRuleRecord, RedirectAttributes redirectAttributes) {
        mallSiteRuleRecordService.save(mallSiteRuleRecord);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/mallSiteRuleRecord/update?id="+mallSiteRuleRecord.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MallSiteRuleRecord mallSiteRuleRecord, Model model) {
        model.addAttribute("mallSiteRuleRecord", mallSiteRuleRecord);
        return "product/mallSiteRuleRecord/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MallSiteRuleRecord mallSiteRuleRecord, RedirectAttributes redirectAttributes) {
        mallSiteRuleRecordService.save(mallSiteRuleRecord);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/mallSiteRuleRecord/update?id="+mallSiteRuleRecord.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        mallSiteRuleRecordService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/mallSiteRuleRecord?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
