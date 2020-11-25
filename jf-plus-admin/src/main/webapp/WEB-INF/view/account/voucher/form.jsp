<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>电子券信息表</title>
    <%@ include file="../../include/head.jsp"%>
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<link rel="stylesheet" type="text/css" href="/static1/3rd-lib/wangEditor/css/wangEditor.min.css">
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">电子券信息</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="voucher" action="${ctx}/account/voucher/<c:choose><c:when test="${empty voucher.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
	                            <input type="hidden" name="id" value="${voucher.id}"/>
	                            <input type="hidden" name="source" value="2"/>
						       	<div class="am-form-group">
									<label class="am-u-sm-3 am-form-label"><span class="error">*</span>电子券图片：</label>
									<div class="am-u-sm-9 am-margin-top-xs">
										<div class="am-form-group am-form-file">
											<div class="tpl-form-file-img">
												<img id="showPic" src="<c:choose><c:when test='${empty voucher.showPhotoUrl}'>${ctxStatic}/assets/img/add_img.png</c:when><c:otherwise>${voucher.showPhotoUrl}</c:otherwise></c:choose>" alt="封面图" class="am-radius" width="130" height="130">
											</div>
											<button type="button" id="uploadPic" class="am-btn am-btn-xs am-btn-primary am-btn-sm ">
												<i class="am-icon-cloud-upload"></i> 选择文件</button>
											<input type="file" name="file" id="file">
											<input type="hidden" name="photoUrl" id="photo" value="${voucher.photoUrl}"/>
										</div>
									</div>
								</div>
	                            <div class="am-form-group">
	                                <label class="am-u-sm-3 am-form-label">是否手机端分发：</label>
	                                <div class="am-u-sm-9">
	                                    <select name="isMobile" data="${voucher.isMobile}">
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
	                                </div>
	                            </div>
	                            <div class="am-form-group">
	                                <label class="am-u-sm-3 am-form-label">电子券名称：</label>
	                                <div class="am-u-sm-9">
	                                    <input type="text" name="name" placeholder="电子券名称" value="${voucher.name}" required/>
	                                </div>
	                            </div>
	                            <div class="am-form-group">
	                                <label class="am-u-sm-3 am-form-label">金额方式：</label>
	                                <div class="am-u-sm-9">
	                                	<input type="radio" name="priceType" id="regularValue" checked="checked" value="1" required />固定面值
										<input type="radio" name="priceType" id="freeValue" value="0" required />自定义面值
	                                </div>
	                            </div>
	                            <div class="am-form-group" id="voucherValue">
	                                <label class="am-u-sm-3 am-form-label">电子券面值：</label>
	                                <div class="am-u-sm-9">
	                                    <input type="number" name="price" placeholder="电子券面值" id="price" value="${voucher.price}" required/>
	                                </div>
	                            </div>
	                            <div class="am-form-group">
	                                <label class="am-u-sm-3 am-form-label">失效时间：</label>
	                                <div class="am-u-sm-9">
	                                    <input type="text" class="am-input-sm" name="validEndDate" 
	                                    value="<fmt:formatDate value='${voucher.validEndDate }' pattern='yyyy-MM-dd'/>" 
	                                    placeholder="请选择失效时间" data-am-datepicker/>
	                                </div>
	                            </div>
	                            <div class="am-form-group">
	                                <label class="am-u-sm-3 am-form-label">状态：</label>
	                                <div class="am-u-sm-9">
	                                    <select name="operStatus" data="${voucher.operStatus}">
											<option value="1">启用</option>
											<option value="0">禁用</option>
										</select>
	                                </div>
	                            </div>
	                            <div class="am-form-group">
	                                <label class="am-u-sm-3 am-form-label">电子券详情：</label>
	                                <div class="am-u-sm-9">
	                                   <textarea class="am-validate" name="detail" id="content" rows="20" placeholder="">${voucher.detail}</textarea>
	                                </div>
	                            </div>
	                            <div class="am-form-group">
	                                <div class="am-u-sm-9 am-u-sm-push-3">
	                                    <button type="submit" class="am-btn am-btn-xs am-btn-primary">保存</button>
	                                    <a href="${ctx}/account/voucher/list" class="am-btn am-btn-xs am-btn-danger">取消</a>
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
<script type="text/javascript" src="/static1/3rd-lib/wangEditor/js/wangEditor.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
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
						$("#showPic").attr("src",'${imgUrl}/'+data.data);
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
		
		var editorParams = {
    			uploadImgUrl:"/a/upload/local",
    			uploadImgFileName:"file",
    			uploadParams:{IMG_URL:'http://img.ycb51.com/jf_plus'}
    		};
    	var mEditor = createEditor('content',editorParams);
    	
    	$("#regularValue").change(function(){
    		$("#price").val(null);
			$("#voucherValue").show();
		});

		$("#freeValue").change(function(){
			$("#voucherValue").hide();
			$("#price").val(-1);
		});
    	
    	if('${voucher.price}' == '-1.0'){
			$("#voucherValue").hide();
    		$("#price").val(-1);
    		$("#freeValue").attr("checked","checked");
    		$("#regularValue").removeAttr("checked");
		}else{
		}
    	
    	initSelectValue(true);//初始化下拉框的值
    	
    });
</script>
</body>
</html>
