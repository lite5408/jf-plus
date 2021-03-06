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
                            <div class="widget-title am-fl">商品详情编辑</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="product" action="${ctx}/product/updateDetail" method="post">
                            <input type="hidden" name="id" value="${product.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>商品名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="itemName" placeholder="商品名称" value="${product.itemName}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>商品详情：</label>
                                        <div class="am-u-sm-9">
                                            <textarea class="am-validate" name="content" id="content" rows="20" placeholder="">${product.content }</textarea>
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
