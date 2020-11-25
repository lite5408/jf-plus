<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商城专题</title>
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
                            <div class="widget-title am-fl">商城专题</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="siteAdvert" action="${ctx}/site/siteAdvert/<c:choose><c:when test="${empty siteAdvert.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${siteAdvert.id}"/>
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">类型：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="type" placeholder="类型" value="${siteAdvert.type}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
										<label class="am-u-sm-3 am-form-label"><span class="error">*</span>封面图：</label>
										<div class="am-u-sm-9 am-margin-top-xs">
											<div class="am-form-group am-form-file">
												<div class="tpl-form-file-img">
													<img id="showPic" src="<c:choose><c:when test='${empty siteAdvert.photoUrl}'>${ctxStatic}/assets/img/add_img.png</c:when><c:otherwise>${siteAdvert.photoUrl }</c:otherwise></c:choose>" alt="封面图" class="am-radius" width="130" height="130">
												</div>
												<button type="button" id="uploadPic" class="am-btn am-btn-xs am-btn-primary am-btn-sm ">
													<i class="am-icon-cloud-upload"></i> 选择文件</button>
												<input type="file" name="file" id="file">
												<input type="hidden" name="photo" id="photo" value="${siteAdvert.photo}"/>
											</div>
										</div>
									</div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>专题名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="name" placeholder="专题名称" value="${siteAdvert.name}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>专题类型：</label>
                                        <div class="am-u-sm-9">
                                            <select name="showType" data="${item.showType }">
                                            	<c:forEach items="<%=SpecialShowType.values()%>" var="item">
                                            		<option value=${item.type }>${item.description }</option>
                                            	</c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">图片链接：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="url" placeholder="" value="${siteAdvert.url}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sort" placeholder="排序" value="<c:if test='${empty siteAdvert.sort}'>1</c:if>"/>
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
<script type="text/javascript" src="${ctxStatic}/custom/js/ajaxfileupload.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
        initSelectValue(true);
        
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
    });
</script>
</body>
</html>
