package com.postales.service;

import java.util.List;
import java.util.Optional;

import com.postales.entity.Vehiculo;


public interface VehiculoService {
	
	public Vehiculo insertVehiculo (Vehiculo vehiculo);	
	
	public Vehiculo updateVehiculo (Vehiculo vehiculo);
	
	public Vehiculo deleteVehiculo (Vehiculo vehiculo);
	
	public Optional<Vehiculo> listarVehiculoID (int idVehiculo);
	
	public List<Vehiculo> listarVehiculos ();

}
