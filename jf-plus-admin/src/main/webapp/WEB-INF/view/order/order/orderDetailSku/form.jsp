<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单明细表</title>
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
                            <div class="widget-title am-fl">订单明细表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orderDetailSku" action="${ctx}/order/orderDetailSku/<c:choose><c:when test="${empty orderDetailSku.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${orderDetailSku.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderId" placeholder="订单ID" value="${orderDetailSku.orderId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderNo" placeholder="订单号" value="${orderDetailSku.orderNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">订单子单号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderSubno" placeholder="订单子单号" value="${orderDetailSku.orderSubno}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品详细表ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orderDetailId" placeholder="商品详细表ID" value="${orderDetailSku.orderDetailId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">站点商品id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemId" placeholder="站点商品id" value="${orderDetailSku.itemId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemName" placeholder="" value="${orderDetailSku.itemName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应商id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyId" placeholder="供应商id" value="${orderDetailSku.supplyId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyPrice" placeholder="供应价" value="${orderDetailSku.supplyPrice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="salePrice" placeholder="销售价" value="${orderDetailSku.salePrice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售数量：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="saleNum" placeholder="销售数量" value="${orderDetailSku.saleNum}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售金额：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="amount" placeholder="销售金额" value="${orderDetailSku.amount}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售积分价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="salePoints" placeholder="销售积分价" value="${orderDetailSku.salePoints}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">冻结库存量：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="freezeNum" placeholder="冻结库存量" value="${orderDetailSku.freezeNum}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">SKU ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="skuId" placeholder="SKU ID" value="${orderDetailSku.skuId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${orderDetailSku.remarks}"/>
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
