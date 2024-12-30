# Documentación Sprint 6
**Fecha**: 18 de noviembre de 2024

Durante este sprint se llevó a cabo una semana de formación para la implementación de SonarCloud.

Las CR solicitadas en este sprint fueron:

1. **Implementar SonarCloud**.
2. **Mejorar estilo de interfaces**.
3. **Migración del sistema a la nube de AWS**.

---

## 6.1 Implementar SonarCloud

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó la implementación de SonarCloud para mejorar la gestión de calidad del código y permitir su análisis continuo.
- Se espera un aumento significativo en la capacidad del equipo para identificar y corregir errores de programación, problemas de seguridad y mantener altos estándares de calidad.

**Riesgos:**
- **Configuración inicial:** Posibles dificultades durante la integración.
- **Curva de aprendizaje:** El equipo debe familiarizarse con las herramientas y reportes que ofrece SonarCloud.
- **Incremento de tiempo:** La corrección de problemas reportados podría aumentar temporalmente el tiempo de desarrollo.

### Aprobación del Cambio
- **Viable:** Sí.
- **Impacto:** Medio.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Implementación
- Se configuró SonarCloud de manera local.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Se realizaron pruebas para confirmar que SonarCloud genera reportes precisos de calidad del código y detecta problemas.

**Resultado:** SonarCloud está plenamente operativo y cumple con los requisitos establecidos.

#### Validación de la Integridad de los Datos
- Se verificó que los reportes sean consistentes y no generen falsos positivos o negativos.

**Resultado:** Los datos son precisos y confiables.

#### Revisión de Código
- Auditoría del código realizada para identificar problemas detectados por SonarCloud y priorizar su resolución.

**Resultado:** Código revisado y actualizado según los estándares de calidad.

---

## 6.2 Mejorar Estilo de Interfaces

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó ajustes en diseño y estilo para mejorar la consistencia visual y la experiencia del usuario.
- Incluyó cambios en colores, tipografías y componentes de la interfaz, con el objetivo de hacerla más amigable y atractiva.

**Aprobación del Cambio:**
- **Viable:** Sí.
- **Impacto:** Alto.
- **Prioridad:** Baja.
- **CCB determina:** Válido, ya que, al haberse completado todas las tareas previas, se empezó a implementar la mejora de la interfaz.

### Implementación
- Se rediseñaron las pantallas principales para mejorar la experiencia del usuario.
- Se crearon nuevos botones para mejorar la navegabilidad.
- Se validó la consistencia visual en dispositivos de diferentes tamaños y resoluciones.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Se realizaron pruebas para garantizar que los cambios visuales mejoraran la experiencia del usuario.

**Resultado:** Los cambios implementados son visualmente consistentes y funcionales en todas las plataformas probadas.

#### Validación de la Integridad de los Datos
- Confirmación de que los cambios en la interfaz no afectaron la integridad de los datos ni el rendimiento del sistema.

**Resultado:** No se encontraron inconsistencias ni errores.

#### Revisión de Código
- Se validó que las modificaciones cumplieran con las mejores prácticas de desarrollo y que el código fuera escalable.

**Resultado:** Código revisado y aprobado.

---

## 6.3 Migración del Sistema a la Nube de AWS

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó la migración de la aplicación, la base de datos y SonarCloud a la nube de AWS.
- Permite la compartición de recursos entre los stakeholders y la liberación del sistema en producción para su uso global.

**Riesgos:**
- **Configuración inicial:** Posibles dificultades durante la migración completa del sistema.
- **Cambio de la BBDD:** Fue necesario cambiar de SQLite a MySQL, utilizando una instancia RDS.

### Aprobación del Cambio
- **Viable:** Sí.
- **Impacto:** Alto.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Implementación
- Se migró la base de datos de SQLite a MySQL, configurando una instancia RDS en AWS.
- Se desplegó la aplicación en instancias EC2 de AWS.
- Se configuró SonarCloud para operar de manera conjunta con los recursos en la nube.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para garantizar que la migración del sistema funcione correctamente en la nube.
  - Validación de acceso a la base de datos.
  - Pruebas de rendimiento de la aplicación en instancias de AWS.

**Resultado:** Migración exitosa con rendimiento optimizado.

#### Validación de la Integridad de los Datos
- Verificación de que los datos migrados sean consistentes y precisos.

**Resultado:** Datos almacenados y accesibles sin inconsistencias.

#### Revisión de Código
- Revisión de las configuraciones implementadas para garantizar escalabilidad y seguridad.

**Resultado:** Configuraciones aprobadas y funcionales.

---

### Validaciones Finales

- **Implementación exitosa de SonarCloud:** Los reportes y análisis continuos ya están operativos.
- **Migración completada:** La aplicación y la base de datos están funcionando correctamente en AWS, con una mejora significativa en la escalabilidad.
- **Estilo de interfaces:** Los cambios implementados mejoraron la experiencia del usuario y fueron validados por el equipo de diseño.

---

Este documento resume todas las actividades realizadas durante el **Sprint 6**. La documentación ha sido validada y aprobada por los responsables del proyecto.

