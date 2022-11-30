package com.masai.service;

import com.masai.entity.Cart;
import com.masai.exception.CartException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.exception.ProductException;

public interface CartService {
	
    public String addToCart(Integer productId,Integer quantity, String key) throws CustomerException,ProductException, LoginException, CartException;
	
	public String deleteFromCart(Integer productId, String key) throws CustomerException,ProductException, LoginException, CartException;
	
	public Cart viewCart(String key) throws CustomerException,ProductException, LoginException, CartException;
	
	public String emptyCart(String key) throws CustomerException,ProductException, LoginException, CartException;
	
	public String updateProductQuantity(String key, Integer productId, Integer quantity) throws CustomerException,ProductException, LoginException, CartException;

}
