package com.postales.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.postales.util.AppSettings;
import com.postales.util.ResponseApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.postales.entity.Cotizacion;
import com.postales.entity.Envio;
import com.postales.entity.Paquete;
import com.postales.entity.Usuario;
import com.postales.entity.Vehiculo;
import com.postales.entity.dto.CotizacionDTO;
import com.postales.entity.dto.EnvioDTO;
import com.postales.service.CotizacionService;
import com.postales.service.EnvioService;
import com.postales.service.UsuarioService;

@Controller
@RequestMapping("/api/envios")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class EnvioController {
	

	private Logger log = LoggerFactory.getLogger(EnvioController.class);
	
	@Autowired
	private EnvioService serv;
	
	@Autowired
	private UsuarioService usuServ;
	
	@Autowired
	private CotizacionService cotizacionServ;
	
	
	@GetMapping("/listaEnvios")
	@Secured("ROLE_ADMIN")
	@Operation(summary = "Listar todos los envíos")
	@ResponseBody
	public ResponseEntity<List<Envio>> listEnvios() {
		
		List<Envio> lista = serv.listarEnvio();
		return ResponseEntity.ok(lista);
		
	}
	
	@GetMapping("/listaEnviosPorDia")
	@Secured("ROLE_ADMIN")
	@Operation(summary = "Listar todos los envíos por dia")
	@ResponseBody
	public ResponseEntity<List<Envio>> listEnviosPorDia() {
		
		List<Envio> lista = serv.listarEnviosPorDia();
		return ResponseEntity.ok(lista);
		
	}
	@GetMapping("/listaEnviosPorUsuarioDia")
	@Secured("ROLE_CLIENTE")
	@Operation(summary = "Listar todos los envíos por dia")
	@ResponseBody
	public ResponseEntity<List<Envio>> listEnviosPorUsuarioDia() {
    	int idUsu = usuServ.obtenerIdUsuarioPeticion();

		List<Envio> lista = serv.listaEnvioUsu(idUsu);
		return ResponseEntity.ok(lista);
		
	}
	/*@GetMapping("/listaEnvioPorIdLike/{idUsu}")
	@ResponseBody
	public ResponseEntity<List<Envio>> listaEnvioPorIdLike(@PathVariable("idUsu") int idUsu){
		log.info(" ==> listaEnvioPorIdLike ==> idUsu : " + idUsu);
		
		List<Envio> lista = null;
		try {
			if(idUsu == 0) {
				lista = serv.listarEnvio();
			} else {
				lista = serv.listaPorIdLike(idUsu);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(lista);
	}*/
	
	
	
	@GetMapping("/listaEnviosUsu/{id}")
	@Operation(summary = "Listar envíos del usuario registrado")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listEnviosUsu(@RequestParam(value="idUsu", required=false, defaultValue="0") int idUsu) {
		
		Map<String, Object> salida = new HashMap<String, Object>();
		
		try {
			System.out.println("Este es el ID del usuario : " + usuServ.obtenerIdUsuarioPeticion());
			List<Envio> lista = serv.listaEnvioUsu(usuServ.obtenerIdUsuarioPeticion());
			if(CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existe elementos para la consulta");
			} else {
				salida.put("lista", lista);
				salida.put("mensaje", "Se tiene " + lista.size() + " elementos");
			}
		} catch(Exception e) {
			salida.put("mensaje", "Error : " + e.getMessage());
		}
		
		return ResponseEntity.ok(salida);
		
	}
	
	@PostMapping("/registrar")
	@Secured({"ROLE_ADMIN" , "ROLE_CLIENTE", "ROLE_OPERADOR"})
    @Transactional
    public ResponseEntity<ResponseApi<Envio>> registrarEnvio(@RequestBody EnvioDTO envioDTO) {
        ResponseApi<Envio> data = new ResponseApi<>();
        try {
        	
        	Envio envio = new Envio();
        	
        	System.out.println(envioDTO.toString());
        	
        	envio.setAdjunto(null);
        	envio.setEstado(1);
        	
            if (envioDTO.getFechaInicio() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar una fecha de inicio");
                return ResponseEntity.ok(data);
            }
            
            if (envioDTO.getFechaEntrega() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar una fecha de entrega");
                return ResponseEntity.ok(data);
            }
            
            if (envioDTO.getFechaCreacion() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar una fecha de creacion");
                return ResponseEntity.ok(data);
            }
            
            if (envioDTO.getIdCotizacion() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere una cotizacion");
                return ResponseEntity.ok(data);
            }
            
            if (envioDTO.getIdUsuario() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere un usuario");
                return ResponseEntity.ok(data);
            }
            
            if (envioDTO.getIdVehiculo() == 0) {
                data.setOk(false);
                data.setMensaje("Se requiere un vehiculo");
                return ResponseEntity.ok(data);
            }
            
            Cotizacion objCotizacion = new Cotizacion();
            objCotizacion.setIdCotizacion(envioDTO.getIdCotizacion());
            
            Usuario objUsuario = new Usuario();
            objUsuario.setIdUsuario(envioDTO.getIdUsuario());
            
            Vehiculo objVehiculo = new Vehiculo();
            objVehiculo.setIdVehiculo(envioDTO.getIdVehiculo());
            
            envio.setCotizacion(objCotizacion);
            envio.setUsuario(objUsuario);
            envio.setVehiculo(objVehiculo);
            envio.setFechaCreacion(envioDTO.getFechaCreacion());
            envio.setFechaInicio(envioDTO.getFechaInicio());
            envio.setFechaEntrega(envioDTO.getFechaEntrega());
            
            System.out.println(envio.toString());
            
            Envio registrado = serv.registrar(envio);

            if (registrado == null) {
                data.setOk(false);
                data.setMensaje("Hubo un error al intentar generar el envio");
                return ResponseEntity.ok(data);
            }
            
            Optional<Cotizacion> cotizacion = cotizacionServ.buscarPorId(envioDTO.getIdCotizacion());
            
            if(cotizacion.isPresent()) {
            	Cotizacion objCoti = cotizacion.get();
            	objCoti.setEstado(2);
            	cotizacionServ.actualizar(objCoti);
            }

            data.setOk(true);
            data.setMensaje("Se genero el envio satisfactoriamente");
            

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }
        return ResponseEntity.ok(data);

    }
	
}
