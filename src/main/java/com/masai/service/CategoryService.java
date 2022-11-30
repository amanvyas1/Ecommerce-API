package com.masai.service;

import java.util.List;

import com.masai.entity.Category;
import com.masai.exception.CategoryException;
import com.masai.exception.LoginException;

public interface CategoryService {
	
	Category addCategory(Category category, String key) throws LoginException , CategoryException;
	
	Category updateCategory(Category category, String key) throws LoginException, CategoryException;
	
	Category deleteCategory(Integer categoryId, String key) throws LoginException, CategoryException;
	
	List<Category> viewAllCategory(String key) throws LoginException, CategoryException;
}
