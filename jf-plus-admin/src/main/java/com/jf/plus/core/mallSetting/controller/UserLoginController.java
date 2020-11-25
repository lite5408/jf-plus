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
import com.jf.plus.core.mallSetting.entity.UserLogin;
import com.jf.plus.core.mallSetting.service.UserLoginService;

/**
* 用户登录日志 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/userLogin")
public class UserLoginController extends BaseController {

    @Autowired
    private UserLoginService userLoginService;

    @ModelAttribute
    public UserLogin get(@RequestParam(required = false) String id) {
        UserLogin entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = userLoginService.get(id);
        }
        if (entity == null) {
            entity = new UserLogin();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<UserLogin> page) {
        model.addAttribute("page", page.setList(userLoginService.findPage(page)));
        return "mallSetting/userLogin/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(UserLogin userLogin,Model model) {
        model.addAttribute("userLogin", userLogin);
        return "mallSetting/userLogin/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(UserLogin userLogin, RedirectAttributes redirectAttributes) {
        userLoginService.save(userLogin);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/userLogin/update?id="+userLogin.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(UserLogin userLogin, Model model) {
        model.addAttribute("userLogin", userLogin);
        return "mallSetting/userLogin/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(UserLogin userLogin, RedirectAttributes redirectAttributes) {
        userLoginService.save(userLogin);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/userLogin/update?id="+userLogin.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        userLoginService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/userLogin?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
