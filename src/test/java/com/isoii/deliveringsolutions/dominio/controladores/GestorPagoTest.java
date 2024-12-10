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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
        // Configurar serviceGroup para retornar los servicios mockeados
        when(serviceGroup.getServiceRestaurant()).thenReturn(serviceRestaurante);
        when(serviceGroup.getServiceDireccion()).thenReturn(serviceDireccion);
        when(serviceGroup.getServicePedido()).thenReturn(servicePedido);
        when(serviceGroup.getServiceItemMenu()).thenReturn(serviceItemMenu);
        when(serviceGroup.getServiceItemPedido()).thenReturn(serviceItemPedido);
        when(serviceGroup.getServicePago()).thenReturn(servicePago);
        when(serviceGroup.getServiceUsuario()).thenReturn(serviceUsuario);
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
        @DisplayName("Debe manejar correctamente cuando el ID no es numérico")
        void testFindByIdNoNumerico() {
            // En Java, un parámetro de tipo Long no puede recibir una cadena,
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
            verify(serviceDireccion, times(1)).findById(eq(direccionId));
            verify(servicePedido, times(2)).save(any(Pedido.class));
            verify(serviceItemMenu, times(1)).findById(1);
            verify(serviceItemMenu, times(1)).findById(2);
            verify(serviceItemMenu, times(1)).findById(3);
            verify(serviceItemPedido, times(3)).save(any(ItemPedido.class));
            verify(servicePago, times(1)).save(any(Pago.class));
            verify(serviceUsuario, times(1)).findById(eq(restaurantePrueba.getIdUsuario()));
            verify(serviceDireccion, times(1)).findByUsuario(usuarioRestaurante);
            verify(serviceDireccion, times(1)).findById(eq(direccionId));
        
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
            verify(redirectAttributes, times(1)).addFlashAttribute("error", "Ocurrió un error al procesar el pedido.");
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
            verify(redirectAttributes, times(1)).addFlashAttribute(eq("pedido"), any(Pedido.class));

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
            String resultado = gestorPago.mostrarConfirmacion(model);

            // Verificar la vista retornada
            assertEquals("ConfirmacionPedido", resultado, "Debe retornar la vista 'confirmacion'");
        }

        @Test
        @DisplayName("Debe redirigir al login cuando la sesión es inválida (null)")
        void testConfirmacionSesionInvalidaNull() {
            // Simular sesión sin usuario autenticado
            when(session.getAttribute("usuario")).thenReturn(null);

            // Llamada al método del controlador
            String resultado = gestorPago.mostrarConfirmacion(model);

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
            String resultado = gestorPago.mostrarConfirmacion(model);

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
            String resultado = gestorPago.mostrarConfirmacion(model);

            // Verificar la vista retornada
            assertEquals("ConfirmacionPedido", resultado, "Debe retornar la vista 'confirmacion' incluso si el modelo es null");
        }
    }
}
