package com.postales.service;

import java.util.List;

import com.postales.entity.Cliente;


public interface ClienteService {
	
	public Cliente crearCliente(Cliente cliente);
	
	public Cliente obtenerClientePorId(Integer clienteid);
	
	public List<Cliente>listarCiente();
	
	public Cliente actualizarCliente(Integer clienteId, Cliente cliente);
	
	public void eliminarCliente(Integer clienteId);

}
