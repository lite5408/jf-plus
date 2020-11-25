//初始化执行任务
$(document).ready(function(){
	//监听页面大小改变
	$(window).bind("resize", resizeWindow);
	function resizeWindow(e) {
		try{
			$("iframe")[0].contentWindow.reloadPage();
		}catch(e){}
	}
});
//初始化表单
function initSearchForm(){
	$("#pageNo").val(0);
	return true;
}
//初始化下拉框的值 默认存储到data属性
function initSelectValue(flag) {
	$("select").each(function() {
		var data = $(this).attr("data");
		if (data != "" && data != undefined) {
			data = data.split(",");
			$(this).val(data);
		}
		if(flag){
			$(this).selected();
		}
	});
}
//过滤特殊字符
function filterCharacter(s) {
    var pattern = new RegExp("[` ~!@#$^&*()=|{}':;'\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]")
        var rs = "";
    for (var i = 0; i < s.length; i++) {
        rs = rs + s.substr(i, 1).replace(pattern, '');
    }
    return rs;
}
//去掉所有的html标记
function delHtmlTag(html){
	return html.replace(/<[^>]+>/g,"");
}
//询问对话框
function confirm(msg, href) {
	layer.confirm(msg, {
		shade:0.01,
		offset: '30px',
		btn : [ '确定', '取消' ]
	}, function() {
		window.location.href=href;
	});
	return false;
}

//弹出提示
//function alert(msg){
//	layer.alert(msg,{shade:0.001});
//}

//弹出消息提示
function showMsg(msg){
	top.layer.msg(msg,{offset: '55px',anim: -1});
}
//打开全屏框 是否显示顶部和底部
function openModel(title,url,hideTop,hideBar){
	if(hideTop){
		$(parent.document).find(".iutils-header").hide();
	}
	if(hideBar){
		$(parent.document).find(".iutils-navbar").hide();
	}
	var index = layer.open({
		type: 2,
		move: false,
		title: title,
		closeBtn:1,
		content: url,
		cancel: function(index){
			if(hideTop){
				$(parent.document).find(".iutils-header").show();
			}
			if(hideBar){
				$(parent.document).find(".iutils-navbar").show();
			}
			layer.close(index);
		}
	});
	layer.full(index);
}

//打开全屏框 是否显示顶部和底部
function openModelAdjust(title,url,hideTop,hideBar){
	var target = event.srcElement;
	if(hideTop){
		$(parent.document).find(".iutils-header").hide();
	}
	if(hideBar){
		$(parent.document).find(".iutils-navbar").hide();
	}
	var index = layer.open({
		shade: 0.6,
		offset: $(target).offset().top,
		type: 2,
		move: true,
		title: title,
		closeBtn:1,
		content: url,
		cancel: function(index){
			if(hideTop){
				$(parent.document).find(".iutils-header").show();
			}
			if(hideBar){
				$(parent.document).find(".iutils-navbar").show();
			}
			layer.close(index);
		}
	});
	layer.full(index);
}

//关闭弹出框
function closeModel(isRefresh,showTop,showBar){
	if(showTop){
		$(parent.parent.document).find(".iutils-header").show();
	}
	if(showBar){
		$(parent.parent.document).find(".iutils-navbar").show();
	}
	var index = parent.layer.getFrameIndex(window.name);
	if(isRefresh){
		//刷新列表页
		$(parent.document).find("#searchForm").submit();
	}
	parent.layer.close(index);
}
//重新加载页面
function reloadPage(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.full(index)
}
//ajax get数据
function get(url,call){
	$.get(url,function(data){
		call && call(data);
	});
}
//ajax post数据
function post(url,param,call){
	$.post(url,param, function (data) {
		call && call(data);
	});
}
//时间格式转换 var time = date.format('yyyy-MM-dd h:m:s');
Date.prototype.format = function(format) {
	var date = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S+": this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
	}
	for (var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
				? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}


//======================= validation =====================//

