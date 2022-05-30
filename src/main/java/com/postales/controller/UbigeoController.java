package com.postales.controller;

import java.util.List;

import com.postales.util.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.postales.entity.Ubigeo;
import com.postales.service.UbigeoService;

@RestController
@RequestMapping("/api/ubigeo")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
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
