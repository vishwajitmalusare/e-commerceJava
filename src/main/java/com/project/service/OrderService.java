package com.project.service;

import java.util.Date;
import java.util.List;

import com.project.exception.RecordNotFoundException;
import com.project.model.Order;
import com.project.model.OrderDto;


public interface OrderService {
	
	Order save(Order order);
	
	List<Order> orderList() throws RecordNotFoundException;
    
	List<Order>orderListByUserId();
	
	Order orderById(Long orderId);
	
	Order updateOrder(Long orderId, String status,String remark);
	
	List<Order> filterOrders(OrderDto orderDto);
	
}
