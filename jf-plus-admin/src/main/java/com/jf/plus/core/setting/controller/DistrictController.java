package com.jf.plus.core.setting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.BaseController;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.core.setting.entity.District;
import com.jf.plus.core.setting.service.DistrictService;

/**
* 地区表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/setting/district")
public class DistrictController extends BaseController {

    @Autowired
    private DistrictService districtService;

    @ModelAttribute
    public District get(@RequestParam(required = false) String id) {
        District entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = districtService.get(id);
        }
        if (entity == null) {
            entity = new District();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<District> page) {
        model.addAttribute("page", page.setList(districtService.findPage(page)));
        return "setting/district/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(District district,Model model) {
        model.addAttribute("district", district);
        return "setting/district/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(District district, RedirectAttributes redirectAttributes) {
        districtService.save(district);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/setting/district/update?id="+district.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(District district, Model model) {
        model.addAttribute("district", district);
        return "setting/district/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(District district, RedirectAttributes redirectAttributes) {
        districtService.save(district);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/district/update?id="+district.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        districtService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/setting/district?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    
    @RequestMapping(value={"/districtList"})
    @ResponseBody
    public Result districtList(Model model, String parentId,String source,Page<District> page) {
    	Result result = new Result();
    	try{
    		page.setOrderBy(" a.channel_id asc ");
	    	page.getCondition().put("source", source);
			page.getCondition().put("parentId", parentId);
			page.setPageSize(Integer.MAX_VALUE);
			
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			result.setObj(districtService.findPage(page));
			return result;
			
    	}catch(Exception e){
    		logger.error("查询地址异常：{}",e);
    		return Result.newExceptionInstance();
    	}
        
    }
}
