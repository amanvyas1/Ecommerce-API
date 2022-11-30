package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Feedback;
import com.masai.entity.Orders;
import com.masai.exception.CartException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.exception.OrderException;
import com.masai.exception.OutOfStockException;
import com.masai.exception.ProductException;
import com.masai.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/orders/{transactionMode}")
    public ResponseEntity<String> placeOrder(@PathVariable("transactionMode") String transactionMode,@RequestParam String key) throws LoginException,CartException,OutOfStockException,CustomerException,ProductException {
		String message = orderService.placeOrder(transactionMode, key);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	
	@PutMapping("/orders/{orderId}")
	public ResponseEntity<String> cancelOrder(@RequestParam String key,@PathVariable("orderId") Integer orderId) throws LoginException,OrderException {
		String message = orderService.cancelOrder(key, orderId);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	
	@GetMapping("/orders/{customerId}")
	public ResponseEntity<List<Orders>> viewOrder(@RequestParam String key ,@PathVariable("customerId") Integer customerId) throws LoginException,OrderException,CustomerException {
		List<Orders> orders = orderService.viewOrder(key, customerId);
		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<Orders>> viewAllOrders(@RequestParam String key) throws LoginException , OrderException {
		List<Orders> orders = orderService.viewAllOrders(key);
		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
	}
	
	@PutMapping("/orders/{orderId}/{status}")
	public ResponseEntity<Orders> updateStatus(@RequestParam String key,@PathVariable("status") String status,@PathVariable("orderId") Integer orderId) throws LoginException,OrderException {
		Orders order = orderService.updateStatus(key, status, orderId);
		return new ResponseEntity<Orders>(order, HttpStatus.OK);
	}
	
	@PutMapping("/orders/{orderId}/payment/{status}")
	public ResponseEntity<Orders> updatePaymentStatus(@RequestParam String key,@PathVariable("status") String status,@PathVariable("orderId") Integer orderId)  throws LoginException , OrderException {
		Orders order = orderService.updatePaymentStatus(key, status, orderId);
		return new ResponseEntity<Orders>(order, HttpStatus.OK);
	}
	
	@GetMapping("/orders/cancelled")
	public ResponseEntity<List<Orders>> viewAllCancelledOrder(@RequestParam String key) throws LoginException , OrderException {
		List<Orders> orders = orderService.viewAllCancelledOrder(key);
		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
	}
	
	@PostMapping("/orders/feedback/{productId}/")
	public ResponseEntity<Feedback> giveFeedBack(@RequestParam String key,@PathVariable("productId") Integer productId,@Valid @RequestBody Feedback feedback) throws LoginException, ProductException, OrderException {
		Feedback feedback1 = orderService.giveFeedBack(key, productId, feedback);
		return new ResponseEntity<Feedback>(feedback1, HttpStatus.ACCEPTED);
	}

}
