package com.postales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postales.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	

}
