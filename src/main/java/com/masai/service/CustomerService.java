package com.masai.service;

import java.util.List;

import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.entity.Customer;

public interface CustomerService {

	public Customer addCustomer(Customer customer) throws CustomerException;

	public Customer updateCustomer(Customer customer, String key) throws CustomerException, LoginException;

	public Customer deleteCustomer(Integer customerId, String key) throws CustomerException, LoginException;

	public Customer viewCustomer(Integer customerId, String key) throws CustomerException, LoginException;

	public List<Customer> viewAllCustomer(String key) throws CustomerException, LoginException;

}
