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
import com.jf.plus.common.core.enums.OrgType;
import com.jf.plus.core.product.entity.MallProductBrand;
import com.jf.plus.core.product.service.MallProductBrandService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.OrganizationService;

/**
* 机构品牌表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/product/mallProductBrand")
public class MallProductBrandController extends BaseController {

    @Autowired
    private MallProductBrandService mallProductBrandService;
    
    @Autowired
    private OrganizationService organizationService;

    @ModelAttribute
    public MallProductBrand get(@RequestParam(required = false) String id) {
        MallProductBrand entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = mallProductBrandService.get(id);
        }
        if (entity == null) {
            entity = new MallProductBrand();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, MallProductBrand entity,Page<MallProductBrand> page) {
    	page.setEntity(entity);
        model.addAttribute("page", page.setList(mallProductBrandService.findPage(page)));
        return "product/mallProductBrand/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MallProductBrand mallProductBrand,Model model) {
    	User user = UserUtils.getLoginUser();
		Organization organization = new Organization();
		organization.setType(OrgType.CHD_COMP.getType());
		organization.setUser(user);
		//机构列表
		model.addAttribute("organizationList", organizationService.findList(organization));
		
        model.addAttribute("mallProductBrand", mallProductBrand);
        return "product/mallProductBrand/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MallProductBrand mallProductBrand, RedirectAttributes redirectAttributes) {
        mallProductBrandService.save(mallProductBrand);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/product/mallProductBrand/update?id="+mallProductBrand.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MallProductBrand mallProductBrand, Model model) {
    	User user = UserUtils.getLoginUser();
		Organization organization = new Organization();
		organization.setType(OrgType.CHD_COMP.getType());
		organization.setUser(user);
		//机构列表
		model.addAttribute("organizationList", organizationService.findList(organization));
		
        model.addAttribute("mallProductBrand", mallProductBrand);
        return "product/mallProductBrand/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MallProductBrand mallProductBrand, RedirectAttributes redirectAttributes) {
        mallProductBrandService.save(mallProductBrand);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/product/mallProductBrand/update?id="+mallProductBrand.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        mallProductBrandService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/product/mallProductBrand?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping("/unique")
	@ResponseBody
	public Result unqiue(MallProductBrand mallProductBrand) {
		Result result = new Result();

		try {
			int count = mallProductBrandService.findUniqueCount(mallProductBrand);
			
			result.setObj(count);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
}
