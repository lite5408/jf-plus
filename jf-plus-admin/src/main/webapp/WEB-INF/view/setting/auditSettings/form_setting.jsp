<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>审批参数设置</title>
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
                            <div class="widget-title am-fl">审批参数设置</div>
                        </div>
                        <c:if test="${not empty auditSettings }">
	                        <div class="widget-head am-cf">
	                            <div class="widget-title am-fl">当前设置</div>
	                            <form class="am-form tpl-form-border-form">
	                                    <article class="am-article am-margin-top">
										  <div class="am-article-bd">
										    <p class="am-article-lead am-text-default ">审批开关：<b><c:if test="${auditSettings.isAudit eq 1}">开启</c:if><c:if test="${auditSettings.isAudit eq 0}">关闭</c:if></b>，
										    	审批时间段：
										    	<b>
										    	<c:choose>
										    		<c:when test="${not empty auditSettings.auditStartTime  }">
										    			<fmt:formatDate pattern="yyyy-MM-dd" value="${auditSettings.auditStartTime }"/> - <fmt:formatDate pattern="yyyy-MM-dd" value="${auditSettings.auditEndTime }"/>
										    		</c:when>
										    		<c:otherwise>未设置</c:otherwise>
										    	</c:choose>
										    	</b>
										   </p>
										  </div>
										</article>
	                            </form>
	                        </div>
                        </c:if>
                        <c:if test="${empty auditSettings }">
	                            <form class="am-form tpl-form-border-form">
	                                    <article class="am-article am-margin-top">
										  <div class="am-article-bd">
										    <p class="am-article-lead am-text-default ">当前还未设置审批时间，订单无需审批可自动下单</p>
										  </div>
										</article>
	                            </form>
                        </c:if>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="auditSettings" action="${ctx}/setting/auditSettings/<c:choose><c:when test="${empty auditSettings.id}">formSetting</c:when><c:otherwise>formSetting</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${auditSettings.id}"/>
                            <input type="hidden" name="orgId" value="${loginUser.organizationId}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">审批开关：</label>
                                        <div class="am-u-sm-9">
                                            <select id="isAudit" name="isAudit" data="true" required="required">
												<option value="">请选择</option>
												<option value="1">开启</option>
												<option value="0">关闭</option>
											</select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">设置审批时间段：</label>
                                        <div class="am-u-sm-2">
                                            <input type="checkbox" id="auditTime" />
                                        </div>
                                        <div class="am-u-sm-2">
                                        </div>
                                        <div class="am-u-sm-5"></div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">审批开始时间：</label>
                                        <div class="am-u-sm-2">
                                            <input type="datetime" id="auditStartTime" name="auditStartTime" data-am-datepicker placeholder="开始时间" value="" readonly/>
                                        </div>
                                        <div class="am-u-sm-2">
                                            <input type="datetime" id="auditEndTime" name="auditEndTime" data-am-datepicker placeholder="结束时间" value="" readonly/>
                                        </div>
                                        <div class="am-u-sm-5"></div>
                                    </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="submit" class="am-btn am-btn-xs am-btn-primary">保存</button>
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
        }
        
        initSelectValue(true);
        
        initForm();
    });
    
    function initForm(){
        var auditStartTime = $('#auditStartTime').val();
        if(auditStartTime != ''){
        	$('#auditTime').prop('checked','checked');
        	$('#auditStartTime,#auditEndTime').removeAttr('readonly');
        }
        
        $('#auditTime').click(function(){
        	var checked = $(this).prop('checked');
        	if(checked){
        		$('#auditStartTime,#auditEndTime').removeAttr('readonly').attr('required',true).blur();
        	}else{
        		$('#auditStartTime,#auditEndTime').attr('readonly',true).removeAttr('required').val('').blur();
        	}
        });
    }
</script>
</body>
</html>
