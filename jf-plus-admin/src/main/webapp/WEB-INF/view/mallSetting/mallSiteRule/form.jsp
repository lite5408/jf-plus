<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商城分销配置表</title>
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
                            <div class="widget-title am-fl">商城分销配置表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="mallSiteRule" action="${ctx}/mallSetting/mallSiteRule/<c:choose><c:when test="${empty mallSiteRule.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${mallSiteRule.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgId" placeholder="" value="${mallSiteRule.orgId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分销类型(1:按折扣 2:按固定值)：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distributeType" placeholder="分销类型(1:按折扣 2:按固定值)" value="${mallSiteRule.distributeType}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">值：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distributeValue" placeholder="值" value="${mallSiteRule.distributeValue}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">是否允许超出市场价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="allowExceedMarketPrice" placeholder="是否允许超出市场价" value="${mallSiteRule.allowExceedMarketPrice}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">超出市场价比例：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="exceedRatio" placeholder="超出市场价比例" value="${mallSiteRule.exceedRatio}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品渠道(1:京东  2供应商  3苏宁 4严选 5齐心)：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="productSource" placeholder="商品渠道(1:京东  2供应商  3苏宁 4严选 5齐心)" value="${mallSiteRule.productSource}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">积分比例：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="ratio" placeholder="积分比例" value="${mallSiteRule.ratio}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${mallSiteRule.remarks}"/>
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
