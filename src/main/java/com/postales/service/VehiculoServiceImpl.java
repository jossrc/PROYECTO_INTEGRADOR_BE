package com.postales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.Vehiculo;
import com.postales.repository.VehiculoRepository;

@Service
public class VehiculoServiceImpl implements VehiculoService{
	
	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Override
	public Vehiculo insertVehiculo(Vehiculo vehiculo) {
		
		return vehiculoRepository.save(vehiculo);
		
	}

	@Override
	public Vehiculo updateVehiculo(Vehiculo vehiculo) {

		return vehiculoRepository.save(vehiculo);
		
	}

	@Override
	public Vehiculo deleteVehiculo(Vehiculo vehiculo) {		
		
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public Optional<Vehiculo> listarVehiculoID(int idVehiculo) {
		
		return vehiculoRepository.findById(idVehiculo);
		
	}

	@Override
	public List<Vehiculo> listarVehiculos() {
		
		return vehiculoRepository.findAll();
		
	}

}
