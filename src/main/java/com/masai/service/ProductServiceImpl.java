package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entity.Category;
import com.masai.entity.CurrentAdminSession;
import com.masai.entity.CurrentCustomerSession;
import com.masai.entity.Product;
import com.masai.exception.CategoryException;
import com.masai.exception.LoginException;
import com.masai.exception.ProductException;
import com.masai.repository.CategoryRepo;
import com.masai.repository.CurrentAdminSessionRepo;
import com.masai.repository.CurrentCustomerSessionRepo;
import com.masai.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CurrentAdminSessionRepo adminSessionRepo;
	
	@Autowired
	CurrentCustomerSessionRepo customerSessionRepo;
	

	@Override
	public Product addProduct(Product product, String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession == null) {
			throw new LoginException("Invalid key");
		}else {
			Optional<Category> category = categoryRepo.findById(product.getCategoryId());
			if(category.isEmpty()) {
				throw new ProductException("Category id is invalid");
			}
			product.setAdminId(adminSession.getUserId());
			return productRepo.save(product);
		}
		
	}

	@Override
	public Product updateProduct(Product product, String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession == null) {
			throw new LoginException("Invalid key");
		}
		Optional<Product> existingProduct = productRepo.findById(product.getProductId());
		Optional<Category> category = categoryRepo.findById(product.getCategoryId());
		if(category.isEmpty()) {
			throw new ProductException("Category id is invalid");
		}
		if(existingProduct.isEmpty()) {
			throw new ProductException("Invalid product Id");
		}else {
			return productRepo.save(product);
		}
	}

	@Override
	public Product deleteProduct(Integer productId, String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession == null) {
			throw new LoginException("Invalid key");
		}
		Optional<Product> existingProduct = productRepo.findById(productId);
		if(existingProduct.isEmpty()) {
			throw new ProductException("Invalid product Id");
		}else {
			productRepo.delete(existingProduct.get());
			return existingProduct.get();
		}
	}

	@Override
	public Product viewProduct(Integer productId, String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			Product product = productRepo.findById(productId).get();
			if(product==null) {
				throw new ProductException("Invalid product Id");
			}
			return product;
		}
	}

	@Override
	public List<Product> viewAllProduct(String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findAll();
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> productByName(String productName, String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findByProductNameContainingIgnoreCase(productName);
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> productByCategory(Integer categoryId, String key) throws LoginException, ProductException, CategoryException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findByCategoryId(categoryId);
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> productSortByRatingAsc(String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findByOrderByRatingAsc();
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> prodcutSortByRatingDesc(String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findByOrderByRatingDesc();
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> productFilterByRating(String key, Double min, Double max) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findByRatingBetween(min, max);
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> productSortByPriceAsc(String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findByOrderBySalePriceAsc();
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> prodcutSortByPriceDesc(String key) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findByOrderBySalePriceDesc();
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

	@Override
	public List<Product> productFilterByPrice(String key, Double min, Double max) throws LoginException, ProductException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(adminSession == null && customerSession == null ) {
			throw new LoginException("Invalid key");
		}else {
			List<Product> products = productRepo.findBySalePriceBetween(min, max);
			if(products.size()==0) {
				throw new ProductException("No product found");
			}
			return products;
		}
	}

}
