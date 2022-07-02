package com.postales.service;
import java.util.List;

import com.postales.entity.Paquete;

public interface PaqueteService {
	public Paquete registrar(Paquete paquete);
	public List<Paquete> listarPaquetes();
}
