package com.postales.repository;

import com.postales.entity.Usuario;
import com.postales.projections.UsuarioInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Optional<Usuario> findByEmailAndPassword(String email, String password);

    public Optional<Usuario> findByEmail(String email);

}
