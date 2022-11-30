package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entity.Category;
import com.masai.entity.CurrentAdminSession;
import com.masai.exception.CategoryException;
import com.masai.exception.LoginException;
import com.masai.repository.CategoryRepo;
import com.masai.repository.CurrentAdminSessionRepo;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	CurrentAdminSessionRepo adminSessionRepo;

	@Override
	public Category addCategory(Category category, String key) throws LoginException, CategoryException{
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession == null) {
			throw new LoginException("Invalid key");
		}
		return categoryRepo.save(category);
	}

	@Override
	public Category updateCategory(Category category, String key) throws LoginException , CategoryException{
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession == null) {
			throw new LoginException("Invalid key");
		}
		Optional<Category> savedCategory = categoryRepo.findById(category.getCategoryId());
		if(savedCategory.isEmpty()) {
			throw new CategoryException("Invalid category Id");
		}
		return categoryRepo.save(category);
	}

	@Override
	public Category deleteCategory(Integer categoryId, String key) throws LoginException, CategoryException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession == null) {
			throw new LoginException("Invalid key");
		}
		Optional<Category> savedCategory = categoryRepo.findById(categoryId);
		if(savedCategory.isEmpty()) {
			throw new CategoryException("Invalid category Id");
		}
		categoryRepo.delete(savedCategory.get());
		return savedCategory.get();
	}

	@Override
	public List<Category> viewAllCategory(String key) throws LoginException , CategoryException{
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession == null) {
			throw new LoginException("Invalid key");
		}
		List<Category> categories = categoryRepo.findAll();
		if(categories.size()==0) {
			throw new CategoryException("No category found");
		}else {
			return categories;
		}
	}

}
