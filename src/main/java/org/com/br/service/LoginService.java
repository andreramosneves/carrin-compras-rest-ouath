package org.com.br.service;

import java.util.Optional;
import org.com.br.dto.LoginDTO;
import org.com.br.repositories.LoginRepository;
import org.com.br.request.LoginRequest;
import org.com.br.response.AuthenticationResponse;
import org.com.br.bo.Login;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    
    public LoginService(LoginRepository loginRepository, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.loginRepository = loginRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void save(Login login) {
        loginRepository.save(login);
    }

    public AuthenticationResponse auth(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.user(),
                request.password())
        );

        var user = loginRepository.findByEmail(request.user()).orElseThrow();
        
        var jwtToken = jwtService.generateToken(user);
        /*Depois posso tentar implementar um builder, no tutorial ele implementa o Builder pelo Lombok,
        para educativo vou implementar diretamente.*/
        return new AuthenticationResponse(jwtToken, convertToLoginDTO(user));

    }

    public Optional<Login> findByEmailPassword(String email, String password) {
        return loginRepository.findByEmail(email);
    }
    
    /*public OAuth2User getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            return oauthToken.getPrincipal(); // Retorna o usuário autenticado como um OAuth2User
        }

        return null; // Se o usuário não estiver autenticado via OAuth2
    } 
    */
    
    
    private LoginDTO convertToLoginDTO(Login user) {
        LoginDTO userLocationDTO = new LoginDTO(
                user.getId(),
                user.getName(), user.getEmail(),
                user.getUser_type(),
                user.getCreated_at(),
                user.getUpdated_at());
        return userLocationDTO;
    }

}
