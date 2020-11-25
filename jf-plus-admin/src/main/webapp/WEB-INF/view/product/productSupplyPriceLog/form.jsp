<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title></title>
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
                            <div class="widget-title am-fl"></div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="productSupplyPriceLog" action="${ctx}/product/productSupplyPriceLog/<c:choose><c:when test="${empty productSupplyPriceLog.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${productSupplyPriceLog.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应商Id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyId" placeholder="供应商Id" value="${productSupplyPriceLog.supplyId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品渠道来源（1京东，2供应商，3苏宁，4严选，5齐心）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="source" placeholder="商品渠道来源（1京东，2供应商，3苏宁，4严选，5齐心）" value="${productSupplyPriceLog.source}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品编号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemCode" placeholder="商品编号" value="${productSupplyPriceLog.itemCode}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">市场价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="markPrice" placeholder="市场价" value="${productSupplyPriceLog.markPrice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">最新供应价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyPrice" placeholder="最新供应价" value="${productSupplyPriceLog.supplyPrice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">执行状态(1未执行，2已执行)：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operStatus" placeholder="执行状态(1未执行，2已执行)" value="${productSupplyPriceLog.operStatus}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">执行时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operTime" placeholder="执行时间" value="${productSupplyPriceLog.operTime}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${productSupplyPriceLog.remarks}"/>
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
