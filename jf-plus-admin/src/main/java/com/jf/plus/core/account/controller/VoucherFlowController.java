package com.jf.plus.core.account.controller;

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
import com.jf.plus.core.account.entity.VoucherFlow;
import com.jf.plus.core.account.service.VoucherFlowService;

/**
* 电子券卡号交易记录表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/account/voucherFlow")
public class VoucherFlowController extends BaseController {

    @Autowired
    private VoucherFlowService voucherFlowService;

    @ModelAttribute
    public VoucherFlow get(@RequestParam(required = false) String id) {
        VoucherFlow entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = voucherFlowService.get(id);
        }
        if (entity == null) {
            entity = new VoucherFlow();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<VoucherFlow> page) {
        model.addAttribute("page", page.setList(voucherFlowService.findPage(page)));
        return "account/voucherFlow/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(VoucherFlow voucherFlow,Model model) {
        model.addAttribute("voucherFlow", voucherFlow);
        return "account/voucherFlow/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(VoucherFlow voucherFlow, RedirectAttributes redirectAttributes) {
        voucherFlowService.save(voucherFlow);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/account/voucherFlow/update?id="+voucherFlow.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(VoucherFlow voucherFlow, Model model) {
        model.addAttribute("voucherFlow", voucherFlow);
        return "account/voucherFlow/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(VoucherFlow voucherFlow, RedirectAttributes redirectAttributes) {
        voucherFlowService.save(voucherFlow);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/account/voucherFlow/update?id="+voucherFlow.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        voucherFlowService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/account/voucherFlow?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
