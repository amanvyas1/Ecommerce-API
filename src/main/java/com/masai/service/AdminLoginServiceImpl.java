package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.DTO.AdminLoginDTO;
import com.masai.entity.Admin;
import com.masai.entity.CurrentAdminSession;
import com.masai.exception.AdminException;
import com.masai.exception.LoginException;
import com.masai.repository.AdminRepo;
import com.masai.repository.CurrentAdminSessionRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

	@Autowired
	AdminRepo adminRepo;
	
	@Autowired
	CurrentAdminSessionRepo sessionRepo;
	
	@Override
	public String adminLogin(AdminLoginDTO dTO) throws LoginException, AdminException {
		Admin admin = adminRepo.findByPhone(dTO.getPhone());
		if(admin==null) {
			throw new AdminException("Invalid phone number");
		}
		Optional<CurrentAdminSession> adminSession = sessionRepo.findById(admin.getAdminId());
		if(adminSession.isPresent()) {
			throw new LoginException("Admin already logged in with these credentials");
		}
		if(admin.getPassword().equals(dTO.getPassword())) {
			String key = RandomString.make(8);
			CurrentAdminSession session = new CurrentAdminSession();
			session.setKey(key);
			session.setLocalDateTime(LocalDateTime.now());
			session.setUserId(admin.getAdminId());
			sessionRepo.save(session);
			return session.toString();
		}else {
			throw new LoginException("Invalid password");
		}
	}

	@Override
	public String adminLogout(String key) throws LoginException {
		
		CurrentAdminSession session = sessionRepo.findByKey(key);
		if(session == null) {
			throw new LoginException("No session found with this key");
		}
		sessionRepo.delete(session);
		return "Admin logged out sucessfully";
	}
	
}
