<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>组织机构编辑</title>
	<%@ include file="../../include/head.jsp"%>
	<style type="text/css">
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
							<div class="widget-title am-fl">机构新增</div>
						</div>
					      	<div class="widget-body am-fr">
								<form class="am-form tpl-form-border-form" modelAttribute="organization" action="${ctx}/organization/create" method="post">
									<input type="hidden" name="parentId" value="${organization.id}" />
									<input type="hidden" name="parentIds" value="${organization.parentIds}${organization.id}/" />
									<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label">上级机构：</label>
										<div class="am-u-sm-9">
											<input type="text" id="parentName" value="${organization.name}" readonly/>
										</div>
									</div>
									<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label"><span class="error">*</span>机构编码：</label>
										<div class="am-u-sm-9">
											<input type="text" minlength="2" name="no" placeholder="机构编码（至少2个字符）" required unique/>
										</div>
									</div>
									<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label"><span class="error">*</span>机构名称：</label>
										<div class="am-u-sm-9">
											<input type="text" minlength="2" name="name" placeholder="机构名称（至少2个字符）" required />
										</div>
									</div>
									<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label">机构类型：</label>
										<div class="am-u-sm-9">
											<select name="type" data="${organization.type}" required>
												<c:forEach items="<%=OrgType.values()%>" var="val">
													<c:if test="${val.type gt loginUser.organization.type }">
													<option value="${val.type }">${val.description }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="am-form-group" role="sale_area_div" style="display:none;">
										<label class="am-u-sm-3 am-form-label">所属销售区域：</label>
										<div class="am-u-sm-9">
											<select name="saleAreas" data="${organization.saleAreas}" data-am-selected="{searchBox: 1}" multiple>
												<c:forEach items="${saleAreaList }" var="item">
													<option value="${item.id }">${item.val }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label">是否可用：</label>
										<div class="am-u-sm-9">
											<select name="available" data="true" required>
												<option value="true">可用</option>
												<option value="false">禁用</option>
											</select>
										</div>
									</div>
									<div class="am-form-group">
										<div class="am-u-sm-9 am-u-sm-push-3">
											<shiro:hasPermission name="sys:organization:edit"><button id="submitFroms" class="am-btn am-btn-xs am-btn-primary">保存</button></shiro:hasPermission>
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
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
	$(function () {
		//消息提醒
		var msg = '${msg}';
		if(msg!=''){
			showMsg(msg);
			closeModel(true);//关闭窗口
		}
		initSelectValue(true);//初始化下拉框的值
		
	});
	
	
	$('[unique]').not('[readonly]').blur(function(){
		if(isNull($(this).val()))
    		return true;
		
		$.ajaxSetup({aysnc:false});
		post("${ctx}/organization/unique",{id:null,no:$(this).val()},function(result){
			if(result.obj >= 1){
				alert('机构编码重复，请重新输入');
				$('[unique]').val('');
				return false;
			}else{
				return true;
			}
		});
	});
	
	
	$('[name=type]').change(function(){
		if($(this).val() == 4){
			$('[role=sale_area_div]').show();
		}else{
			$('[role=sale_area_div]').val('').hide();
		}
	})
	
</script>
</body>
</html>
