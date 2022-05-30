package com.postales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.postales.entity.Vehiculo;
import com.postales.service.VehiculoService;

@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {

	@Autowired
	private VehiculoService repositoryService;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Vehiculo>> listarVehiculos() {
		List<Vehiculo> listado = repositoryService.listarVehiculos();
		return ResponseEntity.ok(listado);
	}

	@PostMapping
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
	}

	@PutMapping
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
	}

	@DeleteMapping("/{id}")
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
	}

}
