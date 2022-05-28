package com.postales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postales.entity.Ubigeo;
import com.postales.repository.UbigeoRepository;

@Service
public class UbigeoServiceImpl implements UbigeoService {

	@Autowired
	private UbigeoRepository repository;

	@Override
	public List<String> listarDepartamentos() {
		return repository.listarDepartamentos();
	}

	@Override
	public List<String> listarProvincias(String departamento) {
		return repository.listarProvincias(departamento);
	}

	@Override
	public List<Ubigeo> listarDistritos(String departamento, String provincia) {
		return repository.listarDistritos(departamento, provincia);
	}

}
