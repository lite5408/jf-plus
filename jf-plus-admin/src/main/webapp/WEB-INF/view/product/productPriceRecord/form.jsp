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
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="productPriceRecord" action="${ctx}/product/productPriceRecord/<c:choose><c:when test="${empty productPriceRecord.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${productPriceRecord.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品Id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="productId" placeholder="商品Id" value="${productPriceRecord.productId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商城规则配置记录Id(价格变更记录0)：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="ruleRecordId" placeholder="商城规则配置记录Id(价格变更记录0)" value="${productPriceRecord.ruleRecordId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">记录变更来源（1.商城配置修改2.接口供应价修改）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="recordSource" placeholder="记录变更来源（1.商城配置修改2.接口供应价修改）" value="${productPriceRecord.recordSource}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">修改前供应价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="preSupplyPrice" placeholder="修改前供应价" value="${productPriceRecord.preSupplyPrice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">修改后供应价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sufSupplyPrice" placeholder="修改后供应价" value="${productPriceRecord.sufSupplyPrice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">执行状态(1未执行，2已执行)：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operStatus" placeholder="执行状态(1未执行，2已执行)" value="${productPriceRecord.operStatus}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">执行时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operTime" placeholder="执行时间" value="${productPriceRecord.operTime}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${productPriceRecord.remarks}"/>
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
