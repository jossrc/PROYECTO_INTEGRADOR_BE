package com.postales;

import com.postales.auth.custom.CustomAuthenticationEntryPoint;
import com.postales.auth.filter.JWTAuthenticationFilter;
import com.postales.auth.filter.JWTAuthorizationFilter;
import com.postales.auth.filter.SimpleCORSFilter;
import com.postales.auth.service.JWTService;
import com.postales.service.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JPAUserDetailsService usuarioDetailService;

    @Autowired
    private JWTService jwtService;


    @Autowired
    private SimpleCORSFilter corsFilter;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests().antMatchers(
                "/",
                        "/api/usuarios/cliente/registrar",
                        "/api/ubigeo/**",
                        "/api-docs/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-ui/index.html",
                        "/webjars/**" ,
                        "/swagger.json"
                ).permitAll()
                .anyRequest().authenticated()
                .and() //.addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .csrf()
                .disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(usuarioDetailService).passwordEncoder(passwordEncoder());
    }

}
