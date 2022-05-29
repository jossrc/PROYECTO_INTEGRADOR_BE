package com.postales.service;

import java.util.List;
import java.util.Optional;

import com.postales.entity.CategoriaPaquete;

public interface CategoriaPaqueteService {

	public List<CategoriaPaquete> listarTodo();
    public Optional<CategoriaPaquete> buscarPorId(int id);
    public CategoriaPaquete registrar(CategoriaPaquete cpaquete);
    public CategoriaPaquete actualizar(CategoriaPaquete cpaquete);
    public CategoriaPaquete eliminar(CategoriaPaquete cpaquete); 
}
