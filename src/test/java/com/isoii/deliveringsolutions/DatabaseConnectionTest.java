package com.isoii.deliveringsolutions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testConnection() {
        // Intentamos realizar una simple consulta para comprobar la conexión
        String query = "SELECT 1";
        
        Integer result = jdbcTemplate.queryForObject(query, Integer.class);
        
        // Si la conexión es exitosa, esperamos que el resultado sea 1
        assertTrue(result != null && result == 1, "La conexión a la base de datos ha fallado");
    }
}
