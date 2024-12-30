package com.isoii.deliveringsolutions.dominio.controladores;

import com.isoii.deliveringsolutions.dominio.entidades.Cliente;
import com.isoii.deliveringsolutions.dominio.entidades.CodigoPostal;
import com.isoii.deliveringsolutions.dominio.entidades.Direccion;
import com.isoii.deliveringsolutions.dominio.entidades.ItemMenu;
import com.isoii.deliveringsolutions.dominio.entidades.ItemPedido;
import com.isoii.deliveringsolutions.dominio.entidades.Pago;
import com.isoii.deliveringsolutions.dominio.entidades.Pedido;
import com.isoii.deliveringsolutions.dominio.entidades.Restaurante;
import com.isoii.deliveringsolutions.dominio.entidades.Usuario;
import com.isoii.deliveringsolutions.dominio.service.ServiceGroup;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/pago")
public class GestorPago {

    private static final Logger logger = LoggerFactory.getLogger(GestorPago.class);

    private static final String ERROR = "error";
    private static final String REDIRECT_ERROR = "redirect:/error";
    private static final String USUARIO = "usuario";

    private final ServiceGroup serviceGroup;

    @Autowired
    public GestorPago(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    // Método para listar todos los pagos
    @GetMapping("/findAll")
    public List<Pago> findAll() {
        return serviceGroup.getServicePago().findAll();
    }

    // Método para registrar un pago (mostrar formulario)
    @PostMapping("/register")
    public String mostrarFormularioRegistro(@RequestParam("cartData") String cartData,
                                            @RequestParam("restauranteId") String restauranteId,
                                            Model model, HttpSession session) {

        logger.info("<<RestauranteId>>: {}", restauranteId);

        Usuario usuario = (Usuario) session.getAttribute(USUARIO);
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }

        if (serviceGroup.getServiceDireccion().findByUsuario(usuario) == null) {
            return "redirect:/direccion/formularioRegistro";
        }

        Restaurante restaurante = serviceGroup.getServiceRestaurant().findById(restauranteId).orElse(null);
        if (restaurante == null) {
            return REDIRECT_ERROR; // Manejo de error apropiado
        }

        logger.info("<<Restaurante>>: {}", restaurante.getNombre());

        List<ItemMenu> carrito = convertirJsonACarrito(cartData);
        logger.info("<<Carrito size>>: {}", carrito.size());

        double totalPrice = 0;
        for (ItemMenu item : carrito) {
            logger.info("<<Item>>: {}, Precio: {}", item.getNombre(), item.getPrecio());
            totalPrice += item.getPrecio();
        }
        logger.info("<<Total Price>>: {}", totalPrice);

        List<Direccion> direcciones = serviceGroup.getServiceDireccion().findByUsuario(usuario);
        List<CodigoPostal> codigosPostales = serviceGroup.getServiceCodigoPostal().findAll();

        model.addAttribute("direcciones", direcciones);
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", totalPrice);
        model.addAttribute(USUARIO, usuario);
        model.addAttribute("codigosPostales", codigosPostales);

        return "RegistrarPedidos";
    }

