package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.entidades.Cliente;
import com.isoii.deliveringsolutions.dominio.entidades.ItemMenu;
import com.isoii.deliveringsolutions.dominio.entidades.ItemPedido;
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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

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
        @DisplayName("Debe retornar la vista del pedido con ítems asociados")
        void testMostrarPedidoConItems() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(1);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);

            when(servicePedido.findById(1)).thenReturn(Optional.of(pedido));
            when(serviceItemPedido.findAll()).thenReturn(List.of(itemPedido));

            String vista = gestorPedido.mostrarPedido(1, model);

            assertEquals("/administrador/VerPedido", vista);
            verify(model).addAttribute("pedido", pedido);
            verify(model).addAttribute("itemsPedido", List.of(itemPedido));
        }

        @Test
        @DisplayName("Debe retornar la vista del pedido sin ítems asociados")
        void testMostrarPedidoSinItems() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(1);

            when(servicePedido.findById(1)).thenReturn(Optional.of(pedido));
            when(serviceItemPedido.findAll()).thenReturn(Collections.emptyList());

            String vista = gestorPedido.mostrarPedido(1, model);

            assertEquals("/administrador/VerPedido", vista);
            verify(model).addAttribute("pedido", pedido);
            verify(model).addAttribute("itemsPedido", Collections.emptyList());
        }


        @Test
        @DisplayName("Debe retornar la vista para registrar un pedido")
        void testMostrarFormularioRegistro() {
            String vista = gestorPedido.mostrarFormularioRegistro();

            assertEquals("Pruebas-RegisterPedido", vista);
        }


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

        @Test
        @DisplayName("Debe retornar la vista de lista de pedidos cuando hay pedidos")
        void testMostrarPedidosConPedidos() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(1);

            when(servicePedido.findAll()).thenReturn(List.of(pedido));

            String vista = gestorPedido.mostrarPedidos(model);

            assertEquals("/administrador/ListaPedidos", vista);
            verify(model).addAttribute("pedidos", List.of(pedido));
        }

        @Test
        @DisplayName("Debe retornar la vista de error cuando no hay pedidos")
        void testMostrarPedidosSinPedidos() {
            when(servicePedido.findAll()).thenReturn(Collections.emptyList());

            String vista = gestorPedido.mostrarPedidos(model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "No se encontraron pedidos");
        }


        @Test
        @DisplayName("Debe retornar la vista de error cuando el pedido no es encontrado")
        void testMostrarPedidoNoEncontrado() {
            when(servicePedido.findById(1)).thenReturn(Optional.empty());

            String vista = gestorPedido.mostrarPedido(1, model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "Pedido no encontrado");
        }

        @Test
        @DisplayName("Debe retornar la vista del pedido cuando es encontrado")
        void testMostrarPedidoEncontrado() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(1);

            when(servicePedido.findById(1)).thenReturn(Optional.of(pedido));

            String vista = gestorPedido.mostrarPedido(1, model);

            assertEquals("/administrador/VerPedido", vista);
            verify(model).addAttribute("pedido", pedido);
        }

        @Test
        @DisplayName("Debe retornar la vista de error cuando la lista de pedidos es null")
        void testMostrarPedidosListaNull() {
            when(servicePedido.findAll()).thenReturn(null);

            String vista = gestorPedido.mostrarPedidos(model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "No se encontraron pedidos");
        }

        @Test
        @DisplayName("Debe retornar la vista de lista de ítems de pedido cuando hay ítems")
        void testMostrarItemsPedidoConDatos() {
            Pedido pedido = new Pedido();
            ItemMenu itemMenu = new ItemMenu();
            ItemPedido itemPedido = new ItemPedido(1, pedido, itemMenu);

            when(serviceItemPedido.findAll()).thenReturn(List.of(itemPedido));

            String vista = gestorPedido.mostrarItemsPedido(model);

            assertEquals("/administrador/ListaItemsPedido", vista);
            verify(model).addAttribute("itemsPedidos", List.of(itemPedido));
        }

        @Test
        @DisplayName("Debe retornar la vista de error cuando no hay ítems de pedido")
        void testMostrarItemsPedidoSinDatos() {
            when(serviceItemPedido.findAll()).thenReturn(Collections.emptyList());

            String vista = gestorPedido.mostrarItemsPedido(model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "Items de pedido no encontrados");
        }

        @Test
        @DisplayName("Debe retornar la vista de error cuando la lista de ítems de pedido es null")
        void testMostrarItemsPedidoListaNull() {
            when(serviceItemPedido.findAll()).thenReturn(null);

            String vista = gestorPedido.mostrarItemsPedido(model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "Items de pedido no encontrados");
        }


    }

    @Nested
    @DisplayName("Tests para el método registrarPedido")
    class RegistrarPedidoTests {

        @Test
        @DisplayName("Debe agregar un item al carrito y retornar HttpStatus.OK")
        void testAddToCart() {
            ItemMenu item = new ItemMenu();
            item.setIdItemMenu(1);
            item.setNombre("Item Prueba");

            ResponseEntity<String> response = gestorPedido.addToCart(item);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Item añadido al carrito", response.getBody());
        }

        @Test
        @DisplayName("Debe mostrar el carrito con los ítems agregados")
        void testMostrarCarrito() {
            String vista = gestorPedido.mostrarCarrito(model);

            assertEquals("verMenusRestaurante", vista);
        }

        @Test
        @DisplayName("Debe registrar un pedido correctamente cuando los datos son válidos")
        void testRegistrarPedidoValido() {
            Pedido pedido = new Pedido();
            pedido.setFecha(1622547800L);
            pedido.setEstadoPedido("PENDIENTE");

            when(servicePedido.save(any(Pedido.class))).thenReturn(pedido);

            ResponseEntity<Pedido> response = gestorPedido.registrarPedido(pedido);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            verify(servicePedido, times(1)).save(pedido);
        }

        @Test
        @DisplayName("Debe retornar BAD_REQUEST cuando la fecha es inválida")
        void testRegistrarPedidoFechaInvalida() {
            Pedido pedido = new Pedido();
            pedido.setFecha(0);
            pedido.setEstadoPedido("PENDIENTE");

            ResponseEntity<Pedido> response = gestorPedido.registrarPedido(pedido);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @DisplayName("Prueba parametrizada para entradas inválidas en registrarPedido")
        @ParameterizedTest(name = "{index} => fecha={0}, estado={1}")
        @MethodSource("invalidDataProvider")
        void testRegistrarPedidoInvalido(long fecha, String estado) {
            Pedido pedidoEntrada = new Pedido(1, fecha, estado, clientePrueba, restaurantePrueba);
            ResponseEntity<Pedido> respuesta = gestorPedido.registrarPedido(pedidoEntrada);
            assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
            assertNull(respuesta.getBody());
            verify(servicePedido, times(0)).save(any(Pedido.class));
        }

        static Stream<Arguments> invalidDataProvider() {
            return Stream.of(
                Arguments.of(0L, null),          // fecha = 0, estado = null
                Arguments.of(1622547800L, ""),   // fecha válida, estado vacío
                Arguments.of(0L, "")            // fecha = 0, estado vacío
            );
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
        @DisplayName("Debe retornar BAD_REQUEST cuando el estado del pedido es null")
        void testRegistrarPedidoEstadoNull() {
            Pedido pedido = new Pedido();
            pedido.setFecha(1622547800L);
            pedido.setEstadoPedido(null);

            ResponseEntity<Pedido> response = gestorPedido.registrarPedido(pedido);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Debe retornar BAD_REQUEST cuando el estado del pedido está vacío")
        void testRegistrarPedidoEstadoVacio() {
            Pedido pedido = new Pedido();
            pedido.setFecha(1622547800L);
            pedido.setEstadoPedido("");

            ResponseEntity<Pedido> response = gestorPedido.registrarPedido(pedido);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Debe retornar BAD_REQUEST cuando los datos del pedido son inválidos")
        void testRegistrarPedidoDatosInvalidos() {
            Pedido pedidoInvalido = new Pedido();
            pedidoInvalido.setFecha(0);
            pedidoInvalido.setEstadoPedido("");

            ResponseEntity<Pedido> response = gestorPedido.registrarPedido(pedidoInvalido);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Debe registrar un pedido correctamente cuando los datos son válidos")
        void testRegistrarPedidoDatosValidos() {
            Pedido pedidoValido = new Pedido();
            pedidoValido.setFecha(1622547800L);
            pedidoValido.setEstadoPedido("PENDIENTE");

            when(servicePedido.save(any(Pedido.class))).thenReturn(pedidoValido);

            ResponseEntity<Pedido> response = gestorPedido.registrarPedido(pedidoValido);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            verify(servicePedido, times(1)).save(pedidoValido);
        }

        @Test
        @DisplayName("Debe ignorar los ítems que no coinciden con el pedido")
        void testMostrarPedidoConItemsNoCoincidentes() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(1);

            ItemPedido itemPedido = new ItemPedido();
            Pedido otroPedido = new Pedido();
            otroPedido.setIdPedido(2);
            itemPedido.setPedido(otroPedido);

            when(servicePedido.findById(1)).thenReturn(Optional.of(pedido));
            when(serviceItemPedido.findAll()).thenReturn(List.of(itemPedido));

            String vista = gestorPedido.mostrarPedido(1, model);

            assertEquals("/administrador/VerPedido", vista);
            verify(model).addAttribute("pedido", pedido);
            verify(model).addAttribute("itemsPedido", Collections.emptyList());
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

        @Test
        @DisplayName("Debe retornar la vista de error cuando el item no es encontrado")
        void testMostrarItemPedidoNoEncontrado() {
            when(serviceItemPedido.findById(1)).thenReturn(Optional.empty());

            String vista = gestorPedido.mostrarItemPedido(1, model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "Item de pedido no encontrado");
        }

        @Test
        @DisplayName("Debe retornar la vista del item cuando es encontrado")
        void testMostrarItemPedidoEncontrado() {
            
            Pedido pedido = new Pedido();
            ItemMenu itemMenu = new ItemMenu();
            ItemPedido itemPedido = new ItemPedido(1, pedido, itemMenu);
            
            when(serviceItemPedido.findById(1)).thenReturn(Optional.of(itemPedido));

            String vista = gestorPedido.mostrarItemPedido(1, model);

            assertEquals("/administrador/VerItemPedido", vista);
            verify(model).addAttribute("itemPedido", itemPedido);
        }
    }
}
