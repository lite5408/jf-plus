/*商品数量加减*/

var shopNumLimit = 10;

$(function(){
	setTotal();
	//全选反选
	$('.cart-all').on('click',function(){
		$(this).toggleClass("on");
		if($('.cart-all').hasClass('on')){
			$('.cart ul .check').addClass('on');
		}else{
			$('.cart ul .check').removeClass('on');
		}
		setTotal();
	});
	
	function setTotal(){
		var total = 0;
		$('.check[class*=on][role=child]').each(function(){
			total += parseInt($(this).parent().find('input[class*=text_box]').val()) * parseFloat($(this).parent().find('i[class*=price]').text());
		});
		
		//无数据
		if($('.check[role=child]').length == 0){
			$('div[role=no-data]').show();
		}else{
			$('div[role=no-data]').hide();
		}
		
		$("#total").html(total.toFixed(0));
	}
	
	$(".cart .increase").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		t.val(parseInt(t.val()) + 1)
		if(parseInt(t.val()) > shopNumLimit){
			tips('最多只能购买10件');
			t.val(10);
		}
		setTotal();
		
		updShopCart($(this).attr('item_id'),t.val());
	})
	
	$(".cart .decrease").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		t.val(parseInt(t.val()) - 1)
		if(parseInt(t.val()) < 0) {
			t.val(1);
		}
		setTotal();
	
		updShopCart($(this).attr('item_id'),t.val());
	})
	
	$('.cart-del').on('click', function() {
		$('#my-confirm').modal({
			relatedTarget: this,
			onConfirm: function(options) {
					var item_id = $(this.relatedTarget).attr('item_id');
					var supply_id = $('div[item_id='+ item_id +']').attr('supply_id');
					$('div[id=c_'+ item_id +']').remove();
					var cLength = $('div[role=child][supply_id='+ supply_id +']').length;
					if(cLength == 0){
						$('li[supply_id='+ supply_id +']').remove();
					}
					delShopCart(item_id,1);
					
					setTotal();
				}
				// closeOnConfirm: false,

		});
	});
	
	$('.check[role=child]').on('click',function(){
		$(this).toggleClass('on');
		var checkOnLength = $('.cart ul .check[class*=on]').length;
		var checkLength = $('.cart ul .check').length;
		if(checkOnLength == checkLength){
			$('.cart-all').addClass('on');
		}else{
			$('.cart-all').removeClass('on');
		}
		
		setTotal();
	})
	
	$('.check[role=parent]').on('click',function(){
		$(this).toggleClass('on');
		if($(this).hasClass('on')){
			$('.check[role=child][supply_id='+$(this).attr('supply_id') +']').addClass('on');
		}else{
			$('.check[role=child][supply_id='+$(this).attr('supply_id') +']').removeClass('on');
		}
		
		var checkOnLength = $('.cart ul .check[class*=on]').length;
		var checkLength = $('.cart ul .check').length;
		if(checkOnLength == checkLength){
			$('.cart-all').addClass('on');
		}else{
			$('.cart-all').removeClass('on');
		}
		setTotal();
	});
	
	$('span[role=opt_del]').on('click',function(){
		$('div[role=child][supply_id='+ $(this).attr('supply_id') +']').each(function(){
			delShopCart($(this).attr('item_id'),1);
			$('div[id=c_'+ $(this).attr('item_id') +']').remove();
		});
		$('li[supply_id='+ $(this).attr('supply_id') +']').remove();
		setTotal();
	});
	
});

function addShopCart(itemId,num){
	$.post(CONSTANTS_URL.SYNC_CART,{siteProductId:itemId,shopNum:num,oper:'add'},function(result){
		tips(result.msg)
	})
}

function updShopCart(itemId,num){
	$.post(CONSTANTS_URL.SYNC_CART,{siteProductId:itemId,shopNum:num,oper:'upd'},function(result){
		//tips('加入成功')
	})
}

function delShopCart(itemId,num){
	$.post(CONSTANTS_URL.SYNC_CART,{siteProductId:itemId,shopNum:num,oper:'del'},function(result){
		//tips('加入成功')
	})
}


function toOrderMulti(){
	var postdata = [];
	var isLimit = false,isSame = true;
	var supplyIds = [];
	
	$('.check[class*=on][role=child]').each(function(){
		var cartItem = {};
		cartItem.shopNum = $(this).parent().find('input[class*=text_box]').val();
		cartItem.itemId = $(this).attr('item_id');
		if(parseInt(cartItem.shopNum) > shopNumLimit){
			isLimit = true;
			return false;
		}
		var s = $(this).attr("supply_id");
		if(supplyIds.length > 0){
			if(supplyIds[supplyIds.length - 1] != s){
				isSame = false;
				return false;
			}
		}
		supplyIds.push(s);
		
		postdata.push(cartItem);
	});
	
	
	if(postdata.length <= 0){
		tips('请选择商品');
		return false;
	}
	
	
	if(!isSame){
		tips('只能同时购买一个供应商商品');
		return false;
	}
	
	if(isLimit){
		tips('每件商品限购10件');
		return false;
	}
	
	//sku限制50个
	if(postdata.length >= 50){
		tips('不能超过50种商品');
		return false;
	}
	
	$('#cartForm #items').val(JSON.stringify(postdata));
	$('#cartForm').submit();
}
