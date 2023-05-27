package com.project.service;

import java.io.IOException;
import java.util.List;

import com.project.exception.AlreadyExistException;
import com.project.model.Product;


public interface ProductService {
	
	Product createProduct(Product product)throws AlreadyExistException;
	
	List<Product> getAllProducts();
	
	Object getProductsById(Long productId);

	Product updateProduct(Product product, String filecode) throws IOException;
	
    void deleteUser(Long productId);
    
    List<Product> filterByCategory(Long catId);
    
    List<Product> searchProduct(String query);
    
    Object paginatePtoducts(int pageNo, int pageSize);
    
}
