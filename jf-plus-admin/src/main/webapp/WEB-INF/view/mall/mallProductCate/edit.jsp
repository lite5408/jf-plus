<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商城商品分类</title>
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
                            <div class="widget-title am-fl">商品分类</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="mallProductCate" action="${ctx}/mall/mallProductCate/create" method="post">
                            <input type="hidden" name="id" value="${mallProductCate.id}"/>
                            <input type="hidden" name="catPid" value="${mallProductCate.catPid}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">父类别：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="catPid" placeholder="父类别" value="${mallProductCate.catPidName}" readonly/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">编码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="catCode" placeholder="类别编码" value="${mallProductCate.catCode }" required unique/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">类别名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="catName" placeholder="类别名称" value="${mallProductCate.catName }" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sort" placeholder="排序" value="${mallProductCate.sort }" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">图标：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="icon" placeholder="" value="${mallProductCate.icon }"/>
                                        </div>
                                    </div>
                                   <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">启用：</label>
                                        <div class="am-u-sm-9">
                                            <select name="isFront" data="${mallProductCate.isFront }">
                                            	<option value="1">启用</option>
                                            	<option value="0">禁用</option>
                                            </select>
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
        
        initSelectValue(true)
    });
    
    $('[unique]').blur(function(){
    	if(isNull($(this).val()))
    		return true;
    	
		$.ajaxSetup({aysnc:false});
		post("${ctx}/mall/mallProductCate",{catCode:$(this).val(),id:'${mallProductCate.id}'},function(result){
			if(result.obj >= 1){
				alert('编码重复，请重新输入');
				$('[unique]').val('');
				return false;
			}else{
				return true;
			}
		});
	});
</script>
</body>
</html>
