package com.project.response;

import lombok.Data;

@Data
public class ProductItems {

	private Long id;
	private String productName;
	private Long price;
	private String description;
	private Long catId;
    private String photos;
	private Long quantity;
	private  Long cartId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	
	public ProductItems(Long id, String productName, Long price, String description, Long catId, String photos,
			Long quantity, Long cartId) {
		super();
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.catId = catId;
		this.photos = photos;
		this.quantity = quantity;
		this.cartId = cartId;
	}
	public ProductItems() {
		super();
	}
	@Override
	public String toString() {
		return "ProductItems [id=" + id + ", productName=" + productName + ", price=" + price + ", description="
				+ description + ", catId=" + catId + ", photos=" + photos + ", quantity=" + quantity + ", cartId="
				+ cartId + "]";
	}
	
	
	
}
