package com.crud.demo.controller;

import com.crud.demo.CookieUtil;
import com.crud.demo.JwtUtil;
import com.crud.demo.model.entity.UsuarioLogin;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuteticacaoController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil = new JwtUtil();
    private final CookieUtil cookieUtil = new CookieUtil();

    @PostMapping("/auth/login")
    public ResponseEntity<String> authenticate(@RequestBody UsuarioLogin usuario, HttpServletRequest request, HttpServletResponse response) {

        try {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()); // Ele sempre recebe o username  e o password para criar o token deste usuário
            Authentication authentication = authenticationManager.authenticate(authenticationToken); // certifica se o usuário já está autenticado ou não

            UserDetails user = (UserDetails) authentication.getPrincipal(); // username
            Cookie cookie = cookieUtil.gerarCookieJwt(user);
            response.addCookie(cookie);

            return ResponseEntity.ok("Autentificação bem-sucedida");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação!");

        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Cookie cookie = cookieUtil.getCookie(request, "JWT");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        } catch (Exception e) {
            response.setStatus(401);
        }


    }
}
