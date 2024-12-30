# Documentación Sprint 7
**Fecha**: 1 de diciembre de 2024

Durante este sprint se solucionaron los issues que mostraba SonarQube y se establecieron los test iniciales.

Las CR solicitadas en este sprint fueron:

1. **Solucionar Issues SonarQube**.
2. **Generar test iniciales para todos los gestores**.

---

## 7.1 Solucionar Issues SonarQube

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó revisar y corregir los issues detectados por SonarQube en la calidad del código.
- Las correcciones incluyeron problemas de mantenibilidad, duplicación de código y otros errores menores reportados.

**Riesgos:**
- **Interrupción en desarrollo:** La corrección de los problemas identificados podría interferir temporalmente con el desarrollo de nuevas funcionalidades.
- **Falsos positivos:** Algunos issues podrían no representar errores reales y requerirían revisiones manuales adicionales.

### Aprobación del Cambio
- **Viable:** Sí.
- **Impacto:** Alto.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Implementación
- Se realizaron revisiones exhaustivas del código basándose en el reporte de SonarQube.
- Se corrigieron 201 issues relacionados con mantenibilidad, 56 con fiabilidad, 2 issues de seguridad y duplicación de código.
- Se redujo la duplicación del código hasta el 1,1%.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para garantizar que los cambios no afectaran otras funcionalidades.
  - Reanálisis del código en SonarQube tras las correcciones.
  - Confirmación de la resolución de todos los issues detectados inicialmente.

**Resultado:** Los issues se solucionaron correctamente y SonarQube reportó un incremento en la calidad general del código.

#### Validación de la Integridad de los Datos
- Verificación de que las correcciones realizadas no introdujeran errores adicionales ni inconsistencias en el sistema.

**Resultado:** Datos y funcionalidades consistentes.

#### Revisión de Código
- Auditoría final para garantizar que se siguieron las mejores prácticas de desarrollo.

**Resultado:** Código revisado y aprobado con 1 issue leve.

---

## 7.2 Generar Test Iniciales para Todos los Gestores

### Evaluación del Cambio

**Análisis de Impacto:**
- Este cambio implicó crear tests unitarios y de integración para validar el correcto funcionamiento de los gestores principales.
- Los tests iniciales cubrirán casos básicos para garantizar estabilidad en futuras implementaciones.

**Riesgos:**
- **Cobertura incompleta:** Al tratarse de tests iniciales, algunos casos extremos podrían no estar contemplados.
- **Errores en los tests:** La implementación de tests incorrectos podría generar falsos positivos.

### Aprobación del Cambio
- **Viable:** Sí.
- **Impacto:** Medio.
- **Prioridad:** Alta.
- **CCB determina:** Válido.

### Implementación
- Se desarrollaron tests iniciales para los gestores de usuarios, pedidos y zonas.
- Se configuró un pipeline para ejecutar los tests automáticamente en cada build.
- Se logró una cobertura inicial del 53,6% del código.

### Revisión y Cierre

#### Validación de los Requisitos Implementados
- Pruebas realizadas para confirmar que los tests se ejecutan correctamente y cubren los casos esperados.

**Resultado:** Los tests iniciales se ejecutaron satisfactoriamente, detectando y corrigiendo errores en escenarios previamente no cubiertos, como por ejemplo realizar un pedido sin items y con coste 0.

#### Validación de la Integridad de los Datos
- Verificación de que los tests no generaron conflictos en el flujo de desarrollo.

**Resultado:** Sistema estable con tests funcionales.

#### Revisión de Código
- Auditoría del código de los tests para confirmar su calidad y efectividad.

**Resultado:** Tests revisados, aprobados e integrados. 82 nuevos issues.

---

### Validaciones Finales

- **Corrección de issues SonarQube:** Se alcanzó un nivel satisfactorio de calidad del código.
- **Tests iniciales implementados:** Se logró una base de pruebas funcional para futuros desarrollos.

---

Este documento resume todas las actividades realizadas durante el **Sprint 7**. La documentación ha sido validada y aprobada por los responsables del proyecto.

