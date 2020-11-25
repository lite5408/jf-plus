package com.jf.plus.core.mallSetting.controller;

import java.util.Date;

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

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.utils.CardGen;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.core.account.service.BlanceService;
import com.jf.plus.core.account.service.CodeService;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.mallSetting.entity.PacksAccCash;
import com.jf.plus.core.mallSetting.entity.PacksInfo;
import com.jf.plus.core.mallSetting.service.PacksAccCashService;
import com.jf.plus.core.mallSetting.service.PacksInfoService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;

/**
* 礼包卡券表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/mallSetting/packsAccCash")
public class PacksAccCashController extends BaseController {

    @Autowired
    private PacksAccCashService packsAccCashService;
    
    @Autowired
    private CodeService codeService;
    
    @Autowired
    private PacksInfoService packsInfoService;
    
    @Autowired
    private BlanceService blanceService;
    
    @Autowired
    private MallSiteService mallSiteService;
    
    @ModelAttribute
    public PacksAccCash get(@RequestParam(required = false) String id) {
        PacksAccCash entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = packsAccCashService.get(id);
        }
        if (entity == null) {
            entity = new PacksAccCash();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<PacksAccCash> page, PacksAccCash packsAccCash) {
    	page.setEntity(packsAccCash);
        model.addAttribute("page", page.setList(packsAccCashService.findPacksAccCashUserPage(page)));
        return "mallSetting/packsAccCash/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(PacksAccCash packsAccCash,Model model) {
        model.addAttribute("packsAccCash", packsAccCash);
        return "mallSetting/packsAccCash/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PacksAccCash packsAccCash, RedirectAttributes redirectAttributes) {
        packsAccCashService.save(packsAccCash);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/mallSetting/packsAccCash/update?id="+packsAccCash.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(PacksAccCash packsAccCash, Model model) {
        model.addAttribute("packsAccCash", packsAccCash);
        return "mallSetting/packsAccCash/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PacksAccCash packsAccCash, RedirectAttributes redirectAttributes) {
        packsAccCashService.save(packsAccCash);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/mallSetting/packsAccCash/update?id="+packsAccCash.getId();
    }
    
    @ResponseBody
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public Result changeStatus(String packsAccCashId,String status) {
    	Result r = Result.newInstance();
    	try {
    		PacksAccCash packsAccCash = packsAccCashService.get(packsAccCashId);
    		packsAccCash.setOperStatus(Integer.valueOf(status));
    		packsAccCashService.save(packsAccCash);
    		r.setSuccess(true);
    		r.setObj(packsAccCash);
    		return r;
		} catch (Exception e) {
			return Result.newExceptionInstance();
		}
    }
    
    @ResponseBody
    @RequestMapping(value = "/batchActiveStatus", method = RequestMethod.POST)
    public Result batchActiveStatus(String packsAccCashStr) {
    	Result r = Result.newInstance();
    	String[] packsAccCashArr = packsAccCashStr.split(",");
    	try {
    		for (String packsAccCashId : packsAccCashArr) {
    			PacksAccCash packsAccCash = packsAccCashService.get(packsAccCashId);
    			packsAccCash.setOperStatus(Status.NORMAL.getType());
    			packsAccCashService.save(packsAccCash);
			}
    		r.setSuccess(true);
    		return r;
		} catch (Exception e) {
			return Result.newExceptionInstance();
		}
    }
    
    @RequestMapping(value="/batchCreateCash", method = RequestMethod.POST)
    public String batchCreateCash(String packsId,String prefix,Integer num, RedirectAttributes redirectAttributes) {
    	PacksInfo packsInfo = packsInfoService.get(packsId);
    	Long orgId = mallSiteService.get(UserUtils.getMySite().getSiteId()).getOrgId();
    	if(blanceService.getOrgBlance(orgId + "").doubleValue() > (packsInfo.getSalePrice().doubleValue() * num.doubleValue())){
	    	for (int i = 0; i < num; i++) {
	    		PacksAccCash packsAccCash = new PacksAccCash();
				packsAccCash.setPacksId(Long.valueOf(packsInfo.getId()));
				packsAccCash.setBatchNo(CodeGen.getBatchNo());
				packsAccCash.setDistOrgId(orgId);
				packsAccCash.setDistUserId(Long.valueOf(UserUtils.getLoginUser().getId()));
				packsAccCash.setDelayCount(0);
				int couponAccount = codeService.genAccount(Constants.PACKS_SEQ);
				packsAccCash.setCouponAccount(prefix.toUpperCase() + CardGen.formatAccount(couponAccount));
				packsAccCash.setCouponCode(CardGen.getCardAccPwd());
				packsAccCash.setValidStartDate(packsInfo.getValidStartDate());
				packsAccCash.setValidEndDate(packsInfo.getValidEndDate());
				packsAccCash.setCreateBy(UserUtils.getLoginUser().getId());
				packsAccCash.setCreateDate(new Date());
				packsAccCash.setUpdateBy(UserUtils.getLoginUser().getId());
				packsAccCash.setUpdateDate(new Date());
				packsAccCash.setRemarks("批量生成礼包卡券");
				packsAccCash.setOperStatus(Status.LOCK.getType());
				packsAccCash.setStatus(Status.NORMAL.getType() + "");
				packsAccCashService.save(packsAccCash);
			}
    	}else {
    		addMessage(redirectAttributes,"礼包券生成失败，机构余额不足");
		}
        return "redirect:"+ adminPath +"/mallSetting/packsAccCash/list?packsId="+packsInfo.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        packsAccCashService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/mallSetting/packsAccCash?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
