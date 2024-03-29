package com.crud.demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor

public class FiltroAutenticacao extends OncePerRequestFilter {
    private final CookieUtil cookieUtil = new CookieUtil();
    private final JwtUtil jwtUtil = new JwtUtil();
    private AutenticacaoService autenticacaoService;
    private SecurityContextRepository securityContextRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!rotaPublica(request)) {
            // Busca um cookie
            Cookie cookie;
            try {
                cookie = cookieUtil.getCookie(request, "JWT");
            } catch (Exception e) {
                response.setStatus(401);
                return;
            }
            // Valida o JWT
            String token = cookie.getValue();
            String username = jwtUtil.getUsername(token);

            // Criação do usuário autenticado
            UserDetails user = autenticacaoService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

            // Salvamento do usuário autenticadpp no Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext(); // Criando um contexto vazio(só porque o authentication deu certo)
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response); // Salva o contexto com o objeto autenticado para que toda vez que for fazer uma requisição o próprio já reconheça que há um salvo, mantém o usuário ativo

            // Renovação do JWT e do cookie
            Cookie cookieRenvado = cookieUtil.gerarCookieJwt(user);
            response.addCookie(cookieRenvado);
        }
        // Continuação da requisição
        filterChain.doFilter(request, response);
    }

    private boolean rotaPublica(HttpServletRequest request) {
        // toda rota com permitALl tem que estar aqui
        return request.getRequestURI().equals("/auth/login") && request.getMethod().equals(("POST"));
    }
}
