# Documentación Sprint 5
**Fecha**: 13 de noviembre de 2024

Durante este sprint nos hemos encargado de realizar los cambios solicitados por el cliente, así como mejoras del sistema que hemos considerado necesarias.

La auditoría con el cliente se realizó el día 6 de noviembre. Se recibieron las siguientes solicitudes de cambio (CR):

1. Formato de los precios con dos decimales y notación europea (puntos en los millares, comas en los decimales).
2. Selección de distintas direcciones del usuario a la hora de hacer el pedido, o registrar una nueva.
3. Visualizar el estado del pedido en el rol de cliente.
4. El repartidor debe asignarse en función de las zonas que tenga establecidas.

Adicionalmente, para mejorar la experiencia del usuario, se propuso el siguiente cambio:

5. Establecer un estilo más amigable y llamativo para las interfaces.

---

## Roles y Responsabilidades
Cada miembro del equipo asume roles específicos para asegurar el cumplimiento de los objetivos del sprint:

- **Gestor de Configuración**: Supervisó el cumplimiento de los procedimientos de configuración y mantuvo el repositorio organizado.
- **Desarrolladores**: Implementaron los cambios en el código, gestionaron las ramas correspondientes y documentaron cada modificación en el repositorio.
- **Equipo de Calidad (QA)**: Verificó que los cambios cumplieran con los requisitos de calidad y participó en las auditorías de configuración.
- **CCB (Change Control Board)**: Revisó y aprobó los cambios propuestos, evaluando su viabilidad e impacto.

---

## Cambios Solicitados en el Sprint

1. **Formato de precios**.
2. **Visualizar estado del pedido**.
3. **Selección de dirección en pedido**.
4. **Asignación de repartidor en función de la zona**.
5. **Mejorar estilo de interfaces**.
6. **Documentación de este Sprint 5**.

---

## 5.1 Formato de Precios

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó modificaciones en la presentación de datos y ajustes en la interfaz de usuario.
- La experiencia del usuario se incrementó significativamente, así como la satisfacción del cliente final.

**Riesgos:**
- Posibles inconsistencias en la presentación y manipulación de datos.
- Necesidad de pruebas para asegurar que no se almacenen datos incorrectos en la base de datos ni se muestren valores erróneos al cliente.

**Aprobación del Cambio:**
- **Viable:** Sí.
- **Impacto:** Medio.
- **Prioridad:** Baja.
- **CCB determina:** Válido.

### Implementación
- Se corrigieron todas las interfaces para mostrar los precios en formato europeo.
- Se movió el archivo `pom.xml` de vuelta a la raíz del proyecto.
- Los precios fueron alineados a la derecha en todos los tickets, mejorando su visualización.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para garantizar que los precios se muestren correctamente en formato europeo.
  - Visualización de precios en tickets.
  - Pruebas con valores atípicos (muy altos, bajos, o con decimales).

**Resultado:** Los precios se muestran correctamente en todos los casos probados.

#### Validación de la Integridad de los Datos
- Confirmación de que las modificaciones no afecten la manipúlación o el almacenamiento de datos.
  - **Base de datos:** Datos almacenados correctamente según el formato europeo.
  - **Operaciones internas:** Cálculos y sumas sin valores erróneos.

**Resultado:** Datos consistentes y sin errores.

#### Revisión de Código
- Auditoría del código realizada para asegurar que el cambio esté aislado y siga las mejores prácticas.

**Resultado:** Código revisado, aprobado y puesto en producción.

---

## 5.2 Visualizar Estado del Pedido

### Evaluación del Cambio

**Análisis de Impacto:**
- Ajustes necesarios en la presentación de datos y en la interfaz de usuario.
- Información del estado del pedido debe actualizarse en tiempo real o en intervalos adecuados.

**Riesgos:**
- Posibles inconsistencias en los datos mostrados al cliente.
- Problemas de diseño o bugs en la interfaz que afecten la experiencia del usuario.

**Aprobación del Cambio:**
- **Viable:** Sí.
- **Impacto:** Alto.
- **Prioridad:** Media.
- **CCB determina:** Válido.

### Implementación
- Se agregó un botón en el menú inicial del cliente para visualizar los pedidos realizados.
- Se incluyó la posibilidad de ver detalles del pedido y actualizaciones en tiempo real.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para garantizar que el estado de los pedidos se visualice correctamente.
  - Acceso al botón en el menú inicial del cliente.
  - Verificación de detalles del pedido y actualizaciones consistentes.

**Resultado:** Los estados de los pedidos se visualizan correctamente, con actualizaciones consistentes.

#### Validación de la Integridad de los Datos
- Confirmación de que los estados mostrados sean consistentes con los datos de la base de datos.

**Resultado:** Datos precisos y consistentes.

#### Revisión de Código
- Auditoría del código realizada para garantizar que los cambios no afecten otras funcionalidades.

**Resultado:** Código revisado y aprobado.

---

## 5.3 Selección de Dirección en Pedido

### Evaluación del Cambio

**Análisis de Impacto:**
- Ajustes en la interfaz de usuario y en la lógica de backend para manejar la selección de múltiples direcciones.

**Riesgos:**
- Posibles inconsistencias al seleccionar direcciones desactualizadas o inválidas.
- Necesidad de pruebas exhaustivas para asegurar el correcto funcionamiento.

**Aprobación del Cambio:**
- **Viable:** Sí.
- **Impacto:** Medio.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para garantizar que los clientes puedan:
  - Seleccionar direcciones registradas.
  - Registrar nuevas direcciones durante el pedido.

**Resultado:** Funcionalidad implementada correctamente.

#### Validación de la Integridad de los Datos
- Confirmación de que las direcciones seleccionadas o registradas se almacenan correctamente en la base de datos.

**Resultado:** Sin inconsistencias ni errores.

---

## 5.4 Asignación de Repartidor en Función de la Zona

### Evaluación del Cambio

**Análisis de Impacto:**
- Ajustes en la lógica de asignación de pedidos, restringiendo su visualización según las zonas configuradas.

**Riesgos:**
- Pedidos sin asignar si no coinciden con ninguna zona disponible.

**Aprobación del Cambio:**
- **Viable:** Sí.
- **Impacto:** Alto.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para garantizar que:
  - Solo se muestren pedidos correspondientes a las zonas del repartidor.
  - No se puedan seleccionar pedidos fuera de la zona asignada.

**Resultado:** Asignación correcta y funcionalidad implementada.

---

## 5.5 Mejorar Estilo de Interfaces

### Evaluación del Cambio

**Análisis de Impacto:**
- Ajustes en diseño y estilo para mejorar la consistencia visual y la experiencia del usuario.

**Riesgos:**
- Incompatibilidades en dispositivos.
- Posible impacto en el rendimiento.

**Aprobación del Cambio:**
- **Viable:** Sí.
- **Impacto:** Alto.
- **Prioridad:** Baja.
- **CCB determina:** No Válido.

### Revisión y Cierre

**Resultado:** Cierre sin cambios.

---

## 5.6 Documentación del Sprint 5

Este documento resume todas las actividades realizadas durante el **Sprint 5**. La documentación ha sido validada y aprobada por los responsables del proyecto.

