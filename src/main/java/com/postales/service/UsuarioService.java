package com.postales.service;

import com.postales.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {
    public Optional<Usuario> buscarPorEmailYPassword(String email, String password);
}
