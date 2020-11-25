/*商品数量加减*/

var shopNumLimit = 10;
$(function() {
	$(".cart .increase").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		t.val(parseInt(t.val()) + 1)
		if(parseInt(t.val()) > shopNumLimit){
			tips('最多只能购买10件');
			t.val(10);
		}
		setTotal();
		
		updShopCart($(this).parents('li').attr('item-id'),t.val());
	})

	$(".cart .decrease").click(function() {
		var t = $(this).parent().find('input[class*=text_box]');
		t.val(parseInt(t.val()) - 1)
		if(parseInt(t.val()) < 0) {
			t.val(1);
		}
		setTotal();
	
		updShopCart($(this).parents('li').attr('item-id'),t.val());
	})
		//单选复选框更改价格

	$(".cart ul li .check").click(function() {
		var type = $(this).attr("type");
		
		var checklenght = $(".cart ul li.on").length;
		var clenght = $(".cart ul li").length;
		if(checklenght == clenght) {
			$(".cart-all").removeClass("on");

		}

		if($(this).hasClass("on")) {
			$(this).parents("li").removeClass("on")
			$(this).removeClass("on")
		} else {
			$(".cart-all").addClass("on");
			$(this).parents("li").addClass("on");
			$(this).addClass("on");
		}
		setTotal();

	})

	//全选反选更改价格

	$(document).on("click", ".cart-all", function() {
		$(this).toggleClass("on")
		var checklenght = $(".cart ul li .check[class*=on][role=child]").length;
		var clenght = $(".cart ul li .check[role=child]").length;

		$(".cart ul li .check[role=child]").each(function() {
			if(checklenght == clenght) {

				$(".cart ul li").find(".check").removeClass("on")
				$(".cart ul li").removeClass("on")
			} else {
				$(this).addClass("on")
				$(".cart ul li").find(".check").addClass("on")
				$(".cart ul li").addClass("on");
			}
		})
		setTotal();
	})

	$(function() {
		$('.cart-del').on('click', function() {
			$('#my-confirm').modal({
				relatedTarget: this,
				onConfirm: function(options) {
						var li = $(this.relatedTarget).parents('div');
						li.remove()
						setTotal();
						
						delShopCart(li.attr('item-id'),1);
					}
					// closeOnConfirm: false,

			});
		});
	});

	function setTotal() {
		var s = 0;
		$(".cart ul li .check[class*=on][role=child]").each(function() {
			s += parseInt($(this).parent().find('input[class*=text_box]').val()) * parseFloat($(this).parent().find('i[class*=price]').text());
		});
		$("#total").html(s.toFixed(0));
	}
	setTotal();
	
})

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
	var isLimit = false;
	$(".cart ul li.on").each(function() {
		var $childList = $(this).find('div[role=child][class*=on]');
		$.each($childList,function(){
			var cartItem = {};
			cartItem.shopNum = $(this).parent('div').find('input[class*=text_box]').val();
			if(parseInt(cartItem.shopNum) > shopNumLimit){
				isLimit = true;
				return false;
			}
			cartItem.itemId = $(this).attr('item_id');
			
			postdata.push(cartItem);
		});
		
	})
	
	if(isLimit){
		tips('每件商品限购10件');
		return false;
	}
	
	if(postdata.length <= 0){
		tips('请选择商品');
		return false;
	}
	
	//sku限制50个
	if(postdata.length >= 50){
		tips('不能超过50个品类');
		return false;
	}
	
	$('#cartForm #items').val(JSON.stringify(postdata));
	$('#cartForm').submit();
}
