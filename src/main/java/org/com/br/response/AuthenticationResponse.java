/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.response;

import org.com.br.dto.LoginDTO;

/**
 *
 * @author andre
 */

public class AuthenticationResponse {
    private String token;

    private final LoginDTO user;

    public AuthenticationResponse(String token, LoginDTO user) {
        this.token = token;
        this.user = user;
    }
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginDTO getUser() {
        return user;
    }


    
    
    
    
    
}
