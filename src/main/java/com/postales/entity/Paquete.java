package com.postales.entity;

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
@Table(name = "paquete")
public class Paquete {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idpaquete;
	private double pesototal;
	private int cantidad;
	private int idcategoria;
	private String productos;
}
