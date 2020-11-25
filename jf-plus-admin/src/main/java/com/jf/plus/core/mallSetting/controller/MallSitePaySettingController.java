package com.jf.plus.core.mallSetting.controller;

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
import com.jf.plus.core.mallSetting.entity.MallSitePaySetting;
import com.jf.plus.core.mallSetting.service.MallSitePaySettingService;

/**
* 站点支付配置表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/mallSitePaySetting")
public class MallSitePaySettingController extends BaseController {

    @Autowired
    private MallSitePaySettingService mallSitePaySettingService;

    @ModelAttribute
    public MallSitePaySetting get(@RequestParam(required = false) String id) {
        MallSitePaySetting entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = mallSitePaySettingService.get(id);
        }
        if (entity == null) {
            entity = new MallSitePaySetting();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<MallSitePaySetting> page) {
        model.addAttribute("page", page.setList(mallSitePaySettingService.findPage(page)));
        return "mallSetting/mallSitePaySetting/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MallSitePaySetting mallSitePaySetting,Model model) {
        model.addAttribute("mallSitePaySetting", mallSitePaySetting);
        return "mallSetting/mallSitePaySetting/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MallSitePaySetting mallSitePaySetting, RedirectAttributes redirectAttributes) {
        mallSitePaySettingService.save(mallSitePaySetting);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/mallSitePaySetting/update?id="+mallSitePaySetting.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MallSitePaySetting mallSitePaySetting, Model model) {
        model.addAttribute("mallSitePaySetting", mallSitePaySetting);
        return "mallSetting/mallSitePaySetting/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MallSitePaySetting mallSitePaySetting, RedirectAttributes redirectAttributes) {
        mallSitePaySettingService.save(mallSitePaySetting);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/mallSitePaySetting/update?id="+mallSitePaySetting.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        mallSitePaySettingService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/mallSitePaySetting?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
