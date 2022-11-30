package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.entity.Product;


public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	List<Product> findByProductNameContainingIgnoreCase(String productName);

	List<Product> findByCategoryId(Integer categoryId);
	
	List<Product> findByOrderByRatingAsc();
	
	List<Product> findByOrderByRatingDesc();
	
	List<Product> findByOrderBySalePriceAsc();
	
	List<Product> findByOrderBySalePriceDesc();
	
	List<Product> findBySalePriceBetween(Double min, Double max);
	
	List<Product> findByRatingBetween(Double min, Double max);
	
}
