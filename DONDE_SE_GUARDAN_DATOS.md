# 💾 ¿DÓNDE SE GUARDAN LOS DATOS AHORA?

## 🎯 RESPUESTA CORTA

**Tu app usa AMBOS sistemas:**

1. **💾 Room (Base de datos local)** - Para cotizaciones/formularios
2. **🖥️ Backend (Base de datos remota)** - Para solicitudes

**NO se guarda en RAM.** Se guarda en **archivos de base de datos** (SQLite local y base de datos del backend).

---

## 📊 TU SISTEMA ACTUAL

### 🔄 DOS FLUJOS DIFERENTES

```
┌─────────────────────────────────────────────────────────┐
│                      TU APP                             │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  💰 COTIZACIONES (Formulario Servicio)                  │
│      ↓                                                  │
│  💾 Room (SQLite local en el teléfono)                  │
│      ↓                                                  │
│  📁 /data/data/tu.app/databases/                        │
│      app_servicio_tecnico_db                            │
│                                                         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  📋 SOLICITUDES (Gestión Backend)                       │
│      ↓                                                  │
│  🌐 Retrofit (HTTP)                                     │
│      ↓                                                  │
│  🖥️ Backend Spring Boot (tu PC)                        │
│      ↓                                                  │
│  💾 Base de datos del servidor                          │
│      (H2 o PostgreSQL)                                  │
│      ↓                                                  │
│  📁 Archivo en tu PC                                    │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## 💾 ALMACENAMIENTO DETALLADO

### 1️⃣ Room (SQLite) - Para Cotizaciones

**Ubicación física:**
```
📱 Teléfono/Emulador
  └── 📁 /data/data/appserviciotecnico/
      └── 📁 databases/
          └── 📄 app_servicio_tecnico_db
```

**Qué se guarda aquí:**
- ✅ Cotizaciones (FormularioServicioEntity)
- ✅ Solicitudes locales (SolicitudEntity)

**Características:**
- 💾 Se guarda en el **almacenamiento interno del teléfono**
- ✅ Persiste aunque cierres la app
- ❌ Se pierde si desinstalas la app
- ❌ Solo accesible desde TU teléfono
- ✅ **NO** es RAM (es disco)

**Tecnología:** SQLite (base de datos embebida)

---

### 2️⃣ Backend Database - Para Solicitudes

**Ubicación física:**
```
🖥️ Tu PC (donde corre el backend)
  └── 📁 backend_proyecto/
      └── 📁 data/
          └── 📄 servicio_tecnico.mv.db  (si es H2)
```

**O si es PostgreSQL:**
```
🖥️ Tu PC
  └── 🗄️ PostgreSQL Server
      └── 📊 servicio_tecnico_db
          ├── 📋 solicitudes (tabla)
          └── 💰 cotizaciones (tabla)
