package com.isoii.deliveringsolutions.dominio.controladores;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceCodigoPostal;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del controlador GestorCodigoPostal")
class GestorCodigoPostalTest {

    private static final String ERROR_VIEW = "error";

    @Mock
    private ServiceCodigoPostal serviceCodigoPostal;

    @InjectMocks
    private GestorCodigoPostal gestorCodigoPostal;

    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
    }

    @Test
    @DisplayName("findAll: Debería retornar una lista con códigos postales")
    void testFindAllListaConDatos() {
        List<CodigoPostal> lista = Arrays.asList(new CodigoPostal(1, "45001"), new CodigoPostal(2, "45002"));
        when(serviceCodigoPostal.findAll()).thenReturn(lista);

        List<CodigoPostal> result = gestorCodigoPostal.findAll();
        assertNotNull(result, "La lista no debería ser nula");
        assertEquals(2, result.size(), "La lista debería tener 2 elementos");
    }

    @Test
    @DisplayName("findAll: Debería retornar una lista vacía si no hay códigos postales")
    void testFindAllListaVacia() {
        when(serviceCodigoPostal.findAll()).thenReturn(Collections.emptyList());

        List<CodigoPostal> result = gestorCodigoPostal.findAll();
        assertNotNull(result, "La lista no debería ser nula, aunque esté vacía");
        assertTrue(result.isEmpty(), "La lista debería estar vacía");
    }

    @Test
    @DisplayName("findAll: Debería retornar null si el servicio devuelve null")
    void testFindAllNulo() {
        when(serviceCodigoPostal.findAll()).thenReturn(null);

        List<CodigoPostal> result = gestorCodigoPostal.findAll();
        assertNull(result, "El resultado debería ser null");
    }

    @Test
    @DisplayName("mostrarFormularioRegistro: Debería retornar la vista del formulario")
    void testMostrarFormularioRegistro() {
        String vista = gestorCodigoPostal.mostrarFormularioRegistro();
        assertEquals("Pruebas-RegisterCodigoPostal", vista, "La vista debería ser 'Pruebas-RegisterCodigoPostal'");
    }

    @Test
    @DisplayName("findById: Debería retornar un código postal válido por su ID")
    void testFindByIdValido() {
        CodigoPostal cp = new CodigoPostal(1, "45001");
        when(serviceCodigoPostal.findById(1)).thenReturn(Optional.of(cp));

        CodigoPostal result = gestorCodigoPostal.findById(1);
        assertNotNull(result, "Debería encontrarse un código postal");
        assertEquals("45001", result.getCodigo(), "El código postal debería coincidir");
    }

    @Test
    @DisplayName("findById: Debería retornar null si el ID no existe")
    void testFindByIdNoExistente() {
        when(serviceCodigoPostal.findById(999)).thenReturn(Optional.empty());

        CodigoPostal result = gestorCodigoPostal.findById(999);
        assertNull(result, "Debería retornar null si no se encuentra el código postal");
    }

    @Test
    @DisplayName("findById: Debería retornar null si el ID es inválido")
    void testFindByIdInvalido() {
        when(serviceCodigoPostal.findById(-1)).thenReturn(Optional.empty());

        CodigoPostal result = gestorCodigoPostal.findById(-1);
        assertNull(result, "Debería retornar null para un ID inválido");
    }

    @Test
    @DisplayName("registrarCodigoPostal: Debería registrar un código postal válido")
    void testRegistrarCodigoPostalValido() {
        CodigoPostal cp = new CodigoPostal();
        cp.setCodigo("45001");
        when(serviceCodigoPostal.save(cp)).thenReturn(new CodigoPostal(1, "45001"));

        ResponseEntity<CodigoPostal> response = gestorCodigoPostal.registrarCodigoPostal(cp);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Debería devolver CREATED");
        assertNotNull(response.getBody(), "El cuerpo de la respuesta no debería ser nulo");
        assertEquals("45001", response.getBody().getCodigo(), "El código postal registrado debería ser '45001'");
    }

    @Test
    @DisplayName("registrarCodigoPostal: Debería retornar BAD_REQUEST si el código postal es nulo")
    void testRegistrarCodigoPostalNulo() {
        CodigoPostal cp = new CodigoPostal();
        cp.setCodigo(null);

        ResponseEntity<CodigoPostal> response = gestorCodigoPostal.registrarCodigoPostal(cp);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Debería devolver BAD_REQUEST");
    }

    @Test
    @DisplayName("registrarCodigoPostal: Debería retornar BAD_REQUEST si el código postal está vacío")
    void testRegistrarCodigoPostalVacio() {
        CodigoPostal cp = new CodigoPostal();
        cp.setCodigo("");

        ResponseEntity<CodigoPostal> response = gestorCodigoPostal.registrarCodigoPostal(cp);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Debería devolver BAD_REQUEST");
    }

    @Test
    @DisplayName("mostrarCodigosPostales: Debería retornar error si la lista está vacía")
    void testMostrarCodigosPostalesListaVacia() {
        when(serviceCodigoPostal.findAll()).thenReturn(Collections.emptyList());

        String vista = gestorCodigoPostal.mostrarCodigosPostales(model);
        assertEquals(ERROR_VIEW, vista, "Debería retornar la vista 'error'");
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarCodigosPostales: Debería mostrar la lista de códigos postales")
    void testMostrarCodigosPostalesConElementos() {
        List<CodigoPostal> lista = Collections.singletonList(new CodigoPostal(1, "45001"));
        when(serviceCodigoPostal.findAll()).thenReturn(lista);

        String vista = gestorCodigoPostal.mostrarCodigosPostales(model);
        assertEquals("/administrador/ListaCodigoPostales", vista,
                "Debería retornar la vista de la lista de códigos postales");
        verify(model).addAttribute("codigosPostales", lista);
    }

    @Test
    @DisplayName("mostrarCodigosPostales: Debería retornar error si la lista es nula")
    void testMostrarCodigosPostalesNulo() {
        when(serviceCodigoPostal.findAll()).thenReturn(null);

        String vista = gestorCodigoPostal.mostrarCodigosPostales(model);
        assertEquals(ERROR_VIEW, vista, "Debería retornar la vista 'error'");
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarCodigoPostal: Debería mostrar un código postal existente")
    void testMostrarCodigoPostalValido() {
        CodigoPostal cp = new CodigoPostal(1, "45001");
        when(serviceCodigoPostal.findById(1)).thenReturn(Optional.of(cp));

        String vista = gestorCodigoPostal.mostrarCodigoPostal(1, model);
        assertEquals("/administrador/VerCodigoPostal", vista, "Debería retornar la vista del código postal");
        verify(model).addAttribute("codigoPostal", cp);
    }

    @Test
    @DisplayName("mostrarCodigoPostal: Debería retornar error si el código postal no existe")
    void testMostrarCodigoPostalNoExistente() {
        when(serviceCodigoPostal.findById(999)).thenReturn(Optional.empty());

        String vista = gestorCodigoPostal.mostrarCodigoPostal(999, model);
        assertEquals(ERROR_VIEW, vista, "Debería retornar la vista 'error'");
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarCodigoPostal: Debería retornar error si el ID es inválido")
    void testMostrarCodigoPostalInvalido() {
        when(serviceCodigoPostal.findById(-1)).thenReturn(Optional.empty());

        String vista = gestorCodigoPostal.mostrarCodigoPostal(-1, model);
        assertEquals(ERROR_VIEW, vista, "Debería retornar la vista 'error'");
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }
}
