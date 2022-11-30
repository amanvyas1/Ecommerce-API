package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entity.CurrentAdminSession;
import com.masai.entity.CurrentCustomerSession;
import com.masai.entity.Customer;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.repository.AddressRepo;
import com.masai.repository.CurrentAdminSessionRepo;
import com.masai.repository.CurrentCustomerSessionRepo;
import com.masai.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	CurrentCustomerSessionRepo customerSessionRepo;
	
	@Autowired
	CurrentAdminSessionRepo adminSessionRepo;
	
	@Autowired
	AddressRepo addressRepo;

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {
		Customer existingCustomer = customerRepo.findByPhone(customer.getPhone());
		if(existingCustomer!=null) {
			throw new CustomerException("Account already exist with this phone number");
		}
		if(customer.getEmail()!=null) {
			existingCustomer = customerRepo.findByEmail(customer.getEmail());
			if(existingCustomer!=null) {
				throw new CustomerException("Account already exist with this email");
			}
		}
		customer.setDateRegistered(LocalDateTime.now());
		return customerRepo.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer, String key) throws CustomerException, LoginException {
		CurrentCustomerSession session = customerSessionRepo.findByKey(key);
		if(session == null) {
			throw new CustomerException("Key is invalid");
		}
		if(session.getUserId()==customer.getCustomerId()) {
			return customerRepo.save(customer);
		}else {
			throw new CustomerException("Invalid customer Id");
		}
		
	}

	@Override
	public Customer deleteCustomer(Integer customerId, String key) throws CustomerException, LoginException {
		Optional<Customer> customer = customerRepo.findById(customerId);
		if(customer.isEmpty()) {
			throw new CustomerException("Invalid customer Id");
		}
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession != null) {
			customerRepo.delete(customer.get());
			return customer.get();
		}	
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession == null || customerSession.getUserId() != customer.get().getCustomerId()) {
			throw new CustomerException("Key is invalid");
		}else {
			customerRepo.delete(customer.get());
			return customer.get();
		}
		
	}

	@Override
	public Customer viewCustomer(Integer customerId, String key) throws CustomerException, LoginException {
		Optional<Customer> customer = customerRepo.findById(customerId);
		if(customer.isEmpty()) {
			throw new CustomerException("Invalid customer Id");
		}
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession != null) {
			return customer.get();
		}	
		CurrentCustomerSession customerSession = customerSessionRepo.findByKey(key);
		if(customerSession == null || customerSession.getUserId() != customer.get().getCustomerId()) {
			throw new CustomerException("Key is invalid");
		}else {
			return customer.get();
		}
	}

	@Override
	public List<Customer> viewAllCustomer(String key) throws CustomerException, LoginException {
		CurrentAdminSession adminSession = adminSessionRepo.findByKey(key);
		if(adminSession != null) {
			List<Customer> customers = customerRepo.findAll();
			if(customers.size()==0) {
				throw new CustomerException("Customer not found");
			}else {
				return customers;
			}
		}else {	
			throw new LoginException("Invalid key for the admin");
		}
		
	}

}
