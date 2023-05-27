package com.project.service;

import java.util.List;

import com.project.exception.AlreadyExistException;
import com.project.model.Category;

public interface CateogoryService {
	
	Category createCategory(Category category) throws AlreadyExistException;
	
	Category getCategory(Long catId);
	
	List<Category> getAllCategories();
	
	void deleteCategory(Long catId);
	
	Category updateCategory(Category category);

}
