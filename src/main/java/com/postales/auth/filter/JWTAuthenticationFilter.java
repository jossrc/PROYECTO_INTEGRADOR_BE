package com.postales.auth.filter;

import com.google.gson.Gson;
import com.postales.auth.service.JWTService;
import com.postales.auth.service.JWTServiceImpl;
import com.postales.entity.Usuario;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        System.out.println("En el JWTAuthenticationFilter");
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("En el attemptAuthentication");
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

            try {
                Usuario usuario;
                String jsonString = request.getReader().lines().collect(Collectors.joining());
                System.out.println( "LOGIN " + jsonString );
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
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Error de autenticación: correo o contraseña incorrectas");
        body.put("error", failed.getMessage());

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Gson gson = new Gson();
        String token = jwtService.create(authResult);
        User user = (User) authResult.getPrincipal();

        response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("mensaje", String.format("Bienvenido %s, has iniciado sesión con éxito!", user.getUsername()));

        response.getWriter().write(gson.toJson(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

}
