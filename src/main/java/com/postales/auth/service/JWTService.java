package com.postales.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public interface JWTService {

    public String create(Authentication auth) throws IOException;
    public boolean validate(String token);
    public Claims getClaims(String token);
    public String getUsernameByToken(String token);
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
    public String resolve(String token);
    public int getUsuarioId(String token);

}
