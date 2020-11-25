<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>字典表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
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
                            <div class="widget-title am-fl">货源地区</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="dict" action="${ctx}/setting/dict/<c:choose><c:when test="${empty dict.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${dict.id}"/>
                            
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">字典名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="dict" placeholder="字典名称" value="${dict.dict }"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">地区编码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="key" placeholder="地区编码" value="${dict.key}" required unique/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">地区名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="val" placeholder="地区名称" value="${dict.val}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">启用：</label>
                                        <div class="am-u-sm-9">
                                            <select name="avaliable" data="${dict.avaliable }" >
                                            	<option value="1">启用</option>
                                            	<option value="0">禁用</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="sort" placeholder="排序" value="${dict.sort}" required/>
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
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
        initSelectValue(true);
    });
    
    $('[unique]').blur(function(){
    	if(isNull($(this).val()))
    		return true;
    	
		$.ajaxSetup({aysnc:false});
		post("${ctx}/setting/dict/unique",{dict:'${dict.dict}',id:null,key:$(this).val()},function(result){
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
