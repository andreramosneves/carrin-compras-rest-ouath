/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import org.com.br.service.JWTService;
import org.com.br.bo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author andre
 */
@Component
public class JwtAuthenticatorFilter extends OncePerRequestFilter{

    @Autowired
    private JWTService jwtService;

    /*In video, 2 classes were utilized, but, this don't necessary create this class. Sprin Boot controled the in your context  */
    //@Autowired
    //private UserDetailsService userDetailsService;
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        
        if(authHeader == null ||!authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
        }else{
            jwt = authHeader.substring(7);
            
            userEmail = jwtService.extractUsername(jwt);
            /* if not connected yet*/
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.jwtService.loadUserByUsername(userEmail);
                
                if(jwtService.isTokenValid(jwt, userDetails)){
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEmail,
                            //null,
                            ((Login) userDetails),//.getId(),
                            userDetails.getAuthorities()
                            
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                }
                
            }
            
        }
    }
    
    
}
