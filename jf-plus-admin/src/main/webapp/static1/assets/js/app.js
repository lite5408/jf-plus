$(function() {
    autoLeftNav();
    $(window).resize(function() {
        autoLeftNav();
    });
});

// 风格切换
$('.tpl-skiner-toggle').on('click', function() {
    $('.tpl-skiner').toggleClass('active');
})
$('.tpl-skiner-content-bar').find('span').on('click', function() {
    var $this = $(this);
    var dataColor = $this.attr('data-color');
    $('body').attr('class', dataColor);//改变本业
    $('#main-content').contents().find('body').attr('class', dataColor);//改变子页
    $('#main-content').contents().find('iframe').each(function(){
        $(this).contents().find('body').attr('class', dataColor);//改变子页的子页
    });
    saveSelectColor.Color = $(this).attr('data-color');
    // 保存选择项
    storageSave(saveSelectColor);
})

// 侧边菜单开关
function autoLeftNav() {
    $('.tpl-header-switch-button').on('click', function() {
        if ($('.left-sidebar').is('.active')) {
            if ($(window).width() > 980) {
                $('.tpl-content-wrapper').removeClass('active');
            }
            $('.left-sidebar').removeClass('active');
        } else {
            $('.left-sidebar').addClass('active');
            if ($(window).width() > 980) {
                $('.tpl-content-wrapper').addClass('active');
            }
        }
    })
    if ($(window).width() < 980) {
        $('.left-sidebar').addClass('active');
    } else {
        $('.left-sidebar').removeClass('active');
    }
}

// 侧边菜单
$('.sidebar-nav-sub-title').on('click', function() {
	$(".menu-link-clear").removeClass("active").removeClass("sub-active");
	 
    $(this).siblings('.sidebar-nav-sub').slideToggle(80)
        .end()
        .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
    
    $(this).parent().siblings('.sidebar-nav-link').each(function(){
    	$(this).find('ul').hide(200);
    	$(this).find('.sidebar-nav-sub-ico').removeClass('sidebar-nav-sub-ico-rotate')
    })
    
})
