package com.masai.service;

import com.masai.DTO.CustomerLoginDTO;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;

public interface CustomerLoginService {

	public String customerLogin(CustomerLoginDTO dto) throws LoginException, CustomerException;

	public String customerLogout(String key) throws LoginException;

}
