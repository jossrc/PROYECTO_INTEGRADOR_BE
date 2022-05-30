package com.postales.controller;

import com.postales.entity.Rol;
import com.postales.entity.Usuario;

import com.postales.service.UsuarioService;
import com.postales.util.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                data.setMensaje("Se requiere ingresar un ubigeo válido");
                return ResponseEntity.ok(data);
            }

            if (usuario.getLocal() != null ) {
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
            data = new ResponseApi<>(true, "Se registró correctamente al cliente", enviar );

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
                data.setMensaje("Correo electrónico ya se encuentra en uso");
                return ResponseEntity.ok(data);
            }

            if (usuario.getUbigeo() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un ubigeo válido");
                return ResponseEntity.ok(data);
            }

            if (usuario.getRol() == null) {
                data.setOk(false);
                data.setMensaje("Se requiere ingresar un rol válido");
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
            data = new ResponseApi<>(true, "Se registró correctamente al empleado", enviar );

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);

    }

    @GetMapping
    @Secured("ROLE_ADMIN")
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
                    data.setMensaje("Se encontró un registro");
                } else {
                    data.setMensaje("Se encontraron " + usuarios.size() + " registros");
                }
            }

            List<Usuario> usuariosNoPassword = usuarios.stream().map( u -> new Usuario(u)).collect(Collectors.toList());
            data.setDatos(usuariosNoPassword);

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);
    }

    @GetMapping("/empleado/listar")
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseApi<Usuario>> listarEmpleados() {
        ResponseApi<Usuario> data = new ResponseApi<>();
        try {
            List<Usuario> usuarios = service.listarEmpleados();

            data.setOk(true);

            if (usuarios.size() <= 0) {
                data.setMensaje("No se encontraron resultados");
            } else {
                if (usuarios.size() == 1) {
                    data.setMensaje("Se encontró un registro");
                } else {
                    data.setMensaje("Se encontraron " + usuarios.size() + " registros");
                }
            }

            List<Usuario> usuariosNoPassword = usuarios.stream().map( u -> new Usuario(u)).collect(Collectors.toList());
            data.setDatos(usuariosNoPassword);

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);
    }

    @GetMapping("/cliente/listar")
    @Secured("ROLE_ADMIN")
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
                    data.setMensaje("Se encontró un registro");
                } else {
                    data.setMensaje("Se encontraron " + usuarios.size() + " registros");
                }
            }

            List<Usuario> usuariosNoPassword = usuarios.stream().map( u -> new Usuario(u)).collect(Collectors.toList());
            data.setDatos(usuariosNoPassword);

        } catch (Exception e) {
            e.printStackTrace();
            data.setOk(false);
            data.setMensaje("Sucedió un error inesperado consulte con su administrador");
            data.setError(e.getMessage());
        }

        return ResponseEntity.ok(data);
    }


}
