# Documentación del Historial de Errores - Pre-Release

**Fecha:** 5 de noviembre de 2024

Durante la fase de pre-release, encontramos y resolvimos varios errores críticos que afectaban el flujo de la aplicación. A continuación, se detalla el problema específico, su causa y la solución implementada para garantizar un comportamiento correcto del sistema.

---

## Error Encontrado: `NullPointerException` en el Método `mostrarFormularioRegistro`

- **Clase Afectada:** `GestorPago`
- **Método Afectado:** `mostrarFormularioRegistro`
- **Descripción del Error:**  
  Se produjo un error de tipo `NullPointerException` cuando la aplicación intentaba ejecutar el método `getIdUsuario()` en un objeto `usuario` que era `null`. Este problema surgía cuando se accedía al método `mostrarFormularioRegistro` sin que el usuario estuviera previamente autenticado y su información no estaba disponible en la sesión.

- **Ubicación del Error en el Código:**  
  ```java
  Usuario usuario = (Usuario) session.getAttribute("usuario");
  if (serviceDireccion.findByUsuario(usuario) == null) {
      return "redirect:/direccion/formularioRegistro";
  }

## Análisis de la Causa
El error fue provocado por la falta de verificación de `null` en el objeto `usuario` antes de intentar acceder a sus métodos. En este caso, cuando el objeto `usuario` no estaba presente en la sesión, el sistema intentaba llamar a `getIdUsuario()`, lo que resultaba en un `NullPointerException`.

### Detalles del Problema
- **Situación:** El `NullPointerException` se producía porque el método `mostrarFormularioRegistro` asumía que `usuario` siempre estaría disponible en la sesión.
- **Comportamiento no esperado:** Al no tener `usuario` en la sesión, el método `getIdUsuario()` no puede ejecutarse, lo que causa una interrupción del flujo de trabajo en la aplicación.
- **Impacto:** Los usuarios no autenticados que intentaban acceder al formulario de registro de pedidos experimentaban fallos en la aplicación debido a la falta de control de sesión.

## Solución Implementada
Para resolver el problema, se añadió una condición que verifica si el objeto `usuario` es `null` antes de intentar acceder a sus métodos. Si `usuario` es `null`, el sistema redirige al usuario a la página de inicio de sesión (`/usuarios/login`), asegurando que solo los usuarios autenticados puedan continuar con la acción solicitada.

### Modificaciones en el Código
- **Se añadió una verificación `null` en el objeto `usuario`:** Esto permite detectar si el usuario no está autenticado y redirigirlo adecuadamente.
- **Redirección a `/usuarios/login` si `usuario` es `null`:** Con esta redirección, los usuarios no autenticados son llevados al inicio de sesión antes de intentar acceder a información o procesos restringidos.

#### Código Actualizado:
```java
Usuario usuario = (Usuario) session.getAttribute("usuario");

// Verificar si el usuario está en la sesión antes de continuar
if (usuario == null) {
    return "redirect:/usuarios/login"; // Redirige a la página de inicio de sesión
}

Direccion direccion = serviceDireccion.findByUsuario(usuario);
if (direccion == null) {
    return "redirect:/direccion/formularioRegistro";
}
```

## Resultado
Con esta modificación:
- **Autenticación Verificada:** La aplicación ahora verifica que el usuario esté autenticado antes de intentar acceder a su información, asegurando que solo los usuarios válidos puedan acceder al formulario de registro de pedidos.
- **Error Prevenido:** Se evita el `NullPointerException`, lo que permite un flujo de trabajo más seguro y estable.
- **Redirección Apropiada:** Los usuarios no autenticados son redirigidos a la página de inicio de sesión (`/usuarios/login`), manteniendo la seguridad y consistencia en el acceso a funcionalidades restringidas.

## Próximos Pasos y Recomendaciones
Para evitar problemas similares en futuras actualizaciones, se sugieren las siguientes acciones:

1. **Añadir Verificaciones de `null` Consistentemente:**
   Asegurarse de verificar si los objetos críticos están inicializados antes de acceder a sus métodos. Esto incluye validar objetos recuperados de la sesión y otras fuentes externas antes de cualquier operación sobre ellos.

2. **Implementar Pruebas de Integración y Unitarias:**
   Desarrollar pruebas que simulen el acceso a funcionalidades sin estar autenticado, verificando la correcta gestión de la sesión y la autenticación en los métodos que dependen de esta información. Estas pruebas pueden ayudar a detectar problemas similares en etapas tempranas del desarrollo.

3. **Mejorar la Documentación de Requerimientos de Autenticación:**
   Documentar cada punto del flujo de la aplicación donde se requiere autenticación, para mantener la lógica clara y facilitar el trabajo de futuros desarrolladores. Especificar claramente las rutas que necesitan autenticación y los posibles puntos de redirección en caso de accesos no autorizados.

4. **Agregar Manejo de Errores Centralizado:**
   Considerar la implementación de un mecanismo de manejo de errores centralizado para capturar `NullPointerException` y otros errores comunes, proporcionando mensajes de error más informativos y una experiencia de usuario más fluida.

---
