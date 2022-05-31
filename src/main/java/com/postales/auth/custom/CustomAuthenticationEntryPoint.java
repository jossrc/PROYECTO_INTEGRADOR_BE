package com.postales.auth.custom;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        System.out.println("Acceso denegado");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        result.put("timestamp", new Date());
        result.put("status", 403);
        result.put("error", "Forbidden");
        result.put("message", "Acceso denegado");
        result.put("path", request.getRequestURI());

        response.getWriter().write( new Gson().toJson(result) );
    }
}
