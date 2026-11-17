package br.com.fiap3es.springbootproject.config.security.util;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtils {
    public static String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}