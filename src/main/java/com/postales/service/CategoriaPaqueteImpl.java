package com.postales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.CategoriaPaquete;
import com.postales.repository.CategoriaPaqueteRepository;

@Service
public class CategoriaPaqueteImpl implements CategoriaPaqueteService {

	@Autowired
	private CategoriaPaqueteRepository repository;
	
	@Override
	public List<CategoriaPaquete> listarTodo() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Optional<CategoriaPaquete> buscarPorId(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public CategoriaPaquete registrar(CategoriaPaquete cpaquete) {
		// TODO Auto-generated method stub
		return repository.save(cpaquete);
	}

	@Override
	public CategoriaPaquete actualizar(CategoriaPaquete cpaquete) {
		// TODO Auto-generated method stub
		return repository.save(cpaquete);
	}

	@Override
	public CategoriaPaquete eliminar(CategoriaPaquete cpaquete) {
		// TODO Auto-generated method stub
		return repository.save(cpaquete);
	}

}
