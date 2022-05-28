package com.postales.service;

import java.util.List;

import com.postales.entity.Vehiculo;


public interface VehiculoService {
	
	public Vehiculo insertVehiculo (Vehiculo vehiculo);
	
	public Vehiculo listarVehiculoID (int idVehiculo);
	
	public List<Vehiculo> listarVehiculos ();
	
	public Vehiculo updateVehiculo (int idVehiculo, Vehiculo vehiculo);
	
	public void deleteVehiculo (int idVehiculo);

}
