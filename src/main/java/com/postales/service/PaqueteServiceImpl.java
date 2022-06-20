package com.postales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.Paquete;
import com.postales.repository.PaqueteRepository;

@Service
public class PaqueteServiceImpl implements PaqueteService{

	@Autowired
	private PaqueteRepository repository;
	
	@Override
	public Paquete registrar(Paquete paquete) {
		return repository.save(paquete);
	}

}
