<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单发货商品表</title>
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
                            <div class="widget-title am-fl">订单发货商品表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orderDeliveryDetail" action="${ctx}/order/orderDeliveryDetail/<c:choose><c:when test="${empty orderDeliveryDetail.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${orderDeliveryDetail.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderId" placeholder="订单id" value="${orderDeliveryDetail.orderId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单编号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderNo" placeholder="订单编号" value="${orderDeliveryDetail.orderNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单发货id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryId" placeholder="订单发货id" value="${orderDeliveryDetail.deliveryId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单明细id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderDetailId" placeholder="订单明细id" value="${orderDeliveryDetail.orderDetailId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">站点商品ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemId" placeholder="站点商品ID" value="${orderDeliveryDetail.itemId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemName" placeholder="商品名称" value="${orderDeliveryDetail.itemName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应商ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyId" placeholder="供应商ID" value="${orderDeliveryDetail.supplyId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应商名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyName" placeholder="供应商名称" value="${orderDeliveryDetail.supplyName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发货数量：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="deliveryNum" placeholder="发货数量" value="${orderDeliveryDetail.deliveryNum}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${orderDeliveryDetail.remarks}"/>
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
