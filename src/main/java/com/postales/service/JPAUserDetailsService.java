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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String correo = username.trim();

        Optional<Usuario> data = repository.findByEmail(correo);

        if(data.isEmpty()) {
            throw new UsernameNotFoundException("Error correo o contraseña incorrectos");
        }

        Usuario usuario = data.get();

        // Creamos lista de autoridades
        List<GrantedAuthority> authorities = new ArrayList<>();

        /* El usuario puede tener muchos roles
        for(Rol role: usuario.getRoles()) {
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
         */

        authorities.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));

        if(authorities.isEmpty()) {
            throw new UsernameNotFoundException("Error en el Login: usuario con el correo '" + correo + "' no tiene roles asignados!");
        }

        return new User(usuario.getEmail(), usuario.getPassword(), usuario.isDisponible(), true, true, true, authorities);

    }
}
