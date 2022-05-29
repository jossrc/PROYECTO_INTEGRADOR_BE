package com.postales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.Cliente;
import com.postales.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente crearCliente(Cliente cliente) {
		
		return clienteRepository.save(cliente);
		
	}

	@Override
	public Cliente actualizarCliente(Integer clienteId, Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarCliente(Integer clienteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente obtenerClientePorId(Integer clienteid) {
		// TODO Auto-generated method stub
		return clienteRepository.findById(clienteid).get();
	}

	@Override
	public List<Cliente> listarCiente() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}

}
