# 🏗️ Arquitectura WebSocket - Múltiples Dashboards

## 📊 Diagrama de Flujo

```
┌─────────────────────────────────────────────────────────────────────┐
│                         BACKEND (Spring Boot)                        │
│                                                                       │
│  ┌────────────────────────────────────────────────────────────────┐ │
│  │         PedidoWebSocketController                              │ │
│  │                                                                 │ │
│  │  @MessageMapping("/pedido.cambiarEstado")                      │ │
│  │  ┌─────────────────────────────────────────────────────────┐  │ │
│  │  │ 1. Recibe cambio de estado                              │  │ │
│  │  │ 2. Actualiza BD (Pedido.estadoPedido)                   │  │ │
│  │  │ 3. Construye PedidoNotificacion                         │  │ │
│  │  │ 4. Envía a múltiples topics:                            │  │ │
│  │  │    ├─> /topic/pedidos/admin                             │  │ │
│  │  │    ├─> /topic/pedidos/sucursal/{id}                     │  │ │
│  │  │    ├─> /topic/pedidos/usuario/{id}                      │  │ │
│  │  │    └─> /topic/pedidos (legacy)                          │  │ │
│  │  └─────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────┘ │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                                │ WebSocket (STOMP)
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
        ▼                       ▼                       ▼
┌───────────────┐      ┌───────────────┐      ┌───────────────┐
│  TOPIC ADMIN  │      │TOPIC SUCURSAL │      │ TOPIC USUARIO │
│               │      │               │      │               │
│ /topic/       │      │ /topic/       │      │ /topic/       │
│ pedidos/admin │      │ pedidos/      │      │ pedidos/      │
│               │      │ sucursal/{id} │      │ usuario/{id}  │
└───────┬───────┘      └───────┬───────┘      └───────┬───────┘
        │                      │                      │
        │                      │                      │
        ▼                      ▼                      ▼
┌───────────────┐      ┌───────────────┐      ┌───────────────┐
│   Dashboard   │      │   Dashboard   │      │   Dashboard   │
│     ADMIN     │      │    COCINA     │      │    CLIENTE    │
│               │      │               │      │               │
│ - Ve TODOS    │      │ - Ve solo     │      │ - Ve solo     │
│   los pedidos │      │   pedidos de  │      │   SUS propios │
│ - Todas las   │      │   SU sucursal │      │   pedidos     │
│   sucursales  │      │               │      │               │
└───────────────┘      └───────────────┘      └───────────────┘
                       ┌───────────────┐
                       │   Dashboard   │
                       │   DELIVERY    │
                       │               │
                       │ - Ve solo     │
                       │   pedidos de  │
                       │   SU sucursal │
                       └───────────────┘
```

---

## 🔄 Flujo Detallado de un Cambio de Estado

### Escenario: Cocinero marca pedido como "LISTO"

