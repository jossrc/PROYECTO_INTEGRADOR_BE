package com.postales.entity;

import java.sql.Date;

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
@Table(name = "cotizacion")
public class Cotizacion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCotizacion;
	private String descripcion;
	private double costo;
	private String direccion;
	private Date fechaCreacion;
	private int idUbigeo;
	private int idUsuario;
	private int idLocal;
	private int idRol;
	private int idPaquete;
	private int estado;
}
