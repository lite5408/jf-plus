package com.jf.plus.core.site.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.jf.plus.common.core.enums.AdvertType;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.utils.StringUtil;
import com.jf.plus.core.site.entity.AdvertItems;
import com.jf.plus.core.site.entity.SiteAdvert;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.AdvertItemsService;
import com.jf.plus.core.site.service.SiteAdvertService;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;

/**
 * 商城广告专题配置 控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/site/siteAdvert")
public class SiteAdvertController extends BaseController {

	@Autowired
	private SiteAdvertService siteAdvertService;

	@Autowired
	private AdvertItemsService advertItemsService;
	
	@Autowired
	private SiteProductService siteProductService;

	@ModelAttribute
	public SiteAdvert get(@RequestParam(required = false) String id) {
		SiteAdvert entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = siteAdvertService.get(id);
		}
		if (entity == null) {
			entity = new SiteAdvert();
		}
		return entity;
	}

	@RequestMapping(value = "/list")
	public String list(Model model, SiteAdvert siteAdvert, Page<SiteAdvert> page) {
		if(UserUtils.getMySite() == null){
			model.addAttribute("errorMsg", "您没有管理站点权限，请联系管理员");
			return "error/400";
		}
		page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());
		page.getCondition().put("type", siteAdvert.getType());
		page.getCondition().put("name", siteAdvert.getName());
		model.addAttribute("page", page.setList(siteAdvertService.findPage(page)));
		AdvertType advertType = AdvertType.getByType(siteAdvert.getType());
		switch (advertType) {
		case ADVERT:
			return "site/siteAdvert/list";

		case CLASS:
			return "site/siteAdvert/list_cate";

		case SPECIAL:

			return "site/siteAdvert/list_special";
		default:
			return "site/siteAdvert/list";
		}

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(SiteAdvert siteAdvert, Model model) {
		model.addAttribute("siteAdvert", siteAdvert);
		AdvertType advertType = AdvertType.getByType(siteAdvert.getType());
		switch (advertType) {
		case ADVERT:
			return "site/siteAdvert/form";

		case CLASS:
			return "site/siteAdvert/form_cate";

		case SPECIAL:

			return "site/siteAdvert/form_special";
		default:
			return "site/siteAdvert/form";
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(SiteAdvert siteAdvert, RedirectAttributes redirectAttributes) {
		siteAdvert.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		siteAdvert.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
		siteAdvertService.save(siteAdvert);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath + "/site/siteAdvert/update?id=" + siteAdvert.getId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(SiteAdvert siteAdvert, Model model) {
		model.addAttribute("siteAdvert", siteAdvert);
		AdvertType advertType = AdvertType.getByType(siteAdvert.getType());
		switch (advertType) {
		case ADVERT:
			return "site/siteAdvert/form";

		case CLASS:
			return "site/siteAdvert/form_cate";

		case SPECIAL:

			return "site/siteAdvert/form_special";
		default:
			return "site/siteAdvert/form";
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(SiteAdvert siteAdvert, RedirectAttributes redirectAttributes) {
		siteAdvertService.save(siteAdvert);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/site/siteAdvert/update?id=" + siteAdvert.getId();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, int pageNo, int pageSize,String type,
			RedirectAttributes redirectAttributes) {
		siteAdvertService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/site/siteAdvert/list?pageNo=" + pageNo + "&pageSize=" + pageSize + "&type="+type;
	}
	
	/**
	 * 专题商品
	 * @param advertId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myProduct")
	public String mySpecialProduct(String advertId,String itemCode,String itemName,String supplyName,Model model){
		Page<SiteProduct> page = new Page<>();
		page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());
		
		//查询条件
		page.getCondition().put("itemCode", itemCode);
		page.getCondition().put("itemName", itemName);
		page.getCondition().put("supplyName", supplyName);
		page.setOrderBy("id desc");
		
		String items = siteAdvertService.get(advertId).getItems();
		if(StringUtils.isNotBlank(items)){
			page.getCondition().put("inz", "in");
			page.getCondition().put("items", items.split(","));
			List<SiteProduct> productList = siteProductService.findPage(page);
			model.addAttribute("page", page.setList(productList));
		}else{
			model.addAttribute("page", page);
		}
		
		
		model.addAttribute("advertId", advertId);
		
		return "site/siteAdvert/my_product";
	}

	@RequestMapping(value = "/pick")
	@ResponseBody
	public Result pick(String advertId, SiteProduct siteProduct) {
		Result result = new Result();

		try {
			ProductStatus productStatus = ProductStatus.getByType(siteProduct.getOperStatus());
			switch (productStatus) {
			case SHELVES:
				pickInSpecial(advertId, siteProduct);
				break;
			case OUT_SHELVES:
				pickOutSpecial(advertId, siteProduct);
				break;
			default:
				break;
			}

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			logger.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	private void pickOutSpecial(String advertId, SiteProduct siteProduct) {
		SiteAdvert siteAdvert = siteAdvertService.get(advertId);
		siteAdvert.setItemsCount(siteAdvert.getItemsCount() - 1);
		String items = StringUtil.replaceSeparator(siteAdvert.getItems(), siteProduct.getId(), "", ",");
		siteAdvert.setItems(items);
		siteAdvertService.save(siteAdvert);
		
		AdvertItems entity = new AdvertItems();
		entity.setAdvertId(Long.valueOf(advertId));
		entity.setItemId(Long.valueOf(siteProduct.getId()));
		advertItemsService.deleteEntity(entity );
	}

	private void pickInSpecial(String advertId, SiteProduct siteProduct) {
		SiteAdvert siteAdvert = siteAdvertService.get(advertId);
		if(siteAdvert.getItemsCount() == null){
			siteAdvert.setItemsCount(0);
		}
		siteAdvert.setItemsCount(siteAdvert.getItemsCount() + 1);
		if (StringUtils.isBlank(siteAdvert.getItems())) {
			siteAdvert.setItems(siteProduct.getId());
		}else{
			siteAdvert.setItems(siteAdvert.getItems() + "," + siteProduct.getId());
		}
		siteAdvertService.save(siteAdvert);
		
		AdvertItems entity = new AdvertItems();
		entity.setAdvertId(Long.valueOf(advertId));
		entity.setItemId(Long.valueOf(siteProduct.getId()));
		entity.setOrgId(siteAdvert.getOrgId());
		entity.setSiteId(siteAdvert.getSiteId());
		entity.setSource(siteProduct.getSource());
		advertItemsService.save(entity);

	}
	
}
