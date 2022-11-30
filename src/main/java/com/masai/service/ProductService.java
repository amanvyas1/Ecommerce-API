package com.masai.service;

import java.util.List;

import javax.validation.constraints.Min;

import com.masai.entity.Product;
import com.masai.exception.CategoryException;
import com.masai.exception.LoginException;
import com.masai.exception.ProductException;

public interface ProductService {
	
	Product addProduct(Product product, String key) throws LoginException, ProductException;
	
	Product updateProduct(Product product, String key) throws LoginException, ProductException;
	
	Product deleteProduct(Integer productId, String key) throws LoginException, ProductException;
	
	Product viewProduct(Integer productId, String key) throws LoginException, ProductException;
	
	List<Product> viewAllProduct(String key) throws LoginException, ProductException;
	
	List<Product> productByName(String productName, String key) throws LoginException , ProductException;
	
	List<Product> productByCategory(Integer categoryId, String key) throws LoginException, ProductException , CategoryException;
	
	List<Product> productSortByRatingAsc(String key) throws LoginException, ProductException;
	
	List<Product> prodcutSortByRatingDesc(String key) throws LoginException , ProductException;
	
	List<Product> productFilterByRating(String key, Double min, Double max) throws LoginException, ProductException;
	
    List<Product> productSortByPriceAsc(String key) throws LoginException, ProductException;
	
	List<Product> prodcutSortByPriceDesc(String key) throws LoginException , ProductException;
	
	List<Product> productFilterByPrice(String key, Double min, Double max) throws LoginException, ProductException;
	
}
