package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.*;
import com.isoii.deliveringsolutions.dominio.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GestorClienteTest {

    @InjectMocks
    private GestorCliente gestorCliente;

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

    // CP1 - CP4: findById
    @Test
    void testFindById_ValidId() {
        Cliente cliente = new Cliente();
        when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));
        Cliente result = gestorCliente.findById("1");
        assertNotNull(result);
        verify(serviceClient).findById("1");
    }

    @Test
    void testFindById_IdNoExistente() {
        when(serviceClient.findById("999")).thenReturn(Optional.empty());
        Cliente result = gestorCliente.findById("999");
        assertNull(result);
    }

    @Test
    void testFindById_IdInvalido() {
        when(serviceClient.findById("-1")).thenReturn(Optional.empty());
        Cliente result = gestorCliente.findById("-1");
        assertNull(result);
    }

    @Test
    void testFindById_IdNulo() {
        when(serviceClient.findById(null)).thenReturn(Optional.empty());

        Cliente result = gestorCliente.findById(null);
        assertNull(result); // Verifica que el resultado sea null
        verify(serviceClient).findById(null);
    }


    // CP5 - CP6: findAll
    @Test
    void testFindAll_BBDDConClientes() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(serviceClient.findAll()).thenReturn(clientes);

        List<Cliente> result = gestorCliente.findAll();
        assertEquals(2, result.size());
        verify(serviceClient).findAll();
    }

    @Test
    void testFindAll_BBDDSinClientes() {
        when(serviceClient.findAll()).thenReturn(Collections.emptyList());

        List<Cliente> result = gestorCliente.findAll();
        assertTrue(result.isEmpty());
    }

    // CP7 - CP8: verRestaurantes
    @Test
    void testVerRestaurantes_FavoritosActivos() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("1");

        Cliente cliente = new Cliente();
        cliente.addFavorito(new Restaurante());

        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));

        String result = gestorCliente.verRestaurantes("true", model, session);
        assertEquals("verRestaurantes", result);
        
        verify(model).addAttribute(eq("restaurantes"), anyList());
    }

    @Test
    void testVerRestaurantes_SessionNoIniciada() {
        when(session.getAttribute("usuario")).thenReturn(null);
        String result = gestorCliente.verRestaurantes("true", model, session);
        assertEquals("redirect:/", result);
    }

    // CP9: RegistrarCliente
    @Test
    void testRegistrarCliente_ContraseñaValida() {
        Cliente cliente = new Cliente();
        cliente.setPass("1234");
        when(serviceClient.save(cliente)).thenReturn(cliente);

        String result = gestorCliente.registrarCliente(cliente);
        assertEquals("redirect:/", result);
        verify(serviceClient).save(cliente);
    }

    @Test
    void testRegistrarCliente_ContraseñaNula() {
        Cliente cliente = new Cliente();
        cliente.setPass(null);
        String result = gestorCliente.registrarCliente(cliente);
        assertEquals("redirect:/clientes/register", result);
    }

    // CP10: listar
    @Test
    void testListarRestaurantes() {
        List<Restaurante> restaurantes = List.of(new Restaurante());
        when(serviceRestaurant.findAll()).thenReturn(restaurantes);

        String result = gestorCliente.listarRestaurantes(model);
        assertEquals("verRestaurantes", result);
        verify(model).addAttribute("restaurantes", restaurantes);
    }

    // CP11: editarDatos
    @Test
    void testEditarDatos_SesionValida() {
        Usuario usuario = new Usuario();
        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(serviceDireccion.findByUsuario(usuario)).thenReturn(List.of(new Direccion()));

        String result = gestorCliente.editarDatos(model, session);
        assertEquals("editarDatosCliente", result);
        verify(model).addAttribute("usuario", usuario);
    }

    @Test
    void testEditarDatos_SesionNoIniciada() {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuario")).thenReturn(null);

        Model model = mock(Model.class);

        String result = gestorCliente.editarDatos(model, session);

        assertEquals("editarDatosCliente", result);
        verify(model).addAttribute("usuario", null);
        verify(model).addAttribute(eq("direcciones"), anyList());
    }


    // CP12: verPedidos
    @Test
    void testVerPedidos_SesionValida() {
        // Configuración del cliente en sesión
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("cliente1");
        when(session.getAttribute("usuario")).thenReturn(usuario);

        // Configuración de pedidos
        Cliente cliente = new Cliente();
        cliente.setIdUsuario("cliente1");

        Pedido pedido1 = new Pedido();
        pedido1.setCliente(cliente);

        Pedido pedido2 = new Pedido(); // Este no tiene cliente y será ignorado

        when(servicePedido.findAll()).thenReturn(List.of(pedido1, pedido2));
        when(serviceClient.findById("cliente1")).thenReturn(Optional.of(cliente));

        // Mock del modelo
        Model model = mock(Model.class);

        String result = gestorCliente.verPedidos(model, session);

        assertEquals("verPedidosCliente", result);
        verify(model).addAttribute(eq("cliente"), eq(cliente));
        verify(model).addAttribute(eq("pedidos"), anyList());
    }


    @Test
    void testVerPedidos_ClienteNoEncontrado() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("1");

        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(serviceClient.findById("1")).thenReturn(Optional.empty());

        String result = gestorCliente.verPedidos(model, session);
        assertEquals("redirect:/", result);
    }

    // CP13: toggleFavorito
    @Test
    void testToggleFavorito_IdValido() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("1");
        Cliente cliente = new Cliente();

        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(serviceClient.findById("1")).thenReturn(Optional.of(cliente));
        when(serviceRestaurant.findById("rest1")).thenReturn(Optional.of(new Restaurante()));

        String result = gestorCliente.toggleFavorito("rest1", session, null);
        assertEquals("redirect:/clientes/verRestaurantes", result);
    }

    @Test
    void testToggleFavorito_SesionNoIniciada() {
        when(session.getAttribute("usuario")).thenReturn(null);
        String result = gestorCliente.toggleFavorito("rest1", session, null);
        assertEquals("redirect:/", result);
    }
}

