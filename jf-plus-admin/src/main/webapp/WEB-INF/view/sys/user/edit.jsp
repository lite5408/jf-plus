<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../include/head.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/css/zTreeStyle.css">
    <style>
		ul.ztree {
			margin-top: 10px;
			border: 1px solid #ddd;
			background: #fff;
			width: 198px;
			height: 200px;
			overflow-y: auto;
			overflow-x: auto;
		}
		.tpl-content-wrapper{margin-left:0}
		.am-selected{width: 100%;}
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
							<div class="widget-title am-fl">用户信息</div>
						</div>
						<div class="widget-body am-fr">
							<form class="am-form tpl-form-border-form" data-am-validator modelAttribute="user" action="${ctx}/user/<c:choose><c:when test="${empty user.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
								<input type="hidden" name="id" value="${user.id}" />
								<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>编码：</label>
									<div class="am-u-sm-9">
										<input type="text" name="no" minlength="3" placeholder="编码（至少3个字符）"
											   value="${user.no}" ${empty user.no?'':'readonly'} required unique/>
									</div>
								</div>
								<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>账号：</label>
									<div class="am-u-sm-9">
										<input type="text" name="username" minlength="3" placeholder="账号（至少3个字符）"
											   value="${user.username}" ${empty user.id?'':'readonly'} required unique/>
									</div>
								</div>
								<c:if test="${empty user.id}">
									<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label"><span class="error">*</span>密码：</label>
										<div class="am-u-sm-9">
											<input type="password" name="password" minlength="6" placeholder="密码（至少6个字符）"
												   value="${user.password}" required />
										</div>
									</div>
								</c:if>
								<div class="am-form-group" style="display:none">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>是否部门管理员：</label>
									<div class="am-u-sm-5">
										<select name="isDept" data="${user.isDept}">
											<option value="false">否</option>
											<option value="true">是</option>
										</select>
									</div>
									<label class="am-u-sm-4"></label>
								</div>
								<div class="am-form-group" style="display:none">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>用户类型：</label>
									<div class="am-u-sm-5">
										<select name="type" data="${user.type}">
											<c:forEach items="<%=UserType.values()%>" var="val">
												<option value="${val.type }">${val.description }</option>
											</c:forEach>
										</select>
									</div>
									<label class="am-u-sm-4"></label>
								</div>
								<div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>所属机构：</label>
                                    <div class="am-u-sm-9">
                                    	<div class="am-form-group">
		                                    <select data-am-selected="{searchBox: 1,maxHeight:120}" data="true" name="organizationIds" id="selectOrg" placeholder="请输入编码或者名称筛选" required>
											  
											</select>
										</div>
									</div>
                                </div>
								<div class="am-form-group" style="display:none;">
                                    <label class="am-u-sm-3 am-form-label">&nbsp;</label>
                                    <div class="am-u-sm-9">
                                        <input type="hidden" id="organizationIds1" name="organizationIds1" value='${user.organizationIds }' />
                                        <ul id="treeOrg" class="ztree" style="margin-top:0; width:auto;height: auto;"></ul>
                                    </div>
                                </div>
                                <div class="am-form-group">
                                     <label class="am-u-sm-3 am-form-label"><span class="error">*</span>角色组：</label>
                                     <div class="am-u-sm-9">
                                      <div class="am-panel am-panel-default">
										    <div class="am-panel-bd" id="myRoleGroupList">
										    	
										      </div>
										</div>
                                     </div>
                                </div>
								<div class="am-form-group" style="display:none;">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>角色列表：</label>
									<div class="am-u-sm-9">
										<select name="roleIds" data="<c:forEach items="${user.roleIds}" var="item">${item},</c:forEach>" multiple>
											<c:forEach items="${roleList}" var="m">
												<option value="${m.id}">${m.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>姓名：</label>
									<div class="am-u-sm-9">
										<input type="text" name="name" placeholder="姓名" value="${user.name}" required/>
									</div>
								</div>
								<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>电话：</label>
									<div class="am-u-sm-9">
										<input type="text" name="phone" placeholder="电话" value="${user.phone}" required/>
									</div>
								</div>
								<div class="am-form-group" style="display:none">
									<label class="am-u-sm-3 am-form-label">邮箱：</label>
									<div class="am-u-sm-9">
										<input type="text" name="email" placeholder="邮箱" value="${user.email}" />
									</div>
								</div>
								<div class="am-form-group" style="display:none">
									<label class="am-u-sm-3 am-form-label">手机：</label>
									<div class="am-u-sm-9">
										<input type="text" name="mobile" placeholder="手机" value="${user.mobile}" />
									</div>
								</div>
								<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label">状态：</label>
									<div class="am-u-sm-5">
										<select name="locked" data="${user.locked}">
											<option value="false">在职</option>
											<option value="true">离职</option>
										</select>
									</div>
									<label class="am-u-sm-4"></label>
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
<script src="${ctxStatic}/3rd-lib/jquery-ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctxStatic}/custom/js/ztree.select.js"></script>
<script src="${ctxStatic}/custom/js/ztree.org.js"></script>
<script>
	$(document).ready(function() {
		//消息提醒
		var msg = '${msg}';
		if(msg!=''){
			showMsg(msg);
			if(msg=="保存成功"){
				closeModel(true);//关闭窗口
			}
		}
		initSelectValue(true);//初始化下拉框的值
	});
	$(function () {
		var zNodes =[
			<c:forEach items="${organizationList}" var="o" varStatus="status">
				<c:if test="${not o.rootNode}">
					{ id:${o.id}, pId:${o.parentId}, no:"${o.no}",name:"${o.name}",checked:${fnc:inStr(user.organizationIds, o.id)},open:false}<c:if test="${!status.last}">,</c:if>
				</c:if>
			</c:forEach>
		];
		$.fn.ztreeOrg($("#treeOrg"),zNodes);
		
		for(var i =0 ;i < zNodes.length ; i ++){
			if(zNodes[i].checked){
				$('#selectOrg').append('<option value="' + zNodes[i].id + '" selected>' + zNodes[i].no+zNodes[i].name + "</option>");
			}else{
				$('#selectOrg').append('<option value="' + zNodes[i].id + '">' + zNodes[i].no+zNodes[i].name + "</option>");
			}
		}
		
		
		//展开2级
        var treeObj = $.fn.zTree.getZTreeObj("treeOrg");
        var nodes = treeObj.getNodes();
        for (var i = 0; i < nodes.length; i++) { //设置节点展开
            treeObj.expandNode(nodes[i], true, false, true);
        }
        
        
        loadRoleGroupList();
        
	});

    //ztree机构点击回调
    function ztreeOrgOnClickCall(id){
        $("#organizationIds1").val(id);
    }
    
    function loadRoleGroupList(){
    	get('${ctx}/sys/roleGroup/getMyRoleGroupList',function(result){
    		if(result.success){
    			roleGroupList = result.obj;
    			var shtml = "";
    			for(var i = 0 ; i < roleGroupList.length ; i++){
    				shtml += '<label class="am-checkbox-inline">';
    				shtml += '<input type="checkbox" name="roleGroupIds" value="'+ roleGroupList[i].id +'" data-am-ucheck ';
    				checked = ',${user.roleGroupIds},'.indexOf(','+roleGroupList[i].id+',') != -1;
    				shtml += checked ? 'checked':'';
    				shtml += '> '+ roleGroupList[i].groupName +' ';
    				shtml += '</label> ';
    			}
    			$('#myRoleGroupList').append(shtml);
    		}
    	})
    }
    
    function onUserSubmitFun(){
    	var orgs = $('#selectOrg').val();
    	if(orgs != '')
    		$("#organizationIds1").val(orgs);
    }
    
    $('[unique]').not('[readonly]').blur(function(){
    	if(isNull($(this).val()))
    		return true;
    	
    	var columnName = $(this).attr('name');
    	var postdata = {};
    	postdata[columnName] = $(this).val();
    	postdata["id"] = '${user.id}';
    	
		$.ajaxSetup({aysnc:false});
		post("${ctx}/user/unique",postdata,function(result){
			if(result.obj >= 1){
				alert('编码重复，请重新输入');
				$('[unique]').val('');
				return false;
			}else{
				return true;
			}
		});
	});
</script>

</body>
</html>