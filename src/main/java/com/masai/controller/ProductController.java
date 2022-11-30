package com.masai.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Product;
import com.masai.exception.CategoryException;
import com.masai.exception.LoginException;
import com.masai.exception.ProductException;
import com.masai.service.ProductService;

@RestController
@Validated
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/products")
    public ResponseEntity<Product> addProductHandler(@Valid @RequestBody Product product,@RequestParam String key) throws LoginException, ProductException{
    	Product savedProduct = productService.addProduct(product, key);
    	return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);
    }
	
	@PutMapping("/products")
    public ResponseEntity<Product> updateProductHandler(@Valid @RequestBody Product product,@RequestParam String key) throws LoginException, ProductException{
    	Product updatedProduct = productService.updateProduct(product, key);
    	return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }
	
	@DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("productId") Integer productId,@RequestParam String key) throws LoginException, ProductException{
    	Product deletedProduct = productService.deleteProduct(productId, key);
    	return new ResponseEntity<Product>(deletedProduct,HttpStatus.ACCEPTED);
    }
	
	@GetMapping("/products/{productId}")
    public ResponseEntity<Product> viewProduct(@PathVariable("productId") Integer productId,@RequestParam String key) throws LoginException, ProductException{
    	Product product = productService.viewProduct(productId, key);
    	return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
	
	@GetMapping("/products")
    public ResponseEntity<List<Product>> viewAllProduct(@RequestParam String key) throws LoginException, ProductException{
    	List<Product> products = productService.viewAllProduct(key);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/name/{productName}")
    public ResponseEntity<List<Product>> productByName(@PathVariable("productName") String productName,@RequestParam String key) throws LoginException , ProductException{
    	List<Product> products = productService.productByName(productName, key);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<Product>> productByCategory(@PathVariable("categoryId") Integer categoryId,@RequestParam String key) throws LoginException, ProductException , CategoryException{
    	List<Product> products = productService.productByCategory(categoryId, key);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/rating/asc")
    public ResponseEntity<List<Product>> productSortByRatingAsc(@RequestParam String key) throws LoginException, ProductException{
    	List<Product> products = productService.productSortByRatingAsc(key);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/rating/desc")
    public ResponseEntity<List<Product>> prodcutSortByRatingDesc(@RequestParam String key) throws LoginException , ProductException{
    	List<Product> products = productService.prodcutSortByRatingDesc(key);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/rating/{min}/{max}")
    public ResponseEntity<List<Product>> productFilterByRating(@RequestParam String key,@Min(value = 1, message = "Min should be great than or equal to 1") @PathVariable("min") Double min,@Max(value = 5, message = "max should be less than or eqaul to 5") @PathVariable("max") Double max) throws LoginException, ProductException{
    	List<Product> products = productService.productFilterByRating(key, min, max);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/sort/asc")
    public ResponseEntity<List<Product>> productSortByPriceAsc(@RequestParam String key) throws LoginException, ProductException{
    	List<Product> products = productService.productSortByPriceAsc(key);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/sort/desc")
    public ResponseEntity<List<Product>> prodcutSortByPriceDesc(@RequestParam String key) throws LoginException , ProductException{
    	List<Product> products = productService.prodcutSortByPriceDesc(key);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/price/{min}/{max}")
    public ResponseEntity<List<Product>> productFilterByPrice(@RequestParam String key,@Min(value=1,message =  "Value should be greater than 0")  Double min,@Min(value=1,message= "Value should be greater than 0") Double max) throws LoginException, ProductException{
    	List<Product> products = productService.productFilterByPrice(key, min, max);
    	return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	
	
}
