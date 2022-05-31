package com.postales.service;

import com.postales.entity.Usuario;
import com.postales.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService  {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Optional<Usuario> buscarPorEmailYPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Usuario registrarCliente(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario registrarEmpleado(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario actualizarEmpleado(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    @Override
    public List<Usuario> listarEmpleados() {
        return repository.listarEmpleados();
    }

    @Override
    public List<Usuario> listarClientes() {
        return repository.listarClientes();
    }

    @Override
    public Optional<Usuario> obtenerEmpleado(int id) {
        return repository.obtenerEmpleado(id);
    }
}
