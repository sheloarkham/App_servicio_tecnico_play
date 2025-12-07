# 🎯 GUÍA DEFINITIVA - Qué Verás EXACTAMENTE en Tu App

## 📱 LO QUE VERÁS PASO A PASO

---

## PASO 1: Ejecutar la App en Android Studio

### En la barra superior verás:

```
┌──────────────────────────────────────────────────┐
│ [app ▼] [Pixel 4 API 30 ▼] [▶️] [⏹] [🐛]        │
│                              ↑                    │
│                         TOCA AQUÍ                 │
└──────────────────────────────────────────────────┘
```

**Asegúrate que diga:**
- ✅ "Pixel 4 API 30" (o similar) = Emulador
- ❌ "Samsung Galaxy" o nombre de teléfono = Dispositivo físico (NO usar)

---

## PASO 2: Esperar que Compile

En la parte inferior verás:

```
Build Output
─────────────────────────────────────
> Task :app:compileDebugKotlin    
> Task :app:mergeDebugResources    RUNNING...
> Task :app:processDebugManifest   
...

⏳ Espera hasta que diga:
BUILD SUCCESSFUL in 45s
```

---

## PASO 3: Emulador Abre Automáticamente

Se abrirá una ventana nueva con el emulador (parece un teléfono):

```
┌─────────────────────┐
│    [Android Logo]   │
│                     │
│  📱 Pixel 4 API 30  │
│                     │
│   [Cargando...]     │
│                     │
└─────────────────────┘

Después de unos segundos:

┌─────────────────────┐
│  ☰  Mi App          │ ← Tu app se abre
│─────────────────────│
│                     │
│  [Pantalla inicial] │
│                     │
│                     │
└─────────────────────┘
```

---

## PASO 4: Abrir el Menú ☰

En la esquina superior izquierda verás el ícono de **menú hamburguesa ☰**:

```
┌─────────────────────┐
│  ☰  Mi App          │ ← TOCA AQUÍ (las 3 líneas)
│─────────────────────│
│                     │
│  Contenido...       │
│                     │
└─────────────────────┘
```

---

## PASO 5: Menú Lateral Se Abre

El menú se deslizará desde la izquierda:

```
┌────────────┬────────┐
│            │        │
│ Mi App     │  Main  │
│            │  Screen│
│────────────│        │
│            │        │
│ 🏠 Inicio  │        │
│            │        │
│ 📋 Otros   │        │
│            │        │
│ ☁️ Gestión │  ← BUSCA│
│   Backend  │   ESTO │
│            │        │
│ ⚙️ Config  │        │
│            │        │
└────────────┴────────┘
```

**TOCA: "Gestión Backend"** (tiene un ícono de nube ☁️)

---

## PASO 6A: ✅ SI TODO FUNCIONA (Conexión OK)

Verás esto:

### Opción 1: Lista Vacía (No hay solicitudes creadas)

```
┌─────────────────────────────────┐
│  Gestión de Solicitudes         │
│  (Backend)                      │
├─────────────────────────────────┤
│                                 │
│                                 │
│                                 │
│  No hay solicitudes.            │ ← ESTE MENSAJE
│  Crea una nueva.                │
│                                 │
│                                 │
│                                 │
│                                 │
│                 ┌───┐           │
│                 │ ➕ │ ← Botón  │
│                 └───┘   crear   │
└─────────────────────────────────┘
```

**✅ ESTO ES BUENO** = La app se conectó al backend correctamente

### Opción 2: Lista con Solicitudes (Si ya hay datos)

```
┌─────────────────────────────────┐
│  Gestión de Solicitudes         │
│  (Backend)                      │
├─────────────────────────────────┤
│ ┌─────────────────────────────┐ │
│ │ Servicio: Reparación PS5    │ │
│ │ Cliente: Juan Pérez         │ │
│ │ Estado: PENDIENTE           │ │
│ │ Fecha: 2024-12-06           │ │
│ │ [✏️] [🗑️]                    │ │
│ └─────────────────────────────┘ │
│                                 │
│ ┌─────────────────────────────┐ │
│ │ Servicio: Limpieza PS4      │ │
│ │ Cliente: María López        │ │
│ │ Estado: EN_PROCESO          │ │
│ │ Fecha: 2024-12-05           │ │
│ │ [✏️] [🗑️]                    │ │
│ └─────────────────────────────┘ │
│                 ┌───┐           │
│                 │ ➕ │           │
│                 └───┘           │
└─────────────────────────────────┘
```

