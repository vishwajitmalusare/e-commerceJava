package com.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.model.Product;
import com.project.response.ProductItems;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	 @Query("SELECT p FROM Product p WHERE " +
	            "p.productName LIKE CONCAT('%',:query, '%')" +
	            "Or p.description LIKE CONCAT('%', :query, '%')")
	    List<Product> searchProducts(String query);
	
	Optional<Product> findByproductName(String productName);
	
	Optional<Product> findById(Long id);
	
	@Query("SELECT new com.project.response.ProductItems( p.id, p.productName, p.price, p.description, p.catId, p.photos, c.quantity, c.id) "
			+ "FROM Cart as c  INNER JOIN Product p ON c.productId = p.id INNER JOIN User as u ON c.userId = u.id "
			+ "WHERE p.id = :productId AND u.id = :userId")
	ProductItems findProductWithCart(Long userId,Long productId);

	
	List<Product> findByCatId(Long catId);

	
	}
