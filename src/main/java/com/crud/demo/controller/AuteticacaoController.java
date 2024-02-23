package com.crud.demo.controller;

import com.crud.demo.model.entity.UsuarioLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuteticacaoController {

    private final AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UsuarioLogin usuario, HttpServletRequest request, HttpServletResponse response){
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()); // Ele sempre recebe o username  e o password para criar o token deste usuário
            Authentication authentication = authenticationManager.authenticate(authenticationToken); // certifica se o usuário já está autenticado ou não

            // Sem o contexto é necessário em toda requisição passar o username e o password do usuário, o que é um trbalho desnecessário

            SecurityContext context = SecurityContextHolder.createEmptyContext(); // Criando um contexto vazio(só porque o authentication deu certo)
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response); // Salva o contexto com o objeto autenticado para que toda vez que for fazer uma requisição o próprio já reconheça que há um salvo, mantém o usuário ativo
//            SecurityContextHolder.setContext(context);

            return ResponseEntity.ok("Autentificação bem-sucedida");
        } catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação!");

        }
    }
}