/* 
用途：检查输入的Email信箱格式是否正确 
输入：strEmail：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function checkEmail(strEmail) 
{
    //var emailReg = /^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}$/; 
    var emailReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    if ( emailReg.test(strEmail) ) {
        return true;
    }
    else {
        alert("您输入的Email地址格式不正确！");
        return false;
    }
};
 
/*
用途：校验ip地址的格式 
输入：strIP：ip地址 
返回：如果通过验证返回true,否则返回false； 
*/
function isIP(strIP) 
{
    if (isNull(strIP)) {
        return false;
    }
    var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式 
    if (re.test(strIP)) {
        if ( RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256) {
            return true;
        }
    }
    return false;
};
 
/* 
用途：检查输入手机号码是否正确 
输入：strMobile：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function checkMobile( strMobile )
{
    var regu = /^1[3-9][0-9]\d{8}$/;
    var re = new RegExp(regu);
    if (re.test(strMobile)) {
        return true;
    }
    else {
        return false;
    }
};
 
/* 
用途：检查输入的电话号码格式是否正确 
输入：strPhone：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function checkPhone( strPhone ) 
{
    var phoneRegWithArea = /^[0][1-9]{2,3}-[0-9]{5,10}$/;
    var phoneRegNoArea = /^[1-9]{1}[0-9]{5,8}$/;
    var prompt = "您输入的电话号码不正确!";
    if ( strPhone.length > 9 ) {
        if ( phoneRegWithArea.test(strPhone) ) {
            return true;
        }
        else {
            alert( prompt );
            return false;
        }
    }
    else {
        if ( phoneRegNoArea.test( strPhone ) ) {
            return true;
        }
        else {
            alert( prompt );
            return false;
        }
    }
};
 
/* 
用途：检查输入字符串是否为空或者全部都是空格 
输入：str 
返回：如果全是空返回true,否则返回false 
*/
function isNull( str )
{
    if ( str == "" ) {
        return true;
    }
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    return re.test(str);
};
 
/* 
用途：检查输入对象的值是否符合整数格式 
输入：str 输入的字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isInteger( str )
{
    var regu = /^[-]{0,1}[0-9]{1,}$/;
    return regu.test(str);
};
 
/* 
用途：检查输入字符串是否符合正整数格式 
输入：s：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isNumber( s )
{
    var regu = "^[0-9]+$";
    var re = new RegExp(regu);
    if (s.search(re) != - 1) {
        return true;
    }
    else {
        return false;
    }
};
 
/* 
用途：检查输入字符串是否是带小数的数字格式,可以是负数 
输入：str：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isDecimal( str )
{
    if (isInteger(str)) {
        return true;
    }
    var re = /^[-]{0,1}(\d+)[\.]+(\d+)$/;
    if (re.test(str)) {
        if (RegExp.$1 == 0 && RegExp.$2 == 0) {
            return false;
        }
        return true;
    }
    else {
        return false;
    }
};
 
/* 
用途：检查输入对象的值是否符合端口号格式 
输入：str 输入的字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isPort( str )
{
    return (isNumber(str) && str < 65536);
};
 
/* 
用途：检查输入字符串是否符合金额格式,格式定义为正数，小数点后最多三位 
输入：s：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isMoney( s )
{
    var regu = "^[0-9]+[\.]*[0-9]{0,3}$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    }
    else {
        return false;
    }
};
 
/* 
用途：检查输入字符串是否只由英文字母和数字和下划线组成 
输入：s：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isNumberOr_Letter( s )
{
    //判断是否是数字或字母 
    var regu = "^[0-9a-zA-Z\_]+$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    }
    else {
        return false;
    }
};
 
/* 
用途：检查输入字符串是否只由英文字母和数字组成 
输入：s：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isNumberOrLetter( s )
{
    //判断是否是数字或字母 
    var regu = "^[0-9a-zA-Z]+$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    }
    else {
        return false;
    }
};
 
/* 
用途：检查输入字符串是否只由汉字、字母、数字组成 
输入：s：字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isChinaOrNumbOrLett( s )
{
    //判断是否是汉字、字母、数字组成 
    var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    }
    else {
        return false;
    }
};

/*
 * 检查是否含有非法字符
 * @param temp_str
 * @returns {Boolean}
 */
