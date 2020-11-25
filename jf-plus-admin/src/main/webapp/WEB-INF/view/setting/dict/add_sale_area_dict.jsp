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
                            <div class="widget-title am-fl">销售区域</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="dict" action="${ctx}/setting/dict/<c:choose><c:when test="${empty dict.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${dict.id}"/>
                            <c:if test="${not empty pDict }">
                            	<c:set var="pid" value = "${pDict.id }"/>
                            	<c:set var="pids" value = "${pDict.pids }${pDict.id }"/>
                            </c:if>
                            <input type="hidden" name="pid" value="${empty pid?'0':pid}"/>
							<input type="hidden" name="pids" value="${empty pids?'0':pids}/" />
                            
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">字典名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="dict" placeholder="字典名称" value="${dict.dict }"/>
                                        </div>
                                    </div>
                                    <c:if test="${not empty pDict }">
	                                    <div class="am-form-group">
	                                        <label class="am-u-sm-3 am-form-label">上级区域：</label>
	                                        <div class="am-u-sm-9">
	                                            <input type="text" name="pval" value="${pDict.val}" readonly/>
	                                        </div>
	                                    </div>
                                    </c:if>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">区域编码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="key" placeholder="" value="" required unique/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">区域名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="val" placeholder="" value="" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">类型：</label>
                                        <div class="am-u-sm-9">
                                            <select name="type" data="true">
                                            	 <c:choose>
	                                            	 <c:when test="${empty pDict }">
	                                            		<option value="1">大区</option>
	                                            	 </c:when>
	                                            	 <c:when test="${pDict.type == 1 }">
	                                            		<option value="2">省份</option>
	                                            	 </c:when>
	                                            	 <c:when test="${pDict.type == 2 }">
	                                            		<option value="3">城市</option>
	                                            	 </c:when>
                                            	</c:choose>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">启用：</label>
                                        <div class="am-u-sm-9">
                                            <select name="avaliable">
                                            	<option value="1">启用</option>
                                            	<option value="0">禁用</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="number" name="sort" placeholder="排序" />
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
        initSelectValue(true)
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
