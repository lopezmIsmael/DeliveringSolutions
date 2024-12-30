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

import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Zona;
import com.isoii.deliveringsolutions.dominio.entidades.ZonaCodigoPostal;
import com.isoii.deliveringsolutions.dominio.service.ServiceZona;
import com.isoii.deliveringsolutions.dominio.service.ServiceZonaCodigoPostal;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del controlador GestorZona")
class GestorZonaTest {

    private static final String ERROR_VIEW = "error";

    @Mock
    private ServiceZona serviceZonaMock;

    @Mock
    private ServiceZonaCodigoPostal serviceZonaCodigoPostalMock;

    @Mock
    private Model model;

    @InjectMocks
    private GestorZona gestorZona;

    @Captor
    private ArgumentCaptor<List<CodigoPostal>> captor;

    // findAll
    @Test
    @DisplayName("findAll: Lista con elementos")
    void testFindAllListaConElementos() {
        List<Zona> lista = Arrays.asList(new Zona(1, "Talavera"), new Zona(2, "Toledo"));
        when(serviceZonaMock.findAll()).thenReturn(lista);

        List<Zona> result = gestorZona.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("findAll: Lista vacía")
    void testFindAllListaVacia() {
        when(serviceZonaMock.findAll()).thenReturn(Collections.emptyList());

        List<Zona> result = gestorZona.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findAll: Servicio retorna null")
    void testFindAllNull() {
        when(serviceZonaMock.findAll()).thenReturn(null);

        List<Zona> result = gestorZona.findAll();
        assertNull(result);
    }

    // mostrarFormularioRegistro
    @Test
    @DisplayName("mostrarFormularioRegistro: Vista existente")
    void testMostrarFormularioRegistro() {
        String vista = gestorZona.mostrarFormularioRegistro();
        assertEquals("Pruebas-RegisterZona", vista);
    }

    // findById
    @Test
    @DisplayName("findById: ID válido existente")
    void testFindByIdValido() {
        Zona z = new Zona(1, "Talavera");
        when(serviceZonaMock.findById(1)).thenReturn(Optional.of(z));

        Zona result = gestorZona.findById(1);
        assertNotNull(result);
        assertEquals("Talavera", result.getNombre());
    }

    @Test
    @DisplayName("findById: ID no existente")
    void testFindByIdNoExistente() {
        when(serviceZonaMock.findById(9999)).thenReturn(Optional.empty());
        Zona result = gestorZona.findById(9999);
        assertNull(result);
    }

    @Test
    @DisplayName("findById: ID negativo")
    void testFindByIdNegativo() {
        when(serviceZonaMock.findById(-1)).thenReturn(Optional.empty());
        Zona result = gestorZona.findById(-1);
        assertNull(result);
    }

    @Test
    @DisplayName("findById: ID = 0")
    void testFindByIdCero() {
        when(serviceZonaMock.findById(0)).thenReturn(Optional.empty());
        Zona result = gestorZona.findById(0);
        assertNull(result);
    }

    @Test
    @DisplayName("findById: ID nulo (conjetura)")
    void testFindByIdNulo() {
        assertThrows(NullPointerException.class, () -> gestorZona.findById(null));
    }

    // registrarZona
    @Test
    @DisplayName("registrarZona: nombre válido")
    void testRegistrarZonaValido() {
        Zona input = new Zona();
        input.setNombre("Talavera");
        Zona saved = new Zona(1, "Talavera");
        when(serviceZonaMock.save(input)).thenReturn(saved);

        String vista = gestorZona.registrarZona(input);
        assertEquals("redirect:/zonaCodigoPostal/register", vista);
    }

    @Test
    @DisplayName("registrarZona: nombre vacío")
    void testRegistrarZonaNombreVacio() {
        Zona input = new Zona();
        input.setNombre("");
        String vista = gestorZona.registrarZona(input);
        assertEquals(ERROR_VIEW, vista);
    }

    @Test
    @DisplayName("registrarZona: nombre nulo")
    void testRegistrarZonaNombreNulo() {
        Zona input = new Zona();
        input.setNombre(null);
        String vista = gestorZona.registrarZona(input);
        assertEquals(ERROR_VIEW, vista);
    }

    @Test
    @DisplayName("registrarZona: nombre largo")
    void testRegistrarZonaNombreLargo() {
        Zona input = new Zona();
        input.setNombre("a".repeat(51));
        when(serviceZonaMock.save(input)).thenReturn(new Zona(2, "NombreLargo"));
        String vista = gestorZona.registrarZona(input);
        assertEquals("redirect:/zonaCodigoPostal/register", vista,
                "Sin validación extra, este test debería comportarse igual que un nombre válido");
    }

    @Test
    @DisplayName("registrarZona: zona nula (conjetura)")
    void testRegistrarZonaNulo() {
        assertThrows(NullPointerException.class, () -> gestorZona.registrarZona(null));
    }

    @Test
    @DisplayName("registrarZona: error BD al guardar (conjetura)")
    void testRegistrarZonaErrorBD() {
        Zona input = new Zona();
        input.setNombre("Toledo");
        when(serviceZonaMock.save(input)).thenThrow(new RuntimeException("Error BD"));

        // Sin try/catch en el controlador, esto lanzará excepción.
        assertThrows(RuntimeException.class, () -> gestorZona.registrarZona(input));
    }

    // mostrarZonas
    @Test
    @DisplayName("mostrarZonas: lista con elementos")
    void testMostrarZonasConElementos() {
        List<Zona> lista = Arrays.asList(new Zona(1, "Talavera"));
        when(serviceZonaMock.findAll()).thenReturn(lista);

        String vista = gestorZona.mostrarZonas(model);
        assertEquals("/administrador/ListaZonas", vista);
        verify(model).addAttribute("zonas", lista);
    }

    @Test
    @DisplayName("mostrarZonas: lista vacía")
    void testMostrarZonasVacia() {
        when(serviceZonaMock.findAll()).thenReturn(Collections.emptyList());

        String vista = gestorZona.mostrarZonas(model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZonas: lista nula")
    void testMostrarZonasNula() {
        when(serviceZonaMock.findAll()).thenReturn(null);

        String vista = gestorZona.mostrarZonas(model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    // mostrarZona
    @Test
    @DisplayName("mostrarZona: id válido con zona y codigosPostales")
    void testMostrarZonaConCodigos() {
        Zona z = new Zona(1, "Talavera");
        CodigoPostal cp = new CodigoPostal(2, "45002");
        ZonaCodigoPostal zcp = new ZonaCodigoPostal(10, cp, z);

        when(serviceZonaMock.findById(1)).thenReturn(Optional.of(z));
        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(Collections.singletonList(zcp));

        String vista = gestorZona.mostrarZona(1, model);
        assertEquals("/administrador/VerZona", vista);
        verify(model).addAttribute("zona", z);

        verify(model).addAttribute(eq("codigosPostales"), captor.capture());

        List<CodigoPostal> cpList = captor.getValue();
        assertEquals(1, cpList.size());
        assertEquals("45002", cpList.get(0).getCodigo());
    }

    @Test
        @DisplayName("mostrarZona: id válido con zona y múltiples ZonaCodigoPostal")
        void testMostrarZonaMultiplesZonaCodigoPostal() {
            Zona z = new Zona(1, "Talavera");
            CodigoPostal cp1 = new CodigoPostal(2, "45002");
            CodigoPostal cp2 = new CodigoPostal(3, "45003");
            ZonaCodigoPostal zcp1 = new ZonaCodigoPostal(10, cp1, z);
            ZonaCodigoPostal zcp2 = new ZonaCodigoPostal(11, cp2, new Zona(2, "Toledo"));

            when(serviceZonaMock.findById(1)).thenReturn(Optional.of(z));
            when(serviceZonaCodigoPostalMock.findAll()).thenReturn(Arrays.asList(zcp1, zcp2));

            String vista = gestorZona.mostrarZona(1, model);
            assertEquals("/administrador/VerZona", vista);

            verify(model).addAttribute("zona", z);
            verify(model).addAttribute(eq("codigosPostales"), captor.capture());

            List<CodigoPostal> cpList = captor.getValue();
            assertEquals(1, cpList.size());
            assertEquals("45002", cpList.get(0).getCodigo());
        }

    @Test
    @DisplayName("mostrarZona: id válido con zona sin codigosPostales")
    void testMostrarZonaSinCodigos() {
        Zona z = new Zona(1, "Talavera");
        when(serviceZonaMock.findById(1)).thenReturn(Optional.of(z));
        when(serviceZonaCodigoPostalMock.findAll()).thenReturn(Collections.emptyList());

        String vista = gestorZona.mostrarZona(1, model);
        assertEquals("/administrador/VerZona", vista);
        verify(model).addAttribute("zona", z);
        verify(model).addAttribute("codigosPostales", Collections.emptyList());
    }

    @Test
    @DisplayName("mostrarZona: id no existente")
    void testMostrarZonaNoExiste() {
        when(serviceZonaMock.findById(9999)).thenReturn(Optional.empty());

        String vista = gestorZona.mostrarZona(9999, model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZona: id negativo")
    void testMostrarZonaNegativo() {
        when(serviceZonaMock.findById(-1)).thenReturn(Optional.empty());

        String vista = gestorZona.mostrarZona(-1, model);
        assertEquals(ERROR_VIEW, vista);
        verify(model).addAttribute(eq(ERROR_VIEW), anyString());
    }

    @Test
    @DisplayName("mostrarZona: id nulo (conjetura)")
    void testMostrarZonaNulo() {
        assertThrows(NullPointerException.class, () -> gestorZona.mostrarZona(null, model));
    }

    @Test
    @DisplayName("mostrarZona: error BD (conjetura)")
    void testMostrarZonaErrorBD() {
        when(serviceZonaMock.findById(1)).thenThrow(new RuntimeException("Error BD"));
        assertThrows(RuntimeException.class, () -> gestorZona.mostrarZona(1, model));
    }
}
