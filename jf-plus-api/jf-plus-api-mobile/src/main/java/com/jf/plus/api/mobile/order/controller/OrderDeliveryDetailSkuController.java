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
import com.jf.plus.core.order.entity.OrderDeliveryDetailSku;
import com.jf.plus.core.order.service.OrderDeliveryDetailSkuService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
* 订单发货SKU表 接口控制器
* @author Tng
* @version 1.0
*/
@Controller
public class OrderDeliveryDetailSkuController extends BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderDeliveryDetailSkuController.class);

    @Autowired
    private OrderDeliveryDetailSkuService orderDeliveryDetailSkuService;

    @RequestMapping(value={"/api/orderDeliveryDetailSkuList"})
    @ResponseBody
    public Result list(Page<OrderDeliveryDetailSku> page) {
        Result result = Result.newInstance();
        try{
            page.setList(orderDeliveryDetailSkuService.findPage(page));

            ResultObj resultObj = new ResultObj();
            resultObj.put("page", MPageInfo.transform(page));
            result.setObj(resultObj);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            return result;
        }catch(Exception e){
            LOGGER.error("查询订单发货SKU表列表异常:{}",e);
            return Result.newExceptionInstance();
        }

    }

    @RequestMapping(value={"/api/saveOrderDeliveryDetailSku"})
    @ResponseBody
    public Result save(OrderDeliveryDetailSku orderDeliveryDetailSku) {
            Result result = Result.newInstance();
            try{

                orderDeliveryDetailSkuService.save(orderDeliveryDetailSku);

                result.setCode(ResultCode.SUCCESS);
                result.setMsg("新增成功");
                return result;
            }catch(Exception e){
                LOGGER.error("新增订单发货SKU表异常:{}",e);
                return Result.newExceptionInstance();
            }

    }
}