function is_forbid(temp_str){
    temp_str = temp_str.replace(/(^\s*)|(\s*$)/g, "");
	temp_str = temp_str.replace('--',"@");
	temp_str = temp_str.replace('/',"@");
	temp_str = temp_str.replace('+',"@");
	temp_str = temp_str.replace('\'',"@");
	temp_str = temp_str.replace('\\',"@");
	temp_str = temp_str.replace('$',"@");
	temp_str = temp_str.replace('^',"@");
	temp_str = temp_str.replace('.',"@");
	temp_str = temp_str.replace(';',"@");
	temp_str = temp_str.replace('<',"@");
	temp_str = temp_str.replace('>',"@");
	temp_str = temp_str.replace('"',"@");
	temp_str = temp_str.replace('=',"@");
	temp_str = temp_str.replace('{',"@");
	temp_str = temp_str.replace('}',"@");
	var forbid_str = new String('@,%,~,&');
	var forbid_array = new Array();
	forbid_array = forbid_str.split(',');
	for(i=0;i<forbid_array.length;i++){
		if(temp_str.search(new RegExp(forbid_array[i])) != -1)
		return false;
	}
	return true;
}

/**
 * 判断是否是空
 * @param value
 */
function isEmpty(value){
	if(value == null || value == "" || value == "undefined" || value == undefined || value == "null"){
		return true;
	}
	else{
		value = (value+"").replace(/\s/g,'');
		if(value == ""){
			return true;
		}
		return false;
	}
}
 
/* 
用途：判断是否是日期 
输入：date：日期；fmt：日期格式 
返回：如果通过验证返回true,否则返回false 
*/
function isDate( date, fmt ) 
{
    if (fmt == null) {
        fmt = "yyyyMMdd";
    }
    var yIndex = fmt.indexOf("yyyy");
    if (yIndex ==- 1) {
        return false;
    }
    var year = date.substring(yIndex, yIndex + 4);
    var mIndex = fmt.indexOf("MM");
    if (mIndex ==- 1) {
        return false;
    }
    var month = date.substring(mIndex, mIndex + 2);
    var dIndex = fmt.indexOf("dd");
    if (dIndex ==- 1) {
        return false;
    }
    var day = date.substring(dIndex, dIndex + 2);
    if (!isNumber(year) || year > "2100" || year < "1900") {
        return false;
    }
    if (!isNumber(month) || month > "12" || month < "01") {
        return false;
    }
    if (day > getMaxDay(year, month) || day < "01") {
        return false;
    }
    return true;
};
function getMaxDay(year, month) 
{
    if (month == 4 || month == 6 || month == 9 || month == 11) {
        return "30";
    }
    if (month == 2) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return "29";
        }
        else {
            return "28";
        }
        return "31";;
    }
};
 
/* 
用途：字符1是否以字符串2结束 
输入：str1：字符串；str2：被包含的字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isLastMatch(str1, str2) 
{
    var index = str1.lastIndexOf(str2);
    if (str1.length == index + str2.length) {
        return true;
    }
    return false;
};
 
/* 
用途：字符1是否以字符串2开始 
输入：str1：字符串；str2：被包含的字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isFirstMatch(str1, str2) 
{
    var index = str1.indexOf(str2);
    if (index == 0) {
        return true;
    }
    return false;
};
 
/* 
用途：字符1是包含字符串2 
输入：str1：字符串；str2：被包含的字符串 
返回：如果通过验证返回true,否则返回false 
*/
function isMatch(str1, str2) 
{
    var index = str1.indexOf(str2);
    if (index ==- 1) {
        return false;
    }
    return true;
};
 
/* 
用途：检查输入的起止日期是否正确，规则为两个日期的格式正确，且结束如期>=起始日期 
输入：startDate：起始日期，字符串; endDate：结束如期，字符串 
返回：如果通过验证返回true,否则返回false 
*/
function checkTwoDate( startDate, endDate ) 
{
    if ( !isDate(startDate) ) {
        alert("起始日期不正确!");
        return false;
    }
    else if ( !isDate(endDate) ) {
        alert("终止日期不正确!");
        return false;
    }
    else if ( startDate > endDate ) {
        alert("起始日期不能大于终止日期!");
        return false;
    }
    return true;
};
 
