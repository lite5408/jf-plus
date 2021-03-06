<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>站点商品表</title>
    <%@ include file="../../include/head.jsp"%>
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
                            <div class="widget-title am-fl">站点商品表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="siteProduct" action="${ctx}/site/siteProduct/<c:choose><c:when test="${empty siteProduct.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${siteProduct.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="productId" placeholder="" value="${siteProduct.productId}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="productNo" placeholder="" value="${siteProduct.productNo}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">组织id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgId" placeholder="组织id" value="${siteProduct.orgId}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">站点id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="siteId" placeholder="站点id" value="${siteProduct.siteId}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyPrice" placeholder="供应价" value="${siteProduct.supplyPrice}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">市场价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="markPrice" placeholder="市场价" value="${siteProduct.markPrice}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="salePrice" placeholder="销售价" value="${siteProduct.salePrice}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">利润比：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="profitPercent" placeholder="利润比" value="${siteProduct.profitPercent}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品渠道： 1:京东商品 2：供应商商品：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="source" placeholder="商品渠道： 1:京东商品 2：供应商商品" value="${siteProduct.source}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${siteProduct.remarks}" required/>
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
<%@ include file="../../include/bottom.jsp"%>
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