**✅ ESTO TAMBIÉN ES BUENO** = Hay solicitudes guardadas

---

## PASO 6B: ❌ SI NO FUNCIONA (Sin Conexión)

Verás esto:

### Opción 1: Error de Red

```
┌─────────────────────────────────┐
│  Gestión de Solicitudes         │
│  (Backend)                      │
├─────────────────────────────────┤
│                                 │
│                                 │
│         ⚠️                       │
│                                 │
│  Error: Unable to resolve       │ ← MENSAJE
│  host "10.0.2.2": No address    │   DE ERROR
│  associated with hostname       │
│                                 │
│                                 │
│        [Reintentar]             │
│                                 │
│                                 │
└─────────────────────────────────┘
```

**❌ ESTO ES MALO** = La app NO puede conectarse al backend

### Opción 2: Cargando Eternamente

```
┌─────────────────────────────────┐
│  Gestión de Solicitudes         │
│  (Backend)                      │
├─────────────────────────────────┤
│                                 │
│                                 │
│                                 │
│            ⏳                    │
│         Cargando...             │ ← SE QUEDA
│            ○○○                  │   AQUÍ
│                                 │   PARA
│                                 │   SIEMPRE
│                                 │
│                                 │
└─────────────────────────────────┘
```

**❌ ESTO ES MALO** = El backend no responde

---

## 🎉 PRUEBA EXTRA: Crear una Solicitud

Si todo funciona (viste "No hay solicitudes"), prueba crear una:

### 1. Tocar el botón ➕

```
┌─────────────────────────────────┐
│  No hay solicitudes.            │
│  Crea una nueva.                │
│                                 │
│                 ┌───┐           │
│                 │ ➕ │ ← TOCA   │
│                 └───┘   AQUÍ    │
└─────────────────────────────────┘
```

### 2. Formulario Aparece

```
┌─────────────────────────────────┐
│  ← Nueva Solicitud              │
├─────────────────────────────────┤
│                                 │
│  Servicio:                      │
│  [_____________________]        │
│                                 │
│  Cliente:                       │
│  [_____________________]        │
│                                 │
│  Descripción:                   │
│  [_____________________]        │
│  [_____________________]        │
│                                 │
│  Fecha Solicitud:               │
│  [__/__/____] 📅                │
│                                 │
│  Hora:                          │
│  [__:__] 🕐                     │
│                                 │
│  Estado:                        │
│  [PENDIENTE ▼]                  │
│                                 │
│  ID Categoría:                  │
│  [1_________]                   │
│                                 │
│      [GUARDAR] [CANCELAR]       │
└─────────────────────────────────┘
```

### 3. Llenar el Formulario

Ejemplo de datos:
- **Servicio:** Limpieza PS5
- **Cliente:** Juan Test
- **Descripción:** Prueba de conexión
- **Fecha:** 2024-12-06
- **Hora:** 14:30
- **Estado:** PENDIENTE
- **ID Categoría:** 1

### 4A. ✅ Se Guarda Correctamente

Verás esto:

```
┌─────────────────────────────────┐
│  Gestión de Solicitudes         │
│  (Backend)                      │
├─────────────────────────────────┤
│ ┌─────────────────────────────┐ │
│ │ Servicio: Limpieza PS5      │ │ ← Tu solicitud
│ │ Cliente: Juan Test          │ │   APARECE
│ │ Estado: PENDIENTE           │ │   aquí
│ │ Fecha: 2024-12-06           │ │
│ │ [✏️] [🗑️]                    │ │
│ └─────────────────────────────┘ │
│                                 │
│                 ┌───┐           │
│                 │ ➕ │           │
│                 └───┘           │
└─────────────────────────────────┘
```

**✅ PERFECTO** = Backend y App están conectados

