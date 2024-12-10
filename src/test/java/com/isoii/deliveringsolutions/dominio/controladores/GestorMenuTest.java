package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.CartaMenu;
import com.isoii.deliveringsolutions.dominio.entidades.ItemMenu;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.service.ServiceCartaMenu;
import com.isoii.deliveringsolutions.dominio.service.ServiceItemMenu;
import com.isoii.deliveringsolutions.dominio.service.ServiceRestaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GestorMenuTest {

    @InjectMocks
    private GestorMenu gestorMenu;

    @Mock
    private ServiceCartaMenu serviceCartaMenu;

    @Mock
    private ServiceItemMenu serviceItemMenu;

    @Mock
    private ServiceRestaurant serviceRestaurant;

    @Mock
    private Model model;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test: findAll()
    @Test
    public void testFindAll_WithResults() {
        when(serviceCartaMenu.findAll()).thenReturn(List.of(new CartaMenu()));
        List<CartaMenu> result = gestorMenu.findAll();
        assertFalse(result.isEmpty());
        verify(serviceCartaMenu).findAll();
    }

    @Test
    public void testFindAll_EmptyList() {
        when(serviceCartaMenu.findAll()).thenReturn(List.of());
        List<CartaMenu> result = gestorMenu.findAll();
        assertTrue(result.isEmpty());
        verify(serviceCartaMenu).findAll();
    }

    // Test: findById()
    @Test
    public void testFindById_ValidId() {
        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(new CartaMenu()));
        CartaMenu result = gestorMenu.findById(1);
        assertNotNull(result);
        verify(serviceCartaMenu).findById(1);
    }

    @Test
    public void testFindById_NotFound() {
        when(serviceCartaMenu.findById(999)).thenReturn(Optional.empty());
        CartaMenu result = gestorMenu.findById(999);
        assertNull(result);
        verify(serviceCartaMenu).findById(999);
    }

    @Test
    public void testFindById_NullId() {
        CartaMenu result = gestorMenu.findById(null);
        assertNull(result);
    }

    // Test: register/{id}
    @Test
    public void testMostrarFormularioRegistro_ValidId() {
        when(serviceRestaurant.findById("rest1")).thenReturn(Optional.of(new Restaurante()));
        String result = gestorMenu.mostrarFormularioRegistro("rest1", model);
        assertEquals("Pruebas-RegisterMenu", result);
        verify(model).addAttribute(eq("restaurante"), any());
    }

    @Test
    public void testMostrarFormularioRegistro_InvalidId() {
        when(serviceRestaurant.findById("999")).thenReturn(Optional.empty());
        String result = gestorMenu.mostrarFormularioRegistro("999", model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "Restaurante no encontrado");
    }

    @Test
    public void testMostrarFormularioRegistro_NullId() {
        String result = gestorMenu.mostrarFormularioRegistro(null, model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "Restaurante no encontrado");
    }

    // Test: eliminarCarta/{id}
    @Test
    public void testEliminarCarta_ValidId() {
        CartaMenu cartaMenu = new CartaMenu();
        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));
        ResponseEntity<CartaMenu> response = gestorMenu.eliminarCarta(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceCartaMenu).deleteById(1);
    }

    @Test
    public void testEliminarCarta_NotFound() {
        when(serviceCartaMenu.findById(999)).thenReturn(Optional.empty());
        ResponseEntity<CartaMenu> response = gestorMenu.eliminarCarta(999);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(serviceCartaMenu, never()).deleteById(anyInt());
    }

    // Test: eliminarItem/{id}
    @Test
    public void testEliminarItem_ValidId() {
        ItemMenu itemMenu = new ItemMenu();
        when(serviceItemMenu.findById(1)).thenReturn(Optional.of(itemMenu));
        ResponseEntity<Void> response = gestorMenu.eliminarItem(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        serviceItemMenu.findById(1);
        serviceItemMenu.deleteById(0);
    }

    @Test
    public void testEliminarItem_NotFound() {
        when(serviceItemMenu.findById(999)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = gestorMenu.eliminarItem(999);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(serviceItemMenu, never()).deleteById(anyInt());
    }

    // Test: mostrarItems()
    @Test
    public void testMostrarItems_WithResults() {
        when(serviceItemMenu.findAll()).thenReturn(List.of(new ItemMenu()));
        String result = gestorMenu.mostrarItems(model);
        assertEquals("/administrador/ListaItemsMenu", result);
        verify(model).addAttribute(eq("items"), anyList());
    }

    @Test
    public void testMostrarItems_EmptyList() {
        when(serviceItemMenu.findAll()).thenReturn(List.of());
        String result = gestorMenu.mostrarItems(model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "No se encontraron items");
    }

    // Test: mostrarMenu/{id}
    @Test
    public void testMostrarMenu_ValidId() {
        CartaMenu cartaMenu = new CartaMenu();
        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));
        String result = gestorMenu.mostrarMenu(1, model);
        assertEquals("/administrador/VerMenu", result);
        verify(model).addAttribute("cartaMenu", cartaMenu);
    }

    @Test
    public void testMostrarMenu_NotFound() {
        when(serviceCartaMenu.findById(999)).thenReturn(Optional.empty());
        String result = gestorMenu.mostrarMenu(999, model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "Menú no encontrado");
    }
}
