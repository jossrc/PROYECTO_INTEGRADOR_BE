package com.postales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.postales.util.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.postales.entity.Local;
import com.postales.service.LocalService;

@RestController
@RequestMapping("/api/local")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class LocalController {

	@Autowired
	private LocalService service;
	
	@GetMapping
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Local>> listarTodo() {
        List<Local> lista = service.listarTodo();
        return ResponseEntity.ok(lista);
    }
	
	@PostMapping
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HashMap<String, Object>> registrar(@RequestBody Local local) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<Local> obj = service.buscarPorId(local.getIdLocal());

            if (obj.isEmpty()) {
                local.setEstado(1);
                Local objSalida = service.registrar(local);
                if (objSalida == null) {
                    salida.put("mensaje", "Error en el registro");
                } else {
                    salida.put("mensaje", "Registro exitoso");
                }
            } else {
                salida.put("mensaje", "Local ya existe");
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
    public ResponseEntity<HashMap<String, Object>> actualizar(@RequestBody Local local) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        try {
            Optional<Local> obj = service.buscarPorId(local.getIdLocal());

            if (obj.isPresent()) {
                Local objSalida = service.actualizar(local);
                if (objSalida == null) {
                    salida.put("mensaje", "Error al actualizar");
                } else {
                    salida.put("mensaje", "Se actualizo correctamente");
                }
            } else {
                salida.put("mensaje", "Local no existe");
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
            Optional<Local> optional = service.buscarPorId(id);
            if (optional.isPresent()) {
                Local obj = optional.get();
                obj.setEstado(0);
                Local eliminado = service.eliminar(obj);
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
