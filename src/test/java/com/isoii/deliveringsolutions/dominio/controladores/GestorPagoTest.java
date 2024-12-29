package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.*;
import com.isoii.deliveringsolutions.dominio.service.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorPagoTest {

    @Mock
    private ServiceGroup serviceGroup;
    @Mock
    private ServiceRestaurant serviceRestaurante;
    @Mock
    private ServiceDireccion serviceDireccion;
    @Mock
    private ServicePedido servicePedido;
    @Mock
    private ServiceItemMenu serviceItemMenu;
    @Mock
    private ServiceItemPedido serviceItemPedido;
    @Mock
    private ServicePago servicePago;
    @Mock
    private ServiceUser serviceUsuario;
    @Mock
    private ServiceCodigoPostal serviceCodigoPostal;

    @Mock
    private HttpSession session;
    @Mock
    private Model model;
    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private GestorPago gestorPago;

    // --- Objetos de prueba ---
    private Cliente clientePrueba;
    private Restaurante restaurantePrueba;
    private Direccion direccionEntrega;
    private Direccion direccionRecogida;
    private CartaMenu cartaMenuPrueba;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simulamos la inyección de servicios en serviceGroup
        when(serviceGroup.getServiceRestaurant()).thenReturn(serviceRestaurante);
        when(serviceGroup.getServiceDireccion()).thenReturn(serviceDireccion);
        when(serviceGroup.getServicePedido()).thenReturn(servicePedido);
        when(serviceGroup.getServiceItemMenu()).thenReturn(serviceItemMenu);
        when(serviceGroup.getServiceItemPedido()).thenReturn(serviceItemPedido);
        when(serviceGroup.getServicePago()).thenReturn(servicePago);
        when(serviceGroup.getServiceUsuario()).thenReturn(serviceUsuario);
        when(serviceGroup.getServiceCodigoPostal()).thenReturn(serviceCodigoPostal);

        // Instanciamos algunos objetos base de ejemplo
        clientePrueba = new Cliente("cliente1", "password1", "CLIENTE", "Juan", "Pérez", "12345678A");
        restaurantePrueba = new Restaurante("1", "password2", "RESTAURANTE", "12345678B", "Restaurante Prueba");
        CodigoPostal codigoPostal = new CodigoPostal(1, "45600");
        direccionEntrega = new Direccion("Calle Entrega", "456", "", "Talavera", codigoPostal, clientePrueba);
        direccionRecogida = new Direccion("Calle Recogida", "123", "", "Talavera", codigoPostal, clientePrueba);
        cartaMenuPrueba = new CartaMenu(1, "MENU", restaurantePrueba);

        // Mock para códigos postales
        when(serviceCodigoPostal.findAll()).thenReturn(List.of(codigoPostal));
    }

    // --------------------------------------------------------------------------------------
    //                               TESTS PARA /pago/findAll
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /pago/findAll")
    class FindAllTests {

        @Test
        @DisplayName("Retorna lista vacía si no hay pagos")
        void testFindAllEmptyList() {
            when(servicePago.findAll()).thenReturn(Collections.emptyList());

            List<Pago> resultado = gestorPago.findAll();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(servicePago).findAll();
        }

        @Test
        @DisplayName("Retorna lista con pagos si existen")
        void testFindAllWithElements() {
            Pago pago1 = new Pago(1, "Tarjeta", new Pedido());
            Pago pago2 = new Pago(2, "Efectivo", new Pedido());
            when(servicePago.findAll()).thenReturn(List.of(pago1, pago2));

            List<Pago> resultado = gestorPago.findAll();

            assertEquals(2, resultado.size());
            assertEquals(pago1, resultado.get(0));
            assertEquals(pago2, resultado.get(1));
            verify(servicePago).findAll();
        }
    }

    // --------------------------------------------------------------------------------------
    //                             TESTS PARA /pago/findById/{id}
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /pago/findById/{id}")
    class FindByIdTests {

        @Test
        @DisplayName("Retorna el pago si el ID existe")
        void testFindByIdExiste() {
            Pago pago = new Pago(1, "Tarjeta", new Pedido());
            when(servicePago.findById(1)).thenReturn(Optional.of(pago));

            Pago resultado = gestorPago.findById(1);

            assertNotNull(resultado);
            assertEquals(pago, resultado);
            verify(servicePago).findById(1);
        }

        @Test
        @DisplayName("Retorna null cuando el pago no existe o ID inválido")
        void testFindByIdNoExiste() {
            // Caso ID no encontrado
            when(servicePago.findById(9999)).thenReturn(Optional.empty());
            assertNull(gestorPago.findById(9999));
            verify(servicePago).findById(9999);

            // Caso ID 0
            when(servicePago.findById(0)).thenReturn(Optional.empty());
            assertNull(gestorPago.findById(0));
            verify(servicePago).findById(0);

            // Caso ID negativo
            when(servicePago.findById(-1)).thenReturn(Optional.empty());
            assertNull(gestorPago.findById(-1));
            verify(servicePago).findById(-1);

            // Caso ID nulo
            when(servicePago.findById(null)).thenReturn(Optional.empty());
            assertNull(gestorPago.findById(null));
            verify(servicePago).findById(null);
        }
    }

    // --------------------------------------------------------------------------------------
    //                        TESTS PARA mostrarFormularioRegistro (/pago/register)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /pago/register (mostrarFormularioRegistro)")
    class RegisterFormTests {

        @Test
        @DisplayName("Redirige a login si el usuario no está en sesión")
        void testUsuarioNoAutenticado() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String resultado = gestorPago.mostrarFormularioRegistro("{}", "1", model, session);

            assertEquals("redirect:/usuarios/login", resultado);
        }

        @Test
        @DisplayName("Redirige a /direccion/formularioRegistro si el usuario no tiene direcciones")
        void testSinDireccionesUsuario() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceDireccion.findByUsuario(clientePrueba)).thenReturn(null);

            String resultado = gestorPago.mostrarFormularioRegistro("{}", "1", model, session);

            assertEquals("redirect:/direccion/formularioRegistro", resultado);
        }

        @Test
        @DisplayName("Redirige a /error si el restaurante no existe")
        void testRestauranteNoExiste() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceDireccion.findByUsuario(clientePrueba)).thenReturn(List.of(direccionEntrega));
            when(serviceRestaurante.findById("1")).thenReturn(Optional.empty());

            String resultado = gestorPago.mostrarFormularioRegistro("{}", "1", model, session);

            assertEquals("redirect:/error", resultado);
        }

        @Test
        @DisplayName("Muestra formulario de registro si todo está correcto (carrito válido)")
        void testMostrarFormularioRegistroCarritoValido() {
            String cartData = "[{\"nombre\":\"Pizza\",\"precio\":10.0},{\"nombre\":\"Hamburguesa\",\"precio\":8.0}]";

            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceDireccion.findByUsuario(clientePrueba)).thenReturn(List.of(direccionEntrega));
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            String resultado = gestorPago.mostrarFormularioRegistro(cartData, "1", model, session);

            assertAll(
                () -> assertEquals("RegistrarPedidos", resultado),
                () -> verify(model).addAttribute(eq("direcciones"), anyList()),
                () -> verify(model).addAttribute(eq("restaurante"), eq(restaurantePrueba)),
                () -> verify(model).addAttribute(eq("carrito"), anyList()),
                () -> verify(model).addAttribute(eq("total"), eq(18.0)),
                () -> verify(model).addAttribute(eq("usuario"), eq(clientePrueba)),
                () -> verify(model).addAttribute(eq("codigosPostales"), anyList())
            );
        }

        @Test
        @DisplayName("Maneja error de conversión JSON sin romper el flujo")
        void testErrorConversionJson() {
            String cartData = "{malformed_json}";

            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceDireccion.findByUsuario(clientePrueba)).thenReturn(List.of(direccionEntrega));
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            String resultado = gestorPago.mostrarFormularioRegistro(cartData, "1", model, session);

            assertEquals("RegistrarPedidos", resultado, 
                "Debe continuar y mostrar la página incluso si hay un error al convertir el JSON del carrito."
            );
        }
    }

    // --------------------------------------------------------------------------------------
    //                    TESTS PARA registrarPedido (/pago/registrarPedido)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("POST /pago/registrarPedido")
    class RegistrarPedidoTests {

        @Test
        @DisplayName("Redirige a /error si el carrito está vacío o es nulo")
        void testCarritoVacioONulo() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));
            when(serviceDireccion.findById(1L)).thenReturn(Optional.of(direccionEntrega));

            // Caso carrito vacío
            String resultadoVacio = gestorPago.registrarPedido("TARJETA", "1", 1L, session, Collections.emptyList(), model, redirectAttributes);
            assertEquals("redirect:/error", resultadoVacio);
            verify(redirectAttributes).addFlashAttribute("error", "El carrito está vacío.");

            // Caso carrito nulo
            String resultadoNulo = gestorPago.registrarPedido("TARJETA", "1", 1L, session, null, model, redirectAttributes);
            assertEquals("redirect:/error", resultadoNulo);
            verify(redirectAttributes, times(2)).addFlashAttribute(eq("error"), anyString());
        }

        @Test
        @DisplayName("Redirige a /error si el método de pago es nulo o vacío")
        void testMetodoPagoInvalido() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));
            when(serviceDireccion.findById(1L)).thenReturn(Optional.of(direccionEntrega));

            // Caso método de pago nulo
            String resultadoNulo = gestorPago.registrarPedido(null, "1", 1L, session, List.of(1), model, redirectAttributes);
            assertEquals("redirect:/error", resultadoNulo);
            verify(redirectAttributes).addFlashAttribute("error", "El método de pago es inválido.");

            // Caso método de pago vacío
            String resultadoVacio = gestorPago.registrarPedido("", "1", 1L, session, List.of(1), model, redirectAttributes);
            assertEquals("redirect:/error", resultadoVacio);
            verify(redirectAttributes, times(2)).addFlashAttribute(eq("error"), anyString());
        }

        @Test
        @DisplayName("Redirige a /error si el restaurante no existe")
        void testRestauranteNoEncontrado() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceRestaurante.findById("1")).thenReturn(Optional.empty());

            String resultado = gestorPago.registrarPedido("TARJETA", "1", 1L, session, List.of(1), model, redirectAttributes);

            assertEquals("redirect:/error", resultado);
            verify(redirectAttributes).addFlashAttribute("error", "El restaurante no existe.");
        }

        @Test
        @DisplayName("Redirige a /error si la dirección no existe")
        void testDireccionNoEncontrada() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));
            when(serviceDireccion.findById(0L)).thenReturn(Optional.empty());

            String resultado = gestorPago.registrarPedido("TARJETA", "1", 0L, session, List.of(1), model, redirectAttributes);

            assertEquals("redirect:/error", resultado);
            verify(redirectAttributes).addFlashAttribute("error", "La dirección de entrega no existe.");
        }

        @Test
        @DisplayName("Registra pedido y pago correctamente cuando todo es válido")
        void testRegistrarPedidoCorrecto() {
            // Datos de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = List.of(1, 2, 3);

            // Mocks
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceRestaurante.findById(restauranteId)).thenReturn(Optional.of(restaurantePrueba));
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Simula el guardado del pedido
            Pedido pedido = new Pedido(1, System.currentTimeMillis(), "Pendiente", clientePrueba, restaurantePrueba);
            when(servicePedido.save(any(Pedido.class))).thenReturn(pedido);

            // Simula items de menú
            ItemMenu item1 = new ItemMenu(1, "Pizza", 10.0, cartaMenuPrueba, "COMIDA");
            ItemMenu item2 = new ItemMenu(2, "Hamburguesa", 8.0, cartaMenuPrueba, "COMIDA");
            ItemMenu item3 = new ItemMenu(3, "Ensalada", 5.0, cartaMenuPrueba, "COMIDA");
            when(serviceItemMenu.findById(1)).thenReturn(Optional.of(item1));
            when(serviceItemMenu.findById(2)).thenReturn(Optional.of(item2));
            when(serviceItemMenu.findById(3)).thenReturn(Optional.of(item3));

            // Simula la creación y guardado de ItemPedido
            when(serviceItemPedido.save(any(ItemPedido.class)))
                .thenAnswer(inv -> inv.getArgument(0));

            // Simula la creación y guardado de Pago
            Pago pago = new Pago(1, metodoPago, pedido);
            when(servicePago.save(any(Pago.class))).thenReturn(pago);

            // Simula el usuario del restaurante y sus direcciones de recogida
            Usuario usuarioRestaurante = new Usuario("restaurante1", "password2", "RESTAURANTE");
            when(serviceUsuario.findById(restaurantePrueba.getIdUsuario())).thenReturn(Optional.of(usuarioRestaurante));
            when(serviceDireccion.findByUsuario(usuarioRestaurante)).thenReturn(Collections.singletonList(direccionRecogida));

            // Ejecución
            String resultado = gestorPago.registrarPedido(metodoPago, restauranteId, direccionId, session, itemIds, model, redirectAttributes);

            // Verificaciones
            assertEquals("redirect:/pago/confirmacion", resultado);
            verify(servicePedido, times(2)).save(any(Pedido.class));  // Se guarda dos veces (creación + actualización estado)
            verify(serviceItemMenu, times(itemIds.size())).findById(anyInt());
            verify(serviceItemPedido, times(itemIds.size())).save(any(ItemPedido.class));
            verify(servicePago).save(any(Pago.class));
            verify(redirectAttributes).addFlashAttribute("items", List.of(item1, item2, item3));
            verify(redirectAttributes).addFlashAttribute("restaurante", restaurantePrueba);
            verify(redirectAttributes).addFlashAttribute("cliente", clientePrueba);
            verify(redirectAttributes).addFlashAttribute("total", 23.0);
            verify(redirectAttributes).addFlashAttribute("direccionRecogida", direccionRecogida);
            verify(redirectAttributes).addFlashAttribute("direccionEntrega", direccionEntrega);
        }

        @Test
        @DisplayName("Maneja excepciones inesperadas y redirige a /error")
        void testExcepcionInesperada() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));
            when(serviceDireccion.findById(1L)).thenReturn(Optional.of(direccionEntrega));

            when(servicePedido.save(any(Pedido.class))).thenThrow(new RuntimeException("Error inesperado"));

            String resultado = gestorPago.registrarPedido("TARJETA", "1", 1L, session, List.of(1), model, redirectAttributes);

            assertEquals("redirect:/error", resultado);
            verify(redirectAttributes).addFlashAttribute("error", "Ocurrió un error inesperado al procesar el pedido.");
        }
    }

    // --------------------------------------------------------------------------------------
    //                      TESTS PARA mostrarConfirmacion (/pago/confirmacion)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /pago/confirmacion")
    class ConfirmacionTests {

        @Test
        @DisplayName("Muestra confirmación si el usuario está en sesión")
        void testConfirmacionSesionValida() {
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            String resultado = gestorPago.mostrarConfirmacion(session, model);

            assertEquals("ConfirmacionPedido", resultado);
            verify(model).addAttribute("usuario", clientePrueba);
        }

        @Test
        @DisplayName("Redirige a /login si no hay usuario en sesión")
        void testConfirmacionSinUsuario() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String resultado = gestorPago.mostrarConfirmacion(session, model);

            assertEquals("redirect:/login", resultado);
            verify(model, never()).addAttribute(anyString(), any());
        }
    }

    // --------------------------------------------------------------------------------------
    //                       TESTS PARA mostrarPagos y mostrarPago
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /pago/mostrarPagos y /pago/mostrarPago/{id}")
    class MostrarPagosTests {

        @Test
        @DisplayName("mostrarPagos: Muestra lista de pagos si existen")
        void testMostrarPagosConPagos() {
            Pago pago1 = new Pago(1, "Tarjeta", new Pedido());
            Pago pago2 = new Pago(2, "Efectivo", new Pedido());
            when(servicePago.findAll()).thenReturn(List.of(pago1, pago2));

            String resultado = gestorPago.mostrarPagos(model);

            assertEquals("/administrador/ListaPagos", resultado);
            verify(model).addAttribute("pagos", List.of(pago1, pago2));
        }

        @Test
        @DisplayName("mostrarPagos: Muestra error si no hay pagos")
        void testMostrarPagosSinPagos() {
            when(servicePago.findAll()).thenReturn(Collections.emptyList());

            String resultado = gestorPago.mostrarPagos(model);

            assertEquals("error", resultado);
            verify(model).addAttribute("error", "No se encontraron pagos");
        }

        @Test
        @DisplayName("mostrarPago: Muestra pago si el ID existe")
        void testMostrarPagoExiste() {
            Pago pago = new Pago(1, "Tarjeta", new Pedido());
            when(servicePago.findById(1)).thenReturn(Optional.of(pago));

            String resultado = gestorPago.mostrarPago(1, model);

            assertEquals("/administrador/VerPago", resultado);
            verify(model).addAttribute("pago", pago);
        }

        @Test
        @DisplayName("mostrarPago: Muestra error si el pago no existe")
        void testMostrarPagoNoExiste() {
            when(servicePago.findById(9999)).thenReturn(Optional.empty());

            String resultado = gestorPago.mostrarPago(9999, model);

            assertEquals("error", resultado);
            verify(model).addAttribute("error", "Pago no encontrado");
        }
    }
}
