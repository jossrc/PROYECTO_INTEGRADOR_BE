package com.postales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.postales.entity.Cliente;
import com.postales.entity.Cotizacion;
import com.postales.service.CotizacionService;

@Controller
@RequestMapping("/api/cotizaciones")
public class CotizacionController {
	@Autowired
	private CotizacionService service;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Cotizacion>> listadoCotizacones(){
		List<Cotizacion> lista = service.listar();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> registrar(@RequestBody Cotizacion cotizacion) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<Cotizacion> obj = service.buscarPorId(cotizacion.getIdCotizacion());

            if (obj.isEmpty()) {
            	cotizacion.setEstado(1);
                Cotizacion objSalida = service.registrar(cotizacion);
                if (objSalida == null) {
                    salida.put("mensaje", "Error en el registro");
                } else {
                    salida.put("mensaje", "Registro exitoso");
                }
            } else {
                salida.put("mensaje", "Cotizacion ya existe");
            }

        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", "Error en el registro : " + e.getMessage());
        }
        return ResponseEntity.ok(salida);
    }
	
	@PutMapping
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> actualizar(@RequestBody Cotizacion cotizacion) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<Cotizacion> obj = service.buscarPorId(cotizacion.getIdCotizacion());

            if (obj.isPresent()) {
            	Cotizacion objSalida = service.actualizar(cotizacion);
                if (objSalida == null) {
                    salida.put("mensaje", "Error al actualizar");
                } else {
                    salida.put("mensaje", "Se actualizo correctamente");
                }
            } else {
                salida.put("mensaje", "Cotizacion no existe");
            }

        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", "Error al actualizar : " + e.getMessage());
        }
        return ResponseEntity.ok(salida);
    }
	
	@DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> eliminar(@PathVariable int id) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<Cotizacion> optional = service.buscarPorId(id);
            if (optional.isPresent()) {
            	Cotizacion obj = optional.get();
                obj.setEstado(0);
                Cotizacion eliminado = service.eliminar(obj);
                if (eliminado == null) {
                    salida.put("mensaje", "No se pudo eliminar la cotizacion");
                } else {
                    salida.put("mensaje", "Eliminación exitosa");
                }
            }else {
                salida.put("mensaje", "El ID no existe : " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", "Error en la eliminación " + e.getMessage());
        }
        return ResponseEntity.ok(salida);
    }
	
}