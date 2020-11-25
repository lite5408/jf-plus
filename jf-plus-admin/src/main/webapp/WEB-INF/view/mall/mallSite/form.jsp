<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商城站点表</title>
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
                            <div class="widget-title am-fl">商城站点</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="mallSite" action="${ctx}/mall/mallSite/<c:choose><c:when test="${empty mallSite.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${mallSite.id}"/>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>站点类型：</label>
                                        <div class="am-u-sm-9">
                                            <select name="siteType" data="${mallSite.siteType }">
												<c:forEach items="<%=SiteType.values()%>" var="val">
													<option value="${val.type }">${val.description }</option>
												</c:forEach>
											</select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>站点名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="siteName" placeholder="站点名称" value="${mallSite.siteName}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>货币单位：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="cashUnit" placeholder="货币单位" value="${mallSite.cashUnit}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>站点管理员：</label>
                                        <div class="am-u-sm-9">
                                            <select data-am-selected="{searchBox: 1}" name="siteUserIds" data="true" multiple required>
												<c:forEach items="${userList}" var="m">
													<option value="${m.id}" <c:if test="${fnc:inStr(mallSite.siteUserIds, m.id)}">selected</c:if>>${m.no}${m.name}</option>
												</c:forEach>
											</select>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">管理员名称集合：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="siteUserNames" placeholder="管理员名称集合" value="${mallSite.siteUserNames}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>支付方式配置：</label>
                                        <div class="am-u-sm-9">
							              	<div class="am-form-group">
							              		<c:forEach var="payWay" items="<%=PayWayConf.values()%>">
							              		<label class="am-checkbox-inline"><input name="paywayConf" type="checkbox" value="${payWay.type }" <c:if test="${fn:indexOf(mallSite.paywayConf, payWay.type) != -1}">checked="checked"</c:if> required> ${payWay.description }</label>
							              		</c:forEach>
							              	</div>
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
        initSelectValue(true);//初始化下拉框的值
    });
</script>
</body>
</html>
