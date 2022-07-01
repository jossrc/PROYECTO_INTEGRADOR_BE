package com.postales.repository;

import com.postales.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Optional<Usuario> findByEmailAndPassword(String email, String password);

    public Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where u.rol.id in (1,2) and u.disponible = true")
    public List<Usuario> listarEmpleados();

    @Query("select u from Usuario u where u.rol.id = 3 and u.disponible = true")
    public List<Usuario> listarClientes();

    @Query("select u from Usuario u where u.idUsuario = ?1 and u.rol.id in (1,2) and u.disponible = true")
    public Optional<Usuario> obtenerEmpleado(int id);
    
    @Query("select u from Usuario u where u.idUsuario = ?1 and u.disponible = true")
    public List<Usuario> obtenerEmpleadoPerfilRol(int id);
    
    @Query("select u from Usuario u where u.disponible = true")
    public List<Usuario> obtenerCantidadUsuarios();

}
