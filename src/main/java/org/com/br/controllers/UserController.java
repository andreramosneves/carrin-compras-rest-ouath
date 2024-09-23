package org.com.br.controllers;

import org.com.br.request.LoginRequest;
import org.com.br.response.AuthenticationResponse;
import org.com.br.service.LoginService;
import org.com.br.bo.Login;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final LoginService service;
    

    public UserController(LoginService service, AuthenticationManager authenticationManager) {
        this.service = service;
    }


    @PostMapping(path = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthenticationResponse auth(@RequestBody LoginRequest request) {
        return service.auth(request);

    }

    @PostMapping(path = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity inseriUsuario(@RequestBody Login login) {

        login.setPassword(BCrypt.hashpw(login.getPassword(), BCrypt.gensalt(10)));
        if (service.findByEmail(login.getEmail()).isEmpty()) {
            login.geraValoresDefault();
            service.save(login);
            service.saveMongo(login);
            return ResponseEntity.ok("\"retorno\":\"Usuário cadastrado com o Usuário\"");
        }
            login.geraValoresDefault();
            service.saveMongo(login);
        return ResponseEntity.ok("\"retorno\":\"Nao foi possivel cadastrar o Usuário.\"");

    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "Logout";
    }


}
