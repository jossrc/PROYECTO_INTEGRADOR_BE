package com.postales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.postales.util.AppSettings;
import com.postales.util.ResponseApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.postales.entity.Local;
import com.postales.service.LocalService;

@RestController
@RequestMapping("/api/local")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class LocalController {

	@Autowired
	private LocalService service;

	@GetMapping("/listar")
	@Secured("ROLE_ADMIN")
	@Operation(summary = "Listar locales")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseApi<Local>> listarTodo() {
		ResponseApi<Local> data = new ResponseApi<>();
		try {
			List<Local> locales = service.listarTodo();

			data.setOk(true);

			if (locales.size() <= 0) {
				data.setMensaje("No se encontraron resultados");
			} else {
				if (locales.size() == 1) {
					data.setMensaje("Se encontró un registro");
				} else {
					data.setMensaje("Se encontraron " + locales.size() + " registros");
				}
			}

			data.setDatos(locales);
			
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
	@Operation(summary = "Registrar local")
	@ResponseBody
	@Transactional
	public ResponseEntity<ResponseApi<Local>> registrar(@RequestBody Local local) {
		ResponseApi<Local> data = new ResponseApi<>();
		try {
			local.setEstado(1);
			System.out.println(local.getIdLocal());
			Optional<Local> existe = service.buscarPorId(local.getIdLocal());

			if (existe.isPresent()) {
				data.setOk(false);
				data.setMensaje("Local ya se encuentra registrado");
				return ResponseEntity.ok(data);
			}

			if (local.getNombre() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar un nombre válido");
				return ResponseEntity.ok(data);
			}

			if (local.getHora_inicio() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una hora inicio válido");
				return ResponseEntity.ok(data);
			}

			if (local.getHora_fin() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una hora final válido");
				return ResponseEntity.ok(data);
			}

			if (local.getDireccion() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una dirección válido");
				return ResponseEntity.ok(data);
			}
			if (local.getUbigeo() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una ubigeo válido");
				return ResponseEntity.ok(data);
			}
			
			System.out.println(local.toString());
			Local registrado = service.registrar(local);
			
			
			if (registrado == null) {
				
				data.setOk(false);
				data.setMensaje("Hubo un error al intentar registrar el local");
				return ResponseEntity.ok(data);
			}
			data.setMensaje("Se registró correctamente el local");

		} catch (Exception e) {
			
			data.setOk(false);
			data.setMensaje("Sucedió un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);
	}

	@PutMapping("/actualizar/{id}")
	@Secured("ROLE_ADMIN")
	@Operation(summary = "Actualizar local")
	@ResponseBody
	@Transactional
	public ResponseEntity<ResponseApi<Local>> actualizar(@PathVariable("id") int idLocal, @RequestBody Local local) {
		ResponseApi<Local> data = new ResponseApi<>();
		try {

			local.setEstado(1);

			Optional<Local> existe = service.buscarPorId(local.getIdLocal());

			if (existe.isPresent()) {
				data.setOk(false);
				data.setMensaje("Local ya se encuentra registrado");
				return ResponseEntity.ok(data);
			}

			if (local.getNombre() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar un nombre válido");
				return ResponseEntity.ok(data);
			}

			if (local.getHora_inicio() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una hora inicio válido");
				return ResponseEntity.ok(data);
			}

			if (local.getHora_fin() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una hora final válido");
				return ResponseEntity.ok(data);
			}

			if (local.getDireccion() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una dirección válido");
				return ResponseEntity.ok(data);
			}
			if (local.getUbigeo() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar una ubigeo válido");
				return ResponseEntity.ok(data);
			}

			local.setIdLocal(idLocal);

			Local actualizado = service.actualizar(local);

			if (actualizado == null) {
				data.setOk(false);
				data.setMensaje("Hubo un error al intentar registrar el local");
				return ResponseEntity.ok(data);
			}

			data.setMensaje("Se actualizo correctamente el local");

		} catch (Exception e) {
			e.printStackTrace();
			data.setOk(false);
			data.setMensaje("Sucedió un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseBody
	@Operation(summary = "Eliminar local")
	@Secured("ROLE_ADMIN")
	@Transactional
	public ResponseEntity<HashMap<String, Object>> eliminar(@PathVariable int id) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		salida.put("objeto", null);
		salida.put("datos", new ArrayList<>());
		try {
			Optional<Local> optional = service.buscarPorId(id);
			if (optional.isPresent()) {
				Local obj = optional.get();
				obj.setEstado(0);
				Local eliminado = service.eliminar(obj);
				if (eliminado == null) {
					salida.put("ok", false);
					salida.put("mensaje", "No se pudo eliminar el local");
				} else {
					salida.put("ok", true);
					salida.put("mensaje", "Eliminación exitosa");
				}
			} else {
				salida.put("ok", false);
				salida.put("mensaje", "El ID no existe : " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("ok", false);
			salida.put("mensaje", "Error en la eliminación " + e.getMessage());
		}
		return ResponseEntity.ok(salida);
	}

}
