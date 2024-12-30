package com.isoii.deliveringsolutions.dominio.controladores;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Zona;
import com.isoii.deliveringsolutions.dominio.entidades.ZonaCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceZona;
import com.isoii.deliveringsolutions.dominio.service.ServiceZonaCodigoPostal;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del controlador GestorZonaCodigoPostal")
class GestorZonaCodigoPostalTest {

    private static final String ERROR_VIEW = "error";

    @Mock
    private ServiceZonaCodigoPostal serviceZonaCodigoPostalMock;

    @Mock
    private ServiceZona serviceZonaMock;

    @Mock
    private ServiceCodigoPostal serviceCodigoPostalMock;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private GestorZonaCodigoPostal gestorZonaCodigoPostal;

    @Test
    @DisplayName("findAll: lista con elementos")
    void testFindAllConElementos() {
        Zona z = new Zona(1, "Talavera");
        CodigoPostal cp = new CodigoPostal(2, "45002");
        ZonaCodigoPostal zcp = new ZonaCodigoPostal(1, cp, z);

        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(Collections.singletonList(zcp));

        List<ZonaCodigoPostal> result = gestorZonaCodigoPostal.findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findAll: lista vacía")
    void testFindAllListaVacia() {
        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(Collections.emptyList());
        List<ZonaCodigoPostal> result = gestorZonaCodigoPostal.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findAll: servicio retorna null")
    void testFindAllNull() {
        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(null);
        List<ZonaCodigoPostal> result = gestorZonaCodigoPostal.findAll();
        assertNull(result);
    }

    @Test
    @DisplayName("mostrarFormularioRegistro: vista existente con datos")
    void testMostrarFormularioRegistro() {
        Zona z = new Zona(1, "Talavera");
        CodigoPostal cp = new CodigoPostal(2, "45002");
        when(serviceZonaMock.findAll()).thenReturn(Collections.singletonList(z));
        when(serviceCodigoPostalMock.findAll()).thenReturn(Collections.singletonList(cp));

        String vista = gestorZonaCodigoPostal.mostrarFormularioRegistro(model);

        assertEquals("Pruebas-RegisterZonaCodigoPostal", vista);
        verify(model).addAttribute(eq("zonas"), anyList());
        verify(model).addAttribute(eq("codigosPostales"), anyList());
        verify(model).addAttribute(eq("zonaCodigoPostal"), any(ZonaCodigoPostal.class));
    }

    @Test
    @DisplayName("mostrarFormularioRegistro: listas vacías")
    void testMostrarFormularioRegistroListasVacias() {
        when(serviceZonaMock.findAll()).thenReturn(Collections.emptyList());
        when(serviceCodigoPostalMock.findAll()).thenReturn(Collections.emptyList());

        String vista = gestorZonaCodigoPostal.mostrarFormularioRegistro(model);
        assertEquals("Pruebas-RegisterZonaCodigoPostal", vista);
        // Sin validación de error en el código actual, simplemente mostrará la vista
        // con listas vacías
    }

    @Test
    @DisplayName("findById: id válido existente")
    void testFindByIdValido() {
        Zona z = new Zona(1, "Talavera");
        CodigoPostal cp = new CodigoPostal(2, "45002");
        ZonaCodigoPostal zcp = new ZonaCodigoPostal(10, cp, z);
        when(serviceZonaCodigoPostalMock.findById(1L)).thenReturn(Optional.of(zcp));

        ZonaCodigoPostal result = gestorZonaCodigoPostal.findById(1L);
        assertNotNull(result);
        assertEquals(2, result.getCodigoPostal().getId());
    }

    @Test
    @DisplayName("findById: id no existente")
    void testFindByIdNoExiste() {
        when(serviceZonaCodigoPostalMock.findById(9999L)).thenReturn(Optional.empty());
        ZonaCodigoPostal result = gestorZonaCodigoPostal.findById(9999L);
        assertNull(result);
    }

    @Test
    @DisplayName("findById: id negativo (conjetura)")
    void testFindByIdNegativo() {
        when(serviceZonaCodigoPostalMock.findById(-1L)).thenReturn(Optional.empty());
        ZonaCodigoPostal result = gestorZonaCodigoPostal.findById(-1L);
        assertNull(result);
    }

    @Test
    @DisplayName("registrarZonaCodigoPostal: zona y CP válidos")
    void testRegistrarZonaCodigoPostalValido() {
        Zona z = new Zona(1, "Talavera");
        CodigoPostal cp = new CodigoPostal(2, "45002");
        when(serviceZonaMock.findById(1)).thenReturn(Optional.of(z));
        when(serviceCodigoPostalMock.findById(2)).thenReturn(Optional.of(cp));
        when(serviceZonaCodigoPostalMock.save(any(ZonaCodigoPostal.class))).thenAnswer(i -> i.getArguments()[0]);

        String vista = gestorZonaCodigoPostal.registrarZonaCodigoPostal(Arrays.asList(2), 1, redirectAttributes);
        assertEquals("redirect:/repartidores/register", vista);
        verify(redirectAttributes).addFlashAttribute(eq("success"), anyString());
    }

    @Test
    @DisplayName("registrarZonaCodigoPostal: zona no existe")
    void testRegistrarZonaCodigoPostalZonaNoExiste() {
        when(serviceZonaMock.findById(9999)).thenReturn(Optional.empty());

        String vista = gestorZonaCodigoPostal.registrarZonaCodigoPostal(Arrays.asList(2), 9999, redirectAttributes);
        assertEquals("redirect:/zonaCodigoPostal/register", vista);
        verify(redirectAttributes).addFlashAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("registrarZonaCodigoPostal: CP no existe")
    void testRegistrarZonaCodigoPostalCPNoExiste() {
        Zona z = new Zona(1, "Talavera");
        when(serviceZonaMock.findById(1)).thenReturn(Optional.of(z));
        when(serviceCodigoPostalMock.findById(9999)).thenReturn(Optional.empty());

        String vista = gestorZonaCodigoPostal.registrarZonaCodigoPostal(Arrays.asList(9999), 1, redirectAttributes);
        assertEquals("redirect:/zonaCodigoPostal/register", vista);
        verify(redirectAttributes).addFlashAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZonasCodigoPostal: lista con elementos")
    void testMostrarZonasCodigoPostalConElementos() {
        Zona z = new Zona(1, "Talavera");
        CodigoPostal cp = new CodigoPostal(2, "45002");
        ZonaCodigoPostal zcp = new ZonaCodigoPostal(10, cp, z);
        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(Collections.singletonList(zcp));

        String vista = gestorZonaCodigoPostal.mostrarZonasCodigoPostal(model);
        assertEquals("/administrador/ListaZonasCodigoPostal", vista);
        verify(model).addAttribute(eq("zonasCodigosPostales"), anyList());
    }

    @Test
    @DisplayName("mostrarZonasCodigoPostal: lista vacía")
    void testMostrarZonasCodigoPostalVacia() {
        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(Collections.emptyList());

        String vista = gestorZonaCodigoPostal.mostrarZonasCodigoPostal(model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZonasCodigoPostal: lista nula (conjetura)")
    void testMostrarZonasCodigoPostalNula() {
        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(null);

        String vista = gestorZonaCodigoPostal.mostrarZonasCodigoPostal(model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZonaCodigoPostal: id válido existe")
    void testMostrarZonaCodigoPostalValido() {
        Zona z = new Zona(1, "Talavera");
        CodigoPostal cp = new CodigoPostal(2, "45002");
        ZonaCodigoPostal zcp = new ZonaCodigoPostal(10, cp, z);
        when(serviceZonaCodigoPostalMock.findById(10L)).thenReturn(Optional.of(zcp));

        String vista = gestorZonaCodigoPostal.mostrarZonaCodigoPostal(10L, model);
        assertEquals("/administrador/VerZonaCodigoPostal", vista);
        verify(model).addAttribute("zonaCodigoPostal", zcp);
    }

    @Test
    @DisplayName("mostrarZonaCodigoPostal: id no existente")
    void testMostrarZonaCodigoPostalNoExiste() {
        when(serviceZonaCodigoPostalMock.findById(9999L)).thenReturn(Optional.empty());

        String vista = gestorZonaCodigoPostal.mostrarZonaCodigoPostal(9999L, model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZonaCodigoPostal: id negativo (conjetura)")
    void testMostrarZonaCodigoPostalNegativo() {
        when(serviceZonaCodigoPostalMock.findById(-1L)).thenReturn(Optional.empty());

        String vista = gestorZonaCodigoPostal.mostrarZonaCodigoPostal(-1L, model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZonaCodigoPostal: id nulo (conjetura)")
    void testMostrarZonaCodigoPostalNulo() {
        assertThrows(NullPointerException.class, () -> gestorZonaCodigoPostal.mostrarZonaCodigoPostal(null, model));
    }
}
