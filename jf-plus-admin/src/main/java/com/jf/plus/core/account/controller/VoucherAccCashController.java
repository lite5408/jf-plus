package com.jf.plus.core.account.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.enums.AccountType;
import com.jf.plus.common.core.enums.DistSource;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.utils.CardGen;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.core.account.entity.Voucher;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.service.BlanceService;
import com.jf.plus.core.account.service.CodeService;
import com.jf.plus.core.account.service.VoucherAccCashService;
import com.jf.plus.core.account.service.VoucherService;
import com.jf.plus.core.mall.service.MallSiteService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;

/**
* 电子券卡号信息表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/account/voucherAccCash")
public class VoucherAccCashController extends BaseController {

    @Autowired
    private VoucherAccCashService voucherAccCashService;
    
    @Autowired
    private VoucherService voucherService;
    
    @Autowired
    private CodeService codeService;
    
    @Autowired
    private BlanceService blanceService;
    
    @Autowired
    private MallSiteService mallSiteService;

    @ModelAttribute
    public VoucherAccCash get(@RequestParam(required = false) String id) {
        VoucherAccCash entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = voucherAccCashService.get(id);
        }
        if (entity == null) {
            entity = new VoucherAccCash();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<VoucherAccCash> page) {
        model.addAttribute("page", page.setList(voucherAccCashService.findPage(page)));
        return "account/voucherAccCash/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(VoucherAccCash voucherAccCash,Model model) {
        model.addAttribute("voucherAccCash", voucherAccCash);
        return "account/voucherAccCash/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(VoucherAccCash voucherAccCash, RedirectAttributes redirectAttributes) {
        voucherAccCashService.save(voucherAccCash);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/account/voucherAccCash/update?id="+voucherAccCash.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(VoucherAccCash voucherAccCash, Model model) {
        model.addAttribute("voucherAccCash", voucherAccCash);
        return "account/voucherAccCash/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(VoucherAccCash voucherAccCash, RedirectAttributes redirectAttributes) {
        voucherAccCashService.save(voucherAccCash);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/account/voucherAccCash/update?id="+voucherAccCash.getId();
    }
    
    @RequestMapping(value="/batchCreateCash", method = RequestMethod.POST)
    public String batchCreateCash(String voucherId,String prefix,Integer num, RedirectAttributes redirectAttributes) {
    	Voucher voucher = voucherService.get(voucherId);
    	if (voucher.getPrice().doubleValue() < 0D) {
    		addMessage(redirectAttributes,"自定义面值的电子券不可批量生成卡号！");
    		return "redirect:"+ adminPath +"/account/voucher/cardPage?voucherId="+voucherId;
		}
    	Long orgId = mallSiteService.get(UserUtils.getMySite().getSiteId()).getOrgId();
    	if(blanceService.getOrgBlance(orgId + "").doubleValue() > (num.doubleValue() * voucher.getPrice().doubleValue())){
	    	for (int i = 0; i < num; i++) {
	    		VoucherAccCash voucherAccCash = new VoucherAccCash();
	    		voucherAccCash.setVoucherId(Long.valueOf(voucher.getId()));
	    		voucherAccCash.setBatchNo(CodeGen.getBatchNo());
	    		voucherAccCash.setAccountType(AccountType.JF.getType());
	    		voucherAccCash.setDistOrgId(orgId);
	    		voucherAccCash.setDistUserId(Long.valueOf(UserUtils.getLoginUser().getId()));
	    		voucherAccCash.setDistSource(DistSource.SITE_DIST.getType());
	    		voucherAccCash.setDelayCount(0);
	    		int couponAccount = codeService.genAccount(Constants.VOUCHER_SEQ);
	    		voucherAccCash.setCouponAccount(prefix.toUpperCase() + CardGen.formatAccount(couponAccount));
				voucherAccCash.setCouponCode(CardGen.getCardAccPwd());
				voucherAccCash.setBlance(voucher.getPrice());
				voucherAccCash.setTotalBlance(voucher.getPrice());
				voucherAccCash.setValidStartDate(voucher.getValidStartDate());
				voucherAccCash.setValidEndDate(voucher.getValidEndDate());
				voucherAccCash.setOperStatus(Status.LOCK.getType());
				voucherAccCash.setCreateBy(UserUtils.getLoginUser().getId());
				voucherAccCash.setCreateDate(new Date());
				voucherAccCash.setUpdateBy(UserUtils.getLoginUser().getId());
				voucherAccCash.setUpdateDate(new Date());
				voucherAccCash.setRemarks("批量生成电子券卡券");
				voucherAccCash.setStatus(Status.NORMAL.getType() + "");
				voucherAccCashService.save(voucherAccCash);
			}
    	}else {
    		addMessage(redirectAttributes,"电子券生成失败，机构余额不足");
		}
        return "redirect:"+ adminPath +"/account/voucher/cardPage?voucherId="+voucherId;
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        voucherAccCashService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/account/voucherAccCash?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
