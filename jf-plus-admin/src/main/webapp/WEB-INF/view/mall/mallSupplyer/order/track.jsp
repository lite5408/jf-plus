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
						        	<c:if test="${empty orderDistrib}">
						        		该订单没有配送信息
						        	</c:if>
						        	<c:if test="${not empty orderDistrib}">
						        		<span class="am-text-success">发货日期：[<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${orderDistrib.deliveryTime }' />]</span>
						        		<c:if test="${ orderDistrib.type==0}">
							        		<span class="am-text-success">配送方式：自配</span>
							        		<span class="am-text-success">配送员：${ orderDistrib.delivery}</span>
							        		<span class="am-text-success">联系方式：${ orderDistrib.deliveryMobile}</span>
						        		</c:if>
						        		<c:if test="${ orderDistrib.type==1}">
							        		<span class="am-text-success">配送方式：快递</span>
							        		<span class="am-text-success">快递公司：${ orderDistrib.express}</span>
							        		<span class="am-text-success">快递单号：${ orderDistrib.expressNo}</span>
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
