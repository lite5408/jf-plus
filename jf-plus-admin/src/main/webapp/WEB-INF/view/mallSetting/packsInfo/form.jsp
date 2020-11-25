<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>礼包信息表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/zxxFile.js"></script>
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
                            <div class="widget-title am-fl">礼包信息</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="packsInfo" action="${ctx}/mallSetting/packsInfo/<c:choose><c:when test="${empty packsInfo.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            		<input type="hidden" name="id" value="${packsInfo.id}"/>
		                            <input type="hidden" name="orgId" value="${packsInfo.orgId}"/>
		                            <input type="hidden" name="siteId" value="${packsInfo.siteId}"/>
		                            <input type="hidden" id="imgUrl" value="${packsInfo.showVideoUrl}">
		                            <div class="am-form-group">
										<label class="am-u-sm-3 am-form-label"><span class="error">*</span>礼包封面图：</label>
										<div class="am-u-sm-9 am-margin-top-xs">
											<div class="am-form-group am-form-file">
												<div class="tpl-form-file-img">
													<img id="showPic" src="<c:choose><c:when test='${empty packsInfo.showPhotoUrl}'>${ctxStatic}/assets/img/add_img.png</c:when><c:otherwise>${packsInfo.showPhotoUrl}</c:otherwise></c:choose>" alt="封面图" class="am-radius" width="130" height="130">
												</div>
												<button type="button" id="uploadPic" class="am-btn am-btn-xs am-btn-primary am-btn-sm ">
													<i class="am-icon-cloud-upload"></i> 选择文件</button>
												<input type="file" name="file" id="file">
												<input type="hidden" name="photoUrl" id="photo" value="${packsInfo.photoUrl}"/>
											</div>
										</div>
									</div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">礼包名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="name" placeholder="礼包名称" value="${packsInfo.name}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
		                                <label class="am-u-sm-3 am-form-label">是否手机端分发：</label>
		                                <div class="am-u-sm-9">
		                                    <select name="isMobile" data="${packsInfo.isMobile}">
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
		                                </div>
		                            </div>
                                    <%-- <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">市场价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="markPrice" placeholder="市场价" value="${packsInfo.markPrice}" required/>
                                        </div>
                                    </div> --%>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">销售价：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="salePrice" placeholder="销售价" value="${packsInfo.salePrice}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">截止日期：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" class="am-input-sm" name="validEndDate" required="required" 
		                                    value="<fmt:formatDate value='${packsInfo.validEndDate }' pattern='yyyy-MM-dd' />" 
		                                    placeholder="请选择截止日期" data-am-datepicker/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
		                                <label class="am-u-sm-3 am-form-label">礼包状态：</label>
		                                <div class="am-u-sm-9">
		                                    <select name="operStatus" data="${packsInfo.operStatus}">
												<option value="1">启用</option>
												<option value="0">禁用</option>
											</select>
		                                </div>
		                            </div>
		                            <div class="am-form-group">
		                                <label class="am-u-sm-3 am-form-label">是否显示价格：</label>
		                                <div class="am-u-sm-9">
		                                    <select name="isShowPrice" data="${packsInfo.isShowPrice}">
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
		                                </div>
		                            </div>
                                    <%-- <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">礼包商品：</label>
                                        <div class="am-u-sm-9">
                                            <span class="am-input-group-btn">
								           		<input type="hidden" id="prods" name="items" value="${packsInfo.items }" />
								            	<button class="am-btn am-btn-primary" type="button" id="selectPacksProBtn">挑选商品</button>
								            	<button class="am-btn am-btn-default" type="button" id="showPacksProBtn">查看已选商品</button>
								            	<input type="hidden" id="prodNum" name="itemsCount" >
								          	</span>
								          	<p id="prodNumShow">您已挑选<c:choose><c:when test='${empty packsInfo.itemsCount}'>0</c:when><c:otherwise>${packsInfo.itemsCount}</c:otherwise></c:choose>件商品 </p>
                                        </div>
                                    </div> --%>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">赠送寄语：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="shareName" placeholder="赠送寄语" value="${packsInfo.shareName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">详情说明：</label>
                                        <div class="am-u-sm-9">
                                        	<textarea class="am-validate" name="details" id="content" rows="20" placeholder="详情说明">${packsInfo.details}</textarea>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
						    			<label class="am-u-sm-3 am-form-label">赠送封面图：</label>
						    			<div class="am-u-sm-9">
							             		<input type="hidden" id="photoSelectUrl" name="photoSelectUrl" value="" />
												<div class="upload_box">
													<div class="upload_main">
														<div class="upload_choose">
															<input id="fileImages" type="file" size="30" name="fileselect[]" multiple />
														</div>
														<div id="preview" class="upload_preview"></div>
													</div>
													<div class="upload_submit">
														<button type="button" id="fileSubmits" class="upload_submit_btn">确认上传图片</button>
													</div>
													<div id="uploadInf" class="upload_inf"></div>
												</div>
								        </div>
									</div>
		                            <div class="am-form-group">
		                                <div class="am-u-sm-9 am-u-sm-push-3">
		                                    <button type="submit" class="am-btn am-btn-xs am-btn-primary">保存</button>
		                                    <a href="${ctx}/mallSetting/packsInfo/list" id="cancle" class="am-btn am-btn-xs am-btn-danger">取消</a>
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
		
		$("#cancle").click(function(){
			location.href('${ctx}/mallSetting/packsInfo/list');
		});
		
		var editorParams = {
    			uploadImgUrl:"/a/upload/local",
    			uploadImgFileName:"file",
    			uploadParams:{IMG_URL:'http://img.ycb51.com/jf_plus'}
    		};
    	var mEditor = createEditor('content',editorParams);
    	
    	//------------------- 批量上传图片 ----------------------//
		
    	var detailImgs = "";
    	uploadImages('fileImages', 'fileDragArea', 'fileSubmits', function(result) {
    		//$('#images').val(result.obj.imgName);
    		$("#uploadInf").append("<p>上传成功，文件名：" + result.data + "</p>");
    		detailImgs += result.data + ";";
    		$('#photoSelectUrl').val(detailImgs.substring(0, detailImgs.length - 1));
    	});
    	//------------------- 上传图片 ----------------------//
    	
    	initSelectValue(true);//初始化下拉框的值
    });
    
  	//初始化图片信息
	function loadImg(){
  		if(!$('#imgUrl').val()==''){
			var array = $('#imgUrl').val().split(";");
			var html ='';
			for (var i=0 ; i< array.length ; i++){
				html = html + 
				'<img width="280" height="180" id="uploadImage_' + i + '" src="' + array[i] + '" class="upload_image"/>';
			}
			$("#preview").html(html);
  		}
	}
	loadImg();
    
    
  //上传图片(批量)
    function uploadImages(_fileImage,_fileDragArea,_fileSubmit,_callback){
    	var params = {
    		fileInput: $("#"+_fileImage).get(0),
    		dragDrop: $("#"+_fileDragArea).get(0),
    		upButton: $("#"+_fileSubmit).get(0),
    		url: "${ctx}/upload/localfiles",
    		filter: function(files) {
    			var arrFiles = [];
    			for (var i = 0, file; file = files[i]; i++) {
    				if (file.type.indexOf("image") == 0) {
    					if (file.size >= 2097152) {
    						alert('您这张"'+ file.name +'"图片过大，应小于2M');	
    					} else {
    						arrFiles.push(file);	
    					}			
    				} else {
    					alert('文件"' + file.name + '"不是图片。');	
    				}
    			}
    			return arrFiles;
    		},
    		onSelect: function(files) {
    			var html = '', i = 0;
    			$("#preview").html('<div class="upload_loading"></div>');
    			var funAppendImage = function() {
    				file = files[i];
    				if (file) {
    					var reader = new FileReader()
    					reader.onload = function(e) {
    						html = html + '<div id="uploadList_'+ i +'" class="upload_append_list" height="180px"><p><strong>' + file.name + '</strong>'+ 
    							'<a href="javascript:" class="upload_delete" title="删除" data-index="'+ i +'">删除</a>' +
    							'<br />'+
    							'<img id="uploadImage_' + i + '" src="' + e.target.result + '" class="upload_image"  width="180px" height="160px"/></p>'+ 
    							'<span id="uploadProgress_' + i + '" class="upload_progress"></span>' +
    						'</div>';
    						
    						i++;
    						funAppendImage();
    					}
    					reader.readAsDataURL(file);
    				} else {
    					
    					$("#preview").html(html);
    					if (html) {
    						//删除方法
    						$(".upload_delete").click(function() {
    							ZXXFILE_MULIS.funDeleteFile(files[parseInt($(this).attr("data-index"))]);
    							$("#uploadList_" + $(this).attr("data-index")).fadeOut();
    							return false;	
    						});
    						//提交按钮显示
    						$("#"+_fileSubmit).show();	
    					} else {
    						//提交按钮隐藏
    						$("#"+_fileSubmit).hide();	
    					}
    				}
    			};
    			funAppendImage();		
    		},
    		//onDelete: function(file) {
    			//$("#uploadList_" + file.index).fadeOut();
    		//},
    		onDragOver: function() {
    			$(this).addClass("upload_drag_hover");
    		},
    		onDragLeave: function() {
    			$(this).removeClass("upload_drag_hover");
    		},
    		onProgress: function(file, loaded, total) {
    			var eleProgress = $("#uploadProgress_" + file.index), percent = (loaded / total * 100).toFixed(2) + '%';
    			eleProgress.show().html(percent);
    		},
    		onSuccess: function(file, result) {
    			result = eval('(' + result + ')');
    			if (result.ret == 1) {
    				_callback(result);
    			} else {
    				alert(result.msg);
    			}
    		},
    		onFailure: function(file) {
    			$("#uploadInf").append("<p>图片" + file.name + "上传失败！</p>");	
    			$("#uploadImage_" + file.index).css("opacity", 0.2);
    		},
    		onComplete: function() {
    			//提交按钮隐藏
    			$("#"+_fileSubmit).hide();
    			//file控件value置空
    			$("#"+_fileImage).val("");
    			//$("#uploadInf").append("<p>当前图片全部上传完毕，可继续添加上传。</p>");
    		}
    	};
    	ZXXFILE = $.extend(ZXXFILE, params);
    	var ZXXFILE_MULIS = $.extend(ZXXFILE_MULIS, ZXXFILE);
    	ZXXFILE_MULIS.init();
    }
</script>
</body>
</html>
