package org.com.br.config;

/**
 *
 * @author andre
 */
import org.com.br.filters.JwtAuthenticatorFilter;
import org.com.br.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = {"org.com.br.repositories"})
@ComponentScan(basePackages = {"org.com.br.config", "org.com.br.controllers", "org.com.br.service", "org.com.br.auth", "org.com.br.filters"})
@EntityScan(basePackages = "org.com.br.bo")
@EnableSpringDataWebSupport( pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableWebSecurity
public class Application extends SpringBootServletInitializer {

    @Autowired
    private JwtAuthenticatorFilter jwtAuthFilter;

    @Autowired
    private JWTService jwtService;

    /*
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withIssuerLocation(this.issuer)
                .jwsAlgorithm(RS512).build();
    }    
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/api/user/**").permitAll()
                                .requestMatchers("/api/home/**").permitAll()
                                .requestMatchers("/api/product/**").hasAnyRole("admin")
                                .requestMatchers("/api/kart/**").hasAnyRole("admin","normal")
                                .requestMatchers("/api/order/**").hasAnyRole("admin", "normal")//Exemplo de Autoridade, Normalmente aqui é se permite Inserir ou Não. Tudo é uma autoridade no Spring.
                                .anyRequest()
                                .denyAll()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(
                        (csrf) -> csrf.disable()
                );

        return http.build();
    }

}

/*
    @Bean
    public RouterFunction<ServerResponse> routerFunction(MyUserHandler userHandler) {
        return route()
                .GET("/{user}", ACCEPT_JSON, userHandler::getUser)
                .GET("/{user}/customers", ACCEPT_JSON, userHandler::getUserCustomers)
                .DELETE("/{user}", ACCEPT_JSON, userHandler::deleteUser)
                .build();
    }
 */
 /*
    
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
 */
