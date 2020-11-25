<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>采购事由设置</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<!-- treegrid -->
<link href="${ctxStatic }/3rd-lib/maxazan-jquery-treegrid-447d662/css/jquery.treegrid.css" rel="stylesheet" />
<script src="${ctxStatic }/3rd-lib/maxazan-jquery-treegrid-447d662/js/jquery.treegrid.min.js"></script>
<script src="${ctxStatic }/3rd-lib/maxazan-jquery-treegrid-447d662/js/jquery.treegrid.bootstrap3.js"></script>
<script src="${ctxStatic }/3rd-lib/maxazan-jquery-treegrid-447d662/extension/jquery.treegrid.extension.js"></script>
    
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">销售区域设置</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel('新增大区','${ctx}/setting/dict/create?dict=${page.entity.dict }')"><span class="am-icon-plus"></span> 新增大区
                                                </button>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/setting/dict/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input id="dict" name="dict" type="hidden" value="${page.entity.dict}"/>
                                </form>
                            </div>

                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                </table>
                            </div>
                            <div class="am-u-lg-12 am-cf">
<%--                                 <%@ include file="../../utils/pagination.jsp"%> --%>
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
        
        
        $('#contentTable').treegridData({
            id: 'id',
            parentColumn: 'pid',
            type: "GET", //请求数据的ajax类型
            url: '${ctx}/setting/dict/saleAreaList?dict=sale_area_dict',   //请求数据的ajax的url
            ajaxParams: {}, //请求数据的ajax的data属性
            expandColumn: null,//在哪一列上面显示展开按钮
            striped: true,   //是否各行渐变色
            bordered: true,  //是否显示边框
            expandAll: false,  //是否全部展开
            columns: [
                {
                    title: '编码',
                    field: 'key'
                },
                {
                    title: '名称',
                    field: 'val'
                },
                {
                    title: '类型',
                    field: 'type',
                    formatter:function(val){
                    	if(val == 1){
                    		return '<span class="am-badge am-badge-success am-radius">大区</span>';
                    	}else if(val == 2){
                    		return '<span class="am-badge am-badge-primary am-radius">省份</span>';
                    	}else if(val == 3){
                    		return '<span class="am-badge am-badge-default am-radius">城市</span>';
                    	}
                    	return val;
                    }
                },
                {
                    title: '启用',
                    field: 'avaliable',
                    formatter:function(val){
                    	if(val == 1){
                    		return '启用';
                    	}else if(val == 0){
                    		return '禁用';
                    	}
                    	return val;
                    }
                },
                {
                    title: '操作',
                    field: 'type',
                    formatter:function(val,item){
                    	var html = '';
                    	if(item["type"] < 3){
                    		html += '<a role="oper" class="am-btn am-btn-default am-btn-xs" href="javascript:openModel(false,\'${ctx}/setting/dict/create?dict=${page.entity.dict}&pid='+ item["id"] +'\')" title="添加下级"><span class="am-text-secondary am-icon-plus"> 添加下级</span></a>';
                    	}
                    	
                    	html += ' <a role="oper" class="am-btn am-btn-default am-btn-xs" href="javascript:openModel(false,\'${ctx}/setting/dict/update?dict=${page.entity.dict}&id='+ item["id"] +'\')" title="编辑"><span class="am-text-secondary am-icon-pencil"> 编辑</span></a>';
                    	return html;
                    }
                }
            ]
        });
    });
</script>
</body>
</html>
