<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单发货表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">订单发货表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orderDelivery" action="${ctx}/order/orderDelivery/<c:choose><c:when test="${empty orderDelivery.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${orderDelivery.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderId" placeholder="订单id" value="${orderDelivery.orderId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单编号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderNo" placeholder="订单编号" value="${orderDelivery.orderNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发货类型（1：部分发货 2：发货）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryType" placeholder="发货类型（1：部分发货 2：发货）" value="${orderDelivery.deliveryType}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发货时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryDate" placeholder="发货时间" value="${orderDelivery.deliveryDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发货员：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryOperator" placeholder="发货员" value="${orderDelivery.deliveryOperator}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">仓库：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryStore" placeholder="仓库" value="${orderDelivery.deliveryStore}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发货方式：1：快递 2：自配：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryExpressType" placeholder="发货方式：1：快递 2：自配" value="${orderDelivery.deliveryExpressType}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">快递公司/配送员：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryExpress" placeholder="快递公司/配送员" value="${orderDelivery.deliveryExpress}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">快递单号/配送员电话：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryExpressNo" placeholder="快递单号/配送员电话" value="${orderDelivery.deliveryExpressNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">导出状态：0未导出 1已导出：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operStatus" placeholder="导出状态：0未导出 1已导出" value="${orderDelivery.operStatus}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${orderDelivery.remarks}"/>
                                        </div>
                                    </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="submit" class="am-btn am-btn-xs am-btn-primary">保存</button>
                                    <button type="button" class="am-btn am-btn-xs am-btn-danger" onclick="closeModel(false)">关闭</button>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
    });
</script>
</body>
</html>
