<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><c:set var="iutilsName" value='${fnc:getConfig("iutils.name")}' />${iutilsName} - 后台管理</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/custom/css/amazeui.select.css">
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，平台暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 头部 -->
    <header>
        <!-- logo -->
        <div class="am-fl tpl-header-logo">
            <a href="${ctxS}">
<%--             	<img src="${ctxStatic}/assets/img/logo.png" alt=""> --%>
供应商管理系统
            </a>
        </div>
        <!-- 右侧内容 -->
        <div class="tpl-header-fluid">
            <!-- 侧边切换 -->
            <div class="am-fl tpl-header-switch-button am-icon-list">
                    <span>
                </span>
            </div>
            <!-- 搜索 -->
            <div class="am-fl tpl-header-search">
                <form class="tpl-header-search-form" action="javascript:;" style="display:none;">
                    <button class="tpl-header-search-btn am-icon-search"></button>
                    <input class="tpl-header-search-box am-select-ui-input" type="text" placeholder="功能检索...">
                    <ul class="am-select-ui" style="width: 240px;margin-top: 5px;margin-left: 15px;"></ul>
                </form>
            </div>
            <!-- 其它功能-->
            <div class="am-fr tpl-header-navbar">
                <ul>
                    <!-- 欢迎语 -->
                    <li class="am-text-sm tpl-header-navbar-welcome">
                        <a href="#" onclick="">欢迎你, <span>${session_supply_user.supplyName}</span> </a>
                    </li>
                    <!-- 退出 -->
                    <li class="am-text-sm">
                        <a href="${ctxS}/logout" onclick="return confirm('确认要退出吗？', this.href)">
                            <span class="am-icon-sign-out"></span> 退出
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </header>

    <!-- 风格切换 -->
    <div class="tpl-skiner" style="display:none;">
        <div class="tpl-skiner-toggle am-icon-cog">
        </div>
        <div class="tpl-skiner-content">
            <div class="tpl-skiner-content-title">
                选择主题
            </div>
            <div class="tpl-skiner-content-bar">
                <span class="skiner-color skiner-white" data-color="theme-white"></span>
                <span class="skiner-color skiner-black" data-color="theme-black"></span>
            </div>
        </div>
    </div>

    <!-- 侧边导航栏 -->
    <div class="left-sidebar">
    	<!-- 用户信息 -->
        <div class="tpl-sidebar-user-panel">
            <div class="tpl-user-panel-slide-toggleable">
                <div class="tpl-user-panel-profile-picture">
                    <img src="${ctxStatic }/login/images/app_icon.png" alt="用户头像">
                </div>
				<div class="am-dropdown" data-am-dropdown>
				  <button class="am-btn am-btn-xs am-btn-link am-dropdown-toggle" data-am-dropdown-toggle style="text-decoration: none;"><i class="am-icon-circle-o am-text-success tpl-user-panel-status-icon"></i> ${session_supply_user.adminLoginName} <span class="am-icon-caret-down"></span></button>
				  <ul class="am-dropdown-content">
				    <li><a class="am-text-sm" href="#${ctxS}/changePassword" onclick="link('${ctxS}/changePassword')">修改密码</a></li>
				  </ul>
				</div>
            </div>
        </div>
        <!-- 菜单 -->
        <ul class="sidebar-nav">
            <li class="sidebar-nav-heading">功能菜单</li>
            <li class="sidebar-nav-link">
                 <a href="javascript:;" class="sidebar-nav-sub-title menu-link-clear">
                         <img src="${ctxStatic }/assets/i/menu.png" style="width:16px;height:16px;margin-right:8px;"> 商品管理
                     <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico  sidebar-nav-sub-ico-rotate"></span>
                 </a>
                 <ul class="sidebar-nav sidebar-nav-sub">
                    <li class="sidebar-nav-link">
                        <a href="#${ctxS}/product" class="menu-link menu-link-clear" level="2">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 商品管理
                        </a>
                    </li>
                 </ul>
             </li>
             <li class="sidebar-nav-link">
                 <a href="javascript:;" class="sidebar-nav-sub-title menu-link-clear">
                         <img src="${ctxStatic }/assets/i/menu.png" style="width:16px;height:16px;margin-right:8px;"> 订单管理
                     <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico  sidebar-nav-sub-ico-rotate"></span>
                 </a>
                 <ul class="sidebar-nav sidebar-nav-sub">
                    <li class="sidebar-nav-link">
                        <a href="#${ctxS}/order" class="menu-link menu-link-clear" level="2">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 订单管理
                        </a>
                    </li>
                 </ul>
<!--                  <ul class="sidebar-nav sidebar-nav-sub"> -->
<!--                     <li class="sidebar-nav-link"> -->
<!--                         <a href="#${ctxS}/order/report" class="menu-link menu-link-clear" level="2"> -->
<!--                             <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 订单报表 -->
<!--                         </a> -->
<!--                     </li> -->
<!--                  </ul> -->
             </li>
        </ul>
    </div>

    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <iframe name="main-content" id="main-content" src="${ctxS}/welcome" style="width:100%;" scrolling="no" frameborder="no"></iframe>
    </div>
</div>
</div>
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
<script src="${ctxStatic}/3rd-lib/tpl/tpl.js"></script>
<script>
    $(document).ready(function(){
    	//点击左侧菜单
        $(".sidebar-nav").on('click','.menu-link',function(){
            //清除菜单选中状态
            $(".menu-link-clear").removeClass("active").removeClass("sub-active");
            var $menu = $(this);
            var level = $menu.attr("level");
            if(level=='1'){
                $menu.addClass("active");
            }else if(level=='2'){
                $menu.addClass("sub-active");
                $menu.parent().parent().prev().addClass("active");
            }
            $("#main-content").attr("src",$menu.attr("href").substr(1));
        });

    });

    //跳转
    function link(url){
        $(".menu-link-clear").removeClass("active").removeClass("sub-active");
        $("#main-content").attr("src",url);
    }
    
    $('.sidebar-nav-sub-title').click();
    
</script>
</body>
</html>