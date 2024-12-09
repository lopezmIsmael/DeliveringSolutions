package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.entidades.Cliente;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.service.ServiceItemPedido;
import com.isoii.deliveringsolutions.dominio.service.ServicePedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GestorPedidoTest {

    @Mock
    private ServicePedido servicePedido;

    @Mock
    private ServiceItemPedido serviceItemPedido;

    @Mock
    private Model model;

    @InjectMocks
    private GestorPedido gestorPedido;

    // Objetos de prueba para Cliente y Restaurante
    private final Cliente clientePrueba = new Cliente("cliente1", "password1", "CLIENTE", "Juan", "Pérez", "12345678A");
    private final Restaurante restaurantePrueba = new Restaurante("restaurante1", "password2", "RESTAURANTE", "12345678A", "Restaurante Prueba");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Tests para el método findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar una lista vacía cuando no hay pedidos")
        void testFindAllEmptyList() {
            when(servicePedido.findAll()).thenReturn(Collections.emptyList());

            List<Pedido> resultado = gestorPedido.findAll();

            assertNotNull(resultado, "La lista retornada no debe ser null");
            assertTrue(resultado.isEmpty(), "La lista debe estar vacía");
            verify(servicePedido, times(1)).findAll();
        }

        @Test
        @DisplayName("Debe retornar una lista con uno o más pedidos")
        void testFindAllWithElements() {
            Pedido pedido1 = new Pedido(1, 1622547800L, "PENDIENTE", clientePrueba, restaurantePrueba);
            Pedido pedido2 = new Pedido(2, 1622634200L, "PAGADO", clientePrueba, restaurantePrueba);
            List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

            when(servicePedido.findAll()).thenReturn(pedidos);

            List<Pedido> resultado = gestorPedido.findAll();

            assertNotNull(resultado, "La lista retornada no debe ser null");
            assertEquals(2, resultado.size(), "La lista debe contener 2 pedidos");
            assertEquals(pedido1, resultado.get(0), "El primer pedido debe ser pedido1");
            assertEquals(pedido2, resultado.get(1), "El segundo pedido debe ser pedido2");
            verify(servicePedido, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Tests para el método registrarPedido")
    class RegistrarPedidoTests {

        @Test
        @DisplayName("Debe registrar un pedido con fecha válida y estado válido")
        void testRegistrarPedidoValido() {
            Pedido pedidoEntrada = new Pedido(1, 1622547800L, "PENDIENTE", clientePrueba, restaurantePrueba);
            Pedido pedidoRegistrado = new Pedido(1, 1622547800L, "PENDIENTE", clientePrueba, restaurantePrueba);

            when(servicePedido.save(any(Pedido.class))).thenReturn(pedidoRegistrado);

            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);

            assertEquals(HttpStatus.CREATED, respuesta.getStatusCode(), "El estado HTTP debe ser CREATED");
            assertEquals(pedidoRegistrado, respuesta.getBody(), "El cuerpo de la respuesta debe ser el pedido registrado");
            verify(servicePedido, times(1)).save(pedidoEntrada);
        }

        @Test
        @DisplayName("Debe retornar BadRequest cuando la fecha es inválida (<=0)")
        void testRegistrarPedidoFechaInvalida() {
            Pedido pedidoEntrada = new Pedido(1, 0L, "PENDIENTE", clientePrueba, restaurantePrueba);

            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);

            assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode(), "El estado HTTP debe ser BAD_REQUEST");
            assertNull(respuesta.getBody(), "El cuerpo de la respuesta debe ser null");
            verify(servicePedido, times(0)).save(any(Pedido.class));
        }

        @Test
        @DisplayName("Debe retornar BadRequest cuando la fecha es nula")
        void testRegistrarPedidoFechaNula() {
            Pedido pedidoEntrada = new Pedido(1, 0L, null, clientePrueba, restaurantePrueba); // Asumiendo que fecha 0 representa null o inválido

            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);

            assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode(), "El estado HTTP debe ser BAD_REQUEST");
            assertNull(respuesta.getBody(), "El cuerpo de la respuesta debe ser null");
            verify(servicePedido, times(0)).save(any(Pedido.class));
        }

        @Test
        @DisplayName("Debe registrar un pedido con estado válido y fecha límite mínima")
        void testRegistrarPedidoEstadoValidoFechaMinima() {
            Pedido pedidoEntrada = new Pedido(1, 1L, "PENDIENTE", clientePrueba, restaurantePrueba);
            Pedido pedidoRegistrado = new Pedido(1, 1L, "PENDIENTE", clientePrueba, restaurantePrueba);

            when(servicePedido.save(any(Pedido.class))).thenReturn(pedidoRegistrado);

            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);

            assertEquals(HttpStatus.CREATED, respuesta.getStatusCode(), "El estado HTTP debe ser CREATED");
            assertEquals(pedidoRegistrado, respuesta.getBody(), "El cuerpo de la respuesta debe ser el pedido registrado");
            verify(servicePedido, times(1)).save(pedidoEntrada);
        }

        @Test
        @DisplayName("Debe retornar BadRequest cuando el estadoPedido es inválido (cadena vacía)")
        void testRegistrarPedidoEstadoInvalidoVacio() {
            Pedido pedidoEntrada = new Pedido(1, 1622547800L, "", clientePrueba, restaurantePrueba);

            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);

            assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode(), "El estado HTTP debe ser BAD_REQUEST");
            assertNull(respuesta.getBody(), "El cuerpo de la respuesta debe ser null");
            verify(servicePedido, times(0)).save(any(Pedido.class));
        }

        @Test
        @DisplayName("Debe registrar correctamente un objeto Pedido válido")
        void testRegistrarPedidoObjetoValido() {
            Pedido pedidoEntrada = new Pedido(1, 1622547800L, "PAGADO", clientePrueba, restaurantePrueba);
            Pedido pedidoRegistrado = new Pedido(1, 1622547800L, "PAGADO", clientePrueba, restaurantePrueba);

            when(servicePedido.save(any(Pedido.class))).thenReturn(pedidoRegistrado);

            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);

            assertEquals(HttpStatus.CREATED, respuesta.getStatusCode(), "El estado HTTP debe ser CREATED");
            assertEquals(pedidoRegistrado, respuesta.getBody(), "El cuerpo de la respuesta debe ser el pedido registrado");
            verify(servicePedido, times(1)).save(pedidoEntrada);
        }

        @Test
        @DisplayName("Debe retornar BadRequest cuando el objeto Pedido es inválido (fecha y estado nulos)")
        void testRegistrarPedidoObjetoNoValido() {
            Pedido pedidoEntrada = new Pedido(1, 0L, null, clientePrueba, restaurantePrueba);

            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);

            assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode(), "El estado HTTP debe ser BAD_REQUEST");
            assertNull(respuesta.getBody(), "El cuerpo de la respuesta debe ser null");
            verify(servicePedido, times(0)).save(any(Pedido.class));
        }
    }

    @Nested
    @DisplayName("Tests para el método findById")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar el pedido cuando el ID es válido y existe")
        void testFindByIdValidoExiste() {
            Pedido pedido = new Pedido(1, 1622547800L, "PENDIENTE", clientePrueba, restaurantePrueba);
            when(servicePedido.findById(1)).thenReturn(Optional.of(pedido));

            Pedido resultado = gestorPedido.findById(1);

            assertNotNull(resultado, "El pedido retornado no debe ser null");
            assertEquals(pedido, resultado, "El pedido retornado debe ser igual al esperado");
            verify(servicePedido, times(1)).findById(1);
        }

        @Test
        @DisplayName("Debe retornar null cuando el ID no existe en la base de datos")
        void testFindByIdInexistente() {
            when(servicePedido.findById(9999)).thenReturn(Optional.empty());

            Pedido resultado = gestorPedido.findById(9999);

            assertNull(resultado, "El resultado debe ser null cuando el pedido no existe");
            verify(servicePedido, times(1)).findById(9999);
        }

        @Test
        @DisplayName("Debe retornar null cuando el ID es inválido (0)")
        void testFindByIdInvalidoCero() {
            Pedido resultado = gestorPedido.findById(0);

            assertNull(resultado, "El resultado debe ser null cuando el ID es 0");
            verify(servicePedido, times(1)).findById(0);
        }

        @Test
        @DisplayName("Debe retornar null cuando el ID es inválido (-1)")
        void testFindByIdInvalidoNegativo() {
            // Configurar el mock para que devuelva Optional.empty() cuando el id es -1
            when(servicePedido.findById(-1)).thenReturn(Optional.empty());

            // Invocar el método del controlador
            Pedido resultado = gestorPedido.findById(-1);

            // Verificar que el resultado sea null
            assertNull(resultado, "El resultado debe ser null cuando el ID es negativo");

            // Verificar que servicePedido.findById(-1) haya sido llamado exactamente una vez
            verify(servicePedido, times(1)).findById(-1);
        }
    }
}
