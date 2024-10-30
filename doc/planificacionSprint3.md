# Documentación Sprint 3

**Fecha**: 30 de octubre de 2024

Durante este sprint nos hemos encargado de seguir implementando las funcionalidades básicas al proyecto. Hemos creado las entidades restantes que eran **pedido**, **pago** y **servicioEntrega** para poder, posteriormente, registrar esos pedidos con sus respectivos pagos y las entregas. Hemos añadido pequeños detalles como la posibilidad de añadir y eliminar un restaurante como favorito, así como crear un carrito con los ítems que un cliente quiere añadir a su pedido. Además, ahora los usuarios anónimos también pueden ver los restaurantes. Por último, hemos añadido un campo al restaurante para registrar la **dirección**.

Algo que puede ocurrir, y de hecho nos ha ocurrido en este sprint, es que no siempre se terminan todas las tareas de un sprint a tiempo. Por ello, hemos dejado para el próximo y último sprint, antes del seguimiento del proyecto, las siguientes tres tareas: **“Registrar pedido”**, **“Actualizar estado del repartidor”** y **“Notificar repartidor”**.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen14.png" alt="Ilustración 14: Captura completa del Sprint 3 pt1" width="100%">
    <p><em>Ilustración 14. Captura completa del Sprint 3 pt1.</em></p>
</div>

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen15.png" alt="Ilustración 15: Captura completa del Sprint 3 pt2" width="100%">
    <p><em>Ilustración 15. Captura completa del Sprint 3 pt2.</em></p>
</div>

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen16.png" alt="Ilustración 16: Captura completa del Sprint 3 pt3" width="100%">
    <p><em>Ilustración 16. Captura completa del Sprint 3 pt3.</em></p>
</div>

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen17.png" alt="Ilustración 17: Captura completa del Sprint 3 pt4" width="100%">
    <p><em>Ilustración 17. Captura completa del Sprint 3 pt4.</em></p>
</div>

Las tareas realizadas en este sprint son:

- **Carrito cliente con ítems**.
- **Marcar restaurante como favorito**.
- **Dirección objeto, asignar dirección a restaurante y pedido**.
- **Entidades pedidos, pago y servicioEntrega**.
- **Usuario anónimo visualiza los restaurantes**.
- Documentación de este **Sprint 3**.

---

## 3.1 Carrito Cliente con Ítems

Implementada la funcionalidad para que un cliente, una vez registrado, pueda añadir ítems de un menú procedente de un restaurante previamente elegido a un carrito de compra.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen18.png" alt="Ilustración 18: Tarea de Carrito Cliente con Ítems" width="100%">
    <p><em>Ilustración 18. Tarea de “Carrito Cliente con Ítems”.</em></p>
</div>

---

## 3.2 Marcar Restaurante como Favorito

Funcionalidad implementada para poder marcar y desmarcar un restaurante como favorito. Esta opción es única para cada cliente registrado.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen19.png" alt="Ilustración 19: Tarea de Marcar Restaurante como Favorito" width="100%">
    <p><em>Ilustración 19. Tarea de “Marcar Restaurante como Favorito”.</em></p>
</div>

---

## 3.3 Dirección Objeto, Asignar Dirección a Restaurante y Pedidos

Implementada la funcionalidad de asignar un campo de dirección a cada restaurante, el cual se registra de manera correcta en la base de datos al dar de alta un nuevo restaurante.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen20.png" alt="Ilustración 20: Tarea de Dirección Objeto, Asignar Dirección a Restaurante y Pedidos" width="100%">
    <p><em>Ilustración 20. Tarea de “Dirección Objeto, Asignar Dirección a Restaurante y Pedidos”.</em></p>
</div>

---

## 3.4 Entidades Pedidos, Pago, ServicioEntrega

Se han añadido las entidades de **pedidos**, **pago** y **servicioEntrega** incluyendo sus correspondientes servicios, DAOs y gestores para implementar funcionalidades en el proyecto a posteriori.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen21.png" alt="Ilustración 21: Tarea de Entidades Pedidos, Pago, ServicioEntrega" width="100%">
    <p><em>Ilustración 21. Tarea de “Entidades Pedidos, Pago, ServicioEntrega”.</em></p>
</div>

---

## 3.5 Usuario Anónimo Visualiza los Restaurantes

Implementada la funcionalidad que permite a un usuario no registrado visualizar los restaurantes disponibles, así como ver sus menús y los ítems de estos. Los usuarios anónimos no pueden interactuar con la base de datos, es decir, no pueden añadir un restaurante a favoritos ni realizar pedidos, únicamente pueden visualizar.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen22.png" alt="Ilustración 22: Tarea de Usuario Anónimo Visualiza los Restaurantes" width="100%">
    <p><em>Ilustración 22. Tarea de “Usuario Anónimo Visualiza los Restaurantes”.</em></p>
</div>

---

## 3.6 Registrar Pedidos

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen23.png" alt="Ilustración 23: Tarea de Registrar Pedidos" width="100%">
    <p><em>Ilustración 23. Tarea de “Registrar Pedidos”.</em></p>
</div>

---

## 3.7 Notificar Repartidor

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen24.png" alt="Ilustración 24: Tarea de Notificar Repartidor" width="100%">
    <p><em>Ilustración 24. Tarea de “Notificar Repartidor”.</em></p>
</div>

---

## 3.8 Repartidor Actualiza Estados

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen25.png" alt="Ilustración 25: Tarea de Repartidor Actualiza Estados" width="100%">
    <p><em>Ilustración 25. Tarea de “Repartidor Actualiza Estados”.</em></p>
</div>

---

Este archivo cubre toda la documentación de **Sprint 3**.