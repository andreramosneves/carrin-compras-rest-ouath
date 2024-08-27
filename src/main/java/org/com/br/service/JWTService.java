/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.com.br.repositories.LoginRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
public class JWTService implements UserDetailsService{

    private final LoginRepository repository;

    public JWTService(LoginRepository repository) {
        this.repository = repository;
    }
    
    
    //admin!2024*
    private static final String SECRET_KEY = "213077231d4efd26060faa67abdfd1b1728ef81533771bfc7f8dabd85e84259f";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWTService.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDatails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDatails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    

    public String generateToken(UserDetails userDatails) {
        /*
        OAuth2AccessToken token = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                SECRET_KEY,
                Instant.now(),
                Instant.now().plus(1, ChronoUnit.HOURS));
        OAuth2AuthenticatedPrincipal auth2 = new DefaultOAuth2User(SECRET_KEY, attributes, authorities);
        
        //return token.getV();
        */
        return generateToken(new HashMap<>(), userDatails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return repository.findByEmail(username)
                    .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        
    }
}
