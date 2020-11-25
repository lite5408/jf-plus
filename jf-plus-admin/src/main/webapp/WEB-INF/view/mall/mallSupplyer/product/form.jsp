<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商品表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/3rd-lib/wangEditor/css/wangEditor.min.css">
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
                            <div class="widget-title am-fl">商品维护</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="product" action="${ctxS}/product/<c:choose><c:when test="${empty product.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${product.id}"/>
                            <input type="hidden" name="source" value="2"/>
                            		<div class="am-form-group">
										<label class="am-u-sm-3 am-form-label"><span class="error">*</span>封面图：</label>
										<div class="am-u-sm-9 am-margin-top-xs">
											<div class="am-form-group am-form-file">
												<div class="tpl-form-file-img">
													<img id="showPic" src="<c:choose><c:when test='${empty product.photoUrl}'>${ctxStatic}/assets/img/add_img.png</c:when><c:otherwise>${product.photoUrl }</c:otherwise></c:choose>" alt="封面图" class="am-radius" width="130" height="130">
												</div>
												<button type="button" id="uploadPic" class="am-btn am-btn-xs am-btn-primary am-btn-sm ">
													<i class="am-icon-cloud-upload"></i> 选择文件</button>
												<input type="file" name="file" id="file">
												<input type="hidden" name="photo" id="photo" value="${product.photo}"/>
											</div>
										</div>
									</div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>商品编码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemCode" placeholder="商品编码" value="${product.itemCode}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>商品名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemName" placeholder="商品名称" value="${product.itemName}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>商品类别：</label>
                                        <div class="am-u-sm-9">
                                            <div class="am-input-group am-input-group-sm am-input-group-primary">
										      <input type="hidden" class="am-form-field" id="itemCate" name="itemCate" value="${product.itemCate }" required>
										      <input type="text" class="am-form-field" id="itemCateName" name="itemCateName" value="${product.itemCateName }" onkeyup="if($.trim(this.value) == ''){$('#itemCate').val('')}" required readonly>
										      <span class="am-input-group-btn">
										        <button class="am-btn am-btn-xs am-btn-primary" type="button" onclick="openModelAdjust('选择商品分类','${ctx}/mall/mallChannelCate/select?channelType=2')">选择</button>
										      </span>
										    </div>
                                        </div>
                                        <script>
									    	function itemCateCallback(data){
								    			$('#itemCate').val(data.catId).blur();
								    			$('#itemCateName').val(data.name).blur();
									    	}
									    </script>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">商品品牌：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="brandName" placeholder="商品品牌" value="${product.brandName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>供应价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="supplyPrice" placeholder="供应价" value="${product.supplyPrice}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>市场价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="markPrice" placeholder="市场价" value="${product.markPrice}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>商品详情：</label>
                                        <div class="am-u-sm-9">
                                            <textarea class="am-validate" name="content" id="content" rows="20" placeholder="">${product.contentHTML }</textarea>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>库存数量：</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="stock" placeholder="库存数量" value="<c:if test='${empty product.stock}'>10000</c:if><c:if test='${not empty product.stock}'>${product.stock }</c:if>" required/>
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
<script type="text/javascript" src="${ctxStatic}/custom/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctxStatic}/3rd-lib/wangEditor/js/wangEditor.min.js"></script>
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
    			uploadImgUrl:"${ctx}/upload/local",
    			uploadImgFileName:"file",
    			uploadParams:{IMG_URL:'${imgUrl}'}
    		};
    	var mEditor = createEditor('content',editorParams);
    });
    
</script>
</body>
</html>
