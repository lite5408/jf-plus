<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>导入结果</title>
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
                            <div class="widget-title am-fl">批量导入</div>
                        </div>
                        <div class="widget-body am-fr">
				          <div class="am-btn-toolbar">
				            <div class="am-btn-group am-btn-group-xs">
				              <a href="${ctx }/product/orgGroup" class="am-btn am-btn-xs am-btn-primary"> 返回</a>
				            </div>
				          </div>
				        </div>
                        <div class="widget-body am-fr">
							  <div class="am-panel am-panel-default">
							  	<div class="am-panel-bd">
							  		<c:if test="${result.success eq true}">
							  			导入成功，共${fn:length(result.obj)}条。
							  		</c:if>
							  		<c:if test="${result.success eq false}">
							  			导入失败：${result.msg }，失败数据如下。
							  		</c:if>
							  	</div>
							  	<c:if test="${result.code == 403 }">
								  	<table class="am-table am-table-compact am-table-striped tpl-table-black">
						              <thead>
						              	<tr>
											<th class="table-title">代理商编码</th>
											<th class="table-title">代理商名称</th>
											<th class="table-title">失败原因</th>
										</tr>
						              </thead>
						              <tbody>
							              <c:forEach items="${result.obj}" var="rowList">
								              <tr>
								              	<td>${rowList[0]}</td>
								              	<td>${rowList[1]}</td>
								              	<td><span class="error">${rowList[2]}</span></td>
								              </tr>
							              </c:forEach>
						              </tbody>
						            </table>
					            </c:if>
							  </div>
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
    });
</script>
</body>
</html>
