package com.project.model;


import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="order_item")
public class OrderItem 	 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        
    @Column(name="id")
    private Long id;

    @Column(name="photo")
    private String photo;
    
    @Column(name="name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name="quantity")
    private Long quantity;

  
    @Column(name="product_id")
    private Long productId;
    
    @Column(name="total_amount")
    private BigDecimal totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
	
    
    public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getTotalAmount()
	{
    	 return BigDecimal.valueOf( this.quantity ).multiply( this.price );
		
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = BigDecimal.valueOf( this.quantity ).multiply( this.price );
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
    

}