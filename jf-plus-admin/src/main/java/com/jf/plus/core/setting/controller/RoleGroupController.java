package com.jf.plus.core.setting.controller;

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
import com.jf.plus.core.setting.entity.RoleGroup;
import com.jf.plus.core.setting.service.RoleGroupService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Role;
import cn.iutils.sys.entity.enums.DataScopeEnum;
import cn.iutils.sys.service.RoleService;

/**
* 角色组 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/sys/roleGroup")
public class RoleGroupController extends BaseController {

    @Autowired
    private RoleGroupService roleGroupService;
    
    @Autowired
    private RoleService roleService;

    @ModelAttribute
    public RoleGroup get(@RequestParam(required = false) String id) {
        RoleGroup entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = roleGroupService.get(id);
        }
        if (entity == null) {
            entity = new RoleGroup();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, RoleGroup roleGroup,Page<RoleGroup> page) {
    	roleGroup.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.org, "org", "a"));
    	page.setEntity(roleGroup);
        model.addAttribute("page", page.setList(roleGroupService.findPage(page)));
        return "setting/roleGroup/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(RoleGroup roleGroup,Model model) {
    	Role entity = new Role();
    	entity.setOrganizationId(UserUtils.getLoginUser().getOrganizationId());
		model.addAttribute("myRoleList", roleService.findMyRoleList(entity));
    	
        model.addAttribute("roleGroup", roleGroup);
        return "setting/roleGroup/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(RoleGroup roleGroup, RedirectAttributes redirectAttributes) {
        roleGroupService.save(roleGroup);
        
        
        
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/sys/roleGroup/update?id="+roleGroup.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(RoleGroup roleGroup, Model model) {
    	
    	Role entity = new Role();
    	entity.setOrganizationId(UserUtils.getLoginUser().getOrganizationId());
		model.addAttribute("myRoleList", roleService.findMyRoleList(entity));
		
        model.addAttribute("roleGroup", roleGroup);
        return "setting/roleGroup/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(RoleGroup roleGroup, RedirectAttributes redirectAttributes) {
        roleGroupService.save(roleGroup);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/sys/roleGroup/update?id="+roleGroup.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        roleGroupService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/sys/roleGroup?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    /**
     * 查询我的角色组
     * @param model
     * @return
     */
    @RequestMapping(value = "/getMyRoleGroupList")
    @ResponseBody
    public Result getMyRoleGroupList(RoleGroup entity, Model model){
    	Result result = new Result();

		try {
			entity.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
			List<RoleGroup> list = roleGroupService.findMyRoleGroupList(entity);
			result.setObj(list);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			logger.error("查询角色组异常:{}", e);
			return Result.newExceptionInstance();
		}
    }
}
