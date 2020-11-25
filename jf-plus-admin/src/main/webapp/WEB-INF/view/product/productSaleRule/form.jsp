<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>销售规则</title>
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
                            <div class="widget-title am-fl">销售规则</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="productSaleRule" action="${ctx}/product/productSaleRule/<c:choose><c:when test="${empty productSaleRule.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${productSaleRule.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="productId" placeholder="商品ID" value="${productSaleRule.productId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">冗余销售类型：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="saleType" placeholder="冗余销售类型" value="${productSaleRule.saleType}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">出货时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="shipmentDate" placeholder="出货时间" value="${productSaleRule.shipmentDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">限制库存：1限制 -1不限制：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="limitStock" placeholder="限制库存：1限制 -1不限制" value="${productSaleRule.limitStock}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售提醒值：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="saleNotice" placeholder="销售提醒值" value="${productSaleRule.saleNotice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售提醒单位，% 百分比，数字代表件：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="saleNoticeUnit" placeholder="销售提醒单位，% 百分比，数字代表件" value="${productSaleRule.saleNoticeUnit}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">截止时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="endDate" placeholder="截止时间" value="${productSaleRule.endDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">代理商分组：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgGroups" placeholder="代理商分组" value="${productSaleRule.orgGroups}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${productSaleRule.remarks}"/>
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
