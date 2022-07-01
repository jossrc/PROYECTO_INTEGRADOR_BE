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
	
	@Query("select c from Cotizacion c")
	public List<Cotizacion> listarCotizaciones();
	
	@Query("select c from Cotizacion c where c.fechaCreacion = current_date")
	public List<Cotizacion> listarCotizacionesPorDia();
	
	@Query("select c from Cotizacion c where c.idUsuario = ?1 and c.estado = 2")
	public List<Cotizacion> listarCotizacionesAprobadas(int idUsuario);
	
	@Query("select c from Cotizacion c where c.idUsuario = ?1 and c.estado = 1")
	public List<Cotizacion> listarCotizacionesPendientes(int idUsuario);
	
	@Query("select c from Cotizacion c where c.idUsuario = ?1 and c.estado = 3")
	public List<Cotizacion> listarCotizacionesRechazadas(int idUsuario);
	
	@Query("select c from Cotizacion c where c.idUsuario = ?1 and c.fechaCreacion = current_date")
	public List<Cotizacion> listarCotizacionesPorIdYDia(int idUsuario);
}
