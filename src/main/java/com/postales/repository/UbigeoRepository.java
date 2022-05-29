package com.postales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.postales.entity.Ubigeo;


public interface UbigeoRepository  extends JpaRepository<Ubigeo, Integer>{

	@Query("select distinct u.departamento from Ubigeo u")
	public abstract List<String> listarDepartamentos();
	
	@Query("select distinct u.provincia from Ubigeo u where u.departamento = :d1")
	public abstract List<String> listarProvincias(@Param("d1")String departamento);
	
	@Query("select u from Ubigeo u where u.departamento = :d1 and u.provincia = :p1")
	public abstract List<Ubigeo> listarDistritos(@Param("d1")String departamento, @Param("p1")String provincia);

}