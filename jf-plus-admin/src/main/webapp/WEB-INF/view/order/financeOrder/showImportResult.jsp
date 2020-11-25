<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>财务对账簿表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
        .am-tabs-default .am-tabs-nav a{
        	height:40px!important;
        }
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/3rd-lib/jqueryStep/css/jquery.step.css" />
<script src="${ctxStatic}/3rd-lib/jqueryStep/js/jquery.step.min.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div id="step"></div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                               <div class="am-u-sm-12 am-text-lg">导入成功，成功匹配：<span class="am-text-danger">${matchSuccCount }</span>条订单，未匹配：<span class="am-text-danger">${matchFailCount }</span>条</div>
                            </div>
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12 am-margin-top-sm ">
                               <div class="am-u-sm-3">
                                   <a class="am-text-left am-link" href="${ctx }/export/financeOrder/excel?batchNo=${batchNo}" class="am-btn">匹配详情下载</a>
                               </div>
                               <div class="am-u-sm-6"></div>
                           	</div>
                           	<div class="am-u-sm-12 am-u-md-12 am-u-lg-12 am-margin-top-sm">
                                <div class="am-u-sm-12">
                                    <button type="button" class="am-btn am-btn-xs am-btn-primary" id="submitBtn" onclick="submitForm()">提交结算</button>
                                	<a class="am-btn am-btn-xs am-btn-danger" href="${ctx }/order/financeOrder/importOrder">返回</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
    });
    
    var $step = $("#step");
    $step.step({
			index: 0,
			time: 500,
			title: ["导入订单", "订单匹配", "提交结算"]
		});
    $step.toStep(1);
    
    function submitForm(){
    	var matchSuccCount = ${matchSuccCount};
    	if(matchSuccCount <= 0){
    		alert('没有成功匹配的订单，无需提交！')
    		return false;
    	}
    	
    	layer.confirm('确认要提交结算吗?', {
    		shade:0.01,
    		offset: '30px',
    		btn : [ '确定', '取消' ]
    	}, function() {
    		$('#submitBtn').html('提交中').attr('disabled',true);
    		$.get("${ctx}/order/financeOrder/submit?batchNo=${batchNo}",function(result){
    			if(result.success){
    				location.href="${ctx}/order/financeOrder/financeFinish?operStatus=1&batchNo=${batchNo}";
    			}else{
    				alert("操作失败："+result.msg);
    				$('#submitBtn').html('提交结算').removeAttr('disabled');
    			}
    		});
    	});
    	
    }
</script>
</body>
</html>
