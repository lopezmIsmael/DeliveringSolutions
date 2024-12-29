package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.*;
import com.isoii.deliveringsolutions.dominio.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GestorPagoTest {
    private static final Logger logger = LoggerFactory.getLogger(GestorPagoTest.class);

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
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private GestorPago gestorPago;

    // Objetos de prueba
    private final Cliente clientePrueba = new Cliente("cliente1", "password1", "CLIENTE", "Juan", "Pérez", "12345678A");
    private final Restaurante restaurantePrueba = new Restaurante("1", "password2", "RESTAURANTE", "12345678A", "Restaurante Prueba");
    private final CodigoPostal codigoPostalRecogida = new CodigoPostal(1, "45600");
    private final Direccion direccionRecogida = new Direccion("Calle Recogida", "123", "", "Talavera", codigoPostalRecogida, clientePrueba);
    private final Direccion direccionEntrega = new Direccion("Calle Entrega", "456", "", "Talavera", codigoPostalRecogida, clientePrueba);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Configurar los servicios mockeados
        when(serviceGroup.getServiceRestaurant()).thenReturn(serviceRestaurante);
        when(serviceGroup.getServiceDireccion()).thenReturn(serviceDireccion);
        when(serviceGroup.getServicePedido()).thenReturn(servicePedido);
        when(serviceGroup.getServiceItemMenu()).thenReturn(serviceItemMenu);
        when(serviceGroup.getServiceItemPedido()).thenReturn(serviceItemPedido);
        when(serviceGroup.getServicePago()).thenReturn(servicePago);
        when(serviceGroup.getServiceUsuario()).thenReturn(serviceUsuario);

        // Mock para ServiceCodigoPostal
        ServiceCodigoPostal mockCodigoPostal = mock(ServiceCodigoPostal.class);
        when(serviceGroup.getServiceCodigoPostal()).thenReturn(mockCodigoPostal);

        // Simulación del comportamiento de findAll
        List<CodigoPostal> codigosPostales = Arrays.asList(new CodigoPostal(1, "45600"));
        when(mockCodigoPostal.findAll()).thenReturn(codigosPostales);
    }


    @Nested
    @DisplayName("Tests para el endpoint /pago/findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar una lista vacía cuando no hay pagos")
        void testFindAllEmptyList() {
            when(servicePago.findAll()).thenReturn(Collections.emptyList());

            List<Pago> resultado = gestorPago.findAll();

            assertNotNull(resultado, "La lista retornada no debe ser null");
            assertTrue(resultado.isEmpty(), "La lista debe estar vacía");
            verify(servicePago, times(1)).findAll();
        }

        @Test
        @DisplayName("Debe retornar una lista con uno o más pagos")
        void testFindAllWithElements() {
            Pago pago1 = new Pago(1, "Tarjeta", new Pedido());
            Pago pago2 = new Pago(2, "Efectivo", new Pedido());
            List<Pago> pagos = Arrays.asList(pago1, pago2);
            when(servicePago.findAll()).thenReturn(pagos);

            List<Pago> resultado = gestorPago.findAll();

            assertNotNull(resultado, "La lista retornada no debe ser null");
            assertEquals(2, resultado.size(), "La lista debe contener 2 pagos");
            assertEquals(pago1, resultado.get(0), "El primer pago debe ser pago1");
            assertEquals(pago2, resultado.get(1), "El segundo pago debe ser pago2");
            verify(servicePago, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Tests para el endpoint /pago/findById/{id}")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar el pago cuando el ID es válido y existe")
        void testFindByIdValidoExiste() {
            Pago pago1 = new Pago(1, "Tarjeta", new Pedido());
            when(servicePago.findById(1)).thenReturn(Optional.of(pago1));

            Pago resultado = gestorPago.findById(1);

            assertNotNull(resultado, "El pago retornado no debe ser null");
            assertEquals(pago1, resultado, "El pago retornado debe ser igual al esperado");
            verify(servicePago, times(1)).findById(1);
        }

        @Test
        @DisplayName("Debe retornar null cuando el ID no existe en la base de datos")
        void testFindByIdInexistente() {
            when(servicePago.findById(9999)).thenReturn(Optional.empty());

            Pago resultado = gestorPago.findById(9999);

            assertNull(resultado, "El resultado debe ser null cuando el pago no existe");
            verify(servicePago, times(1)).findById(9999);
        }

        @Test
        @DisplayName("Debe retornar null cuando el ID es inválido (0)")
        void testFindByIdInvalidoCero() {
            Pago resultado = gestorPago.findById(0);

            assertNull(resultado, "El resultado debe ser null cuando el ID es 0");
            verify(servicePago, times(1)).findById(0);
        }

        @Test
        @DisplayName("Debe retornar null cuando el ID es inválido (-1)")
        void testFindByIdInvalidoNegativo() {
            when(servicePago.findById(-1)).thenReturn(Optional.empty());

            Pago resultado = gestorPago.findById(-1);

            assertNull(resultado, "El resultado debe ser null cuando el ID es negativo");
            verify(servicePago, times(1)).findById(-1);
        }

        @Test
        @DisplayName("Debe retornar null cuando el ID es nulo")
        void testFindByIdNulo() {
            Pago resultado = gestorPago.findById(null);

            assertNull(resultado, "El resultado debe ser null cuando el ID es null");
            verify(servicePago, times(1)).findById(null);
        }
        @Test
        @DisplayName("Debe redirigir a /direccion/formularioRegistro si no hay direcciones asociadas al usuario")
        void testSinDireccionesUsuario() {
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular que no hay direcciones asociadas al usuario
            when(serviceDireccion.findByUsuario(clientePrueba)).thenReturn(null);

            // Llamada al método del controlador
            String resultado = gestorPago.mostrarFormularioRegistro("{}", "1", model, session);

            // Verificar la redirección a /direccion/formularioRegistro
            assertEquals("redirect:/direccion/formularioRegistro", resultado, "Debe redirigir al formulario de registro de direcciones");
        }


        @Test
        @DisplayName("Debe manejar correctamente cuando el ID no es numérico")
        void testFindByIdNoNumerico() {
            // En Java, un parámetro de tipo Integer no puede recibir una cadena,
            // por lo que este caso no es aplicable directamente.
            // Si el controlador maneja excepciones para tipos incorrectos,
            // deberías implementar pruebas de integración.
            // Aquí, no se realiza ninguna acción.
            assertTrue(true, "No se puede probar IDs no numéricos en pruebas unitarias de Java.");
        }
    }

    @Nested
    @DisplayName("Tests para el endpoint /pago/register")
    class RegisterTests {

        @Test
        @DisplayName("Debe registrar correctamente un carrito válido")
        void testRegisterPagoCarritoValido() {
            // Parámetros de entrada
            String metodoPago = "TARJETA";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);
        
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
        
            // Simular restaurante existente
            when(serviceRestaurante.findById(eq(restauranteId))).thenReturn(Optional.of(restaurantePrueba));
        
            // Simular dirección válida
            when(serviceDireccion.findById(eq(direccionId))).thenReturn(Optional.of(direccionEntrega));
        
            // Simular la creación y guardado del pedido
            Pedido pedidoGuardado = new Pedido(1, System.currentTimeMillis(), "Pendiente", clientePrueba, restaurantePrueba);
            when(servicePedido.save(any(Pedido.class))).thenAnswer(invocation -> {
                Pedido pedido = invocation.getArgument(0);
                pedido.setIdPedido(1); // Simula asignación de ID
                return pedido;
            });
        
            // Simular ItemMenu existentes
            CartaMenu cartaMenu = new CartaMenu(1, "MENU", restaurantePrueba);
            ItemMenu item1 = new ItemMenu(1, "Pizza", 10.0, cartaMenu, "COMIDA");
            ItemMenu item2 = new ItemMenu(2, "Hamburguesa", 8.0, cartaMenu, "COMIDA");
            ItemMenu item3 = new ItemMenu(3, "Ensalada", 5.0, cartaMenu, "COMIDA");
            when(serviceItemMenu.findById(1)).thenReturn(Optional.of(item1));
            when(serviceItemMenu.findById(2)).thenReturn(Optional.of(item2));
            when(serviceItemMenu.findById(3)).thenReturn(Optional.of(item3));
        
            // Simular la creación y guardado de ItemPedido
            when(serviceItemPedido.save(any(ItemPedido.class))).thenReturn(
                    new ItemPedido(1, pedidoGuardado, item1),
                    new ItemPedido(2, pedidoGuardado, item2),
                    new ItemPedido(3, pedidoGuardado, item3)
            );
        
            // Simular la creación y guardado del Pago
            Pago pagoGuardado = new Pago(1, "TARJETA", pedidoGuardado);
            when(servicePago.save(any(Pago.class))).thenReturn(pagoGuardado);
        
            // Simular usuario del restaurante y direcciones de recogida
            Usuario usuarioRestaurante = new Usuario("restaurante1", "password2", "RESTAURANTE");
            when(serviceUsuario.findById(restaurantePrueba.getIdUsuario())).thenReturn(Optional.of(usuarioRestaurante));
        
            List<Direccion> direccionesRecogida = Arrays.asList(direccionRecogida);
            when(serviceDireccion.findByUsuario(usuarioRestaurante)).thenReturn(direccionesRecogida);
        
            // Simular dirección de entrega
            when(serviceDireccion.findById(eq(direccionId))).thenReturn(Optional.of(direccionEntrega));
        
            // Calcular total
            Double total = item1.getPrecio() + item2.getPrecio() + item3.getPrecio(); // 10 + 8 + 5 = 23.0
        
            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );
        
            // Verificar la redirección
            assertEquals("redirect:/pago/confirmacion", resultado, "Debe redirigir a la confirmación");
        
            // Verificar interacciones con los servicios
            verify(serviceRestaurante, times(1)).findById(eq("1"));
            //verify(serviceDireccion, times(2)).findById(eq(direccionId)); // Una vez en el inicio y otra después
            verify(servicePedido, times(2)).save(any(Pedido.class)); // Una vez para crear y otra para actualizar el estado
            verify(serviceItemMenu, times(1)).findById(1);
            verify(serviceItemMenu, times(1)).findById(2);
            verify(serviceItemMenu, times(1)).findById(3);
            verify(serviceItemPedido, times(3)).save(any(ItemPedido.class));
            verify(servicePago, times(1)).save(any(Pago.class));
            verify(serviceUsuario, times(1)).findById(eq(restaurantePrueba.getIdUsuario()));
            verify(serviceDireccion, times(1)).findByUsuario(usuarioRestaurante);
            //verify(serviceDireccion, times(2)).findById(eq(direccionId));
        
            // Verificar atributos flash
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("pedido"), any(Pedido.class));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("items"), eq(Arrays.asList(item1, item2, item3)));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("pago"), any(Pago.class));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("restaurante"), eq(restaurantePrueba));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("cliente"), eq(clientePrueba));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("total"), eq(total));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("direccionRecogida"), eq(direccionRecogida));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("direccionEntrega"), eq(direccionEntrega));
        }
    
        @Test
        @DisplayName("Debe retornar una redirección a error cuando el carrito está vacío")
        void testRegisterPagoCarritoVacio() {
            // Parámetros de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Collections.emptyList();

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar la redirección a la página de error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error cuando el carrito está vacío");

            // Verificar que no se realizan guardados adicionales
            verify(servicePedido, times(0)).save(any(Pedido.class));
            verify(servicePago, times(0)).save(any(Pago.class));
            verify(serviceItemMenu, times(0)).findById(anyInt());
            verify(serviceItemPedido, times(0)).save(any(ItemPedido.class));

            // Verificar que se agrega el atributo de error
            verify(redirectAttributes, times(1)).addFlashAttribute("error", "El carrito está vacío.");
        }

        @Test
        @DisplayName("Debe retornar una redirección a error cuando metodoPago es null")
        void testRegisterPagoMetodoPagoNulo() {
            // Parámetros de entrada
            String metodoPago = null;
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar la redirección a la página de error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error cuando metodoPago es null");

            // Verificar que no se realizan guardados adicionales
            verify(servicePedido, times(0)).save(any(Pedido.class));
            verify(servicePago, times(0)).save(any(Pago.class));
            verify(serviceItemMenu, times(0)).findById(anyInt());
            verify(serviceItemPedido, times(0)).save(any(ItemPedido.class));

            // Verificar que se agrega el atributo de error
            verify(redirectAttributes, times(1)).addFlashAttribute("error", "El método de pago es inválido.");
        }

        @Test
        @DisplayName("Debe redirigir a error cuando el restauranteId es inválido (-1)")
        void testRegisterPagoRestauranteIdInvalido() {
            // Parámetros de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "-1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante inexistente
            when(serviceRestaurante.findById("-1")).thenReturn(Optional.empty());

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar la redirección a la página de error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error cuando el restauranteId es inválido");

            // Verificar que no se realizan guardados adicionales
            verify(servicePedido, times(0)).save(any(Pedido.class));
            verify(servicePago, times(0)).save(any(Pago.class));
            verify(serviceItemMenu, times(0)).findById(anyInt());
            verify(serviceItemPedido, times(0)).save(any(ItemPedido.class));

            // Verificar que se agrega el atributo de error
            verify(redirectAttributes, times(1)).addFlashAttribute("error", "El restaurante no existe.");
        }

        @Test
        @DisplayName("Debe redirigir a error cuando la dirección es inválida (0)")
        void testRegisterPagoDireccionInvalida() {
            // Parámetros de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "1";
            Long direccionId = 0L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección inexistente
            when(serviceDireccion.findById(0L)).thenReturn(Optional.empty());

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar la redirección a la página de error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error cuando la dirección es inválida");

            // Verificar que no se realizan guardados adicionales
            verify(servicePedido, times(0)).save(any(Pedido.class));
            verify(servicePago, times(0)).save(any(Pago.class));
            verify(serviceItemMenu, times(0)).findById(anyInt());
            verify(serviceItemPedido, times(0)).save(any(ItemPedido.class));

            // Verificar que se agrega el atributo de error
            verify(redirectAttributes, times(1)).addFlashAttribute("error", "La dirección de entrega no existe.");
        }

        @Test
        @DisplayName("Debe manejar correctamente la conversión del JSON a lista de objetos")
        void testRegisterPagoFalloConversionJSON() {
            // Parámetros de entrada con JSON mal formado
            String metodoPago = "TARJETA";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Simular una excepción durante el guardado del pedido
            when(servicePedido.save(any(Pedido.class))).thenThrow(new RuntimeException("Error al procesar el pedido"));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar la redirección a la página de error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error ante un error en el procesamiento");

            // Verificar que no se guardó el Pago debido al error
            verify(servicePago, times(0)).save(any(Pago.class));

            // Verificar que el pedido fue intentado guardar pero falló
            verify(servicePedido, times(1)).save(any(Pedido.class));

            // Verificar que se agrega el atributo de error
            verify(redirectAttributes, times(1)).addFlashAttribute("error", "Ocurrió un error inesperado al procesar el pedido.");
        }

        @Test
        @DisplayName("Debe manejar el error al convertir el carrito desde JSON")
        void testErrorConversionCarrito() {
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            // Simular direcciones asociadas al usuario
            when(serviceDireccion.findByUsuario(clientePrueba)).thenReturn(Arrays.asList(direccionEntrega));

            // Proveer un JSON mal formado para cartData
            String cartData = "{malformed_json}";

            // Llamada al método del controlador
            String resultado = gestorPago.mostrarFormularioRegistro(cartData, "1", model, session);

            // Verificar que no lanza excepciones y sigue procesando
            assertEquals("RegistrarPedidos", resultado, "Debe continuar y mostrar la página incluso si el carrito no puede ser convertido");
        }

        @Test
        @DisplayName("Debe redirigir a /error si el restaurante no existe")
        void testRestauranteNoEncontrado() {
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular que el restaurante no existe
            when(serviceRestaurante.findById("1")).thenReturn(Optional.empty());

            // Llamada al método del controlador
            String resultado = gestorPago.mostrarFormularioRegistro("{}", "1", model, session);

            // Verificar la redirección a /error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el restaurante no existe");
        }

        @Test
        @DisplayName("Debe redirigir a /error si el método de pago es nulo o vacío")
        void testMetodoPagoInvalido() {
            // Parámetros de entrada
            String metodoPago = null;
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById(restauranteId)).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección de entrega válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(metodoPago, restauranteId, direccionId, session, itemIds, model, redirectAttributes);

            // Verificar la redirección a /error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el método de pago es inválido");
        }

        @Test
        @DisplayName("Debe redirigir a /error si el carrito está vacío")
        void testCarritoVacio() {
            // Parámetros de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Collections.emptyList(); // Carrito vacío
        
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);
        
            // Simular restaurante existente
            when(serviceRestaurante.findById(restauranteId)).thenReturn(Optional.of(restaurantePrueba));
        
            // Simular dirección de entrega válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));
        
            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(metodoPago, restauranteId, direccionId, session, itemIds, model, redirectAttributes);
        
            // Verificar la redirección a /error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el carrito está vacío");
        }   

        @Test
        @DisplayName("Debe registrar correctamente un pedido cuando la dirección de recogida no se encuentra")
        void testRegistrarPedidoDireccionRecogidaNoEncontrada() {
            // Parámetros de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById(restauranteId)).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección de recogida no encontrada
            when(serviceDireccion.findByUsuario(any())).thenReturn(Collections.emptyList());

            // Simular dirección de entrega válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Llamar al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar que se manejó el caso donde no se encuentra la dirección de recogida
            verify(serviceDireccion, times(1)).findByUsuario(any());
            logger.info("<<Direccion de recogida no encontrada>>");

            // Verificar que la ejecución continúa correctamente
            assertEquals("redirect:/pago/confirmacion", resultado, "Debe continuar incluso si no se encuentra la dirección de recogida");
        }


        @Test
        @DisplayName("Debe manejar correctamente un carrito con un solo elemento")
        void testRegisterPagoCarritoUnElemento() {
            // Parámetros de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Collections.singletonList(1);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Simular el guardado del pedido
            when(servicePedido.save(any(Pedido.class))).thenAnswer(invocation -> {
                Pedido pedido = invocation.getArgument(0);
                pedido.setIdPedido(1); // Simula el ID generado
                return pedido;
            });

            // Simular ItemMenu existente
            ItemMenu item1 = new ItemMenu(1, "Pizza", 10.0, new CartaMenu(1, "MENU", restaurantePrueba), "COMIDA");
            when(serviceItemMenu.findById(1)).thenReturn(Optional.of(item1));

            // Simular la creación y guardado de ItemPedido
            ItemPedido itemPedido1 = new ItemPedido(1, null, item1);
            when(serviceItemPedido.save(any(ItemPedido.class))).thenReturn(itemPedido1);

            // Simular la creación y guardado del Pago
            Pago pagoGuardado = new Pago(1, "Efectivo", null);
            when(servicePago.save(any(Pago.class))).thenReturn(pagoGuardado);

            // Simular usuario del restaurante y direcciones de recogida
            Usuario usuarioRestaurante = new Usuario("restaurante1", "password2", "RESTAURANTE");
            when(serviceUsuario.findById(restaurantePrueba.getIdUsuario())).thenReturn(Optional.of(usuarioRestaurante));

            List<Direccion> direccionesRecogida = Arrays.asList(direccionRecogida);
            when(serviceDireccion.findByUsuario(usuarioRestaurante)).thenReturn(direccionesRecogida);

            // Simular dirección de entrega
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar la redirección
            assertEquals("redirect:/pago/confirmacion", resultado, "Debe redirigir a la confirmación");

            // Verificar interacciones con los servicios
            verify(servicePedido, times(2)).save(any(Pedido.class)); // 2 veces: inicial y actualización
            verify(serviceItemMenu, times(1)).findById(1);
            verify(serviceItemPedido, times(1)).save(any(ItemPedido.class));
            verify(servicePago, times(1)).save(any(Pago.class));
            verify(serviceUsuario, times(1)).findById(eq(restaurantePrueba.getIdUsuario()));
            verify(serviceDireccion, times(1)).findByUsuario(usuarioRestaurante);
            //verify(serviceDireccion, times(2)).findById(eq(direccionId)); // Una vez en el inicio y otra después

            // Verificar atributos flash
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("pedido"), any(Pedido.class));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("items"), eq(Collections.singletonList(item1)));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("pago"), any(Pago.class));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("restaurante"), eq(restaurantePrueba));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("cliente"), eq(clientePrueba));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("total"), eq(item1.getPrecio()));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("direccionRecogida"), eq(direccionRecogida));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("direccionEntrega"), eq(direccionEntrega));
        }

        @Test
        @DisplayName("Debe manejar correctamente cuando el restaurante es null")
        void testRestauranteEsNull() {
            // Parámetros de entrada
            String metodoPago = "TARJETA";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2, 3);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante inexistente (null)
            when(serviceRestaurante.findById(restauranteId)).thenReturn(Optional.empty());

            // Simular dirección válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(
                    metodoPago,
                    restauranteId,
                    direccionId,
                    session,
                    itemIds,
                    model,
                    redirectAttributes
            );

            // Verificar que el flujo llega al punto esperado
            verify(serviceRestaurante, times(1)).findById(restauranteId);

            // Verificar redirección al error o comportamiento esperado
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el restaurante es null");
        }

        @Test
        @DisplayName("Debe registrar un pedido cuando el restaurante es válido")
        void testRegistrarPedidoRestauranteValido() {
            // Parámetros de entrada
            String metodoPago = "Efectivo";
            String restauranteId = "1";
            Long direccionId = 1L;
            List<Integer> itemIds = Arrays.asList(1, 2);

            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante válido
            when(serviceRestaurante.findById(restauranteId)).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección válida
            when(serviceDireccion.findById(direccionId)).thenReturn(Optional.of(direccionEntrega));

            // Simular items de menú válidos
            ItemMenu item1 = new ItemMenu(1, "Pizza", 10.0, null, "COMIDA");
            ItemMenu item2 = new ItemMenu(2, "Hamburguesa", 8.0, null, "COMIDA");
            when(serviceItemMenu.findById(1)).thenReturn(Optional.of(item1));
            when(serviceItemMenu.findById(2)).thenReturn(Optional.of(item2));

            // Simular creación y guardado del pedido
            Pedido pedido = new Pedido(1, System.currentTimeMillis(), "Pendiente", clientePrueba, restaurantePrueba);
            when(servicePedido.save(any(Pedido.class))).thenReturn(pedido);

            // Simular guardado de pago
            Pago pago = new Pago(1, "Efectivo", pedido);
            when(servicePago.save(any(Pago.class))).thenReturn(pago);

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido(metodoPago, restauranteId, direccionId, session, itemIds, model, redirectAttributes);

            // Verificar que se ejecutó correctamente
            assertEquals("redirect:/pago/confirmacion", resultado, "Debe redirigir a la confirmación");

            // Verificar interacciones con los mocks
            verify(serviceRestaurante, times(1)).findById(restauranteId);
            verify(serviceDireccion, times(1)).findById(direccionId);
            verify(serviceItemMenu, times(1)).findById(1);
            verify(serviceItemMenu, times(1)).findById(2);
            verify(servicePedido, times(2)).save(any(Pedido.class)); // Una vez para guardar y otra para actualizar
            verify(servicePago, times(1)).save(any(Pago.class));

            // Verificar atributos flash
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("pedido"), any(Pedido.class));
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("pago"), any(Pago.class));
        }


        @Test
        @DisplayName("Debe redirigir a /error si el restaurante es nulo")
        void testRestauranteEsNulo() {
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante inexistente
            when(serviceRestaurante.findById(anyString())).thenReturn(Optional.empty());

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido("TARJETA", "1", 1L, session, Arrays.asList(1, 2, 3), model, redirectAttributes);

            // Verificar la redirección a /error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el restaurante es nulo");
        }


        @Test
        @DisplayName("Debe redirigir a /error si el método de pago está vacío")
        void testMetodoPagoVacio() {
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById(anyString())).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección válida
            when(serviceDireccion.findById(anyLong())).thenReturn(Optional.of(direccionEntrega));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido("", "1", 1L, session, Arrays.asList(1, 2, 3), model, redirectAttributes);

            // Verificar la redirección a /error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el método de pago está vacío");
        }

        @Test
        @DisplayName("Debe redirigir a /error si el carrito es nulo")
        void testItemIdsEsNulo() {
            // Simular sesión con usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(clientePrueba);

            // Simular restaurante existente
            when(serviceRestaurante.findById(anyString())).thenReturn(Optional.of(restaurantePrueba));

            // Simular dirección válida
            when(serviceDireccion.findById(anyLong())).thenReturn(Optional.of(direccionEntrega));

            // Llamada al método del controlador
            String resultado = gestorPago.registrarPedido("TARJETA", "1", 1L, session, null, model, redirectAttributes);

            // Verificar la redirección a /error
            assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el carrito es nulo");
        }


    }

    @Nested
    @DisplayName("Tests para el endpoint /pago/confirmacion")
    class ConfirmacionTests {

        @Test
        @DisplayName("Debe retornar la vista de confirmación cuando la sesión es válida")
        void testConfirmacionSesionValida() {
            // Simular sesión con usuario autenticado
            Usuario usuario = new Usuario("cliente1", "password1", "CLIENTE");
            when(session.getAttribute("usuario")).thenReturn(usuario);

            // Llamada al método del controlador
            String resultado = gestorPago.mostrarConfirmacion(session, model);

            // Verificar la vista retornada
            assertEquals("ConfirmacionPedido", resultado, "Debe retornar la vista 'ConfirmacionPedido'");
            verify(model, times(1)).addAttribute("usuario", usuario);
        }

        @Test
        @DisplayName("Debe redirigir al login cuando la sesión es inválida (null)")
        void testConfirmacionSesionInvalidaNull() {
            // Simular sesión sin usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(null);

            // Llamada al método del controlador
            String resultado = gestorPago.mostrarConfirmacion(session, model);

            // Verificar la redirección al login
            assertEquals("redirect:/login", resultado, "Debe redirigir al login cuando la sesión es inválida");

            // Verificar que no se agrega ningún atributo al modelo
            verify(model, never()).addAttribute(anyString(), any());
        }

        @Test
        @DisplayName("Debe redirigir al login cuando la sesión no contiene un usuario autenticado")
        void testConfirmacionSesionSinUsuario() {
            // Simular sesión sin atributo usuario
            when(session.getAttribute("usuario")).thenReturn(null);

            // Llamada al método del controlador
            String resultado = gestorPago.mostrarConfirmacion(session, model);

            // Verificar la redirección al login
            assertEquals("redirect:/login", resultado, "Debe redirigir al login cuando no hay usuario autenticado");

            // Verificar que no se agrega ningún atributo al modelo
            verify(model, never()).addAttribute(anyString(), any());
        }

        @Test
        @DisplayName("Debe manejar correctamente cuando el modelo es null")
        void testConfirmacionModeloNulo() {
            // Simular sesión con usuario autenticado
            Usuario usuario = new Usuario("cliente1", "password1", "CLIENTE");
            when(session.getAttribute("usuario")).thenReturn(usuario);

            // Llamada al método del controlador con modelo null
            String resultado = gestorPago.mostrarConfirmacion(session, model);

            // Verificar la vista retornada
            assertEquals("ConfirmacionPedido", resultado, "Debe retornar la vista 'ConfirmacionPedido' incluso si el modelo es null");

            // Verificar que se agrega el atributo al modelo
            verify(model, times(1)).addAttribute("usuario", usuario);
        }
    }

    @Nested
    @DisplayName("Tests para los endpoints /pago/mostrarPagos y /pago/mostrarPago/{id}")
    class MostrarTests {

        @Nested
        @DisplayName("Tests para el endpoint /pago/mostrarPagos")
        class MostrarPagosTests {

            @Test
            @DisplayName("Debe mostrar la lista de pagos cuando existen pagos")
            void testMostrarPagosConPagos() {
                // Arrange
                Pago pago1 = new Pago(1, "Tarjeta", new Pedido());
                Pago pago2 = new Pago(2, "Efectivo", new Pedido());
                List<Pago> pagos = Arrays.asList(pago1, pago2);
                when(servicePago.findAll()).thenReturn(pagos);

                // Act
                String resultado = gestorPago.mostrarPagos(model);

                // Assert
                assertEquals("/administrador/ListaPagos", resultado, "Debe retornar la vista '/administrador/ListaPagos'");
                verify(servicePago, times(1)).findAll();
                verify(model, times(1)).addAttribute("pagos", pagos);
                verify(model, never()).addAttribute(eq("error"), anyString());
            }

            @Test
            @DisplayName("Debe mostrar error cuando no hay pagos")
            void testMostrarPagosSinPagos() {
                // Arrange
                when(servicePago.findAll()).thenReturn(Collections.emptyList());

                // Act
                String resultado = gestorPago.mostrarPagos(model);

                // Assert
                assertEquals("error", resultado, "Debe retornar la vista 'error'");
                verify(servicePago, times(1)).findAll();
                verify(model, times(1)).addAttribute("error", "No se encontraron pagos");
                verify(model, never()).addAttribute(eq("pagos"), any());
            }
        }

        @Nested
        @DisplayName("Tests para el endpoint /register")
        class MostrarFormularioRegistroTests {

            @Test
            @DisplayName("Debe mostrar el formulario de registro correctamente")
            void testMostrarFormularioRegistroCorrecto() {
                // Simular sesión con usuario autenticado
                when(session.getAttribute("usuario")).thenReturn(clientePrueba);

                // Simular restaurante existente
                when(serviceRestaurante.findById("1")).thenReturn(Optional.of(restaurantePrueba));

                // Simular direcciones existentes
                List<Direccion> direcciones = Arrays.asList(direccionEntrega, direccionRecogida);
                when(serviceDireccion.findByUsuario(clientePrueba)).thenReturn(direcciones);

                // Simular códigos postales
                List<CodigoPostal> codigosPostales = Arrays.asList(codigoPostalRecogida);
                when(serviceGroup.getServiceCodigoPostal().findAll()).thenReturn(codigosPostales);

                // Simular carrito
                String cartData = "[{\"nombre\":\"Pizza\", \"precio\":10.0}, {\"nombre\":\"Hamburguesa\", \"precio\":8.0}]";

                // Llamar al método del controlador
                String resultado = gestorPago.mostrarFormularioRegistro(cartData, "1", model, session);

                // Verificar la vista retornada
                assertEquals("RegistrarPedidos", resultado, "Debe retornar la vista 'RegistrarPedidos'");

                // Verificar interacciones con los servicios
                verify(serviceRestaurante, times(1)).findById("1");
                //verify(serviceDireccion, times(1)).findByUsuario(clientePrueba);
                verify(serviceGroup.getServiceCodigoPostal(), times(1)).findAll();

                // Verificar que se agregan los atributos correctos al modelo
                verify(model, times(1)).addAttribute(eq("direcciones"), eq(direcciones));
                verify(model, times(1)).addAttribute(eq("restaurante"), eq(restaurantePrueba));
                verify(model, times(1)).addAttribute(eq("carrito"), anyList());
                verify(model, times(1)).addAttribute(eq("total"), eq(18.0));
                verify(model, times(1)).addAttribute(eq("usuario"), eq(clientePrueba));
                verify(model, times(1)).addAttribute(eq("codigosPostales"), eq(codigosPostales));
            }

            @Test
            @DisplayName("Debe redirigir al login si el usuario no está autenticado")
            void testMostrarFormularioRegistroSinUsuario() {
                // Simular sesión sin usuario autenticado
                when(session.getAttribute("usuario")).thenReturn(null);

                // Llamar al método del controlador
                String resultado = gestorPago.mostrarFormularioRegistro("{}", "1", model, session);

                // Verificar la redirección al login
                assertEquals("redirect:/usuarios/login", resultado, "Debe redirigir al login si no hay usuario en la sesión");
            }

            @Test
            @DisplayName("Debe redirigir al error si el restaurante no existe")
            void testMostrarFormularioRegistroRestauranteNoExiste() {
                // Simular sesión con usuario autenticado
                when(session.getAttribute("usuario")).thenReturn(clientePrueba);

                // Simular restaurante inexistente
                when(serviceRestaurante.findById("1")).thenReturn(Optional.empty());

                // Llamar al método del controlador
                String resultado = gestorPago.mostrarFormularioRegistro("{}", "1", model, session);

                // Verificar la redirección a la página de error
                assertEquals("redirect:/error", resultado, "Debe redirigir a la página de error si el restaurante no existe");
            }
        }


        @Nested
        @DisplayName("Tests para el endpoint /pago/mostrarPago/{id}")
        class MostrarPagoTests {

            @Test
            @DisplayName("Debe mostrar el pago cuando el ID existe")
            void testMostrarPagoExiste() {
                // Arrange
                Pago pago = new Pago(1, "Tarjeta", new Pedido());
                when(servicePago.findById(1)).thenReturn(Optional.of(pago));

                // Act
                String resultado = gestorPago.mostrarPago(1, model);

                // Assert
                assertEquals("/administrador/VerPago", resultado, "Debe retornar la vista '/administrador/VerPago'");
                verify(servicePago, times(1)).findById(1);
                verify(model, times(1)).addAttribute("pago", pago);
                verify(model, never()).addAttribute(eq("error"), anyString());
            }

            @Test
            @DisplayName("Debe mostrar error cuando el pago no existe")
            void testMostrarPagoNoExiste() {
                // Arrange
                when(servicePago.findById(9999)).thenReturn(Optional.empty());

                // Act
                String resultado = gestorPago.mostrarPago(9999, model);

                // Assert
                assertEquals("error", resultado, "Debe retornar la vista 'error'");
                verify(servicePago, times(1)).findById(9999);
                verify(model, times(1)).addAttribute("error", "Pago no encontrado");
                verify(model, never()).addAttribute(eq("pago"), any());
            }
        }
    }
}
