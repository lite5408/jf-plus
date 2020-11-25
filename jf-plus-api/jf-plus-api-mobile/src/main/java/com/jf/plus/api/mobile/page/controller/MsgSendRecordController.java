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
import com.jf.plus.core.setting.entity.MsgSendRecord;
import com.jf.plus.core.setting.service.MsgSendRecordService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
* 消息发送记录表 接口控制器
* @author Tng
* @version 1.0
*/
@Controller
public class MsgSendRecordController extends BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(MsgSendRecordController.class);

    @Autowired
    private MsgSendRecordService msgSendRecordService;

    @RequestMapping(value={"/api/msgSendRecordList"})
    @ResponseBody
    public Result list(Page<MsgSendRecord> page,MsgSendRecord msgSendRecord) {
        Result result = Result.newInstance();
        try{
        	msgSendRecord.setStatus("1");
        	page.setEntity(msgSendRecord);
            page.setList(msgSendRecordService.findPage(page));

            ResultObj resultObj = new ResultObj();
            resultObj.put("page", MPageInfo.transform(page));
            result.setObj(resultObj);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            return result;
        }catch(Exception e){
            LOGGER.error("查询消息发送记录表列表异常:{}",e);
            return Result.newExceptionInstance();
        }

    }

    @RequestMapping(value={"/api/saveMsgSend"})
    @ResponseBody
    public Result save(MsgSendRecord msgSendRecord) {
            Result result = Result.newInstance();
            try{

                msgSendRecordService.save(msgSendRecord);

                result.setCode(ResultCode.SUCCESS);
                result.setMsg("新增成功");
                return result;
            }catch(Exception e){
                LOGGER.error("新增消息发送记录表异常:{}",e);
                return Result.newExceptionInstance();
            }

    }
}
