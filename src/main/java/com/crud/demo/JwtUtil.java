package com.crud.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

    public JwtUtil() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = encoder.encode("senha123");
    }

    public String gerarToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256("senha123");
        return JWT.create()
                .withIssuer("WEG") // é a "WEG" que gera esse token, exemplo de entidade no caso
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(userDetails.getUsername()) // esse sempre deve ser único, pois a partir dele iremos procurar o usuário
                .sign(algorithm);

    }


//    private Jws<Claims> validarToken(String token) {
//        return  getParser().parseSignedClaims(token);
//    }

//    private JwtParser getParser() {
//        return Jwts.parser().setSigningKey("senha123")
//                .build();
//    }

//    private JwtParser getParser() {
//        return Jwts.parser()
//                .verifyWith(this.key)
//                .build();
//    }

    public String getUsername(String token) {
        return JWT.decode(token).getSubject();
    }
}
