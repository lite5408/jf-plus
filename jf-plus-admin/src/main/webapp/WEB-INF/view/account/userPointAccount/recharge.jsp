<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>余额充值</title>
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
                            <div class="widget-title am-fl">额度互拨</div>
                        </div>
				    	<div class="widget-body am-fr">
                        	<form class="am-form tpl-form-border-form" data-am-validator modelAttribute="user" action="${ctx}/account/userPointAccount/recharge" method="post">
	                            <input type="hidden" name="bindUser" value="${user.id}"/>
	                            <input type="hidden" name="orgId" value="${user.organizationId}"/>
	                            <div class="am-form-group">
									<label class="am-u-sm-3 am-form-label">互拨类型：</label>
									<div class="am-u-sm-9">
										<select name="changeType" data="">
											<option value="1">集采额度转换为分发额度</option>
											<option value="2">分发额度转换为集采额度</option>
										</select>
									</div>
								</div>
                               	<div class="am-form-group">
                              		<label class="am-u-sm-3 am-form-label">转换值：</label>
                                	<div class="am-u-sm-9">
                                		<input type="number" name="totalBlance" id="totalBlance" required/>
                               		</div>
                               	</div>
	                           	<div class="am-form-group">
	                            	<div class="am-u-sm-9 am-u-sm-push-3">
	                                	<button type="submit" class="am-btn am-btn-xs am-btn-primary">转换</button>
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
        
        $("#totalBlance").blur(function(){
        	if($("#totalBlance").val() < 0){
        		alert("转换值不能为负数！");
        		$("#totalBlance").val(null);
        	}
        });
    });
</script>
</body>
</html>
