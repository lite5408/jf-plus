<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>关联商品分类</title>
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
                            <div class="widget-title am-fl">关联商品分类</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="mallChannelCate" action="${ctx}/mall/mallChannelCate/relatedCate" method="post">
                            <input type="hidden" name="id" value="${mallChannelCate.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">渠道类别：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="catName" placeholder="类别名称" value="${mallChannelCate.catName}" readonly/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
	                                    <label class="am-u-sm-3 am-form-label"><span class="error">*</span>商品分类：</label>
	                                    <div class="am-u-sm-9">
	                                        <div class="am-input-group">
	                                            <input type="text" id="itemCateName" class="am-form-field" minlength="1" value="${mallChannelCate.productCateName}" required/>
	                                            <input type="hidden" id="itemCate" name="productCateIds" value="${mallChannelCate.productCateIds}" />
											    <span class="am-input-group-btn">
													<button class="am-btn am-btn-xs am-btn-primary" type="button" id="itemCateBtn" onclick="openModelAdjust('选择商品分类','${ctx}/mall/mallProductCate/select')">选择</button>
													<button class="am-btn am-btn-default" type="button" id="itemCateBtnClear" onclick="$('#itemCateName #itemCate').val('');">清除</button>
											    </span>
											    <script>
											    	function itemCateCallback(data){
											    		$('#itemCate').val(data.id);
											    		$('#itemCateName').val(data.name);
											    	}
											    </script>
	                                        </div>
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
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
    });
</script>
</body>
</html>
