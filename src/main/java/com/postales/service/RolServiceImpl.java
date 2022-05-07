package com.postales.service;

import com.postales.entity.Rol;
import com.postales.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService{

    @Autowired
    private RolRepository repository;

    @Override
    public List<Rol> listar() {
        return repository.findAll();
    }

}
