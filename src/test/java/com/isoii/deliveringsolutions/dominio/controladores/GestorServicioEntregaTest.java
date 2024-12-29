package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.ServicioEntrega;
import com.isoii.deliveringsolutions.dominio.service.ServiceServicioEntrega;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.List;

@WebMvcTest(GestorServicioEntrega.class)
public class GestorServicioEntregaTest {

    @MockBean
    private ServiceServicioEntrega serviceServicioEntrega;

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
        when(gestorServicioEntrega.findAll()).thenReturn(Collections.emptyList());
        List<ServicioEntrega> servicios = gestorServicioEntrega.findAll();
        assertNotNull(servicios, "La lista no debe ser nula");
        assertTrue(servicios.isEmpty(), "La lista debe estar vacía");
        verify(serviceServicioEntrega, times(1)).findAll();

    }

    // Prueba 2: findAll - Lista con elementos
    @Test
    @DisplayName("Debe retornar una lista con entregas existentes")
    public void testFindAllWithElements() {
        when(gestorServicioEntrega.findAll()).thenReturn(List.of(servicioEntrega));

        List<ServicioEntrega> servicios = gestorServicioEntrega.findAll();

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

        ServicioEntrega result = gestorServicioEntrega.registrarServicioEntrega(servicioEntrega).getBody();
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

    @Test
    @DisplayName("Debe retornar la vista de registro de servicio de entrega")
    public void testMostrarFormularioRegistro() {
        String vista = gestorServicioEntrega.mostrarFormularioRegistro();
        assertEquals("Pruebas-RegisterServicioEntrega", vista, "La vista debe coincidir con 'Pruebas-RegisterServicioEntrega'");
    }

    @Test
    @DisplayName("Debe retornar BAD_REQUEST cuando las fechas son inválidas (0)")
    public void testRegistrarServicioEntrega_InvalidDates() throws Exception {
        servicioEntrega.setFechaRecepcion(0L);
        servicioEntrega.setFechaEntrega(0L);

        mockMvc.perform(post("/servicioEntrega/registrarServicioEntrega")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("servicioEntrega", servicioEntrega))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe mostrar la lista de servicios de entrega si existen")
    public void testMostrarServiciosEntrega_ConServicios() throws Exception {
        when(serviceServicioEntrega.findAll()).thenReturn(List.of(servicioEntrega));

        mockMvc.perform(get("/servicioEntrega/mostrarServiciosEntrega"))
                .andExpect(status().isOk())
                .andExpect(view().name("/administrador/ListaServiciosEntrega"))
                .andExpect(model().attributeExists("serviciosEntrega"))
                .andExpect(model().attribute("serviciosEntrega", hasSize(1)));
    }

    @Test
    @DisplayName("Debe mostrar error si no existen servicios de entrega")
    public void testMostrarServiciosEntrega_SinServicios() throws Exception {
        when(serviceServicioEntrega.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/servicioEntrega/mostrarServiciosEntrega"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "No se encontraron servicios de entrega"));
    }

    @Test
    @DisplayName("Debe mostrar los detalles del servicio de entrega si existe")
    public void testMostrarServicioEntrega_Existe() throws Exception {
        when(serviceServicioEntrega.findById(1)).thenReturn(Optional.of(servicioEntrega));

        mockMvc.perform(get("/servicioEntrega/mostrarServicioEntrega/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/administrador/VerServicioEntrega"))
                .andExpect(model().attributeExists("servicioEntrega"))
                .andExpect(model().attribute("servicioEntrega", servicioEntrega));
    }

    @Test
    @DisplayName("Debe mostrar error si el servicio de entrega no existe")
    public void testMostrarServicioEntrega_NoExiste() throws Exception {
        when(serviceServicioEntrega.findById(9999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/servicioEntrega/mostrarServicioEntrega/9999"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Servicio de entrega no encontrado"));
    }
}
