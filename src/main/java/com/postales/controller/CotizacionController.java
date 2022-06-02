package com.postales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.postales.entity.Cotizacion;
import com.postales.service.CotizacionService;
import com.postales.util.ResponseApi;

@Controller
@RequestMapping("/api/cotizaciones")
public class CotizacionController {
	@Autowired
	private CotizacionService service;
	
//	@GetMapping
//	@ResponseBody
//	public ResponseEntity<List<Cotizacion>> listadoCotizacones(){
//		List<Cotizacion> lista = service.listar();
//		return ResponseEntity.ok(lista);
//	}
	
	@GetMapping("/listar")
	@Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseApi<Cotizacion>> listarCotizaciones() {
        ResponseApi<Cotizacion> data = new ResponseApi<>();
        try {
            List<Cotizacion> cotizaciones = service.listar();

            data.setOk(true);

            if (cotizaciones.size() <= 0) {
                data.setMensaje("No se encontraron resultados");
            } else {
                if (cotizaciones.size() == 1) {
                    data.setMensaje("Se encontró un registro");
                } else {
                    data.setMensaje("Se encontraron " + cotizaciones.size() + " registros");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);
    }
	
//	@PostMapping
//    @ResponseBody
//    public ResponseEntity<HashMap<String, Object>> registrar(@RequestBody Cotizacion cotizacion) {
//        HashMap<String, Object> salida = new HashMap<String, Object>();
//        try {
//            Optional<Cotizacion> obj = service.buscarPorId(cotizacion.getIdCotizacion());
//
//            if (obj.isEmpty()) {
//            	cotizacion.setEstado(1);
//                Cotizacion objSalida = service.registrar(cotizacion);
//                if (objSalida == null) {
//                    salida.put("mensaje", "Error en el registro");
//                } else {
//                    salida.put("mensaje", "Registro exitoso");
//                }
//            } else {
//                salida.put("mensaje", "Cotizacion ya existe");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            salida.put("mensaje", "Error en el registro : " + e.getMessage());
//        }
//        return ResponseEntity.ok(salida);
//    }
	
	@PostMapping("/registrar")
    @Transactional
    public ResponseEntity<ResponseApi<Cotizacion>> registrarCotizacion(@RequestBody Cotizacion cotizacion) {
        ResponseApi<Cotizacion> data = new ResponseApi<>();
        try {
            Optional<Cotizacion> existe = service.buscarPorId(cotizacion.getIdCotizacion());

            if (existe.isPresent()) {
                data.setOk(false);
                data.setMensaje("Cotizacion ya se encuentra registrado");
                return ResponseEntity.ok(data);
            }

            if (cotizacion.getDescripcion() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar una descripción");
                return ResponseEntity.ok(data);
            }

            if (cotizacion.getCosto() == 0 ) {
                data.setOk(false);
                data.setMensaje("Se requiere un costo");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getDireccion() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar una direccion");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getIdUbigeo() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un ubigeo");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getIdUsuario() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un usuario");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getIdLocal() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un local");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getIdRol() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un rol");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getIdPaquete() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un paquete");
                return ResponseEntity.ok(data);
            }

            Cotizacion registrado = service.registrar(cotizacion);

            if (registrado == null) {
                data.setOk(false);
                data.setMensaje("Hubo un error al intentar registrar la cotizacion");
                return ResponseEntity.ok(data);
            }

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
