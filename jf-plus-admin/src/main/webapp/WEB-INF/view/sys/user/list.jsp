<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <%@ include file="../../include/head.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/css/zTreeStyle.css">
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
        .theme-black .widget .ztree li a{color: #ffffff}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">组织机构</div>
                        </div>
                        <div class="widget-body widget-body-lg am-fr">
                            <div class="am-scrollable-horizontal">
                                <ul id="tree" class="ztree"></ul><!-- 组织机构列表 -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">用户列表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-2 am-u-md-2 am-u-lg-2">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="sys:user:create">
                                            <button type="button" class="am-btn am-btn-default am-btn-success"
                                                    onclick="openModel('创建用户','${ctx}/user/create?organizationId=${page.entity.organizationId}')"><span class="am-icon-plus"></span> 新增
                                            </button>
                                        </shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-10 am-u-md-10 am-u-lg-10">
                                <form id="searchForm" action="${ctx}/user/list?organizationId=${page.entity.organizationId}" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                    	<div class="am-u-sm-12 am-u-md-3 am-u-lg-2 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">姓名</span>
											  <input type="text" class="am-form-field"  name="name"  value="${page.entity.name }">
											</div>
                                        </div>
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-2 tagsinput nobg">
                                        	<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">账号</span>
											  <input type="text" class="am-form-field" name="username" value="${page.entity.username }">
											</div>
                                        </div>
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-2 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">状态</span>
											  <select name="locked" data="${page.entity.locked}">
											  	  <option value="">状态</option>
											  	  <option value="false">在职</option>
											  	  <option value="true">离职</option>
											  </select>
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
                                        <th>账号</th>
                                        <th>姓名</th>
                                        <th>归属机构</th>
                                        <th>所属角色组</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="user" varStatus="status">
                                        <tr>
                                            <td>${user.username}</td>
                                            <td>${user.name}</td>
                                            <td>${user.organizationNames}</td>
                                            <td>${user.roleGroupNames}</td>
                                            <td>${user.locked?'<span class="am-badge am-badge-danger am-radius">离职</span>':'<span class="am-badge am-badge-success am-radius">在职</span>'}</td>
                                            <td>
                                                <shiro:hasPermission name="sys:user:update"><a href="javascript:;" onclick="openModel(false,'${ctx}/user/update?id=${user.id}')"
                                                                                               title="编辑"><span class="am-icon-pencil"></span></a></shiro:hasPermission>
                                                <shiro:hasPermission name="sys:user:delete"><c:if test="${user.id!=1 && user.id!=fnc:getLoginUser().id}">
                                                    <a href="${ctx}/user/delete?id=${user.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"
                                                       onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span
                                                            class="am-text-danger am-icon-trash-o"></span></a>
                                                </c:if></shiro:hasPermission>
                                                <a href="javascript:;" onclick="openModel('修改密码解绑','${ctx}/user/${user.id}/changePassword')"
                                                   title="改密"><span class="am-text-success am-icon-key"></span></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="../../utils/pagination.jsp" %>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<form id="impForm" action="${ctx }/import/user/excel" method="post" enctype="multipart/form-data">
    <div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
      <div class="am-modal-dialog">
        <div class="am-modal-hd">导入用户</div>
        <div class="am-modal-bd">
          <input type="file" name="file" id="uploadFile">
        </div>
        <div class="am-modal-footer">
          <span class="am-modal-btn" data-am-modal-cancel>取消</span>
          <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>
      </div>
    </div>
</form>

<%@ include file="../../include/bottom.jsp"%>
<script src="${ctxStatic}/3rd-lib/jquery-ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if (msg != '') {
            showMsg(msg);
        }
        
        $('#impBtn').on('click', function() {
            var $promt = $('#my-prompt').modal({
              relatedTarget: this,
              onConfirm: function(e) {
              	var fileName = $('#uploadFile').val();
  	        	if(fileName == ''){
  	        		alert('请选择要上传的文件');
  	        		//$(this).modal('open');
  	        		return false;
  	        	}
  	        	
  	        	var ext = fileName.substring(fileName.lastIndexOf('.')+1).toUpperCase();
  	        	if(ext != 'XLS' && ext != 'XLSX'){
  	        		alert('请上传excel格式文件');
  	        		//$(this).modal('open');
  	        		return false;
  	        	}
  	        	
  	        	$('#impForm').submit();
              },
              onCancel: function(e) {
              	$('#uploadExcel').val('');
              }
            });

            $promt.find('[data-am-modal-confirm]').off('click.close.modal.amui');
        });
    });
</script>
<script>
    $(function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback : {
                onClick : function(event, treeId, treeNode) {
                    location.href = "${ctx}/user/list?organizationId="+treeNode.id;
                }
            }
        };
        var zNodes =[
            <c:forEach items="${organizationList}" var="o" varStatus="status">
            { id:${o.id}, pId:${o.parentId}, name:"${o.name}", open:${o.rootNode}}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];
        $(document).ready(function(){
            var ztree = $.fn.zTree.init($("#tree"), setting, zNodes);
            //ztree.expandAll(true);
            //展开2级
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }
        });
    });
</script>
</body>
</html>