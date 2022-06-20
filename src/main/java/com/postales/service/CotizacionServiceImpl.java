package com.postales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.Cotizacion;
import com.postales.repository.CotizacionRepository;

@Service
public class CotizacionServiceImpl implements CotizacionService{

	@Autowired
	private CotizacionRepository repository;

	@Override
	public Cotizacion registrar(Cotizacion cotizacion) {
		return repository.save(cotizacion);
	}

	@Override
	public Optional<Cotizacion> buscarPorId(int id) {
		return repository.findById(id);
	}

	@Override
	public List<Cotizacion> listar() {
		return repository.findAll();
	}

	@Override
	public Cotizacion actualizar(Cotizacion cotizacion) {
		return repository.save(cotizacion);
	}

	@Override
	public Cotizacion eliminar(Cotizacion cotizacion) {
		return repository.save(cotizacion);
	}

	@Override
	public Optional<Cotizacion> buscarPorIdUsuario(int idUsuario) {
		return repository.findByIdUsuario(idUsuario);
	}
	
	

}
