<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>代理商分组表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
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
                            <div class="widget-title am-fl">代理商分组</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/product/orgGroup/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
	                                        	 <a href="${ctxStatic }/fmt/代理商导入模板.xls" class="am-link am-text-sm am-margin-left-sm">模板下载</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/product/orgGroup/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                            <tr>
                                                <td class="am-padding-sm am-text-sm am-text-right">分组名称</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="groupName"  value="${page.entity.groupName}">
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
                                            <th>ID</th>
                                            <th>分组名称</th>
                                            <th>所属公司</th>
                                            <th>排序</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orgGroup" varStatus="status">
                                        <tr>
                                                <td>${orgGroup.id}</td>
                                                <td>${orgGroup.groupName}</td>
                                                <td>${orgGroup.orgName}</td>
                                                <td>${orgGroup.sort}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改代理商分组','${ctx}/product/orgGroup/update?id=${orgGroup.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
                                                    <a name="impBtn" data-id="${orgGroup.id }" role="oper" href="javascript:;" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-file-excel-o"></span> 导入代理商</a>
                                                    <a role="oper" href="javascript:;" onclick="openModel('查看代理商','${ctx}/product/orgGroup/merchantList?orgType=4&groupId=${orgGroup.id}&isRelated=1')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 查看代理商</a>
                                                    <a role="oper" href="${ctx}/product/orgGroup/${orgGroup.id}/delete?pageNo=${page.pageNo}&pageSize=${page.pageSize}" onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除" class="am-btn am-btn-default am-btn-xs am-text-danger"><span class="am-text-danger am-icon-trash-o"></span> 删除</a>
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
<form id="impForm" action="${ctx }/import/orggroup/excel" method="post" enctype="multipart/form-data">
    <input type="hidden" name="orgGroupId" value="0">
    <div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
      <div class="am-modal-dialog">
        <div class="am-modal-hd">导入代理商</div>
        <div class="am-modal-bd">
          <input onchange="submitImpForm()" type="file" name="file" id="uploadFile" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <div class="am-modal-footer">
          <span class="am-modal-btn" data-am-modal-cancel>取消</span>
          <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>
      </div>
    </div>
</form>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
        
        $('a[name=impBtn]').on('click', function() {
        	
			$('#impForm input[name=orgGroupId]').val($(this).attr('data-id'));
            $('#uploadFile').click();
        });
    });
    
    function submitImpForm(){
    	$('#impForm').submit();
    }
</script>
</body>
</html>
