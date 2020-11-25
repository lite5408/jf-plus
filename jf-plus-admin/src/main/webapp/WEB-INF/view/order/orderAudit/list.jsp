<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单审批</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<script src="${ctxStatic}/3rd-lib/amazeui/js/amazeui.min.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">订单审批</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-2 am-u-md-2 am-u-lg-2">
                                <table class="am-u-sm-12">
	                                <tr>
										<td class="am-padding-xs">
											<button class="am-btn am-btn-xs am-btn-success am-icon-check" onclick="batchAudit('1');"> 通过</button><button class="am-btn am-btn-xs am-btn-default am-icon-share" onclick="batchAudit('2');"> 拒绝</button>
										</td>
	                                </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-10 am-u-md-10 am-u-lg-10">
                                <form id="searchForm" action="${ctx}/order/orderAudit/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-xs am-text-xs am-text-right">状态</td>
                                    			<td>
											  		<select class="am-input-xs" name="operStatus" data="${status}" style="min-width:100px;">
														<option value="1">待审批</option>
														<option value="2">已审批</option>
													</select>
												</td>
                                    			<td class="am-padding-xs am-text-xs am-text-right">订单号</td>
                                    			<td>
											  		<input type="text" class="am-input-xs"  name="orderNo"  value="${page.entity.orderNo }">
												</td>
                                    			<td class="am-padding-xs am-text-xs am-text-right">机构</td>
                                    			<td>
											  		<input type="text" class="am-input-xs"  name="orgName"  value="${page.entity.orgName }">
												</td>
												<td class="am-padding-xs am-text-xs am-text-right">采购人</td>
												<td>
													<input type="text" class="am-input-xs"  name="userName"  value="${page.entity.userName }">
												</td>
												<td class="am-padding-xs am-text-xs am-text-right">收货人</td>
												<td>
													<input type="text" class="am-input-xs"  name="receiver"  value="${page.entity.receiver }">
												</td>
												<td class="am-padding-xs">
													<button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button>
													<a href="${ctx }/export/orderAudit/excel?pageNo=0&operStatus=${status}&orgName=${page.entity.orgName}&userName=${page.entity.userName}&orderNo=${page.entity.orderNo}" 
												class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o" type="button"> 导出</a>
												</td>
                                    		</tr>
                                    	</table>
                                    </div>
                                </form>
                            </div>
                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th><input type="checkbox" id="checkAll"/></th>
                                            <th>订单号</th>
                                            <th>机构</th>
                                            <th>购买人</th>
                                            <th width="20%">商品信息</th>
                                            <th>商品件数</th>
                                            <th>订单总价</th>
                                            <th>下单时间</th>
                                            <th width="5%">采购事由</th>
                                            <th>状态</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orderAudit" varStatus="status">
                                        <tr>
                                                <td><input type="checkbox" name="rowId" value="${orderAudit.id }" data-id="${orderAudit.id }" data-taskid="${orderAudit.taskProcessId }"/></td>
                                                <td>${fn:substring(orderAudit.orderNo,12,21)}</td>
                                                <td>${orderAudit.orgNo}-${orderAudit.orgName}</td>
                                                <td>${orderAudit.userNo }-${orderAudit.userName }</td>
                                                <td>
                                                	<div class="am-u-sm-10"><a onclick="openModelAdjust('商品详情','${ctx }/product/detail?productNo=${orderAudit.firstProductId}',false,false)">${orderAudit.firstItemName }</a></div>
                                                	<div class="am-u-sm-2"><a onclick="openModelAdjust('查看商品详情','${ctx}/order/orderAudit/myProductList?orderNo=${orderAudit.orderNo }',false,false)" class="am-btn am-btn-xs am-btn-default am-icon-search" title="更多"></a></div>
                                                </td>
                                                <td>${orderAudit.totalNum}</td>
                                                <td id="pay_${orderAudit.id }">
                                                	${orderAudit.payAmount}
                                                	<script>
                                                		var content = "商品总额：${orderAudit.totalAmount}+运费：${orderAudit.freight}";
                                                	 	$('#pay_${orderAudit.id}').popover({
                                                		    content: content,
                                                		    trigger:'hover focus'
                                                		})
                                                	</script>
                                                </td>
                                                <td><fmt:formatDate value="${orderAudit.createDate }" pattern="yyyy-MM-dd HH:mm"/></td>
                                                <td>${orderAudit.proReasons }</td>
                                                <td>
                                                	<c:if test="${orderAudit.auditStatus == 11 or orderAudit.auditStatus == 12 }">待审批</c:if>
                                                </td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                    <a role="oper" href="javascript:;" onclick="audit('${orderAudit.id}','${orderAudit.taskProcessId }','1')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-check"></span> 通过</a>
                                                    <a role="oper" href="javascript:;" onclick="audit('${orderAudit.id}','${orderAudit.taskProcessId }','2')" class="am-btn am-btn-default am-btn-xs am-text-danger"><span class="am-icon-share"></span> 拒绝</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="../../utils/pagination.jsp"%>
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
        
        initSelectValue(true);
    });
    
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
					$('#searchForm').submit();
				});
			} else {
				window.alert(result.msg);
			}
		});
	}
	
	
	$('#checkAll').click(function(){
		var checked = $(this).prop('checked') ? true:false;
		$('input[name="rowId"]').prop('checked',checked);
	});
	
	//历史审批
	$('select[name=operStatus]').change(function(){
		if($(this).val() == 2){
			$('#searchForm').submit();
		}
	});
	
	/**批量审批*/
	function batchAudit(status){
		var $checkedRows = $('input[name="rowId"]:checked');
		if($checkedRows.length == 0){
			window.alert('请勾选审批单，再进行操作');
			return false;
		}
		
		var toAuditList = new Array();
		$.each($checkedRows,function(){
			toAuditList.push({id:$(this).attr('data-id'),processId:$(this).attr('data-taskid')});
		});
		
		doBatchAudit(toAuditList, status);
		
	}
</script>
</body>
</html>
