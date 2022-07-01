package com.postales.service;

import java.util.List;
import java.util.Optional;

import com.postales.entity.Cotizacion;

public interface CotizacionService {
	public Cotizacion registrar(Cotizacion cotizacion);
	
	public Optional<Cotizacion> buscarPorId(int id);
	
	public Optional<Cotizacion> buscarPorIdUsuario(int idUsuario);
	
	public List<Cotizacion> listar();
	
	public List<Cotizacion> listarPorIdUsuario(int idUsuario);
	public List<Cotizacion> listarCotizaciones();
	public List<Cotizacion> listarCotizacionesPorDia();
	public List<Cotizacion> listarCotizacionesAprobado(int idUsuario);
	public List<Cotizacion> listarCotizacionesPendiente(int idUsuario);
	public List<Cotizacion> listarCotizacionesRechazado(int idUsuario);
	public List<Cotizacion> listarPorIdDia(int idUsuario);
	
	public Cotizacion actualizar(Cotizacion cotizacion);
	
	public Cotizacion eliminar(Cotizacion cotizacion);
}
