<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商品详情</title>
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
                            <div class="widget-title am-fl">商品详情</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" method="post">
                            <input type="hidden" name="id" value="${product.productId}"/>
                            		<div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">封面图：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                            <img src="${fn:split(product.photoUrl, ',')[0]}" width="140" height="140" />
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品编码：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">${product.itemCode}</div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品名称：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">${product.itemName}</div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">类型：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">${product.saleType}</div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应商：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">${product.supplyName}</div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">品牌：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">${product.brand}</div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">货源地：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">${product.area}</div>
                                    </div>
                                     <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">截止日期：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left"><fmt:formatDate value="${product.endDate}" pattern="yyyy-MM-dd HH:mm"/></div>
                                    </div>
                                    <c:if test="${product.saleType eq '正价' }">
                                    	<div class="am-form-group">
	                                        <label class="am-u-sm-3 am-form-label">预计出货时间：</label>
	                                        <div class="am-u-sm-9 am-form-label am-text-left"><fmt:formatDate value="${product.shipmentDate}" pattern="yyyy-MM-dd"/></div>
	                                    </div>
	                                    <div class="am-form-group">
	                                        <label class="am-u-sm-3 am-form-label">规格：</label>
	                                        <div class="am-u-sm-9 am-form-label am-text-left">
	                                        	<table class="am-table am-table-compact am-table-bordered">
	                                        		<c:forEach items="${product.skus }" var="sku">
	                                        			<tr><td>${sku.specColorText }</td><td>${sku.specSizeText }</td></tr>
	                                        		</c:forEach>
	                                        	</table>
	                                        </div>
	                                    </div>
                                    </c:if>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品属性：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                        	<table class="am-table am-table-compact am-table-bordered">
                                        		<tr><td>年份</td><td>${product.yearth }</td></tr>
                                        		<tr><td>季节</td><td>${product.season }</td></tr>
                                        		<c:forEach items="${product.attrs }" var="attr">
                                        		<tr><td>${attr.attrInfo }</td><td>${attr.attrText }</td></tr>
                                        		</c:forEach>
                                        	</table>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">详情：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">${product.content}</div>
                                    </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="button" class="am-btn am-btn-xs am-btn-primary" onclick="editDetail(${product.productId})">编辑详情</button>
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
    
    function editDetail(id){
    	location.href="${ctx}/product/toEditDetailPage?id="+id;
    }
</script>
</body>
</html>
