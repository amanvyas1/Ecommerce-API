package com.masai.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.DTO.AdminLoginDTO;
import com.masai.exception.AdminException;
import com.masai.exception.LoginException;
import com.masai.service.AdminLoginService;

@RestController
@RequestMapping("/admin")
public class AdminLoginController {

	@Autowired
	AdminLoginService adminLoginService;

	@PostMapping("/login")
	public ResponseEntity<String> adminLoginHandler(@Valid @RequestBody AdminLoginDTO dTO)
			throws LoginException, AdminException {
		String message = adminLoginService.adminLogin(dTO);
		return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> adminLogout(@RequestParam String key) throws LoginException {
		String message = adminLoginService.adminLogout(key);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
