package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.*;
import com.isoii.deliveringsolutions.dominio.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests para GestorCliente")
class GestorClienteTest {

    @InjectMocks
    private GestorCliente gestorCliente;

    // Mocks de los servicios
    @Mock
    private ServiceClient serviceClient;
    @Mock
    private ServiceRestaurant serviceRestaurant;
    @Mock
    private ServiceCartaMenu serviceCartaMenu;
    @Mock
    private ServiceDireccion serviceDireccion;
    @Mock
    private ServicePedido servicePedido;

    // Mocks de infrastructura web
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
    //                                GET /clientes/findAll
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/findAll -> findAll()")
    class FindAllTests {

        @Test
        @DisplayName("Retorna lista con clientes cuando hay datos en BD")
        void testFindAll_BBDDConClientes() {
            List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
            when(serviceClient.findAll()).thenReturn(clientes);

            List<Cliente> result = gestorCliente.findAll();

            assertEquals(2, result.size());
            verify(serviceClient).findAll();
        }

        @Test
        @DisplayName("Retorna lista vacía cuando no hay clientes en BD")
        void testFindAll_BBDDSinClientes() {
            when(serviceClient.findAll()).thenReturn(Collections.emptyList());

            List<Cliente> result = gestorCliente.findAll();

            assertTrue(result.isEmpty());
            verify(serviceClient).findAll();
        }
    }

    // --------------------------------------------------------------------------------------
    //                              GET /clientes/register
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/register -> mostrarFormularioRegistro()")
    class MostrarFormularioRegistroTests {

        @Test
        @DisplayName("Debe retornar la vista de registro de clientes")
        void testMostrarFormularioRegistro() {
            String vista = gestorCliente.mostrarFormularioRegistro();
            assertEquals("Pruebas-RegisterClient", vista);
        }
    }

    // --------------------------------------------------------------------------------------
    //                         GET /clientes/findById/{id} -> findById()
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/findById/{id} -> findById()")
    class FindByIdTests {

        @Test
        @DisplayName("Retorna cliente cuando el ID existe")
        void testFindById_ValidId() {
            Cliente cliente = new Cliente();
            when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));

            Cliente result = gestorCliente.findById("1");

