package com.jf.plus.core.product.controller;

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
import com.jf.plus.core.mall.entity.MallProductCate;
import com.jf.plus.core.mall.service.MallProductCateService;
import com.jf.plus.core.product.entity.OrgGroup;
import com.jf.plus.core.product.entity.OrgGroupMerchant;
import com.jf.plus.core.product.service.OrgGroupMerchantService;
import com.jf.plus.core.product.service.OrgGroupService;
import com.jf.plus.core.setting.entity.Dict;
import com.jf.plus.core.setting.service.DictService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
* 供应商分组表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/orgGroup")
public class OrgGroupController extends BaseController {

    @Autowired
    private OrgGroupService orgGroupService;
    
    @Autowired
    private MallProductCateService mallProductCateService;
    
    @Autowired
    private DictService dictService;
    
    @Autowired
    private OrgGroupMerchantService orgGroupMerchantService;

    @ModelAttribute
    public OrgGroup get(@RequestParam(required = false) String id) {
        OrgGroup entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orgGroupService.get(id);
        }
        if (entity == null) {
            entity = new OrgGroup();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, OrgGroup orgGroup, Page<OrgGroup> page) {
    	page.setEntity(orgGroup);
        model.addAttribute("page", page.setList(orgGroupService.findPage(page)));
        return "product/orgGroup/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrgGroup orgGroup,Model model) {
    	Dict dict = new Dict();
    	dict.setDict(DictType.SALE_AREA.getType());
    	dict.setStatus("1");
		model.addAttribute("saleAreas",dictService.findList(dict));
		
		MallProductCate mallProductCate = new MallProductCate();
		mallProductCate.setCatPid(0L);
		mallProductCate.setStatus("1");
		mallProductCate.setOrderBy("a.sort asc,a.id asc");
		model.addAttribute("cateIds",mallProductCateService.findList(mallProductCate ));
    	
        model.addAttribute("orgGroup", orgGroup);
        return "product/orgGroup/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrgGroup orgGroup, RedirectAttributes redirectAttributes) {
        orgGroupService.save(orgGroup);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/orgGroup/update?id="+orgGroup.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrgGroup orgGroup, Model model) {
    	Dict dict = new Dict();
    	dict.setDict(DictType.SALE_AREA.getType());
    	dict.setStatus("1");
		model.addAttribute("saleAreas",dictService.findList(dict));
		
		MallProductCate mallProductCate = new MallProductCate();
		mallProductCate.setCatPid(0L);
		mallProductCate.setStatus("1");
		mallProductCate.setOrderBy("a.sort asc,a.id asc");
		model.addAttribute("cateIds",mallProductCateService.findList(mallProductCate ));
		
        model.addAttribute("orgGroup", orgGroup);
        return "product/orgGroup/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrgGroup orgGroup, RedirectAttributes redirectAttributes) {
        orgGroupService.save(orgGroup);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/orgGroup/update?id="+orgGroup.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orgGroupService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/orgGroup?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
	/**
	 * 选择代理商列表
	 */
	@RequestMapping("/merchantList")
	public String methodName(Model model, Page<OrgGroupMerchant> page,String groupId,String isRelated,String orgType,String merchantName) {
		page.getCondition().put("groupId", groupId);
		page.getCondition().put("isRelated", isRelated);
		page.getCondition().put("orgType", orgType);
		page.getCondition().put("merchantName", merchantName);
		model.addAttribute("page", page.setList(orgGroupMerchantService.findPage(page)));
		
		return "select/merchant/select";
	}
	
	/**
	     *选择代理商
	     */
@RequestMapping("/merchantPick")
@ResponseBody
public Result merchantPick(OrgGroupMerchant merchant){
	Result result = new Result();
	try{
		if(merchant.getStatus().equals("1")){
			orgGroupMerchantService.save(merchant);
		}else if (merchant.getStatus().equals("0")) {
			if(merchant.getGroupId() == null || merchant.getMerchantId() == null){
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}
			orgGroupMerchantService.deleteEntity(merchant);
		}
		
		result.setCode(ResultCode.SUCCESS);
		return result;
	}catch(Exception e){
		logger.error("系统异常:{}",e);
		return Result.newExceptionInstance();
	}
}
}
