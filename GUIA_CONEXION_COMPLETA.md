# 🚀 GUÍA RÁPIDA: Conectar Frontend con Backend

## ✅ CHECKLIST COMPLETO

### ✅ 1. BACKEND (Ya está implementado en HU12 y HU13)
- [x] Entidades creadas (Solicitud, Cotizacion)
- [x] Repositories implementados
- [x] Services implementados
- [x] Controllers con endpoints REST
- [x] CORS configurado

### ✅ 2. FRONTEND (Ya está implementado en HU14)
- [x] Retrofit configurado
- [x] DTOs creados
- [x] API Interfaces creadas
- [x] Repository implementado
- [x] ViewModel con StateFlow
- [x] UI Screen completa
- [x] Navegación integrada
- [x] Permisos de Internet

---

## 🔥 PASOS PARA CONECTAR AHORA

### PASO 1: Iniciar el Backend
1. ✅ **VS Code abierto** con el proyecto: `APP_SERVICIO_TECNICO_BACKEND`
2. Abre la terminal en VS Code (Ctrl + ` o menú Terminal → New Terminal)
3. Ejecuta en la terminal:
   ```bash
   # Windows
   .\mvnw.cmd spring-boot:run
   
   # O si tienes Maven instalado
   mvn spring-boot:run
   ```
4. Espera a ver: **"Tomcat started on port(s): 8080"**
5. Verás también: **"Started BackendApplication in X.XXX seconds"**

### PASO 2: Verificar el Backend
1. Abre tu navegador
2. Ve a: http://localhost:8080/api/solicitudes
3. Deberías ver: `[]` (lista vacía en JSON)

### PASO 3: Sincronizar Android Studio
1. En Android Studio, ve a: **File → Sync Project with Gradle Files**
2. Espera a que termine (verás barra de progreso abajo)
3. Verifica que no haya errores

### PASO 4: Ejecutar la App
1. Dale play ▶️ en Android Studio
2. Espera a que abra el emulador
3. Inicia sesión en la app
4. Abre el menú lateral (☰)
5. Selecciona **"Gestión Backend"**

### PASO 5: Probar CRUD
1. Presiona el botón flotante **+** (abajo a la derecha)
2. Llena el formulario:
   - Servicio: "Limpieza PS4"
   - Cliente: "Juan Pérez"
   - Descripción: "Limpieza profunda"
   - Fecha: "2024-12-15"
   - Hora: "14:00"
   - Estado: PENDIENTE
   - Categoría: 1
3. Presiona **"Guardar"**
4. Deberías ver la solicitud en la lista
5. Prueba editar ✏️ y eliminar 🗑️

---

## 🔧 SOLUCIÓN DE PROBLEMAS

### ❌ "Error: Unable to resolve host"
- El backend no está corriendo
- Solución: Inicia el backend (Paso 1)

### ❌ "Error 404 Not Found"
- La URL del backend es incorrecta
- Solución: Verifica que sea `http://10.0.2.2:8080/api/` en el emulador

### ❌ "Unresolved reference 'Retrofit'"
- Las dependencias no se descargaron
- Solución: Sincroniza el proyecto (Paso 3)

### ❌ Backend no inicia
- Verifica que tienes Java 11 o superior
- Ejecuta: `java -version`

---

## 📝 URLs IMPORTANTES

| Contexto | URL |
|----------|-----|
| **Backend desde navegador** | http://localhost:8080/api/ |
| **Backend desde emulador** | http://10.0.2.2:8080/api/ |
| **Backend desde dispositivo físico** | http://192.168.100.141:8080/api/ |

---

## ✅ VERIFICACIÓN FINAL

Una vez que todo funcione:
- [x] ✅ El backend responde en el navegador (http://localhost:8080)
- [x] ✅ El backend está corriendo en puerto 8080
- [ ] La app se conecta al backend (PENDIENTE)
- [ ] Puedes crear solicitudes
- [ ] Puedes ver la lista
- [ ] Puedes editar solicitudes
- [ ] Puedes eliminar solicitudes

---

## 🎯 ESTADO ACTUAL

### ✅ Lo que YA está listo:
- Código completo implementado
- Merge a dev realizado
- Push a GitHub completado

### ⏳ Lo que falta:
1. Iniciar el backend
2. Sincronizar Android Studio
3. Probar la conexión

**¡Ya casi está! Solo falta iniciar el backend y probar.** 🚀

