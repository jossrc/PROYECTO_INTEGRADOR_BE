package com.postales.service;

import com.postales.entity.Usuario;
import com.postales.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO: Obtener desde la base de datos y verificar que el model sea el mismo
        // TODO: El registrar debe guardar la password usando bcrypt

        String correo = username.trim();

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jose");
        usuario.setApellido("Robles");
        usuario.setEmail("jose@gmail.com"); // username
        usuario.setPassword("$2a$12$/BLWh.HU/seSMsFM7tJTQOYf1X41lMlrGpsQ7rMblPpUPv37tYxwO"); // 123456
        usuario.setDisponible(true);

        // Creamos lista de autoridades
        List<GrantedAuthority> authorities = new ArrayList<>();

        /* El usuario puede tener muchos roles
        for(Rol role: usuario.getRoles()) {
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
         */

        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        if(authorities.isEmpty()) {
            throw new UsernameNotFoundException("Error en el Login: usuario con el correo '" + correo + "' no tiene roles asignados!");
        }

        return new User(usuario.getEmail(), usuario.getPassword(), usuario.isDisponible(), true, true, true, authorities);

    }
}