```
PASO 1: Frontend (Dashboard Cocina)
┌────────────────────────────────────────────────────────────┐
│ Usuario hace clic en "Marcar como Listo"                   │
│                                                             │
│ webSocketService.cambiarEstadoPedido(42, 3, "15 minutos"); │
│                                                             │
│ Envía a: /app/pedido.cambiarEstado                         │
│ Payload: {                                                 │
│   pedidoId: 42,                                            │
│   nuevoEstadoId: 3,  // LISTO                              │
│   tiempoEstimado: "15 minutos"                             │
│ }                                                           │
└────────────────────────────────────────────────────────────┘
                        │
                        │ WebSocket
                        ▼
PASO 2: Backend (PedidoWebSocketController)
┌────────────────────────────────────────────────────────────┐
│ @MessageMapping("/pedido.cambiarEstado")                   │
│                                                             │
│ 1. Busca Pedido #42 en BD                                  │
│ 2. Busca EstadoPedido #3 (LISTO)                           │
│ 3. Actualiza: pedido.setEstadoPedido(LISTO)                │
│ 4. Actualiza: pedido.setTiempoEstimado("15 minutos")       │
│ 5. Guarda en BD: pedidoRepository.save(pedido)             │
│                                                             │
│ 6. Construye notificación:                                 │
│    {                                                        │
│      pedidoId: 42,                                         │
│      estadoId: 3,                                          │
│      estadoNombre: "LISTO",                                │
│      tiempoEstimado: "15 minutos",                         │
│      usuarioId: 123,                                       │
│      sucursalId: 1,                                        │
│      timestamp: "2025-10-13T15:30:00"                      │
│    }                                                        │
└────────────────────────────────────────────────────────────┘
                        │
                        │ Envía notificación a múltiples topics
                        │
        ┌───────────────┼───────────────┬───────────────┐
        │               │               │               │
        ▼               ▼               ▼               ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ /topic/      │ │ /topic/      │ │ /topic/      │ │ /topic/      │
│ pedidos/     │ │ pedidos/     │ │ pedidos/     │ │ pedidos      │
│ admin        │ │ sucursal/1   │ │ usuario/123  │ │ (legacy)     │
└──────┬───────┘ └──────┬───────┘ └──────┬───────┘ └──────┬───────┘
       │                │                │                │
       ▼                ▼                ▼                ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ Dashboard    │ │ Dashboard    │ │ Dashboard    │ │ Cualquier    │
│ ADMIN        │ │ COCINA       │ │ CLIENTE      │ │ dashboard    │
│              │ │ (Sucursal 1) │ │ (Usuario 123)│ │ legacy       │
│ ✅ Recibe    │ │ ✅ Recibe    │ │ ✅ Recibe    │ │ ✅ Recibe    │
│ notificación │ │ notificación │ │ notificación │ │ notificación │
│              │ │              │ │              │ │              │
│ Actualiza UI │ │ Actualiza UI │ │ Muestra:     │ │ Actualiza UI │
│ con nuevo    │ │ Mueve pedido │ │ "Tu pedido   │ │              │
│ estado       │ │ a sección    │ │ está listo"  │ │              │
│              │ │ "Listos"     │ │              │ │              │
└──────────────┘ └──────────────┘ └──────────────┘ └──────────────┘
                        │
                        ▼
                 ┌──────────────┐
                 │ Dashboard    │
                 │ DELIVERY     │
                 │ (Sucursal 1) │
                 │ ✅ Recibe    │
                 │ notificación │
                 │              │
                 │ 🔔 ALERTA:   │
                 │ "Pedido #42  │
                 │ listo para   │
                 │ entregar"    │
                 └──────────────┘
```

---

## 🎯 Tabla de Responsabilidades

| Dashboard | Suscrito a | Puede Cambiar Estado | Estados Relevantes |
|-----------|------------|----------------------|-------------------|
| **Admin** | `/topic/pedidos/admin` | ✅ Todos | Todos (1-6) |
| **Cocina** | `/topic/pedidos/sucursal/{id}` | ✅ Solo de su sucursal | PENDIENTE (1), EN_PREPARACION (2) |
| **Delivery** | `/topic/pedidos/sucursal/{id}` | ✅ Solo de su sucursal | LISTO (3), EN_CAMINO (4) |
| **Cliente** | `/topic/pedidos/usuario/{id}` | ❌ No puede cambiar | Todos (solo lectura) |

---

## 🔐 Seguridad (Próxima Implementación)

### Filtrado por Rol en Backend

```java
@MessageMapping("/pedido.cambiarEstado")
@Transactional
public void cambiarEstadoPedido(
    CambioEstadoPedidoRequest request,
    Principal principal  // Usuario autenticado
) {
    // Obtener usuario del token JWT
    String username = principal.getName();
    Usuario usuario = usuarioRepository.findByUsername(username);
    
    // Validar permisos según rol
    if (usuario.getRol().getTipoRol() == TipoRol.COCINERO) {
        // Verificar que el pedido sea de su sucursal
        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
            .orElseThrow();
        
        if (!pedido.getSucursal().getId().equals(usuario.getSucursal().getId())) {
            throw new UnauthorizedException("No tienes permiso para modificar este pedido");
        }
        
        // Verificar que solo pueda cambiar a estados permitidos
        if (request.getNuevoEstadoId() > 3) {
            throw new UnauthorizedException("Cocinero solo puede marcar hasta LISTO");
        }
    }
    
    // ... resto de la lógica
}
```

---

