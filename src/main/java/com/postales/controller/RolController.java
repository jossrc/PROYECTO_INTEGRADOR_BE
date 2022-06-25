package com.postales.controller;

import com.postales.entity.Rol;
import com.postales.service.RolService;
import com.postales.util.AppSettings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
@Controller
@RequestMapping("/api/roles")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class RolController {

    @Autowired
    private RolService service;

    @GetMapping
    @ResponseBody
    @Operation(summary = "Listar roles")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Rol>> listadoRoles(){
        List<Rol> lista = service.listar();
        return ResponseEntity.ok(lista);
    }


}
