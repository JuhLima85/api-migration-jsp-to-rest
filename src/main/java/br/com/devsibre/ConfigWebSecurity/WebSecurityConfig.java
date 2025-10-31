package br.com.devsibre.ConfigWebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())       // evita 403 em POST/PUT sem CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }

/*@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true) // 🔹 Habilita @RolesAllowed
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Evita erro 403 em POST sem CSRF
                .cors(cors -> {}) // Usa config padrão de CORS (se precisar, pode adicionar bean CorsConfigurationSource)
                .authorizeHttpRequests(auth -> auth
                        // Libere endpoints públicos (ex: login, swagger, health)
                        .requestMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Tudo o resto precisa estar autenticado
                        .anyRequest().authenticated()
                )
                // 🔹 Informa que o backend usará tokens JWT (OAuth2 Resource Server)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }*/
}