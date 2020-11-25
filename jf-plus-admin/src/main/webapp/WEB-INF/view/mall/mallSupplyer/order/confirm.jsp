<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>供应商</title>
    <%@ include file="../../../include/head.jsp"%>
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
                        <div class="widget-body am-fr">
                            <div class="am-g error-log">
						        <div class="am-u-sm-12">
						        <pre class="">
						        	<c:if test="${empty order}">
						        		该订单没有签收信息
						        	</c:if>
						        	<c:if test="${not empty order}">
						        		<span class="am-text-success">签收时间：[<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${order.receiptDate }' />]</span>
						        		<c:if test="${not empty order.receiptOpertor}">
							        		<span class="am-text-success">签收信息：${ order.receiptOpertor}</span>
						        		</c:if>
						        		<c:if test="${empty order.receiptOpertor}">
							        		该订单没有签收信息
							        	</c:if>
						        	</c:if>
						        </pre>
						        </div>
						      </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
