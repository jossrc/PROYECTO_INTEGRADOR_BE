package com.postales.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private int idUsuario;

    private String nombre;
    private String apellido;

    @Column(name="correo", length = 100, unique = true)
    private String email;

    @Column(length = 8)
    private String dni;

    @Column(length = 100)
    private String password;

    private String direccion;

    private boolean disponible; // Default true
    private int estado; // Default 1

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUbigeo")
    private Ubigeo ubigeo;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idrol")
    private Rol rol;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLocal")
    private Local local;

    public Usuario() {
        this.estado = 1;
        this.disponible = true;
    }

    public Usuario(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
        this.dni = usuario.getDni();
        this.direccion = usuario.getDireccion();
        this.disponible = usuario.isDisponible();
        this.estado = usuario.getEstado();
        this.ubigeo = usuario.getUbigeo();
        this.rol = usuario.getRol();
        this.local = usuario.getLocal();
    }

}
