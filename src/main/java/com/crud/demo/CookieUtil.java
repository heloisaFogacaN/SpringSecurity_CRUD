package com.crud.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class CookieUtil {
    public Cookie gerarCookieJwt(UserDetails userDetails){
                String token = new JwtUtil().gerarToken(userDetails);
                Cookie cookie = new Cookie("JWT", token);
                cookie.setPath("/"); // ele va utilizar ese cookie a partir desse path, a partir desse caminho
                cookie.setMaxAge(300); // tempo de vida do cookie
                return cookie;
    }

    public Cookie getCookie(HttpServletRequest request, String name){
        return WebUtils.getCookie(request, name); //
    }
}
