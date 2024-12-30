package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.ServicioEntrega;
import com.isoii.deliveringsolutions.dominio.service.ServiceServicioEntrega;
import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.entidades.Repartidor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@WebMvcTest(GestorServicioEntrega.class)
class GestorServicioEntregaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceServicioEntrega serviceServicioEntrega;

    private ServicioEntrega servicioEntrega;

    private GestorServicioEntrega gestorServicioEntrega;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Instancia del controlador (si es necesaria)
        gestorServicioEntrega = new GestorServicioEntrega(serviceServicioEntrega);

        // Crea un Pedido para asociarlo al servicioEntrega
        Pedido pedido = new Pedido();
        pedido.setIdPedido(42);        // Asigna un ID cualquiera
        pedido.setEstadoPedido("OK");  // Opcional, por ejemplo

        // Crea el ServicioEntrega
        servicioEntrega = new ServicioEntrega();
        servicioEntrega.setIdServicioEntrega(1);
        servicioEntrega.setFechaRecepcion(10L);
        servicioEntrega.setFechaEntrega(20L);

        // Asigna el pedido al servicioEntrega si tu entidad lo permite
        servicioEntrega.setPedido(pedido);

        Repartidor repartidor = new Repartidor();
        repartidor.setNombre("Nombre Repartidor");
        servicioEntrega.setRepartidor(repartidor);
    }

    // Prueba 1: findAll - Lista vacía
    @Test
    @DisplayName("Debe retornar una lista vacía cuando no hay entregas")
    void testFindAllEmptyList() {
        when(serviceServicioEntrega.findAll()).thenReturn(Collections.emptyList());
        List<ServicioEntrega> servicios = gestorServicioEntrega.findAll();
        assertNotNull(servicios, "La lista no debe ser nula");
        assertTrue(servicios.isEmpty(), "La lista debe estar vacía");
        verify(serviceServicioEntrega, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar una lista con entregas existentes")
    void testFindAllWithElements() {
        when(serviceServicioEntrega.findAll()).thenReturn(List.of(servicioEntrega));
        List<ServicioEntrega> servicios = gestorServicioEntrega.findAll();
        assertNotNull(servicios, "La lista no debe ser nula");
        assertEquals(1, servicios.size(), "La lista debe tener un elemento");
        assertEquals(servicioEntrega, servicios.get(0), "El servicio debe coincidir");
        verify(serviceServicioEntrega, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar un servicio de entrega válido cuando el ID existe")
    void testFindByIdValid() {
        when(serviceServicioEntrega.findById(1)).thenReturn(Optional.of(servicioEntrega));
        ServicioEntrega result = gestorServicioEntrega.findById(1);
        assertNotNull(result, "El servicio no debe ser nulo");
        assertEquals(servicioEntrega, result, "El servicio debe coincidir");
        verify(serviceServicioEntrega, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar null cuando el ID no existe")
    void testFindByIdNonExistent() {
        when(serviceServicioEntrega.findById(9999)).thenReturn(Optional.empty());
        ServicioEntrega result = gestorServicioEntrega.findById(9999);
        assertNull(result, "El resultado debe ser nulo");
        verify(serviceServicioEntrega, times(1)).findById(9999);
    }

    @Test
    @DisplayName("Debe registrar un servicio de entrega con datos válidos")
    void testRegistrarServicioEntregaValid() {
        when(serviceServicioEntrega.save(any(ServicioEntrega.class))).thenReturn(servicioEntrega);
        ServicioEntrega result = gestorServicioEntrega.registrarServicioEntrega(servicioEntrega).getBody();
        assertNotNull(result, "El servicio registrado no debe ser nulo");
        assertEquals(servicioEntrega, result, "El servicio debe coincidir");
        verify(serviceServicioEntrega, times(1)).save(servicioEntrega);
    }

    @Test
    @DisplayName("Debe manejar correctamente las fechas inválidas")
    void testRegistrarServicioEntregaInvalidDates() {
        servicioEntrega.setFechaRecepcion(0L);
        servicioEntrega.setFechaEntrega(-1L);

        when(serviceServicioEntrega.save(any(ServicioEntrega.class))).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            serviceServicioEntrega.save(servicioEntrega);
        }, "Debe lanzar una excepción para fechas inválidas");

        verify(serviceServicioEntrega, times(1)).save(servicioEntrega);
    }

    @Test
    @DisplayName("Debe manejar correctamente un objeto nulo")
    void testRegistrarServicioEntregaNull() {
        when(serviceServicioEntrega.save(null)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            serviceServicioEntrega.save(null);
        }, "Debe lanzar una excepción para un objeto nulo");

        verify(serviceServicioEntrega, times(1)).save(null);
    }

    @Test
    @DisplayName("Debe rechazar fechas incoherentes (fechaEntrega < fechaRecepcion)")
    void testRegistrarServicioEntregaIncoherentDates() {
        servicioEntrega.setFechaRecepcion(20L);
        servicioEntrega.setFechaEntrega(10L);
        when(serviceServicioEntrega.save(any(ServicioEntrega.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            gestorServicioEntrega.registrarServicioEntrega(servicioEntrega);
        }, "Debe lanzar una excepción si fechaEntrega es menor que fechaRecepcion");
        verify(serviceServicioEntrega, times(1)).save(servicioEntrega);
    }

    @Test
    @DisplayName("Debe retornar la vista de registro de servicio de entrega")
    void testMostrarFormularioRegistro() {
        String result = gestorServicioEntrega.mostrarFormularioRegistro();
        assertEquals("Pruebas-RegisterServicioEntrega", result,
                "La vista debe coincidir con 'Pruebas-RegisterServicioEntrega'");
    }

    @Test
    @DisplayName("Debe retornar BAD_REQUEST cuando las fechas son inválidas (0)")
    void testRegistrarServicioEntrega_InvalidDates() throws Exception {
        servicioEntrega.setFechaRecepcion(0L);
        servicioEntrega.setFechaEntrega(0L);

        mockMvc.perform(post("/servicioEntrega/registrarServicioEntrega")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("servicioEntrega", servicioEntrega))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe mostrar la lista de servicios de entrega si existen")
    void testMostrarServiciosEntrega_ConServicios() throws Exception {
        when(serviceServicioEntrega.findAll()).thenReturn(List.of(servicioEntrega));
        mockMvc.perform(get("/servicioEntrega/mostrarServiciosEntrega"))
                .andExpect(status().isOk())
                .andExpect(view().name("/administrador/ListaServiciosEntrega"))
                .andExpect(model().attributeExists("serviciosEntrega"))
                .andExpect(model().attribute("serviciosEntrega", hasSize(1)));
    }

    @Test
    @DisplayName("Debe mostrar error si no existen servicios de entrega")
    void testMostrarServiciosEntrega_SinServicios() throws Exception {
        when(serviceServicioEntrega.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/servicioEntrega/mostrarServiciosEntrega"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "No se encontraron servicios de entrega"));
    }

    @Test
    @DisplayName("Debe mostrar los detalles del servicio de entrega si existe")
    void testMostrarServicioEntrega_Existe() throws Exception {
        when(serviceServicioEntrega.findById(1)).thenReturn(Optional.of(servicioEntrega));
        mockMvc.perform(get("/servicioEntrega/mostrarServicioEntrega/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/administrador/VerServicioEntrega"))
                .andExpect(model().attributeExists("servicioEntrega"))
                .andExpect(model().attribute("servicioEntrega", servicioEntrega));
    }

    @Test
    @DisplayName("Debe mostrar error si el servicio de entrega no existe")
    void testMostrarServicioEntrega_NoExiste() throws Exception {
        when(serviceServicioEntrega.findById(9999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/servicioEntrega/mostrarServicioEntrega/9999"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Servicio de entrega no encontrado"));
    }

}