package com.project.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartResponse {

	private String name;
	private Long productId;
	private Long userId;
	private Long price;
	private Long quantity;
	private String photo;
	private String category;
	private Long cartId;
	
	public CartResponse(String name, Long productId, Long userId, Long price, Long quantity, String photo,
			String category, Long cartId) {
		super();
		this.name = name;
		this.productId = productId;
		this.userId = userId;
		this.price = price;
		this.quantity = quantity;
		this.photo = photo;
		this.category = category;
		this.cartId = cartId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	@Override
	public String toString() {
		return "CartResponse [name=" + name + ", productId=" + productId + ", userId=" + userId + ", price=" + price
				+ ", quantity=" + quantity + ", photo=" + photo + ", category=" + category + ", cartId=" + cartId + "]";
	}

	
	
}
