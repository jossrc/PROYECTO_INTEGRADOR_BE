package com.postales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.postales.entity.Cotizacion;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer>{
	public Optional<Cotizacion> findByIdUsuario(int idUsuario);
	
	@Query("select c from Cotizacion c where c.idUsuario = ?1")
	public List<Cotizacion> listarCotizacionesPorId(int idUsuario);
}
