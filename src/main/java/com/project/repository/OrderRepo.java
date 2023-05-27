package com.project.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.model.Order;


public interface OrderRepo extends JpaRepository<Order, Long> {
	
	List<Order> findByUserIdOrderByDateCreatedDesc(Long userId);
		
	Optional<Order> findById(Long orderId);
	
	@Query(value ="SELECT o FROM Order o "
			+ "WHERE o.lastUpdated LIKE CONCAT('%', :date, '%') "
			+ "AND o.status LIKE CONCAT('%', :status, '%') "
			+ "AND o.userId LIKE CONCAT('%', :userId, '%') ")
	List<Order> filterOrder(String date, String status, String userId);
	
	List<Order> findByLastUpdatedBetween(Date fromDate, Date toDate);
	
}
