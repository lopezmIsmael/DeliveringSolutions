# Documentación Sprint 8
**Fecha**: 30 de diciembre de 2024

Durante este sprint se solucionaron los issues que mostraba SonarQube y se generaron los tests finales.

Las CR solicitadas en este sprint fueron:

1. **Solucionar Issues SonarQube**.
2. **Generar test finales para todos los gestores**.

---

## 8.1 Solucionar Issues SonarQube

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó una revisión exhaustiva del código para solucionar los problemas reportados por SonarQube.
- Los ajustes realizados mejoraron significativamente la mantenibilidad y duplicación del código.

**Riesgos:**
- **Falsos positivos:** Algunos problemas identificados, como los 22 relacionados con el uso de mockito matchers, no representaban errores reales y fueron marcados como aceptados.
- **Impacto en la integración:** Cambios extensivos podrían afectar otras funcionalidades.

### Aprobación del Cambio
- **Viable:** Sí.
- **Impacto:** Alto.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Implementación
- Se solucionaron los issues identificados en el SonarQube inicial y se marcó como "aceptados" los 22 issues relacionados con Mosquitto al ser falsos positivos.
- Se optimizó el código, logrando una cobertura del 88.1% y reduciendo la duplicación al 1.0%.
- Se aseguró la confiabilidad y la mantenibilidad con un puntaje "A" en todas las categorías.

<div style="text-align: center;">
    <img src="/mnt/data/image.png" alt="Resultado final del SonarQube" width="100%">
    <p><em>Ilustración 43. Resultado final del SonarQube</em></p>
</div>

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para confirmar la resolución de los problemas y la mejora general de calidad.
  - Validación del nuevo análisis de SonarQube.
  - Confirmación de la disminución de issues abiertos y duplicación de código.

**Resultado:** Los problemas se resolvieron satisfactoriamente, marcándose los falsos positivos como aceptados, y se mejoró la calidad del código.

#### Validación de la Integridad de los Datos
- Verificación de que los cambios realizados no introdujeran nuevos problemas.

**Resultado:** Integridad del código garantizada.

#### Revisión de Código
- Auditoría del código completada para garantizar el cumplimiento de estándares.

**Resultado:** Código optimizado, revisado y aprobado.

---

## 8.2 Generar Tests Finales para Todos los Gestores

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó la generación de tests unitarios y de integración para cubrir casos extremos y asegurar la estabilidad del sistema.
- La implementación de los tests finales incrementó la cobertura de código a un nivel casi completo.

**Riesgos:**
- **Casos no cubiertos:** Existe la posibilidad de que algunos escenarios específicos no hayan sido incluidos.
- **Errores en los tests:** Tests mal diseñados podrían generar falsos positivos.

### Aprobación del Cambio
- **Viable:** Sí.
- **Impacto:** Medio.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Implementación
- Se desarrollaron tests finales para los gestores de usuarios, pedidos, zonas y productos.
- Se logró una cobertura del 88.1% del código, con pruebas diseñadas para cubrir los escenarios críticos.
- Se configuró el pipeline de CI/CD para ejecutar los tests automáticamente.

<div style="text-align: center;">
    <img src="/mnt/data/image.png" alt="Ejecución de los tests finales" width="100%">
    <p><em>Ilustración 44. Ejecución de los tests finales</em></p>
</div>

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para asegurar que los tests cubran todos los escenarios necesarios.
  - Ejecución exitosa de los tests finales.

**Resultado:** Los tests finales cumplen con los requisitos establecidos y detectaron errores previos que fueron corregidos.

#### Validación de la Integridad de los Datos
- Confirmación de que los tests no interfirieron con el funcionamiento general del sistema.

**Resultado:** Sistema estable y validado.

#### Revisión de Código
- Revisión de los tests generados para garantizar su efectividad y adherencia a las mejores prácticas.

**Resultado:** Tests revisados y aprobados.

---

### Validaciones Finales

- **Resolución de issues SonarQube:** Calidad del código mejorada significativamente, con falsos positivos gestionados correctamente.
- **Generación de tests finales:** Cobertura casi completa y sistema estable.

---

Este documento resume todas las actividades realizadas durante el **Sprint 8**. La documentación ha sido validada y aprobada por los responsables del proyecto.

