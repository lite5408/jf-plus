<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>机构资金</title>
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
                            <div class="widget-title am-fl">机构资金</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="account:orgAccount:batchimp">
                                        <button type="button" class="am-btn am-btn-default am-btn-success" id="impBtn"><span class="am-icon-file-excel-o"></span> 批量充值
                                        </button>
                                        <a href="${ctxStatic }/fmt/机构充值模板.xls" class="am-btn">模板下载</a>
                                        </shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/account/orgAccount/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">机构名</span>
											  <input type="text" class="am-form-field" placeholder="编码或名称"  name="orgName"  value="${page.entity.orgName }">
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
                                            <th>编号</th>
                                            <th>机构名称</th>
                                            <th>累计充值</th>
                                            <th>已用额度</th>
                                            <th>可用额度</th>
                                            <th>发货金额</th>
                                            <th>上级</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orgAccount" varStatus="status">
                                        <tr>
                                                <td>${orgAccount.orgNo}</td>
                                                <td>${orgAccount.orgName}</td>
                                                <td id="purchaseTotalAmount${status.index}"><fmt:formatNumber value="${orgAccount.purchaseTotalAmount}" pattern="#.##" minFractionDigits="2" /></td>
                                                <td id="usedAmount${status.index}"></td>
                                                <td id="purchaseBlance${status.index}"><fmt:formatNumber value="${orgAccount.purchaseBlance}" pattern="#.##" minFractionDigits="2" /></td>
                                                <td>
                                                	<p>已发货：${orgAccount.hasFhAmount }</p>
                                                	<p>未发货：${orgAccount.notFhAmount }</p>
                                                </td>
                                                <td>${orgAccount.parentOrgName }</td>
	                                            <td>
								                    <div class="am-btn-group am-btn-group-xs">
								                      <shiro:hasPermission name="account:orgAccount:update">
								                      	<a role="oper" href="javascript:" onclick="openModel('充值','${ctx}/account/orgAccount/recharge?id=${orgAccount.id}&organizationId=${orgAccount.organizationId }')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 充值</a>
								                      </shiro:hasPermission>
								                      
								                      <shiro:hasPermission name="account:orgAccount:update">
								                      	<a role="oper" href="javascript:" onclick="openModel('明细','${ctx}/account/orgAccountRecharge/list?accountId=${orgAccount.id }')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-file-text-o"></span> 明细</a>
								                      </shiro:hasPermission>
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
<form id="impForm" action="${ctx }/import/orgaccount/excel" method="post" enctype="multipart/form-data">
    <div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
      <div class="am-modal-dialog">
        <div class="am-modal-hd">批量充值</div>
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
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
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
        
        for (var i = 0; i < ${page.pageSize}; i++) {
        	var usedAmount = $("#purchaseTotalAmount"+i).html()-$("#purchaseBlance"+i).html();
			$("#usedAmount"+i).html(usedAmount.toFixed(2));
		}
    });
</script>
</body>
</html>
