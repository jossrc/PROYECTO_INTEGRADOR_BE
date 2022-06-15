package com.postales.auth.filter;

import com.postales.auth.service.JWTService;
import com.postales.auth.service.JWTServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        System.out.println("En JWTAuthorizationFilter");
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Paso?");
        System.out.println("En doFilterInternal");
        String token = request.getHeader(JWTServiceImpl.HEADER_STRING);
        if (!requiresAuthentication(token)) {
            chain.doFilter(request, response);
            return;
        }

        boolean isValidToken = jwtService.validate(token);
        UsernamePasswordAuthenticationToken authentication = null;
        System.out.println("Es token valido? : " + isValidToken);
        if (isValidToken) {
            String username = jwtService.getUsernameByToken(token); // Email
            authentication = new UsernamePasswordAuthenticationToken(username, null, jwtService.getRoles(token));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    protected boolean requiresAuthentication(String token) {
        return token != null && token.startsWith(JWTServiceImpl.TOKEN_PREFIX);
    }

}
