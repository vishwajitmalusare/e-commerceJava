package com.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.model.Cart;
import com.project.response.CartResponse;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Optional<Cart> findById(Long catId);
	
//	@Query("SELECT new springboot.mysql.restapi.springbootmysql.dto.CartReponse(p.id, p.name, c.user_id, p.price, c.quantity,p.photo, cat.name, c.id) FROM Cart c  JOIN Product p ON c.product_id = p.id AND c.user_id= :user_id JOIN Category cat on cat.id=p.cid")
//	 public List<CartResponse> getCartItems(Long user_id);
	@Query("SELECT new com.project.response.CartResponse( p.productName, p.id, c.userId, p.price, c.quantity, p.photos, cat.catName, c.id) FROM Cart c  "
			+ "JOIN Product p ON c.productId = p.id "
			+ "AND c.userId= :userId JOIN Category cat on cat.id=p.catId")
	 public List<CartResponse> getCartItems(Long userId); 
	
	@Transactional
	int deleteByUserId(Long userId);
	
	@Transactional
	int deleteByProductId(int productId);
	
//	 @Modifying
//	 @Transactional
//	  @Query(value="DELETE FROM Cart c WHERE c.userId = :userId")
//	 int deleteByUserId(Long userId);
//}
	
}