/* 
用途：检查复选框被选中的数目 
输入：checkboxID：字符串 
返回：返回该复选框中被选中的数目 
*/
function checkSelect( checkboxID ) 
{
    var check = 0;
    var i = 0;
    if ( document.all(checkboxID).length > 0 ) 
    {
        for ( i = 0; i < document.all(checkboxID).length; i++ ) {
            if ( document.all(checkboxID).item( i ).checked ) {
                check += 1;
            }
        }
    }
    else {
        if ( document.all(checkboxID).checked ) {
            check = 1;
        }
    }
    return check;
}
function getTotalBytes(varField) 
{
    if (varField == null) {
        return - 1;
    }
    var totalCount = 0;
    for (i = 0; i < varField.value.length; i++) {
        if (varField.value.charCodeAt(i) > 127) {
            totalCount += 2;
        }
        else {
            totalCount++ ;
        }
    }
    return totalCount;
}
function getFirstSelectedValue( checkboxID )
{
    var value = null;
    var i = 0;
    if ( document.all(checkboxID).length > 0 )
    {
        for ( i = 0; i < document.all(checkboxID).length; i++ )
        {
            if ( document.all(checkboxID).item( i ).checked ) {
                value = document.all(checkboxID).item(i).value;
                break;
            }
        }
    }
    else {
        if ( document.all(checkboxID).checked ) {
            value = document.all(checkboxID).value;
        }
    }
    return value;
}
function getFirstSelectedIndex( checkboxID )
{
    var value = - 2;
    var i = 0;
    if ( document.all(checkboxID).length > 0 )
    {
        for ( i = 0; i < document.all(checkboxID).length; i++ ) {
            if ( document.all(checkboxID).item( i ).checked ) {
                value = i;
                break;
            }
        }
    }
    else {
        if ( document.all(checkboxID).checked ) {
            value = - 1;
        }
    }
    return value;
}
function selectAll( checkboxID, status )
{
    if ( document.all(checkboxID) == null) {
        return;
    }
    if ( document.all(checkboxID).length > 0 )
    {
        for ( i = 0; i < document.all(checkboxID).length; i++ ) {
            document.all(checkboxID).item( i ).checked = status;
        }
    }
    else {
        document.all(checkboxID).checked = status;
    }
}
function selectInverse( checkboxID ) 
{
    if ( document.all(checkboxID) == null) {
        return;
    }
    if ( document.all(checkboxID).length > 0 ) 
    {
        for ( i = 0; i < document.all(checkboxID).length; i++ ) 
        {
            document.all(checkboxID).item( i ).checked = !document.all(checkboxID).item( i ).checked;
        }
    }
    else {
        document.all(checkboxID).checked = !document.all(checkboxID).checked;
    }
}
function checkDate( value ) 
{
    if (value == '') {
        return true;
    }
    if (value.length != 8 || !isNumber(value)) {
        return false;
    }
    var year = value.substring(0, 4);
    if (year > "2100" || year < "1900") {
        return false;
    }
    var month = value.substring(4, 6);
    if (month > "12" || month < "01") {
        return false;
    }
    var day = value.substring(6, 8);
    if (day > getMaxDay(year, month) || day < "01") {
        return false;
    }
    return true;
};
 
/* 
用途：检查输入的起止日期是否正确，规则为两个日期的格式正确或都为空且结束日期>=起始日期 
输入：startDate：起始日期，字符串; endDate： 结束日期，字符串 
返回：如果通过验证返回true,否则返回false 
*/
function checkPeriod( startDate, endDate ) 
{
    if ( !checkDate(startDate) ) {
        alert("起始日期不正确!");
        return false;
    }
    else if ( !checkDate(endDate) ) {
        alert("终止日期不正确!");
        return false;
    }
    else if ( startDate > endDate ) {
        alert("起始日期不能大于终止日期!");
        return false;
    }
    return true;
};
 
