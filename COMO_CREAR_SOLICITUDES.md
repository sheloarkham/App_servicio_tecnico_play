# 🧪 SCRIPT: Crear Solicitud de Prueba

## Paso 1: Abre Swagger
```
http://localhost:8080/swagger-ui/index.html
```

## Paso 2: Busca POST /api/solicitudes

## Paso 3: Copia este JSON

```json
{
  "servicio": "Reparación PlayStation 5 - Prueba Backend",
  "cliente": "Usuario de Prueba",
  "descripcion": "Esta es una solicitud de prueba creada para verificar que el backend funciona correctamente",
  "fechaSolicitud": "2024-12-06",
  "horaSolicitud": "14:30",
  "estadoSolicitud": "PENDIENTE",
  "idCategoria": 1
}
```

## Paso 4: Click "Try it out"

## Paso 5: Pega el JSON y click "Execute"

## Paso 6: Verifica el resultado

### ✅ Si ves Code: 201 Created
¡Perfecto! La solicitud se creó.

### ❌ Si ves Code: 400 o 500
Hay un error en el backend o en los datos.

---

## Paso 7: Verificar en la App

1. Abre la app en el emulador
2. Ve a "Gestión Backend"
3. ¡Deberías ver tu solicitud!

---

## 🎉 Cuando Funcione

Verás en la app:

```
┌─────────────────────────────────┐
│  Gestión de Solicitudes         │
│  (Backend)                      │
├─────────────────────────────────┤
│ ┌─────────────────────────────┐ │
│ │ Servicio: Reparación PS5    │ │
│ │ Cliente: Usuario de Prueba  │ │
│ │ Estado: PENDIENTE           │ │
│ │ Fecha: 2024-12-06           │ │
│ │ [✏️] [🗑️]                    │ │
│ └─────────────────────────────┘ │
│                                 │
│                 ┌───┐           │
│                 │ ➕ │           │
│                 └───┘           │
└─────────────────────────────────┘
```

---

## 💡 Ahora prueba tú

Crea 2 o 3 solicitudes más con datos diferentes:
- Diferentes clientes
- Diferentes servicios
- Diferentes estados

Así verás una lista real en tu app. 🎊

