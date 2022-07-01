package com.postales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.postales.entity.Paquete;

public interface PaqueteRepository extends JpaRepository<Paquete, Integer>{
	@Query("select p from Paquete p")
	public List<Paquete> listarPaquetes();
}
