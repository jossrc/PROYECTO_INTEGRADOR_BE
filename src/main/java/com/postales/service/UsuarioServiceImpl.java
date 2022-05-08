package com.postales.service;

import com.postales.entity.Usuario;
import com.postales.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService  {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Optional<Usuario> buscarPorEmailYPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }
}
