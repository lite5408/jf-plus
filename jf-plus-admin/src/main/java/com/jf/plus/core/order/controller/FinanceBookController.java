package com.jf.plus.core.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.core.order.entity.FinanceBook;
import com.jf.plus.core.order.service.FinanceBookService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 财务对账簿表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/financeBook")
public class FinanceBookController extends BaseController {

    @Autowired
    private FinanceBookService financeBookService;

    @ModelAttribute
    public FinanceBook get(@RequestParam(required = false) String id) {
        FinanceBook entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = financeBookService.get(id);
        }
        if (entity == null) {
            entity = new FinanceBook();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, FinanceBook financeBook,Page<FinanceBook> page) {
    	page.setEntity(financeBook);
        model.addAttribute("page", page.setList(financeBookService.findPage(page)));
        return "order/financeBook/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(FinanceBook financeBook,Model model) {
        model.addAttribute("financeBook", financeBook);
        return "order/financeBook/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(FinanceBook financeBook, RedirectAttributes redirectAttributes) {
        financeBookService.save(financeBook);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/financeBook/update?id="+financeBook.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(FinanceBook financeBook, Model model) {
        model.addAttribute("financeBook", financeBook);
        return "order/financeBook/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(FinanceBook financeBook, RedirectAttributes redirectAttributes) {
        financeBookService.save(financeBook);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/financeBook/update?id="+financeBook.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        financeBookService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/financeBook?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
