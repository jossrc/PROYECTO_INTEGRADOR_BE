package com.postales.controller;

import java.util.List;

import com.postales.util.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.postales.entity.Envio;
import com.postales.service.EnvioService;

@Controller
@RequestMapping("/api/envios")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class EnvioController {
	

	@Autowired
	private EnvioService serv;
	
	@GetMapping("/listaEnvios")
	@Secured("ROLE_ADMIN")
	@ResponseBody
	public ResponseEntity<List<Envio>> listEnvios() {
		
		List<Envio> lista = serv.listarEnvio();
		return ResponseEntity.ok(lista);
		
	}
	
}