```

**Qué se guarda aquí:**
- ✅ Solicitudes (de "Gestión Backend")
- ✅ Cotizaciones (si el backend las gestiona también)

**Características:**
- 💾 Se guarda en el **disco duro de tu PC**
- ✅ Persiste aunque apagues el backend
- ✅ Accesible desde múltiples dispositivos
- ✅ Mejor para producción
- ✅ **NO** es RAM (es disco)

**Tecnología:** H2 o PostgreSQL (bases de datos profesionales)

---

## 🔍 VERIFICAR DÓNDE ESTÁN TUS DATOS

### Prueba 1: Room (Datos Locales)

1. **Ejecuta la app en el emulador**
2. **Crea una cotización** en "Solicitar Cotización"
3. **Cierra COMPLETAMENTE la app** (swipe up)
4. **Vuelve a abrir la app**
5. **Ve a "Mis Solicitudes"**

**Resultado:**
- ✅ Si ves la cotización → Está en Room (disco local)
- ❌ Si NO la ves → Hay un problema

---

### Prueba 2: Backend (Datos Remotos)

1. **Ejecuta la app**
2. **Crea una solicitud** en "Gestión Backend"
3. **CIERRA el backend** (Ctrl+C en la terminal)
4. **VUELVE A INICIAR el backend**
5. **Abre Swagger:** `GET /api/solicitudes`

**Resultado:**
- ✅ Si ves la solicitud → Está en base de datos del servidor
- ❌ Si NO la ves → Se guardaba en RAM (problema)

---

## 🆚 RAM vs DISCO - La Diferencia

### ⚡ RAM (Memoria Temporal)
```
Velocidad: 🚀 Súper rápida
Persistencia: ❌ Se pierde al apagar
Capacidad: 📦 Pequeña (GB)
Uso: Datos temporales mientras la app corre
```

**Ejemplo:** Variables en el código que solo existen mientras el programa corre.

### 💾 DISCO (Almacenamiento Permanente)
```
Velocidad: 🐢 Más lenta que RAM
Persistencia: ✅ Se mantiene al apagar
Capacidad: 📦 Grande (GB/TB)
Uso: Bases de datos, archivos permanentes
```

**Ejemplo:** Archivos SQLite, bases de datos H2/PostgreSQL.

---

## 📋 TU CASO ESPECÍFICO

### Room (Local)
```kotlin
// AppDatabase.kt
@Database(entities = [...], version = 2)
abstract class AppDatabase : RoomDatabase() {
    // Crea archivo: app_servicio_tecnico_db
}
```

**Archivo creado:**
```
📄 /data/data/appserviciotecnico/databases/app_servicio_tecnico_db
```

✅ **En DISCO, NO en RAM**

---

### Backend (Remoto)
```properties
# application.properties (en el backend)
spring.datasource.url=jdbc:h2:file:./data/servicio_tecnico
```

**Archivo creado:**
```
📄 backend_proyecto/data/servicio_tecnico.mv.db
```

✅ **En DISCO del servidor, NO en RAM**

---

## 🎯 RESUMEN

| Aspecto | Room (Local) | Backend (Remoto) |
|---------|--------------|------------------|
| **Ubicación** | Teléfono/emulador | PC (servidor) |
| **Tecnología** | SQLite | H2/PostgreSQL |
| **Archivo** | app_servicio_tecnico_db | servicio_tecnico.mv.db |
| **Persiste al cerrar app** | ✅ Sí | ✅ Sí |
| **Persiste al desinstalar** | ❌ No | ✅ Sí |
| **Accesible remotamente** | ❌ No | ✅ Sí |
| **Se guarda en RAM** | ❌ NO - DISCO | ❌ NO - DISCO |
| **Se guarda en DISCO** | ✅ SÍ | ✅ SÍ |

---

## 💡 ENTONCES, ¿USAMOS ROOM O NO?

### Respuesta: AMBOS

**Room se usa para:**
- 💰 Cotizaciones locales (FormularioServicioScreen)
- 📋 Solicitudes locales (si las hay)
- 🔄 Caché local (opcional)

**Backend se usa para:**
- 📋 Solicitudes remotas (SolicitudBackendScreen)
- 💰 Cotizaciones remotas (opcional)
- 🌐 Sincronización entre dispositivos

---

## 🔧 ARQUITECTURA COMPLETA

```
┌─────────────────────────────────────────┐
│           📱 TU APP ANDROID             │
├─────────────────────────────────────────┤
│                                         │
│  🖼️ UI Layer (Screens)                  │
│     ↓                                   │
│  🧠 ViewModel Layer                      │
│     ↓                                   │
│  📦 Repository Layer                     │
│     ├─→ 💾 Room (Local)                 │
│     │   └─→ SQLite (Disco)              │
│     │                                   │
│     └─→ 🌐 Retrofit (Remote)            │
│         └─→ Backend API                 │
│             └─→ 💾 Base de datos        │
│                 └─→ Archivo (Disco)     │
│                                         │
└─────────────────────────────────────────┘
```

---

## 🧪 PRUEBA FINAL

### Confirma que los datos están en DISCO:

**Paso 1: Crear datos**
1. Crea una cotización local (Room)
2. Crea una solicitud remota (Backend)

**Paso 2: Cerrar TODO**
1. Cierra la app COMPLETAMENTE
2. Cierra el backend (Ctrl+C)
3. **REINICIA TU PC** 🔄

**Paso 3: Volver a abrir**
1. Inicia el backend de nuevo
2. Abre la app de nuevo
3. Verifica los datos

**Resultado esperado:**
- ✅ Cotización local sigue ahí (Room → Disco)
- ✅ Solicitud remota sigue ahí (Backend → Disco)

**Si ambos están:** 🎉 **¡TODO está en DISCO, NO en RAM!**

---

## 🎉 CONCLUSIÓN

### ❌ NO se guarda en RAM

### ✅ Se guarda en DISCO en DOS lugares:

1. **Room:** Archivo SQLite en el teléfono
2. **Backend:** Archivo de base de datos en tu PC

**Ambos persisten aunque cierres/reinicies todo.** 💾

---

¿Queda claro? Los datos están seguros en el disco, no se pierden. 😊

