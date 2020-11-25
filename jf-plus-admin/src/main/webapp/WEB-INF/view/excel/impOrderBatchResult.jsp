<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商城站点表</title>
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
                            <div class="widget-title am-fl">批量下单</div>
                        </div>
                        <div class="widget-body am-fr">
				          <div class="am-btn-toolbar">
				            <div class="am-btn-group am-btn-group-xs">
				              <a href="${ctx}/site/orderBatch/list" class="am-btn am-btn-xs am-btn-primary"> 返回</a>
				            </div>
				          </div>
				        </div>
                        <div class="widget-body am-fr">
							  <div class="am-panel am-panel-default">
							  	<div class="am-panel-bd" id="showMsg">
							  		<%-- <c:if test="${result.code eq 200}">
							  			导入成功，正在下单请稍等，本次下单批次号为：${result.obj}（用于下单完成后查询本次下单信息）
							  		</c:if> --%>
							  		<c:if test="${result.code eq 403 or result.code eq 405}">
							  			导入失败，失败原因：${result.msg}。
							  		</c:if>
							  		<c:if test="${result.code eq 500}">
							  			系统繁忙，请联系管理员！
							  		</c:if>
							  		<%-- <c:if test="${result.code eq 400}">
							  			本次批量下单中，共有${fn:length(result.obj)}条失败订单，本次下单批次号为：${result.obj}（用于查询本次下单信息）
							  			<br/>若需获取详细订单信息请点击下方按钮下载失败订单或使用批次号查询本次下单信息
							  			<br/><a href="${ctx}/export/orderBatch/excel?batchNo=${result.obj}&source=${result.obj[0].source}" class="am-btn am-btn-xs am-btn-success">失败订单下载</a>
							  		</c:if> --%>
							  	</div>
							  	
							  	<div class="am-panel-bd" id="successModel">
								  	<div class="am-g am-margin-bottom-xl">
									  <div class="am-u-sm-5"></div>
									  <div class="am-u-sm-7">
									  	<img src="${ctxStatic}/assets/img/success.png" height="100px" width="100px">
									  </div>
									</div>
									<div class="am-g am-margin-top-xl">
									  <div class="am-u-sm-3"></div>
									  <div class="am-u-sm-9">
									  	<div class="am-margin-left-lg am-padding-left-xl">
									  	<p class="am-text-lg">尊敬的用户，您已提交成功，本次下单批次号为<span class="am-text-danger">${result.obj}</span></p>
									  	</div>
									  </div>
									</div>
									<div class="am-g">
									  <div class="am-u-sm-4"></div>
									  <div class="am-u-sm-8"><div class="am-margin-left-xl am-padding-left-xl" id='showErrCount'></div></div>
									</div>
							  	</div>
							  </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<button
  id="startProgress"
  type="button"
  class="am-btn am-btn-success"
  data-am-modal="{target: '#my-modal-loading'}">
  加载进度条
</button>

<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="my-modal-loading">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">导入成功，正在批量下单中请勿刷新页面！</div>
    <div class="am-modal-bd">
      <span class="am-icon-spinner am-icon-spin"></span>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
<script type="text/javascript">
	var flag = "0";
	var batchNo = ${result.obj};
	var resultCode = ${result.code};

    $(document).ready(function () {
    	$("#startProgress").hide();
    	$("#successModel").hide();
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        initSelectValue(true);//初始化下拉框的值
        
        if (resultCode == 200) {
        	$("#startProgress").click();
	        var start = setInterval(function(){
	        	if (flag == "0") {
	        		$.post("${ctx}/site/orderBatch/getSessionStatus",{batchNo:batchNo},function(data){
	            		if(data.success){
	            			flag = data.obj;
	            		}
	            	});
	    		}else if (flag == "1"){
	    			clearInterval(start);
	    			$.post("${ctx}/site/orderBatch/getBatchResult",{batchNo:batchNo},function(data){
	            		if(data.success){
	            			if(data.code == 400){
	            				$("#showErrCount").html("失败订单共计：" + data.obj.length + "条<a href='${ctx}/export/orderBatch/excel?batchNo=" + batchNo + "' class='am-btn am-btn-xs'>失败订单下载</a>");
	            			}else if(data.code == 200){
	            				$("#showErrCount").html("失败订单共计：0条");
	            			}
	            			$("#my-modal-loading").modal('close');
	            			$("#successModel").show();
	            		}
	            	});
	    		}
	        },5000);
		}
    });
    
</script>
</body>
</html>
