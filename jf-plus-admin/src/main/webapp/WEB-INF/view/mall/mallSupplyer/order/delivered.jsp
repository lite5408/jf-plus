<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>供应商</title>
    <%@ include file="../../../include/head.jsp"%>
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
                            <div class="widget-title am-fl">配送信息</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orderDistrib" action="${ctxS}/order/delivered" method="post">
                            <input type="hidden" name="orderId" value="${orderId}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>配送方式：</label>
                                        <div class="am-u-sm-9">
                                            <select id="sel" name="type" required>
												<option value="0">自配</option>
												<option value="1">快递</option>
											</select>
											<script type="text/javascript">
												$("#sel").change(function(){
													if($("#sel").val()==0){
														$("#del").show();
														$("#exp").hide();
														$("#express").val("");
														$("#expressNo").val("");
													}else{
														$("#exp").show();
														$("#del").hide();
														$("#delivery").val("");
														$("#deliveryMobile").val("");
													}
												});
											</script>
                                        </div>
                                    </div>
                                    <div id="del">
	                                    <div class="am-form-group">
	                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>配送员：</label>
	                                        <div class="am-u-sm-9">
	                                            <input type="text" id="delivery" name="delivery" value="" placeholder="请输入配送员名字" required>
	                                        </div>
	                                    </div>
	                                    <div class="am-form-group">
	                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>配送员电话：</label>
	                                        <div class="am-u-sm-9">
	                                            <input type="text" id="deliveryMobile" name="deliveryMobile" value="" placeholder="请输入配送员电话" required>
	                                        </div>
	                                    </div>
                                    </div>
                                    <div id="exp" style="display: none;">
	                                    <div class="am-form-group">
	                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>快递公司：</label>
	                                        <div class="am-u-sm-9">
	                                            <input type="text" id="express" name="express" value="" placeholder="请输入快递公司" required>
	                                        </div>
	                                    </div>
	                                    <div class="am-form-group">
	                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>快递单号：</label>
	                                        <div class="am-u-sm-9">
	                                            <input type="text" id="expressNo" name="expressNo" value="" placeholder="请输入快递单号" required>
	                                        </div>
	                                    </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>发货时间：</label>
                                        <div class="am-u-sm-9">
                                        	<jsp:useBean id="now" class="java.util.Date" scope="page"/>
                                            <input type="text" name="deliveryTime" placeholder="发货时间" value="<fmt:formatDate value='${now}' pattern="yyyy-MM-dd" />" data-am-datepicker required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <textarea rows="2" name="remarks" placeholder="请输入备注说明"></textarea>
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
<%@ include file="../../../include/bottom.jsp"%>
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
