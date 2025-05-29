


# El Buen Sabor - Backend API
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/LucasUdrea-dev/ElBuenSabor-backend-PF25)
## 🍽️ Descripción

**El Buen Sabor** es un sistema integral de gestión de restaurantes desarrollado con Spring Boot que maneja todos los aspectos operativos de establecimientos gastronómicos. Este backend proporciona APIs REST para la gestión de productos, procesamiento de pedidos, control de inventario y administración multi-sucursal. [1](#0-0) 

## 🚀 Características Principales

- **Gestión Multi-sucursal**: Administración de empresas con múltiples ubicaciones
- **Catálogo de Productos**: Sistema jerárquico de artículos, ingredientes y productos manufacturados  
- **Procesamiento de Pedidos**: Gestión completa del ciclo de vida de pedidos con múltiples estados
- **Control de Inventario**: Seguimiento de stock por sucursal con historial de precios
- **Gestión de Usuarios**: Sistema de autenticación con roles diferenciados (clientes/empleados)
- **Integración de Pagos**: Soporte para MercadoPago
- **Documentación API**: Interfaz Swagger para testing y documentación [2](#0-1) 

## 🏗️ Arquitectura

El sistema implementa una arquitectura por capas con un framework genérico de CRUD que proporciona operaciones consistentes en todas las entidades de negocio:

### Dominios de Negocio
- **Gestión de Empresa**: Administración de empresas y sucursales
- **Gestión de Productos**: Catálogo de productos con jerarquía de herencia
- **Gestión de Pedidos**: Procesamiento y seguimiento de órdenes
- **Gestión de Usuarios**: Cuentas de usuario y control de acceso basado en roles
- **Gestión de Ubicaciones**: Sistema geográfico jerárquico (País → Provincia → Ciudad → Dirección)
- **Gestión de Inventario**: Control de stock y gestión de precios [3](#0-2) 

## 🛠️ Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17 | Lenguaje de programación |
| **Spring Boot** | 3.4.5 | Framework de aplicación |
| **Spring Data JPA** | - | Acceso a datos y ORM |
| **MySQL** | - | Base de datos principal |
| **OpenAPI/Swagger** | 2.6.0 | Documentación de API |
| **Lombok** | - | Reducción de código boilerplate |
| **Maven** | - | Gestión de dependencias | [4](#0-3) 

## 📚 Estructura del Proyecto

```
src/main/java/com/buenSabor/BackEnd/
├── config/          # Configuraciones (OpenAPI, CORS)
├── controllers/     # Controladores REST por dominio
├── dto/            # Objetos de transferencia de datos
├── enums/          # Enumeraciones del sistema
├── models/         # Entidades JPA organizadas por dominio
├── repositories/   # Repositorios de acceso a datos
└── services/       # Lógica de negocio
``` [5](#0-4) 

## 🌐 API Documentation

La documentación completa de la API está disponible a través de Swagger UI una vez que la aplicación esté ejecutándose:

```
http://localhost:8080/swagger-ui.html
``` [6](#0-5) 

## 📋 Requisitos Previos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.8 o superior

## ⚡ Inicio Rápido

1. **Clonar el repositorio**
2. **Configurar la base de datos MySQL**
3. **Ejecutar la aplicación**:
   ```bash
   ./gradlew bootRun
   ```

La aplicación estará disponible en `http://localhost:8080` [7](#0-6) 

