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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class GestorMenuTest {

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

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test: findAll()
    @Test
    void testFindAll_WithResults() {
        when(serviceCartaMenu.findAll()).thenReturn(List.of(new CartaMenu()));
        List<CartaMenu> result = gestorMenu.findAll();
        assertFalse(result.isEmpty());
        verify(serviceCartaMenu).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        when(serviceCartaMenu.findAll()).thenReturn(List.of());
        List<CartaMenu> result = gestorMenu.findAll();
        assertTrue(result.isEmpty());
        verify(serviceCartaMenu).findAll();
    }

    // Test: findById()
    @Test
    void testFindById_ValidId() {
        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(new CartaMenu()));
        CartaMenu result = gestorMenu.findById(1);
        assertNotNull(result);
        verify(serviceCartaMenu).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(serviceCartaMenu.findById(999)).thenReturn(Optional.empty());
        CartaMenu result = gestorMenu.findById(999);
        assertNull(result);
        verify(serviceCartaMenu).findById(999);
    }

    @Test
    void testFindById_NullId() {
        CartaMenu result = gestorMenu.findById(null);
        assertNull(result);
    }

    @Test
    void testMostrarFormularioRegistro_ValidId() {
        when(serviceRestaurant.findById("rest1")).thenReturn(Optional.of(new Restaurante()));
        String result = gestorMenu.mostrarFormularioRegistro("rest1", model);
        assertEquals("Pruebas-RegisterMenu", result);
        verify(model).addAttribute("restaurante", any());
    }

    @Test
    void testMostrarFormularioRegistro_InvalidId() {
        when(serviceRestaurant.findById("999")).thenReturn(Optional.empty());
        String result = gestorMenu.mostrarFormularioRegistro("999", model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "Restaurante no encontrado");
    }

    @Test
    void testMostrarFormularioRegistro_NullId() {
        String result = gestorMenu.mostrarFormularioRegistro(null, model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "Restaurante no encontrado");
    }

    @Test
    void testEliminarCarta_ValidId() {
        CartaMenu cartaMenu = new CartaMenu();
        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));
        ResponseEntity<CartaMenu> response = gestorMenu.eliminarCarta(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceCartaMenu).deleteById(1);
    }

    @Test
    void testEliminarCarta_NotFound() {
        when(serviceCartaMenu.findById(999)).thenReturn(Optional.empty());
        ResponseEntity<CartaMenu> response = gestorMenu.eliminarCarta(999);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(serviceCartaMenu, never()).deleteById(anyInt());
    }

    @Test
    void testEliminarItem_ValidId() {
        ItemMenu itemMenu = new ItemMenu();
        when(serviceItemMenu.findById(1)).thenReturn(Optional.of(itemMenu));
        ResponseEntity<Void> response = gestorMenu.eliminarItem(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        serviceItemMenu.findById(1);
        serviceItemMenu.deleteById(0);
    }

    @Test
    void testEliminarItem_NotFound() {
        when(serviceItemMenu.findById(999)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = gestorMenu.eliminarItem(999);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(serviceItemMenu, never()).deleteById(anyInt());
    }

    @Test
    void testMostrarItems_WithResults() {
        when(serviceItemMenu.findAll()).thenReturn(List.of(new ItemMenu()));
        String result = gestorMenu.mostrarItems(model);
        assertEquals("/administrador/ListaItemsMenu", result);
        verify(model).addAttribute("items", anyList());
    }

    @Test
    void testMostrarItems_EmptyList() {
        when(serviceItemMenu.findAll()).thenReturn(List.of());
        String result = gestorMenu.mostrarItems(model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "No se encontraron items");
    }

    @Test
    void testMostrarMenu_ValidId() {
        CartaMenu cartaMenu = new CartaMenu();
        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));
        String result = gestorMenu.mostrarMenu(1, model);
        assertEquals("/administrador/VerMenu", result);
        verify(model).addAttribute("cartaMenu", cartaMenu);
    }

    @Test
    void testMostrarMenu_NotFound() {
        when(serviceCartaMenu.findById(999)).thenReturn(Optional.empty());
        String result = gestorMenu.mostrarMenu(999, model);
        assertEquals("error", result);
        verify(model).addAttribute("error", "Menú no encontrado");
    }

    @Test
    void testMostrarFormularioRegistroItem() {
        String viewName = gestorMenu.mostrarFormularioRegistroItem(model);
        assertEquals("Pruebas-RegisterItemMenu", viewName);
        verify(model).addAttribute("itemMenu", any(ItemMenu.class));
    }

    @Test
    void testMostrarFormularioModificar_CartaMenuExists() {
        Integer id = 1;
        CartaMenu cartaMenu = new CartaMenu();
        when(serviceCartaMenu.findById(id)).thenReturn(Optional.of(cartaMenu));

        String viewName = gestorMenu.mostrarFormularioModificar(id, model);

        assertEquals("gestorItems", viewName);
        verify(model).addAttribute("cartaMenu", cartaMenu);
        verify(serviceItemMenu).findByCartaMenu(cartaMenu);
    }

    @Test
    void testMostrarFormularioModificar_CartaMenuNotFound() {
        Integer id = 999;
        when(serviceCartaMenu.findById(id)).thenReturn(Optional.empty());

        String viewName = gestorMenu.mostrarFormularioModificar(id, model);

        assertEquals("error", viewName);
        verify(model).addAttribute("error", "Carta no encontrada");
    }

    @Test
    void testRegistrarItem_Success() {
        // Configuración del objeto ItemMenu
        ItemMenu itemMenu = new ItemMenu();
        CartaMenu cartaMenu = new CartaMenu();
        cartaMenu.setIdCarta(1); 
        itemMenu.setCartamenu(cartaMenu);
        itemMenu.setNombre("Test Item");
        itemMenu.setTipo("Main Course");
        itemMenu.setPrecio(10.0);

        // Mock del comportamiento del serviceCartaMenu
        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));

        // Llamada al método
        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        // Verificación
        assertEquals("redirect:/cartas/modificar/1", viewName);
        verify(serviceItemMenu).save(itemMenu); // Verificar que se haya llamado a save
        verify(model).addAttribute("success", "Item registrado exitosamente.");
    }


    @Test
    void testRegistrarItem_CartaMenuNotFound() {
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setCartamenu(new CartaMenu());

        when(serviceCartaMenu.findById(anyInt())).thenReturn(Optional.empty());

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/" + itemMenu.getCartamenu().getIdCarta(), viewName);
    }

    @Test
    void testRegistrarCarta_RestauranteNotFound() {
        CartaMenu cartaMenu = new CartaMenu();

        when(serviceRestaurant.findById(any())).thenReturn(Optional.empty());

        String viewName = gestorMenu.registrarCarta(cartaMenu, redirectAttributes);

        assertEquals("redirect:/cartas/register", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Restaurante no válido");
    }

    @Test
    void testMostrarItems_ItemsFound() {
        when(serviceItemMenu.findAll()).thenReturn(List.of(new ItemMenu()));

        String viewName = gestorMenu.mostrarItems(model);

        assertEquals("/administrador/ListaItemsMenu", viewName);
        verify(model).addAttribute("items", anyList());
    }

    @Test
    void testMostrarItems_ItemsNotFound() {
        when(serviceItemMenu.findAll()).thenReturn(List.of());

        String viewName = gestorMenu.mostrarItems(model);

        assertEquals("error", viewName);
        verify(model).addAttribute("error", "No se encontraron items");
    }

    @Test
    void testFindAllItems_WithResults() {
        // Mock del comportamiento del servicio para devolver una lista de ItemMenu
        List<ItemMenu> items = List.of(new ItemMenu(), new ItemMenu());
        when(serviceItemMenu.findAll()).thenReturn(items);

        // Llama al método
        List<ItemMenu> result = gestorMenu.findAllItems();

        // Verifica que se devuelve la lista esperada
        assertEquals(2, result.size());
        verify(serviceItemMenu).findAll(); 
    }

    @Test
    void testFindAllItems_EmptyList() {
        // Mock del comportamiento del servicio para devolver una lista vacía
        when(serviceItemMenu.findAll()).thenReturn(List.of());

        // Llama al método
        List<ItemMenu> result = gestorMenu.findAllItems();

        // Verifica que se devuelve una lista vacía
        assertTrue(result.isEmpty());
        verify(serviceItemMenu).findAll(); 
    }

    @Test
    void testRegistrarItem_CartamenuNoEncontrado() {
        ItemMenu itemMenu = new ItemMenu();
        CartaMenu cartaMenu = new CartaMenu();
        cartaMenu.setIdCarta(1);
        itemMenu.setCartamenu(cartaMenu);

        when(serviceCartaMenu.findById(1)).thenReturn(Optional.empty());

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/1", viewName);
        verify(model).addAttribute("error", "Carta no encontrada");
    }

    @Test
    void testRegistrarItem_NombreVacio() {
        ItemMenu itemMenu = new ItemMenu();
        CartaMenu cartaMenu = new CartaMenu();
        cartaMenu.setIdCarta(1);
        itemMenu.setCartamenu(cartaMenu);
        itemMenu.setNombre(""); 

        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/1", viewName);
        verify(model).addAttribute("error", "Carta no válida, nombre no puede estar vacío");
    }

    @Test
    void testRegistrarItem_TipoVacio() {
        ItemMenu itemMenu = new ItemMenu();
        CartaMenu cartaMenu = new CartaMenu();
        cartaMenu.setIdCarta(1);
        itemMenu.setCartamenu(cartaMenu);
        itemMenu.setNombre("Nombre válido");
        itemMenu.setTipo(""); // Tipo vacío

        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/1", viewName);
        verify(model).addAttribute("error", "Carta no válida, tipo no puede estar vacío");
    }

    @Test
    void testRegistrarItem_PrecioInvalido() {
        ItemMenu itemMenu = new ItemMenu();
        CartaMenu cartaMenu = new CartaMenu();
        cartaMenu.setIdCarta(1);
        itemMenu.setCartamenu(cartaMenu);
        itemMenu.setNombre("Nombre válido");
        itemMenu.setTipo("Tipo válido");
        itemMenu.setPrecio(0); // Precio inválido

        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/1", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "El precio debe ser mayor que 0");
    }

    @Test
    void testRegistrarItem_Success2() {
        ItemMenu itemMenu = new ItemMenu();
        CartaMenu cartaMenu = new CartaMenu();
        cartaMenu.setIdCarta(1);
        itemMenu.setCartamenu(cartaMenu);
        itemMenu.setNombre("Nombre válido");
        itemMenu.setTipo("Tipo válido");
        itemMenu.setPrecio(10.0); // Precio válido

        when(serviceCartaMenu.findById(1)).thenReturn(Optional.of(cartaMenu));

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/1", viewName);
        verify(serviceItemMenu).save(itemMenu); // Verifica que se guardó el item
        verify(model).addAttribute("success", "Item registrado exitosamente.");
    }

    @Test
    void testRegistrarCarta_RestauranteNulo() {
        CartaMenu cartaMenu = new CartaMenu(); // Sin restaurante asociado

        String viewName = gestorMenu.registrarCarta(cartaMenu, redirectAttributes);

        assertEquals("redirect:/cartas/register", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Restaurante no válido");
    }

    @Test
    void testRegistrarCarta_IdUsuarioNulo() {
        CartaMenu cartaMenu = new CartaMenu();
        Restaurante restaurante = new Restaurante();
        cartaMenu.setRestaurante(restaurante); // Restaurante sin ID

        String viewName = gestorMenu.registrarCarta(cartaMenu, redirectAttributes);

        assertEquals("redirect:/cartas/register", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Restaurante no válido");
    }

    @Test
    void testRegistrarCarta_NombreVacio() {
        CartaMenu cartaMenu = new CartaMenu();
        Restaurante restaurante = new Restaurante("1", "pass", "restaurante", "cif", "nombre");
        cartaMenu.setRestaurante(restaurante); // Restaurante válido
        cartaMenu.setNombre(""); // Nombre vacío

        String viewName = gestorMenu.registrarCarta(cartaMenu, redirectAttributes);

        assertEquals("redirect:/cartas/register", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "El nombre de la carta no puede estar vacío");
    }

    @Test
    void testRegistrarCarta_RestauranteNoEncontrado() {
        CartaMenu cartaMenu = new CartaMenu();
        Restaurante restaurante = new Restaurante("1", "pass", "restaurante", "cif", "nombre");
        cartaMenu.setRestaurante(restaurante);
        cartaMenu.setNombre("Carta válida");

        when(serviceRestaurant.findById("1")).thenReturn(Optional.empty()); // Restaurante no encontrado

        String viewName = gestorMenu.registrarCarta(cartaMenu, redirectAttributes);

        assertEquals("redirect:/cartas/register", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Restaurante no encontrado");
    }

    @Test
    void testRegistrarCarta_Success() {
        CartaMenu cartaMenu = new CartaMenu();
        Restaurante restaurante = new Restaurante("1", "pass", "restaurante", "cif", "nombre");
        cartaMenu.setRestaurante(restaurante);
        cartaMenu.setNombre("Carta válida");

        when(serviceRestaurant.findById("1")).thenReturn(Optional.of(restaurante)); // Restaurante encontrado

        String viewName = gestorMenu.registrarCarta(cartaMenu, redirectAttributes);

        assertEquals("redirect:/restaurantes/gestion/1", viewName);
        verify(serviceCartaMenu).save(cartaMenu); // Verifica que se guardó la carta
        verify(redirectAttributes).addFlashAttribute("success", "Carta registrada exitosamente.");
    }


    @Test
    void testMostrarItem_ItemExists() {
        Integer id = 1;
        ItemMenu itemMenu = new ItemMenu();
        when(serviceItemMenu.findById(id)).thenReturn(Optional.of(itemMenu));

        String viewName = gestorMenu.mostrarItem(id, model);

        assertEquals("/administrador/VerItemMenu", viewName);
        verify(model).addAttribute("itemMenu", itemMenu);
    }

    @Test
    void testMostrarItem_ItemNotFound() {
        Integer id = 1;
        when(serviceItemMenu.findById(id)).thenReturn(Optional.empty());

        String viewName = gestorMenu.mostrarItem(id, model);

        assertEquals("error", viewName);
        verify(model).addAttribute("error", "Item no encontrado");
    }

    @Test
    void testMostrarMenus_MenusFound() {
        List<CartaMenu> cartas = List.of(new CartaMenu(), new CartaMenu());
        when(serviceCartaMenu.findAll()).thenReturn(cartas);

        String viewName = gestorMenu.mostrarMenus(model);

        assertEquals("/administrador/ListaMenus", viewName);
        verify(model).addAttribute("cartas", cartas);
    }

    @Test
    void testMostrarMenus_NoMenusFound() {
        when(serviceCartaMenu.findAll()).thenReturn(List.of());

        String viewName = gestorMenu.mostrarMenus(model);

        assertEquals("error", viewName);
        verify(model).addAttribute("error", "No se encontraron menús");
    }


    @Test
    void testRegistrarItem_CartamenuEsNull() {
        ItemMenu itemMenu = new ItemMenu(); // No se establece cartamenu

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/null", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "Carta no válida");
    }

    @Test
    void testRegistrarItem_IdCartaEsCero() {
        ItemMenu itemMenu = new ItemMenu();
        CartaMenu cartaMenu = new CartaMenu();
        cartaMenu.setIdCarta(0); // ID inválido
        itemMenu.setCartamenu(cartaMenu);

        String viewName = gestorMenu.registrarItem(itemMenu, model, redirectAttributes, bindingResult);

        assertEquals("redirect:/cartas/modificar/0", viewName);
        verify(redirectAttributes).addFlashAttribute("error", "ID de Carta no puede ser 0");
    }


}
