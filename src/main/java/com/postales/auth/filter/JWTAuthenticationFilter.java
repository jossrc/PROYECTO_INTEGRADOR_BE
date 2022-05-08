package com.postales.auth.filter;

import com.google.gson.Gson;
import com.postales.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Gson gson  = new Gson();
        // Obtiene por el form-data
        String username = obtainUsername(request); // username
        String password = obtainPassword(request);

        if (username == null || password == null) {
            username = "";
            password = "";
        }

        if (username.isEmpty() && password.isEmpty()) {
            // Busca por el body
            Usuario usuario;
            try {
                String jsonString = request.getReader().lines().collect(Collectors.joining());
                usuario = gson.fromJson( jsonString, Usuario.class);
                username = usuario.getEmail();
                password = usuario.getPassword();

                logger.info("Username desde request InputStream (raw) : " + username);
                logger.info("Password desde request InputStream (raw) : " + password);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            logger.info("Username desde request parameter (form-data) : " + username);
            logger.info("Password desde request parameter (form-data) : " + password);
        }

        username = username.trim();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Gson gson = new Gson();
        String username = ((User) authResult.getPrincipal()).getUsername();

        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", gson.toJson(roles));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration( new Date(System.currentTimeMillis() + 14000000L) )
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("mensaje", String.format("Bienvenido %s, has iniciado sesión con éxito!", username));

        response.getWriter().write(gson.toJson(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

}
