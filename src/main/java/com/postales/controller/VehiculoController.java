package com.postales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.postales.util.AppSettings;
import com.postales.util.ResponseApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.postales.entity.Vehiculo;
import com.postales.service.VehiculoService;

@RestController
@RequestMapping("/api/vehiculo")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class VehiculoController {

	@Autowired
	private VehiculoService service;

	/*@GetMapping
	@ResponseBody
	public ResponseEntity<List<Vehiculo>> listarVehiculos() {
		List<Vehiculo> listado = repositoryService.listarVehiculos();
		return ResponseEntity.ok(listado);
	}*/
	
	@GetMapping("/listar")
    @Secured("ROLE_ADMIN")
	@Transactional(readOnly = true)
    public ResponseEntity<ResponseApi<Vehiculo>> listarTodo() {
		ResponseApi<Vehiculo> data = new ResponseApi<>();
        try {
            List<Vehiculo> vehiculo = service.listarVehiculos();

            data.setOk(true);

            if (vehiculo.size() <= 0) {
                data.setMensaje("No se encontraron resultados");
            } else {
                if (vehiculo.size() == 1) {
                    data.setMensaje("Se encontró un registro");
                } else {
                    data.setMensaje("Se encontraron " + vehiculo.size() + " registros");
                }
            }
            data.setDatos(vehiculo);

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
	public ResponseEntity<HashMap<String, Object>> insert(@RequestBody Vehiculo vehiculo) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			Optional<Vehiculo> obj = repositoryService.listarVehiculoID(vehiculo.getIdVehiculo());
			if (obj.isEmpty()) {
				vehiculo.setEstado(1);
				Vehiculo objSalida = repositoryService.insertVehiculo(vehiculo);
				if (objSalida == null) {
					salida.put("mensaje", "Error en el registro");
				} else {
					salida.put("mensaje", "¡Vehículo registrado con éxito!");
				}
			} else {
				salida.put("mensaje", "Vehículo ya existe");
			}

		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en el registro : " + e.getMessage());
		}
		return ResponseEntity.ok(salida);
	}*/
	
	@PostMapping("/registrar")
    @Secured("ROLE_ADMIN")
	@Transactional
    public ResponseEntity<ResponseApi<Vehiculo>> registrar(@RequestBody Vehiculo vehiculo) {
		ResponseApi<Vehiculo> data = new ResponseApi<>();
        try {
            Optional<Vehiculo> obj = service.listarVehiculoID(vehiculo.getIdVehiculo());

            if(obj.isPresent()) {
            	data.setOk(false);
                data.setMensaje("Vehiculo ya existe");
                return ResponseEntity.ok(data);
            }
            
            if(vehiculo.getPlaca() == null) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar una placa válida");
                return ResponseEntity.ok(data);
            }
            
            if(vehiculo.getModelo() == null) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar un modelo válida¿o");
                return ResponseEntity.ok(data);
            }
            
            if(vehiculo.getCapacidad() == 0) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar una capacidad válida");
                return ResponseEntity.ok(data);
            }
            
            Vehiculo registrado = service.insertVehiculo(vehiculo);
            
            if (registrado == null) {
                data.setOk(false);
                data.setMensaje("Hubo un error al intentar registrar el vehiculo");
                return ResponseEntity.ok(data);
            }
            Vehiculo enviar = new Vehiculo(registrado);
            data = new ResponseApi<>(true, "Se registró correctamente el vehiculo", enviar );

        } catch (Exception e) {
        	e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }
        return ResponseEntity.ok(data);
	}

	/*@PutMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> update(@RequestBody Vehiculo vehiculo) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			Optional<Vehiculo> obj = repositoryService.listarVehiculoID(vehiculo.getIdVehiculo());

			if (obj.isPresent()) {
				Vehiculo objSalida = repositoryService.updateVehiculo(vehiculo);
				if (objSalida == null) {
					salida.put("mensaje", "¡Error al actualizar!");
				} else {
					salida.put("mensaje", "¡Vehículo actualizado con éxito");
				}
			} else {
				salida.put("mensaje", "¡Vehículo no existe!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error al actualizar : " + e.getMessage());
		}
		return ResponseEntity.ok(salida);
	}*/
	
	@PutMapping("/actualizar/{id}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ResponseApi<Vehiculo>> actualizar(@PathVariable("id") int idVehiculo ,@RequestBody Vehiculo vehiculo) {
		ResponseApi<Vehiculo> data = new ResponseApi<>();
        try {
            Optional<Vehiculo> encontrado = service.listarVehiculoID(idVehiculo);

            if (encontrado.isEmpty()) {
                data.setOk(false);
                data.setMensaje("Vehiculo no existe o no está disponible");
                return ResponseEntity.ok(data);
            }
             
            
            vehiculo.setEstado(1);
             
            if(vehiculo.getPlaca() == null) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar una placa válida");
                return ResponseEntity.ok(data);
            }
            
            if(vehiculo.getModelo() == null) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar un modelo válida¿o");
                return ResponseEntity.ok(data);
            }
            
            if(vehiculo.getCapacidad() == 0) {
            	data.setOk(false);
                data.setMensaje("Se requiere ingresar una capacidad válida");
                return ResponseEntity.ok(data);
            }
            
            
            vehiculo.setIdVehiculo(idVehiculo);
            
            Vehiculo actualizado = service.updateVehiculo(vehiculo);

            if (actualizado == null) {
                data.setOk(false);
                data.setMensaje("Hubo un error al intentar actualizar el vehiculo");
                return ResponseEntity.ok(data);
            }
            Vehiculo enviar = new Vehiculo(actualizado);
            data = new ResponseApi<>(true, "Se actualizó correctamente el vehiculo", enviar );

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);
    }

	/*@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> delete(@PathVariable int id) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			Optional<Vehiculo> optional = repositoryService.listarVehiculoID(id);
			if (optional.isPresent()) {
				Vehiculo obj = optional.get();
				obj.setEstado(0);
				Vehiculo eliminado = repositoryService.deleteVehiculo(obj);
				if (eliminado == null) {
					salida.put("mensaje", "¡No se pudo eliminar el vehículo!");
				} else {
					salida.put("mensaje", "¡Vehículo eliminado con éxito!");
				}
			} else {
				salida.put("mensaje", "Código ingresado no existe:  " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "¡Sucedió un error inesperado en la eliminación!" + e.getMessage());
		}
		return ResponseEntity.ok(salida);
	}*/
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@Transactional
    public ResponseEntity<HashMap<String, Object>> eliminar(@PathVariable int id) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
        salida.put("objeto", null);
        salida.put("datos", new ArrayList<>());
        try {
            Optional<Vehiculo> optional = service.listarVehiculoID(id);
            if (optional.isPresent()) {
            	Vehiculo vehiculo = optional.get();
            	vehiculo.setEstado(0);
            	Vehiculo eliminado = service.updateVehiculo(vehiculo);
                if (eliminado != null) {
                    salida.put("ok", true);
                    salida.put("mensaje", "Se elimino el vehiculo");
                } else {
                    salida.put("ok", false);
                    salida.put("mensaje", "No se pudo eliminar el vehiculo");
                }

            } else {
                salida.put("ok", false);
                salida.put("mensaje", "El vehiculo con ID " + id + " no existe");
            }

        } catch (Exception e) {
            salida.put("ok", false);
            salida.put("mensaje", "Sucedió un error inesperado consulte con su administrador");
        }

        return ResponseEntity.ok(salida);
    }

}
