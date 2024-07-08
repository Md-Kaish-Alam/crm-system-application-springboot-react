package com.nuwaish.crm_system_backend_springboot.securityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.nuwaish.crm_system_backend_springboot.securityConfig.JwtConstant.SECRET_KEY;

public class JwtProvider {

    @Autowired
    private static TokenBlacklistService tokenBlacklistService;

    static SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        @SuppressWarnings("deprecation")
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();

        return jwt;
    }

    public static boolean validateToken(String token) {
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority: authorities) {
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }

    @SuppressWarnings("deprecation")
    public static String getEmailFromJwtToken(String jwt) {
        // Assuming 'Bearer ' is removed from the token
        jwt = jwt.substring(7);

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String email = String.valueOf(claims.get("email"));

            return email;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

}
