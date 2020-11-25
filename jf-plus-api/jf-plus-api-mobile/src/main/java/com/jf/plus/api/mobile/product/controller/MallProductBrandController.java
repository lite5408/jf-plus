package com.jf.plus.api.mobile.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.product.entity.MallProductBrand;
import com.jf.plus.core.product.service.MallProductBrandService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
* 机构品牌表 接口控制器
* @author Tng
* @version 1.0
*/
@Controller
public class MallProductBrandController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(MallProductBrandController.class);

    @Autowired
    private MallProductBrandService mallProductBrandService;

    @RequestMapping(value={"/api/mallProductBrandList"})
    @ResponseBody
    public Result list(Page<MallProductBrand> page) {
    	Result result = Result.newInstance();
        try{
            page.setList(mallProductBrandService.findPage(page));

            ResultObj resultObj = new ResultObj();
            resultObj.put("page", MPageInfo.transform(page));
            result.setObj(resultObj);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            return result;
        }catch(Exception e){
            LOGGER.error("查询机构品牌表列表异常:{}",e);
            return Result.newExceptionInstance();
        }

    }

    @RequestMapping(value={"/api/saveMallProductBrand"})
    @ResponseBody
    public Result save(MallProductBrand mallProductBrand) {
    	Result result = Result.newInstance();
            try{

                mallProductBrandService.save(mallProductBrand);

                result.setCode(ResultCode.SUCCESS);
                result.setMsg("新增成功");
                return result;
            }catch(Exception e){
                LOGGER.error("新增机构品牌表异常:{}",e);
                return Result.newExceptionInstance();
            }

    }
}
