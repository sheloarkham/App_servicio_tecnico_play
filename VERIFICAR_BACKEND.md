# 🔍 GUÍA DE VERIFICACIÓN: ¿Está funcionando el Backend?

## ⚡ VERIFICACIÓN RÁPIDA (5 minutos)

### 📍 PASO 1: ¿Está corriendo el backend?

**Opción A - Desde PowerShell:**
```powershell
Test-NetConnection -ComputerName localhost -Port 8080
```
✅ Si dice `TcpTestSucceeded : True` → **BACKEND CORRIENDO**
❌ Si dice `TcpTestSucceeded : False` → **BACKEND DETENIDO**

**Opción B - Desde el navegador:**
1. Abre: http://localhost:8080/api/solicitudes
2. ✅ Si ves `[]` o `[{...}]` → **FUNCIONA**
3. ❌ Si dice "No se puede acceder" → **NO ESTÁ CORRIENDO**

---

### 📍 PASO 2: Iniciar el Backend (si está detenido)

1. **Ubicar el proyecto backend**
   - Busca una carpeta llamada: `APP_SERVICIO_TECNICO_BACKEND` o similar
   - Debe contener: `pom.xml` y carpeta `src/`

2. **Abrir VS Code en esa carpeta**
   ```powershell
   cd "C:\ruta\a\tu\backend"
   code .
   ```

