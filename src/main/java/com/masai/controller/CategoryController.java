package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Category;
import com.masai.exception.CategoryException;
import com.masai.exception.LoginException;
import com.masai.service.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/category")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category,@RequestParam String key) throws LoginException , CategoryException{
    	Category savedCategory = categoryService.addCategory(category, key);
    	return new ResponseEntity<Category>(savedCategory,HttpStatus.OK);
    }
	
	@PutMapping("/category")
	public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category,@RequestParam String key) throws LoginException, CategoryException{
		Category updatedCategory = categoryService.updateCategory(category, key);
		return new ResponseEntity<Category>(updatedCategory, HttpStatus.OK);
	}
	
	@DeleteMapping("/category/{categoryID}")
	public ResponseEntity<Category> deleteCategory(@PathVariable("categoryID") Integer categoryId,@RequestParam String key) throws LoginException, CategoryException{
		Category deletedCategory = categoryService.deleteCategory(categoryId, key);
		return new ResponseEntity<Category>(deletedCategory, HttpStatus.OK);
	}
	
	@GetMapping("/category")
	public ResponseEntity<List<Category>> viewAllCategory(@RequestParam String key) throws LoginException, CategoryException{
		List<Category> categories = categoryService.viewAllCategory(key);
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}

}
