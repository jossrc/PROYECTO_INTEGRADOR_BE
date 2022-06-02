package com.postales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.postales.util.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.postales.entity.CategoriaPaquete;
import com.postales.service.CategoriaPaqueteService;

@RestController
@RequestMapping("/api/categoriaPaquete")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class CategoriaPaqueteController {

	@Autowired
	private CategoriaPaqueteService service;
	
	@GetMapping
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<CategoriaPaquete>> listarTodo() {
        List<CategoriaPaquete> lista = service.listarTodo();
        return ResponseEntity.ok(lista);
    }
	
	@PostMapping
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HashMap<String, Object>> registrar(@RequestBody CategoriaPaquete cpaquete) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<CategoriaPaquete> obj = service.buscarPorId(cpaquete.getIdCategoria());

            if (obj.isEmpty()) {
                cpaquete.setEstado(1);
                CategoriaPaquete objSalida = service.registrar(cpaquete);
                if (objSalida == null) {
                    salida.put("mensaje", "Error en el registro");
                } else {
                    salida.put("mensaje", "Registro exitoso");
                }
            } else {
                salida.put("mensaje", "Categoria paquete ya existe");
            }

        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", "Error en el registro : " + e.getMessage());
        }
        return ResponseEntity.ok(salida);
    }

    @PutMapping
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HashMap<String, Object>> actualizar(@RequestBody CategoriaPaquete cpaquete) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<CategoriaPaquete> obj = service.buscarPorId(cpaquete.getIdCategoria());

            if (obj.isPresent()) {
                CategoriaPaquete objSalida = service.actualizar(cpaquete);
                if (objSalida == null) {
                    salida.put("mensaje", "Error al actualizar");
                } else {
                    salida.put("mensaje", "Se actualizo correctamente");
                }
            } else {
                salida.put("mensaje", "Categoria paquete no existe");
            }

        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", "Error al actualizar : " + e.getMessage());
        }
        return ResponseEntity.ok(salida);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HashMap<String, Object>> eliminar(@PathVariable int id) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<CategoriaPaquete> optional = service.buscarPorId(id);
            if (optional.isPresent()) {
                CategoriaPaquete obj = optional.get();
                obj.setEstado(0);
                CategoriaPaquete eliminado = service.eliminar(obj);
                if (eliminado == null) {
                    salida.put("mensaje", "No se pudo eliminar el producto");
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
