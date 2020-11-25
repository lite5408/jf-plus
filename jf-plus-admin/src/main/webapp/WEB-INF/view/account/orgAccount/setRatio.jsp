<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>积分比例设置表</title>
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
                            <div class="widget-title am-fl">商城积分比例配置</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="organization" 
                            		action="${ctx}/account/orgAccount/changeRetio" method="post">
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">机构：</label>
                                        <div class="am-u-sm-9">
                                        	<select name="orgId" data-am-selected="{searchBox: 1}" data="true" id="orgId" required>
                                        		<option value="">--请选择--</option>
                                        		<c:forEach items="${organizationList}" var="organization" varStatus="status">
													<option value="${organization.id}">${organization.no}_${organization.name}</option>
                                        		</c:forEach>
											</select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"></label>
                                        <div class="am-u-sm-9">
                                            <input type="checkbox" name="setAll" value="1"/>统一设置
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">积分比例：</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="marketRatio" id="marketRatio" placeholder="积分比例" required/>
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
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
        $("#marketRatio").blur(function(){
       		var r = /^\+?[1-9][0-9]*$/;
        	if (!r.test($("#marketRatio").val())) {
        		$("#marketRatio").val(null);
        		alert("积分比例必须为正整数");
			}
        })
        
        $("#orgId").change(function(){
        	var orgId = $("#orgId").val();
        	$.post("${ctx}/account/orgAccount/getRatio",{orgId:orgId},function(result){
        		if(result.success){
        			$("#marketRatio").val(result.obj);
        		}else{
        			alert(result.msg);
        		}
        	});
        });
        
        initSelectValue(true);//初始化下拉框的值
    });
</script>
</body>
</html>
