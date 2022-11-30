package com.masai.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.CustomerLoginDTO;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.service.CustomerLoginService;

@RestController
public class CustomerLoginController {

	@Autowired
	CustomerLoginService customerLoginService;

	@PostMapping("/login")
	public ResponseEntity<String> customerLoginHandler(@Valid @RequestBody CustomerLoginDTO dTO)
			throws LoginException, CustomerException {
		String message = customerLoginService.customerLogin(dTO);
		return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> customerLogout(String key) throws LoginException {
		String message = customerLoginService.customerLogout(key);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
