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
import com.jf.plus.core.order.entity.OrderDeliveryDetail;
import com.jf.plus.core.order.service.OrderDeliveryDetailService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
* 订单发货商品表 接口控制器
* @author Tng
* @version 1.0
*/
@Controller
public class OrderDeliveryDetailController extends BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderDeliveryDetailController.class);

    @Autowired
    private OrderDeliveryDetailService orderDeliveryDetailService;

    @RequestMapping(value={"/api/orderDeliveryDetailList"})
    @ResponseBody
    public Result list(Page<OrderDeliveryDetail> page) {
        Result result = Result.newInstance();
        try{
            page.setList(orderDeliveryDetailService.findPage(page));

            ResultObj resultObj = new ResultObj();
            resultObj.put("page", MPageInfo.transform(page));
            result.setObj(resultObj);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            return result;
        }catch(Exception e){
            LOGGER.error("查询订单发货商品表列表异常:{}",e);
            return Result.newExceptionInstance();
        }

    }

    @RequestMapping(value={"/api/saveOrderDeliveryDetail"})
    @ResponseBody
    public Result save(OrderDeliveryDetail orderDeliveryDetail) {
            Result result = Result.newInstance();
            try{

                orderDeliveryDetailService.save(orderDeliveryDetail);

                result.setCode(ResultCode.SUCCESS);
                result.setMsg("新增成功");
                return result;
            }catch(Exception e){
                LOGGER.error("新增订单发货商品表异常:{}",e);
                return Result.newExceptionInstance();
            }

    }
}
