package com.postales.service;

import java.util.List;

import com.postales.entity.Envio;

public interface EnvioService {
	
	public Envio registrar(Envio envio);

	public abstract List<Envio> listarEnvio();
	
	public abstract List<Envio> listaEnvioUsu(int idUsu);
	
	/*public abstract List<Envio> listaPorIdLike(int idUsu);*/
	
}
