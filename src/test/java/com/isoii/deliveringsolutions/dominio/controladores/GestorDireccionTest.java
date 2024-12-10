package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Cliente;
import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.dominio.service.ServiceCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceDireccion;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorDireccionTest {

    @Mock
    private ServiceDireccion serviceDireccion;

    @Mock
    private ServiceCodigoPostal serviceCodigoPostal;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private GestorDireccion gestorDireccion;

    private static final String USUARIO = "usuario";
    private static final String ERROR = "error";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Tests para mostrarFormularioRegistro")
    class MostrarFormularioRegistroTests {

        @Test
        @DisplayName("Debe retornar la vista 'RegistrarDireccion' con usuario presente y lista de códigos postales no vacía")
        void testMostrarFormularioRegistro_usuarioPresente_codigosPostalesNoVacia() {
            Usuario usuario = new Usuario();
            List<CodigoPostal> codigosPostales = Arrays.asList(new CodigoPostal(), new CodigoPostal());

            when(session.getAttribute(USUARIO)).thenReturn(usuario);
            when(serviceCodigoPostal.findAll()).thenReturn(codigosPostales);

            String vista = gestorDireccion.mostrarFormularioRegistro(model, session);

            assertEquals("RegistrarDireccion", vista);
            verify(model).addAttribute(eq("direccion"), any(Direccion.class));
            verify(model).addAttribute("codigosPostales", codigosPostales);
            verify(model).addAttribute(USUARIO, usuario);
        }

        @Test
        @DisplayName("Debe retornar la vista 'RegistrarDireccion' sin usuario en sesión")
        void testMostrarFormularioRegistro_usuarioAusente() {
            List<CodigoPostal> codigosPostales = Arrays.asList(new CodigoPostal(), new CodigoPostal());

            when(session.getAttribute(USUARIO)).thenReturn(null);
            when(serviceCodigoPostal.findAll()).thenReturn(codigosPostales);

            String vista = gestorDireccion.mostrarFormularioRegistro(model, session);

            assertEquals("RegistrarDireccion", vista);
            verify(model).addAttribute(eq("direccion"), any(Direccion.class));
            verify(model).addAttribute("codigosPostales", codigosPostales);
            verify(model).addAttribute(USUARIO, null);
        }

        @Test
        @DisplayName("Debe retornar la vista 'RegistrarDireccion' con lista de códigos postales vacía")
        void testMostrarFormularioRegistro_codigosPostalesVacia() {
            Usuario usuario = new Usuario();
            List<CodigoPostal> codigosPostales = Collections.emptyList();

            when(session.getAttribute(USUARIO)).thenReturn(usuario);
            when(serviceCodigoPostal.findAll()).thenReturn(codigosPostales);

            String vista = gestorDireccion.mostrarFormularioRegistro(model, session);

            assertEquals("RegistrarDireccion", vista);
            verify(model).addAttribute(eq("direccion"), any(Direccion.class));
            verify(model).addAttribute("codigosPostales", codigosPostales);
            verify(model).addAttribute(USUARIO, usuario);
        }
    }

    @Nested
    @DisplayName("Tests para registrarDireccion")
    class RegistrarDireccionTests {

        @Test
        @DisplayName("Debe redirigir al formulario con error si campos obligatorios están vacíos")
        void testRegistrarDireccion_camposObligatoriosVacios() {
            Direccion direccion = new Direccion(); // campos calle y numero nulos
            Integer codigoPostalId = null;
            String idUsuario = null;

            String resultado = gestorDireccion.registrarDireccion(direccion, codigoPostalId, idUsuario, redirectAttributes, session);

            assertEquals("redirect:/direccion/formularioRegistro", resultado);
            verify(redirectAttributes).addFlashAttribute(ERROR, "Rellenar los campos obligatorios");
            verify(serviceCodigoPostal, never()).findById(anyInt());
            verify(serviceDireccion, never()).findUsuarioById(anyString());
            verify(serviceDireccion, never()).save(any(Direccion.class));
        }

        @Test
        @DisplayName("Debe redirigir al formulario con error si codigoPostalId no es válido")
        void testRegistrarDireccion_codigoPostalInvalido() {
            Direccion direccion = new Direccion();
            direccion.setCalle("Av. Principal");
            direccion.setNumero("123");
            Integer codigoPostalId = 9999; // Supongamos que no existe
            String idUsuario = "usuarioValido";

            when(serviceCodigoPostal.findById(codigoPostalId)).thenReturn(Optional.empty());
            when(serviceDireccion.findUsuarioById(idUsuario)).thenReturn(Optional.of(new Usuario()));

            String resultado = gestorDireccion.registrarDireccion(direccion, codigoPostalId, idUsuario, redirectAttributes, session);

            assertEquals("redirect:/direccion/formularioRegistro", resultado);
            verify(redirectAttributes).addFlashAttribute(ERROR, "Código Postal o Usuario no válido");
            verify(serviceDireccion).findUsuarioById(idUsuario);
            verify(serviceDireccion, never()).save(any(Direccion.class));
        }

        @Test
        @DisplayName("Debe redirigir al formulario con error si idUsuario no es válido")
        void testRegistrarDireccion_idUsuarioInvalido() {
            Direccion direccion = new Direccion();
            direccion.setCalle("Av. Principal");
            direccion.setNumero("123");
            Integer codigoPostalId = 1;
            String idUsuario = "usuarioInvalido";

            when(serviceCodigoPostal.findById(codigoPostalId)).thenReturn(Optional.of(new CodigoPostal()));
            when(serviceDireccion.findUsuarioById(idUsuario)).thenReturn(Optional.empty());

            String resultado = gestorDireccion.registrarDireccion(direccion, codigoPostalId, idUsuario, redirectAttributes, session);

            assertEquals("redirect:/direccion/formularioRegistro", resultado);
            verify(redirectAttributes).addFlashAttribute(ERROR, "Código Postal o Usuario no válido");
            verify(serviceDireccion).findUsuarioById(idUsuario);
            verify(serviceDireccion, never()).save(any(Direccion.class));
        }

        @Test
        @DisplayName("Debe registrar la dirección y redirigir según el tipo de usuario (Cliente)")
        void testRegistrarDireccion_exito_tipoCliente() {
            Direccion direccion = new Direccion();
            direccion.setCalle("Av. Principal");
            direccion.setNumero("123");
            Integer codigoPostalId = 1;
            String idUsuario = "usuarioValido";

            CodigoPostal codigoPostal = new CodigoPostal();
            Usuario usuario = new Usuario();

            when(serviceCodigoPostal.findById(codigoPostalId)).thenReturn(Optional.of(codigoPostal));
            when(serviceDireccion.findUsuarioById(idUsuario)).thenReturn(Optional.of(usuario));
            when(session.getAttribute(USUARIO)).thenReturn(new Cliente());

            String resultado = gestorDireccion.registrarDireccion(direccion, codigoPostalId, idUsuario, redirectAttributes, session);

            assertEquals("redirect:/clientes/verRestaurantes", resultado);
            verify(serviceDireccion).save(direccion);
            verify(redirectAttributes).addFlashAttribute("mensaje", "Dirección registrada con éxito");
        }

        @Test
        @DisplayName("Debe registrar la dirección y redirigir según el tipo de usuario (Restaurante)")
        void testRegistrarDireccion_exito_tipoRestaurante() {
            Direccion direccion = new Direccion();
            direccion.setCalle("Av. Principal");
            direccion.setNumero("123");
            Integer codigoPostalId = 1;
            String idUsuario = "restauranteValido";

            CodigoPostal codigoPostal = new CodigoPostal();
            Restaurante restaurante = new Restaurante();
            restaurante.setIdUsuario("restauranteValido");

            when(serviceCodigoPostal.findById(codigoPostalId)).thenReturn(Optional.of(codigoPostal));
            when(serviceDireccion.findUsuarioById(idUsuario)).thenReturn(Optional.of(restaurante));
            when(session.getAttribute(USUARIO)).thenReturn(restaurante);

            String resultado = gestorDireccion.registrarDireccion(direccion, codigoPostalId, idUsuario, redirectAttributes, session);

            assertEquals("redirect:/restaurantes/gestion/restauranteValido", resultado);
            verify(serviceDireccion).save(direccion);
            verify(redirectAttributes).addFlashAttribute("mensaje", "Dirección registrada con éxito");
        }

        @Test
        @DisplayName("Debe registrar la dirección y redirigir a la raíz si el tipo de usuario no es ni Cliente ni Restaurante")
        void testRegistrarDireccion_exito_tipoDesconocido() {
            Direccion direccion = new Direccion();
            direccion.setCalle("Av. Principal");
            direccion.setNumero("123");
            Integer codigoPostalId = 1;
            String idUsuario = "usuarioValido";

            CodigoPostal codigoPostal = new CodigoPostal();
            Usuario usuario = new Usuario();

            when(serviceCodigoPostal.findById(codigoPostalId)).thenReturn(Optional.of(codigoPostal));
            when(serviceDireccion.findUsuarioById(idUsuario)).thenReturn(Optional.of(usuario));
            when(session.getAttribute(USUARIO)).thenReturn(new Usuario()); // Tipo desconocido

            String resultado = gestorDireccion.registrarDireccion(direccion, codigoPostalId, idUsuario, redirectAttributes, session);

            assertEquals("redirect:/", resultado);
            verify(serviceDireccion).save(direccion);
            verify(redirectAttributes).addFlashAttribute("mensaje", "Dirección registrada con éxito");
        }
    }

    @Nested
    @DisplayName("Tests para findById")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar una dirección si el id existe")
        void testFindById_existe() {
            Long id = 1L;
            Direccion direccion = new Direccion();

            when(serviceDireccion.findById(id)).thenReturn(Optional.of(direccion));

            Direccion resultado = gestorDireccion.findById(id);

            assertNotNull(resultado);
            assertEquals(direccion, resultado);
            verify(serviceDireccion).findById(id);
        }

        @Test
        @DisplayName("Debe retornar null si el id no existe")
        void testFindById_noExiste() {
            Long id = 9999L;

            when(serviceDireccion.findById(id)).thenReturn(Optional.empty());

            Direccion resultado = gestorDireccion.findById(id);

            assertNull(resultado);
            verify(serviceDireccion).findById(id);
        }

        @Test
        @DisplayName("Debe manejar correctamente un id nulo (si es posible)")
        void testFindById_idNulo() {
            // Dado que @PathVariable no permite null, este caso podría no ser aplicable.
            // Sin embargo, si el método se llama directamente con null:
            Long id = null;

            when(serviceDireccion.findById(id)).thenReturn(Optional.empty());

            Direccion resultado = gestorDireccion.findById(id);

            assertNull(resultado);
            verify(serviceDireccion).findById(id);
        }

        @Test
        @DisplayName("Debe retornar null para un id negativo")
        void testFindById_idNegativo() {
            Long id = -1L;

            when(serviceDireccion.findById(id)).thenReturn(Optional.empty());

            Direccion resultado = gestorDireccion.findById(id);

            assertNull(resultado);
            verify(serviceDireccion).findById(id);
        }
    }

    @Nested
    @DisplayName("Tests para findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar una lista de direcciones cuando existen")
        void testFindAll_conDirecciones() {
            List<Direccion> direcciones = Arrays.asList(new Direccion(), new Direccion());

            when(serviceDireccion.findAll()).thenReturn(direcciones);

            List<Direccion> resultado = gestorDireccion.findAll();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            verify(serviceDireccion).findAll();
        }

        @Test
        @DisplayName("Debe retornar una lista vacía cuando no existen direcciones")
        void testFindAll_sinDirecciones() {
            List<Direccion> direcciones = Collections.emptyList();

            when(serviceDireccion.findAll()).thenReturn(direcciones);

            List<Direccion> resultado = gestorDireccion.findAll();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(serviceDireccion).findAll();
        }

        @Test
        @DisplayName("Debe manejar correctamente cuando el servicio retorna null")
        void testFindAll_servicioRetornaNull() {
            when(serviceDireccion.findAll()).thenReturn(null);

            List<Direccion> resultado = gestorDireccion.findAll();

            assertNull(resultado);
            verify(serviceDireccion).findAll();
        }
    }

    @Nested
    @DisplayName("Tests para mostrarDireccion")
    class MostrarDireccionTests {

        @Test
        @DisplayName("Debe agregar la dirección al modelo y retornar la vista 'VerDireccion' si existe")
        void testMostrarDireccion_existe() {
            Long id = 1L;
            Direccion direccion = new Direccion();

            when(serviceDireccion.findById(id)).thenReturn(Optional.of(direccion));

            String vista = gestorDireccion.mostrarDireccion(id, model);

            assertEquals("/administrador/VerDireccion", vista);
            verify(model).addAttribute("direccion", direccion);
            verify(model, never()).addAttribute(eq(ERROR), anyString());
        }

        @Test
        @DisplayName("Debe agregar un error al modelo y retornar la vista 'error' si no existe la dirección")
        void testMostrarDireccion_noExiste() {
            Long id = 9999L;

            when(serviceDireccion.findById(id)).thenReturn(Optional.empty());

            String vista = gestorDireccion.mostrarDireccion(id, model);

            assertEquals(ERROR, vista);
            verify(model).addAttribute(ERROR, "Dirección no encontrada");
            verify(model, never()).addAttribute(eq("direccion"), any());
        }

        @Test
        @DisplayName("Debe manejar correctamente un id nulo (si es posible)")
        void testMostrarDireccion_idNulo() {
            Long id = null;

            when(serviceDireccion.findById(id)).thenReturn(Optional.empty());

            String vista = gestorDireccion.mostrarDireccion(id, model);

            assertEquals(ERROR, vista);
            verify(model).addAttribute(ERROR, "Dirección no encontrada");
            verify(model, never()).addAttribute(eq("direccion"), any());
        }

        @Test
        @DisplayName("Debe manejar un id negativo")
        void testMostrarDireccion_idNegativo() {
            Long id = -1L;

            when(serviceDireccion.findById(id)).thenReturn(Optional.empty());

            String vista = gestorDireccion.mostrarDireccion(id, model);

            assertEquals(ERROR, vista);
            verify(model).addAttribute(ERROR, "Dirección no encontrada");
            verify(model, never()).addAttribute(eq("direccion"), any());
        }
    }

    @Nested
    @DisplayName("Tests para mostrarDirecciones")
    class MostrarDireccionesTests {

        @Test
        @DisplayName("Debe agregar la lista de direcciones al modelo y retornar 'ListaDirecciones' si existen")
        void testMostrarDirecciones_conDirecciones() {
            List<Direccion> direcciones = Arrays.asList(new Direccion(), new Direccion());

            when(serviceDireccion.findAll()).thenReturn(direcciones);

            String vista = gestorDireccion.mostrarDirecciones(model);

            assertEquals("/administrador/ListaDirecciones", vista);
            verify(model).addAttribute("direcciones", direcciones);
            verify(model, never()).addAttribute(eq(ERROR), anyString());
        }

        @Test
        @DisplayName("Debe agregar un error al modelo y retornar 'error' si no existen direcciones")
        void testMostrarDirecciones_sinDirecciones() {
            List<Direccion> direcciones = Collections.emptyList();

            when(serviceDireccion.findAll()).thenReturn(direcciones);

            String vista = gestorDireccion.mostrarDirecciones(model);

            assertEquals(ERROR, vista);
            verify(model).addAttribute(ERROR, "No se encontraron direcciones");
            verify(model, never()).addAttribute(eq("direcciones"), any());
        }

        @Test
        @DisplayName("Debe agregar un error al modelo y retornar 'error' si el servicio retorna null")
        void testMostrarDirecciones_servicioRetornaNull() {
            when(serviceDireccion.findAll()).thenReturn(null);

            String vista = gestorDireccion.mostrarDirecciones(model);

            assertEquals(ERROR, vista);
            verify(model).addAttribute(ERROR, "No se encontraron direcciones");
            verify(model, never()).addAttribute(eq("direcciones"), any());
        }
    }
}
