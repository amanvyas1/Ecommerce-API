package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.CustomerLoginDTO;
import com.masai.entity.Admin;
import com.masai.entity.CurrentAdminSession;
import com.masai.entity.CurrentCustomerSession;
import com.masai.entity.Customer;
import com.masai.exception.AdminException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.repository.CurrentCustomerSessionRepo;
import com.masai.repository.CustomerRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	CurrentCustomerSessionRepo sessionRepo;

	@Override
	public String customerLogin(CustomerLoginDTO dTO) throws LoginException, CustomerException {
		Customer customer = customerRepo.findByPhone(dTO.getPhone());
		if(customer==null) {
			throw new CustomerException("Invalid phone number");
		}
		Optional<CurrentCustomerSession> customerSession = sessionRepo.findById(customer.getCustomerId());
		if(customerSession.isPresent()) {
			throw new LoginException("Customer already logged in with these credentials");
		}
		if(customer.getPassword().equals(dTO.getPassword())) {
			String key = RandomString.make(8);
			CurrentCustomerSession session = new CurrentCustomerSession();
			session.setKey(key);
			session.setLocalDateTime(LocalDateTime.now());
			session.setUserId(customer.getCustomerId());
			sessionRepo.save(session);
			return session.toString();
		}else {
			throw new LoginException("Invalid password");
		}
	}

	@Override
	public String customerLogout(String key) throws LoginException {
		CurrentCustomerSession session = sessionRepo.findByKey(key);
		if(session == null) {
			throw new LoginException("No session found with this key");
		}
		sessionRepo.delete(session);
		return "Customer logged out sucessfully";
	}

}
