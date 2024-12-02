
package com.IsoII.DeliveringSolutions.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceGroup {
    private final ServiceRestaurant serviceRestaurant;
    private final ServicePedido servicePedido;
    private final ServiceItemMenu serviceItemMenu;
    private final ServiceItemPedido serviceItemPedido;
    private final ServiceDireccion serviceDireccion;
    private final ServiceUser serviceUsuario;
    private final ServicePago servicePago;
    private final ServiceCodigoPostal serviceCodigoPostal;

    @Autowired
    public ServiceGroup(ServiceRestaurant serviceRestaurant, ServicePedido servicePedido, ServiceItemMenu serviceItemMenu,
                        ServiceItemPedido serviceItemPedido, ServiceDireccion serviceDireccion, ServiceUser serviceUsuario,
                        ServicePago servicePago, ServiceCodigoPostal serviceCodigoPostal) {
        this.serviceRestaurant = serviceRestaurant;
        this.servicePedido = servicePedido;
        this.serviceItemMenu = serviceItemMenu;
        this.serviceItemPedido = serviceItemPedido;
        this.serviceDireccion = serviceDireccion;
        this.serviceUsuario = serviceUsuario;
        this.servicePago = servicePago;
        this.serviceCodigoPostal = serviceCodigoPostal;
    }

    public ServiceRestaurant getServiceRestaurant() {
        return serviceRestaurant;
    }

    public ServicePedido getServicePedido() {
        return servicePedido;
    }

    public ServiceItemMenu getServiceItemMenu() {
        return serviceItemMenu;
    }

    public ServiceItemPedido getServiceItemPedido() {
        return serviceItemPedido;
    }

    public ServiceDireccion getServiceDireccion() {
        return serviceDireccion;
    }

    public ServiceUser getServiceUsuario() {
        return serviceUsuario;
    }

    public ServicePago getServicePago() {
        return servicePago;
    }

    public ServiceCodigoPostal getServiceCodigoPostal() {
        return serviceCodigoPostal;
    }
}