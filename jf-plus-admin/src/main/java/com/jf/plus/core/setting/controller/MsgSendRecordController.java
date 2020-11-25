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
import com.jf.plus.core.setting.entity.MsgSendRecord;
import com.jf.plus.core.setting.service.MsgSendRecordService;

/**
* 消息发送记录表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/setting/msgSendRecord")
public class MsgSendRecordController extends BaseController {

    @Autowired
    private MsgSendRecordService msgSendService;

    @ModelAttribute
    public MsgSendRecord get(@RequestParam(required = false) String id) {
        MsgSendRecord entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = msgSendService.get(id);
        }
        if (entity == null) {
            entity = new MsgSendRecord();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<MsgSendRecord> page) {
        model.addAttribute("page", page.setList(msgSendService.findPage(page)));
        return "setting/msgSendRecord/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MsgSendRecord msgSendRecord,Model model) {
        model.addAttribute("msgSendRecord", msgSendRecord);
        return "setting/msgSendRecord/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MsgSendRecord msgSendRecord, RedirectAttributes redirectAttributes) {
        msgSendService.save(msgSendRecord);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/setting/msgSendRecord/update?id="+msgSendRecord.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MsgSendRecord msgSendRecord, Model model) {
        model.addAttribute("msgSendRecord", msgSendRecord);
        return "setting/msgSendRecord/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MsgSendRecord msgSend, RedirectAttributes redirectAttributes) {
        msgSendService.save(msgSend);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/msgSend/update?id="+msgSend.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        msgSendService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/setting/msgSend?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
