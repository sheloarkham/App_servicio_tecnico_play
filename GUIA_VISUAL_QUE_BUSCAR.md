# 🎨 GUÍA VISUAL - Qué Buscar en la App

## 📱 PASO 1: Abrir el Menú

Cuando ejecutes tu app en el emulador, verás algo así:

```
┌──────────────────────────────┐
│  ☰  Mi App                   │  ← Toca aquí (las 3 líneas ☰)
├──────────────────────────────┤
│                              │
│    Bienvenido a la App       │
│                              │
│    [Contenido de la app]     │
│                              │
│                              │
│                              │
│                              │
│                              │
│                              │
└──────────────────────────────┘
```

## 📱 PASO 2: Menú Lateral Abierto

Al tocar ☰, el menú se deslizará desde la izquierda:

```
┌─────────────┬────────────────┐
│             │                │
│  Mi App     │  [Pantalla]    │
│             │                │
│─────────────│                │
│             │                │
│ 🏠 Inicio   │                │
│             │                │
│ 📋 Servicios│                │
│             │                │
│ ☁️ Gestión  │  ← BUSCA ESTA  │
│   Backend   │    OPCIÓN      │
│             │                │
│ ⚙️ Config   │                │
│             │                │
└─────────────┴────────────────┘
```

## 📱 PASO 3A: TODO FUNCIONA ✅

Si todo está bien, verás esta pantalla:

```
┌──────────────────────────────┐
│  ☰  Gestión Backend          │
├──────────────────────────────┤
│                              │
│  📋 Lista de Solicitudes     │
│                              │
│  ┌────────────────────────┐  │
│  │                        │  │
│  │  No hay solicitudes.   │  │
│  │  Crea una nueva.       │  │
│  │                        │  │
│  └────────────────────────┘  │
│                              │
│                              │
│                              │
│                              │
│              ┌───┐           │
│              │ ➕ │  ← Botón │
│              └───┘   crear   │
└──────────────────────────────┘
```

**✅ ESTO SIGNIFICA:** La app se conectó al backend exitosamente.

---

## 📱 PASO 3B: NO FUNCIONA ❌

Si hay problemas de conexión, verás algo así:

```
┌──────────────────────────────┐
│  ☰  Gestión Backend          │
├──────────────────────────────┤
│                              │
│                              │
│     ⚠️ Error de red           │
│                              │
│  No se pudo conectar con     │
│  el servidor.                │
│                              │
│  Por favor, verifica que     │
│  el backend esté corriendo.  │
│                              │
│                              │
│         [Reintentar]         │
│                              │
└──────────────────────────────┘
```

**❌ ESTO SIGNIFICA:** La app NO puede conectarse al backend.

---

## 📱 PASO 3C: CARGANDO ETERNAMENTE ⏳

Si se queda así por más de 10 segundos:

```
┌──────────────────────────────┐
│  ☰  Gestión Backend          │
├──────────────────────────────┤
│                              │
│                              │
│                              │
│         ⏳                    │
│     Cargando...              │
│                              │
│     [Círculo girando]        │
│                              │
│                              │
│                              │
│                              │
│                              │
└──────────────────────────────┘
```

**⚠️ ESTO SIGNIFICA:** La app está intentando conectarse pero no recibe respuesta.

---

## 🎯 RESUMEN VISUAL

### ✅ FUNCIONA si ves:
- ✅ "No hay solicitudes. Crea una nueva."
- ✅ Una lista con solicitudes
- ✅ Botón ➕ visible
- ✅ "Cargando..." desaparece rápido (1-2 segundos)

### ❌ NO FUNCIONA si ves:
- ❌ "Error de red"
- ❌ "No se pudo conectar"
- ❌ "Unable to resolve host"
- ❌ "Cargando..." que nunca termina (más de 10 segundos)
- ❌ La app se cierra sola (crash)

---

## 🧪 CÓMO SE VE CUANDO CREAS UNA SOLICITUD

### PASO 1: Tocar el botón ➕

```
┌──────────────────────────────┐
│  ☰  Gestión Backend          │
├──────────────────────────────┤
│                              │
│  No hay solicitudes.         │
│                              │
│                              │
│                              │
│                              │
│                              │
│                              │
│              ┌───┐           │
│              │ ➕ │  ← TOCA  │
│              └───┘   AQUÍ    │
└──────────────────────────────┘
```

### PASO 2: Formulario

