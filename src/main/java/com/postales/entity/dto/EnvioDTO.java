package com.postales.entity.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioDTO {
	private int idEnvio;
	private Date fechaInicio;
	private Date fechaEntrega;
	private Date fechaCreacion;
	private int idCotizacion;
	private int idUsuario;
	private int idVehiculo;
	@Override
	public String toString() {
		return "EnvioDTO [idEnvio=" + idEnvio + ", fechaInicio=" + fechaInicio + ", fechaEntrega=" + fechaEntrega
				+ ", fechaCreacion=" + fechaCreacion + ", idCotizacion=" + idCotizacion + ", idUsuario=" + idUsuario
				+ ", idVehiculo=" + idVehiculo + "]";
	}
	
	
}
