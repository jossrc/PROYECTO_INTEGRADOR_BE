package com.postales.controller;

import com.postales.entity.Cotizacion;
import com.postales.entity.Envio;
import com.postales.entity.Paquete;
import com.postales.entity.Rol;
import com.postales.entity.Usuario;
import com.postales.service.CotizacionService;
import com.postales.service.EnvioService;
import com.postales.service.PaqueteService;
import com.postales.service.UsuarioService;
import com.postales.util.AppSettings;
import com.postales.util.ResponseApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	@Autowired
	private CotizacionService serviceCotizacion;
	@Autowired
	private PaqueteService servicePaquete;
	@Autowired
	private EnvioService serviceEnvio;
	
	@PostMapping("/cliente/registrar")
	@ResponseBody
	@Operation(summary = "Registro de usuario")
	@Transactional
	public ResponseEntity<ResponseApi<Usuario>> registrarCliente(@RequestBody Usuario usuario) {
		ResponseApi<Usuario> data = new ResponseApi<>();
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String newPassword = passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(newPassword);

			usuario.setDisponible(true);
			usuario.setEstado(1);
			Rol rol = new Rol();
			rol.setId(3);
			rol.setNombre("ROLE_CLIENTE");
			rol.setDescripcion(null);
			usuario.setRol(rol); // Cliente

			Optional<Usuario> existe = service.buscarPorEmail(usuario.getEmail());

			if (existe.isPresent()) {
				data.setOk(false);
				data.setMensaje("Usuario ya se encuentra registrado");
				return ResponseEntity.ok(data);
			}

			if (usuario.getUbigeo() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar un ubigeo v??lido");
				return ResponseEntity.ok(data);
			}

			if (usuario.getLocal() != null) {
				data.setOk(false);
				data.setMensaje("Cliente no puede estar relacionado a un local");
				return ResponseEntity.ok(data);
			}

			Usuario registrado = service.registrarCliente(usuario);

			if (registrado == null) {
				data.setOk(false);
				data.setMensaje("Hubo un error al intentar registrar al cliente");
				return ResponseEntity.ok(data);
			}

			Usuario enviar = new Usuario(registrado);
			data = new ResponseApi<>(true, "Se registr?? correctamente al cliente", enviar);

		} catch (Exception e) {
			e.printStackTrace();
			data.setOk(false);
			data.setMensaje("Sucedi?? un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);

	}

	@PostMapping("/empleado/registrar")
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@Operation(summary = "Registrar empleado", security = @SecurityRequirement(name = "bearerAuth"))
	@Transactional
	public ResponseEntity<ResponseApi<Usuario>> registrarEmpleado(@RequestBody Usuario usuario) {
		ResponseApi<Usuario> data = new ResponseApi<>();
		try {
			System.out.println(usuario.getPassword());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String newPassword = passwordEncoder.encode(usuario.getPassword());
			System.out.println(newPassword);
			usuario.setPassword(newPassword);

			usuario.setDisponible(true);
			usuario.setEstado(1);

			Optional<Usuario> existe = service.buscarPorEmail(usuario.getEmail());

			if (existe.isPresent()) {
				data.setOk(false);
				data.setMensaje("Correo electr??nico ya se encuentra en uso");
				return ResponseEntity.ok(data);
			}

			if (usuario.getUbigeo() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar un ubigeo v??lido");
				return ResponseEntity.ok(data);
			}

			if (usuario.getRol() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar un rol v??lido");
				return ResponseEntity.ok(data);
			}

			if (usuario.getRol().getId() == 2) {
				if (usuario.getLocal() == null) {
					data.setOk(false);
					data.setMensaje("El empleado requiere de un local");
					return ResponseEntity.ok(data);
				}
			}

			if (usuario.getRol().getId() == 3) {
				if (usuario.getLocal() != null) {
					data.setOk(false);
					data.setMensaje("El cliente no puede estar relacionado a un local");
					return ResponseEntity.ok(data);
				}
			}

			Usuario registrado = service.registrarEmpleado(usuario);

			if (registrado == null) {
				data.setOk(false);
				data.setMensaje("Hubo un error al intentar registrar al empleado");
				return ResponseEntity.ok(data);
			}
			Usuario enviar = new Usuario(registrado);
			data = new ResponseApi<>(true, "Se registr?? correctamente al empleado", enviar);

		} catch (Exception e) {
			e.printStackTrace();
			data.setOk(false);
			data.setMensaje("Sucedi?? un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);

	}

	@PutMapping("/empleado/actualizar/{id}")
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@Operation(summary = "Actualizar empleado", security = @SecurityRequirement(name = "bearerAuth"))
	@Transactional
	public ResponseEntity<ResponseApi<Usuario>> actualizarEmpleado(@PathVariable("id") int empleadoId,
			@RequestBody Usuario usuario) {
		ResponseApi<Usuario> data = new ResponseApi<>();

		try {
			Optional<Usuario> encontrado = service.obtenerEmpleado(empleadoId);

			if (encontrado.isEmpty()) {
				data.setOk(false);
				data.setMensaje("Empleado no existe o no est?? disponible");
				return ResponseEntity.ok(data);
			}

			Usuario empleado = encontrado.get();

			if (usuario.getPassword() == null) {
				usuario.setPassword(empleado.getPassword());
			} else {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String newPassword = passwordEncoder.encode(usuario.getPassword());
				usuario.setPassword(newPassword);
			}

			usuario.setDisponible(true);
			usuario.setEstado(1);

			Optional<Usuario> existe = service.buscarPorEmail(usuario.getEmail());

			if (existe.isPresent()) {
				if (existe.get().getIdUsuario() != empleadoId) {
					data.setOk(false);
					data.setMensaje("Correo electr??nico ya se encuentra en uso");
					return ResponseEntity.ok(data);
				}
			}

			if (usuario.getUbigeo() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar un ubigeo v??lido");
				return ResponseEntity.ok(data);
			}

			if (usuario.getRol() == null) {
				data.setOk(false);
				data.setMensaje("Se requiere ingresar un rol v??lido");
				return ResponseEntity.ok(data);
			}

			if (usuario.getRol().getId() == 2) {
				if (usuario.getLocal() == null) {
					data.setOk(false);
					data.setMensaje("El empleado requiere de un local");
					return ResponseEntity.ok(data);
				}
			}

			if (usuario.getRol().getId() == 3) {
				if (usuario.getLocal() != null) {
					data.setOk(false);
					data.setMensaje("El cliente no puede estar relacionado a un local");
					return ResponseEntity.ok(data);
				}
			}

			usuario.setIdUsuario(empleadoId);
			Usuario actualizado = service.actualizarEmpleado(usuario);

			if (actualizado == null) {
				data.setOk(false);
				data.setMensaje("Hubo un error al intentar actualizar al empleado");
				return ResponseEntity.ok(data);
			}
			Usuario enviar = new Usuario(actualizado);
			data = new ResponseApi<>(true, "Se actualiz?? correctamente al empleado", enviar);

		} catch (Exception e) {
			e.printStackTrace();
			data.setOk(false);
			data.setMensaje("Sucedi?? un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);
	}

	@DeleteMapping("/empleado/eliminar/{id}")
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@Operation(summary = "Eliminar empleado", security = @SecurityRequirement(name = "bearerAuth"))
	@Transactional
	public ResponseEntity<HashMap<String, Object>> eliminarEmpleado(@PathVariable int id) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		salida.put("objeto", null);
		salida.put("datos", new ArrayList<>());
		try {
			Optional<Usuario> optional = service.obtenerEmpleado(id);
			if (optional.isPresent()) {
				Usuario empleado = optional.get();
				empleado.setEstado(0);
				empleado.setDisponible(false);
				Usuario eliminado = service.actualizarEmpleado(empleado);
				if (eliminado != null) {
					salida.put("ok", true);
					salida.put("mensaje", "No se pudo eliminar al empleado");
				} else {
					salida.put("ok", false);
					salida.put("mensaje", "No se pudo eliminar al empleado");
				}

			} else {
				salida.put("ok", false);
				salida.put("mensaje", "El empleado con ID " + id + " no existe");
			}

		} catch (Exception e) {
			salida.put("ok", false);
			salida.put("mensaje", "Sucedi?? un error inesperado consulte con su administrador");
		}

		return ResponseEntity.ok(salida);
	}

	@GetMapping
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@Operation(summary = "Listar usuarios", security = @SecurityRequirement(name = "bearerAuth"))
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseApi<Usuario>> listarUsuarios() {
		ResponseApi<Usuario> data = new ResponseApi<>();

		try {
			List<Usuario> usuarios = service.listarUsuarios();

			data.setOk(true);

			if (usuarios.size() <= 0) {
				data.setMensaje("No se encontraron resultados");
			} else {
				if (usuarios.size() == 1) {
					data.setMensaje("Se encontr?? un registro");
				} else {
					data.setMensaje("Se encontraron " + usuarios.size() + " registros");
				}
			}

			List<Usuario> usuariosNoPassword = usuarios.stream().map(u -> new Usuario(u)).collect(Collectors.toList());
			data.setDatos(usuariosNoPassword);

		} catch (Exception e) {
			e.printStackTrace();
			data.setOk(false);
			data.setMensaje("Sucedi?? un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);
	}

	@GetMapping("/empleado/listar")
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@Operation(summary = "Listar empleados", security = @SecurityRequirement(name = "bearerAuth"))
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseApi<Usuario>> listarEmpleados() {
		ResponseApi<Usuario> data = new ResponseApi<>();
		try {
			List<Usuario> usuarios = service.listarEmpleados();

			// int usuarioId = service.obtenerIdUsuarioPeticion();
			// System.out.println("Id del usuario que realiza es : " + usuarioId);

			data.setOk(true);

			if (usuarios.size() <= 0) {
				data.setMensaje("No se encontraron resultados");
			} else {
				if (usuarios.size() == 1) {
					data.setMensaje("Se encontr?? un registro");
				} else {
					data.setMensaje("Se encontraron " + usuarios.size() + " registros");
				}
			}

			List<Usuario> usuariosNoPassword = usuarios.stream().map(u -> new Usuario(u)).collect(Collectors.toList());
			data.setDatos(usuariosNoPassword);

		} catch (Exception e) {
			e.printStackTrace();
			data.setOk(false);
			data.setMensaje("Sucedi?? un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);
	}

	@GetMapping("/cliente/listar")
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@Operation(summary = "Listar clientes", security = @SecurityRequirement(name = "bearerAuth"))
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseApi<Usuario>> listarClientes() {
		ResponseApi<Usuario> data = new ResponseApi<>();
		try {
			List<Usuario> usuarios = service.listarClientes();

			data.setOk(true);

			if (usuarios.size() <= 0) {
				data.setMensaje("No se encontraron resultados");
			} else {
				if (usuarios.size() == 1) {
					data.setMensaje("Se encontr?? un registro");
				} else {
					data.setMensaje("Se encontraron " + usuarios.size() + " registros");
				}
			}

			List<Usuario> usuariosNoPassword = usuarios.stream().map(u -> new Usuario(u)).collect(Collectors.toList());
			data.setDatos(usuariosNoPassword);

		} catch (Exception e) {
			e.printStackTrace();
			data.setOk(false);
			data.setMensaje("Sucedi?? un error inesperado consulte con su administrador");
			data.setError(e.getMessage());
		}

		return ResponseEntity.ok(data);
	}

	@GetMapping("/mostrarPerfil")
	@Secured({ "ROLE_ADMIN", "ROLE_CLIENTE", "ROLE_OPERADOR" })
	@ResponseBody
	@Operation(summary = "Mostrar Perfil Usuario")
	@Transactional(readOnly = true)
	public ResponseEntity<HashMap<String, Object>> mostrarPerfil() {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		salida.put("objeto", null);
		salida.put("datos", new ArrayList<>());
		try {
			int idUsu = service.obtenerIdUsuarioPeticion();

			List<Usuario> lista = service.obtenerEmpleadoPerfilRol(idUsu);
			List<Usuario> cantidad = service.obtenerCantidadUsuarios();
			List<Cotizacion> cantidadCotizaciones = serviceCotizacion.listarCotizaciones();
			List<Paquete> cantidadPaquetes = servicePaquete.listarPaquetes();
			List<Envio> cantidadEnvios = serviceEnvio.listarEnvios();
			//
			List<Cotizacion> cantidadCotizacionesAprobadas = serviceCotizacion.listarCotizacionesAprobado(idUsu);
			List<Cotizacion> cantidadCotizacionesPendientes = serviceCotizacion.listarCotizacionesPendiente(idUsu);
			List<Cotizacion> cantidadCotizacionesRechazados = serviceCotizacion.listarCotizacionesRechazado(idUsu);

			List<Envio> cantidadEnviosEntregados = serviceEnvio.listarEnviosEnviando(idUsu);

			
			for (Usuario us : lista) {
				salida.put("nombreCompleto", us.getNombre() + " " + us.getApellido());
				salida.put("rolusuario", us.getRol().getId());
			}
			salida.put("cantidadUsuarios", cantidad.size());
			salida.put("cantidadCotizaciones", cantidadCotizaciones.size());
			salida.put("cantidadPaquetes", cantidadPaquetes.size());
			salida.put("cantidadEnvios", cantidadEnvios.size());
			//
			salida.put("cantidadCotizacionesAprobadas", cantidadCotizacionesAprobadas.size());
			salida.put("cantidadCotizacionesPendientes", cantidadCotizacionesPendientes.size());
			salida.put("cantidadCotizacionesRechazados", cantidadCotizacionesRechazados.size());
			salida.put("cantidadEnviosEntregados", cantidadEnviosEntregados.size());
			
			salida.put("mensaje", "Se encontr?? perfil a mostrar");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			salida.put("ok", false);
			salida.put("mensaje", "Sucedi?? un error inesperado consulte con su administrador");
		}

		return ResponseEntity.ok(salida);
	}

}
