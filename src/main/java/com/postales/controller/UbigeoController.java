package com.postales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.postales.entity.Ubigeo;
import com.postales.service.UbigeoService;

@RestController
@RequestMapping("/api/ubigeo")
public class UbigeoController {

	@Autowired
	private UbigeoService ubigeoService;

	@GetMapping("/departamento")
	@ResponseBody
	public List<String> Departamentos() {
		return ubigeoService.listarDepartamentos();
	}

	@GetMapping("/provincia/{depa}")
	@ResponseBody
	public List<String> Provincias(@PathVariable("depa") String departamento) {
		return ubigeoService.listarProvincias(departamento);
	}

	@GetMapping("/distrito/{depa}/{prov}")
	@ResponseBody
	public List<Ubigeo> Distritos(@PathVariable("depa") String departamento, @PathVariable("prov") String provincia) {
		return ubigeoService.listarDistritos(departamento, provincia);
	}

}
