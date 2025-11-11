package com.isoii.deliveringsolutions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de seguridad para la aplicación DeliveringSolutions
 * Implementa best practices de seguridad en Spring Boot
 */
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    /**
     * Bean que proporciona BCryptPasswordEncoder para encriptar contraseñas
     * BCrypt es el algoritmo recomendado por OWASP para almacenar contraseñas
     * 
     * @return PasswordEncoder configurado con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // Strength de 12 (más seguro, más lento)
    }

    /**
     * Configuración CORS para prevenir ataques de origen cruzado
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("${app.cors.allowed-origins:http://localhost:3000}")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
