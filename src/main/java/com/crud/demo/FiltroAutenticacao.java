package com.crud.demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FiltroAutenticacao extends OncePerRequestFilter {
    CookieUtil cookieUtil = new CookieUtil();
    private JwtUtil jwtUtil = new JwtUtil();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = cookieUtil.getCookie(request, "JWT");
        String token = cookie.getValue();
        jwtUtil.validarToken(token);
        filterChain.doFilter(request, response);
    }
}
