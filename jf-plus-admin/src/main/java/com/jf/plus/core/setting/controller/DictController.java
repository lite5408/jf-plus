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

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.DictType;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.core.setting.entity.Dict;
import com.jf.plus.core.setting.service.DictService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
* 字典表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/setting/dict")
public class DictController extends BaseController {
	
    @Autowired
    private DictService dictService;

    @ModelAttribute
    public Dict get(@RequestParam(required = false) String id) {
        Dict entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = dictService.get(id);
        }
        if (entity == null) {
            entity = new Dict();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model,Dict dict ,Page<Dict> page) {
    	dict.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.org, "org", "a"));
    	
    	page.setEntity(dict);
    	page.setOrderBy("a.sort asc");
        model.addAttribute("page", page.setList(dictService.findPage(page)));
        if(DictType.AREA.getType().equals(dict.getDict())){
        	return "setting/dict/list_"+ dict.getDict();
        }else if (DictType.SALE_AREA.getType().equals(dict.getDict())) {
			return "setting/dict/list_" + dict.getDict() +"_tree";
		}
        return "setting/dict/list";
    }

    
    
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Dict dict,Model model) {
        model.addAttribute("dict", dict);
        if(DictType.AREA.getType().equals(dict.getDict())){
        	return "setting/dict/form_"+dict.getDict();
        }else if (DictType.SALE_AREA.getType().equals(dict.getDict())) {
        	if(dict.getPid() != null){
            	Dict pDict = dictService.get(dict.getPid());
            	model.addAttribute("pDict", pDict);
            }
        	
			return "setting/dict/add_" + dict.getDict();
		}
        
        return "setting/dict/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Dict dict, RedirectAttributes redirectAttributes) {
    	if(StringUtils.isBlank(dict.getKey())){
    		dict.setKey(dict.getVal());
    	}
    	
    	dict.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	dict.setOrgName(UserUtils.getLoginUser().getOrganization().getName());
    	
        dictService.save(dict);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/setting/dict/update?id="+dict.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Dict dict, Model model) {
        model.addAttribute("dict", dict);
        if(DictType.AREA.getType().equals(dict.getDict())){
        	return "setting/dict/form_"+dict.getDict();
        }else if (DictType.SALE_AREA.getType().equals(dict.getDict())) {
        	if(dict.getPid() != null){
            	Dict pDict = dictService.get(dict.getPid());
            	model.addAttribute("pDict", pDict);
            }
			return "setting/dict/form_" + dict.getDict();
		}
        return "setting/dict/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Dict dict, RedirectAttributes redirectAttributes) {
        dictService.save(dict);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/setting/dict/update?id="+dict.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        dictService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/setting/dict?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    /**
     * 销售区域树
     * @return
     */
    @RequestMapping("/saleAreaList")
	@ResponseBody
	public Result saleAreaList(Dict entity,Page<Dict> page) {
		Result result = new Result();

		try {
			page.setPageSize(Integer.MAX_VALUE);
			page.setOrderBy(" a.pid");
			page.setEntity(entity);
			page.setList(dictService.findPage(page));
			
			result.setCode(ResultCode.SUCCESS);
			result.setObj(page.getList());
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
    
    @RequestMapping("/unique")
	@ResponseBody
	public Result unqiue(Dict dict) {
		Result result = new Result();

		try {
			int count = dictService.findUniqueCount(dict);
			
			result.setObj(count);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
}
