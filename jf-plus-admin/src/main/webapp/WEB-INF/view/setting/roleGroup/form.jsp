<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>角色组</title>
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
                            <div class="widget-title am-fl">角色组</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="roleGroup" action="${ctx}/sys/roleGroup/<c:choose><c:when test="${empty roleGroup.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${roleGroup.id}"/>
                            <input type="hidden" name="orgId" value="${loginUser.organizationId}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>角色组名称：</label>
                                        <div class="am-u-sm-9 am-text-left">
                                            <input type="text" name="groupName" placeholder="角色组名称" value="${roleGroup.groupName}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>角色权限：</label>
                                        <div class="am-u-sm-9">
	                                        <div class="am-panel am-panel-default">
											    <div class="am-panel-bd">
											    	<c:forEach items="${myRoleList }" var="item">
												    	<label class="am-checkbox-inline">
												        	<input type="checkbox" name="groupRoleIds" value="${item.id }" data-am-ucheck 
												        	<c:if test="${fnc:inStr(roleGroup.groupRoleIds,item.id) }">checked</c:if>
												        	> ${item.name }
												      	</label>
											      	</c:forEach>
											      </div>
											</div>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
	                                    <label class="am-u-sm-3 am-form-label">是否可用：</label>
	                                    <div class="am-u-sm-9">
	                                        <select name="available" data="${roleGroup.available}">
	                                            <option value="1">可用</option>
	                                            <option value="0">禁用</option>
	                                        </select>
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
        
        initSelectValue(true)
    });
</script>
</body>
</html>
