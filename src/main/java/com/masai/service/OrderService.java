package com.masai.service;

import java.util.List;

import com.masai.entity.Feedback;
import com.masai.entity.Orders;
import com.masai.exception.CartException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.exception.OrderException;
import com.masai.exception.OutOfStockException;
import com.masai.exception.ProductException;



public interface OrderService {
	
	String placeOrder(String transactionMode, String key) throws LoginException,CartException,OutOfStockException,CustomerException,ProductException;
	
	String cancelOrder(String key,Integer orderId) throws LoginException,OrderException;
	
	List<Orders> viewOrder(String key ,Integer customerId) throws LoginException,OrderException,CustomerException;
	
	List<Orders> viewAllOrders(String key) throws LoginException , OrderException;
	
	Orders updateStatus(String key, String status, Integer orderId) throws LoginException,OrderException;
	
	Orders updatePaymentStatus(String key, String status, Integer orderId)  throws LoginException , OrderException;
	
	List<Orders> viewAllCancelledOrder(String key) throws LoginException , OrderException;
	
	Feedback giveFeedBack(String key, Integer productId, Feedback feedback) throws LoginException, ProductException, OrderException;

}
