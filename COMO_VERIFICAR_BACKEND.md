# ✅ GUÍA FINAL: Cómo Verificar que el Backend Funciona

## 🎯 TIENES EL BACKEND CORRIENDO ✅

Ya confirmamos que Swagger está disponible en:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📝 PASOS PARA VERIFICAR LA CONEXIÓN COMPLETA

### PASO 1: Prueba en Swagger (5 minutos)

#### 1.1 Abre Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

#### 1.2 Busca el controlador "Solicitudes"
- Mira en la página principal de Swagger
- Debería aparecer algo como:
  - `solicitud-controller` 
  - `SolicitudController`
  - O similar

#### 1.3 Prueba GET (Listar solicitudes)
1. **Expande** el endpoint `GET /api/solicitudes` (o similar)
2. Click en **"Try it out"**
3. Click en **"Execute"**
4. **Mira la respuesta:**
   
   ✅ **ÉXITO si ves:**
   ```json
   []
   ```
   O una lista con datos:
   ```json
   [
     {
       "id": 1,
       "servicio": "...",
       "cliente": "...",
       ...
     }
   ]
   ```
   
   ❌ **ERROR si ves:**
   - Código 404 (Not Found)
   - Código 500 (Internal Server Error)
   - Sin respuesta

#### 1.4 Prueba POST (Crear solicitud)
1. **Expande** el endpoint `POST /api/solicitudes` (o similar)
2. Click en **"Try it out"**
3. **Edita el JSON de ejemplo:**
   ```json
   {
     "servicio": "Limpieza PlayStation 5",
     "cliente": "Juan Pérez",
     "descripcion": "Limpieza profunda y cambio de pasta térmica",
     "fechaSolicitud": "2024-12-15",
     "horaSolicitud": "14:30",
     "estadoSolicitud": "PENDIENTE",
     "idCategoria": 1
   }
   ```
4. Click en **"Execute"**
5. **Mira la respuesta:**
   
   ✅ **ÉXITO si ves:**
   - Código **201 Created** o **200 OK**
   - El JSON con la solicitud creada y un `id` asignado
   
   ❌ **ERROR si ves:**
   - Código 400 (Bad Request) - Revisa el formato del JSON
   - Código 500 - Error en el servidor

#### 1.5 Anota el resultado
- [ ] ✅ GET funciona
- [ ] ✅ POST funciona
- [ ] ✅ Puedo ver los datos creados

---

### PASO 2: Verifica las URLs que usa tu App

Tu app Android está configurada con:

**Archivo:** `RetrofitClient.kt`
```kotlin
BASE_URL = "http://10.0.2.2:8080/api/"
```

**Endpoints en:** `SolicitudApi.kt`
```kotlin
@GET("solicitudes")           // → http://10.0.2.2:8080/api/solicitudes
@POST("solicitudes")          // → http://10.0.2.2:8080/api/solicitudes
@PUT("solicitudes/{id}")      // → http://10.0.2.2:8080/api/solicitudes/1
@DELETE("solicitudes/{id}")   // → http://10.0.2.2:8080/api/solicitudes/1
```

**✅ Esto es CORRECTO si:**
- En Swagger la ruta es: `/api/solicitudes`
- Estás usando el **emulador Android** (no dispositivo físico)

**❌ Necesitas cambiar si:**
- En Swagger la ruta es diferente (ej: solo `/solicitudes` sin `/api/`)
- Estás usando un **dispositivo físico**

---

### PASO 3: Prueba desde la App (10 minutos)

#### 3.1 Ejecuta la App
1. En **Android Studio**, asegúrate que el emulador esté seleccionado (no dispositivo físico)
2. Click en el botón **▶️ Run 'app'**
3. Espera que la app se instale y abra

#### 3.2 Navega a Gestión Backend
1. Abre el **menú lateral** (☰ esquina superior izquierda)
2. Toca **"Gestión Backend"** o similar

#### 3.3 Observa el estado inicial

**✅ CONEXIÓN EXITOSA si ves:**
- "Cargando..." que desaparece rápidamente
- Luego: "No hay solicitudes. Crea una nueva."
- O una lista con solicitudes existentes

**❌ SIN CONEXIÓN si ves:**
- "Cargando..." que nunca desaparece
- Mensaje de error: "Error de red"
- Mensaje: "Unable to resolve host"
- La app se congela o crashea

