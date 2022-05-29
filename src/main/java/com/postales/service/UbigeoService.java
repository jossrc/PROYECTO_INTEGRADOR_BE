package com.postales.service;

import java.util.List;

import com.postales.entity.Ubigeo;


public interface UbigeoService {

	public abstract List<String> listarDepartamentos();
	public abstract List<String> listarProvincias(String departamento);
	public abstract List<Ubigeo> listarDistritos(String departamento, String provincia);
	
}
