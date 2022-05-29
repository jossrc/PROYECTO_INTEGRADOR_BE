package com.postales.controller;

import com.postales.entity.Rol;
import com.postales.entity.Usuario;
import com.postales.service.UsuarioService;
import com.postales.util.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/cliente/registrar")
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
            usuario.setRol(rol); // Cliente

            Optional<Usuario> existe = service.buscarPorEmail(usuario.getEmail());

            if (existe.isPresent()) {
                data.setOk(false);
                data.setMensaje("Usuario ya se encuentra registrado");
                return ResponseEntity.ok(data);
            }

            Usuario registrado = service.registrarCliente(usuario);

            if (registrado == null) {
                data.setOk(false);
                data.setMensaje("Hubo un error al intentar registrar al cliente");
                return ResponseEntity.ok(data);
            }

            registrado.setPassword(null);
            data = new ResponseApi<>(true, "Se registró correctamente al cliente", registrado );

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);

    }

    @PostMapping("/empleado/registrar")
    @Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity<ResponseApi<Usuario>> registrarEmpleado(@RequestBody Usuario usuario) {
        ResponseApi<Usuario> data = new ResponseApi<>();
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(newPassword);

            usuario.setDisponible(true);
            usuario.setEstado(1);

            Optional<Usuario> existe = service.buscarPorEmail(usuario.getEmail());

            if (existe.isPresent()) {
                data.setOk(false);
                data.setMensaje("Correo electrónico ya se encuentra en uso");
                return ResponseEntity.ok(data);
            }

            if (usuario.getLocal() == null) {
                data.setOk(false);
                data.setMensaje("El empleado requiere de un local");
                return ResponseEntity.ok(data);
            }

            Usuario registrado = service.registrarCliente(usuario);

            if (registrado == null) {
                data.setOk(false);
                data.setMensaje("Hubo un error al intentar registrar al empleado");
                return ResponseEntity.ok(data);
            }

            registrado.setPassword(null);
            data = new ResponseApi<>(true, "Se registró correctamente al empleado", registrado );

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);

    }


}
