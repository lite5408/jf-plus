<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>对账订单明细表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/3rd-lib/jqueryStep/css/jquery.step.css" />
<script src="${ctxStatic}/3rd-lib/jqueryStep/js/jquery.step.min.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div id="step"></div>
                        </div>
                        <div class="widget-body am-fr">
                            <form id="dataForm" class="am-form tpl-form-border-form" onsubmit="return validFun()" data-am-validator action="${ctx}/order/financeOrder/importOrder" method="post">
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>供应商：</label>
                                        <div class="am-u-sm-9">
                                            <select data-am-selected="{searchBox: 1}" data="true" name="supplyId" id="selectSupply" required>
											  <c:forEach items="${mallSupplyerList }" var="item">
											  	<option value="${item.id }">${item.companyName }</option>
											  </c:forEach>
											</select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>上传订单：</label>
                                        <div class="am-u-sm-9">
                                        	<div class="am-form-group am-form-file">
                                            	<button type="button" id="uploadPic" class="am-btn am-btn-xs am-btn-primary am-btn-xs ">
													<i class="am-icon-cloud-upload"></i> 选择文件</button>
												<input type="file" name="file" id="file" accept="application/vnd.ms-excel">
												<input type="hidden" name="uploadFile" id="photo" value="" required/>
                                        	</div>
											<a class="am-link am-text-sm" id=showPic href="" class="am-btn"></a>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-3">
                                            <input type="text" name="remarks" placeholder="备注" value="${financeOrder.remarks}"/>
                                        </div>
                                        <div class="am-u-sm-6"></div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"></label>
                                        <div class="am-u-sm-3">
                                            <a class="am-link am-text-sm" href="${ctxStatic }/fmt/订单对账导入模板.xls" class="am-btn">模板下载</a>
                                        </div>
                                        <div class="am-u-sm-6"></div>
                                    </div>
		                            <div class="am-form-group">
		                                <div class="am-u-sm-9 am-u-sm-push-3">
		                                    <button type="button" class="am-btn am-btn-xs am-btn-primary" id="submitBtn" onclick="submitForm()">提交</button>
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
<script type="text/javascript" src="${ctxStatic}/custom/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	function validFun(){
		if($('#photo').val() ==''){
			alert('请上传订单Excel')
			return false;
		}
		return true;
	}
	
	function submitForm(){
		$('#submitBtn').html('提交中').attr('disabled',true);
		$.post('${ctx}/order/financeOrder/importOrder',$('#dataForm').serializeArray(),function(result){
			if(result.success){
				location.href = "${ctx}/order/financeOrder/showImportResult?batchNo=" + result.obj;
			}else{
				alert('导入失败：' + result.msg);
				$('#submitBtn').html('提交').removeAttr('disabled');
			}
		})
	}
	
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
        initSelectValue(true)
        
        var $step = $("#step");
        $step.step({
				index: 0,
				time: 500,
				title: ["导入订单", "订单匹配", "提交结算"]
			});
        
        
        
      //------------------- 上传图片 ----------------------//
        $("#uploadPic").click(function(){
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
						$("#showPic").attr("href",'${imgUrl}/'+data.data).html(data.data);
						$("#photo").val(data.data);
						
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
