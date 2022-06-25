package com.postales.controller;

import java.util.List;

import com.postales.util.AppSettings;
import io.swagger.v3.oas.annotations.Operation;
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
	@Operation(summary = "Listar departamentos")
	@ResponseBody
	public List<String> Departamentos() {
		return ubigeoService.listarDepartamentos();
	}

	@GetMapping("/provincia/{depa}")
	@Operation(summary = "Listar provincias")
	@ResponseBody
	public List<String> Provincias(@PathVariable("depa") String departamento) {
		return ubigeoService.listarProvincias(departamento);
	}

	@GetMapping("/distrito/{depa}/{prov}")
	@Operation(summary = "Listar distritos (+Ubigeos)")
	@ResponseBody
	public List<Ubigeo> Distritos(@PathVariable("depa") String departamento, @PathVariable("prov") String provincia) {
		return ubigeoService.listarDistritos(departamento, provincia);
	}

}
