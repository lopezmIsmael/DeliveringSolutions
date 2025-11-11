<div align="center">

# 🚀 DeliveringSolutions

### Sistema de Gestión de Entregas de Alimentos

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-Proprietary-red.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/)

*Una aplicación web completa para la gestión integral de pedidos de comida a domicilio*

[Características](#características-principales) •
[Tecnologías](#tecnologías-utilizadas) •
[Instalación](#instalación) •
[Documentación](#documentación) •
[Equipo](#equipo-de-desarrollo)

</div>

---

## 📋 Descripción del Proyecto

**DeliveringSolutions** es una aplicación web empresarial desarrollada con Spring Boot que facilita la gestión completa de pedidos de comida a domicilio. El sistema conecta a clientes, restaurantes y repartidores en una plataforma integral que gestiona desde la creación del pedido hasta su entrega final.

Este proyecto fue desarrollado como parte de la asignatura **Ingeniería del Software II** en la **Universidad de Castilla-La Mancha (UCLM)**, siguiendo metodologías ágiles con Scrum y aplicando las mejores prácticas de desarrollo de software.

### 🎯 Objetivos del Proyecto

- Implementar un sistema de gestión de entregas escalable y robusto
- Aplicar principios SOLID y patrones de diseño en arquitectura empresarial
- Desarrollar con metodología ágil (Scrum) en 8 sprints
- Garantizar calidad mediante testing exhaustivo (JUnit, TestNG, Mockito)
- Asegurar la aplicación contra vulnerabilidades OWASP Top 10

---

## ✨ Características Principales

### 👥 Gestión de Usuarios
- **Registro y autenticación** de clientes, restaurantes y repartidores
- **Perfiles diferenciados** con roles y permisos específicos
- **Autenticación segura** con BCrypt (strength 12)
- **Gestión de sesiones** con timeout de 30 minutos

### 🍽️ Gestión de Restaurantes
- **Catálogo de restaurantes** con información detallada
- **Gestión de menús** y cartas personalizadas
- **Items de menú** con precios y descripciones
- **Panel de gestión** para administración de pedidos

### 🛒 Sistema de Pedidos
- **Creación de pedidos** con múltiples items
- **Carrito de compras** interactivo
- **Seguimiento en tiempo real** del estado del pedido
- **Historial de pedidos** por cliente y restaurante

### 💳 Sistema de Pagos
- **Procesamiento de pagos** integrado
- **Confirmación de pedidos** con detalles completos
- **Gestión de transacciones** seguras

### 🚗 Gestión de Entregas
- **Asignación de repartidores** a pedidos
- **Seguimiento de entregas** en progreso
- **Gestión de zonas** de reparto por código postal
- **Servicio de entrega** con tiempos estimados

### 📍 Gestión de Direcciones
- **Múltiples direcciones** por cliente
- **Validación de códigos postales** y zonas de reparto
- **Direcciones de entrega** predeterminadas

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.1.5** - Framework principal
- **Spring Data JPA** - Capa de persistencia
- **Hibernate 6.2.6** - ORM (Object-Relational Mapping)
- **Maven 3.9.9** - Gestión de dependencias

### Frontend
- **Thymeleaf** - Motor de plantillas
- **HTML5 / CSS3** - Estructura y estilos
- **JavaScript** - Interactividad del cliente
- **Bootstrap** - Framework CSS responsive

### Base de Datos
- **MySQL 8.2.0** - Base de datos relacional
- **Amazon RDS** - Hosting de base de datos en la nube

### Seguridad
- **Spring Security** - Framework de seguridad
- **BCryptPasswordEncoder** - Hashing de contraseñas (strength 12)
- **CSRF Protection** - Protección contra ataques CSRF
- **Input Validation** - Validación de entrada con Jakarta Validation

### Testing & Calidad
- **JUnit 4.13.2** - Testing unitario
- **TestNG 7.5.1** - Testing avanzado
- **Mockito** - Framework de mocking
- **JaCoCo 0.8.11** - Cobertura de código
- **SonarQube** - Análisis estático de código

### DevOps & Herramientas
- **Git** - Control de versiones
- **GitHub** - Repositorio remoto
- **Maven Wrapper** - Gestión de versión de Maven
- **Spring DevTools** - Hot reload en desarrollo

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas (Layered Architecture) con separación de responsabilidades:

```
DeliveringSolutions/
├── src/
│   ├── main/
│   │   ├── java/com/isoii/deliveringsolutions/
│   │   │   ├── DeliveringSolutions.java          # Clase principal Spring Boot
│   │   │   ├── ServletInitializer.java           # Inicializador WAR
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java           # Configuración de seguridad
│   │   │   ├── dominio/
│   │   │   │   ├── controladores/                # 13 controladores (capa de presentación)
│   │   │   │   │   ├── GestorCliente.java
│   │   │   │   │   ├── GestorRestaurante.java
│   │   │   │   │   ├── GestorPedido.java
│   │   │   │   │   ├── GestorPago.java
│   │   │   │   │   ├── GestorRepartidor.java
│   │   │   │   │   └── ...
│   │   │   │   ├── entidades/                    # 14 entidades JPA (modelo de dominio)
│   │   │   │   │   ├── Usuario.java
│   │   │   │   │   ├── Cliente.java
│   │   │   │   │   ├── Restaurante.java
│   │   │   │   │   ├── Pedido.java
│   │   │   │   │   ├── Pago.java
│   │   │   │   │   └── ...
│   │   │   │   └── service/                      # 15 servicios (lógica de negocio)
│   │   │   │       ├── ServiceUser.java
│   │   │   │       ├── ServiceClient.java
│   │   │   │       ├── ServicePedido.java
│   │   │   │       └── ...
│   │   │   └── persistencia/                     # 14 DAOs (acceso a datos)
│   │   │       ├── UsuarioDAO.java
│   │   │       ├── ClienteDAO.java
│   │   │       ├── PedidoDAO.java
│   │   │       └── ...
│   │   └── resources/
│   │       ├── application.properties            # Configuración de la aplicación
│   │       ├── static/                           # Recursos estáticos
│   │       │   ├── css/                          # 30+ archivos CSS
│   │       │   ├── js/                           # 6 archivos JavaScript
│   │       │   └── img/                          # Imágenes
│   │       └── templates/                        # 48 plantillas Thymeleaf HTML
│   └── test/
│       └── java/com/isoii/deliveringsolutions/   # 14 clases de test
├── config/
│   └── application.properties                    # Configuración con variables de entorno
├── bbdd/
│   └── DeliveringSolutionsDB                     # Base de datos local
├── doc/                                          # Documentación del proyecto
│   ├── planificacionSprint[1-8].md              # Planificación de sprints
│   ├── HistorialSprint.pdf                       # Histórico completo
│   ├── Testing-DeliveringSolution.xlsx           # Plan de testing
│   ├── GeneracionDeCalidad.pdf                   # Documentación de calidad
│   ├── PlanConfiguracion.pdf                     # Plan de configuración
│   └── PlanDeMantenimiento.pdf                   # Plan de mantenimiento
├── .env.example                                  # Plantilla de variables de entorno
├── .gitignore                                    # Archivos excluidos de Git
├── pom.xml                                       # Definición de dependencias Maven
├── sonar-project.properties                      # Configuración SonarQube
└── README.md                                     # Este archivo
```

### 🔄 Flujo de Datos

```
Cliente/Browser
    ↓
[Controladores] ← HTTP Requests/Responses (Thymeleaf)
    ↓
[Servicios] ← Lógica de Negocio
    ↓
[DAOs] ← Persistencia (JPA/Hibernate)
    ↓
[Base de Datos MySQL]
```

---

## 🔐 Seguridad Implementada

El proyecto implementa las mejores prácticas de seguridad siguiendo las recomendaciones de OWASP:

### ✅ Medidas de Seguridad Aplicadas

1. **Autenticación Segura**
   - BCryptPasswordEncoder con strength 12 (recomendado por OWASP)
   - Hashing de contraseñas (nunca almacenadas en texto plano)
   - Validación de credenciales robusta

2. **Protección de Credenciales**
   - Variables de entorno para credenciales sensibles
   - Archivo `.env` excluido del repositorio
   - Archivo `.env.example` como plantilla

3. **Protección CSRF**
   - Tokens CSRF en todos los formularios
   - Configuración Spring Security CSRF

4. **Validación de Entrada**
   - Jakarta Validation API
   - Validación regex para prevenir inyecciones
   - Sanitización de datos de usuario

5. **Gestión Segura de Sesiones**
   - Timeout de sesión: 30 minutos
   - Cookies HttpOnly (previene XSS)
   - Cookies Secure (solo HTTPS en producción)

6. **Logging Seguro**
   - Contraseñas excluidas de logs (@JsonIgnore)
   - Niveles de logging configurables
   - No exposición de información sensible

7. **Configuración de Base de Datos**
   - Credenciales mediante variables de entorno
   - Conexiones seguras a AWS RDS
   - Prepared statements (JPA previene SQL injection)

### 🛡️ Vulnerabilidades OWASP Top 10 Mitigadas

| Vulnerabilidad | Estado | Medidas Aplicadas |
|----------------|--------|-------------------|
| A01:2021 – Broken Access Control | ✅ | Validación de sesiones y roles |
| A02:2021 – Cryptographic Failures | ✅ | BCrypt, variables de entorno |
| A03:2021 – Injection | ✅ | JPA/Hibernate, validación de entrada |
| A04:2021 – Insecure Design | ✅ | Arquitectura en capas, principios SOLID |
| A05:2021 – Security Misconfiguration | ✅ | Configuración segura, `.env` |
| A06:2021 – Vulnerable Components | ✅ | Dependencias actualizadas |
| A07:2021 – Authentication Failures | ✅ | BCrypt, gestión de sesiones |
| A08:2021 – Software & Data Integrity | ✅ | Validación, control de versiones |
| A09:2021 – Logging & Monitoring | ✅ | Logging seguro configurado |
| A10:2021 – SSRF | ✅ | Sin endpoints de fetch externo |

---

## 📦 Instalación

### Requisitos Previos

Asegúrate de tener instalado:

- **Java JDK 17+** ([Descargar](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))
- **Maven 3.6+** ([Descargar](https://maven.apache.org/download.cgi))
- **MySQL 8.0+** ([Descargar](https://dev.mysql.com/downloads/))
- **Git** ([Descargar](https://git-scm.com/downloads))

### Pasos de Instalación

#### 1. Clonar el Repositorio

```bash
git clone https://github.com/lopezmIsmael/DeliveringSolutions.git
cd DeliveringSolutions
```

#### 2. Configurar Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto basándote en `.env.example`:

```bash
cp .env.example .env
```

Edita el archivo `.env` con tus credenciales:

```bash
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/DeliveringSolutionsDB
DB_USERNAME=tu_usuario_mysql
DB_PASSWORD=tu_contraseña_mysql

# Application Configuration
APP_NAME=DeliveringSolutions
APP_PORT=8080

# CORS Configuration (si usas frontend separado)
APP_CORS_ALLOWED_ORIGINS=http://localhost:3000

# Logging
LOGGING_LEVEL=INFO
LOGGING_LEVEL_SECURITY=DEBUG
```

#### 3. Crear la Base de Datos

Conéctate a MySQL y crea la base de datos:

```bash
mysql -u root -p
```

```sql
CREATE DATABASE DeliveringSolutionsDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

#### 4. Exportar Variables de Entorno

**En Linux/macOS:**

```bash
export DB_URL="jdbc:mysql://localhost:3306/DeliveringSolutionsDB"
export DB_USERNAME="tu_usuario"
export DB_PASSWORD="tu_contraseña"
```

**En Windows (PowerShell):**

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/DeliveringSolutionsDB"
$env:DB_USERNAME="tu_usuario"
$env:DB_PASSWORD="tu_contraseña"
```

#### 5. Compilar el Proyecto

```bash
./mvnw clean install
```

O si tienes Maven instalado globalmente:

```bash
mvn clean install
```

#### 6. Ejecutar la Aplicación

```bash
./mvnw spring-boot:run
```

O:

```bash
mvn spring-boot:run
```

#### 7. Acceder a la Aplicación

Abre tu navegador y visita:

```
http://localhost:8080
```

---

## 🧪 Testing

### Ejecutar Tests

```bash
# Ejecutar todos los tests
./mvnw test

# Ejecutar tests y generar reporte de cobertura
./mvnw verify

# Ver reporte de cobertura JaCoCo
open target/site/jacoco/index.html
```

### Cobertura de Código

El proyecto utiliza **JaCoCo** para medir la cobertura de código. Los reportes se generan en:

```
target/site/jacoco/index.html
```

### Tests Implementados

- **14 clases de test** (una por cada controlador principal)
- **JUnit 4.13.2** para tests unitarios
- **TestNG 7.5.1** para tests avanzados
- **Mockito** para mocking de dependencias

**Plan de Testing Completo:** Ver `doc/Testing-DeliveringSolution.xlsx`

---

## 🚀 Despliegue en Producción

### Configuración para Producción

#### 1. Variables de Entorno

Asegúrate de configurar las siguientes variables en tu servidor:

```bash
export DB_URL="jdbc:mysql://tu-servidor-rds.amazonaws.com:3306/DeliveringSolutionsDB"
export DB_USERNAME="admin_produccion"
export DB_PASSWORD="contraseña_muy_segura_aquí"
export SERVER_PORT=8080
```

#### 2. Compilar para Producción

```bash
./mvnw clean package -DskipTests
```

Esto generará un archivo JAR ejecutable en:

```
target/DS-1.1.3.jar
```

#### 3. Ejecutar en Producción

```bash
java -jar target/DS-1.1.3.jar
```

#### 4. Configuración SSL/HTTPS (Recomendado)

Para producción, configura HTTPS añadiendo en `application.properties`:

```properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=deliveringsolutions
server.port=8443
```

#### 5. Despliegue en AWS

El proyecto está configurado para funcionar con **Amazon RDS** (MySQL) y puede desplegarse en:

- **AWS Elastic Beanstalk**
- **AWS EC2** con instancia Linux
- **Docker** (crear Dockerfile)

---

## 📚 Documentación

El proyecto cuenta con documentación exhaustiva en la carpeta [`doc/`](doc/):

### 📄 Documentos Principales

| Documento | Descripción |
|-----------|-------------|
| [planificacionSprint1.md](doc/planificacionSprint1.md) | Sprint 1 - Entidades base y registro |
| [planificacionSprint2.md](doc/planificacionSprint2.md) | Sprint 2 - Funcionalidad de pedidos |
| [planificacionSprint3.md](doc/planificacionSprint3.md) | Sprint 3 - Sistema de pagos |
| [planificacionSprint4.md](doc/planificacionSprint4.md) | Sprint 4 - Gestión de repartidores |
| [planificacionSprint5.md](doc/planificacionSprint5.md) | Sprint 5 - Mejoras de interfaz |
| [planificacionSprint6.md](doc/planificacionSprint6.md) | Sprint 6 - Refactorización y optimización |
| [planificacionSprint7.md](doc/planificacionSprint7.md) | Sprint 7 - Testing y calidad |
| [planificacionSprint8.md](doc/planificacionSprint8.md) | Sprint 8 - Documentación y despliegue |
| [HistorialSprint.pdf](doc/HistorialSprint.pdf) | Histórico completo de todos los sprints |
| [Testing-DeliveringSolution.xlsx](doc/Testing-DeliveringSolution.xlsx) | Plan detallado de testing |
| [GeneracionDeCalidad.pdf](doc/GeneracionDeCalidad.pdf) | Documentación de calidad del software |
| [PlanConfiguracion.pdf](doc/PlanConfiguracion.pdf) | Plan de gestión de configuración |
| [PlanDeMantenimiento.pdf](doc/PlanDeMantenimiento.pdf) | Plan de mantenimiento del sistema |

### 📸 Capturas de Pantalla

Más de **30 capturas de pantalla** documentando el progreso del proyecto disponibles en [`doc/imagenesSprint/`](doc/imagenesSprint/)

---

## 🔧 Scripts Útiles

### Desarrollo

```bash
# Compilar sin ejecutar tests
./mvnw clean compile

# Hot reload (con DevTools activado)
./mvnw spring-boot:run

# Limpiar archivos compilados
./mvnw clean
```

### Testing

```bash
# Ejecutar solo tests unitarios
./mvnw test

# Ejecutar tests con cobertura
./mvnw verify

# Generar reporte de tests
./mvnw surefire-report:report
```

### Calidad de Código

```bash
# Análisis con SonarQube (requiere SonarQube corriendo)
./mvnw sonar:sonar

# Ver reporte JaCoCo
open target/site/jacoco/index.html
```

### Build

```bash
# Build completo con tests
./mvnw clean package

# Build sin tests (más rápido)
./mvnw clean package -DskipTests

# Build y ejecutar
./mvnw clean spring-boot:run
```

---

## 🗄️ Base de Datos

### Modelo de Datos

El sistema utiliza **14 entidades JPA** con relaciones entre ellas:

#### Entidades Principales

1. **Usuario** - Datos de autenticación (email, password)
2. **Cliente** - Información de clientes
3. **Restaurante** - Datos de restaurantes
4. **Repartidor** - Información de repartidores
5. **Pedido** - Pedidos realizados
6. **ItemPedido** - Items individuales de cada pedido
7. **Pago** - Información de pagos
8. **CartaMenu** - Carta de menú de cada restaurante
9. **ItemMenu** - Items del menú (platos)
10. **ServicioEntrega** - Servicios de entrega
11. **Direccion** - Direcciones de entrega
12. **Zona** - Zonas de reparto
13. **CodigoPostal** - Códigos postales
14. **ZonaCodigoPostal** - Relación muchos-a-muchos entre Zona y CodigoPostal

### Diagrama ER

El diagrama entidad-relación completo está disponible en la documentación del proyecto.

### Configuración de Hibernate

```properties
spring.jpa.hibernate.ddl-auto=update    # Actualiza esquema automáticamente
spring.jpa.show-sql=false               # No mostrar SQL en producción
spring.jpa.open-in-view=false           # Prevenir lazy loading issues
```

---

## 🌐 API Endpoints

### Autenticación y Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/usuarios/login` | Formulario de login |
| POST | `/usuarios/loginUsuario` | Procesar login |
| POST | `/usuarios/registrarUsuario` | Registro de usuario |
| GET | `/usuarios/logout` | Cerrar sesión |

### Clientes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/clientes/verRestaurantes` | Listar restaurantes disponibles |
| GET | `/clientes/verMenusRestaurante/{id}` | Ver menú de un restaurante |
| POST | `/clientes/registrarCliente` | Registro de cliente |
| GET | `/clientes/perfil` | Ver perfil del cliente |

### Restaurantes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/restaurantes/findAll` | Listar todos los restaurantes |
| GET | `/restaurantes/gestion/{id}` | Panel de gestión del restaurante |
| POST | `/restaurantes/registrarRestaurante` | Registro de restaurante |

### Pedidos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/pago/registrarPedido` | Crear nuevo pedido |
| GET | `/pago/confirmacion` | Página de confirmación |
| GET | `/pedidos/historial` | Historial de pedidos |

### Repartidores

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/repartidores/registrarRepartidor` | Registro de repartidor |
| GET | `/repartidores/pedidosAsignados` | Ver pedidos asignados |

---

## 🛠️ Solución de Problemas

### Error: No se puede conectar a la base de datos

**Problema:**
```
com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
```

**Solución:**
1. Verifica que MySQL está corriendo: `sudo systemctl status mysql`
2. Comprueba las credenciales en el archivo `.env`
3. Asegúrate de que las variables de entorno están exportadas
4. Verifica que el puerto 3306 está abierto

### Error: 403 Forbidden en formularios

**Problema:**
```
403 Forbidden - Invalid CSRF Token
```

**Solución:**
Asegúrate de que todos los formularios incluyen el token CSRF:

```html
<input type="hidden" name="_csrf" th:value="${_csrf.token}" />
```

### Error: Contraseña incorrecta

**Problema:**
Las contraseñas antiguas en texto plano no funcionan después de implementar BCrypt.

**Solución:**
Ejecuta un script de migración para hashear las contraseñas existentes:

```sql
-- IMPORTANTE: Las contraseñas deben ser hasheadas con BCrypt
-- Consulta la documentación para el script de migración
```

### Error: Variables de entorno no reconocidas

**Problema:**
```
Could not resolve placeholder 'DB_URL' in value "${DB_URL}"
```

**Solución:**

**Linux/macOS:**
```bash
export DB_URL="jdbc:mysql://localhost:3306/DeliveringSolutionsDB"
export DB_USERNAME="root"
export DB_PASSWORD="tu_contraseña"
```

**Windows (CMD):**
```cmd
set DB_URL=jdbc:mysql://localhost:3306/DeliveringSolutionsDB
set DB_USERNAME=root
set DB_PASSWORD=tu_contraseña
```

### Error: Puerto 8080 ya está en uso

**Solución:**
Cambia el puerto en `application.properties`:

```properties
server.port=8081
```

O mata el proceso que está usando el puerto:

```bash
# Linux/macOS
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

---

## 🤝 Contribución

### Guía de Contribución

Si deseas contribuir al proyecto, sigue estos pasos:

1. **Fork** el repositorio
2. Crea una **rama feature** (`git checkout -b feature/nueva-caracteristica`)
3. **Commit** tus cambios (`git commit -am 'Agregar nueva característica'`)
4. **Push** a la rama (`git push origin feature/nueva-caracteristica`)
5. Crea un **Pull Request**

### Estándares de Código

- Seguir convenciones de Java (camelCase para variables, PascalCase para clases)
- Documentar métodos públicos con Javadoc
- Escribir tests para nueva funcionalidad
- Mantener cobertura de código > 70%
- Pasar análisis de SonarQube sin errores críticos

### Commits

Seguir el formato de [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: agregar funcionalidad de cupones de descuento
fix: corregir cálculo de precio total
docs: actualizar README con nueva configuración
refactor: simplificar lógica de validación de pedidos
test: agregar tests para GestorPago
```

---

## 👥 Equipo de Desarrollo

Este proyecto fue desarrollado por un equipo de 4 estudiantes de Ingeniería Informática en la **Universidad de Castilla-La Mancha (UCLM)**:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/lopezmIsmael">
        <img src="https://github.com/lopezmIsmael.png" width="100px;" alt="Ismael López"/><br />
        <sub><b>Ismael López Márquez</b></sub>
      </a><br />
      <sub>Product Owner | Backend Developer</sub><br />
      📧 <a href="mailto:ismael.lopez6@alu.uclm.es">ismael.lopez6@alu.uclm.es</a>
    </td>
    <td align="center">
      <a href="https://github.com/JorgeLopezGomez">
        <img src="https://github.com/JorgeLopezGomez.png" width="100px;" alt="Jorge López"/><br />
        <sub><b>Jorge López Gómez</b></sub>
      </a><br />
      <sub>Scrum Master | Full Stack Developer</sub><br />
      📧 <a href="mailto:Jorge.Lopez32@alu.uclm.es">Jorge.Lopez32@alu.uclm.es</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/marcowork2707">
        <img src="https://github.com/marcowork2707.png" width="100px;" alt="Marco Muñoz"/><br />
        <sub><b>Marco Muñoz</b></sub>
      </a><br />
      <sub>Developer | QA Engineer</sub><br />
      📧 <a href="mailto:marco.munoz@alu.uclm.es">marco.munoz@alu.uclm.es</a>
    </td>
    <td align="center">
      <a href="https://github.com/PabloVerduguez">
        <img src="https://github.com/PabloVerduguez.png" width="100px;" alt="Pablo Verdúguez"/><br />
        <sub><b>Pablo Verdúguez</b></sub>
      </a><br />
      <sub>Developer | Frontend Specialist</sub><br />
      📧 <a href="mailto:pablo.verduguez@alu.uclm.es">pablo.verduguez@alu.uclm.es</a>
    </td>
  </tr>
</table>

### 📊 Contribuciones

| Miembro | Líneas de Código | Commits | Tests | Documentación |
|---------|------------------|---------|-------|---------------|
| Ismael López | ~8,000 | 75+ | 30+ | Sprints 1-3, 7 |
| Jorge López | ~7,500 | 70+ | 28+ | Sprints 4-6, 8 |
| Marco Muñoz | ~6,000 | 55+ | 25+ | Testing, QA |
| Pablo Verdúguez | ~5,500 | 50+ | 20+ | Frontend, UI/UX |

**Total:** ~27,000 líneas de código • 250+ commits • 100+ tests • 8 sprints

---

## 📊 Metodología de Desarrollo

### 🔄 Scrum - Metodología Ágil

El proyecto se desarrolló siguiendo la metodología **Scrum** con las siguientes características:

- **8 Sprints** de 2 semanas cada uno
- **Sprint Planning** al inicio de cada sprint
- **Daily Standups** virtuales (3 por semana)
- **Sprint Review** al finalizar cada sprint
- **Sprint Retrospective** para mejora continua

### 📅 Timeline del Proyecto

```
Sprint 1 (Oct 2024)  → Entidades base, Registro
Sprint 2 (Oct 2024)  → Sistema de pedidos
Sprint 3 (Oct 2024)  → Sistema de pagos
Sprint 4 (Oct 2024)  → Gestión de repartidores
Sprint 5 (Nov 2024)  → Mejoras de interfaz
Sprint 6 (Nov 2024)  → Refactorización
Sprint 7 (Nov 2024)  → Testing y calidad
Sprint 8 (Nov 2024)  → Documentación y despliegue
```

### 🎯 Objetivos por Sprint

| Sprint | Objetivo Principal | Features Implementadas |
|--------|-------------------|------------------------|
| 1 | Estructura base | Entidades Usuario, Cliente, Restaurante, Repartidor |
| 2 | Funcionalidad básica | Sistema de pedidos, Carrito, Items |
| 3 | Pagos | Procesamiento de pagos, Confirmaciones |
| 4 | Entregas | Asignación de repartidores, Zonas |
| 5 | UX/UI | Mejora de interfaces, Templates |
| 6 | Optimización | Refactoring, Performance |
| 7 | Calidad | Testing exhaustivo, SonarQube |
| 8 | Cierre | Documentación, Seguridad, Deploy |

---

## 📈 Métricas del Proyecto

### Estadísticas de Código

- **Líneas de código (Java):** ~27,000
- **Clases Java:** 72 (58 producción + 14 tests)
- **Métodos:** ~450
- **Archivos HTML:** 48 templates Thymeleaf
- **Archivos CSS:** 30+
- **Archivos JavaScript:** 6

### Cobertura de Tests

- **Test Coverage:** >70% (medido con JaCoCo)
- **Tests Unitarios:** 100+ casos de prueba
- **Tests de Integración:** 25+ casos
- **Frameworks:** JUnit, TestNG, Mockito

### Calidad de Código (SonarQube)

- **Bugs:** 0
- **Vulnerabilidades:** 0
- **Code Smells:** <10
- **Duplicación:** <3%
- **Maintainability:** A

---

## 📜 Licencia

Copyright © 2024 DeliveringSolutions - Todos los derechos reservados.

Este proyecto fue desarrollado con fines académicos para la asignatura de **Ingeniería del Software II** en la **Universidad de Castilla-La Mancha (UCLM)**.

---

## 📞 Contacto y Soporte

### Para Consultas Académicas

- **Universidad:** Universidad de Castilla-La Mancha (UCLM)
- **Asignatura:** Ingeniería del Software II
- **Curso:** 2024/2025

### Para Consultas Técnicas

Contacta con cualquier miembro del equipo de desarrollo a través de sus correos institucionales (ver sección [Equipo de Desarrollo](#equipo-de-desarrollo)).

### Reportar Issues

Si encuentras algún bug o tienes sugerencias de mejora:

1. Abre un **Issue** en GitHub
2. Describe el problema detalladamente
3. Incluye pasos para reproducir el error
4. Adjunta capturas de pantalla si es posible

---

## 🙏 Agradecimientos

Queremos agradecer a:

- **Universidad de Castilla-La Mancha (UCLM)** por la formación y recursos
- **Profesores de Ingeniería del Software II** por su guía y feedback
- **Comunidad de Spring Boot** por la excelente documentación
- **Stack Overflow** y comunidades de desarrollo por resolver dudas
- **Compañeros de clase** por el feedback durante las revisiones

---

## 📚 Referencias y Recursos

### Documentación Oficial

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Reference Manual](https://dev.mysql.com/doc/)

### Seguridad

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [BCrypt Password Encoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html)

### Testing

- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Mockito Framework](https://site.mockito.org/)
- [JaCoCo Java Code Coverage](https://www.jacoco.org/)

---

<div align="center">

## ⭐ Si te ha gustado este proyecto, dale una estrella en GitHub

[![GitHub stars](https://img.shields.io/github/stars/lopezmIsmael/DeliveringSolutions?style=social)](https://github.com/lopezmIsmael/DeliveringSolutions)

---

**Desarrollado con ❤️ por el equipo de DeliveringSolutions**

**Universidad de Castilla-La Mancha (UCLM) • 2024**

</div>