    // Método para convertir el JSON del carrito en una lista de ItemMenu
    private List<ItemMenu> convertirJsonACarrito(String cartData) {
        List<ItemMenu> carrito = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            carrito = objectMapper.readValue(cartData, new TypeReference<List<ItemMenu>>() {});
        } catch (Exception e) {
            logger.error("<<Error al convertir el carrito>>: {}", e.getMessage(), e);
        }
        return carrito;
    }

    // Método para buscar un pago por ID
    @GetMapping("/findById/{id}")
    public Pago findById(@PathVariable Integer id) {
        return serviceGroup.getServicePago().findById(id).orElse(null);
    }

    // Método principal para registrar un pedido
    @PostMapping("/registrarPedido")
    public String registrarPedido(@RequestParam("metodoPago") String metodoPago,
                                  @RequestParam("restauranteId") String restauranteId,
                                  @RequestParam("direccion") Long direccionId,
                                  HttpSession session,
                                  @RequestParam("itemIds") List<Integer> itemIds,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        logger.info("<<ESTOY EN REGISTRAR PEDIDO: GestorPago>>");

        try {
            // 1. Validaciones iniciales
            if (!validarCarrito(itemIds, redirectAttributes)) {
                return REDIRECT_ERROR;
            }
            if (!validarMetodoPago(metodoPago, redirectAttributes)) {
                return REDIRECT_ERROR;
            }

            // 2. Obtener restaurante y dirección
            Restaurante restaurante = obtenerRestaurante(restauranteId, redirectAttributes);
            if (restaurante == null) {
                return REDIRECT_ERROR;
            }
            Direccion direccionEntrega = obtenerDireccion(direccionId, redirectAttributes);
            if (direccionEntrega == null) {
                return REDIRECT_ERROR;
            }

            logger.info("<<Metodo de pago>>: {}", metodoPago);
            logger.info("<<RestauranteId>>: {}", restauranteId);
            logger.info("<<DireccionId>>: {}", direccionId);

            // 3. Obtener cliente (usuario logueado)
            Cliente cliente = (Cliente) session.getAttribute(USUARIO);

            // 4. Crear y guardar el pedido
            Pedido pedido = crearPedido(cliente, restaurante);

            // 5. Registrar ítems del pedido
            List<ItemMenu> items = new ArrayList<>();
            double total = registrarItems(itemIds, pedido, items);

            // 6. Crear y guardar el pago
            Pago pago = crearPago(metodoPago, pedido);

            // 7. Actualizar estado del pedido y preparar datos de confirmación
            pedido.setEstadoPedido("Pagado");
            serviceGroup.getServicePedido().save(pedido);
            logger.info("<<Pedido actualizado (Pagado)>>: {}", pedido);

            Direccion direccionRecogida = obtenerDireccionRecogidaRestaurante(restaurante);
            logger.info("<<DireccionRecogida>>: {}", 
                        direccionRecogida != null ? direccionRecogida : "No encontrada");

            agregarAtributosConfirmacion(redirectAttributes, pedido, items, pago, 
                                            restaurante, cliente, total, 
                                            direccionRecogida, direccionEntrega);

            return "redirect:/pago/confirmacion";

        } catch (Exception e) {
            logger.error("<<Error inesperado>>: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute(ERROR, 
                "Ocurrió un error inesperado al procesar el pedido.");
            return REDIRECT_ERROR;
        }
    }

    /** ======================== MÉTODOS PRIVADOS DE APOYO ======================== **/

    /**
     * Valida que el carrito (lista de itemIds) no esté vacío.
     * @return true si es válido, false en caso contrario
     */
    private boolean validarCarrito(List<Integer> itemIds, RedirectAttributes redirectAttributes) {
        if (itemIds == null || itemIds.isEmpty()) {
            logger.error("<<Error: carrito vacío>>");
            redirectAttributes.addFlashAttribute(ERROR, "El carrito está vacío.");
            return false;
        }
        return true;
    }

    /**
     * Valida que el método de pago no sea nulo o vacío.
     * @return true si es válido, false en caso contrario
     */
    private boolean validarMetodoPago(String metodoPago, RedirectAttributes redirectAttributes) {
        if (metodoPago == null || metodoPago.isEmpty()) {
            logger.error("<<Error: método de pago no especificado>>");
            redirectAttributes.addFlashAttribute(ERROR, "El método de pago es inválido.");
            return false;
        }
        return true;
    }

    /**
     * Obtiene el restaurante a partir de su ID. Regresa null si no existe.
     */
    private Restaurante obtenerRestaurante(String restauranteId, RedirectAttributes redirectAttributes) {
        Restaurante restaurante = serviceGroup.getServiceRestaurant()
                                              .findById(restauranteId)
                                              .orElse(null);
        if (restaurante == null) {
            logger.error("<<Error: Restaurante no encontrado>>");
            redirectAttributes.addFlashAttribute(ERROR, "El restaurante no existe.");
        }
        return restaurante;
    }

    /**
     * Obtiene la dirección a partir de su ID. Regresa null si no existe.
     */
    private Direccion obtenerDireccion(Long direccionId, RedirectAttributes redirectAttributes) {
        Direccion direccionEntrega = serviceGroup.getServiceDireccion()
                                                 .findById(direccionId)
                                                 .orElse(null);
        if (direccionEntrega == null) {
            logger.error("<<Error: Dirección no válida>>");
            redirectAttributes.addFlashAttribute(ERROR, "La dirección de entrega no existe.");
        }
        return direccionEntrega;
    }

    /**
     * Crea un nuevo pedido y lo persiste.
     */
    private Pedido crearPedido(Cliente cliente, Restaurante restaurante) {
        Pedido pedido = new Pedido();
        pedido.setFecha(System.currentTimeMillis());
        pedido.setEstadoPedido("Pendiente");
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        serviceGroup.getServicePedido().save(pedido);
        logger.info("<<Pedido registrado>>: {}", pedido);
        return pedido;
    }

    /**
     * Registra los items en el pedido y devuelve el total acumulado.
     */
    private double registrarItems(List<Integer> itemIds, Pedido pedido, List<ItemMenu> items) {
        double total = 0.0;
        for (Integer itemId : itemIds) {
            logger.info("<<Item ID>>: {}", itemId);
            Optional<ItemMenu> optionalItem = serviceGroup.getServiceItemMenu().findById(itemId);
            if (optionalItem.isPresent()) {
                ItemMenu itemMenu = optionalItem.get();
                items.add(itemMenu);
                total += itemMenu.getPrecio();
                logger.info("<<Item encontrado>>: {}", itemMenu);

                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setItemMenu(itemMenu);
                itemPedido.setPedido(pedido);
                serviceGroup.getServiceItemPedido().save(itemPedido);
                logger.info("<<ItemPedido registrado>>: {}", itemPedido);
            }
        }
        return total;
    }

    /**
     * Crea y persiste un pago vinculado al pedido.
     */
    private Pago crearPago(String metodoPago, Pedido pedido) {
        Pago pago = new Pago();
        pago.setMetodoPago(metodoPago);
        pago.setPedido(pedido);
        serviceGroup.getServicePago().save(pago);
        logger.info("<<Pago registrado>>: {}", pago);
        return pago;
    }

    /**
     * Obtiene la dirección de recogida del restaurante (la primera que exista).
     */
    private Direccion obtenerDireccionRecogidaRestaurante(Restaurante restaurante) {
        Usuario usuarioRestaurante = serviceGroup.getServiceUsuario()
                                                .findById(restaurante.getIdUsuario())
                                                .orElse(null);

        if (usuarioRestaurante == null) {
            return null;
        }

        List<Direccion> direccionesRecogida = serviceGroup.getServiceDireccion()
                                                          .findByUsuario(usuarioRestaurante);
        return !direccionesRecogida.isEmpty() ? direccionesRecogida.get(0) : null;
    }

    /**
     * Agrega al RedirectAttributes todos los objetos que se requieren para la vista de confirmación.
     */
    private void agregarAtributosConfirmacion(RedirectAttributes redirectAttributes,
                                              Pedido pedido,
                                              List<ItemMenu> items,
                                              Pago pago,
                                              Restaurante restaurante,
                                              Cliente cliente,
                                              double total,
                                              Direccion direccionRecogida,
                                              Direccion direccionEntrega) {

        redirectAttributes.addFlashAttribute("pedido", pedido);
        redirectAttributes.addFlashAttribute("items", items);
        redirectAttributes.addFlashAttribute("pago", pago);
        redirectAttributes.addFlashAttribute("restaurante", restaurante);
        redirectAttributes.addFlashAttribute("cliente", cliente);
        redirectAttributes.addFlashAttribute("total", total);
        redirectAttributes.addFlashAttribute("direccionRecogida", direccionRecogida);
        redirectAttributes.addFlashAttribute("direccionEntrega", direccionEntrega);
    }

    // Método para mostrar el formulario de confirmación
    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute(USUARIO);
        if (usuario == null) {
            logger.error("<<Error: sesión inválida o usuario no autenticado>>");
            return "redirect:/login";
        }
        logger.info("<<Usuario autenticado>>: {}", usuario.getIdUsuario());
        model.addAttribute(USUARIO, usuario);
        return "ConfirmacionPedido";
    }

    // Método para listar todos los pagos (administrador)
    @GetMapping("/mostrarPagos")
    public String mostrarPagos(Model model) {
        List<Pago> pagos = serviceGroup.getServicePago().findAll();
        if (!pagos.isEmpty()) {
            model.addAttribute("pagos", pagos);
            return "/administrador/ListaPagos";
        } else {
            model.addAttribute(ERROR, "No se encontraron pagos");
            return ERROR;
        }
    }

    // Método para mostrar el detalle de un pago específico (administrador)
    @GetMapping("/mostrarPago/{id}")
    public String mostrarPago(@PathVariable Integer id, Model model) {
        Optional<Pago> optionalPago = serviceGroup.getServicePago().findById(id);
        if (optionalPago.isPresent()) {
            model.addAttribute("pago", optionalPago.get());
            return "/administrador/VerPago";
        } else {
            model.addAttribute(ERROR, "Pago no encontrado");
            return ERROR;
        }
    }
}

