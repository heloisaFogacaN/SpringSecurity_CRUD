package com.crud.demo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    // Token é gerado em três sessões
    // Primeira: cabeçalho
    // Segunda: é isso aqui -> gerarToken(), colocar as informações do token
    // Terceira é usado o algoritimo e a senha

    //gerar senha

    private final SecretKey key;

    public JwtUtil(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = encoder.encode("senha123");
        this.key = Keys.hmacShaKeyFor(senha.getBytes());
    }

    public String gerarToken(UserDetails userDetails) {
        return Jwts.builder()
                .issuer("WEG") // é a "WEG" que gera esse token, exemplo de entidade no caso
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 300000))
                .signWith(key, Jwts.SIG.HS256) // tipo de algoritimo que vai ser utilizado para avaliar se o token que ele está recebendo é o certo
                .subject(userDetails.getUsername()) // esse sempre deve ser único, pois a partir dele iremos procurar o usuário
                .compact();
    }


    private Jws<Claims> validarToken(String token) {
        return  getParser().parseSignedClaims(token);
    }

//    private JwtParser getParser() {
//        return Jwts.parser().setSigningKey("senha123")
//                .build();
//    }

    private JwtParser getParser() {
        return Jwts.parser()
                .verifyWith(this.key)
                .build();
    }

    public String getUsername(String token) {
        return validarToken(token)
                .getPayload()
                .getSubject();
    }
}
