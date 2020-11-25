package com.jf.plus.core.account.controller;

import java.util.Date;
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
import com.jf.plus.common.core.enums.AbstractType;
import com.jf.plus.common.core.enums.AccountType;
import com.jf.plus.common.core.enums.DealType;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.VoucherSource;
import com.jf.plus.core.account.entity.Voucher;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.entity.VoucherFlow;
import com.jf.plus.core.account.service.BlanceService;
import com.jf.plus.core.account.service.VoucherAccCashService;
import com.jf.plus.core.account.service.VoucherFlowService;
import com.jf.plus.core.account.service.VoucherService;
import com.jf.plus.core.mall.service.MallSiteService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.service.OrganizationService;

/**
* 电子券信息表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/account/voucher")
public class VoucherController extends BaseController {

    @Autowired
    private VoucherService voucherService;
    
    @Autowired
    private OrganizationService organizationService;
    
    @Autowired
    private VoucherAccCashService voucherAccCashService;
    
    @Autowired
    private VoucherFlowService voucherFlowService;
    
    @Autowired
    private MallSiteService mallSiteService;
    
    @Autowired
    private BlanceService blanceService;
    
    @ModelAttribute
    public Voucher get(@RequestParam(required = false) String id) {
        Voucher entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = voucherService.get(id);
        }
        if (entity == null) {
            entity = new Voucher();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<Voucher> page, Voucher voucher) {
    	voucher.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	voucher.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
    	voucher.setSource(VoucherSource.BUY.getType());
    	page.setEntity(voucher);
        model.addAttribute("page", page.setList(voucherService.findPage(page)));
        return "account/voucher/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Voucher voucher,Model model) {
        model.addAttribute("voucher", voucher);
        return "account/voucher/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Voucher voucher, RedirectAttributes redirectAttributes) {
    	if(UserUtils.getMySite() == null){
			addMessage(redirectAttributes,"您没有站点管理权限");
			return "error/400";
		}
    	Integer marketRatio = UserUtils.getLoginUser().getOrganization().getMarketRatio();
    	if(marketRatio==null){
    		addMessage(redirectAttributes,"当前机构积分比例参数缺失，请联系系统管理员完善该配置后操作！");
			return "redirect:"+ adminPath +"/account/voucher/list";
    	}
    	
    	voucher.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
    	voucher.setRatio(marketRatio);
    	voucher.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
    	voucher.setValidStartDate(new Date());
        voucherService.save(voucher);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/account/voucher/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Voucher voucher, Model model) {
    	model.addAttribute("organization", organizationService.get(UserUtils.getLoginUser().getOrganizationId()));
        model.addAttribute("voucher", voucher);
        return "account/voucher/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Voucher voucher, RedirectAttributes redirectAttributes) {
        voucherService.save(voucher);
        
//        //修改电子券下对应卡券的失效时间
//        VoucherAccCash voucherAccCash = new VoucherAccCash();
//        voucherAccCash.setVoucherId(Long.valueOf(voucher.getId()));
//        List<VoucherAccCash> voucherAccCashList = voucherAccCashService.findList(voucherAccCash);
//        if (CollectionUtil.isNotEmpty(voucherAccCashList)) {
//			for (VoucherAccCash v : voucherAccCashList) {
//				long changeTime = voucher.getValidEndDate().getTime();
//				long accEndTime = v.getValidEndDate().getTime();
//				long realAccEndTime = accEndTime + (1000 * 60 * 60 * 24 * 10);
//				if (realAccEndTime > new Date().getTime() && changeTime>accEndTime){
//					v.setValidEndDate(voucher.getValidEndDate());
//					voucherAccCashService.save(v);
//				}
//			}
//		}
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/account/voucher/list";
    }
    
    @RequestMapping(value = "/distribute")
    public String distribute(String voucherId,String distAmount, String mobile,String remarks, RedirectAttributes redirectAttributes) {
    	Voucher voucherJF = voucherService.get(voucherId);
		if (-1D != voucherJF.getPrice().doubleValue()) {
			distAmount = "" + voucherJF.getPrice().doubleValue();
		}
		Long orgId = mallSiteService.get(UserUtils.getMySite().getSiteId()).getOrgId();
    	if(blanceService.getOrgBlance(orgId + "").doubleValue() > Double.valueOf(distAmount).doubleValue()){
    		int ret = voucherAccCashService.distribute(voucherJF,distAmount,mobile,remarks);
    		if(ret == 1){
				addMessage(redirectAttributes,"分发成功");
			}else if(ret == -1){
				addMessage(redirectAttributes,"分发失败，用户余额不满足负充值需要");
			}else{
				addMessage(redirectAttributes, "分发失败，系统异常，请联系管理员");
			}
    	}else {
    		addMessage(redirectAttributes,"分发失败，用户余额不足");
		}
    	return "redirect:"+ adminPath +"/account/voucher/list";
    }
    
    @RequestMapping(value={"/cardPage",""})
    public String cardPage(Model model, Page<VoucherAccCash> page, VoucherAccCash voucherAccCash) {
        model.addAttribute("voucher", voucherService.get(voucherAccCash.getVoucherId() + ""));
        page.setEntity(voucherAccCash);
        List<VoucherAccCash> voucherAccCashList = voucherAccCashService.findPointPage(page);
        for (VoucherAccCash vac : voucherAccCashList) {
        	VoucherFlow voucherFlow = new VoucherFlow();
        	voucherFlow.setAccId(Long.valueOf(vac.getId()));
        	voucherFlow.setAccountType(AccountType.JF.getType());
        	voucherFlow.setDealType(DealType.EXPENDITURE.getType());
        	voucherFlow.setAbstractType(AbstractType.ORDER.getType());
        	voucherFlow = voucherFlowService.findAccRecentOrderFlow(voucherFlow);
        	vac.setBindOrder(voucherFlow == null ? "无" : (voucherFlow.getTargetId() + ""));
		}
        model.addAttribute("page", page.setList(voucherAccCashList));
        return "account/voucher/voucherCard";
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        voucherService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/account/voucher?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    @RequestMapping(value = "/changeOperStatus", method = RequestMethod.GET)
    public String changeOperStatus(Voucher voucher) {
    	if(voucher != null && (voucher.getOperStatus() == null || voucher.getOperStatus() == 1)){
    		voucher.setOperStatus(Integer.valueOf(Voucher.STATUS_DELETE));
    		voucherService.save(voucher);
    	}else {
    		voucher.setOperStatus(Integer.valueOf(Voucher.STATUS_NORMAL));
    		voucherService.save(voucher);
		}
        return "redirect:"+ adminPath + "/account/voucher/list";
    }
    
    @ResponseBody
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public Result changeStatus(String voucherAccCashId,String status) {
    	Result r = Result.newInstance();
    	try {
    		VoucherAccCash voucherAccCash = voucherAccCashService.get(voucherAccCashId);
    		voucherAccCash.setOperStatus(Integer.valueOf(status));
    		voucherAccCashService.save(voucherAccCash);
    		r.setSuccess(true);
    		r.setObj(voucherAccCash);
    		return r;
		} catch (Exception e) {
			return Result.newExceptionInstance();
		}
    }
    
    @ResponseBody
    @RequestMapping(value = "/batchActiveStatus", method = RequestMethod.POST)
    public Result batchActiveStatus(String voucherAccCashStr) {
    	Result r = Result.newInstance();
    	String[] voucherAccCashArr = voucherAccCashStr.split(",");
    	try {
    		for (String voucherAccCashId : voucherAccCashArr) {
    			VoucherAccCash voucherAccCash = voucherAccCashService.get(voucherAccCashId);
    			voucherAccCash.setOperStatus(Status.NORMAL.getType());
        		voucherAccCashService.save(voucherAccCash);
			}
    		r.setSuccess(true);
    		return r;
		} catch (Exception e) {
			return Result.newExceptionInstance();
		}
    }
}
