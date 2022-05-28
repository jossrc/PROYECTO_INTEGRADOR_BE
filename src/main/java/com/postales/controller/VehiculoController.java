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

import com.postales.entity.Vehiculo;
import com.postales.service.VehiculoService;

@RestController
public class VehiculoController {
	
	@Autowired
	private VehiculoService repositoryService;
	
	@GetMapping("/Vehiculo")
	public List<Vehiculo> listarVehiculos(){
		return repositoryService.listarVehiculos();
	}
	
	@GetMapping("/Vehiculo/{id}")
	public ResponseEntity<Vehiculo> listarVehiculoID(@PathVariable int  idVehiculo) {
		try {
			Vehiculo vehiculo= repositoryService.listarVehiculoID(idVehiculo);
			return new ResponseEntity<Vehiculo>(vehiculo,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Vehiculo>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/Vehiculo")
	public void insertVehiculo(@RequestBody Vehiculo vehiculo) {
		repositoryService.insertVehiculo(vehiculo);
		
	}

}
