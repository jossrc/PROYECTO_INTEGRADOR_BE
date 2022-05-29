package com.postales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.Local;
import com.postales.repository.LocalRepository;

@Service
public class LocalServiceImpl implements LocalService {

	@Autowired
	private LocalRepository repository;
	
	@Override
	public List<Local> listarTodo() {

		return repository.findAll();
	}

	@Override
	public Optional<Local> buscarPorId(int id) {

		return repository.findById(id);
	}

	@Override
	public Local registrar(Local local) {

		return repository.save(local);
	}

	@Override
	public Local actualizar(Local local) {
		
		return repository.save(local);
	}

	@Override
	public Local eliminar(Local local) {
		
		return repository.save(local);
	}

	
}
