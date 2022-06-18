package com.postales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.postales.util.AppSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.postales.entity.Envio;
import com.postales.service.EnvioService;

@Controller
@RequestMapping("/api/envios")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class EnvioController {
	

	private Logger log = LoggerFactory.getLogger(EnvioController.class);
	
	@Autowired
	private EnvioService serv;
	
	
	@GetMapping("/listaEnvios")
	@Secured("ROLE_ADMIN")
	@ResponseBody
	public ResponseEntity<List<Envio>> listEnvios() {
		
		List<Envio> lista = serv.listarEnvio();
		return ResponseEntity.ok(lista);
		
	}
	
	/*@GetMapping("/listaEnvioPorIdLike/{idUsu}")
	@ResponseBody
	public ResponseEntity<List<Envio>> listaEnvioPorIdLike(@PathVariable("idUsu") int idUsu){
		log.info(" ==> listaEnvioPorIdLike ==> idUsu : " + idUsu);
		
		List<Envio> lista = null;
		try {
			if(idUsu == 0) {
				lista = serv.listarEnvio();
			} else {
				lista = serv.listaPorIdLike(idUsu);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(lista);
	}*/
	
	
	
	@GetMapping("/listaEnviosUsu/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listEnviosUsu(@RequestParam(value="idUsu", required=false, defaultValue="0") int idUsu) {
		
		Map<String, Object> salida = new HashMap<String, Object>();
		try {
			List<Envio> lista = serv.listaEnvioUsu(idUsu);
			if(CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existe elementos para la consulta");
			} else {
				salida.put("lista", lista);
				salida.put("mensaje", "Se tiene " + lista.size() + " elementos");
			}
		} catch(Exception e) {
			salida.put("mensaje", "Error : " + e.getMessage());
		}
		
		return ResponseEntity.ok(salida);
		
	}
	
	
	
}
