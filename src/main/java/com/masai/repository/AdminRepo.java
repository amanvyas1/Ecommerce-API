package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.entity.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer>{
	
	public Admin findByPhone(String phone);
	
	public Admin findByEmail(String email);
}
