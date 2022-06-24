package com.postales.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postales.entity.Cotizacion;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer>{
	public Optional<Cotizacion> findByIdUsuario(int idUsuario);
}
