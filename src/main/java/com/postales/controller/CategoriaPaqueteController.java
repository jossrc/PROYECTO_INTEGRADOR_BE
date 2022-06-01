package com.postales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.postales.util.AppSettings;
import com.postales.util.ResponseApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.postales.entity.CategoriaPaquete;
import com.postales.service.CategoriaPaqueteService;

@RestController
@RequestMapping("/api/categoriaPaquete")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class CategoriaPaqueteController {

	@Autowired
	private CategoriaPaqueteService service;
	
	@GetMapping("/listar")
    @Secured("ROLE_ADMIN")
	@Transactional(readOnly = true)
    public ResponseEntity<ResponseApi<CategoriaPaquete>> listarTodo() {
		ResponseApi<CategoriaPaquete> data = new ResponseApi<>();
        try {
            List<CategoriaPaquete> categoriapaquete = service.listarTodo();

            data.setOk(true);

            if (categoriapaquete.size() <= 0) {
                data.setMensaje("No se encontraron resultados");
            } else {
                if (categoriapaquete.size() == 1) {
                    data.setMensaje("Se encontró un registro");
                } else {
                    data.setMensaje("Se encontraron " + categoriapaquete.size() + " registros");
                }
            }
            data.setDatos(categoriapaquete);

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);
    }
	
	@PostMapping("/registrar")
    @Secured("ROLE_ADMIN")
	@Transactional
    public ResponseEntity<ResponseApi<CategoriaPaquete>> registrar(@RequestBody CategoriaPaquete cpaquete) {
		ResponseApi<CategoriaPaquete> data = new ResponseApi<>();
        try {
            Optional<CategoriaPaquete> obj = service.buscarPorId(cpaquete.getIdCategoria());

            if(obj.isPresent()) {
            	data.setOk(false);
                data.setMensaje("Categoria paquete ya existe");
                return ResponseEntity.ok(data);
            }
            
            if(cpaquete.getNombre() == null) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar un nombre válido");
                return ResponseEntity.ok(data);
            }
            
            if(cpaquete.getDescripcion() == null) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar una descripcion válida");
                return ResponseEntity.ok(data);
            }
            
            CategoriaPaquete registrado = service.registrar(cpaquete);
            
            if (registrado == null) {
                data.setOk(false);
                data.setMensaje("Hubo un error al intentar registrar la categoria paquete");
                return ResponseEntity.ok(data);
            }
            CategoriaPaquete enviar = new CategoriaPaquete(registrado);
            data = new ResponseApi<>(true, "Se registró correctamente la categoria paquete", enviar );

        } catch (Exception e) {
        	e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }
        return ResponseEntity.ok(data);
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
