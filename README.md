


# 🍽️ El Buen Sabor - Sistema de Gestión de Restaurantes

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1.svg)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/LucasUdrea-dev/ElBuenSabor-backend-PF25)

> Sistema integral de gestión empresarial para establecimientos gastronómicos con arquitectura empresarial escalable y multi-sucursal.

## 📋 Tabla de Contenidos

- [Descripción](#-descripción)
- [Características Principales](#-características-principales)
- [Arquitectura](#️-arquitectura)
- [Stack Tecnológico](#️-stack-tecnológico)
- [Requisitos del Sistema](#-requisitos-del-sistema)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Configuración de Base de Datos](#️-configuración-de-base-de-datos)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Documentación de API](#-documentación-de-api)
- [Testing](#-testing)
- [Despliegue](#-despliegue)
- [Contribución](#-contribución)
- [Roadmap](#️-roadmap)
- [Licencia](#-licencia)
- [Contacto](#-contacto)

## 🎯 Descripción

**El Buen Sabor** es una solución empresarial completa para la gestión de restaurantes y establecimientos gastronómicos. Desarrollado con arquitectura empresarial moderna, proporciona un backend robusto y escalable que maneja todos los aspectos operativos críticos del negocio gastronómico. [1](#0-0) 

### 💼 Casos de Uso Empresariales

- **Cadenas de Restaurantes**: Gestión centralizada de múltiples ubicaciones
- **Restaurantes Individuales**: Control integral de operaciones
- **Empresas de Catering**: Gestión de pedidos y logística
- **Food Courts**: Administración multi-tenant

## ⭐ Características Principales

### 🏢 Gestión Multi-Sucursal
- Administración centralizada de empresas con múltiples ubicaciones
- Control de inventario independiente por sucursal
- Reportes consolidados y por sucursal

### 📦 Gestión de Productos
- Sistema jerárquico de categorización
- Gestión de ingredientes y productos manufacturados
- Control de precios históricos y promociones

### 🛒 Procesamiento de Pedidos
- Gestión completa del ciclo de vida de pedidos
- Estados configurables de pedido
- Integración con sistemas de pago (MercadoPago)

### 👥 Gestión de Usuarios
- Sistema de autenticación y autorización basado en roles
- Perfiles diferenciados (clientes, empleados, administradores)
- Auditoría completa de acciones de usuario

### 📊 Control de Inventario
- Seguimiento de stock en tiempo real
- Alertas de stock mínimo
- Historial de movimientos de inventario [2](#0-1) 

## 🏗️ Arquitectura

### Patrones de Diseño Implementados

- **Arquitectura por Capas**: Separación clara de responsabilidades
- **Repository Pattern**: Abstracción de acceso a datos
- **DTO Pattern**: Transferencia segura de datos
- **Mapper Pattern**: Conversión automática entre entidades y DTOs

### Dominios de Negocio

```
📁 Dominios
├── 🏢 Gestión de Empresa (company/)
├── 📦 Gestión de Productos (producto/)
├── 🛒 Gestión de Pedidos (venta/)
├── 👥 Gestión de Usuarios (user/)
├── 🌍 Gestión de Ubicaciones (ubicacion/)
└── 🔐 Seguridad (seguridad/)
``` [3](#0-2) 

## 🛠️ Stack Tecnológico

### Backend Core
| Tecnología | Versión | Propósito | Documentación |
|------------|---------|-----------|---------------|
| **Java** | 17 LTS | Lenguaje de programación | [Oracle Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) |
| **Spring Boot** | 3.4.5 | Framework de aplicación | [Spring Boot Docs](https://spring.io/projects/spring-boot) |
| **Spring Data JPA** | Incluido | ORM y acceso a datos | [Spring Data](https://spring.io/projects/spring-data-jpa) |
| **Hibernate Envers** | 6.6.12 | Auditoría de entidades | [Envers Guide](https://hibernate.org/orm/envers/) |

### Base de Datos
| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **MySQL** | 8.0+ | Base de datos principal |
| **MySQL Connector/J** | Latest | Driver JDBC |

### Herramientas de Desarrollo
| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Gradle** | 8.x | Gestión de dependencias y build |
| **Lombok** | Latest | Reducción de boilerplate |
| **MapStruct** | 1.5.5 | Mapping automático DTO ↔ Entity |
| **OpenAPI 3** | 2.6.0 | Documentación de API |

### Testing
| Tecnología | Propósito |
|------------|-----------|
| **JUnit 5** | Framework de testing |
| **Spring Boot Test** | Testing de integración | [4](#0-3) 

## 💻 Requisitos del Sistema

### Requisitos Mínimos
- **Java**: OpenJDK 17 o superior
- **Memoria RAM**: 4 GB mínimo (8 GB recomendado)
- **Espacio en Disco**: 2 GB para desarrollo
- **MySQL**: 8.0 o superior
- **Gradle**: 8.0 o superior (incluido wrapper)

### Herramientas de Desarrollo Recomendadas
- **IDE**: IntelliJ IDEA Ultimate / Eclipse / VS Code
- **Cliente MySQL**: MySQL Workbench / DBeaver
- **API Testing**: Postman / Insomnia
- **Git**: 2.30 o superior

## 🚀 Instalación y Configuración

### 1. Clonación del Repositorio

```bash
git clone https://github.com/LucasUdrea-dev/ElBuenSabor-backend-PF25.git
cd ElBuenSabor-backend-PF25/BackEnd
```

### 2. Configuración de Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=3306
DB_NAME=elbuensabor
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_password

# Application Configuration
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev

# JWT Configuration
JWT_SECRET=tu_jwt_secret_key
JWT_EXPIRATION=86400000

# MercadoPago Configuration
MERCADOPAGO_ACCESS_TOKEN=tu_access_token
```

### 3. Construcción del Proyecto

```bash
# Verificar instalación de Java
java --version

# Construir el proyecto
./gradlew clean build

# Ejecutar tests
./gradlew test
```

### 4. Ejecución de la Aplicación

```bash
# Modo desarrollo
./gradlew bootRun

# O con jar
./gradlew bootJar
java -jar build/libs/BackEnd-0.0.1-SNAPSHOT.jar
``` [5](#0-4) 

## 🗄️ Configuración de Base de Datos

### Configuración Local

1. **Instalar MySQL 8.0+**

2. **Crear la base de datos**:
```sql
CREATE DATABASE elbuensabor CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'elbuensabor_user'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON elbuensabor.* TO 'elbuensabor_user'@'localhost';
FLUSH PRIVILEGES;
```

3. **Configurar application.properties**:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/elbuensabor?useSSL=false&serverTimezone=UTC
spring.datasource.username=elbuensabor_user
spring.datasource.password=password123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Envers Configuration
spring.jpa.properties.org.hibernate.envers.audit_table_suffix=_AUD
spring.jpa.properties.org.hibernate.envers.revision_field_name=REV
spring.jpa.properties.org.hibernate.envers.revision_type_field_name=REVTYPE
```

### Docker Compose (Recomendado para Desarrollo)

Crea un archivo `docker-compose.yml`:

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: elbuensabor-mysql
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: elbuensabor
      MYSQL_USER: elbuensabor_user
      MYSQL_PASSWORD: password123
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - elbuensabor-network

volumes:
  mysql_data:

networks:
  elbuensabor-network:
    driver: bridge
```

## 📁 Estructura del Proyecto

```
ElBuenSabor-backend-PF25/
├── BackEnd/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/buenSabor/BackEnd/
│   │   │   │   ├── config/           # Configuraciones (OpenAPI, CORS, Security)
│   │   │   │   ├── controllers/      # Controladores REST por dominio
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── enums/           # Enumeraciones del sistema
│   │   │   │   ├── mapper/          # MapStruct mappers
│   │   │   │   ├── models/          # Entidades JPA
│   │   │   │   │   ├── audit/       # Entidades de auditoría
│   │   │   │   │   ├── bean/        # Entidades base
│   │   │   │   │   ├── company/     # Dominio empresa
│   │   │   │   │   ├── producto/    # Dominio productos
│   │   │   │   │   ├── seguridad/   # Dominio seguridad
│   │   │   │   │   ├── ubicacion/   # Dominio ubicaciones
│   │   │   │   │   ├── user/        # Dominio usuarios
│   │   │   │   │   └── venta/       # Dominio ventas
│   │   │   │   ├── repositories/    # Repositorios de acceso a datos
│   │   │   │   ├── services/        # Lógica de negocio
│   │   │   │   └── BackEndApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── static/
│   │   └── test/
│   │       └── java/               # Tests unitarios e integración
│   ├── build.gradle                # Configuración de build
│   ├── gradlew                     # Gradle wrapper
│   └── logs/                       # Archivos de log
├── docs/                           # Documentación adicional
├── docker-compose.yml             # Configuración Docker
├── .gitignore
├── README.md
└── LICENSE
``` [6](#0-5) 

## 📚 Documentación de API

### Swagger UI

Una vez que la aplicación esté ejecutándose, la documentación interactiva de la API estará disponible en:

```
http://localhost:8080/swagger-ui.html
```

### OpenAPI Specification

La especificación OpenAPI 3.0 está disponible en:

```
http://localhost:8080/v3/api-docs
```

### Endpoints Principales

| Dominio | Base URL | Descripción |
|---------|----------|-------------|
| **Empresas** | `/buen-sabor/companies` | Gestión de empresas y sucursales |
| **Productos** | `/buen-sabor/products` | Catálogo de productos |
| **Pedidos** | `/buen-sabor/orders` | Gestión de pedidos |
| **Usuarios** | `/buen-sabor/users` | Gestión de usuarios |
| **Ubicaciones** | `/buen-sabor/locations` | Gestión geográfica | [7](#0-6) 

## 🧪 Testing

### Estructura de Testing

```
src/test/java/
├── unit/                    # Tests unitarios
├── integration/             # Tests de integración
└── e2e/                     # Tests end-to-end
```

### Ejecutar Tests

```bash
# Todos los tests
./gradlew test

# Tests unitarios solamente
./gradlew test --tests "*.unit.*"

# Tests de integración
./gradlew test --tests "*.integration.*"

# Con reporte de cobertura
./gradlew test jacocoTestReport
```

### Configuración de Test Profile

```properties
# application-test.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
logging.level.com.buenSabor=DEBUG
``` [8](#0-7) 

## 🚀 Despliegue

### Preparación para Producción

1. **Crear perfil de producción**:
```properties
# application-prod.properties
spring.profiles.active=prod
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.com.buenSabor=WARN
```

2. **Generar JAR ejecutable**:
```bash
./gradlew bootJar
```

3. **Variables de entorno de producción**:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=tu-servidor-mysql
export DB_USERNAME=usuario_prod
export DB_PASSWORD=password_seguro
```

### Docker Deployment

```dockerfile
FROM openjdk:17-jdk-slim

LABEL maintainer="ElBuenSabor Team"

VOLUME /tmp

COPY build/libs/BackEnd-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
```

### Health Checks

La aplicación incluye actuators de Spring Boot:

```
GET /actuator/health
GET /actuator/info
GET /actuator/metrics
```

## 🤝 Contribución

### Guías de Contribución

1. **Fork del repositorio**
2. **Crear una rama feature**: `git checkout -b feature/nueva-funcionalidad`
3. **Commit con mensaje descriptivo**: `git commit -m 'Add: nueva funcionalidad'`
4. **Push a la rama**: `git push origin feature/nueva-funcionalidad`
5. **Crear Pull Request**

### Estándares de Código

- **Convenciones de naming**: CamelCase para clases, camelCase para métodos
- **Documentación**: JavaDoc obligatorio para APIs públicas
- **Testing**: Cobertura mínima del 80%
- **Code Style**: Checkstyle configurado

### Convenciones de Commit

```
feat: nueva funcionalidad
fix: corrección de bug
docs: cambios en documentación
style: formato, punto y coma faltante, etc
refactor: refactoring de código
test: agregando tests
chore: actualización de tareas de build, configuraciones, etc
```

## 🗺️ Roadmap

### Versión 1.1 (Q2 2024)
- [ ] Implementación de microservicios
- [ ] Integración con Redis para caching
- [ ] API de notificaciones push
- [ ] Dashboard de analytics

### Versión 1.2 (Q3 2024)
- [ ] Módulo de reportes avanzados
- [ ] Integración con sistemas de delivery
- [ ] API para aplicaciones móviles
- [ ] Sistema de fidelización de clientes

### Versión 2.0 (Q4 2024)
- [ ] Arquitectura multi-tenant
- [ ] IA para predicción de demanda
- [ ] Integración con IoT
- [ ] Marketplace de proveedores

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

```
MIT License

Copyright (c) 2024 El Buen Sabor Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

## 📞 Contacto

### Equipo de Desarrollo

- **Líder del Proyecto**: Lucas Udrea
- **Email**: contacto@elbuensabor.com
- **Repositorio**: [GitHub](https://github.com/LucasUdrea-dev/ElBuenSabor-backend-PF25)

### Soporte

- **Issues**: [GitHub Issues](https://github.com/LucasUdrea-dev/ElBuenSabor-backend-PF25/issues)
- **Discussions**: [GitHub Discussions](https://github.com/LucasUdrea-dev/ElBuenSabor-backend-PF25/discussions)
- **Wiki**: [Documentación Técnica](https://github.com/LucasUdrea-dev/ElBuenSabor-backend-PF25/wiki)

---

<div align="center">

**⭐ Si este proyecto te resulta útil, no olvides darle una estrella ⭐**

</div>


