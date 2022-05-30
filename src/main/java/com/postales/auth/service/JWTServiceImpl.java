package com.postales.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.postales.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTServiceImpl implements JWTService{

    private static final String SECRET_KEY_STRING = "DT75biyRRdK7iqsjhByGaGCRwzGam3oJkT9ve2tRdaT3ud4";

    public static final SecretKey SECRET_KEY = new SecretKeySpec(SECRET_KEY_STRING.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    public static final long EXPIRATION_DATE = 14000000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Override
    public String create(Authentication auth) throws IOException {
        Gson gson = new Gson();
        String username = ((User) auth.getPrincipal()).getUsername();

        // SecretKey -> Keys.secretKeyFor(SignatureAlgorithm.HS512);

        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", gson.toJson(roles));

        // Retorna Token
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration( new Date(System.currentTimeMillis() + EXPIRATION_DATE) )
                .compact();
    }

    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;

        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(resolve(token))
                .getBody();
    }

    @Override
    public String getUsernameByToken(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        return Arrays.asList(
                new ObjectMapper()
                        .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                        .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class)
        );
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }

        return null;
    }
}
