package com.crud.demo;

import jakarta.servlet.http.Cookie;
import org.springframework.security.core.userdetails.UserDetails;

public class CookieUtil {
    public Cookie gerarCookie(UserDetails userDetails){
                String token = new JwtUtil().gerarToken(userDetails);
                Cookie cookie = new Cookie("JWT", token);
                cookie.setPath("/"); // ele va utilizar ese cookie a partir desse path, a partir desse caminho
                cookie.setMaxAge(300); // tempo de vida do cookie
                return cookie;
    }
}
