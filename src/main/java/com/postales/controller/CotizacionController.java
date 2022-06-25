package com.postales.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.postales.entity.Cotizacion;
import com.postales.entity.Paquete;
import com.postales.entity.dto.CotizacionDTO;
import com.postales.service.CotizacionService;
import com.postales.service.PaqueteService;
import com.postales.service.UsuarioService;
import com.postales.util.ResponseApi;

@Controller
@RequestMapping("/api/cotizaciones")
public class CotizacionController {
	@Autowired
	private CotizacionService service;
	
	@Autowired
	private PaqueteService servicePaquete;
	
	@Autowired
	private UsuarioService serviceUsuario;
	
	/*@GetMapping
	@ResponseBody
	public ResponseEntity<List<Cotizacion>> listadoCotizacones(){
		List<Cotizacion> lista = service.listar();
		return ResponseEntity.ok(lista);
	}*/
	
	@GetMapping("/listar")
	@Secured({"ROLE_ADMIN" , "ROLE_CLIENTE", "ROLE_OPERADOR"})
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseApi<Cotizacion>> listarCotizaciones() {
        ResponseApi<Cotizacion> data = new ResponseApi<>();
        try {
        	int idUsu = serviceUsuario.obtenerIdUsuarioPeticion();
        	
            List<Cotizacion> cotizaciones = service.listarPorIdUsuario(idUsu);

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

            data.setDatos(cotizaciones);
        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);
	}
	
	/*@PostMapping
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
    }*/
	
	@PostMapping("/registrar")
	@Secured({"ROLE_ADMIN" , "ROLE_CLIENTE", "ROLE_OPERADOR"})
    @Transactional
    public ResponseEntity<ResponseApi<Cotizacion>> registrarCotizacion(@RequestBody CotizacionDTO cotizacion) {
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
            
            if (cotizacion.getDireccion() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar una direccion");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getProductos() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar productos");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getUbigeo() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un ubigeo");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getPesoTotal() < 0) {
                data.setOk(false);
                data.setMensaje("Tiene que ser mayor que 0");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getCantidad() < 0) {
                data.setOk(false);
                data.setMensaje("Tiene que ser mayor que 0");
                return ResponseEntity.ok(data);
            }
            
            if (cotizacion.getIdCategoria() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar una categoria");
                return ResponseEntity.ok(data);
            }
            
            Cotizacion objCotizacion = new Cotizacion();
            
            objCotizacion.setDireccion(cotizacion.getDireccion());
            objCotizacion.setDescripcion(cotizacion.getDescripcion());
            objCotizacion.setUbigeo(cotizacion.getUbigeo());
            //obj.setPaquete(cotizacion.getp)

            Paquete objPaquete = new Paquete();
            String listaProductos = "";
            
            for (int i = 0; i < cotizacion.getProductos().size(); i++) {
				listaProductos += cotizacion.getProductos().get(i);
				if(i != cotizacion.getProductos().size()) {
					listaProductos += ";";
				}
			}
            
            objPaquete.setProductos(listaProductos);
            objPaquete.setCantidad(cotizacion.getCantidad());
            objPaquete.setPesototal(cotizacion.getPesoTotal());
            objPaquete.setIdcategoria(cotizacion.getIdCategoria());
            
            Paquete paqueteRetorno = servicePaquete.registrar(objPaquete);
            
            objCotizacion.setPaquete(paqueteRetorno);
            
            double precio = cotizacion.getPesoTotal() * 4.0 + 20;
            
            objCotizacion.setCosto(precio);
            objCotizacion.setFechaCreacion(new Date());
            
            int usuarioRetorno = serviceUsuario.obtenerIdUsuarioPeticion();
            
            objCotizacion.setIdUsuario(usuarioRetorno);
            
            Cotizacion registrado = service.registrar(objCotizacion);

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
	
//	@PutMapping("/actualizar/{id}")
//    @Secured("ROLE_ADMIN")
//    @Transactional
//    public ResponseEntity<ResponseApi<Cotizacion>> actualizarCotizacion(@PathVariable("id") int cotizacionId ,@RequestBody Cotizacion cotizacion) {
//        ResponseApi<Cotizacion> data = new ResponseApi<>();
//
//        try {
//            Optional<Cotizacion> encontrado = service.buscarPorId(cotizacionId);
//
//            if (encontrado.isEmpty()) {
//                data.setOk(false);
//                data.setMensaje("Cotizacion no existe o no está disponible");
//                return ResponseEntity.ok(data);
//            }
//            
//            if (cotizacion.getDescripcion() == null) {
//                data.setOk(false);
//                data.setMensaje("Se requiere ingresar una descripción");
//                return ResponseEntity.ok(data);
//            }
//
//            if (cotizacion.getCosto() == 0 ) {
//                data.setOk(false);
//                data.setMensaje("Se requiere un costo");
//                return ResponseEntity.ok(data);
//            }
//            
//            if (cotizacion.getDireccion() == null) {
//                data.setOk(false);
//                data.setMensaje("Se requiere ingresar una direccion");
//                return ResponseEntity.ok(data);
//            }
//            
//            if (cotizacion.getIdUbigeo() == 0) {
//                data.setOk(false);
//                data.setMensaje("Se requiere ingresar un ubigeo");
//                return ResponseEntity.ok(data);
//            }
//            
//            if (cotizacion.getIdUsuario() == 0) {
//                data.setOk(false);
//                data.setMensaje("Se requiere ingresar un usuario");
//                return ResponseEntity.ok(data);
//            }
//            
//            if (cotizacion.getIdPaquete() == 0) {
//                data.setOk(false);
//                data.setMensaje("Se requiere ingresar un paquete");
//                return ResponseEntity.ok(data);
//            }
//
//            cotizacion.setIdCotizacion(cotizacionId);
//            Cotizacion actualizado = service.actualizar(cotizacion);
//
//            if (actualizado == null) {
//                data.setOk(false);
//                data.setMensaje("Hubo un error al intentar actualizar la cotizacion");
//                return ResponseEntity.ok(data);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            data.setOk(false);
//            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
//            data.setError(e.getMessage());
//        }
//
//        return ResponseEntity.ok(data);
//    }
	
	/*@PutMapping
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
    }*/
	
	@DeleteMapping("/eliminar/{id}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity<HashMap<String, Object>> eliminarCotizacion(@PathVariable int id) {
        HashMap<String, Object> salida = new HashMap<String, Object>();
        salida.put("objeto", null);
        salida.put("datos", new ArrayList<>());
        try {
            Optional<Cotizacion> optional = service.buscarPorId(id);
            if (optional.isPresent()) {
            	Cotizacion cotizacion = optional.get();
            	cotizacion.setEstado(0);
                Cotizacion eliminado = service.actualizar(cotizacion);
                if (eliminado != null) {
                    salida.put("ok", true);
                    salida.put("mensaje", "No se pudo eliminar la cotizacion");
                } else {
                    salida.put("ok", false);
                    salida.put("mensaje", "No se pudo eliminar la cotizacion");
                }

            } else {
                salida.put("ok", false);
                salida.put("mensaje", "La cotizacion con ID " + id + " no existe");
            }

        } catch (Exception e) {
            salida.put("ok", false);
            salida.put("mensaje", "Sucedió un error inesperado consulte con su administrador");
        }

        return ResponseEntity.ok(salida);
    }
	
	/*@DeleteMapping("/{id}")
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
    }*/
	
}
