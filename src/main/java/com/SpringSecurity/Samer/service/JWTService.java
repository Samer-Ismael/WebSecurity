package com.SpringSecurity.Samer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService {
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
                .signWith(getSecritkey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSecritkey() {
        /*
    You can add environment variable in your operating system
    with the name SECRET_KEY and the value of your secret key.
    If you want to store that key in a very secure place.
    I will not do that, but you can do it.

    Use System.getenv() to get the value of the key and use it :).
    String secretKey = System.getenv("SECRET_KEY");
     */

        byte[] key = Decoders.BASE64.decode("daa5ec3d56b653ffcde8470d7ba08d918dd5d27fd447542ff8dadb590f0cacff");
        return Keys.hmacShaKeyFor(key);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    public String getUserNameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecritkey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
