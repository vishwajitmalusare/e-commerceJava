package com.project.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.exception.AlreadyExistException;
import com.project.model.Product;
import com.project.model.User;
import com.project.repository.PaginationRepogitory;
import com.project.repository.ProductRepository;
import com.project.response.ProductItems;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private PaginationRepogitory paginationRepo;
	
	@Autowired
	private IAuthenticationFacade authFacade;

	@Override
	public Product createProduct(Product product) {
		Optional<Product> newProduct = productRepo.findByproductName(product.getProductName());

		if (newProduct.isPresent()) {

			throw new AlreadyExistException("Product Already Exists!!!");
		} else {

			LocalDateTime localDate = LocalDateTime.now();
			String updatedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss").format(localDate);

			product.setCreated_at(updatedDate);
			product.setUpdated_at(updatedDate);
			return productRepo.save(product);
		}
	}

	@Override
	public List<Product> getAllProducts() {

		List<Product> allProducts = productRepo.findAll();
		return allProducts;
	}

	@Override
	public Product updateProduct(Product product, String filecode) throws IOException {
		Product updatedProduct = productRepo.findById(product.getId()).get();

		LocalDateTime localDate = LocalDateTime.now();
		String updatedDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss").format(localDate);

		updatedProduct.setProductName(product.getProductName());
		updatedProduct.setDescription(product.getDescription());
		updatedProduct.setPrice(product.getPrice());
		updatedProduct.setActive(false);
		updatedProduct.setUpdated_at(updatedDate);
		updatedProduct.setCatId(product.getCatId());
		updatedProduct.setPhotos(filecode);

		productRepo.save(updatedProduct);
		return updatedProduct;
	}

	@Override
	public void deleteUser(Long productId) {
		productRepo.deleteById(productId);
	}

	public List<Product> filterByCategory(Long catId) {
		List<Product> result = productRepo.findByCatId(catId);
		return result;
	}

	@Override
	public List<Product> searchProduct(String query) {
		return productRepo.searchProducts(query);
	}

	@Override
	public Object getProductsById(Long productId) {
		
		Authentication auth = authFacade.getAuthentication();
		User user = (User) auth.getPrincipal();
		
		ProductItems prduct = productRepo.findProductWithCart(user.getId(), productId);
		System.out.println("Product In get User Id: "+prduct);
		if (prduct == null) {
			Optional<Product> prod1 = productRepo.findById(productId);

			return prod1.get();
		} else {
			return prduct;

		}
	}


	@Override
	public Object paginatePtoducts(int pageNo, int pageSize) {

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Product> pageList = paginationRepo.findAll(paging);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Products Fetsch Successfully");
		map.put("status", 200);
		map.put("data", pageList.getContent());
		map.put("totalpages", pageList.getTotalPages());
		return map;

	}
}
