package com.jf.plus.api.mobile.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.order.entity.OrderDeliveryExport;
import com.jf.plus.core.order.service.OrderDeliveryExportService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
* 订单导出表 接口控制器
* @author Tng
* @version 1.0
*/
@Controller
public class OrderDeliveryExportController extends BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderDeliveryExportController.class);

    @Autowired
    private OrderDeliveryExportService orderDeliveryExportService;

    @RequestMapping(value={"/api/orderDeliveryExportList"})
    @ResponseBody
    public Result list(Page<OrderDeliveryExport> page) {
        Result result = Result.newInstance();
        try{
            page.setList(orderDeliveryExportService.findPage(page));

            ResultObj resultObj = new ResultObj();
            resultObj.put("page", MPageInfo.transform(page));
            result.setObj(resultObj);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            return result;
        }catch(Exception e){
            LOGGER.error("查询订单导出表列表异常:{}",e);
            return Result.newExceptionInstance();
        }

    }

    @RequestMapping(value={"/api/saveOrderDeliveryExport"})
    @ResponseBody
    public Result save(OrderDeliveryExport orderDeliveryExport) {
            Result result = Result.newInstance();
            try{

                orderDeliveryExportService.save(orderDeliveryExport);

                result.setCode(ResultCode.SUCCESS);
                result.setMsg("新增成功");
                return result;
            }catch(Exception e){
                LOGGER.error("新增订单导出表异常:{}",e);
                return Result.newExceptionInstance();
            }

    }
}