### 4B. ❌ Error al Guardar

Verás esto:

```
┌─────────────────────────────────┐
│  ← Nueva Solicitud              │
├─────────────────────────────────┤
│                                 │
│  ⚠️ Error al crear solicitud     │
│                                 │
│  No se pudo conectar con el     │
│  servidor. Verifica que el      │
│  backend esté corriendo.        │
│                                 │
│  [Campos del formulario...]     │
│                                 │
│      [REINTENTAR] [CANCELAR]    │
└─────────────────────────────────┘
```

**❌ HAY PROBLEMA** = No hay conexión

---

## 🔍 VERIFICAR EN SWAGGER

Si la solicitud se guardó en la app, verifica en el navegador:

### 1. Abre Swagger

```
http://localhost:8080/swagger-ui/index.html
```

### 2. Busca el Endpoint GET

```
┌─────────────────────────────────┐
│ solicitud-controller            │
├─────────────────────────────────┤
│ ▼ GET /api/solicitudes          │ ← Expande esto
│   Obtener todas las solicitudes │
│                                 │
│ ▼ POST /api/solicitudes         │
│ ▼ PUT /api/solicitudes/{id}     │
│ ▼ DELETE /api/solicitudes/{id}  │
└─────────────────────────────────┘
```

### 3. Ejecutar

```
┌─────────────────────────────────┐
│ GET /api/solicitudes            │
├─────────────────────────────────┤
│                                 │
│  [Try it out]  ← Click aquí     │
│                                 │
│  [Execute] ← Luego aquí         │
│                                 │
└─────────────────────────────────┘
```

### 4A. ✅ Respuesta Exitosa

```
Responses

Code: 200
Description: OK

Response body:
[
  {
    "id": 1,
    "servicio": "Limpieza PS5",
    "cliente": "Juan Test",
    "descripcion": "Prueba de conexión",
    "fechaSolicitud": "2024-12-06",
    "horaSolicitud": "14:30",
    "estadoSolicitud": "PENDIENTE",
    "idCategoria": 1
  }
]
```

**✅ PERFECTO** = Tu solicitud está en el backend

### 4B. Lista Vacía (Si no has creado nada)

```
Response body:
[]
```

**✅ ESTO ES NORMAL** = No hay solicitudes todavía

---

## 📊 TABLA COMPARATIVA

| Situación | En la App | En Swagger | Significa |
|-----------|-----------|------------|-----------|
| ✅ TODO BIEN | "No hay solicitudes. Crea una nueva." | `[]` | Conectado, lista vacía |
| ✅ TODO BIEN | Lista con solicitudes | Lista con datos JSON | Conectado, con datos |
| ❌ PROBLEMA | "Error: Unable to resolve host" | No importa | App no puede conectarse |
| ❌ PROBLEMA | "Cargando..." infinito | Backend funciona | Timeout o URL incorrecta |
| ❌ PROBLEMA | App se cierra | Backend funciona | Crash en la app |

---

## 🎯 RESUMEN ULTRA SIMPLE

### ✅ FUNCIONA = Verás esto en la app:
```
"No hay solicitudes. Crea una nueva."
```
O
```
[Lista de solicitudes con botones de editar/eliminar]
```

### ❌ NO FUNCIONA = Verás esto en la app:
```
"Error: ..."
```
O
```
"Cargando..." (que nunca termina)
```

---

## 💡 IMPORTANTE

**No necesitas entender TODO el código.**

**Solo necesitas:**

1. ✅ Ejecutar la app (▶️)
2. ✅ Abrir menú (☰)
3. ✅ Tocar "Gestión Backend"
4. ✅ Ver si dice "No hay solicitudes" o muestra error

**Eso es TODO.** 😊

---

## 🚀 SIGUIENTE PASO

1. Ve a Android Studio
2. Click en el botón ▶️ (Run 'app')
3. Espera que el emulador abra
4. Abre el menú ☰
5. Toca "Gestión Backend"
6. **AVÍSAME QUÉ VES** (¿"No hay solicitudes" o "Error"?)

**¡Eso es todo lo que necesitas hacer!** 🎉

