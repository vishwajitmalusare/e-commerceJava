package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.model.Cart;
import com.project.model.User;
import com.project.repository.CartRepository;
import com.project.repository.UserRepository;
import com.project.response.CartResponse;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private IAuthenticationFacade authFacade;
	
	@SuppressWarnings("unused")
	@Autowired
	private UserRepository userRepo;

//	@Override
//	public Cart createCart(Cart cart) {
//	Long cart1 = cart.getId();
//		
//	if(cart1==null) {
//			throw new AlreadyExistException("Cart is already exists!!!");
//		}
//		else {	
//			cartRepo.save(cart);
//			return cart;
//		}
//	
//	}
//
//	@Override
//	public void deleteCart(Long cartId) {
//		Cart getCart = cartRepo.findById(cartId).get();
//		cartRepo.delete(getCart);
//	}
//
//	@Override
//	public List<Cart> getAllCart() {
//		
//		List<Cart> allCarts = cartRepo.findAll();
//		return allCarts;
//	}
//
//	@Override
//	public Cart update(Long cartId, Cart cart) {
//		Cart newCart = cartRepo.findById(cartId).get();
//		
//		newCart.setQuantity(cart.getQuantity());
//		
//		return cartRepo.save(newCart);
//	}
	
	//-------------------------------------------------------------XX-----------------------------------------------
//Try This
	//	@Override
	public Cart save(Cart cart) {
//		Authentication authentication = authenticationFacade.getAuthentication();
//		User dbUser = (User) authentication.getPrincipal();
//		cart.setUser_id(dbUser.getId());

				return cartRepo.save(cart);
	}

	@Override
	public List<CartResponse> getAllCartItem() {
		// TODO Auto-generated method stub
		Authentication authentication = authFacade.getAuthentication();
		User dbUser = (User) authentication.getPrincipal();
		
//		User user = userRepo.findById(userId).get();
		
		    List<CartResponse> carList  =  cartRepo.getCartItems(dbUser.getId());
//		    System.out.println("Cart List => "+carList);
		    
		return carList;
	}

	@Override
	public void deleteAllCartItems() {
		Authentication  auth = authFacade.getAuthentication();
		User dbUser = (User) auth.getPrincipal();
		cartRepo.deleteByUserId(dbUser.getId());
		//Posobility to make it use Token?
	}

	@Override
	public void deleteByProductsId(int productId) {
		cartRepo.deleteByProductId(productId);
	}

}
