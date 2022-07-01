package com.postales.service;

import java.util.List;

import com.postales.entity.Envio;

public interface EnvioService {
	
	public Envio registrar(Envio envio);

	public abstract List<Envio> listarEnvio();
	
	public List<Envio> listarEnvios();
	public List<Envio> listarEnviosPorDia();
	public List<Envio> listarEnviosPorUsuarioDia(int idUsu);
	public List<Envio> listarEnviosEnviando(int idUsu);
	
	public abstract List<Envio> listaEnvioUsu(int idUsu);
	
	/*public abstract List<Envio> listaPorIdLike(int idUsu);*/
	
}
