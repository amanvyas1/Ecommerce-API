package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Cart;
import com.masai.exception.CartException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.exception.ProductException;
import com.masai.service.CartService;

@RestController
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@PostMapping("/cart")
	public ResponseEntity<String> addToCartHandler(@RequestParam Integer productId,@RequestParam Integer quantity,@RequestParam String key) throws CustomerException, ProductException, LoginException, CartException{
		String message = cartService.addToCart(productId, quantity, key);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@DeleteMapping("/cart/product")
	public ResponseEntity<String> deleteFromCartHandler(@RequestParam Integer productId,@RequestParam String key) throws CustomerException, ProductException, LoginException, CartException{
		String message = cartService.deleteFromCart(productId, key);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping("/cart")
	public ResponseEntity<Cart> viewCartHandler(@RequestParam String key) throws CustomerException, ProductException, LoginException, CartException{
		Cart cart = cartService.viewCart(key);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@DeleteMapping("/cart")
	public ResponseEntity<String> emptyCartHandler(@RequestParam String key) throws CustomerException, ProductException, LoginException, CartException{
		String message = cartService.emptyCart(key);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}

	@PutMapping("/cart")
	public ResponseEntity<String> updateProductQuantityHandler(@RequestParam String key,@RequestParam Integer productId,@RequestParam Integer quantity) throws CustomerException, ProductException, LoginException, CartException{
		String message = cartService.updateProductQuantity(key, productId, quantity);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
