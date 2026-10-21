package com.aula.segurancaweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Permite acesso a todas as rotas (já que o foco principal é demonstrar o CSRF)
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            )
            // No Spring Security 6, o CSRF já é habilitado por padrão.
            // Podemos declarar explicitamente com Customizer.withDefaults():
            .csrf(Customizer.withDefaults());

        return http.build();
    }
}
