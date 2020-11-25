package com.jf.plus.api.mobile.page.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.setting.entity.MsgTemplate;
import com.jf.plus.core.setting.service.MsgTemplateService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
* 消息模板表 接口控制器
* @author Tng
* @version 1.0
*/
@Controller
public class MsgTemplateController extends BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(MsgTemplateController.class);

    @Autowired
    private MsgTemplateService msgTemplateService;

    @RequestMapping(value={"/api/msgTemplateList"})
    @ResponseBody
    public Result list(Page<MsgTemplate> page,MsgTemplate msgTemplate) {
        Result result = Result.newInstance();
        try{
        	msgTemplate.setStatus("1");
        	msgTemplate.setAvaliable("1");
        	page.setEntity(msgTemplate);
        	page.setOrderBy("a.sort asc");
            page.setList(msgTemplateService.findPage(page));

            ResultObj resultObj = new ResultObj();
            resultObj.put("page", MPageInfo.transform(page));
            result.setObj(resultObj);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            return result;
        }catch(Exception e){
            LOGGER.error("查询消息模板表列表异常:{}",e);
            return Result.newExceptionInstance();
        }

    }

    @RequestMapping(value={"/api/saveMsgTemplate"})
    @ResponseBody
    public Result save(MsgTemplate msgTemplate) {
            Result result = Result.newInstance();
            try{

                msgTemplateService.save(msgTemplate);

                result.setCode(ResultCode.SUCCESS);
                result.setMsg("新增成功");
                return result;
            }catch(Exception e){
                LOGGER.error("新增消息模板表异常:{}",e);
                return Result.newExceptionInstance();
            }

    }
}
