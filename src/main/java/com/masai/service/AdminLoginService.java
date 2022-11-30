package com.masai.service;

import com.masai.DTO.AdminLoginDTO;
import com.masai.exception.AdminException;
import com.masai.exception.LoginException;

public interface AdminLoginService {

	public String adminLogin(AdminLoginDTO dto) throws LoginException, AdminException;

	public String adminLogout(String key) throws LoginException;
	

}
