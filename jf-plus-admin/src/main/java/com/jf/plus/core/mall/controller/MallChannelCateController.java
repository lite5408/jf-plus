package com.jf.plus.core.mall.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.vo.TreeVo;
import com.jf.plus.core.mall.entity.MallChannelCate;
import com.jf.plus.core.mall.service.MallChannelCateService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;

/**
 * 渠道商品分类表 控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/mall/mallChannelCate")
public class MallChannelCateController extends BaseController {

	@Autowired
	private MallChannelCateService mallChannelCateService;

	@ModelAttribute
	public MallChannelCate get(@RequestParam(required = false) String id) {
		MallChannelCate entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = mallChannelCateService.get(id);
		}
		if (entity == null) {
			entity = new MallChannelCate();
		}
		return entity;
	}

	@RequestMapping(value = "/list")
	public String list(Model model, MallChannelCate mallChannelCate, Page<MallChannelCate> page) {
		if(StringUtils.isBlank(mallChannelCate.getCatName())){
			if(mallChannelCate.getCatPid() == null){
				mallChannelCate.setCatPid(0L);
			}
		}
		page.setEntity(mallChannelCate);
		model.addAttribute("page", new Page<TreeVo>().setList(mallChannelCateService.findTreePage(page)));
		model.addAttribute("mallChannelCate", mallChannelCate);
		return "mall/mallChannelCate/list";
	}

	@RequestMapping(value = "/relatedCate", method = RequestMethod.GET)
	public String relatedCate(MallChannelCate mallChannelCate,Model model) {
		model.addAttribute("mallChannelCate", mallChannelCate);
		return "mall/mallChannelCate/relatedCate";
	}
	
	@RequestMapping(value = "/relatedCate", method = RequestMethod.POST)
	public String relatedCate(MallChannelCate mallChannelCate, RedirectAttributes redirectAttributes) {
		mallChannelCateService.save(mallChannelCate);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/mall/mallChannelCate/relatedCate?id=" + mallChannelCate.getId();
	}

	@RequestMapping(value = "/childNodes")
	@ResponseBody
	public Result childNodes(MallChannelCate entity) {
		Result result = Result.newInstance();
		try {

			List<MallChannelCate> cateList = mallChannelCateService.findList(entity);

			result.setObj(cateList);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (RuntimeException e) {
			logger.error("查询失败：{}", e);
			return Result.newExceptionInstance();

		}
	}
	
	@RequestMapping(value = "/select")
	public String select(Model model, MallChannelCate mallChannelCate, Page<MallChannelCate> page) {
		if(StringUtils.isBlank(mallChannelCate.getCatName())){
			if(mallChannelCate.getCatPid() == null){
				mallChannelCate.setCatPid(0L);
			}
		}
		page.setEntity(mallChannelCate);
		model.addAttribute("page", new Page<TreeVo>().setList(mallChannelCateService.findTreePage(page)));
		model.addAttribute("mallChannelCate", mallChannelCate);
		return "select/channelCate/list";
	}
}
