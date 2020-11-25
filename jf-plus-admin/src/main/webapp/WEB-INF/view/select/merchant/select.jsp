<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>代理商</title>
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
<!--                     	<div class="widget-head am-cf"> -->
<!--                             <div class="widget-title am-fl">关联代理商</div> -->
<!--                         </div> -->
<!--                         <div data-am-widget="tabs" class="am-tabs am-tabs-default am-u-sm-4" > -->
<!-- 					      <ul class="am-tabs-nav am-cf"> -->
<%-- 					          <li class="${page.condition.isRelated == 0 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/product/orgGroup/merchantList?orgType=4&groupId=${page.condition.groupId }&isRelated=0'">待加入分组</a></li> --%>
<%-- 					          <li class="${page.condition.isRelated == 1 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/product/orgGroup/merchantList?orgType=4&groupId=${page.condition.groupId }&isRelated=1'">已加入分组</a></li> --%>
<!-- 					      </ul> -->
<!-- 					    </div> -->
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/product/orgGroup/merchantList?orgType=4&groupId=${page.condition.groupId}&isRelated=${page.condition.isRelated}" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
												<td class="am-padding-sm am-text-sm am-text-right">名称</td>
												<td>
													<input type="text" class="am-input-sm"  name="merchantName"  value="${page.condition.merchantName }">
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
                                            <th>代理商</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="merchant" varStatus="status">
                                        <tr>
                                                <td>${merchant.merchantName}</td>
	                                            <td>
	                                                <div class="am-btn-group am-btn-group-xs">
	                                                	<c:if test="${page.condition.isRelated == 0 }">
								                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="pick('${merchant.orgId}',1)"><span class="am-icon-check"></span> 加入分组</a>
	                                                	</c:if>
	                                                	<c:if test="${page.condition.isRelated == 1 }">
								                      		<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="pick('${merchant.orgId}',0)"><span class="am-icon-trash-o"></span> 移出分组</a>
	                                                	</c:if>
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
    });
    
    
    function pick(itemId,status){
    	var target = event.srcElement;
   		$.post('${ctx}/product/orgGroup/merchantPick',{groupId:${page.condition.groupId},merchantId:itemId,status:status},function(result){
   			if(result.success){
   				layer.msg('操作成功',{icon: 1,time: 1000},function(){
					$(target).text('已加入').attr('disabled',true);
	           	});
   			}else{
   				window.alert(result.msg);
   			}
   		})
    }
</script>
</body>
</html>
