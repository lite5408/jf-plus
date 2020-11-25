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
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
	                                <tr>
										<td class="am-padding-sm">
											<button class="am-btn am-btn-xs am-btn-success am-icon-check"> 批量通过</button><button class="am-btn am-btn-xs am-btn-default am-icon-share"> 批量拒绝</button>
										</td>
	                                </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/order/act/myOrderAudit" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">状态</td>
                                    			<td>
											  		<select class="am-input-sm" name="status" data="${status}">
														<option value="0">全部</option>
														<option value="1">待审批</option>
														<option value="2">审批通过</option>
													</select>
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">订单号</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="orderNo"  value="${page.entity.orderNo }">
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">采购人</td>
												<td>
													<input type="text" class="am-input-sm"  name="userName"  value="${page.entity.userName }">
												</td>
												<td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
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
                                            <th>商品信息</th>
                                            <th>数量</th>
                                            <th>订单总价</th>
                                            <th>下单时间</th>
                                            <th>采购事由</th>
                                            <th>状态</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="order" varStatus="status">
                                        <tr>
                                                <td><input type="checkbox" name="rowId" value="${order.id }"/></td>
                                                <td>${order.orderNo}</td>
                                                <td>${order.orgName}</td>
                                                <td>${order.userNo }-${order.userName }</td>
                                                <td>商品信息</td>
                                                <td>${order.totalNum}</td>
                                                <td>${order.totalAmount}</td>
                                                <td>${order.createDate }</td>
                                                <td>${order.proReasons }</td>
                                                <td>
                                                	<c:if test="${order.auditStatus == 11 or order.auditStatus == 12 }">待审批</c:if>
                                                </td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                    <a role="oper" href="javascript:;" onclick="audit('${order.id}','${order.taskProcessId }','4')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-check"></span> 通过</a>
                                                    <a role="oper" href="javascript:;" onclick="audit('${order.id}','${order.taskProcessId }','3')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-share"></span> 拒绝</a>
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
    
    function audit(id,processId,status){
    	var postdata = {};
		postdata.status = status;
		var auditList = new Array();
		
    	if(status == '4'){
    		auditList.push({id:id,taskProcessId:processId});
    		
    		postdata.auditList = JSON.stringify(auditList);
			doAudit(postdata);
		} else {
			var index = layer.prompt({
				shade : 0.01,
				offset : $(event.srcElement).offset().top,
				title : "  输入拒绝原因（选填）",
				formType : 0
			}, function(value, index, elem) {
				auditList.push({id:id,taskProcessId:processId,auditReasons:value});
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
</script>
</body>
</html>
