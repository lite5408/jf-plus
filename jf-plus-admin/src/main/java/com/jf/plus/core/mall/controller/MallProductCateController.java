package com.jf.plus.core.mall.controller;

import java.util.List;

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
import com.jf.plus.core.mall.entity.MallProductCate;
import com.jf.plus.core.mall.service.MallProductCateService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
* 商城商品分类 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mall/mallProductCate")
public class MallProductCateController extends BaseController {

    @Autowired
    private MallProductCateService mallProductCateService;

    @ModelAttribute
    public MallProductCate get(@RequestParam(required = false) String id) {
        MallProductCate entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = mallProductCateService.get(id);
        }
        if (entity == null) {
            entity = new MallProductCate();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, MallProductCate mallProductCate, Page<MallProductCate> page) {
    		
    	MallProductCate mallProductCate1 = new MallProductCate();
    	mallProductCate1.setUser(UserUtils.getLoginUser());
    	mallProductCate1.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "a", "a"));
    	mallProductCate1.setOrderBy("a.id asc");
		List<MallProductCate> mallProductCateList = mallProductCateService.findList(mallProductCate1);
		model.addAttribute("mallProductCateList", mallProductCateList);
		//初始化加载第一个
		if(mallProductCate.getCatPid() == null && mallProductCateList.size()>0){
			mallProductCate.setCatPid(Long.valueOf(mallProductCateList.get(0).getId()));
		}
		
        page.setEntity(mallProductCate);
        page.setOrderBy("a.sort asc,a.id desc");
        model.addAttribute("page", page.setList(mallProductCateService.findPage(page)));
        return "mall/mallProductCate/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MallProductCate mallProductCate,Model model) {
        model.addAttribute("mallProductCate", mallProductCate);
        
        if(mallProductCate.getCatPid() != null){
        	model.addAttribute("parentMallProductCate", mallProductCateService.get(mallProductCate.getCatPid()+""));
        }
        
        return "mall/mallProductCate/add";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MallProductCate mallProductCate, RedirectAttributes redirectAttributes) {
        mallProductCateService.save(mallProductCate);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mall/mallProductCate/update?id="+mallProductCate.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MallProductCate mallProductCate, Model model) {
        model.addAttribute("mallProductCate", mallProductCate);
        
        if(mallProductCate.getCatPid() != null){
        	model.addAttribute("parentMallProductCate", mallProductCateService.get(mallProductCate.getCatPid()+""));
        }
        
        return "mall/mallProductCate/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MallProductCate mallProductCate, RedirectAttributes redirectAttributes) {
        mallProductCateService.save(mallProductCate);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mall/mallProductCate/update?id="+mallProductCate.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        mallProductCateService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mall/mallProductCate?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping(value = "/select")
    public String select(Model model){
    	MallProductCate mallProductCate1 = new MallProductCate();
    	mallProductCate1.setUser(UserUtils.getLoginUser());
    	mallProductCate1.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "a", "a"));
    	mallProductCate1.setOrderBy("a.sort asc,a.id desc");
		List<MallProductCate> mallProductCateList = mallProductCateService.findList(mallProductCate1);
		model.addAttribute("mallProductCateList", mallProductCateList);
    	return "select/cate/cateSelect";
    }
    
    @RequestMapping("/unique")
   	@ResponseBody
   	public Result unqiue(MallProductCate mallProductCate) {
   		Result result = new Result();

   		try {
   			int count = mallProductCateService.findUniqueCount(mallProductCate);
   			
   			result.setObj(count);
   			result.setCode(ResultCode.SUCCESS);
   			return result;
   		} catch (Exception e) {
   			logger.error("系统异常：{}", e);
   			return Result.newExceptionInstance();
   		}
   	}
}
