package com.jf.plus.core.mall.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.enums.DictType;
import com.jf.plus.core.mall.entity.MallSupplyer;
import com.jf.plus.core.mall.service.MallSupplyerService;
import com.jf.plus.core.product.entity.MallProductBrand;
import com.jf.plus.core.product.service.MallProductBrandService;
import com.jf.plus.core.setting.entity.Dict;
import com.jf.plus.core.setting.service.DictService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
* 供应商表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mall/mallSupplyer")
public class MallSupplyerController extends BaseController {

    @Autowired
    private MallSupplyerService mallSupplyerService;
    
    @Autowired
    private DictService dictService;
    
    @Autowired
    private MallProductBrandService mallProductBrandService;

    @ModelAttribute
    public MallSupplyer get(@RequestParam(required = false) String id) {
        MallSupplyer entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = mallSupplyerService.get(id);
        }
        if (entity == null) {
            entity = new MallSupplyer();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, MallSupplyer mallSupplyer , Page<MallSupplyer> page) {
    	page.setEntity(mallSupplyer);
    	page.getEntity().setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.org, "org", "a"));
    	
        model.addAttribute("page", page.setList(mallSupplyerService.findPage(page)));
        return "mall/mallSupplyer/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(MallSupplyer mallSupplyer,Model model) {
    	Dict dict = new Dict();
    	dict.setDict(DictType.AREA.getType());
    	dict.setStatus("1");
		model.addAttribute("area",dictService.findList(dict));
		
		MallProductBrand mallProductBrand = new MallProductBrand();
		mallProductBrand.setStatus("1");
		mallProductBrand.setOrderBy("a.sort asc");
		model.addAttribute("brandIds",mallProductBrandService.findList(mallProductBrand));
    	
        model.addAttribute("mallSupplyer", mallSupplyer);
        return "mall/mallSupplyer/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(MallSupplyer mallSupplyer, RedirectAttributes redirectAttributes) {
    	mallSupplyer.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	mallSupplyer.setStartDate(mallSupplyer.getRegTime());
    	mallSupplyer.setAdminPwd(DigestUtils.md5Hex(mallSupplyer.getAdminPwd()));
    	
        mallSupplyerService.save(mallSupplyer);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mall/mallSupplyer/update?id="+mallSupplyer.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(MallSupplyer mallSupplyer, Model model) {
    	
    	Dict dict = new Dict();
    	dict.setDict(DictType.AREA.getType());
    	dict.setStatus("1");
		model.addAttribute("area",dictService.findList(dict));
		
		MallProductBrand mallProductBrand = new MallProductBrand();
		mallProductBrand.setStatus("1");
		mallProductBrand.setOrderBy("a.sort asc");
		model.addAttribute("brandIds",mallProductBrandService.findList(mallProductBrand));
		
        model.addAttribute("mallSupplyer", mallSupplyer);
        return "mall/mallSupplyer/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MallSupplyer mallSupplyer, RedirectAttributes redirectAttributes) {
    	mallSupplyer.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	
        mallSupplyerService.save(mallSupplyer);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mall/mallSupplyer/update?id="+mallSupplyer.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        mallSupplyerService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mall/mallSupplyer?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
