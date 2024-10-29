# Documentación Sprint 1

**Fecha**: 6 de noviembre de 2024

Durante este Sprint 1 nos hemos centrado en la parte inicial del proyecto. Hemos importado el proyecto desde Visual Paradigm a Visual Studio Code (IDE en el cual estamos trabajando).

![Ilustración 1: Captura completa del Sprint 1](/doc/imagenesSprint/Imagen01.png)
*Captura completa del Sprint 1.*

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

![Ilustración 2: Crear entidades Usuario, Cliente, Repartidor y Restaurante](/doc/imagenesSprint/Imagen02.png)
*Captura de la tarea “Crear entidades Usuario, Cliente, Repartidor y Restaurante”.*

---

## 1.2 Crear Entidades CodigoPostal, Zona, ZonaCodigoPostal

- **Prioridad**: Alta
- **Tamaño**: Medio

Al igual que en la tarea anterior, se ha añadido una capa `service` con Hibernate para la sintaxis SQL en SQLite. Se han creado las entidades correspondientes **CodigoPostal**, **Zona** y **ZonaCodigoPostal**, con sus `Gestor` y `DAO` para acceso en base de datos.

![Ilustración 3: Crear entidades CodigoPostal, Zona, ZonaCodigoPostal](/doc/imagenesSprint/Imagen03.png)
*Captura de la tarea “Crear entidades CodigoPostal, Zona, ZonaCodigoPostal”.*

---

## 1.3 Feature Registro

- **Prioridad**: Media
- **Tamaño**: Grande

Esta funcionalidad permite registrar cualquier tipo de usuario, sea cliente, repartidor o restaurante. Al añadir un usuario, este se guarda en la base de datos, quedando disponible para futuras operaciones de acceso o gestión.

![Ilustración 4: Feature Registro](/doc/imagenesSprint/Imagen04.png)
*Tarea de “Feature Registro”.*

---

## 1.4 Interfaz de Registro

- **Prioridad**: Media
- **Tamaño**: Medio

Se ha desarrollado una interfaz gráfica para el registro de usuarios, facilitando la entrada de datos para los diferentes tipos de usuarios del sistema.

![Ilustración 5: Interfaz de Registro](/doc/imagenesSprint/Imagen05.png)
*Tarea de “Interfaz de Registro”.*

---

Este archivo cubre toda la documentación de **Sprint 1**.
