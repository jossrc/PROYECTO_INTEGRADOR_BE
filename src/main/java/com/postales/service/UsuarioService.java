package com.postales.service;

import com.postales.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    public Optional<Usuario> buscarPorEmailYPassword(String email, String password);

    public Usuario registrarCliente(Usuario usuario);

    public Usuario registrarEmpleado(Usuario usuario);

    public List<Usuario> listarUsuarios();

}
