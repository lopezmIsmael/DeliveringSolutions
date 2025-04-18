package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.dominio.service.ServiceUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GestorUsuarioTest {

    @InjectMocks
    private GestorUsuario gestorUsuario;

    @Mock
    private ServiceUser serviceUsuario;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @Mock
    private Logger logger;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_ValidId() {
        Usuario usuario = new Usuario();
        when(serviceUsuario.findById("1")).thenReturn(Optional.of(usuario));
        Usuario result = gestorUsuario.findById("1");
        assertNotNull(result);
        verify(serviceUsuario).findById("1");
    }

    @Test
    void testFindById_InvalidId() {
        when(serviceUsuario.findById("-1")).thenReturn(Optional.empty());
        Usuario result = gestorUsuario.findById("-1");
        assertNull(result);
    }

    @Test
    void testFindById_NullId() {
        assertThrows(NullPointerException.class, () -> gestorUsuario.findById(null));
    }


    @Test
    void testFindAll_WithResults() {
        when(serviceUsuario.findAll()).thenReturn(List.of(new Usuario()));
        List<Usuario> result = gestorUsuario.findAll();
        assertFalse(result.isEmpty());
        verify(serviceUsuario).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        when(serviceUsuario.findAll()).thenReturn(List.of());
        List<Usuario> result = gestorUsuario.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void testMostrarFormularioRegistro() {
        String result = gestorUsuario.mostrarFormularioRegistro();
        assertEquals("rol", result);
    }

    @Test
    void testRegistrarUsuario_ValidUser() {
        Usuario usuario = new Usuario();
        usuario.setPass("1234");
        when(serviceUsuario.save(usuario)).thenReturn(usuario);
        String result = gestorUsuario.registrarUsuario(usuario);
        assertEquals("redirect:/", result);
    }

    @Test
    void testRegistrarUsuario_NullPassword() {
        Usuario usuario = new Usuario();
        usuario.setPass(null);
        String result = gestorUsuario.registrarUsuario(usuario);
        assertEquals("redirect:/usuarios/registrarUsuario", result);
    }

    @Test
    void testDeleteById_ValidId() {
        ResponseEntity<String> response = gestorUsuario.deleteById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario eliminado", response.getBody());
        verify(serviceUsuario).deleteById("1");
    }

    @Test
    void testDeleteById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> gestorUsuario.deleteById(null));

        // Verificar que el método deleteById del servicio no se llama
        verify(serviceUsuario, never()).deleteById(any());
    }



    @Test
    void testLoginUsuario_ValidClient() {
        Usuario usuario = new Usuario();
        usuario.setPass("1234");
        usuario.settipoUsuario("CLIENTE");
        when(serviceUsuario.findById("cliente1")).thenReturn(Optional.of(usuario));

        String result = gestorUsuario.loginUsuario("cliente1", "1234", redirectAttributes, session);
        assertEquals("redirect:/clientes/verRestaurantes", result);
        verify(session).setAttribute("usuario", usuario);
        verify(redirectAttributes).addFlashAttribute("mensaje", "Inicio de sesión exitoso.");
    }

    @Test
    void testLoginUsuario_InvalidPassword() {
        Usuario usuario = new Usuario();
        usuario.setPass("1234");
        when(serviceUsuario.findById("cliente1")).thenReturn(Optional.of(usuario));

        String result = gestorUsuario.loginUsuario("cliente1", "12", redirectAttributes, session);
        assertEquals("redirect:/", result);
        verify(redirectAttributes).addFlashAttribute("error", "Usuario o contraseña incorrectos.");
    }

    @Test
    void testLoginUsuario_UserNotFound() {
        when(serviceUsuario.findById("noExiste")).thenReturn(Optional.empty());
        String result = gestorUsuario.loginUsuario("noExiste", "1234", redirectAttributes, session);
        assertEquals("redirect:/", result);
        verify(redirectAttributes).addFlashAttribute("error", "Usuario o contraseña incorrectos.");
    }

    @Test
    void testLoginUsuario_NullUsername() {
        String result = gestorUsuario.loginUsuario(null, "1234", redirectAttributes, session);
        assertEquals("redirect:/", result);
        verify(redirectAttributes).addFlashAttribute("error", "Usuario o contraseña incorrectos.");
    }

    @Test
    void testLoginUsuario_NullPassword() {
        Usuario usuario = new Usuario();
        usuario.setPass("1234");

        when(serviceUsuario.findById("cliente1")).thenReturn(Optional.of(usuario));

        String result = gestorUsuario.loginUsuario("cliente1", null, redirectAttributes, session);

        assertEquals("redirect:/", result);
        verify(redirectAttributes).addFlashAttribute("error", "Usuario o contraseña incorrectos.");
    }


    @Test
    void testLoginUsuario_RestaurantRedirect() {
        Usuario usuario = new Usuario();
        usuario.setPass("1234");
        usuario.settipoUsuario("RESTAURANTE");
        when(serviceUsuario.findById("rest1")).thenReturn(Optional.of(usuario));

        String result = gestorUsuario.loginUsuario("rest1", "1234", redirectAttributes, session);
        assertEquals("redirect:/restaurantes/gestion/rest1", result);
    }

    @Test
    void testMostrarFormularioLogin() {
        String result = gestorUsuario.mostrarFormularioLogin();
        assertEquals("index", result);
    }

    @Test
    void testMostrarAboutUs() {
        String result = gestorUsuario.mostrarAboutUs();
        assertEquals("aboutUs", result);
    }

    @Test
    void testRegistrarUsuario_EmptyPassword() {
        Usuario usuario = new Usuario();
        usuario.setPass(""); // Contraseña vacía
        String result = gestorUsuario.registrarUsuario(usuario);
        assertEquals("redirect:/usuarios/registrarUsuario", result);
    }
    @Test
    void testLoginUsuario_RepartidorRedirect() {
        Usuario usuario = new Usuario();
        usuario.setPass("1234");
        usuario.settipoUsuario("REPARTIDOR");
        when(serviceUsuario.findById("repartidor1")).thenReturn(Optional.of(usuario));

        String result = gestorUsuario.loginUsuario("repartidor1", "1234", redirectAttributes, session);
        assertEquals("redirect:/repartidores/login", result);
        verify(session).setAttribute("usuario", usuario);
        verify(redirectAttributes).addFlashAttribute("mensaje", "Inicio de sesión exitoso.");
    }

    @Test
    void testLoginUsuario_InvalidUsernameOrPassword() {
        when(serviceUsuario.findById("invalido")).thenReturn(Optional.empty());
        String result = gestorUsuario.loginUsuario("invalido", "wrongPass", redirectAttributes, session);
        assertEquals("redirect:/", result);
        verify(redirectAttributes).addFlashAttribute("error", "Usuario o contraseña incorrectos.");
    }

}
