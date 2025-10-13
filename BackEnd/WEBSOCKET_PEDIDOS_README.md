# 🔌 WebSocket para Actualización de Estado de Pedidos

## 📋 Descripción General

Sistema de WebSocket implementado para notificar cambios de estado de pedidos en tiempo real. Cuando el frontend envía un cambio de estado, el backend actualiza la base de datos y notifica a todos los clientes conectados.

---

## 🎯 Flujo de Funcionamiento

```
Frontend → WebSocket → Backend → BD → WebSocket → Frontend
   1. Envía cambio      2. Actualiza    3. Notifica
```

1. **Frontend envía cambio de estado** via WebSocket
2. **Backend recibe, valida y actualiza** la base de datos
3. **Backend notifica** a todos los dashboards y al usuario específico

---

## 🔧 Configuración del Cliente (Frontend)

### 1. Conectarse al WebSocket

```javascript
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

// Crear conexión
const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);

// Conectar
stompClient.connect({}, (frame) => {
    console.log('Conectado: ' + frame);
    
    // Suscribirse a notificaciones generales (para dashboards)
    stompClient.subscribe('/topic/pedidos', (message) => {
        const notificacion = JSON.parse(message.body);
        console.log('Notificación recibida:', notificacion);
        // Actualizar UI con la notificación
    });
    
    // Suscribirse a notificaciones específicas del usuario
    const usuarioId = 123; // ID del usuario logueado
    stompClient.subscribe(`/topic/pedidos/usuario/${usuarioId}`, (message) => {
        const notificacion = JSON.parse(message.body);
        console.log('Notificación personal:', notificacion);
        // Mostrar notificación al usuario
    });
});
```

### 2. Enviar Cambio de Estado

```javascript
// Cuando se cambia el estado de un pedido desde el dashboard
function cambiarEstadoPedido(pedidoId, nuevoEstadoId, tiempoEstimado) {
    const request = {
        pedidoId: pedidoId,
        nuevoEstadoId: nuevoEstadoId,
        tiempoEstimado: tiempoEstimado // Opcional
    };
    
    stompClient.send(
        '/app/pedido.cambiarEstado',
        {},
        JSON.stringify(request)
    );
}

// Ejemplo de uso
cambiarEstadoPedido(42, 3, "30 minutos");
```

---

## 📦 Estructura de Mensajes

### Request (Frontend → Backend)

**Endpoint:** `/app/pedido.cambiarEstado`

```json
{
  "pedidoId": 42,
  "nuevoEstadoId": 3,
  "tiempoEstimado": "30 minutos"
}
```

### Response (Backend → Frontend)

**Topics:**
- `/topic/pedidos` - Notificación general (todos los dashboards)
- `/topic/pedidos/usuario/{id}` - Notificación específica del usuario

```json
{
  "pedidoId": 42,
  "estadoId": 3,
  "estadoNombre": "EN_PREPARACION",
  "tiempoEstimado": "30 minutos",
  "fecha": "2025-10-13T15:30:00",
  "usuarioId": 123,
  "usuarioNombre": "Juan Pérez",
  "sucursalId": 1,
  "mensaje": null,
  "timestamp": "2025-10-13T15:35:00"
}
```

---

## 🎨 Ejemplo de Implementación en React

