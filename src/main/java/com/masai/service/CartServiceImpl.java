package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.ProductDTO;
import com.masai.entity.Cart;
import com.masai.entity.CurrentCustomerSession;
import com.masai.entity.Customer;
import com.masai.entity.Product;
import com.masai.exception.CartException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.exception.ProductException;
import com.masai.repository.CartRepo;
import com.masai.repository.CurrentCustomerSessionRepo;
import com.masai.repository.CustomerRepo;
import com.masai.repository.ProductDTORepo;
import com.masai.repository.ProductRepo;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	CurrentCustomerSessionRepo customerSessionRepo;
	
	@Autowired
	ProductDTORepo productDTORepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartRepo cartRepo;

	@Override
	public String addToCart(Integer productId,Integer quantity, String key) throws CustomerException, ProductException, LoginException, CartException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid customer key");
		}
		Optional<Product> product = productRepo.findById(productId);
		if(product.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		if(quantity<1) {
			throw new ProductException("Quantity can not be less than one");
		}
		Optional<Customer> customer = customerRepo.findById(customerSession.getUserId());
		if(customer.isEmpty()) {
			throw new CustomerException("Customer does not exist");
		}else {
			ProductDTO productDTO = new ProductDTO();
		    Product product1 = product.get();
		    productDTO.setBrand(product1.getBrand());
		    productDTO.setColor(product1.getColor());
		    productDTO.setProductId(productId);
		    productDTO.setProductName(product1.getProductName());
		    productDTO.setQuantity(quantity);
		    productDTO.setSalePrice(product1.getSalePrice());
		    if(product1.getStock()>0) {
		    	productDTO.setStockStatus("In Stock");
		    }else {
		    	productDTO.setStockStatus("Out of Stock");
		    }
		    customer.get().getCart().getProducts().add(productDTO);
		    if(customer.get().getCart().getQuantity()==null) {
		    	customer.get().getCart().setQuantity(quantity);
		    }else {
		    	customer.get().getCart().setQuantity(customer.get().getCart().getQuantity()+quantity);
		    }
		    if(customer.get().getCart().getTotalCost()==null) {
		    	customer.get().getCart().setTotalCost(productDTO.getSalePrice()*productDTO.getQuantity());
		    }else {
		    	customer.get().getCart().setTotalCost(customer.get().getCart().getTotalCost() + (productDTO.getSalePrice()*productDTO.getQuantity()));
		    }
		    cartRepo.save(customer.get().getCart());
		    customerRepo.save(customer.get());
		    return "Product Added succesfully";
		}
	
	}

	@Override
	public String deleteFromCart(Integer productId, String key) throws CustomerException, ProductException, LoginException, CartException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid customer key");
		}
		Optional<Product> product = productRepo.findById(productId);
		if(product.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		Optional<Customer> customer = customerRepo.findById(customerSession.getUserId());
		if(customer.isEmpty()) {
			throw new CustomerException("Customer does not exist");
		}else {
			boolean flag = customer.get().getCart().getProducts().removeIf(p -> p.getProductId()==productId);
			if(flag==true) {
				Double cost = 0.0;
				Integer quantity = 0;
				List<ProductDTO>  products = customer.get().getCart().getProducts();
				for(ProductDTO p: products) {
					cost += p.getSalePrice();
					quantity += p.getQuantity();
				}
				customer.get().getCart().setQuantity(quantity);
				customer.get().getCart().setTotalCost(cost*quantity);
				customerRepo.save(customer.get());
				cartRepo.save(customer.get().getCart());
				return "Product deleted from cart";
			}else {
				return "product does not exist in cart";
			}
		}
		
	}

	@Override
	public Cart viewCart(String key) throws CustomerException, ProductException, LoginException, CartException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid customer key");
		}
		Optional<Customer> customer = customerRepo.findById(customerSession.getUserId());
		if(customer.get().getCart()==null) {
			throw new CartException("Cart is empty");
		}
		return customer.get().getCart();
	}

	@Override
	public String emptyCart(String key) throws CustomerException, ProductException, LoginException, CartException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid customer key");
		}
		Optional<Customer> customer = customerRepo.findById(customerSession.getUserId());
		if(customer.get().getCart()==null) {
			throw new CartException("Cart is already empty");
		}
		customer.get().getCart().getProducts().clear();
		customer.get().getCart().setQuantity(0);
		customer.get().getCart().setTotalCost(0.0);
		cartRepo.save(customer.get().getCart());
		customerRepo.save(customer.get());
		return "Cart successfully emptied";
	}

	@Override
	public String updateProductQuantity(String key, Integer productId, Integer quantity) throws CustomerException, ProductException, LoginException, CartException {
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession==null) {
			throw new LoginException("Invalid customer key");
		}
		if(quantity<1) {
			throw new ProductException("Quantity should be more than 0");
		}
		Optional<Customer> customer = customerRepo.findById(customerSession.getUserId());
		if(customer.get().getCart().getProducts()==null) {
			throw new CartException("No product exist in cart");
		}
		List<ProductDTO> products2 = customer.get().getCart().getProducts();
		for(ProductDTO p: products2) {
			if( p.getProductId()==productId ) {
				p.setQuantity(quantity);
			}
		}
		Double cost = 0.0;
		Integer newQuantity = 0;
		List<ProductDTO>  products = customer.get().getCart().getProducts();
		for(ProductDTO p: products) {
			cost += p.getSalePrice();
			newQuantity += p.getQuantity();
		}
		customer.get().getCart().setQuantity(newQuantity);
		customer.get().getCart().setTotalCost(cost*newQuantity);
		customerRepo.save(customer.get());
		cartRepo.save(customer.get().getCart());
		return "Quantity updated successfully";
	}

}
