package com.postales.entity.dto;

import java.util.List;
import com.postales.entity.Ubigeo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CotizacionDTO {
	private int idCotizacion;
	private String direccion;
	private String descripcion;
	private Ubigeo ubigeo;
	private List<String> productos;
	private int cantidad;
	private double pesoTotal;
	private int idCategoria;

}