```javascript
import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

function PedidosDashboard() {
    const [pedidos, setPedidos] = useState([]);
    const [stompClient, setStompClient] = useState(null);

    useEffect(() => {
        // Conectar al WebSocket
        const socket = new SockJS('http://localhost:8080/ws');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            console.log('WebSocket conectado');

            // Suscribirse a notificaciones
            client.subscribe('/topic/pedidos', (message) => {
                const notificacion = JSON.parse(message.body);
                
                // Actualizar el pedido en el estado
                setPedidos(prevPedidos => 
                    prevPedidos.map(pedido => 
                        pedido.id === notificacion.pedidoId
                            ? { ...pedido, estado: notificacion.estadoNombre }
                            : pedido
                    )
                );

                // Mostrar notificación
                showNotification(`Pedido #${notificacion.pedidoId} actualizado a ${notificacion.estadoNombre}`);
            });
        });

        setStompClient(client);

        // Cleanup al desmontar
        return () => {
            if (client) {
                client.disconnect();
            }
        };
    }, []);

    const cambiarEstado = (pedidoId, nuevoEstadoId) => {
        if (stompClient && stompClient.connected) {
            const request = {
                pedidoId,
                nuevoEstadoId
            };
            stompClient.send('/app/pedido.cambiarEstado', {}, JSON.stringify(request));
        }
    };

    return (
        <div>
            {pedidos.map(pedido => (
                <div key={pedido.id}>
                    <h3>Pedido #{pedido.id}</h3>
                    <p>Estado: {pedido.estado}</p>
                    <button onClick={() => cambiarEstado(pedido.id, 2)}>
                        Marcar como En Preparación
                    </button>
                </div>
            ))}
        </div>
    );
}
```

---

## 🔐 Estados de Pedido Disponibles

Los estados están precargados en la base de datos:

| ID | Nombre Estado | Descripción |
|----|---------------|-------------|
| 1  | PENDIENTE     | Pedido recibido, esperando confirmación |
| 2  | EN_PREPARACION| Pedido en cocina |
| 3  | LISTO         | Pedido listo para entrega |
| 4  | EN_CAMINO     | Pedido en delivery |
| 5  | ENTREGADO     | Pedido completado |
| 6  | CANCELADO     | Pedido cancelado |

---

## 🎯 Casos de Uso

### 1. Dashboard de Cocina
```javascript
// Suscribirse solo a pedidos de la sucursal
stompClient.subscribe('/topic/pedidos', (message) => {
    const notif = JSON.parse(message.body);
    if (notif.sucursalId === miSucursalId) {
        actualizarPedidoEnPantalla(notif);
    }
});
```

### 2. Notificación al Cliente
```javascript
// Cliente suscrito a sus propios pedidos
const usuarioId = obtenerUsuarioLogueado();
stompClient.subscribe(`/topic/pedidos/usuario/${usuarioId}`, (message) => {
    const notif = JSON.parse(message.body);
    mostrarNotificacionPush(`Tu pedido #${notif.pedidoId} está ${notif.estadoNombre}`);
});
```

### 3. Dashboard Administrativo
```javascript
// Ver todos los cambios en tiempo real
stompClient.subscribe('/topic/pedidos', (message) => {
    const notif = JSON.parse(message.body);
    agregarAHistorial(notif);
    actualizarEstadisticas();
});
```

---

## 🐛 Debugging

### Verificar Conexión
```javascript
stompClient.connect({}, 
    (frame) => {
        console.log('✅ Conectado:', frame);
    },
    (error) => {
        console.error('❌ Error de conexión:', error);
    }
);
```

### Logs del Backend
El backend registra cada operación:
```
INFO - Recibido cambio de estado - Pedido ID: 42, Nuevo Estado ID: 3
INFO - Estado actualizado - Pedido: 42, Estado: EN_PREPARACION
```

---

## 📚 Dependencias Necesarias

### Frontend (npm/yarn)
```bash
npm install sockjs-client @stomp/stompjs
```

### Backend (Gradle)
Ya incluidas en el proyecto:
- `spring-boot-starter-websocket`
- `spring-messaging`

---

## ✅ Checklist de Implementación

- [x] Configuración de WebSocket en backend
- [x] Controller para recibir cambios de estado
- [x] Actualización de BD al recibir cambio
- [x] Notificación a `/topic/pedidos` (general)
- [x] Notificación a `/topic/pedidos/usuario/{id}` (específica)
- [x] DTOs para request y response
- [x] Logging de operaciones
- [x] Manejo de errores

---

## 🚀 Próximos Pasos

1. Implementar autenticación en WebSocket
2. Agregar notificaciones por rol (ADMIN, COCINERO, DELIVERY)
3. Implementar heartbeat para detectar desconexiones
4. Agregar persistencia de notificaciones no leídas
5. Implementar rate limiting para prevenir spam

---

**¡El sistema está listo para usar!** 🎉
