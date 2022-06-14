package com.postales.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Vehiculo")
public class Vehiculo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVehiculo;	
	private String placa;
    private String  modelo;
    private int capacidad;
    private int estado;
    
    public Vehiculo() {
        this.estado = 1;
    }
    
	public Vehiculo(Vehiculo vehiculo) {
		super();
		this.idVehiculo = vehiculo.getIdVehiculo();
		this.placa = vehiculo.getPlaca();
		this.modelo = vehiculo.getModelo();
		this.capacidad = vehiculo.getCapacidad();
		this.estado = vehiculo.getEstado();
	}	

}
