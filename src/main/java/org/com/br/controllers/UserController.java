package org.com.br.controllers;

import org.com.br.request.LoginRequest;
import org.com.br.response.AuthenticationResponse;
import org.com.br.service.LoginService;
import org.com.br.bo.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
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
 

    
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/valida")
    public String index(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return String.format("Hello, %s!", (String) principal.getAttribute("sub"));
    }

    @PostMapping("/login")
    @ResponseBody
    public AuthenticationResponse auth(@RequestBody LoginRequest request) {
        return service.auth(request);

    }

    /*
    private void criaSessao(HttpServletRequest request, Login login) {
        request.getSession().setAttribute("user", login);
    }
     */
    @RequestMapping(value = "/register")
    public ResponseEntity inseriUsuario(Login login) {

        login.setPassword(BCrypt.hashpw(login.getPassword(), BCrypt.gensalt(10)));

        if (service.findByEmailPassword(login.getEmail(), login.getPassword()) == null) {
            login.geraValoresDefault();
            service.save(login);
            return ResponseEntity.ok("Usuário cadastrado com o Usuário");
        }
        return ResponseEntity.ok("Não foi cadastrar o Usuário.");

    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "Logout";
    }
    /*
    @RequestMapping({"/login", "/"})
    public String execute() {
        model.addObject("errorMessage", "");
        return "login";
    }
     */

}