#### 3.4 Crea una solicitud desde la App
1. Toca el botón flotante **➕** (abajo a la derecha)
2. **Llena el formulario:**
   - **Servicio:** "Reparación PS4"
   - **Cliente:** "María González"
   - **Descripción:** "Problema con lector de discos"
   - **Fecha:** 2024-12-15
   - **Hora:** 15:00
   - **Estado:** PENDIENTE
   - **Categoría:** 1
3. Toca **"Guardar"** o **"Crear"**

#### 3.5 Verifica el resultado

**A. En la App:**
- ✅ La solicitud debe aparecer inmediatamente en la lista
- ✅ Deberías ver los datos que acabas de ingresar

**B. En Swagger:**
1. Vuelve al navegador
2. Abre Swagger: http://localhost:8080/swagger-ui/index.html
3. Ejecuta `GET /api/solicitudes` nuevamente
4. ✅ Deberías ver tu solicitud en la respuesta JSON

**C. En Logcat (Android Studio):**
1. Mira la pestaña **"Logcat"** (parte inferior de Android Studio)
2. Filtra escribiendo: `package:mine` en el campo de búsqueda
3. Busca líneas con:
   - `OkHttp` - Peticiones HTTP
   - `Retrofit` - Respuestas del servidor
   - Deberías ver algo como:
     ```
     --> POST http://10.0.2.2:8080/api/solicitudes
     <-- 201 CREATED (145ms)
     ```

---

### PASO 4: Prueba CRUD Completo

#### 4.1 Editar una solicitud
1. En la app, toca una solicitud de la lista
2. Modifica algún campo (ej: cambia el estado a "EN_PROCESO")
3. Guarda los cambios
4. ✅ Verifica que el cambio se refleje en la lista
5. ✅ Verifica en Swagger que el cambio se guardó

#### 4.2 Eliminar una solicitud
1. En la app, toca el ícono de eliminar (🗑️) en una solicitud
2. Confirma la eliminación
3. ✅ Verifica que desaparezca de la lista
4. ✅ Verifica en Swagger que ya no existe

---

## 🐛 SOLUCIÓN DE PROBLEMAS

### ❌ Problema 1: "Unable to resolve host 10.0.2.2"

**Causa:** El emulador no puede conectarse al host

**Soluciones:**
1. **Reinicia el emulador:**
   - En Android Studio: Tools → AVD Manager
   - Click en el menú ⬇️ junto a tu emulador
   - Selecciona "Cold Boot Now"
   - Espera que reinicie
   - Vuelve a ejecutar la app

2. **Verifica que uses el emulador:**
   - En la barra superior de Android Studio
   - Debe decir algo como "Pixel 4 API 30" (no un dispositivo físico)

3. **Verifica la configuración de red del emulador:**
   - En el emulador, abre "Settings"
   - Ve a "Network & internet"
   - Verifica que esté conectado

### ❌ Problema 2: "Connection refused"

**Causa:** URL incorrecta o firewall bloqueando

**Soluciones:**
1. **Verifica que el backend esté corriendo:**
   ```
   http://localhost:8080/swagger-ui/index.html
   ```
   - Si no abre, reinicia el backend

2. **Verifica la URL en el código:**
   - Abre `RetrofitClient.kt`
   - Debe ser: `http://10.0.2.2:8080/api/`
   - NO: `http://localhost:8080/api/` (esto no funciona en emulador)

3. **Verifica el firewall:**
   - Busca "Firewall de Windows" en el menú Inicio
   - Ve a "Permitir una aplicación"
   - Busca "Java" o "javaw"
   - Asegúrate que esté permitido en "Privada"

### ❌ Problema 3: "404 Not Found"

**Causa:** La ruta del endpoint no coincide

**Soluciones:**
1. **Verifica la ruta en Swagger:**
   - Mira exactamente cuál es la URL del endpoint
   - Puede ser `/api/solicitudes` o solo `/solicitudes`

2. **Ajusta el código si es necesario:**
   
   **Si en Swagger es `/solicitudes` (sin `/api/`):**
   
   Cambia `RetrofitClient.kt`:
   ```kotlin
   private const val BASE_URL = "http://10.0.2.2:8080/"
   ```
   
   **Si en Swagger es `/api/solicitudes`:**
   
   Mantén:
   ```kotlin
   private const val BASE_URL = "http://10.0.2.2:8080/api/"
   ```

