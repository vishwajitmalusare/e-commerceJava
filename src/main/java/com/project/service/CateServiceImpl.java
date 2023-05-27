package com.project.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.exception.AlreadyExistException;
import com.project.model.Category;
import com.project.repository.CategoryRepository;

@Service
public class CateServiceImpl implements CateogoryService {

	@Autowired
	private CategoryRepository catRepo;

	@Override
	public Category createCategory(Category category) {

		Optional<Category> category1 = catRepo.findByCatName(category.getCatName());

		if (category1.isPresent()) {
			throw new AlreadyExistException("Category Already Exists!!!");
		} else {

			LocalDateTime localDate = LocalDateTime.now();
			String updatedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss").format(localDate);
			category.setCreated_date(updatedDate);
			category.setUpdated_date(updatedDate);

			return catRepo.save(category);
		}
	}

	@Override
	public Category getCategory(Long catId) {
		Category resCat = catRepo.findById(catId).get();
		return resCat;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> allCategories = catRepo.findAll();
		return allCategories;
	}

	@Override
	public void deleteCategory(Long categoryId) {
		catRepo.deleteById(categoryId);
	}

	@Override
	public Category updateCategory(Category category) {
		Category updatedCat = catRepo.findById(category.getId()).get();
		LocalDateTime localDate = LocalDateTime.now();
		String updatedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss").format(localDate);

		updatedCat.setCatName(category.getCatName());
		updatedCat.setUpdated_date(updatedDate);
		
		catRepo.save(updatedCat);
		return updatedCat;
	}

}
