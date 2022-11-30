package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.OrderDTO;
import com.masai.DTO.ProductDTO;
import com.masai.entity.CurrentAdminSession;
import com.masai.entity.CurrentCustomerSession;
import com.masai.entity.Customer;
import com.masai.entity.Feedback;
import com.masai.entity.Orders;
import com.masai.entity.Product;
import com.masai.exception.CartException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.exception.OrderException;
import com.masai.exception.OutOfStockException;
import com.masai.exception.ProductException;
import com.masai.repository.CartRepo;
import com.masai.repository.CurrentAdminSessionRepo;
import com.masai.repository.CurrentCustomerSessionRepo;
import com.masai.repository.CustomerRepo;
import com.masai.repository.FeedbackRepo;
import com.masai.repository.OrderRepo;
import com.masai.repository.ProductRepo;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	CurrentCustomerSessionRepo customerSessionRepo;
	
	@Autowired
	CurrentAdminSessionRepo adminSessionRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	FeedbackRepo feedbackRepo;

	@Override
	public String placeOrder(String transactionMode, String key)
			throws LoginException, CartException, OutOfStockException, CustomerException, ProductException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid customer key");
		}
		Optional<Customer> customer = customerRepo.findById(customerSession.getUserId());
		if(customer.get().getCart().getProducts()==null) {
			throw new CartException("No product exist in cart");
		}
		
		List<ProductDTO> products = customer.get().getCart().getProducts();
		for(ProductDTO p:products) {
			Optional<Product> product = productRepo.findById(p.getProductId());
			if(product.get().getStock()<p.getQuantity()) {
				throw new OutOfStockException("Product with product Id " + p.getProductId()+ " out of stock");
			}
		}
		Orders order = new Orders();
		order.setCustomerId(customer.get().getCustomerId());
		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus("Placed");
		if(transactionMode.toLowerCase().equals("cash")) {
			order.setPaymentStatus("Pending");
		}else {
			order.setPaymentStatus("Paid");
		}
		order.setQuantity(customer.get().getCart().getQuantity());
		order.setTotalCost(customer.get().getCart().getTotalCost());
		order.setTransactionMode(transactionMode);
		for(ProductDTO p:products) {
			Optional<Product> product = productRepo.findById(p.getProductId());
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setBrand(product.get().getBrand());
			orderDTO.setPrice(product.get().getSalePrice());
			orderDTO.setProductId(product.get().getProductId());
			orderDTO.setProductName(product.get().getProductName());
			orderDTO.setQuantity(p.getQuantity());
			order.getProducts().add(orderDTO);
			product.get().setStock(product.get().getStock()-p.getQuantity());
			productRepo.save(product.get());
		}
		orderRepo.save(order);
		cartService.emptyCart(key);
		return "Order placed successfully";
	}

	@Override
	public String cancelOrder(String key, Integer orderId) throws LoginException, OrderException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid customer key");
		}
		Optional<Customer> customer = customerRepo.findById(customerSession.getUserId());
 		Optional<Orders> order = orderRepo.findById(orderId);
 		if(order==null) {
 			throw new OrderException("Invalid order Id");
 		}
 		if(order.get().getCustomerId()!= customer.get().getCustomerId()) {
 			throw new OrderException("Invalid order Id");
 		}
 		order.get().setOrderStatus("cancelled");
 		if(order.get().getPaymentStatus().toLowerCase().equals("paid")) {
 			order.get().setPaymentStatus("Refund initiated");
 		}
 		orderRepo.save(order.get());
		return "Order cancelled successfully";
	}

	@Override
	public List<Orders> viewOrder(String key, Integer customerId)
			throws LoginException, OrderException, CustomerException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		
		if(customerSession==null && adminSession==null) {
			throw new LoginException("Invalid key");
		}
		
		if(adminSession!=null) {
			List<Orders> orders = orderRepo.findByCustomerId(customerId);
			if(orders.size()==0) {
				throw new OrderException("No order found");
			}else {
				return orders;
			}
		}
		if(customerSession!=null && customerSession.getUserId()==customerId) {
			List<Orders> orders = orderRepo.findByCustomerId(customerId);
			if(orders.size()==0) {
				throw new OrderException("No order found");
			}else {
				return orders;
			}
		}else {
			throw new CustomerException("Invalid customer credential");
		}
	}

	@Override
	public List<Orders> viewAllOrders(String key) throws LoginException, OrderException {
		
        CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession==null) {
			throw new LoginException("Invalid key");
		}
		return orderRepo.findAll();
	}

	@Override
	public Orders updateStatus(String key, String status, Integer orderId) throws LoginException, OrderException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession==null) {
			throw new LoginException("Invalid key");
		}
		Optional<Orders> order = orderRepo.findById(orderId);
		if(order.isEmpty()) {
			throw new OrderException("Invalid order Id");
		}
		order.get().setOrderStatus(status);
		return orderRepo.save(order.get());
	}

	@Override
	public Orders updatePaymentStatus(String key, String status, Integer orderId)
			throws LoginException, OrderException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession==null) {
			throw new LoginException("Invalid key");
		}
		Optional<Orders> order = orderRepo.findById(orderId);
		if(order.isEmpty()) {
			throw new OrderException("Invalid order Id");
		}
		order.get().setPaymentStatus(status);
		return orderRepo.save(order.get());
	}

	@Override
	public List<Orders> viewAllCancelledOrder(String key) throws LoginException, OrderException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession==null) {
			throw new LoginException("Invalid key");
		}
		List<Orders> orders = orderRepo.findAll().stream().filter(o -> o.getOrderStatus().equalsIgnoreCase("cancelled")).toList();
		
		return orders;
	}

	@Override
	public Feedback giveFeedBack(String key, Integer productId, Feedback feedback) throws LoginException, ProductException, OrderException{
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid key");
		}
		Optional<Product> product = productRepo.findById(productId);
		if(product.isEmpty()) {
			throw new ProductException("Product Id is invalid");
		}
		List<Orders> orders = orderRepo.findByCustomerId(customerSession.getUserId());
		if(orders.size()==0) {
			throw new OrderException("No order found");
		}
		List<OrderDTO> orderDTO = null;
		for(Orders o:orders) {
			orderDTO =  o.getProducts().stream().filter(ord-> ord.getProductId()==productId).toList();
		}
		if(orderDTO!=null) {
			product.get().getFeedback().add(feedback);
			if(product.get().getRating()==null) {
				product.get().setRating((double)feedback.getRating());
				product.get().setRatingCount(1);
			}else {
				product.get().setRatingCount(product.get().getRatingCount()+1);
				product.get().setRating((product.get().getRating()+feedback.getRating())/product.get().getRatingCount());
			}
			feedback.setCustomerId(customerSession.getUserId());
			productRepo.save(product.get());
		    feedbackRepo.save(feedback);
		    return feedback;
		}else {
			throw new OrderException("Please provide a valid product Id");
		}
	}

}