### ❌ Problema 4: La app se congela en "Cargando..."

**Causa:** El backend no responde o hay timeout

**Soluciones:**
1. **Verifica el backend en el navegador:**
   ```
   http://localhost:8080/api/solicitudes
   ```
   - Debe responder rápido (menos de 1 segundo)

2. **Mira Logcat para ver el error exacto:**
   - En Android Studio, pestaña Logcat
   - Busca mensajes con "Error", "Exception", o "Failed"

3. **Aumenta el timeout en RetrofitClient:**
   - Abre `RetrofitClient.kt`
   - Busca la configuración de OkHttpClient
   - Aumenta el timeout si es necesario

---

## ✅ CHECKLIST FINAL

Marca cada uno cuando lo completes:

### Backend
- [ ] Swagger UI accesible en http://localhost:8080/swagger-ui/index.html
- [ ] GET /api/solicitudes funciona en Swagger
- [ ] POST /api/solicitudes funciona en Swagger
- [ ] Puedo crear una solicitud desde Swagger
- [ ] La solicitud tiene un ID asignado

### App - Configuración
- [ ] RetrofitClient.kt tiene URL: `http://10.0.2.2:8080/api/`
- [ ] SolicitudApi.kt tiene endpoints correctos
- [ ] AndroidManifest.xml tiene permiso INTERNET
- [ ] La app compila sin errores

### App - Ejecución
- [ ] Emulador seleccionado (no dispositivo físico)
- [ ] App ejecuta y abre correctamente
- [ ] Puedo navegar a "Gestión Backend"
- [ ] La pantalla carga (no se queda congelada)

### App - Funcionalidad
- [ ] Puedo ver la lista de solicitudes
- [ ] Puedo crear una nueva solicitud
- [ ] La solicitud creada aparece en la lista
- [ ] Puedo editar una solicitud existente
- [ ] Puedo eliminar una solicitud
- [ ] Los cambios se reflejan en Swagger

### Sincronización
- [ ] Crear en App → Ver en Swagger ✅
- [ ] Crear en Swagger → Ver en App (refrescar) ✅
- [ ] Editar en App → Ver cambio en Swagger ✅
- [ ] Eliminar en App → Desaparece de Swagger ✅

---

## 🎯 RESUMEN

### ✅ LO QUE YA TIENES:
- Backend corriendo en puerto 8080
- Swagger UI disponible
- API documentada
- App Android con Retrofit configurado

### 📱 LO QUE DEBES HACER AHORA:
1. **Abrir Swagger y probar los endpoints manualmente**
2. **Ejecutar la app en el EMULADOR**
3. **Navegar a "Gestión Backend"**
4. **Crear una solicitud de prueba**
5. **Verificar en ambos lados que funcione**

### 🎉 SABRÁS QUE FUNCIONA CUANDO:
- ✅ Creas una solicitud en la app
- ✅ La ves inmediatamente en la lista
- ✅ Refrescas Swagger y la ves allí también
- ✅ Los datos coinciden exactamente
- ✅ Puedes editar y eliminar sin problemas

---

## 📞 COMANDOS DE AYUDA RÁPIDA

**Verificar puerto 8080:**
```powershell
Test-NetConnection -ComputerName localhost -Port 8080
```

**Ver proceso en puerto 8080:**
```powershell
Get-NetTCPConnection -LocalPort 8080 | Format-Table
```

**Obtener IP local (para dispositivo físico):**
```powershell
(Get-NetIPAddress -AddressFamily IPv4 | Where-Object {$_.InterfaceAlias -like '*Wi-Fi*' -or $_.InterfaceAlias -like '*Ethernet*'}).IPAddress
```

---

## 🚀 SIGUIENTE PASO: ¡PRUEBA AHORA!

1. Abre Swagger: http://localhost:8080/swagger-ui/index.html
2. Prueba crear una solicitud desde Swagger
3. Ejecuta la app en el emulador
4. Navega a "Gestión Backend"
5. ¿Ves la solicitud que creaste en Swagger?

**¡Avísame cómo te va!** 🎉

