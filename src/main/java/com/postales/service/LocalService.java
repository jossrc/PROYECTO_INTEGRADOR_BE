package com.postales.service;

import java.util.List;
import java.util.Optional;

import com.postales.entity.Local;


public interface LocalService {

	public List<Local> listarTodo();
    public Optional<Local> buscarPorId(int id);
    public Local registrar(Local local);
    public Local actualizar(Local local);
    public Local eliminar(Local local); 
	
}