            assertNotNull(result);
            verify(serviceClient).findById("1");
        }

        @Test
        @DisplayName("Retorna null cuando el ID no existe")
        void testFindById_IdNoExistente() {
            when(serviceClient.findById("999")).thenReturn(Optional.empty());

            Cliente result = gestorCliente.findById("999");

            assertNull(result);
        }

        @Test
        @DisplayName("Retorna null cuando el ID es inválido (p.ej. -1)")
        void testFindById_IdInvalido() {
            when(serviceClient.findById("-1")).thenReturn(Optional.empty());

            Cliente result = gestorCliente.findById("-1");

            assertNull(result);
        }

        @Test
        @DisplayName("Retorna null cuando el ID es nulo")
        void testFindById_IdNulo() {
            when(serviceClient.findById(null)).thenReturn(Optional.empty());

            Cliente result = gestorCliente.findById(null);

            assertNull(result);
            verify(serviceClient).findById(null);
        }
    }

    // --------------------------------------------------------------------------------------
    //                            GET /clientes/verRestaurantes
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/verRestaurantes -> verRestaurantes(...)")
    class VerRestaurantesTests {

        @Test
        @DisplayName("Muestra restaurantes favoritos si 'favoritos=true' y hay usuario en sesión")
        void testVerRestaurantes_FavoritosActivos() {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario("1");

            Cliente cliente = new Cliente();
            cliente.setIdUsuario("1");

            Restaurante restFavorito = new Restaurante();
            restFavorito.setIdUsuario("rest1");
            cliente.addFavorito(restFavorito);

            when(session.getAttribute("usuario")).thenReturn(usuario);
            when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));

            String result = gestorCliente.verRestaurantes("true", model, session);

            assertEquals("verRestaurantes", result);
            verify(model).addAttribute(eq("restaurantes"), anyList());
            verify(model).addAttribute("vistaFavoritos", true);
            verify(model).addAttribute("usuario", usuario);
            verify(model).addAttribute("cliente", cliente);
        }

        @Test
        @DisplayName("Redirige a / si no hay usuario en sesión al querer ver favoritos")
        void testVerRestaurantes_SessionNoIniciada() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String result = gestorCliente.verRestaurantes("true", model, session);

            assertEquals("redirect:/", result);
        }

        @Test
        @DisplayName("Muestra todos los restaurantes si 'favoritos=false'")
        void testVerRestaurantes_Todos() {
            List<Restaurante> restaurantes = Arrays.asList(new Restaurante(), new Restaurante());
            when(session.getAttribute("usuario")).thenReturn(new Usuario());
            when(serviceRestaurant.findAll()).thenReturn(restaurantes);

            String result = gestorCliente.verRestaurantes("false", model, session);

            assertEquals("verRestaurantes", result);
            verify(model).addAttribute("restaurantes", restaurantes);
            verify(model).addAttribute("vistaFavoritos", false);
        }

        @Test
        @DisplayName("Muestra todos los restaurantes por defecto si no se pasa el param 'favoritos'")
        void testVerRestaurantes_SinParametroFavoritos() {
            List<Restaurante> restaurantes = Arrays.asList(new Restaurante(), new Restaurante());
            when(serviceRestaurant.findAll()).thenReturn(restaurantes);

            // Simular un usuario cualquiera en sesión
            when(session.getAttribute("usuario")).thenReturn(new Usuario());

            String result = gestorCliente.verRestaurantes(null, model, session);

            assertEquals("verRestaurantes", result);
            verify(model).addAttribute("restaurantes", restaurantes);
            verify(model).addAttribute("vistaFavoritos", false);
        }
    }

    // --------------------------------------------------------------------------------------
    //                        GET /clientes/listar -> listarRestaurantes()
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/listar -> listarRestaurantes(...)")
    class ListarRestaurantesTests {

        @Test
        @DisplayName("Retorna 'verRestaurantes' mostrando lista de restaurantes")
        void testListarRestaurantes() {
            List<Restaurante> restaurantes = List.of(new Restaurante());
            when(serviceRestaurant.findAll()).thenReturn(restaurantes);

            String result = gestorCliente.listarRestaurantes(model);

            assertEquals("verRestaurantes", result);
            verify(model).addAttribute("restaurantes", restaurantes);
        }
    }

    // --------------------------------------------------------------------------------------
    //                      GET /clientes/filtrar -> filtrarRestaurantes(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/filtrar -> filtrarRestaurantes(...)")
    class FiltrarRestaurantesTests {

        @Test
        @DisplayName("Filtra restaurantes por nombre si se recibe un nombre no vacío")
        void testFiltrarRestaurantes_ConNombre() {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario("u1");

            Restaurante r1 = new Restaurante();
            r1.setNombre("Pizzeria la Roma");
            Restaurante r2 = new Restaurante();
            r2.setNombre("Hamburguesería Texas");
            Restaurante r3 = new Restaurante();
            r3.setNombre("Tacos Mexico");

            List<Restaurante> allRest = Arrays.asList(r1, r2, r3);

            when(session.getAttribute("usuario")).thenReturn(usuario);
            when(serviceRestaurant.findAll()).thenReturn(allRest);

            String result = gestorCliente.filtrarRestaurantes("pizzeria", model, session);

            assertEquals("verRestaurantes", result);
            verify(model).addAttribute("usuario", usuario);
            verify(model).addAttribute("vistaFavoritos", false);

            // Debe filtrar solo "Pizzeria la Roma" (ignora mayúsculas/minúsculas)
            @SuppressWarnings("unchecked")
            ArgumentCaptor<List<Restaurante>> captor = ArgumentCaptor.forClass(List.class);
            verify(model).addAttribute(eq("restaurantes"), captor.capture());
            List<Restaurante> filtrados = captor.getValue();
            assertEquals(1, filtrados.size());
            assertEquals("Pizzeria la Roma", filtrados.get(0).getNombre());
        }

        @Test
        @DisplayName("Retorna todos los restaurantes si el nombre es nulo o vacío")
        void testFiltrarRestaurantes_NombreVacioONulo() {
            List<Restaurante> allRest = List.of(new Restaurante(), new Restaurante());
            when(serviceRestaurant.findAll()).thenReturn(allRest);

            when(session.getAttribute("usuario")).thenReturn(new Usuario());

            String result = gestorCliente.filtrarRestaurantes("", model, session);

            assertEquals("verRestaurantes", result);
            verify(model).addAttribute("restaurantes", allRest);
        }
    }

    // --------------------------------------------------------------------------------------
    //               GET /clientes/verMenusRestaurante/{id} -> verMenusRestaurante(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/verMenusRestaurante/{id} -> verMenusRestaurante(...)")
    class VerMenusRestauranteTests {

        @Test
        @DisplayName("Redirige a '/' si no hay usuario en sesión")
        void testVerMenusRestaurante_UsuarioNoEnSesion() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String result = gestorCliente.verMenusRestaurante("rest1", model, session);

            assertEquals("redirect:/", result);
        }

        @Test
        @DisplayName("Muestra error si el restaurante no existe")
        void testVerMenusRestaurante_RestauranteNoExiste() {
            when(session.getAttribute("usuario")).thenReturn(new Usuario());
            when(serviceRestaurant.findById("rest1")).thenReturn(Optional.empty());

            String result = gestorCliente.verMenusRestaurante("rest1", model, session);

            assertEquals("error", result);
            verify(model).addAttribute("error", "Restaurante no encontrado");
        }

        @Test
        @DisplayName("Muestra error si el restaurante existe pero no hay menús")
        void testVerMenusRestaurante_SinMenus() {
            when(session.getAttribute("usuario")).thenReturn(new Usuario());

            Restaurante restaurante = new Restaurante();
            restaurante.setIdUsuario("rest1");
            when(serviceRestaurant.findById("rest1")).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenReturn(Collections.emptyList());

            String result = gestorCliente.verMenusRestaurante("rest1", model, session);

            assertEquals("error", result);
            verify(model).addAttribute("error", "No hay menús disponibles");
        }

        @Test
        @DisplayName("Retorna 'verMenusRestaurante' mostrando menús del restaurante")
        void testVerMenusRestaurante_Exito() {
            when(session.getAttribute("usuario")).thenReturn(new Usuario());

            Restaurante restaurante = new Restaurante();
            restaurante.setIdUsuario("rest1");
            restaurante.setNombre("MiRestaurante");

            CartaMenu menu1 = new CartaMenu();
            CartaMenu menu2 = new CartaMenu();
            when(serviceRestaurant.findById("rest1")).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenReturn(List.of(menu1, menu2));

            String result = gestorCliente.verMenusRestaurante("rest1", model, session);

            assertEquals("verMenusRestaurante", result);
            verify(model).addAttribute("restaurante", restaurante);
            verify(model).addAttribute("menus", List.of(menu1, menu2));
        }
    }

    // --------------------------------------------------------------------------------------
    //                   GET /clientes/editarDatos -> editarDatos(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/editarDatos -> editarDatos(...)")
    class EditarDatosTests {

        @Test
        @DisplayName("Muestra vista 'editarDatosCliente' con datos si hay usuario en sesión")
        void testEditarDatos_SesionValida() {
            Usuario usuario = new Usuario();
            when(session.getAttribute("usuario")).thenReturn(usuario);

            List<Direccion> direcciones = List.of(new Direccion());
            when(serviceDireccion.findByUsuario(usuario)).thenReturn(direcciones);

            String result = gestorCliente.editarDatos(model, session);

            assertEquals("editarDatosCliente", result);
            verify(model).addAttribute("usuario", usuario);
            verify(model).addAttribute("direcciones", direcciones);
            verify(model).addAttribute("cliente", usuario);
        }

        @Test
        @DisplayName("Si no hay usuario, se comporta similar pero con direcciones vacías")
        void testEditarDatos_SesionNoIniciada() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String result = gestorCliente.editarDatos(model, session);

            // Sigues recibiendo la vista, pero el "usuario" es null
            assertEquals("editarDatosCliente", result);
            verify(model).addAttribute("usuario", null);
            verify(model).addAttribute(eq("direcciones"), anyList());
        }
    }

    // --------------------------------------------------------------------------------------
    //                      GET /clientes/logout -> logout(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/logout -> logout(...)")
    class LogoutTests {

        @Test
        @DisplayName("Invalida sesión y redirige a '/' con mensaje flash")
        void testLogout() {
            RedirectAttributes mockRedirect = mock(RedirectAttributes.class);

            String result = gestorCliente.logout(session, mockRedirect);

            assertEquals("redirect:/", result);
            verify(session).invalidate();
            verify(mockRedirect).addFlashAttribute(eq("mensaje"), anyString());
        }
    }

    // --------------------------------------------------------------------------------------
    //                      GET /clientes/verPedidos -> verPedidos(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/verPedidos -> verPedidos(...)")
    class VerPedidosTests {

        @Test
        @DisplayName("Retorna pedidos del cliente si hay usuario en sesión")
        void testVerPedidos_SesionValida() {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario("cliente1");
            when(session.getAttribute("usuario")).thenReturn(usuario);

            // Configurar pedidos
            Cliente cliente = new Cliente();
            cliente.setIdUsuario("cliente1");

            Pedido pedido1 = new Pedido();
            pedido1.setCliente(cliente);
            Pedido pedido2 = new Pedido(); // Este no pertenece a "cliente1"
            pedido2.setCliente(null);

            when(servicePedido.findAll()).thenReturn(List.of(pedido1, pedido2));
            when(serviceClient.findById("cliente1")).thenReturn(Optional.of(cliente));

            String result = gestorCliente.verPedidos(model, session);

            assertEquals("verPedidosCliente", result);
            verify(model).addAttribute("cliente", cliente);

            @SuppressWarnings("unchecked")
            ArgumentCaptor<List<Pedido>> captor = ArgumentCaptor.forClass(List.class);
            verify(model).addAttribute(eq("pedidos"), captor.capture());
            List<Pedido> pedidosCapturados = captor.getValue();

            // Verificamos que se capturó solo pedido1
            assertEquals(1, pedidosCapturados.size());
            assertSame(pedido1, pedidosCapturados.get(0));
        }

        @Test
        @DisplayName("Redirige a '/' si no hay usuario en sesión")
        void testVerPedidos_SinSesion() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String result = gestorCliente.verPedidos(model, session);

            assertEquals("redirect:/", result);
        }

        @Test
        @DisplayName("Redirige a '/' si el cliente no se encuentra en BD")
        void testVerPedidos_ClienteNoEncontrado() {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario("1");
            when(session.getAttribute("usuario")).thenReturn(usuario);
            when(serviceClient.findById("1")).thenReturn(Optional.empty());

            String result = gestorCliente.verPedidos(model, session);

            assertEquals("redirect:/", result);
        }
    }

    // --------------------------------------------------------------------------------------
    //                POST /clientes/registrarCliente -> registrarCliente(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("POST /clientes/registrarCliente -> registrarCliente(...)")
    class RegistrarClienteTests {

        @Test
        @DisplayName("Registra cliente si la contraseña es válida")
        void testRegistrarCliente_ContraseñaValida() {
            Cliente cliente = new Cliente();
            cliente.setPass("1234");
            when(serviceClient.save(cliente)).thenReturn(cliente);

            String result = gestorCliente.registrarCliente(cliente);

            assertEquals("redirect:/", result);
            verify(serviceClient).save(cliente);
        }

        @Test
        @DisplayName("Redirige a /clientes/register si la contraseña es nula o vacía")
        void testRegistrarCliente_ContraseñaNula() {
            Cliente cliente = new Cliente();
            cliente.setPass(null);

            String result = gestorCliente.registrarCliente(cliente);

            assertEquals("redirect:/clientes/register", result);
            verify(serviceClient, never()).save(any());
        }
    }

    // --------------------------------------------------------------------------------------
    //                 DELETE /clientes/deleteById/{id} -> deleteById(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("DELETE /clientes/deleteById/{id} -> deleteById(...)")
    class DeleteByIdTests {

        @Test
        @DisplayName("Elimina cliente y retorna NO_CONTENT")
        void testDeleteById() {
            ResponseEntity<Void> response = gestorCliente.deleteById("1");

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(serviceClient).deleteById("1");
        }
    }

    // --------------------------------------------------------------------------------------
    //          POST /clientes/toggleFavorito/{id} -> toggleFavorito(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("POST /clientes/toggleFavorito/{id} -> toggleFavorito(...)")
    class ToggleFavoritoTests {

        @Test
        @DisplayName("Agrega o quita de favoritos si el restaurante existe y hay usuario en sesión")
        void testToggleFavorito_IdValido() {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario("1");

            Cliente cliente = new Cliente();
            cliente.setIdUsuario("1");

            Restaurante restaurante = new Restaurante();
            restaurante.setIdUsuario("rest1");

            when(session.getAttribute("usuario")).thenReturn(usuario);
            when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));
            when(serviceRestaurant.findById("rest1")).thenReturn(Optional.of(restaurante));

            String result = gestorCliente.toggleFavorito("rest1", session, null);

            assertEquals("redirect:/clientes/verRestaurantes", result);
            verify(serviceClient).save(cliente);
        }

        @Test
        @DisplayName("Redirige a '/' si no hay usuario en sesión")
        void testToggleFavorito_SesionNoIniciada() {
            when(session.getAttribute("usuario")).thenReturn(null);

            String result = gestorCliente.toggleFavorito("rest1", session, null);

            assertEquals("redirect:/", result);
        }

        @Test
        @DisplayName("No hace nada si el restaurante no existe")
        void testToggleFavorito_RestauranteNoExiste() {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario("1");

            Cliente cliente = new Cliente();
            cliente.setIdUsuario("1");

            when(session.getAttribute("usuario")).thenReturn(usuario);
            when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));
            when(serviceRestaurant.findById("rest1")).thenReturn(Optional.empty());

            String result = gestorCliente.toggleFavorito("rest1", session, null);

            assertEquals("redirect:/clientes/verRestaurantes", result, 
                "Si no encuentra el restaurante, simplemente redirecciona, sin cambios en favoritos.");
            verify(serviceClient, never()).save(any());
        }

        @Test
        @DisplayName("Redirige a /clientes/verRestaurantes?favoritos=true si 'favoritosParam' es true")
        void testToggleFavorito_FavoritosParamTrue() {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario("1");
            when(session.getAttribute("usuario")).thenReturn(usuario);

            Cliente cliente = new Cliente();
            cliente.setIdUsuario("1");
            when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));

            Restaurante restaurante = new Restaurante();
            restaurante.setIdUsuario("rest1");
            when(serviceRestaurant.findById("rest1")).thenReturn(Optional.of(restaurante));

            String result = gestorCliente.toggleFavorito("rest1", session, "true");
            assertEquals("redirect:/clientes/verRestaurantes?favoritos=true", result);
        }
    }

    // --------------------------------------------------------------------------------------
    //                GET /clientes/mostrarClientes -> mostrarClientes(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/mostrarClientes -> mostrarClientes(...)")
    class MostrarClientesTests {

        @Test
        @DisplayName("Muestra lista de clientes en /administrador/ListaClientes si hay datos")
        void testMostrarClientes_Existen() {
            List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
            when(serviceClient.findAll()).thenReturn(clientes);

            String vista = gestorCliente.mostrarClientes(model);

            assertEquals("/administrador/ListaClientes", vista);
            verify(model).addAttribute("clientes", clientes);
        }

        @Test
        @DisplayName("Muestra error si no hay clientes")
        void testMostrarClientes_NoExisten() {
            when(serviceClient.findAll()).thenReturn(Collections.emptyList());

            String vista = gestorCliente.mostrarClientes(model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "Clientes no encontrados");
        }
    }

    // --------------------------------------------------------------------------------------
    //                 GET /clientes/mostrarCliente/{id} -> mostrarCliente(...)
    // --------------------------------------------------------------------------------------
    @Nested
    @DisplayName("GET /clientes/mostrarCliente/{id} -> mostrarCliente(...)")
    class MostrarClienteTests {

        @Test
        @DisplayName("Muestra el cliente si lo encuentra")
        void testMostrarCliente_Existe() {
            Cliente cliente = new Cliente();
            when(serviceClient.findById("cli1")).thenReturn(Optional.of(cliente));

            String vista = gestorCliente.mostrarCliente("cli1", model);

            assertEquals("/administrador/VerCliente", vista);
            verify(model).addAttribute("cliente", cliente);
        }

        @Test
        @DisplayName("Muestra error si el cliente no existe")
        void testMostrarCliente_NoExiste() {
            when(serviceClient.findById("cli2")).thenReturn(Optional.empty());

            String vista = gestorCliente.mostrarCliente("cli2", model);

            assertEquals("error", vista);
            verify(model).addAttribute("error", "Cliente no encontrado");
        }
    }
}
