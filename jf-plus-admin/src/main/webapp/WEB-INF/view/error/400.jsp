<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
	String path = request.getContextPath();
%>
<html>
<head>
	<title>系统提示 - 400</title>
	<%@ include file="../include/head.jsp"%>
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
			<div class="widget am-cf">
				<div class="widget-body">
					<div class="tpl-page-state">
						<div class="tpl-page-state-title am-text-center tpl-error-title">400</div>
						<div class="tpl-error-title-info">系统提示</div>
						<div class="tpl-page-state-content tpl-error-content">
							<p>${errorMsg }</p>
							<button type="button" class="am-btn am-btn-secondary am-radius tpl-error-btn" onclick="history.go(-1)'">返回</button></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="../include/bottom.jsp"%>
</body>
</html>