<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title><c:set var="iutilsName" value='${fnc:getConfig("iutils.name")}' />${iutilsName} - 首页</title>
	<%@ include file="/WEB-INF/view/include/head.jsp"%>
	<style>
		.tpl-content-wrapper{margin-left:0}
		.widget-body{padding: 13px 20px;}
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
                            <div class="widget-title am-fl">供应商信息</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="mallSupplyer" action="${ctx}/mall/mallSupplyer/<c:choose><c:when test="${empty mallSupplyer.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${mallSupplyer.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">公司名称：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                            ${mallSupplyer.companyName}
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">公司简称：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                            ${mallSupplyer.companyTitle}
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">开户时间：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                            <fmt:formatDate value="${mallSupplyer.regTime}" pattern="yyyy-MM-dd" />
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">联系人：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                            ${mallSupplyer.contact}
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">联系方式：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                            ${mallSupplyer.contactLink}
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">管理员账号：</label>
                                        <div class="am-u-sm-9 am-form-label am-text-left">
                                            ${mallSupplyer.adminLoginname}
                                        </div>
                                    </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3" style="display:none;">
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
</body>
</html>