<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>组织资金充值</title>
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
                            <div class="widget-title am-fl">资金充值</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orgAccount" action="${ctx}/account/orgAccount/recharge" method="post">
                            <input type="hidden" name="id" value="${orgAccount.id}"/>
                            <input type="hidden" name="organizationId" value="${orgAccount.organizationId}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">采购余额充值：</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="purchaseBlance" placeholder="采购余额（0代表不充值）" value="0" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">积分余额充值：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="pointsBlance" placeholder="积分余额（0代表不充值）" value="0"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value=""/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
										<label class="am-u-sm-3 am-form-label">附件：</label>
										<div class="am-u-sm-9">
											<div class="am-form-group am-form-file">
										    	<button type="button" id="uploadFile" class="am-btn am-btn-danger am-btn-sm">
											    <i class="am-icon-cloud-upload"></i> 选择附件</button>
											    <input id="file" name="file" type="file" multiple>
											    <input type="hidden" name="attachments" id="attachments" />
											</div>
											<div id="file-list"></div>
										</div>
									</div>
		                            <div class="am-form-group">
		                                <div class="am-u-sm-9 am-u-sm-push-3">
		                                    <button type="submit" class="am-btn am-btn-xs am-btn-primary">充值</button>
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
<script type="text/javascript" src="${ctxStatic}/custom/js/ajaxfileupload.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
      //------------------- 上传图片 ----------------------//
        $("#uploadFile").click(function(){
			$("#file").click();
		});
		$("#file").change(function(){
			ajaxUpload();
		});
		var ajaxUpload = function(){
			$.ajaxFileUpload({
				url: '${ctx}/upload/local',
				type: 'post',
				secureuri: false,
				fileElementId: 'file',
				dataType: 'text',
				success: function (data, status){
					data = JSON.parse(delHtmlTag(data));
					if(data.ret==1){
						$('#file-list').html(data.data);
						$("#attachments").val(data.data);
					}else{
						alert(data.msg);
					}
				},
				complete: function(xmlHttpRequest) {  
					$('#file').replaceWith($('#file').clone(true));
					$('#file').on('change',function(){
						ajaxUpload();
					})  
	            },
				error: function (data, status, e){
					alert("上传失败");
				}
			});
		}
		//------------------- 上传图片 ----------------------//
        
    });
</script>
</body>
</html>
