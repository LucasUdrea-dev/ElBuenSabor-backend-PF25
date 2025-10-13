# Cambios en el Mapeo de Entidades

## Problema Resuelto

MySQL con `sql_require_primary_key=1` no permite crear tablas sin clave primaria. Las relaciones `@ManyToMany` de JPA generan tablas intermedias con claves primarias compuestas, lo cual causaba errores.

## Solución Implementada

Se reemplazó la relación `@ManyToMany` entre `Usuario` y `Direccion` por una entidad intermedia con su propio ID.

### Cambios Realizados

#### 1. Nueva Entidad: `UsuarioDireccion`
**Ubicación:** `models/user/UsuarioDireccion.java`

```java
@Entity
@Table(name = "usuario_direccion")
public class UsuarioDireccion extends Bean {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion", nullable = false)
    private Direccion direccion;
}
```

**Beneficios:**
- ✅ Tiene su propio `id` (heredado de `Bean`)
- ✅ Compatible con `sql_require_primary_key=1`
- ✅ Permite agregar campos adicionales en el futuro (ej: fecha_asignacion, es_principal, etc.)
- ✅ Mejor rendimiento en consultas complejas

#### 2. Actualización de `Usuario`
**Antes:**
```java
@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinTable(name = "Usuario_Direccion",
    joinColumns = @JoinColumn(name = "id_usuario"),
    inverseJoinColumns = @JoinColumn(name = "id_direccion"))
protected List<Direccion> direccionList;
```

**Después:**
```java
@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
protected List<UsuarioDireccion> usuarioDireccionList = new ArrayList<>();
```

**Cambios:**
- Cambio de `EAGER` a `LAZY` para mejor rendimiento
- Agregado `orphanRemoval = true` para limpieza automática
- Inicialización de la lista para evitar `NullPointerException`

#### 3. Actualización de `Direccion`
**Antes:**
```java
@ManyToMany(mappedBy = "direccionList", fetch = FetchType.LAZY)
@JsonIgnore
private List<Usuario> usuarioList;
```

**Después:**
```java
@OneToMany(mappedBy = "direccion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<UsuarioDireccion> usuarioDireccionList = new ArrayList<>();
```

#### 4. Nuevo Repositorio: `UsuarioDireccionRepository`
**Ubicación:** `repositories/user/UsuarioDireccionRepository.java`

```java
@Repository
public interface UsuarioDireccionRepository extends BeanRepository<UsuarioDireccion, Long> {
}
```

#### 5. Corrección en `Revision` (Hibernate Envers)
**Antes:**
```java
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_seq")
@SequenceGenerator(name = "revision_seq", sequenceName = "rbac.seq_revision_id")
```

**Después:**
```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

**Razón:** Evita la creación de tabla `seq_revision_id` sin PK.

## Estructura de Tablas Generadas

### Tabla: `usuario_direccion`
```sql
CREATE TABLE usuario_direccion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_direccion BIGINT NOT NULL,
    eliminado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_direccion) REFERENCES direccion(id)
);
```

## Uso en el Código

### Agregar una dirección a un usuario:
```java
Usuario usuario = usuarioRepository.findById(id).orElseThrow();
Direccion direccion = direccionRepository.findById(direccionId).orElseThrow();

UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
usuarioDireccion.setUsuario(usuario);
usuarioDireccion.setDireccion(direccion);

usuario.getUsuarioDireccionList().add(usuarioDireccion);
usuarioRepository.save(usuario);
```

### Obtener direcciones de un usuario:
```java
Usuario usuario = usuarioRepository.findById(id).orElseThrow();
List<Direccion> direcciones = usuario.getUsuarioDireccionList()
    .stream()
    .map(UsuarioDireccion::getDireccion)
    .collect(Collectors.toList());
```

## Otras Entidades Intermedias (Ya Correctas)

Las siguientes entidades ya estaban correctamente implementadas con su propio ID:

- ✅ `ArticuloManufacturadoDetalleInsumo` - Relación entre ArticuloManufacturado y ArticuloInsumo
- ✅ `DetallePedido` - Relación entre Pedido y Articulo
- ✅ `DetallePromocion` - Relación entre Pedido y Promocion
- ✅ `StockArticuloInsumo` - Stock por sucursal
- ✅ `DireccionPedido` - Dirección específica del pedido

## Verificación

Para verificar que todo funciona correctamente:

1. **Eliminar tablas antiguas** (si existen):
```sql
DROP TABLE IF EXISTS usuario_direccion;
```

2. **Reiniciar la aplicación**: Hibernate creará automáticamente la tabla con la estructura correcta.

3. **Verificar la estructura**:
```sql
DESCRIBE usuario_direccion;
```

Deberías ver:
- `id` (PK, AUTO_INCREMENT)
- `id_usuario` (FK)
- `id_direccion` (FK)
- `eliminado` (BOOLEAN)

## Notas Importantes

- ⚠️ **Migración de Datos**: Si ya tienes datos en la tabla `usuario_direccion` antigua, necesitarás migrarlos manualmente.
- 📝 **DTOs**: Actualiza los DTOs relacionados para trabajar con `UsuarioDireccion` en lugar de acceso directo a `Direccion`.
- 🔄 **Servicios**: Revisa y actualiza los servicios que manejan la relación Usuario-Direccion.

## Beneficios de Este Enfoque

1. **Compatibilidad Total** con MySQL `sql_require_primary_key=1`
2. **Flexibilidad** para agregar campos adicionales (ej: `fechaAsignacion`, `esPrincipal`)
3. **Mejor Rendimiento** con `FetchType.LAZY`
4. **Auditoría** completa gracias a la herencia de `Bean`
5. **Mantenibilidad** mejorada con entidades explícitas
