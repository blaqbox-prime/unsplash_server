package com.blaqboxdev.unsplash.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String secretKey = "bQsz63vmdfdlzstGWLa0c11Y/ixM8lRFCDsbGdi48y+6dj1C1wQYvlE/l3awW/p30XfNBdCjFMPzH4mbyyajzdzCUAQgJfdHmMT07MniuWVM4lcvf1DOOarJ5qOJ0Ax7yVe7UI7HdWq0KeGL3WyQ7PBqf5UBTHkLw+D5Px88kgoI8wF3zdg2MImg+kt7hX3T7glkvWpUbZVM/Lf7cz7EXOWUBWEzmOfdBsgtQj7WJRWIVODpoFjhHmVHJ7eBP7c61EoEnGcpNSR0O6WjgzjvZFqNvOc1Desq2JO+IEgCzb4PTAyYEhS0ScR1mRo7h3M5IPv563AEKfpa20oXTYgAolYmCgGBcnOIKIT0caAcljU=";
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
    return exractExpiration(token).before(new Date());
    }

    private Date exractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
