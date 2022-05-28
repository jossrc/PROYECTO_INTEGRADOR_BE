package com.postales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.Envio;
import com.postales.repository.EnvioRepository;

@Service
public class EnvioServiceImpl implements EnvioService {

	@Autowired
	private EnvioRepository repo;
	
	@Override
	public List<Envio> listarEnvio() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}



}
