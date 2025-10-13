# 🔄 Orden de Creación de Entidades - El Buen Sabor

## 📌 Regla de Oro
**"No puedes crear una entidad hija sin que exista su entidad padre"**

---

## 🎯 Flujo de Creación Simplificado

### 1️⃣ **Entidades Base** (Sin dependencias)
```
✅ Pais
✅ TipoRol  
✅ UnidadMedida
✅ EstadoPedido
✅ TipoEnvio
✅ TipoPago
✅ TipoPromocion
✅ Empresa
✅ Categoria
```

### 2️⃣ **Configuración Geográfica**
```
Pais
  └─ Provincia
      └─ Ciudad
          └─ Direccion
```

### 3️⃣ **Configuración de Empresa**
```
Empresa
  └─ Sucursal
      ├─ (requiere Direccion opcional)
      └─ (puede tener Empleados, Stock, Pedidos, Promociones)
```

### 4️⃣ **Configuración de Seguridad**
```
TipoRol
  └─ Rol
      └─ Usuario
          ├─ Telefono
          ├─ UserAuthentication
          └─ UsuarioDireccion (requiere Direccion)
```

### 5️⃣ **Configuración de Productos**
```
Categoria
  └─ Subcategoria
      └─ Articulo (requiere UnidadMedida)
          ├─ ArticuloInsumo
          │   └─ StockArticuloInsumo (requiere Sucursal)
          └─ ArticuloManufacturado (requiere Sucursal)
              └─ ArticuloManufacturadoDetalleInsumo (requiere ArticuloInsumo)
```

### 6️⃣ **Promociones**
```
Sucursal + TipoPromocion
  └─ Promocion
      └─ PromocionArticulo (requiere Articulo)
```

### 7️⃣ **Pedidos**
```
Usuario + Sucursal + EstadoPedido + TipoEnvio + TipoPago
  └─ Pedido
      ├─ DetallePedido (requiere Articulo)
      ├─ DetallePromocion (requiere Promocion)
      └─ DireccionPedido (requiere Direccion)
```

---

## 📊 Tabla de Dependencias

| Entidad | Depende de | Puede crear |
|---------|-----------|-------------|
| **Pais** | - | Provincia |
| **Provincia** | Pais | Ciudad |
| **Ciudad** | Provincia | Direccion |
| **Direccion** | Ciudad | UsuarioDireccion, Sucursal, DireccionPedido |
| **Empresa** | - | Sucursal |
| **Sucursal** | Empresa, Direccion(opt) | Empleado, Stock, Pedido, Promocion |
| **TipoRol** | - | Rol |
| **Rol** | TipoRol | Usuario |
| **Usuario** | Rol | Pedido, Telefono, UsuarioDireccion, UserAuthentication |
| **Categoria** | - | Subcategoria |
| **Subcategoria** | Categoria | Articulo |
| **UnidadMedida** | - | Articulo |
| **Articulo** | Subcategoria, UnidadMedida | DetallePedido, PromocionArticulo |
| **ArticuloInsumo** | (hereda de Articulo) | StockArticuloInsumo, DetalleInsumo |
| **ArticuloManufacturado** | (hereda de Articulo), Sucursal | DetalleInsumo |
| **Promocion** | Sucursal, TipoPromocion | PromocionArticulo, DetallePromocion |
| **Pedido** | Usuario, Sucursal, EstadoPedido, TipoEnvio, TipoPago | DetallePedido, DetallePromocion, DireccionPedido |

---

## 🚀 Ejemplo Práctico: Crear un Pedido Completo

### Paso 1: Configuración Inicial (Una sola vez)
```sql
-- Geografía
INSERT INTO Pais (nombre) VALUES ('Argentina');
INSERT INTO Provincia (nombre, id_pais) VALUES ('Mendoza', 1);
INSERT INTO Ciudad (nombre, id_provincia) VALUES ('Godoy Cruz', 1);

-- Empresa
INSERT INTO Empresa (nombre, razon_social, cuil) VALUES ('El Buen Sabor', 'El Buen Sabor SA', '20-12345678-9');

-- Dirección de Sucursal
INSERT INTO Direccion (nombre_calle, numeracion, id_ciudad) VALUES ('San Martín', '123', 1);

-- Sucursal
INSERT INTO Sucursal (nombre, hora_apertura, hora_cierre, id_empresa, id_direccion) 
VALUES ('Sucursal Centro', '08:00', '22:00', 1, 1);

-- Seguridad
INSERT INTO TipoRol (nombre_rol) VALUES ('CLIENTE');
INSERT INTO Rol (id_tipo_rol, fecha_alta) VALUES (1, NOW());

-- Productos
INSERT INTO UnidadMedida (denominacion) VALUES ('Unidad');
INSERT INTO Categoria (denominacion, es_para_elaborar) VALUES ('Bebidas', false);
INSERT INTO Subcategoria (denominacion, id_categoria) VALUES ('Gaseosas', 1);

-- Estados y Tipos
INSERT INTO EstadoPedido (denominacion) VALUES ('Pendiente');
INSERT INTO TipoEnvio (denominacion) VALUES ('Delivery');
INSERT INTO TipoPago (denominacion) VALUES ('Efectivo');
```

