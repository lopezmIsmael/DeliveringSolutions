package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.*;
import com.isoii.deliveringsolutions.dominio.service.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests para GestorRepartidor")
class GestorRepartidorTest {

    @InjectMocks
    private GestorRepartidor gestorRepartidor;

    @Mock
    private ServicePedido servicePedido;
    @Mock
    private ServiceDireccion serviceDireccion;
    @Mock
    private ServiceRepartidor serviceRepartidor;
    @Mock
    private ServiceServicioEntrega serviceServicioEntrega;
    @Mock
    private ServiceZona serviceZona;
    @Mock
    private ServiceZonaCodigoPostal serviceZonaCodigoPostal;

    @Mock
    private HttpSession session;
    @Mock
    private Model model;
    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/findAll -> findAll()
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/findAll -> findAll()")
    class FindAllTests {

        @Test
        @DisplayName("Devuelve lista de repartidores si hay datos en BD")
        void testFindAll_ConRepartidores() {
            Repartidor r1 = new Repartidor();
            Repartidor r2 = new Repartidor();
            List<Repartidor> mockRepartidores = List.of(r1, r2);
            when(serviceRepartidor.findAll()).thenReturn(mockRepartidores);

            List<Repartidor> resultado = gestorRepartidor.findAll();

            assertEquals(2, resultado.size());
            verify(serviceRepartidor).findAll();
        }

