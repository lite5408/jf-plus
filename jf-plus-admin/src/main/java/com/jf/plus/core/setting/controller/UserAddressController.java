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
import com.jf.plus.core.setting.entity.UserAddress;
import com.jf.plus.core.setting.service.UserAddressService;

/**
* 用户地址表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/setting/userAddress")
public class UserAddressController extends BaseController {

    @Autowired
    private UserAddressService userAddressService;

    @ModelAttribute
    public UserAddress get(@RequestParam(required = false) String id) {
        UserAddress entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = userAddressService.get(id);
        }
        if (entity == null) {
            entity = new UserAddress();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<UserAddress> page) {
        model.addAttribute("page", page.setList(userAddressService.findPage(page)));
        return "setting/userAddress/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(UserAddress userAddress,Model model) {
        model.addAttribute("userAddress", userAddress);
        return "setting/userAddress/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(UserAddress userAddress, RedirectAttributes redirectAttributes) {
        userAddressService.save(userAddress);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/setting/userAddress/update?id="+userAddress.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(UserAddress userAddress, Model model) {
        model.addAttribute("userAddress", userAddress);
        return "setting/userAddress/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(UserAddress userAddress, RedirectAttributes redirectAttributes) {
        userAddressService.save(userAddress);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/userAddress/update?id="+userAddress.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        userAddressService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/setting/userAddress?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
