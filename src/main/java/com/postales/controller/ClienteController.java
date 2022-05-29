package com.postales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.postales.entity.Cliente;
import com.postales.service.ClienteService;

@RestController
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/cliente")
	public List<Cliente> listarCliente(){
		return clienteService.listarCiente();
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> obtenerProducto(@PathVariable Integer id) {
		try {
			Cliente cliente= clienteService.obtenerClientePorId(id);
			return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/cliente")
	public void guardarProducto(@RequestBody Cliente cliente) {
		clienteService.crearCliente(cliente);
		
	}

}
