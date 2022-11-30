package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entity.Admin;
import com.masai.entity.CurrentAdminSession;
import com.masai.exception.AdminException;
import com.masai.exception.LoginException;
import com.masai.repository.AdminRepo;
import com.masai.repository.CurrentAdminSessionRepo;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	AdminRepo adminRepo;
	
	@Autowired
	CurrentAdminSessionRepo sessionRepo;

	@Override
	public Admin addAdmin(Admin admin) throws AdminException {
		Admin savedAdmin = adminRepo.findByPhone(admin.getPhone());
		if(savedAdmin!=null) {
			throw new AdminException("Account with phone number already exists");
		}
		if(admin.getEmail()!=null) {
			Admin savedAdmin2 = adminRepo.findByEmail(admin.getEmail());
			if(savedAdmin2 != null) {
				throw new AdminException("Account with this email already exist");
			}
		}
		admin.setDateRegistered(LocalDateTime.now());
		return adminRepo.save(admin);
	}

	@Override
	public Admin updateAdmin(Admin admin, String key) throws AdminException, LoginException {
		CurrentAdminSession session = sessionRepo.findByKey(key);
		if(session == null) {
			throw new LoginException("Invalid key");
		}
		Optional<Admin> existingAdmin = adminRepo.findById(admin.getAdminId());
		if(existingAdmin.isEmpty()) {
			throw new AdminException("Invalid admin Id");
		}
		if(admin.getAdminId()==session.getUserId()) {
			return adminRepo.save(admin);
		}else {
			throw new AdminException("Invalid admin credentials");
		}
	}

	@Override
	public Admin deleteAdmin(Integer adminId, String key) throws AdminException, LoginException {
		CurrentAdminSession session = sessionRepo.findByKey(key);
		if(session == null) {
			throw new LoginException("Invalid key");
		}
		Optional<Admin> existingAdmin = adminRepo.findById(adminId);
		if(existingAdmin.isEmpty()) {
			throw new AdminException("Invalid admin Id");
		}
		if(adminId == session.getUserId()) {
			adminRepo.delete(existingAdmin.get());
			return existingAdmin.get();
		}else {
			throw new AdminException("Invalid admin Id");
		}
		
	}

	@Override
	public Admin viewAdmin(Integer adminId, String key) throws AdminException, LoginException {
		CurrentAdminSession session = sessionRepo.findByKey(key);
		if(session == null) {
			throw new LoginException("Invalid key");
		}
		Optional<Admin> existingAdmin = adminRepo.findById(adminId);
		if(existingAdmin.isEmpty()) {
			throw new AdminException("Invalid admin Id");
		}
		if(adminId == session.getUserId()) {
			return existingAdmin.get();
		}else {
			throw new AdminException("Invalid admin Id");
		}
		
	}

}
