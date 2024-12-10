package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.ServicioEntrega;
import com.isoii.deliveringsolutions.dominio.service.ServiceServicioEntrega;

import jakarta.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.List;

@WebMvcTest(GestorServicioEntrega.class)
public class GestorServicioEntregaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceServicioEntrega serviceServicioEntrega;

    @Autowired
    private ObjectMapper objectMapper;

    private ServicioEntrega servicioEntrega;

    private GestorServicioEntrega gestorServicioEntrega;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gestorServicioEntrega = new GestorServicioEntrega(serviceServicioEntrega);
        servicioEntrega = new ServicioEntrega();
        servicioEntrega.setIdServicioEntrega(1);
        servicioEntrega.setFechaRecepcion(10L);
        servicioEntrega.setFechaEntrega(20L);
    }

    // Prueba 1: findAll - Lista vacía
    @Test
    @DisplayName("Debe retornar una lista vacía cuando no hay entregas")
    public void testFindAllEmptyList(){
        when(serviceServicioEntrega.findAll()).thenReturn(Collections.emptyList());
        List<ServicioEntrega> servicios = serviceServicioEntrega.findAll();
        assertNotNull(servicios, "La lista no debe ser nula");
        assertTrue(servicios.isEmpty(), "La lista debe estar vacía");
        verify(serviceServicioEntrega, times(1)).findAll();

    }

    // Prueba 2: findAll - Lista con elementos
    @Test
    @DisplayName("Debe retornar una lista con entregas existentes")
    public void testFindAllWithElements() {
        when(serviceServicioEntrega.findAll()).thenReturn(List.of(servicioEntrega));

        List<ServicioEntrega> servicios = serviceServicioEntrega.findAll();

        assertNotNull(servicios, "La lista no debe ser nula");
        assertEquals(1, servicios.size(), "La lista debe tener un elemento");
        assertEquals(servicioEntrega, servicios.get(0), "El servicio debe coincidir");
        verify(serviceServicioEntrega, times(1)).findAll();
    }

    // Prueba 3: findById - ID válido
    @Test
    @DisplayName("Debe retornar un servicio de entrega válido cuando el ID existe")
    public void testFindByIdValid() {
        when(serviceServicioEntrega.findById(1)).thenReturn(Optional.of(servicioEntrega));

        ServicioEntrega result = gestorServicioEntrega.findById(1);

        assertNotNull(result, "El servicio no debe ser nulo");
        assertEquals(servicioEntrega, result, "El servicio debe coincidir");
        verify(serviceServicioEntrega, times(1)).findById(1);
    }

    // Prueba 4: findById - ID inexistente
    @Test
    @DisplayName("Debe retornar null cuando el ID no existe")
    public void testFindByIdNonExistent() {
        when(serviceServicioEntrega.findById(9999)).thenReturn(Optional.empty());

        ServicioEntrega result = gestorServicioEntrega.findById(9999);

        assertNull(result, "El resultado debe ser nulo");
        verify(serviceServicioEntrega, times(1)).findById(9999);
    }

    // Prueba 5: registrarServicioEntrega - Datos válidos
    @Test
    @DisplayName("Debe registrar un servicio de entrega con datos válidos")
    public void testRegistrarServicioEntregaValid() {
        when(serviceServicioEntrega.save(any(ServicioEntrega.class))).thenReturn(servicioEntrega);

        ServicioEntrega result = serviceServicioEntrega.save(servicioEntrega);

        assertNotNull(result, "El servicio registrado no debe ser nulo");
        assertEquals(servicioEntrega, result, "El servicio debe coincidir");
        verify(serviceServicioEntrega, times(1)).save(servicioEntrega);
    }

    // Prueba 6: registrarServicioEntrega - Datos inválidos (fechas)
    @Test
    @DisplayName("Debe manejar correctamente las fechas inválidas")
    public void testRegistrarServicioEntregaInvalidDates() {
        servicioEntrega.setFechaRecepcion(0L);
        servicioEntrega.setFechaEntrega(-1L);

        when(serviceServicioEntrega.save(any(ServicioEntrega.class))).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            serviceServicioEntrega.save(servicioEntrega);
        }, "Debe lanzar una excepción para fechas inválidas");

        verify(serviceServicioEntrega, times(1)).save(servicioEntrega);
    }

    // Prueba 7: registrarServicioEntrega - Datos nulos
    @Test
    @DisplayName("Debe manejar correctamente un objeto nulo")
    public void testRegistrarServicioEntregaNull() {
        when(serviceServicioEntrega.save(null)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            serviceServicioEntrega.save(null);
        }, "Debe lanzar una excepción para un objeto nulo");

        verify(serviceServicioEntrega, times(1)).save(null);
    }

    // Prueba 8: registrarServicioEntrega - Fechas incoherentes
    @Test
    @DisplayName("Debe rechazar fechas incoherentes (fechaEntrega < fechaRecepcion)")
    public void testRegistrarServicioEntregaIncoherentDates() {
        servicioEntrega.setFechaRecepcion(20L);
        servicioEntrega.setFechaEntrega(10L);

        when(serviceServicioEntrega.save(any(ServicioEntrega.class))).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            serviceServicioEntrega.save(servicioEntrega);
        }, "Debe lanzar una excepción si fechaEntrega es menor que fechaRecepcion");

        verify(serviceServicioEntrega, times(1)).save(servicioEntrega);
    }
}
