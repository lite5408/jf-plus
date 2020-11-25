<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>渠道商品分类</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<link href="${ctxStatic}/3rd-lib/treeTable/vsStyle/jquery.treeTable.css" type="text/css" rel="stylesheet" charset="UTF-8"></link>
<script src="${ctxStatic}/3rd-lib/treeTable/jquery.treeTable.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">渠道商品分类</div>
                        </div>
                        <div data-am-widget="tabs" class="am-tabs am-tabs-default am-u-sm-7" >
					      <ul class="am-tabs-nav am-cf">
					          <li class="${mallChannelCate.channelType == 1 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mall/mallChannelCate/list?channelType=1'">京东</a></li>
					          <li class="${mallChannelCate.channelType == 3 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mall/mallChannelCate/list?channelType=3'">苏宁</a></li>
					          <li class="${mallChannelCate.channelType == 5 ? 'am-active':'' }"><a href="javascript:" onclick="location.href='${ctx}/mall/mallChannelCate/list?channelType=5'">齐心</a></li>
					      </ul>
					  </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs" style="display:none;">
                                        <shiro:hasPermission name="mall:mallChannelCate:create">
                                        <button type="button" class="am-btn am-btn-default am-btn-success"
                                                onclick="openModel(false,'${ctx}/mall/mallChannelCate/create')"><span class="am-icon-plus"></span> 新增
                                        </button>
                                        </shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/mall/mallChannelCate/list" method="post" style="display:none">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="pageSize" name="channelType" type="hidden" value="${mallChannelCate.channelType}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">渠道类名</span>
											  <input type="text" class="am-form-field"  name="catName"  value="${mallChannelCate.catName }">
											</div>
                                        </div>
                                        <span class="am-input-group-btn">
                                            <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="submit" onclick="initSearchForm()"></button>
                                        </span>
                                    </div>
                                </form>
                            </div>

                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th>渠道类别</th>
                                            <th>对应商品类别</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${page.list}" var="cate">
							              <tr id="${cate.id}" pid="${cate.pid}" >
							                <td><span controller="true">${cate.name }</span></td>
							                <td>${cate.catName }</td>
							                <td>
							                  <div class="am-btn-toolbar">
							                    <div class="am-btn-group am-btn-group-xs">
							                    	<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="openModel('关联分类','${ctx}/mall/mallChannelCate/relatedCate?id=${cate.id }')"><span class="am-icon-pencil-square-o"></span> 关联分类</a>
							                    </div>
							                  </div>
							                </td>
							              </tr>
						              </c:forEach>
                                    </tbody>
                                </table>
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
        
        
        var option = {
                theme:'vsStyle',
                expandLevel : 1,
                beforeExpand : function($treeTable, id) {
                    //判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
                    if ($('.' + id, $treeTable).length) { return; }
                    //这里的html可以是ajax请求
                    var html = '';
    				
    				$.post('${ctx}/mall/mallChannelCate/childNodes',{catPid:id,channelType:'${mallChannelCate.channelType}'},function(result){
    					if(result.success){
    						var childNodes = result.obj;
    						if(childNodes == undefined){
    							top.layer.msg("已是末级");
    							return false;
    						}

    						for( var i = 0; i < childNodes.length; i++){
    							var child = childNodes[i];
    							var btn = '<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="openModel(\'关联分类\',\'${ctx}/mall/mallChannelCate/relatedCate?id='+ child.id +'\')"><span class="am-icon-pencil-square-o"></span> 关联分类</a>';
    							
    							html += '<tr id="'+child.id+'" pid="'+child.catPid+'" ><td><span controller="true">'+child.catName+'</span></td><td><span>'+child.productCateName+'</span></td><td>'
    				  	              + '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">'+btn+'</div></div></td></tr>';
    						}
    						$treeTable.addChilds(html);
    					}else{
    						top.layer.msg(result.msg);
    					}
    				})
    				
                },
                onSelect : function($treeTable, id) {
                    //window.console && console.log('onSelect:' + id);
                }

            };
            $('#contentTable').treeTable(option);
    });
</script>
</body>
</html>
