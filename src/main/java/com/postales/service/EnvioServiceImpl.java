package com.postales.service;

import java.util.List;
import java.util.Optional;

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

	@Override
	public List<Envio> listarEnvios() {
		// TODO Auto-generated method stub
		return repo.listarEnvios();
	}

	@Override
	public List<Envio> listarEnviosPorDia() {
		// TODO Auto-generated method stub
		return repo.listarEnviosPorDia();
	}

	@Override
	public List<Envio> listarEnviosPorUsuarioDia(int idUsu) {
		// TODO Auto-generated method stub
		return repo.listarEnviosPorIdYDia(idUsu);
	}

	@Override
	public List<Envio> listarEnviosEnviando(int idUsu) {
		// TODO Auto-generated method stub
		return repo.listarEnviosEnviando(idUsu);
	}

	@Override
	public Optional<Envio> buscarPorId(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Envio actualizar(Envio envio) {
		// TODO Auto-generated method stub
		return repo.save(envio);
	}



	/*@Override
	public List<Envio> listaPorIdLike(int idUsu) {
		// TODO Auto-generated method stub
		return repo.findByIdLike(idUsu);
	}*/



}
