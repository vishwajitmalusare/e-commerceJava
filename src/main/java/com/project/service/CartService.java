package com.project.service;

import java.util.List;

import com.project.model.Cart;
import com.project.response.CartResponse;

public interface CartService {

//	Cart createCart(Cart cart)throws AlreadyExistException;
//	
//	Cart update(Long cartId, Cart cart);
//	
//	void deleteCart(Long cartId);
//	
//	List<Cart> getAllCart();
	
	Cart save(Cart cart);
	
	List<CartResponse> getAllCartItem();
	
	void deleteAllCartItems();
	
	void deleteByProductsId(int productId);
}
	
