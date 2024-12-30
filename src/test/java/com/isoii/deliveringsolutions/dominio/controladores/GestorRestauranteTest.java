package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.CartaMenu;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.service.ServiceCartaMenu;
import com.isoii.deliveringsolutions.dominio.service.ServiceDireccion;
import com.isoii.deliveringsolutions.dominio.service.ServiceRestaurant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestorRestauranteTest {

    @Mock
    private ServiceRestaurant serviceRestaurant;

    @Mock
    private ServiceCartaMenu serviceCartaMenu;

    @Mock
    private ServiceDireccion serviceDireccion;

    @Mock
    private Model model;

    @InjectMocks
    private GestorRestaurante gestorRestaurante;

    private static final String ERROR_VIEW = "error"; 
    private static final String RESTAURANTE_NO_ENCONTRADO = "Restaurante no encontrado"; 

    @Nested
    @DisplayName("Tests para findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar una lista de restaurantes cuando existen")
        void testFindAll_conRestaurantes() {
            List<Restaurante> restaurantes = Arrays.asList(new Restaurante(), new Restaurante());

            when(serviceRestaurant.findAll()).thenReturn(restaurantes);

            List<Restaurante> resultado = gestorRestaurante.findAll();

            assertNotNull(resultado, "El resultado no debe ser null");
            assertEquals(2, resultado.size(), "La lista debe contener 2 restaurantes");
            verify(serviceRestaurant, times(1)).findAll();
        }

        @Test
        @DisplayName("Debe retornar una lista vacía cuando no existen restaurantes")
        void testFindAll_sinRestaurantes() {
            List<Restaurante> restaurantes = Collections.emptyList();

            when(serviceRestaurant.findAll()).thenReturn(restaurantes);

            List<Restaurante> resultado = gestorRestaurante.findAll();

            assertNotNull(resultado, "El resultado no debe ser null");
            assertTrue(resultado.isEmpty(), "La lista debe estar vacía");
            verify(serviceRestaurant, times(1)).findAll();
        }

        @Test
        @DisplayName("Debe manejar correctamente cuando el servicio retorna null")
        void testFindAll_servicioRetornaNull() {
            when(serviceRestaurant.findAll()).thenReturn(null);

            List<Restaurante> resultado = gestorRestaurante.findAll();

            assertNull(resultado, "El resultado debe ser null cuando el servicio retorna null");
            verify(serviceRestaurant, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Tests para mostrarFormularioRegistro")
    class MostrarFormularioRegistroTests {

        @Test
        @DisplayName("Debe retornar la vista 'Pruebas-RegisterRestaurante'")
        void testMostrarFormularioRegistro_RetornaVistaCorrecta() {
            String vista = gestorRestaurante.mostrarFormularioRegistro();
            assertEquals("Pruebas-RegisterRestaurante", vista, "El método debe retornar el nombre de la vista 'Pruebas-RegisterRestaurante'");
        }
    }

    @Nested
    @DisplayName("Tests para findById")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar un restaurante si el id existe")
        void testFindById_existe() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setIdUsuario(id);
            restaurante.setNombre("Restaurante Ejemplo");

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));

            Restaurante resultado = gestorRestaurante.findById(id);

            assertNotNull(resultado, "El método debe retornar una instancia de Restaurante");
            assertEquals(id, resultado.getIdUsuario(), "El id del restaurante debe coincidir con el proporcionado");
            assertEquals("Restaurante Ejemplo", resultado.getNombre(), "El nombre del restaurante debe coincidir");
            verify(serviceRestaurant, times(1)).findById(id);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"nonExistentId", "", "!!!"})
        @DisplayName("Debe retornar null cuando el id es nulo, inexistente, vacío o con formato incorrecto")
        void testFindById_invalidCases(String invalidId) {
            when(serviceRestaurant.findById(invalidId)).thenReturn(Optional.empty());
            Restaurante resultado = gestorRestaurante.findById(invalidId);
            assertNull(resultado, "El método debe retornar null cuando el id es " + invalidId);
            verify(serviceRestaurant, times(1)).findById(invalidId);
        }

        @Test
        @DisplayName("Debe propagar la excepción si el servicio lanza una excepción")
        void testFindById_servicioLanzaExcepcion() {
            String id = "restaurante123";

            when(serviceRestaurant.findById(id)).thenThrow(new RuntimeException("Error en el servicio"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                gestorRestaurante.findById(id);
            }, "El método debe propagar la excepción lanzada por el servicio");

            assertEquals("Error en el servicio", exception.getMessage(), "El mensaje de la excepción debe coincidir");
            verify(serviceRestaurant, times(1)).findById(id);
        }
    }

    @Nested
    @DisplayName("Tests para buscarRestaurante")
    class BuscarRestauranteTests {

        @Test
        @DisplayName("Debe redirigir correctamente cuando el restaurante existe")
        void testBuscarRestaurante_existe() {
            String nombre = "RestauranteEjemplo";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre(nombre);

            when(serviceRestaurant.findById(nombre)).thenReturn(Optional.of(restaurante));

            String resultado = gestorRestaurante.buscarRestaurante(nombre, model);

            assertEquals("redirect:/restaurantes/findById/RestauranteEjemplo", resultado, "Debe redirigir a la página del restaurante encontrado");
            verify(serviceRestaurant, times(1)).findById(nombre);
            verify(model, never()).addAttribute(eq(ERROR_VIEW), anyString());
            verifyNoMoreInteractions(model);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"RestauranteInexistente", "", "!!!"})
        @DisplayName("Debe retornar ERROR_VIEW cuando el nombre es nulo, inexistente, vacío o contiene caracteres especiales")
        void testBuscarRestaurante_invalidNames(String invalidName) {
            when(serviceRestaurant.findById(invalidName)).thenReturn(Optional.empty());
            String resultado = gestorRestaurante.buscarRestaurante(invalidName, model);
            assertEquals(ERROR_VIEW, resultado, "Debe retornar la vista de error cuando el nombre es " + invalidName);
            verify(serviceRestaurant, times(1)).findById(invalidName);
            verify(model, times(1)).addAttribute(ERROR_VIEW, RESTAURANTE_NO_ENCONTRADO);
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe propagar la excepción cuando el servicio lanza una excepción")
        void testBuscarRestaurante_servicioLanzaExcepcion() {
            String nombre = "RestauranteEjemplo";

            when(serviceRestaurant.findById(nombre)).thenThrow(new RuntimeException("Error en el servicio"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                gestorRestaurante.buscarRestaurante(nombre, model);
            }, "Debe propagar la excepción lanzada por el servicio");

            assertEquals("Error en el servicio", exception.getMessage(), "El mensaje de la excepción debe coincidir");
            verify(serviceRestaurant, times(1)).findById(nombre);
            verify(model, never()).addAttribute(eq(ERROR_VIEW), anyString());
            verifyNoMoreInteractions(model);
        }
    }

    @Nested
    @DisplayName("Tests para gestionRestaurante")
    class GestionRestauranteTests {

        @Test
        @DisplayName("Debe gestionar correctamente un restaurante existente con menús y direcciones no vacíos")
        void testGestionRestaurante_existe_conMenus_yDirecciones() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("RestauranteEjemplo");

            List<CartaMenu> menus = Arrays.asList(new CartaMenu(), new CartaMenu());
            List<Direccion> direcciones = Arrays.asList(new Direccion(), new Direccion());

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenReturn(menus);
            when(serviceDireccion.findByUsuario(restaurante)).thenReturn(direcciones);

            String resultado = gestorRestaurante.gestionRestaurante(id, model);

            assertEquals("interfazGestionRestaurante", resultado, "Debe retornar la vista 'interfazGestionRestaurante'");
            verify(serviceRestaurant, times(1)).findById(id);
            verify(serviceCartaMenu, times(1)).findByRestaurante(restaurante);
            verify(serviceDireccion, times(1)).findByUsuario(restaurante);
            verify(model).addAttribute("restaurante", restaurante);
            verify(model).addAttribute("menus", menus);
            verify(model).addAttribute("direcciones", direcciones);
            verify(model).addAttribute("direccion", direcciones);
            verifyNoMoreInteractions(model);
        }


        @Test
        @DisplayName("Debe gestionar correctamente un restaurante existente con menús vacíos y direcciones")
        void testGestionRestaurante_existe_conMenusVacios_yDirecciones() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("RestauranteEjemplo");

            List<CartaMenu> menus = Collections.emptyList();
            List<Direccion> direcciones = Arrays.asList(new Direccion());

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenReturn(menus);
            when(serviceDireccion.findByUsuario(restaurante)).thenReturn(direcciones);

            String resultado = gestorRestaurante.gestionRestaurante(id, model);

            assertEquals("interfazGestionRestaurante", resultado, "Debe retornar la vista 'interfazGestionRestaurante'");
            verify(serviceRestaurant, times(1)).findById(id);
            verify(serviceCartaMenu, times(1)).findByRestaurante(restaurante);
            verify(serviceDireccion, times(1)).findByUsuario(restaurante);
            verify(model).addAttribute("restaurante", restaurante);
            verify(model).addAttribute("menus", menus);
            verify(model).addAttribute("direcciones", direcciones);
            verify(model).addAttribute("direccion", direcciones);
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe gestionar correctamente un restaurante existente con direcciones null")
        void testGestionRestaurante_existe_conDireccionesNull() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("RestauranteEjemplo");

            List<CartaMenu> menus = Arrays.asList(new CartaMenu());

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenReturn(menus);
            when(serviceDireccion.findByUsuario(restaurante)).thenReturn(null);

            String resultado = gestorRestaurante.gestionRestaurante(id, model);

            assertEquals("interfazGestionRestaurante", resultado, "Debe retornar la vista 'interfazGestionRestaurante'");
            verify(serviceRestaurant, times(1)).findById(id);
            verify(serviceCartaMenu, times(1)).findByRestaurante(restaurante);
            verify(serviceDireccion, times(1)).findByUsuario(restaurante);
            verify(model).addAttribute("restaurante", restaurante);
            verify(model).addAttribute("menus", menus);
        }

        @Test
        @DisplayName("Debe gestionar correctamente un restaurante existente con direcciones vacías")
        void testGestionRestaurante_existe_conDireccionesVacias() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("RestauranteEjemplo");

            List<CartaMenu> menus = Arrays.asList(new CartaMenu());
            List<Direccion> direcciones = Collections.emptyList();

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenReturn(menus);
            when(serviceDireccion.findByUsuario(restaurante)).thenReturn(direcciones);

            String resultado = gestorRestaurante.gestionRestaurante(id, model);

            assertEquals("interfazGestionRestaurante", resultado, "Debe retornar la vista 'interfazGestionRestaurante'");
            verify(serviceRestaurant, times(1)).findById(id);
            verify(serviceCartaMenu, times(1)).findByRestaurante(restaurante);
            verify(serviceDireccion, times(1)).findByUsuario(restaurante);
            verify(model).addAttribute("restaurante", restaurante);
            verify(model).addAttribute("menus", menus);
            verify(model).addAttribute("direcciones", direcciones);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"restauranteInexistente", "", "!!!"})
        @DisplayName("Debe retornar ERROR_VIEW cuando el id es nulo, inexistente, vacío o con caracteres especiales")
        void testGestionRestaurante_invalidIds(String invalidId) {
            when(serviceRestaurant.findById(invalidId)).thenReturn(Optional.empty());
            String resultado = gestorRestaurante.gestionRestaurante(invalidId, model);
            assertEquals(ERROR_VIEW, resultado, "Debe retornar la vista de error cuando el id es " + invalidId);
            verify(serviceRestaurant, times(1)).findById(invalidId);
            verify(serviceCartaMenu, never()).findByRestaurante(any());
            verify(serviceDireccion, never()).findByUsuario(any());
            verify(model).addAttribute(ERROR_VIEW, RESTAURANTE_NO_ENCONTRADO);
            verify(model, never()).addAttribute(eq("restaurante"), any());
            verify(model, never()).addAttribute(eq("menus"), any());
            verify(model, never()).addAttribute(eq("direcciones"), any());
            verify(model, never()).addAttribute(eq("direccion"), any());
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe propagar la excepción cuando serviceRestaurant.findById lanza una excepción")
        void testGestionRestaurante_servicioRestaurantLanzaExcepcion() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("RestauranteEjemplo");

            when(serviceRestaurant.findById(id)).thenThrow(new RuntimeException("Error en el servicio"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                gestorRestaurante.gestionRestaurante(id, model);
            }, "Debe propagar la excepción lanzada por serviceRestaurant.findById");

            assertEquals("Error en el servicio", exception.getMessage(), "El mensaje de la excepción debe coincidir");
            verify(serviceRestaurant, times(1)).findById(id);
            verify(serviceCartaMenu, never()).findByRestaurante(any());
            verify(serviceDireccion, never()).findByUsuario(any());
            verify(model, never()).addAttribute(anyString(), any());
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe propagar la excepción cuando serviceCartaMenu.findByRestaurante lanza una excepción")
        void testGestionRestaurante_servicioCartaMenuLanzaExcepcion() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("RestauranteEjemplo");

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenThrow(new RuntimeException("Error en serviceCartaMenu"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                gestorRestaurante.gestionRestaurante(id, model);
            }, "Debe propagar la excepción lanzada por serviceCartaMenu.findByRestaurante");

            assertEquals("Error en serviceCartaMenu", exception.getMessage(), "El mensaje de la excepción debe coincidir");
            verify(serviceRestaurant, times(1)).findById(id);
            verify(serviceCartaMenu, times(1)).findByRestaurante(restaurante);
            verify(serviceDireccion, never()).findByUsuario(any());
            verify(model, never()).addAttribute(anyString(), any());
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe propagar la excepción cuando serviceDireccion.findByUsuario lanza una excepción")
        void testGestionRestaurante_servicioDireccionLanzaExcepcion() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre("RestauranteEjemplo");

            List<CartaMenu> menus = Arrays.asList(new CartaMenu());

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));
            when(serviceCartaMenu.findByRestaurante(restaurante)).thenReturn(menus);
            when(serviceDireccion.findByUsuario(restaurante)).thenThrow(new RuntimeException("Error en serviceDireccion"));

            // Ejecutar el método y capturar la excepción
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                gestorRestaurante.gestionRestaurante(id, model);
            }, "Debe propagar la excepción lanzada por serviceDireccion.findByUsuario");

            assertEquals("Error en serviceDireccion", exception.getMessage(), "El mensaje de la excepción debe coincidir");

            // Verificar las interacciones con los servicios
            verify(serviceRestaurant, times(1)).findById(id);
            verify(serviceCartaMenu, times(1)).findByRestaurante(restaurante);
            verify(serviceDireccion, times(1)).findByUsuario(restaurante);

            // Verificar que "restaurante" y "menus" fueron agregados al modelo
            verify(model).addAttribute("restaurante", restaurante);
            verify(model).addAttribute("menus", menus);

            // Verificar que "direcciones" y "direccion" **no** fueron agregados al modelo debido a la excepción
            verify(model, never()).addAttribute(eq("direcciones"), any());
            verify(model, never()).addAttribute(eq("direccion"), any());

            // Asegurar que no haya más interacciones con el modelo
            verifyNoMoreInteractions(model);
        }
    }

    @Nested
    @DisplayName("Tests para mostrarRestraurante")
    class mostrarRestauranteTests {
    
        @Test
        @DisplayName("Debe retornar la lista de restaurantes cuando no es vacía")
        void testMostrarRestaurantes_listaNoVacia() {
            List<Restaurante> restaurantes = Arrays.asList(new Restaurante(), new Restaurante());

            when(serviceRestaurant.findAll()).thenReturn(restaurantes);

            String resultado = gestorRestaurante.mostrarRestaurantes(model);

            assertEquals("/administrador/ListaRestaurantes", resultado, "Debe retornar la vista de la lista de restaurantes");
            verify(serviceRestaurant, times(1)).findAll();
            verify(model).addAttribute("restaurantes", restaurantes);
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe retornar ERROR_VIEW cuando no hay restaurantes")
        void testMostrarRestaurantes_listaVacia() {
            List<Restaurante> restaurantes = Collections.emptyList();

            when(serviceRestaurant.findAll()).thenReturn(restaurantes);

            String resultado = gestorRestaurante.mostrarRestaurantes(model);

            assertEquals(ERROR_VIEW, resultado, "Debe retornar ERROR_VIEW cuando no hay restaurantes");
            verify(serviceRestaurant, times(1)).findAll();
            verify(model).addAttribute(ERROR_VIEW, "No se encontraron restaurantes");
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe retornar ERROR_VIEW cuando la lista de restaurantes es null")
        void testMostrarRestaurantes_listaNull() {
            when(serviceRestaurant.findAll()).thenReturn(null);

            String resultado = gestorRestaurante.mostrarRestaurantes(model);

            assertEquals(ERROR_VIEW, resultado, "Debe retornar ERROR_VIEW cuando la lista de restaurantes es null");
            verify(serviceRestaurant, times(1)).findAll();
            verify(model).addAttribute(ERROR_VIEW, "No se encontraron restaurantes");
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe mostrar los detalles de un restaurante existente")
        void testMostrarRestaurante_existe() {
            String id = "restaurante123";
            Restaurante restaurante = new Restaurante();
            restaurante.setIdUsuario(id);
            restaurante.setNombre("Restaurante Ejemplo");

            when(serviceRestaurant.findById(id)).thenReturn(Optional.of(restaurante));

            String resultado = gestorRestaurante.mostrarRestaurante(id, model);

            assertEquals("/administrador/VerRestaurante", resultado, "Debe retornar la vista de detalles del restaurante");
            verify(serviceRestaurant, times(1)).findById(id);
            verify(model).addAttribute("restaurante", restaurante);
            verifyNoMoreInteractions(model);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"restauranteInexistente", ""})
        @DisplayName("Debe retornar ERROR_VIEW para restaurantes no existentes, id nulo o vacío")
        void testMostrarRestaurante_invalidIds(String invalidId) {
            when(serviceRestaurant.findById(invalidId)).thenReturn(Optional.empty());
            String resultado = gestorRestaurante.mostrarRestaurante(invalidId, model);
            assertEquals(ERROR_VIEW, resultado, "Debe retornar ERROR_VIEW cuando el restaurante no existe o el id es nulo o vacío");
            verify(serviceRestaurant, times(1)).findById(invalidId);
            verify(model).addAttribute(ERROR_VIEW, RESTAURANTE_NO_ENCONTRADO);
            verifyNoMoreInteractions(model);
        }
    }

    @Nested
    @DisplayName("Tests para registrarRestaurante")
    class RegistrarRestauranteTests {

        @Test
        @DisplayName("Debe registrar correctamente un restaurante con contraseña válida")
        void testRegistrarRestaurante_conContraseñaValida() {
            Restaurante restaurante = new Restaurante();
            restaurante.setPass("password123");
            restaurante.setIdUsuario("usuario123");

            Restaurante restauranteRegistrado = new Restaurante();
            restauranteRegistrado.setIdUsuario("usuario123");

            when(serviceRestaurant.save(restaurante)).thenReturn(restauranteRegistrado);

            String resultado = gestorRestaurante.registrarRestaurante(restaurante, model);

            assertEquals("redirect:/restaurantes/gestion/usuario123", resultado, "Debe redirigir correctamente después del registro");
            verify(serviceRestaurant, times(1)).save(restaurante);
            verify(model, never()).addAttribute(eq(ERROR_VIEW), anyString());
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe retornar ERROR_VIEW cuando la contraseña es null")
        void testRegistrarRestaurante_conContraseñaNull() {
            Restaurante restaurante = new Restaurante();
            restaurante.setPass(null);

            String resultado = gestorRestaurante.registrarRestaurante(restaurante, model);

            assertEquals(ERROR_VIEW, resultado, "Debe retornar ERROR_VIEW cuando la contraseña es null");
            verify(serviceRestaurant, never()).save(any());
            verify(model, times(1)).addAttribute(ERROR_VIEW, "Contraseña no puede estar vacía");
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe retornar ERROR_VIEW cuando la contraseña es una cadena vacía")
        void testRegistrarRestaurante_conContraseñaVacia() {
            Restaurante restaurante = new Restaurante();
            restaurante.setPass("");

            String resultado = gestorRestaurante.registrarRestaurante(restaurante, model);

            assertEquals(ERROR_VIEW, resultado, "Debe retornar ERROR_VIEW cuando la contraseña es una cadena vacía");
            verify(serviceRestaurant, never()).save(any());
            verify(model, times(1)).addAttribute(ERROR_VIEW, "Contraseña no puede estar vacía");
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe registrar correctamente un restaurante y redirigir a gestion")
        void testRegistrarRestaurante_registroExitoso() {
            Restaurante restaurante = new Restaurante();
            restaurante.setPass("securePass");
            restaurante.setIdUsuario("usuario456");

            Restaurante restauranteRegistrado = new Restaurante();
            restauranteRegistrado.setIdUsuario("usuario456");

            when(serviceRestaurant.save(restaurante)).thenReturn(restauranteRegistrado);

            String resultado = gestorRestaurante.registrarRestaurante(restaurante, model);

            assertEquals("redirect:/restaurantes/gestion/usuario456", resultado, "Debe redirigir correctamente después del registro");
            verify(serviceRestaurant, times(1)).save(restaurante);
            verify(model, never()).addAttribute(eq(ERROR_VIEW), anyString());
            verifyNoMoreInteractions(model);
        }

        @Test
        @DisplayName("Debe propagar la excepción cuando el servicio save lanza una excepción")
        void testRegistrarRestaurante_servicioSaveLanzaExcepcion() {
            Restaurante restaurante = new Restaurante();
            restaurante.setPass("securePass");
            restaurante.setIdUsuario("usuario123");

            when(serviceRestaurant.save(restaurante)).thenThrow(new RuntimeException("Error al guardar el restaurante"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                gestorRestaurante.registrarRestaurante(restaurante, model);
            }, "Debe propagar la excepción lanzada por el servicio save");

            assertEquals("Error al guardar el restaurante", exception.getMessage(), "El mensaje de la excepción debe coincidir");
            verify(serviceRestaurant, times(1)).save(restaurante);
            verify(model, never()).addAttribute(eq(ERROR_VIEW), anyString());
            verifyNoMoreInteractions(model);
        }
    }
}
