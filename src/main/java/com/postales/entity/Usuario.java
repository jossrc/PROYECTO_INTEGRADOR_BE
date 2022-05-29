package com.postales.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int id;

    private String nombre;
    private String apellido;

    @Column(name="correo", length = 100, unique = true)
    private String email;

    @Column(length = 8)
    private String dni;

    @Column(length = 100)
    private String password;

    private boolean disponible; // Default true
    private int estado; // Default 1

    @Column(name = "idubigeo")
    private int idUbigeo;

    @Column(name = "idrol")
    private int idRol;

    @Column(name="idlocal")
    private Integer idLocal;

    public Usuario() {
        this.estado = 1;
        this.disponible = true;
    }

}
