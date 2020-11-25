package com.jf.plus.common.utils;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.vo.CartItem;

import cn.iutils.sys.entity.User;



/**
 * @ClassName: ShopCartUtils
 * @Description: 购物车工具类
 * @author Tng
 * @date 2016年12月26日 下午4:40:07
 * 
 */

@SuppressWarnings("unchecked")
public class ShopCartUtils {
	
	private List<CartItem> shopCartList;
	
	public static ShopCartUtils getInstance(){
		return new ShopCartUtils();
	}
	
	protected void init() {
		HttpServletRequest request = Servlets.getRequest();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		shopCartList = (List<CartItem>)request.getSession().getAttribute(user.getId() + Constants.CART_SUFFIX);
		if(shopCartList == null){
			shopCartList = new ArrayList<>();
		}
	}
	
	protected void refreshSession() {
		HttpServletRequest request = Servlets.getRequest();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		request.getSession().setAttribute(user.getId() + Constants.CART_SUFFIX, shopCartList);
	}
	
	public void addItem(CartItem cartItem){
		init();
		
		if(cartItem == null){
			return;
		}
		
		int index = shopCartList.indexOf(cartItem);
		//不存在
		if(index == -1){
			shopCartList.add(cartItem);
		}else{
			//存在更新数量
			CartItem srcCartItem = shopCartList.get(index);
			srcCartItem.setShopNum(srcCartItem.getShopNum() + cartItem.getShopNum());
		}
		
		refreshSession();
	}
	
	public void delItem(CartItem cartItem){
		init();
		
		if(cartItem == null)
			return;
		
		int index = shopCartList.indexOf(cartItem);
		if(index != -1){
			shopCartList.remove(index);
		}
		
		refreshSession();
	}
	
	public void updItem(CartItem cartItem){
		init();
		
		if(cartItem == null)
			return;
		
		int index = shopCartList.indexOf(cartItem);
		if(index != -1){
			CartItem srcCartItem = shopCartList.get(index);
			srcCartItem.setShopNum(cartItem.getShopNum());
		}
		
		refreshSession();
	}
	
	public List<CartItem> getShopCartList() {
		HttpServletRequest request = Servlets.getRequest();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		return (List<CartItem>)request.getSession().getAttribute(user.getId() + Constants.CART_SUFFIX);
	}

	public void clear() {
		HttpServletRequest request = Servlets.getRequest();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		request.getSession().removeAttribute(user.getId() + Constants.CART_SUFFIX);
		
	}
	
	
	
	
	
}