## 📈 Escalabilidad

### Múltiples Sucursales

```
Sucursal 1 (Buenos Aires)
├─ Cocinero A → Suscrito a /topic/pedidos/sucursal/1
├─ Cocinero B → Suscrito a /topic/pedidos/sucursal/1
├─ Delivery A → Suscrito a /topic/pedidos/sucursal/1
└─ Delivery B → Suscrito a /topic/pedidos/sucursal/1

Sucursal 2 (Córdoba)
├─ Cocinero C → Suscrito a /topic/pedidos/sucursal/2
├─ Cocinero D → Suscrito a /topic/pedidos/sucursal/2
└─ Delivery C → Suscrito a /topic/pedidos/sucursal/2

Admin Central
└─ Admin → Suscrito a /topic/pedidos/admin (ve TODAS las sucursales)
```

---

## 🧪 Ejemplo de Prueba

### 1. Abrir 4 ventanas del navegador:

```
Ventana 1: Dashboard Admin
Ventana 2: Dashboard Cocina (Sucursal 1)
Ventana 3: Dashboard Delivery (Sucursal 1)
Ventana 4: Vista Cliente (Usuario 123)
```

### 2. Crear un pedido nuevo (Usuario 123, Sucursal 1)

**Resultado esperado:**
- ✅ Ventana 1 (Admin): Ve el nuevo pedido
- ✅ Ventana 2 (Cocina): Ve el nuevo pedido
- ✅ Ventana 3 (Delivery): NO ve el pedido (aún no está listo)
- ✅ Ventana 4 (Cliente): Ve su pedido como PENDIENTE

### 3. Cocinero marca como "EN_PREPARACION"

**Resultado esperado:**
- ✅ Todas las ventanas se actualizan en tiempo real
- ✅ Cliente recibe notificación: "Tu pedido está en preparación"

### 4. Cocinero marca como "LISTO"

**Resultado esperado:**
- ✅ Ventana 3 (Delivery): 🔔 ALERTA sonora + pedido aparece en "Listos para entregar"
- ✅ Cliente recibe notificación: "Tu pedido está listo"

### 5. Delivery marca como "EN_CAMINO"

**Resultado esperado:**
- ✅ Cliente recibe notificación: "Tu pedido está en camino"

### 6. Delivery marca como "ENTREGADO"

**Resultado esperado:**
- ✅ Cliente recibe notificación: "Tu pedido ha sido entregado"
- ✅ Pedido desaparece de dashboards de Cocina y Delivery
- ✅ Admin sigue viendo el pedido (historial completo)

---

## 🎨 Mejoras Visuales Recomendadas

### Indicadores de Estado en Tiempo Real

```javascript
// En cada dashboard, mostrar contador de pedidos activos
const [pedidosActivos, setPedidosActivos] = useState(0);

useEffect(() => {
    // Actualizar contador cuando llega notificación
    webSocketService.subscribe(topic, (notif) => {
        setPedidosActivos(prev => prev + 1);
        
        // Animación de badge
        document.getElementById('badge').classList.add('pulse');
    });
}, []);
```

### Notificaciones Sonoras Diferenciadas

```javascript
const sonidos = {
    nuevoPedido: '/sounds/new-order.mp3',
    pedidoListo: '/sounds/order-ready.mp3',
    pedidoEntregado: '/sounds/delivered.mp3'
};

const reproducirSonidoSegunEstado = (estadoId) => {
    const audio = new Audio(sonidos[estadoId] || sonidos.nuevoPedido);
    audio.play();
};
```

---

## ✅ Checklist de Implementación

- [x] Backend envía a múltiples topics
- [x] Servicio WebSocket base
- [x] Dashboard Admin (todos los pedidos)
- [x] Dashboard Cocina (filtrado por sucursal)
- [x] Dashboard Delivery (filtrado por sucursal)
- [x] Vista Cliente (filtrado por usuario)
- [ ] Autenticación en WebSocket
- [ ] Validación de permisos por rol
- [ ] Reconexión automática
- [ ] Notificaciones push del navegador
- [ ] Persistencia de notificaciones no leídas

---

**¡La arquitectura está lista para soportar múltiples dashboards simultáneos!** 🚀
