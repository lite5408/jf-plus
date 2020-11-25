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
import com.jf.plus.core.mallSetting.entity.UserExtInfo;
import com.jf.plus.core.mallSetting.service.UserExtInfoService;

/**
* 用户扩展信息表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/userExtInfo")
public class UserExtInfoController extends BaseController {

    @Autowired
    private UserExtInfoService userExtInfoService;

    @ModelAttribute
    public UserExtInfo get(@RequestParam(required = false) String id) {
        UserExtInfo entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = userExtInfoService.get(id);
        }
        if (entity == null) {
            entity = new UserExtInfo();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<UserExtInfo> page) {
        model.addAttribute("page", page.setList(userExtInfoService.findPage(page)));
        return "mallSetting/userExtInfo/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(UserExtInfo userExtInfo,Model model) {
        model.addAttribute("userExtInfo", userExtInfo);
        return "mallSetting/userExtInfo/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(UserExtInfo userExtInfo, RedirectAttributes redirectAttributes) {
        userExtInfoService.save(userExtInfo);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/userExtInfo/update?id="+userExtInfo.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(UserExtInfo userExtInfo, Model model) {
        model.addAttribute("userExtInfo", userExtInfo);
        return "mallSetting/userExtInfo/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(UserExtInfo userExtInfo, RedirectAttributes redirectAttributes) {
        userExtInfoService.save(userExtInfo);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/userExtInfo/update?id="+userExtInfo.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        userExtInfoService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/userExtInfo?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
