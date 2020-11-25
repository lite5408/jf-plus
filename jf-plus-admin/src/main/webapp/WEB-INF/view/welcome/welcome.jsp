<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><c:set var="iutilsName"
		value='${fnc:getConfig("iutils.name")}' />${iutilsName} - 首页</title>
<%@ include file="/WEB-INF/view/include/head.jsp"%>
<style>
.tpl-content-wrapper {
	margin-left: 0
}

.widget-body {
	padding: 13px 20px;
}

</style>
</head>
<body>
	<script src="${ctxStatic}/assets/js/theme.js"></script>
	<!-- 内容区域 -->
	<div class="tpl-content-wrapper">
		<div class="row-content am-cf">
			<div class="row  am-cf">
				<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
					<div class="widget widget-default am-cf">
						<div class="widget-statistic-header">待审批单</div>
						<div class="widget-statistic-body">
							<div class="widget-statistic-value"><span role="rpt" role-id="toAudit">0</span></div>
							<div class="widget-statistic-description">
								
							</div>
							<span class="widget-statistic-icon"></span>
						</div>
					</div>
				</div>
				<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
					<div class="widget widget-primary am-cf">
						<div class="widget-statistic-header">已审批单</div>
						<div class="widget-statistic-body">
							<div class="widget-statistic-value"><span role="rpt" role-id="audit">0</span></div>
							<div class="widget-statistic-description">
								
							</div>
							<span class="widget-statistic-icon am-icon-support"></span>
						</div>
					</div>
				</div>
				<div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
					<div class="widget widget-purple am-cf">
						<div class="widget-statistic-header">可用余额</div>
						<div class="widget-statistic-body">
							<div class="widget-statistic-value">￥<span role="rpt" role-id="blance">0</span></div>
							<div class="widget-statistic-description">
								
							</div>
							<span class="widget-statistic-icon am-icon-credit-card-alt"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="row am-cf">
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">

					<div class="widget am-cf widget-body-lg">
						<div class="widget-head am-cf">
                            <div class="widget-title am-fl">待审批单</div>
                            <div class="widget-function am-fr"><a href="${ctx }/order/orderAudit/list?operStatus=1">更多</a></div>
                        </div>
						<div class="widget-body  am-fr">
							<div class="am-scrollable-horizontal ">
								<div id="example-r_wrapper"
									class="dataTables_wrapper am-datatable am-form-inline dt-amazeui no-footer">
									<table class="am-table am-table-compact am-text-nowrap tpl-table-black  dataTable no-footer"
										id="example-r" role="grid" style="width: 100%;">
										<thead>
											<tr role="row">
												<th width="15%">机构</th>
												<th width="15%">用户</th>
												<th width="20%">商品信息</th>
												<th width="5%">金额</th>
												<th width="8%">时间</th>
												<th width="20%">操作</th>
											</tr>
										</thead>
										<tbody role-id="toAuditList">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
			<div class="row am-cf">
				<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
					<div class="am-panel am-panel-default">
					  <div class="am-panel-hd">审批参数</div>
					  <div class="am-panel-bd" id="noAudit" style="display:none;">
					    	当前还未设置审批时间，订单无需审批可自动下单
					  </div>
					  <div class="am-panel-bd" id="auditInfo">
					    	<p>审批开关：<span id="auditOn"></span></p>
					    	<p>审批时间段：<span id="auditStartDate"></span> - <span id="auditEndDate"></span></p>
					  </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
	<script type="text/javascript">
		function getTotalFun(){
			post('${ctx}/zh/home/orderRPT', {}, function(result){
				if(result.success){
					$('span[role-id=toAudit]').text(result.obj.toAuditCount);
					$('span[role-id=audit]').text(result.obj.auditCount);
					$('span[role-id=blance]').text(result.obj.blance);
				}
			})
		}
		
		function getAuditListFun(){
			post('${ctx}/zh/home/auditList',{operStatus:1,pageSize:15},function(result){
				if(result.success){
					var auditList = result.obj;
					var shtml = '';
					for(var i = 0 ;i < auditList.length ; i++){
						var orderAudit = auditList[i];
						shtml +='<tr class="even gradeC odd" role="row">';
						shtml +='<td class="sorting_1">'+orderAudit.orgName+'</td>';
						shtml +='<td>'+ orderAudit.userNo+ '-' + orderAudit.userName+'</td>';
						shtml +='<td><div style="text-decoration:underline;cursor:hand;width:350px; display: block; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" onclick="openModelAdjust(\'查看商品详情\',\'${ctx}/order/orderAudit/myProductList?orderNo='+orderAudit.orderNo+'\',false,false)">'+ orderAudit.firstItemName +'</div></td>';
						shtml +='<td>'+ orderAudit.payAmount +'</td>';
						shtml +='<td>'+ orderAudit.createDate +'</td>';
						shtml +='<td>';
						shtml +='<div class="tpl-table-black-operation">';
						shtml +='	<a href="javascript:;" onclick="audit(\''+ orderAudit.id +'\',\''+ orderAudit.taskProcessId +'\',\'1\')"> <i class="am-icon-check"></i>通过</a> ';
						shtml +='	<a href="javascript:;" onclick="audit(\''+ orderAudit.id +'\',\''+ orderAudit.taskProcessId +'\',\'2\')" class="tpl-table-black-operation-del"> <i class="am-icon-share"></i>驳回</a> ';
						shtml +='	</div>';
						shtml +='</td>';
						shtml +='</tr>';
					}
					$('tbody[role-id=toAuditList]').html(shtml);
				}
			});
		}
		
		function getAuditSetting(){
			post('${ctx}/zh/home/getAuditSetting',{},function(result){
				if(result.success){
					if(result.obj != null && result.obj !=''){
						$('#auditOn').text(result.obj.isAudit == 1 ? '是':'否');
						$('#auditStartDate').text(result.obj.auditStartDate);
						$('#auditEndDate').text(result.obj.auditEndDate);
						
						$('#auditInfo').show();
						$('#noAudit').hide();
					}else{
						$('#auditInfo').hide();
						$('#noAudit').show();
					}
				}
			})
		}
		
		
		//审批
	    function audit(id,processId,status){
	    	doBatchAudit([{id:id,processId:processId}], status);
	    }
	    
	    /**批量审批*/
	    function doBatchAudit(toAuditList,status){
	    	var postdata = {};
			postdata.status = status;
			var auditList = new Array();
			
	    	if(status == '1'){
	    		for(var i = 0 ;i < toAuditList.length; i++){
	    			var toAudit = toAuditList[i];
	    			auditList.push({id:toAudit.id,taskProcessId:toAudit.processId});
	    		}
	    		
	    		postdata.auditList = JSON.stringify(auditList);
				doAudit(postdata);
			} else {
				var index = layer.prompt({
					shade : 0.01,
					offset : $(event.srcElement).offset().top,
					title : "  输入拒绝原因（选填）",
					formType : 0
				}, function(value, index, elem) {
					for(var i = 0 ;i < toAuditList.length; i++){
		    			var toAudit = toAuditList[i];
		    			auditList.push({id:toAudit.id,taskProcessId:toAudit.processId,auditReasons:value});
		    		}
					postdata.auditList = JSON.stringify(auditList);
					
					doAudit(postdata);
					layer.close(index);
				});
			}
		}

		function doAudit(data) {
			$.post('${ctx}/order/act/audit', data, function(result) {
				if (result.success) {
					layer.msg('操作成功', {
						icon : 1,
						time : 1000
					}, function() {
						
					});
					getAuditListFun();
				} else {
					window.alert(result.msg);
				}
			});
		}
		
		$(function(){
			getTotalFun();
			getAuditListFun();
			getAuditSetting();
		})
	</script>
</body>
</html>