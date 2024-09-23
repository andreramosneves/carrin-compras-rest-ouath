package org.com.br.service;

import java.util.Optional;
import org.com.br.dto.LoginDTO;
import org.com.br.repositories.LoginRepository;
import org.com.br.request.LoginRequest;
import org.com.br.response.AuthenticationResponse;
import org.com.br.bo.Login;
import org.com.br.bo.LoginMongo;
import org.com.br.repositories.LoginMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    
    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    private final LoginMongoRepository repository;

    public LoginService(LoginRepository loginRepository, AuthenticationManager authenticationManager, JWTService jwtService, LoginMongoRepository repository) {
        this.loginRepository = loginRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.repository = repository;
    }
    

    public void save(Login login) {
        loginRepository.save(login);
    }
    public void saveMongo(Login login){
        System.out.println("Tentou salvar no Mongo...");
        System.out.println(repository.insert(new LoginMongo("3", login.getEmail(), login.getEmail(),
                login.getPassword(),
                login.getCreated_at(),
                login.getUpdated_at())
        ));
        
    }

    public AuthenticationResponse auth(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.user(),
                request.password())
        );

        var user = loginRepository.findByEmail(request.user()).orElseThrow();
        
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, convertToLoginDTO(user));

    }
    
    public Optional<Login> findByEmail(String email) {
        return loginRepository.findByEmail(email);
    }    

    public Optional<Login> findByEmailPassword(String email, String password) {
        return loginRepository.findByEmail(email);
    }
        
    
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