```
┌──────────────────────────────┐
│  ← Nueva Solicitud           │
├──────────────────────────────┤
│                              │
│  Servicio:                   │
│  [_________________]         │
│                              │
│  Cliente:                    │
│  [_________________]         │
│                              │
│  Descripción:                │
│  [_________________]         │
│  [_________________]         │
│                              │
│  Fecha: [__/__/____]         │
│  Hora:  [__:__]              │
│                              │
│  Estado: [PENDIENTE ▼]       │
│                              │
│  Categoría: [1 ▼]            │
│                              │
│        [GUARDAR]             │  ← TOCA AQUÍ
└──────────────────────────────┘
```

### PASO 3A: ✅ Se guardó correctamente

```
┌──────────────────────────────┐
│  ☰  Gestión Backend          │
├──────────────────────────────┤
│                              │
│  ┌────────────────────────┐  │
│  │ Servicio: Prueba       │  │ ← Tu solicitud
│  │ Cliente: Test          │  │   aparece aquí
│  │ Estado: PENDIENTE      │  │
│  │ Fecha: 2024-12-06      │  │
│  └────────────────────────┘  │
│                              │
│                              │
│              ┌───┐           │
│              │ ➕ │           │
│              └───┘           │
└──────────────────────────────┘
```

### PASO 3B: ❌ Error al guardar

```
┌──────────────────────────────┐
│  ← Nueva Solicitud           │
├──────────────────────────────┤
│                              │
│  ⚠️ Error al guardar          │
│                              │
│  No se pudo conectar con     │
│  el servidor.                │
│                              │
│  [Campos del formulario...]  │
│                              │
│        [REINTENTAR]          │
└──────────────────────────────┘
```

---

## 📊 COMPARACIÓN LADO A LADO

```
✅ TODO BIEN                    ❌ HAY PROBLEMA
═══════════════════════════════════════════════

App carga rápido               App tarda mucho
↓                               ↓
Menu ☰ funciona                 Menu ☰ puede fallar
↓                               ↓
"Gestión Backend" abre          Muestra error
↓                               ↓
Lista carga (vacía o con        "Error de red"
datos)                          o queda cargando
↓                               ↓
Botón ➕ visible                 No se ve nada útil
↓                               ↓
Puedo crear solicitud           No puedo crear
↓                               ↓
Solicitud aparece en lista      Error al guardar
↓                               ↓
¡TODO FUNCIONA! 🎉              Hay que arreglar 🔧
```

---

## 💡 QUÉ HACER EN CADA CASO

### Si ves ✅ (TODO FUNCIONA):
```
🎉 ¡FELICIDADES!
- Tu backend está corriendo
- Tu app se conecta correctamente
- Puedes crear, ver, editar y eliminar solicitudes
- La sincronización funciona

✅ YA ESTÁ TODO LISTO PARA USAR
```

### Si ves ❌ (HAY PROBLEMA):
```
🔧 NECESITAS ARREGLAR:

1. Verifica el backend:
   http://localhost:8080/swagger-ui/index.html
   
2. Reinicia el emulador:
   Tools → AVD Manager → Cold Boot Now
   
3. Verifica la URL en RetrofitClient.kt:
   Debe ser: http://10.0.2.2:8080/api/
   
4. Mira Logcat para ver el error exacto
```

---

## 🎬 PASO A PASO EN ANDROID STUDIO

### 1. Ejecutar la App

```
Android Studio - Barra Superior
═══════════════════════════════════════

[📁] [app ▼] [Pixel 4 API 30 ▼] [▶️]
                                  ↑
                             TOCA AQUÍ
```

### 2. Esperar Compilación

```
Build Output (abajo)
════════════════════
> Task :app:compileDebugKotlin
> Task :app:mergeDebugResources
> Task :app:processDebugManifest
...
BUILD SUCCESSFUL in 45s  ← Espera esto
```

### 3. Emulador Abre

```
[Ventana nueva se abre]
         │
         ↓
┌────────────────┐
│   [Android]    │  ← Emulador
│                │
│   [Tu App]     │  ← Tu app se instala
│                │     y abre automáticamente
│                │
└────────────────┘
```

### 4. Probar

```
En el emulador:
1. Toca ☰
2. Toca "Gestión Backend"
3. Observa el resultado
```

---

¿Entiendes mejor ahora? 😊

**RESUMEN EN 1 LÍNEA:**
Ejecuta la app → Abre el menú ☰ → Toca "Gestión Backend" → Si NO dice "Error de red" = ¡Funciona! 🎉

