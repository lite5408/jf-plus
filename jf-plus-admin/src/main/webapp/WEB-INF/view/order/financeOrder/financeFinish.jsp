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
                        	 	<img src="${ctxStatic}/assets/img/success.png" height="100px" width="100px">
                        	 </div>
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12 am-margin-top">
                               <div class="am-u-sm-12 am-text-lg">订单已标记结算，本次操作批次号：<span class="error">${batchNo }</span></div>
                            </div>
                           	<div class="am-u-sm-12 am-u-md-12 am-u-lg-12 am-margin-top-sm">
                                <div class="am-u-sm-12">
                                	<a class="am-btn am-btn-xs am-btn-primary" href="${ctx }/order/financeOrder?operStatus=1">查看结算详情</a>
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
    $step.toStep(2);
    
</script>
</body>
</html>