3. **Ejecutar el backend**
   - Abre la terminal en VS Code (Ctrl + `)
   - Ejecuta:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```
   - O si tienes Maven instalado:
   ```bash
   mvn spring-boot:run
   ```

4. **Espera el mensaje:**
   ```
   Tomcat started on port(s): 8080
   Started BackendApplication in X.XXX seconds
   ```

---

### 📍 PASO 3: Verificar desde la App Android

#### A. Verificar la configuración de Retrofit

1. Abre el archivo: `app/src/main/java/.../network/RetrofitClient.kt`
2. Verifica que la URL sea:
   ```kotlin
   private const val BASE_URL = "http://10.0.2.2:8080/api/"
   ```
   - `10.0.2.2` es la IP especial del emulador para `localhost`
   - Si usas dispositivo físico, usa la IP de tu PC (ej: `http://192.168.1.x:8080/api/`)

#### B. Probar en la app

1. **Ejecuta la app** en el emulador
2. **Navega a "Gestión Backend"** (menú lateral)
3. **Observa los estados:**

   ✅ **ÉXITO:**
   - Ves: "No hay solicitudes. Crea una nueva." → Backend conectado, lista vacía
   - O ves una lista de solicitudes → Backend conectado, con datos

   ❌ **ERROR:**
   - "Error de red" o "Unable to resolve host" → Backend no está corriendo
   - "Connection refused" → URL incorrecta en RetrofitClient
   - "404 Not Found" → Endpoint incorrecto

---

### 📍 PASO 4: Crear una Solicitud de Prueba

1. **En la app:**
   - Presiona el botón flotante ➕
   - Llena el formulario:
     - Servicio: "Prueba Backend"
     - Cliente: "Test User"
     - Descripción: "Verificando conexión"
     - Fecha: 2024-12-15
     - Hora: 14:00
     - Estado: PENDIENTE
     - Categoría: 1
   - Presiona "Guardar"

2. **Verifica en 3 lugares:**

   **A. En la app:**
   - ✅ La solicitud aparece en la lista

   **B. En el navegador:**
   - Abre: http://localhost:8080/api/solicitudes
   - ✅ Deberías ver el JSON de tu solicitud

   **C. En Logcat (Android Studio):**
   - Busca en los logs:
   ```
   D/Retrofit: --> POST http://10.0.2.2:8080/api/solicitudes
   D/Retrofit: <-- 201 CREATED
   ```

---

## 🔧 SOLUCIÓN DE PROBLEMAS COMUNES

### ❌ Error: "Unable to resolve host 10.0.2.2"

**Causa:** El backend no está corriendo
**Solución:**
1. Inicia el backend (ver Paso 2)
2. Verifica en navegador: http://localhost:8080/api/solicitudes
3. Reinicia la app

---

### ❌ Error: "Connection refused"

**Causa:** URL incorrecta o emulador sin acceso
**Solución:**
1. Verifica RetrofitClient.kt use: `http://10.0.2.2:8080/api/`
2. Si usas dispositivo físico, cambia a IP de tu PC:
   - Obtén tu IP: `ipconfig` → busca IPv4 (ej: 192.168.1.5)
   - Cambia URL a: `http://192.168.1.5:8080/api/`
3. Asegúrate que firewall permita puerto 8080

---

### ❌ Backend se detiene solo

**Causa:** Error en el código backend o BD no disponible
**Solución:**
1. Revisa la consola de VS Code para errores
2. Verifica que la base de datos H2 esté configurada
3. Revisa `application.properties` o `application.yml`

---

### ❌ Error: "404 Not Found"

**Causa:** Endpoint incorrecto
**Solución:**
1. Verifica que el backend tenga el controlador:
   ```java
   @RestController
   @RequestMapping("/api/solicitudes")
   public class SolicitudController { ... }
   ```
2. La URL completa debe ser: `http://localhost:8080/api/solicitudes`

---

## ✅ CHECKLIST FINAL DE VERIFICACIÓN

Marca cada uno al completarlo:

- [ ] **Backend corriendo** - http://localhost:8080/api/solicitudes responde
- [ ] **Puerto 8080 abierto** - `Test-NetConnection` dice `True`
- [ ] **RetrofitClient configurado** - URL: `http://10.0.2.2:8080/api/`
- [ ] **Permisos de Internet** - AndroidManifest.xml tiene `INTERNET`
- [ ] **App ejecutada** - Sin errores de compilación
- [ ] **Navegación a Gestión Backend** - Pantalla carga correctamente
- [ ] **Crear solicitud** - Se guarda y aparece en la lista
- [ ] **Ver en navegador** - http://localhost:8080/api/solicitudes muestra datos
- [ ] **Editar solicitud** - Cambios se reflejan
- [ ] **Eliminar solicitud** - Se borra correctamente

---

## 📊 HERRAMIENTAS ADICIONALES DE VERIFICACIÓN

### 1. Swagger UI (Documentación Interactiva)
Si el backend tiene Swagger configurado:
- Abre: http://localhost:8080/swagger-ui/index.html
- Prueba los endpoints directamente desde el navegador

### 2. Postman / Insomnia
Prueba los endpoints manualmente:
```
GET    http://localhost:8080/api/solicitudes
POST   http://localhost:8080/api/solicitudes
PUT    http://localhost:8080/api/solicitudes/1
DELETE http://localhost:8080/api/solicitudes/1
```

### 3. Logs de Android Studio
En Logcat, filtra por:
- Tag: `OkHttp` - Ver peticiones HTTP
- Tag: `Retrofit` - Ver respuestas del servidor
- Tag: `SolicitudVM` - Ver logs del ViewModel

---

## 🎯 RESUMEN: ¿Cómo sé que funciona?

### ✅ TODO BIEN si:
1. Backend responde en navegador: `[]` o datos JSON
2. App muestra "No hay solicitudes" o lista con datos
3. Crear solicitud → aparece inmediatamente
4. Actualizar navegador → datos coinciden con la app
5. Logcat muestra: `<-- 200 OK` o `<-- 201 CREATED`

### ❌ HAY PROBLEMA si:
1. Navegador: "No se puede acceder al sitio"
2. App muestra: "Error de red"
3. Logcat muestra: "UnknownHostException" o "ConnectException"
4. Backend no está en la lista de procesos

---

## 📞 AYUDA RÁPIDA

**¿Backend corriendo?**
```powershell
netstat -ano | findstr :8080
```
Si ves resultados → el puerto está en uso (backend corriendo)

**¿Qué proceso usa el puerto 8080?**
```powershell
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess
```
Debería mostrar `java` o `javaw`

**¿Matar proceso en puerto 8080?**
```powershell
Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force
```

---

¡Sigue esta guía paso a paso y sabrás exactamente si tu backend funciona! 🚀

