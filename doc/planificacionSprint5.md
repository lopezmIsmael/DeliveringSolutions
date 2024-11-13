# Documentación Sprint 5
**Fecha**: 13 de noviembre de 2024

Durante este sprint nos hemos encargado de realizar los cambios solicitados por el cliente, así como mejoras del sistema que hemos visto necesarias.
La auditoría con el cliente se realizó el día 6 de noviembre. Se dan las siguientes solicitudes de cambio (CR):
1. Formato de los precios con dos decimales y notación europea (puntos en los millares, comas en los decimales)
2. Seleccion de distintas direcciones del usuario a la hora de hacer el pedido, o registrar una nueva
3. Visualizar el estado del pedido en el rol de cliente
4. El repartidor debe asignarse en función de las zonas que tenga establecidas

Con el objetivo de mejorar la experiencia del usuario, también se propone el siguiente cambio:

5. Establecer un estilo más amigable y llamativo a las interfaces.

## Roles y Responsabilidades
Cada miembro del equipo asume roles específicos para asegurar el cumplimiento de los objetivos del sprint:

- **Gestor de Configuración**: Supervisará el cumplimiento de los procedimientos de configuración y mantendrá el repositorio organizado.
- **Desarrolladores**: Implementarán los cambios en el código, gestionarán las ramas correspondientes y documentarán cada modificación en el repositorio.
- **Equipo de Calidad (QA)**: Verificará que los cambios cumplan con los requisitos de calidad y participará en las auditorías de configuración.
- **CCB**: Revisará y aprobará los cambios propuestos, evaluando su viabilidad e impacto.



<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen32.png" alt="Ilustración 32. Inicio del Sprint 5" width="100%">
    <p><em>Ilustración 32. Inicio del Sprint 5</em></p>
</div>

Las CR solicitados en este sprint son:

- **Formato de Precios**.
- **Visualizar estado pedido**.
- **Seleccion de direccion en pedido**.
- **Establecer repartidor en funcion de la zona**.
- **Mejorar estilo interfaces**.
- **Documentación de este Sprint 5**.

---

## 5.1 Formato de Precios
### Evaluación del cambio
<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen33.png" alt="Ilustración 33. Visualizar estado pedido" width="100%">
    <p><em>Ilustración 33. Visualizar estado pedido</em></p>
</div>

**Análisis de impacto:**
Este cambio implica modificaciones en la presentación de datos y ajustes en la interfaz de usuario. La experiencia del usuario se verá incrementada de manera significativa, así como la satisfacción del cliente final.

**Riesgos:**
- Se ha de tener cuidado con las inconsistencias y errores en la presentación y manipulación de datos.
- Se deben realizar pruebas para asegurar que no se almacenan incongruencias en la base de datos, así como que no se muestran valores erróneos al cliente.

### Aprobación del cambio
- **Viable:** Sí
- **Impacto:** Medio
- **Prioridad:** Baja
- **CCB determina:** Válido

## 5.2 Visualizar estado pedido
### Evaluación del cambio
<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen34.png" alt="Ilustración 34. Formato de Precios" width="100%">
    <p><em>Ilustración 34. Formato de Precios</em></p>
</div>

**Análisis de impacto:**
Implementar esta funcionalidad requerirá ajustes en la presentación de datos y en la interfaz de usuario, para mostrar el estado del pedido de manera clara y accesible. El equipo de desarrollo deberá asegurar que esta información se actualice en tiempo real o en intervalos adecuados, y que sea fácilmente comprensible para el usuario final.

**Riesgos:**
- **Inconsistencias de datos:** Existe el riesgo de mostrar estados incorrectos o desactualizados, lo cual podría generar confusión o frustración en el cliente.
- **Errores en la interfaz:** La visualización del estado podría verse afectada por problemas de diseño o bugs, afectando la experiencia del usuario.
- **Pruebas exhaustivas:** Será necesario realizar pruebas para asegurar que los datos mostrados sean precisos y que no se produzcan inconsistencias en la base de datos.

### Aprobación del cambio
- **Viable:** Sí
- **Impacto:** Alto
- **Prioridad:** Media
- **CCB determina:** Válido

## 5.3 Selección de dirección en pedido
### Evaluación del cambio
<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen35.png" alt="Ilustración 35. Selección de dirección en pedido" width="100%">
    <p><em>Ilustración 35. Selección de dirección en pedido</em></p>
</div>

**Análisis de impacto:**
Esta modificación implicará ajustes tanto en la interfaz de usuario como en la lógica de backend para manejar la selección de múltiples direcciones. Se requiere una verificación adicional en la base de datos para confirmar que las direcciones seleccionadas son válidas y están actualizadas.

**Riesgos:**
- **Inconsistencias de datos:** Existe la posibilidad de que se seleccione una dirección desactualizada o inválida. Es fundamental implementar validaciones adecuadas para evitar errores.
- **Pruebas exhaustivas:** Se necesitan pruebas para asegurar que la funcionalidad de selección y registro de direcciones no introduce errores en la interfaz o en el almacenamiento de datos.

### Aprobación del cambio
- **Viable:** Sí
- **Impacto:** Media
- **Prioridad:** Alta
- **CCB determina:** Válido

## 5.4 Establecer repartidor en función de la zona
### Evaluación del cambio
<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen36.png" alt="Ilustración 36. Establecer repartidor en función de la zona" width="100%">
    <p><em>Ilustración 36. Establecer repartidor en función de la zona</em></p>
</div>

**Análisis de impacto:**
Este cambio implica ajustes en la lógica de asignación de pedidos, restringiendo la visualización de pedidos para los repartidores en función de sus zonas registradas. Esto también requerirá un control adicional para validar que los pedidos cumplan con la ubicación establecida en la configuración de cada repartidor.

**Riesgos:**
- **Errores de asignación:** Existe el riesgo de que algunos pedidos queden sin asignar si la zona del pedido no coincide con la de ningún repartidor disponible.
- **Validación de zonas:** Es fundamental realizar pruebas para asegurar que la asignación de pedidos funcione de acuerdo a las zonas configuradas y no presente inconsistencias en la lógica de negocio.

### Aprobación del cambio
- **Viable:** Sí
- **Impacto:** Alto
- **Prioridad:** Alta
- **CCB determina:** Válido

## 5.5 Mejorar estilo interfaces
### Evaluación del cambio
<div style="text-align: center;">
    <img src="/doc/imagenesSprint/Imagen37.png" alt="Ilustración 37. Mejorar estilo interfaces" width="100%">
    <p><em>Ilustración 37. Mejorar estilo interfaces</em></p>
</div>

**Análisis de impacto:**
Este cambio implica modificaciones en los elementos de diseño y estilo de la interfaz. Será necesario trabajar en la consistencia visual, asegurar que los colores, tipografías y componentes mantengan una armonía general, y realizar pruebas de usabilidad para validar que el rediseño mejora efectivamente la experiencia del usuario.

**Riesgos:**
- **Incompatibilidades en dispositivos:** Algunos elementos visuales pueden no renderizarse adecuadamente en todos los dispositivos, por lo que es necesario realizar pruebas en múltiples plataformas.
- **Rendimiento:** Incrementar el uso de elementos gráficos avanzados podría afectar el rendimiento en dispositivos con menor capacidad de procesamiento.

### Aprobación del cambio
- **Viable:** Sí
- **Impacto:** Alto
- **Prioridad:** Baja
- **CCB determina:** No Válido

## 5.6 Documentación de Sprint 5

Este archivo cubre toda la documentación de **Sprint 5**.