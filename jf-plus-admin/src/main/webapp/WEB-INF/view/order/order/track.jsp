<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>供应商</title>
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
                        <div class="widget-body am-fr">
						        	<c:if test="${empty orderTrack}">
						        		该订单没有配送信息
						        	</c:if>
						        	<c:forEach items="${orderTrack }" var="track" varStatus="status">
						        		<div class="am-panel-group" id="accordion">
										  <div class="am-panel am-panel-default">
										    <div class="am-panel-hd">
										      <h4 class="am-panel-title" data-am-collapse="{parent: '#accordion', target: '#do-not-say-${status.index }'}">
										        包裹${status.index + 1 }
										      </h4>
										    </div>
										    <div id="do-not-say-${status.index }" class="am-panel-collapse am-collapse <c:if test="${status.index == 0 }">am-in</c:if>">
										      <div class="am-panel-bd">
										        <c:forEach items="${track.itemList }" var="logist">
										        	<p class="am-text-sm">${logist.msgTime } - ${logist.content }</p>
										        </c:forEach>
										      </div>
										    </div>
										  </div>
									  </div>
						        	</c:forEach>
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
            closeModel(true);//关闭窗口
        }
    });
</script>
</body>
</html>
