# DeliveringSolutions

Sistema de gestión de entregas de alimentos con soporte para clientes, restaurantes y repartidores.

## Descripción

DeliveringSolutions es una aplicación web desarrollada en Spring Boot que facilita la gestión de pedidos de comida, desde su creación hasta su entrega.

## Características Principales

- **Gestión de Usuarios**: Registro y autenticación de clientes, restaurantes y repartidores
- **Pedidos**: Creación, seguimiento y gestión de pedidos
- **Pagos**: Sistema integrado de pagos
- **Restaurantes**: Catálogo de restaurantes y menús
- **Entregas**: Asignación y seguimiento de entregas
- **Direcciones**: Gestión de direcciones de entrega

## Tecnologías

- Java 17+
- Spring Boot 3.x
- Spring Security con BCrypt
- JPA/Hibernate
- MySQL
- Thymeleaf
- Bootstrap

## Seguridad Implementada

- Autenticación con BCryptPasswordEncoder (strength 12)
- Variables de entorno para credenciales sensibles
- Protección CSRF en formularios
- Validación de entrada de usuario
- Gestión segura de sesiones
- Configuración de logging seguro

## Instalación

### Requisitos Previos

- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd DeliveringSolutions
```

2. **Configurar variables de entorno**
```bash
cp .env.example .env
# Editar .env con tus valores reales
export DB_URL="jdbc:mysql://localhost:3306/DeliveringSolutionsDB"
export DB_USERNAME="tu_usuario"
export DB_PASSWORD="tu_contraseña"
```

3. **Crear la base de datos**
```bash
mysql -u root -p < database_schema.sql
```

4. **Compilar y ejecutar**
```bash
mvn clean install
mvn spring-boot:run
```

5. **Acceder a la aplicación**
```
http://localhost:8080
```

## Configuración de Producción

### Variables de Entorno Requeridas

```bash
# Base de Datos
DB_URL=jdbc:mysql://host:3306/database
DB_USERNAME=usuario_bd
DB_PASSWORD=contraseña_segura

# Aplicación
SERVER_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/
```

### Configuración de Spring Security

El archivo `SecurityConfig.java` proporciona:
- BCryptPasswordEncoder con strength 12
- Configuración CORS
- Base para WebSecurityConfig

### SSL/HTTPS

Para producción, configurar en `application.properties`:
```properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/isoii/deliveringsolutions/
│   │       ├── config/
│   │       │   └── SecurityConfig.java
│   │       ├── dominio/
│   │       │   ├── controladores/
│   │       │   ├── entidades/
│   │       │   └── service/
│   │       └── persistencia/
│   └── resources/
│       ├── application.properties
│       ├── templates/
│       └── static/
└── test/
```

## Base de Datos

### Tablas Principales

- `usuario` - Datos de autenticación
- `cliente` - Información de clientes
- `restaurante` - Datos de restaurantes
- `repartidor` - Información de repartidores
- `pedido` - Pedidos realizados
- `pago` - Información de pagos
- `direccion` - Direcciones de entrega

## API Endpoints Principales

### Usuarios
- `GET /usuarios/login` - Formulario de login
- `POST /usuarios/loginUsuario` - Procesar login
- `POST /usuarios/registrarUsuario` - Registro de usuario

### Clientes
- `GET /clientes/verRestaurantes` - Ver restaurantes
- `GET /clientes/verMenusRestaurante/{id}` - Ver menú de restaurante
- `POST /clientes/registrarCliente` - Registro de cliente

### Restaurantes
- `GET /restaurantes/findAll` - Listar restaurantes
- `GET /restaurantes/gestion/{id}` - Panel de gestión

### Pedidos
- `POST /pago/registrarPedido` - Crear pedido
- `GET /pago/confirmacion` - Confirmación de pedido

## Desarrollo

### Ejecutar tests
```bash
mvn test
```

### Compilar sin ejecutar
```bash
mvn clean compile
```

### Build para producción
```bash
mvn clean package -DskipTests
```

## Solución de Problemas

### Error de conexión a BD
```
Verificar que MySQL está ejecutándose y las credenciales son correctas
```

### Error 403 Forbidden en formularios
```
Asegurar que el token CSRF está presente en el formulario:
<input type="hidden" name="_csrf" th:value="${_csrf.token}" />
```

### Error de autenticación
```
- Verificar que la contraseña cumple requisitos mínimos
- Revisar que el usuario existe en BD
- Comprobar los logs para más detalles
```

## Contribución

Para contribuir al proyecto:

1. Crear una rama feature (`git checkout -b feature/mi-feature`)
2. Commit cambios (`git commit -am 'Agregar mi feature'`)
3. Push a la rama (`git push origin feature/mi-feature`)
4. Crear Pull Request

## Licencia

Todos los derechos reservados © 2025 DeliveringSolutions

## Contacto

Para soporte y consultas de seguridad:
- Sitio web: https://deliveringsolutions.com
- Email: support@deliveringsolutions.com

## Historial de Versiones

### v1.1 (11 Noviembre 2025)
- Implementación de seguridad mejorada
- BCryptPasswordEncoder para contraseñas
- Variables de entorno para credenciales
- Protección CSRF en formularios
- Validación de entrada mejorada

### v1.0 (Inicial)
- Funcionalidades base de gestión de pedidos

---

**Última actualización:** 11 de Noviembre de 2025
