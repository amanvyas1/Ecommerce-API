package com.masai.service;

import com.masai.entity.Admin;
import com.masai.exception.AdminException;
import com.masai.exception.LoginException;

public interface AdminService {

	public Admin addAdmin(Admin admin) throws AdminException;

	public Admin updateAdmin(Admin admin, String key) throws AdminException, LoginException;

	public Admin deleteAdmin(Integer adminId, String key) throws AdminException, LoginException;

	public Admin viewAdmin(Integer adminId, String key) throws AdminException, LoginException;

}
