package com.project.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.file.FileUpload;
import com.project.model.Cart;
import com.project.model.Category;
import com.project.model.Order;
import com.project.model.OrderDto;
import com.project.model.Product;
import com.project.response.CartResponse;
import com.project.response.Responsehandler;
import com.project.service.CartService;
import com.project.service.CateogoryService;
import com.project.service.OrderService;
import com.project.service.ProductService;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CateogoryService catService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@PostMapping("products/create-product")
	public ResponseEntity<Object> addUser(@ModelAttribute Product product,
			@RequestParam("file") MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String filecode = FileUpload.saveFile(fileName, multipartFile);
		product.setPhotos(filecode);

		LocalDateTime localDate = LocalDateTime.now();
		String createdDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss").format(localDate);

		product.setCreated_at(createdDate);
		product.setUpdated_at(createdDate);
		Product product1 = productService.createProduct(product);
		return Responsehandler.generatedResponse("User Created Successfully", HttpStatus.CREATED, product1);
	}

	@GetMapping("products/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable("id") Long productId) {
		Object product = productService.getProductsById(productId);
		return Responsehandler.generatedResponse("Get Product", HttpStatus.OK, product);
	}

	@PutMapping("products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @ModelAttribute Product product,
			@RequestParam("file") MultipartFile multipartFile) throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String filecode = FileUpload.saveFile(fileName, multipartFile);
		Product updatedProduct = productService.updateProduct(product, filecode);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@GetMapping("products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@DeleteMapping("products/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long productId) {
		productService.deleteUser(productId);
		return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);
	}

	@PostMapping("products/create")
	ResponseEntity<Object> createProduct(@ModelAttribute Product product) {
		Product productOne = productService.createProduct(product);
		return Responsehandler.generatedResponse("Product Created Successfully", HttpStatus.CREATED, productOne);

	}

	@GetMapping("products/filter/{id}")
	public ResponseEntity<List<Product>> filterByCategoryId(@PathVariable("id") Long catId) {
		List<Product> list = productService.filterByCategory(catId);
//			System.err.println(list);
		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	@GetMapping("products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String query) {
		return ResponseEntity.ok(productService.searchProduct(query));
	}

	@GetMapping("products/pagination")
	public ResponseEntity<Object> getAllPagesOfProducts(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
		Object list = productService.paginatePtoducts(pageNo, pageSize);

		return ResponseEntity.ok(list);
	}

	/**
	 * ------------------------------------------Cayegory---------------------------------------------------------------
	 */
	@PostMapping("category/create")
	ResponseEntity<Object> createCategory(@RequestBody Category category) {
		Category categoryOne = catService.createCategory(category);
		return Responsehandler.generatedResponse("Product Created Successfully", HttpStatus.CREATED, categoryOne);

	}

	@GetMapping("category/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long catId) {
		Category category = catService.getCategory(catId);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@PutMapping("category/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable("id") Long categoryId,
			@ModelAttribute Category category) {
		Category updatedCat = catService.updateCategory(category);
		return new ResponseEntity<>(updatedCat, HttpStatus.OK);
	}

	@GetMapping("category")
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> categories = catService.getAllCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@DeleteMapping("category/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long catId) {
		catService.deleteCategory(catId);
		return new ResponseEntity<>("Category successfully deleted!", HttpStatus.OK);
	}

	/**
	 * -------------------------------------------Cart---------------------------------------------------------------
	 */

	@PostMapping("cart/save")
	public ResponseEntity<?> addToCart(@RequestBody Cart cart) {

		Cart savedCart = cartService.save(cart);
		return Responsehandler.generatedResponse(" Produtct saved in the Cart", HttpStatus.OK, savedCart);

	}

	@GetMapping("cart")
	public ResponseEntity<?> geCartById() {
		List<CartResponse> cartItems = cartService.getAllCartItem();
		return Responsehandler.generatedResponse(" Produtct saved in the Cart", HttpStatus.OK, cartItems);
	}

	@GetMapping("cart/deleteByUserId")
	public void deleteAllCartByUserId() {
		cartService.deleteAllCartItems();
	}

	@GetMapping("cart/deleteByProdId/{id}")
	public void deleteCartByProdId(@PathVariable("id") int prodId) {
		cartService.deleteByProductsId(prodId);
	}

//---------------------------------------Order---------------------------------------------------------------------

	@PostMapping("order/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody Order order) {
		Order savedData = orderService.save(order);
		return Responsehandler.generatedResponse("Order placed successfully", HttpStatus.OK, savedData);

	}

	@GetMapping("order/getAllOrder")
	public ResponseEntity<?> getAllOrder() {
		List<Order> orderList = orderService.orderList();
		return Responsehandler.generatedResponse("Orders fetched successfully", HttpStatus.OK, orderList);
	}

	@GetMapping("order/getAllOrderByUserId")
	public ResponseEntity<?> getAllOrderByUser() {
		List<Order> orderList = orderService.orderListByUserId();
		return Responsehandler.generatedResponse("Orders fetched successfully By User", HttpStatus.OK, orderList);
	}

	@GetMapping("order/invoice/{id}")
	public ResponseEntity<?> getOrderInvoice(@PathVariable("id") Long orderId) {
		Order order = orderService.orderById(orderId);
		return Responsehandler.generatedResponse("Order Fetch Successfully ...", HttpStatus.OK, order);
	}
	
	@PutMapping("order/update/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable("id")Long orderId,@RequestParam String status,@RequestParam String remark){
		Order order = orderService.updateOrder(orderId,status,remark);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PostMapping("order/filter")
	public ResponseEntity<?> filterOrderByFilterKey(@RequestBody OrderDto orderDto){
	List<Order> list = orderService.filterOrders(orderDto);
		return Responsehandler.generatedResponse("Order filter Successfully", HttpStatus.OK, list);
	}
}
