package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.entity.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer>{
	
	List<Orders> findByCustomerId(Integer customerId);
	
}