/* 
用途：检查证券代码是否正确 
输入：secCode:证券代码 
返回：如果通过验证返回true,否则返回false 
*/
function checkSecCode( secCode ) 
{
    if ( secCode.length != 6 ) {
        alert("证券代码长度应该为6位");
        return false;
    }
    if (!isNumber( secCode ) ) {
        alert("证券代码只能包含数字");
        return false;
    }
    return true;
};
 
/*
function:cTrim(sInputString,iType) 
description:字符串去空格的函数 
parameters:iType：1=去掉字符串左边的空格;2=去掉字符串左边的空格;0=去掉字符串左边和右边的空格 
return value:去掉空格的字符串 
*/
function cTrim(sInputString, iType) 
{
    var sTmpStr = ' ';
    var i = - 1;
    if (iType == 0 || iType == 1) 
    {
        while (sTmpStr == ' ') {
            ++i;
            sTmpStr = sInputString.substr(i, 1);
        }
        sInputString = sInputString.substring(i);
    }
    if (iType == 0 || iType == 2) 
    {
        sTmpStr = ' ';
        i = sInputString.length;
        while (sTmpStr == ' ') {
            --i;
            sTmpStr = sInputString.substr(i, 1);
        }
        sInputString = sInputString.substring(0, i + 1);
    }
    return sInputString;
};

Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

function getFormatDate(date, pattern) {
    if (date == undefined) {
        date = new Date();
    }
    
    date = new Date(date);
    
    if (pattern == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    
    return date.format(pattern);
}

function deepClone(obj) {
	var result, oClass = isClass(obj);
	// 确定result的类型
	if (oClass === "Object") {
		result = {};
	} else if (oClass === "Array") {
		result = [];
	} else {
		return obj;
	}
	for (key in obj) {
		var copy = obj[key];
		if (isClass(copy) == "Object") {
			result[key] = arguments.callee(copy);// 递归调用
		} else if (isClass(copy) == "Array") {
			result[key] = arguments.callee(copy);
		} else {
			result[key] = obj[key];
		}
	}
	return result;
}


//返回传递给他的任意对象的类
function isClass(o) {
	if (o === null)
		return "Null";
	if (o === undefined)
		return "Undefined";
	return Object.prototype.toString.call(o).slice(8, -1);
}

function createEditor(target,params){
	// xhr 是 xmlHttpRequest 对象，IE8、9中不支持
    var editor = new wangEditor(target);
    editor.config = deepClone(wangEditor.config);
    
 	  // 上传图片（举例）
    editor.config.uploadImgUrl = params.uploadImgUrl;
    editor.config.uploadImgFileName = params.uploadImgFileName;

    // 配置自定义参数（举例）
    editor.config.uploadParams = params.uploadParams;
    
    if(params.onload){
    	editor.config.uploadImgFns.onload = params.onload;
    }else{
 		// 自定义load事件
	    editor.config.uploadImgFns.onload = function (resultText, xhr) {
	 			resultText = JSON.parse(delHtmlTag(resultText));
	 			if(resultText.ret == 1){
	 				var originalName = editor.uploadImgOriginalName || '';
	 				editor.command(null, 'insertHtml', '<img src="' + params.uploadParams.IMG_URL + '/' + resultText.data + '" alt="' + originalName + '" style="max-width:100%;"/>');
	 			}else{
	 				alert('上传失败，请稍后再试');
	 			}
	    };
    }

    // 自定义timeout事件
    editor.config.uploadImgFns.ontimeout = function (xhr) {
        alert('上传超时');
    };

    // 自定义error事件
    editor.config.uploadImgFns.onerror = function (xhr) {
        alert('上传错误');
    };
    
    editor.create();
    
    return editor;
}

$.fn.serializeJson=function(){  
    var serializeObj={};  
    var array=this.serializeArray();  
    var str=this.serialize();  
    $(array).each(function(){  
        if(serializeObj[this.name]){  
            if($.isArray(serializeObj[this.name])){  
                serializeObj[this.name].push(this.value);  
            }else{  
                serializeObj[this.name]=[serializeObj[this.name],this.value];  
            }  
        }else{  
            serializeObj[this.name]=this.value;   
        }  
    });  
    return serializeObj;  
}
