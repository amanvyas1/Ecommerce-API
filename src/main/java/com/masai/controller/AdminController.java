package com.masai.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Admin;
import com.masai.exception.AdminException;
import com.masai.exception.LoginException;
import com.masai.service.AdminService;

@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@PostMapping("/admin")
	public ResponseEntity<Admin> addAdminHandler(@Valid @RequestBody Admin admin) throws AdminException{
		Admin savedAdmin = adminService.addAdmin(admin);
		return new ResponseEntity<Admin>(savedAdmin, HttpStatus.OK);
	}
	
    @PutMapping("/admin")
	public ResponseEntity<Admin> updateAdminHandler(@Valid @RequestBody Admin admin,@RequestParam String key) throws AdminException, LoginException{
		Admin updatedAdmin = adminService.updateAdmin(admin, key);
		return new ResponseEntity<Admin>(updatedAdmin, HttpStatus.OK);
	}
    
    @DeleteMapping("/admin/{adminId}")
	public ResponseEntity<Admin> deleteAdminHandler(@PathVariable("adminId") Integer adminId,@RequestParam String key) throws AdminException, LoginException{
		Admin deletedAdmin = adminService.deleteAdmin(adminId, key);
		return new ResponseEntity<Admin>(deletedAdmin, HttpStatus.OK);
	}
    
    @GetMapping("/admin/{adminId}")
	public ResponseEntity<Admin> viewAdminHandler(@PathVariable("adminId") Integer adminId,@RequestParam String key) throws AdminException, LoginException{
    	Admin admin = adminService.viewAdmin(adminId, key);
    	return new ResponseEntity<Admin>(admin, HttpStatus.OK);
		
	}

}
