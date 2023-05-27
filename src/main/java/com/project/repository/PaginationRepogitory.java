package com.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.project.model.Product;

public interface PaginationRepogitory extends PagingAndSortingRepository<Product, Long> {

}