        @Test
        @DisplayName("Devuelve lista vacía si no hay repartidores en BD")
        void testFindAll_SinRepartidores() {
            when(serviceRepartidor.findAll()).thenReturn(Collections.emptyList());

            List<Repartidor> resultado = gestorRepartidor.findAll();

            assertTrue(resultado.isEmpty());
            verify(serviceRepartidor).findAll();
        }
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/register -> mostrarFormularioRegistro(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/register -> mostrarFormularioRegistro(...)")
    class MostrarFormularioRegistroTests {

        @Test
        @DisplayName("Muestra la vista de registro y carga las zonas en el modelo")
        void testMostrarFormularioRegistro() {
            Zona z1 = new Zona();
            Zona z2 = new Zona();
            when(serviceZona.findAll()).thenReturn(List.of(z1, z2));

            String vista = gestorRepartidor.mostrarFormularioRegistro(model);

            assertEquals("Pruebas-RegisterRepartidor", vista);
            verify(model).addAttribute(eq("zonas"), eq(List.of(z1, z2)));
        }
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/findById/{id} -> findById(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/findById/{id} -> findById(...)")
    class FindByIdTests {

        @Test
        @DisplayName("Retorna repartidor si existe el id")
        void testFindById_Existe() {
            Repartidor repartidor = new Repartidor();
            when(serviceRepartidor.findById("rep1")).thenReturn(Optional.of(repartidor));

            Repartidor resultado = gestorRepartidor.findById("rep1");

            assertNotNull(resultado);
            verify(serviceRepartidor).findById("rep1");
        }

        @Test
        @DisplayName("Retorna null si no existe el id")
        void testFindById_NoExiste() {
            when(serviceRepartidor.findById("rep2")).thenReturn(Optional.empty());

            Repartidor resultado = gestorRepartidor.findById("rep2");

            assertNull(resultado);
            verify(serviceRepartidor).findById("rep2");
        }
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/login -> mostrarFormularioLogin(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/login -> mostrarFormularioLogin(...)")
    class MostrarFormularioLoginTests {

        @Test
        @DisplayName("Muestra error si no hay repartidor en sesión")
        void testLogin_SinRepartidor() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String vista = gestorRepartidor.mostrarFormularioLogin(model, session);

            assertEquals("error", vista);
            verify(model).addAttribute(eq("error"), anyString());
        }

        @Test
        @DisplayName("Muestra pedidos pendientes si hay repartidor en sesión")
        void testLogin_ConRepartidor() {
            Repartidor repartidor = new Repartidor();
            repartidor.setZona(new Zona());
            repartidor.getZona().setId(1);
            when(session.getAttribute("usuario")).thenReturn(repartidor);

            // Simulamos zona-códigos postales
            ZonaCodigoPostal zcp = new ZonaCodigoPostal();
            zcp.setZona(repartidor.getZona());
            CodigoPostal cp = new CodigoPostal();
            zcp.setCodigoPostal(cp);
            when(serviceZonaCodigoPostal.findAll()).thenReturn(List.of(zcp));

            // Simulamos pedido en "Pagado" con un cliente que tiene una dirección
            Pedido pedidoPagado = new Pedido();
            pedidoPagado.setEstadoPedido("Pagado");
            Cliente cliente = new Cliente();
            pedidoPagado.setCliente(cliente);

            Direccion dir = new Direccion();
            dir.setCodigoPostal(cp);
            when(serviceDireccion.findByUsuario(cliente)).thenReturn(List.of(dir));

            // Otro pedido con estado distinto
            Pedido pedidoNoPagado = new Pedido();
            pedidoNoPagado.setEstadoPedido("Pendiente");

            when(servicePedido.findAll()).thenReturn(List.of(pedidoPagado, pedidoNoPagado));

            String vista = gestorRepartidor.mostrarFormularioLogin(model, session);

            assertEquals("GestorRepartidor", vista);

            // Verifica que en el modelo haya 'pedidosPendientes' con el pedido pagado
            @SuppressWarnings("unchecked")
            ArgumentCaptor<Map<Pedido, Direccion>> mapCaptor = ArgumentCaptor.forClass(Map.class);
            verify(model).addAttribute(eq("pedidosPendientes"), mapCaptor.capture());
            Map<Pedido, Direccion> pedidosPendientes = mapCaptor.getValue();

            assertEquals(1, pedidosPendientes.size());
            assertTrue(pedidosPendientes.containsKey(pedidoPagado));
            assertEquals(dir, pedidosPendientes.get(pedidoPagado));
        }
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/gestionar/{id} -> gestionarPedido(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/gestionar/{id} -> gestionarPedido(...)")
    class GestionarPedidoTests {

        @Test
        @DisplayName("Retorna error si no se encuentra el pedido")
        void testGestionarPedido_NoExiste() {
            when(servicePedido.findById(999)).thenReturn(Optional.empty());

            String vista = gestorRepartidor.gestionarPedido(999, model);

            assertEquals("error", vista);
            verify(model).addAttribute(eq("error"), anyString());
        }

        @Test
        @DisplayName("Muestra la vista con pedido y dirección si existe")
        void testGestionarPedido_Existe() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(10);
            Cliente cliente = new Cliente();
            pedido.setCliente(cliente);

            Direccion dir = new Direccion();

            when(servicePedido.findById(10)).thenReturn(Optional.of(pedido));
            when(serviceDireccion.findByUsuario(cliente)).thenReturn(List.of(dir));

            String vista = gestorRepartidor.gestionarPedido(10, model);

            assertEquals("GestionPedido", vista);
            verify(model).addAttribute("pedido", pedido);
            verify(model).addAttribute("direccion", dir);
        }
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/calcularTiempos/{id} -> calcularTiempos(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/calcularTiempos/{id} -> calcularTiempos(...)")
    class CalcularTiemposTests {

        @Test
        @DisplayName("Muestra error si no se encuentra un ServicioEntrega para ese pedido")
        void testCalcularTiempos_ServicioInexistente() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(1);
            when(servicePedido.findById(1)).thenReturn(Optional.of(pedido));
            when(serviceServicioEntrega.findAll()).thenReturn(Collections.emptyList());

            String vista = gestorRepartidor.calcularTiempos(1, model, session);

            assertEquals("CalcularTiempos", vista);
            verify(model).addAttribute(eq("error"), anyString());
        }

        @Test
        @DisplayName("Calcula tiempos y actualiza eficiencia del repartidor si hay servicioEntrega")
        void testCalcularTiempos_Exito() {
            // Simulamos un pedido
            Pedido pedido = new Pedido();
            pedido.setIdPedido(10);
            pedido.setFecha(1000L); // supongamos la fecha del pedido

            // Simulamos un repartidor en sesión
            Repartidor repartidor = new Repartidor();
            when(session.getAttribute("usuario")).thenReturn(repartidor);

            // Simulamos un servicioEntrega que coincide con el pedido
            ServicioEntrega se1 = new ServicioEntrega();
            se1.setPedido(pedido);
            se1.setFechaRecepcion(1000L); // 1 seg
            se1.setFechaEntrega(1000L + (45 * 60 * 1000)); // 45 min después

            when(servicePedido.findById(10)).thenReturn(Optional.of(pedido));
            when(serviceServicioEntrega.findAll()).thenReturn(List.of(se1));

            // Ejecución
            String vista = gestorRepartidor.calcularTiempos(10, model, session);

            assertEquals("CalcularTiempos", vista);

            // Verificamos que el repartidor se guardó con eficiencia calculada
            verify(serviceRepartidor).save(repartidor);
            // Por 45 minutos -> corresponde a 4 (tiempo < 60min)
            assertEquals(4, repartidor.getEficiencia());

            // Verificar que el model contenga los atributos
            verify(model).addAttribute("pedido", pedido);
            verify(model).addAttribute("repartidor", repartidor);
            verify(model).addAttribute("servicioEntrega", se1);
        }
    }

    // --------------------------------------------------------------------------------------
    // POST /repartidores/registrarRepartidor -> registrarRepartidor(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("POST /repartidores/registrarRepartidor -> registrarRepartidor(...)")
    class RegistrarRepartidorTests {

        @Test
        @DisplayName("Redirige a /repartidores/register si pass es nula, vacía, DNI != 9, o pass < 6 chars")
        void testRegistrarRepartidor_DatosInvalidos() {
            Repartidor repartidor = new Repartidor();
            repartidor.setPass("");         // Pass vacía
            repartidor.setDni("12345");     // Menos de 9 chars
            String result = gestorRepartidor.registrarRepartidor(repartidor, redirectAttributes);

            assertEquals("redirect:/repartidores/register", result);
            verify(redirectAttributes).addFlashAttribute(eq("error"), anyString());
            verify(serviceRepartidor, never()).save(any(Repartidor.class));
        }

        @Test
        @DisplayName("Registra repartidor con datos correctos")
        void testRegistrarRepartidor_Exito() {
            Repartidor repartidor = new Repartidor();
            repartidor.setPass("123456");
            repartidor.setDni("123456789"); // 9 chars

            when(serviceRepartidor.save(repartidor)).thenReturn(repartidor);

            String result = gestorRepartidor.registrarRepartidor(repartidor, redirectAttributes);

            assertEquals("redirect:/", result);
            verify(serviceRepartidor).save(repartidor);
            verify(redirectAttributes).addFlashAttribute(eq("success"), anyString());
        }
    }

    // --------------------------------------------------------------------------------------
    // POST /repartidores/actualizarEstado/{id} -> actualizarEstadoPedido(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("POST /repartidores/actualizarEstado/{id} -> actualizarEstadoPedido(...)")
    class ActualizarEstadoPedidoTests {

        @Test
        @DisplayName("Redirige con error si el pedido no existe")
        void testActualizarEstadoPedido_NoExiste() {
            when(servicePedido.findById(999)).thenReturn(Optional.empty());

            String result = gestorRepartidor.actualizarEstadoPedido(999, "Entregado", redirectAttributes, session);

            assertEquals("redirect:/repartidores/gestionar/999", result);
            verify(redirectAttributes).addFlashAttribute(eq("error"), anyString());
        }

        @Test
        @DisplayName("Actualiza estado y redirige a calcularTiempos si estado es 'Entregado'")
        void testActualizarEstadoPedido_Entregado() {
            // Pedido existente
            Pedido pedido = new Pedido();
            pedido.setEstadoPedido("Pagado");
            pedido.setIdPedido(10);
            pedido.setFecha(1000L);
            when(servicePedido.findById(10)).thenReturn(Optional.of(pedido));

            // Repartidor en sesión
            Repartidor rep = new Repartidor();
            when(session.getAttribute("usuario")).thenReturn(rep);

            // Ejecución
            String result = gestorRepartidor.actualizarEstadoPedido(10, "Entregado", redirectAttributes, session);

            assertEquals("redirect:/repartidores/calcularTiempos/10", result);
            verify(servicePedido).save(pedido);

            // Se debe crear un nuevo ServicioEntrega y guardarse
            ArgumentCaptor<ServicioEntrega> seCaptor = ArgumentCaptor.forClass(ServicioEntrega.class);
            verify(serviceServicioEntrega).save(seCaptor.capture());
            ServicioEntrega se = seCaptor.getValue();
            assertSame(pedido, se.getPedido());
            assertSame(rep, se.getRepartidor());
            // Se sets fechaEntrega = now, fechaRecepcion = pedido.getFecha()
            assertEquals(pedido.getFecha(), se.getFechaRecepcion());
            assertTrue(se.getFechaEntrega() > 0);
        }

        @Test
        @DisplayName("Actualiza estado y redirige a gestionar si no es 'Entregado'")
        void testActualizarEstadoPedido_NoEntregado() {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(11);
            when(servicePedido.findById(11)).thenReturn(Optional.of(pedido));

            // Llamada con estado "En reparto" (por ejemplo)
            String result = gestorRepartidor.actualizarEstadoPedido(11, "En reparto", redirectAttributes, session);

            assertEquals("redirect:/repartidores/gestionar/11", result);
            verify(servicePedido).save(pedido);
            verify(serviceServicioEntrega, never()).save(any(ServicioEntrega.class));
            verify(redirectAttributes).addFlashAttribute(eq("success"), anyString());
        }
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/mostrarRepartidores -> mostrarRepartidores(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/mostrarRepartidores -> mostrarRepartidores(...)")
    class MostrarRepartidoresTests {

        @Test
        @DisplayName("Muestra lista de repartidores si existen")
        void testMostrarRepartidores_Existen() {
            Repartidor r1 = new Repartidor();
            Repartidor r2 = new Repartidor();
            List<Repartidor> lista = List.of(r1, r2);
            when(serviceRepartidor.findAll()).thenReturn(lista);

            String vista = gestorRepartidor.mostrarRepartidores(model);

            assertEquals("/administrador/ListaRepartidores", vista);
            verify(model).addAttribute("repartidores", lista);
        }

        @Test
        @DisplayName("Muestra error si no hay repartidores")
        void testMostrarRepartidores_Vacio() {
            when(serviceRepartidor.findAll()).thenReturn(Collections.emptyList());

            String vista = gestorRepartidor.mostrarRepartidores(model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "No se encontraron repartidores");
        }
    }

    // --------------------------------------------------------------------------------------
    // GET /repartidores/mostrarRepartidor/{id} -> mostrarRepartidor(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /repartidores/mostrarRepartidor/{id} -> mostrarRepartidor(...)")
    class MostrarRepartidorTests {

        @Test
        @DisplayName("Muestra repartidor si el id existe")
        void testMostrarRepartidor_Existe() {
            Repartidor rep = new Repartidor();
            when(serviceRepartidor.findById("rep1")).thenReturn(Optional.of(rep));

            String vista = gestorRepartidor.mostrarRepartidor("rep1", model);

            assertEquals("/administrador/VerRepartidor", vista);
            verify(model).addAttribute("repartidor", rep);
        }

        @Test
        @DisplayName("Muestra error si el repartidor no existe")
        void testMostrarRepartidor_NoExiste() {
            when(serviceRepartidor.findById("rep2")).thenReturn(Optional.empty());

            String vista = gestorRepartidor.mostrarRepartidor("rep2", model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "Repartidor no encontrado");
        }
    }
}

