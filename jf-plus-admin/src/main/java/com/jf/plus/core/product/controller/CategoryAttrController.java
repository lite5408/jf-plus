package com.jf.plus.core.product.controller;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.core.mall.entity.MallProductCate;
import com.jf.plus.core.mall.service.MallProductCateService;
import com.jf.plus.core.product.entity.CategoryAttr;
import com.jf.plus.core.product.entity.CategoryAttrInfo;
import com.jf.plus.core.product.service.CategoryAttrInfoService;
import com.jf.plus.core.product.service.CategoryAttrService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 分类属性定义表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/categoryAttr")
public class CategoryAttrController extends BaseController {

    @Autowired
    private CategoryAttrService categoryAttrService;

    @Autowired
    private MallProductCateService mallProductCateService;
    
    @Autowired
    private CategoryAttrInfoService categoryAttrInfoService;
    
    @ModelAttribute
    public CategoryAttr get(@RequestParam(required = false) String id) {
        CategoryAttr entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = categoryAttrService.get(id);
        }
        if (entity == null) {
            entity = new CategoryAttr();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, CategoryAttr entity,Page<CategoryAttr> page) {
    	page.setEntity(entity);
        model.addAttribute("page", page.setList(categoryAttrService.findPage(page)));
        return "product/categoryAttr/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(CategoryAttr categoryAttr,Model model) {
    	
    	MallProductCate entity = new MallProductCate();
    	entity.setStatus("1");
    	model.addAttribute("categoryList",mallProductCateService.findList(entity));
    	
        model.addAttribute("categoryAttr", categoryAttr);
        return "product/categoryAttr/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(CategoryAttr categoryAttr, RedirectAttributes redirectAttributes) {
        categoryAttrService.save(categoryAttr);
        if(CollectionUtils.isNotEmpty(categoryAttr.getCategoryAttrInfos())){
        	for(CategoryAttrInfo attrInfo : categoryAttr.getCategoryAttrInfos()){
        		if(attrInfo == null || StringUtils.isBlank(attrInfo.getAttrInfo()))
        			continue;
        		attrInfo.setStatus("1");
        		attrInfo.setAttrId(Long.valueOf(categoryAttr.getId()));
        		categoryAttrInfoService.save(attrInfo);
        	}
        }
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/categoryAttr/update?id="+categoryAttr.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(CategoryAttr categoryAttr, Model model) {
    	
    	MallProductCate entity = new MallProductCate();
    	entity.setStatus("1");
    	model.addAttribute("categoryList",mallProductCateService.findList(entity));
    	
    	CategoryAttrInfo categoryAttrInfo = new CategoryAttrInfo();
        categoryAttrInfo.setAttrId(Long.valueOf(categoryAttr.getId()));
        categoryAttr.setCategoryAttrInfos(categoryAttrInfoService.findList(categoryAttrInfo));
    	
    	
    	
        model.addAttribute("categoryAttr", categoryAttr);
        return "product/categoryAttr/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(CategoryAttr categoryAttr, RedirectAttributes redirectAttributes) {
        categoryAttrService.save(categoryAttr);
        
        CategoryAttrInfo dbAttrInfo = new CategoryAttrInfo();
    	dbAttrInfo.setAttrId(Long.valueOf(categoryAttr.getId()));
		categoryAttrInfoService.deleteEntity(dbAttrInfo );
    	
    	if(CollectionUtils.isNotEmpty(categoryAttr.getCategoryAttrInfos())){
        	for(CategoryAttrInfo attrInfo : categoryAttr.getCategoryAttrInfos()){
        		if(attrInfo == null || StringUtils.isBlank(attrInfo.getAttrInfo()))
        			continue;
        		attrInfo.setId(null);
        		attrInfo.setStatus("1");
        		attrInfo.setAttrId(Long.valueOf(categoryAttr.getId()));
        		categoryAttrInfoService.save(attrInfo);
        	}
        }
    	
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/categoryAttr/update?id="+categoryAttr.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        categoryAttrService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/categoryAttr?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
