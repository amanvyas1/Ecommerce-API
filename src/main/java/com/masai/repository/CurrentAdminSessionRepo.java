package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.entity.CurrentAdminSession;

@Repository
public interface CurrentAdminSessionRepo extends JpaRepository<CurrentAdminSession, Integer>{

}
