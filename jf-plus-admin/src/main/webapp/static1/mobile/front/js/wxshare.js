function initConfing(wxConfigUrl,sharePic,shareFriendTitle,shareOtherTile,shareOtherDesc){
	var serverUrl= window.location.href;
	var search = "http://"+window.location.host;
	var shareUrl = serverUrl.substring(serverUrl.indexOf(search)+search.length);
	if(wxConfigUrl.indexOf("http://")==-1){
		jQuery.ajax({
			url: wxConfigUrl,
	 		data:{"params" : shareUrl},
	 		type: "POST",
	 		success: function(result) {
	 			if (result.success) {
	 				config(true,result.obj.appId,result.obj.timestamp,result.obj.nonceStr,result.obj.signature,result.obj.url,sharePic,shareFriendTitle,shareOtherTile,shareOtherDesc);
	 			}else{
	 				console.log(result.msg);
	 			}
	 		},
	 		error: function(result) {
	 			console.log("系统繁忙，请稍后再试。");	
	 		}
	 	});
	}else{
		  $.ajax({  
              type: 'GET',  
              url: wxConfigUrl,  
              dataType:'jsonp',  
              data:{  
                      "params" : shareUrl,
              },  
              jsonp:'jsonpcallback',  
              error: function(XmlHttpRequest,textStatus,errorThrown){  
            	  console.log("系统繁忙请联系管理员");   
              },  
              success: function(result){
            	  console.log(result);
            	  config(true,result.obj.appId,result.obj.timestamp,result.obj.nonceStr,result.obj.signature,result.obj.url,sharePic,shareFriendTitle,shareOtherTile,shareOtherDesc);
              }         
            });
	}
}

function config(debug,appId,timestamp,nonceStr,signature,link,sharePic,shareFriendTitle,shareOtherTile,shareOtherDesc){
	//var piclink = "http://"+window.location.host+'/static/images/about/share.jpg';
	var piclink = sharePic;
 	wx.config({
 	    debug: false,
 	    appId: appId,
 	    timestamp: timestamp,
 	    nonceStr: nonceStr,
 	    signature: signature,
 	    jsApiList: [
 			'checkJsApi',
 			'onMenuShareTimeline',
 			'onMenuShareAppMessage',
 			'onMenuShareQQ',
 			'onMenuShareWeibo',
 			'onMenuShareQZone'
 	    ]
 	});

 	wx.ready(function () {
       wx.onMenuShareTimeline({
         title: shareFriendTitle,  //分享标题
         link: link,  //分享链接
         imgUrl: piclink,  //分享图标
         trigger: function (res) {
            //console.log('用户点击分享到朋友圈');
         },
         success: function (res) { 
         },
         cancel: function (res) {
         },
         fail: function (res) {
             //console.log(JSON.stringify(res));
         }
       });
      
       wx.onMenuShareAppMessage({
     	    title: shareOtherTile,  //分享标题
     	    desc: shareOtherDesc,  //分享描述
     	    link: link,  //分享链接
     	    imgUrl: piclink,  //分享图标
     	    trigger: function (res) {
                // console.log('用户点击分享给朋友');
             },
     	    success: function () { 
     	    },
     	    cancel: function () { 
     	    }
     	});
			
     });
	
 	wx.onMenuShareQQ({
 		title: shareOtherTile,  //分享标题
     	desc: shareOtherDesc,  //分享描述
  	    link: link,  //分享链接
  	    imgUrl: piclink,  //分享图标
 	    success: function () { 
 	        //用户确认分享后执行的回调函数
 	    },
 	    cancel: function () { 
 	        //用户取消分享后执行的回调函数
 	    }
 	});
	
 	wx.onMenuShareWeibo({
 		title: shareOtherTile,  //分享标题
     	desc: shareOtherDesc,  //分享描述
  	    link: link,  //分享链接
  	    imgUrl: piclink,  //分享图标
 	    success: function () { 
 	        //用户确认分享后执行的回调函数
 	    },
 	    cancel: function () { 
 	        //用户取消分享后执行的回调函数
 	    }
 	});
	
 	wx.onMenuShareQZone({
 		title: shareOtherTile,  //分享标题
     	desc: shareOtherDesc,  //分享描述
  	    link: link,  //分享链接
  	    imgUrl: piclink,  //分享图标
 	    success: function () { 
 	        //用户确认分享后执行的回调函数
 	    },
 	    cancel: function () { 
 	        //用户取消分享后执行的回调函数
 	    }
 	});
	 
 	wx.error(function (res) {
 		console.log(res.errMsg);
 	});
 }