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

	@Override
	public List<Envio> listaEnvioUsu(int idUsu) {
		// TODO Auto-generated method stub
		return repo.listaEnvioUsu(idUsu);
	}

	@Override
	public Envio registrar(Envio envio) {
		return repo.save(envio);
	}

	/*@Override
	public List<Envio> listaPorIdLike(int idUsu) {
		// TODO Auto-generated method stub
		return repo.findByIdLike(idUsu);
	}*/



}
