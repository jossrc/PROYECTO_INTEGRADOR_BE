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
@Table(name = "usuario")
public class Cliente implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
	
	private String nombre;
    private String apellido;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    private boolean disponible;

}