### Paso 2: Crear Usuario
```sql
-- Usuario
INSERT INTO Usuario (nombre, apellido, email, id_rol) 
VALUES ('Juan', 'Pérez', 'juan@email.com', 1);

-- Dirección del Usuario
INSERT INTO Direccion (nombre_calle, numeracion, id_ciudad, alias) 
VALUES ('Belgrano', '456', 1, 'Casa');

-- Asociar Usuario con Dirección
INSERT INTO usuario_direccion (id_usuario, id_direccion) VALUES (1, 2);
```

### Paso 3: Crear Productos
```sql
-- Artículo Insumo
INSERT INTO Articulo (nombre, descripcion, precio, id_subcategoria, id_unidad_medida, existe, es_para_elaborar) 
VALUES ('Coca Cola 500ml', 'Gaseosa', 500.00, 1, 1, true, false);

INSERT INTO ArticuloInsumo (id, precio_compra) VALUES (1, 300.00);

-- Stock
INSERT INTO StockArticuloInsumo (id_articulo_insumo, id_sucursal, stock_actual, stock_minimo, stock_maximo) 
VALUES (1, 1, 100, 10, 200);
```

### Paso 4: Crear Pedido
```sql
-- Pedido
INSERT INTO Pedido (tiempo_estimado, fecha, id_usuario, id_sucursal, id_estado_pedido, id_tipo_envio, id_tipo_pago, existe) 
VALUES ('30 minutos', NOW(), 1, 1, 1, 1, 1, true);

-- Detalle Pedido
INSERT INTO Detalle_Pedido (id_pedido, id_articulo, cantidad) 
VALUES (1, 1, 2);

-- Dirección del Pedido
INSERT INTO DireccionPedido (id_pedido, id_direccion) 
VALUES (1, 2);
```

---

## ⚠️ Errores Comunes

### ❌ Error 1: Crear Subcategoría sin Categoría
```java
// INCORRECTO
Subcategoria sub = new Subcategoria();
sub.setDenominacion("Pizzas");
// sub.setCategoria(null); // ❌ Falta la categoría
subcategoriaRepository.save(sub); // ERROR: FK violation
```

```java
// CORRECTO
Categoria categoria = categoriaRepository.findById(1L)
    .orElseThrow(() -> new Exception("Categoría no encontrada"));

Subcategoria sub = new Subcategoria();
sub.setDenominacion("Pizzas");
sub.setCategoria(categoria); // ✅ Categoría asignada
subcategoriaRepository.save(sub);
```

### ❌ Error 2: Crear Pedido sin Usuario
```java
// INCORRECTO
Pedido pedido = new Pedido();
pedido.setFecha(new Date());
// pedido.setUsuario(null); // ❌ Falta el usuario
pedidoRepository.save(pedido); // ERROR: FK violation
```

```java
// CORRECTO
Usuario usuario = usuarioRepository.findById(1L)
    .orElseThrow(() -> new Exception("Usuario no encontrado"));
Sucursal sucursal = sucursalRepository.findById(1L)
    .orElseThrow(() -> new Exception("Sucursal no encontrada"));

Pedido pedido = new Pedido();
pedido.setFecha(new Date());
pedido.setUsuario(usuario); // ✅
pedido.setSucursal(sucursal); // ✅
pedido.setEstadoPedido(estadoPedido); // ✅
pedidoRepository.save(pedido);
```

### ❌ Error 3: Crear UsuarioDireccion sin Usuario o Direccion
```java
// INCORRECTO
UsuarioDireccion ud = new UsuarioDireccion();
// Faltan usuario y dirección
usuarioDireccionRepository.save(ud); // ERROR: FK violation
```

```java
// CORRECTO
Usuario usuario = usuarioRepository.findById(1L).orElseThrow();
Direccion direccion = direccionRepository.findById(1L).orElseThrow();

UsuarioDireccion ud = new UsuarioDireccion();
ud.setUsuario(usuario);
ud.setDireccion(direccion);
usuarioDireccionRepository.save(ud);
```

---

## 🎓 Resumen Visual

```
NIVEL 1 (Base)
├─ Pais, TipoRol, UnidadMedida, Empresa, Categoria
├─ EstadoPedido, TipoEnvio, TipoPago, TipoPromocion
│
NIVEL 2 (Configuración)
├─ Provincia, Ciudad, Direccion
├─ Rol, Usuario
├─ Sucursal
├─ Subcategoria
│
NIVEL 3 (Entidades Principales)
├─ Articulo (Insumo/Manufacturado)
├─ UsuarioDireccion
├─ Stock
├─ Promocion
│
NIVEL 4 (Transacciones)
├─ Pedido
│   ├─ DetallePedido
│   ├─ DetallePromocion
│   └─ DireccionPedido
```

---

## ✅ Checklist de Creación

Antes de crear cualquier entidad, verifica:

- [ ] ¿Existen todas las entidades padre necesarias?
- [ ] ¿Los IDs de las entidades padre son válidos?
- [ ] ¿Los campos obligatorios están completos?
- [ ] ¿Las relaciones están correctamente configuradas?
- [ ] ¿Se respeta el orden de dependencias?

---

## 🔗 Referencias

- Ver `FLUJO_CREACION_MODELOS.md` para análisis detallado
- Ver `CAMBIOS_MAPEO.md` para cambios en relaciones ManyToMany
