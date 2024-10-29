# Documentación Sprint 1

**Fecha**: 11 de octubre de 2024

Durante este Sprint 1 nos hemos centrado en la parte inicial del proyecto. Hemos importado el proyecto desde Visual Paradigm a Visual Studio Code (IDE en el cual estamos trabajando).

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen01.png" alt="Ilustración 1: Captura completa del Sprint 1" width="100%">
    <p><em>Captura completa del Sprint 1.</em></p>
</div>

Las tareas realizadas en este sprint incluyen:

- Crear entidades **Usuario**, **Cliente**, **Repartidor** y **Restaurante**.
- Crear entidades **CodigoPostal**, **Zona** y **ZonaCodigoPostal**.
- Añadir la característica de **Registro**.
- Añadir la **interfaz de Registro**.
- Documentación de Sprint 1.

---

## 1.1 Crear Entidades Usuario, Cliente, Repartidor y Restaurante

- **Prioridad**: Alta
- **Tamaño**: Medio

Para esta tarea, hemos añadido una capa `service` para la sintaxis SQL inexistente usando Hibernate para SQLite. Se han creado las entidades con JPA, junto con sus correspondientes `Gestor` y `DAO`, permitiendo funcionalidad en la base de datos y accesibilidad para las entidades **Usuario**, **Cliente**, **Repartidor** y **Restaurante**.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen02.png" alt="Ilustración 2: Crear entidades Usuario, Cliente, Repartidor y Restaurante" width="100%">
    <p><em>Captura de la tarea “Crear entidades Usuario, Cliente, Repartidor y Restaurante”.</em></p>
</div>

---

## 1.2 Crear Entidades CodigoPostal, Zona, ZonaCodigoPostal

- **Prioridad**: Alta
- **Tamaño**: Medio

Al igual que en la tarea anterior, se ha añadido una capa `service` con Hibernate para la sintaxis SQL en SQLite. Se han creado las entidades correspondientes **CodigoPostal**, **Zona** y **ZonaCodigoPostal**, con sus `Gestor` y `DAO` para acceso en base de datos.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen03.png" alt="Ilustración 3: Crear entidades CodigoPostal, Zona, ZonaCodigoPostal" width="100%">
    <p><em>Captura de la tarea “Crear entidades CodigoPostal, Zona, ZonaCodigoPostal”.</em></p>
</div>

---

## 1.3 Feature Registro

- **Prioridad**: Media
- **Tamaño**: Grande

Esta funcionalidad permite registrar cualquier tipo de usuario, sea cliente, repartidor o restaurante. Al añadir un usuario, este se guarda en la base de datos, quedando disponible para futuras operaciones de acceso o gestión.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen04.png" alt="Ilustración 4: Feature Registro" width="100%">
    <p><em>Tarea de “Feature Registro”.</em></p>
</div>

---

## 1.4 Interfaz de Registro

- **Prioridad**: Media
- **Tamaño**: Medio

Se ha desarrollado una interfaz gráfica para el registro de usuarios, facilitando la entrada de datos para los diferentes tipos de usuarios del sistema.

<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen05.png" alt="Ilustración 5: Interfaz de Registro" width="100%">
    <p><em>Tarea de “Interfaz de Registro”.</em></p>
</div>

---

Este archivo cubre toda la documentación de **Sprint 1**.