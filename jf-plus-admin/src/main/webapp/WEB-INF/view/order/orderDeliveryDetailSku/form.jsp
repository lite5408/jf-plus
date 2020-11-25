<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单发货SKU表</title>
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
                            <div class="widget-title am-fl">订单发货SKU表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orderDeliveryDetailSku" action="${ctx}/order/orderDeliveryDetailSku/<c:choose><c:when test="${empty orderDeliveryDetailSku.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${orderDeliveryDetailSku.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderId" placeholder="订单id" value="${orderDeliveryDetailSku.orderId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单编号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderNo" placeholder="订单编号" value="${orderDeliveryDetailSku.orderNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单发货id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryId" placeholder="订单发货id" value="${orderDeliveryDetailSku.deliveryId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单明细id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderDetailId" placeholder="订单明细id" value="${orderDeliveryDetailSku.orderDetailId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">站点商品ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryDetailId" placeholder="站点商品ID" value="${orderDeliveryDetailSku.deliveryDetailId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单商品SKU_ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderDetailSkuId" placeholder="订单商品SKU_ID" value="${orderDeliveryDetailSku.orderDetailSkuId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发货数量：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryNum" placeholder="发货数量" value="${orderDeliveryDetailSku.deliveryNum}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${orderDeliveryDetailSku.remarks}"/>
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
