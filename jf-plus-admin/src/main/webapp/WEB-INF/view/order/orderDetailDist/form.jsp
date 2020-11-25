<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单分配表</title>
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
                            <div class="widget-title am-fl">订单分配表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orderDetailDist" action="${ctx}/order/orderDetailDist/<c:choose><c:when test="${empty orderDetailDist.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${orderDetailDist.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderId" placeholder="订单ID" value="${orderDetailDist.orderId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderNo" placeholder="订单号" value="${orderDetailDist.orderNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单子单号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderSubno" placeholder="订单子单号" value="${orderDetailDist.orderSubno}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">站点商品id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemId" placeholder="站点商品id" value="${orderDetailDist.itemId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemName" placeholder="" value="${orderDetailDist.itemName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单数量：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="saleNum" placeholder="订单数量" value="${orderDetailDist.saleNum}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单明细状态：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operStatus" placeholder="订单明细状态" value="${orderDetailDist.operStatus}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">是否分发：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="isDist" placeholder="是否分发" value="${orderDetailDist.isDist}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分发总库存：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distStock" placeholder="分发总库存" value="${orderDetailDist.distStock}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分发数量：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distNum" placeholder="分发数量" value="${orderDetailDist.distNum}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分发操作用户：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distOperator" placeholder="分发操作用户" value="${orderDetailDist.distOperator}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分发时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distDate" placeholder="分发时间" value="${orderDetailDist.distDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">批次号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="batchNo" placeholder="批次号" value="${orderDetailDist.batchNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${orderDetailDist.remarks}"/>
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
