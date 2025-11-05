# 🔧 INSTRUCCIONES PARA COMPILAR Y VER EL LOGO

## ✅ Cambios Realizados

1. ✅ **AndroidManifest.xml** - Agregado `package="com.appserviciotecnico"`
2. ✅ **StartScreen.kt** - Mejorada la configuración de la imagen con `ContentScale.Fit`
3. ✅ **Logo** - Presente en `res/drawable/logo.jpg`

---

## 🚀 PASOS PARA COMPILAR (SIGUE ESTE ORDEN EXACTO)

### **Paso 1: Cerrar Android Studio**
- Cierra completamente Android Studio si está abierto
- Esto asegura que no haya procesos bloqueando archivos

### **Paso 2: Eliminar carpetas de build (Limpieza profunda)**
Abre el Explorador de Windows y elimina estas carpetas:
```
C:\Users\Axel\StudioProjects\App_servicio_tecnico_play\app\build
C:\Users\Axel\StudioProjects\App_servicio_tecnico_play\build
C:\Users\Axel\StudioProjects\App_servicio_tecnico_play\.gradle
```
(Si no puedes eliminar `.gradle`, está bien, continúa)

### **Paso 3: Abrir Android Studio**
- Abre Android Studio
- Abre el proyecto: `App_servicio_tecnico_play`
- Espera a que termine de indexar (verás una barra de progreso abajo)

### **Paso 4: Sync Gradle**
- Arriba a la derecha verás un ícono 🐘 con una flecha
- Click en ese ícono o ve a: `File → Sync Project with Gradle Files`
- **ESPERA** a que termine completamente (puede tardar 1-3 minutos)
- Verás "Gradle sync finished" abajo

### **Paso 5: Build → Clean Project**
- Ve al menú: `Build → Clean Project`
- Espera a que termine

### **Paso 6: Build → Rebuild Project**
- Ve al menú: `Build → Rebuild Project`
- **ESPERA** a que compile todo (puede tardar 2-5 minutos)
- Si sale algún error, **CÓPIALO** y compártelo

### **Paso 7: Verificar que no haya errores**
- Mira la pestaña "Build" abajo
- Debe decir algo como "BUILD SUCCESSFUL"
- Si dice "BUILD FAILED", copia el error completo

### **Paso 8: Ejecutar la App**
- Conecta tu teléfono Android O inicia un emulador
- Click en el botón verde ▶️ "Run 'app'"
- Espera a que instale

---

## 📱 LO QUE DEBERÍAS VER

Si todo funciona correctamente:

1. **La app se abre**
2. **Ves la barra superior** con "☰ Inicio"
3. **Ves tu logo** en el centro (debería ocupar el 60% del ancho)
4. **Ves el texto** "Bienvenido"
5. **Ves el texto** "App Servicio Técnico"
6. **Ves el texto** "Usa el menú ☰ para navegar"

---

## 🐛 SI AÚN NO APARECE EL LOGO

### Opción A: El logo no se carga (pero la app funciona)

**Posible causa:** El archivo `logo.jpg` tiene problemas

**Solución:**
1. Convierte tu imagen a PNG (mejor compatibilidad)
2. Renómbrala a `logo.png`
3. Ponla en: `app\src\main\res\drawable\logo.png`
4. Haz Rebuild Project

### Opción B: La app crashea (se cierra inmediatamente)

**Solución:**
1. En Android Studio, abre la pestaña "Logcat" (abajo)
2. En el filtro, selecciona tu app
3. Busca líneas ROJAS con "ERROR" o "FATAL EXCEPTION"
4. Copia TODO el stack trace (el mensaje de error completo)
5. Compártelo para ver qué está pasando

### Opción C: La app no compila

**Solución:**
1. Copia el mensaje de error de la pestaña "Build"
2. Compártelo para solucionarlo

---

## 📝 VERIFICACIÓN RÁPIDA

Antes de compilar, verifica estos archivos:

### 1. `app/build.gradle.kts`
Debe tener:
```kotlin
namespace = "com.appserviciotecnico"
applicationId = "com.appserviciotecnico"
```

### 2. `app/src/main/AndroidManifest.xml`
Primera línea del manifest debe incluir:
```xml
package="com.appserviciotecnico"
```

### 3. `app/src/main/res/drawable/`
Debe contener el archivo:
```
logo.jpg
```

### 4. `StartScreen.kt`
Debe tener el import:
```kotlin
import com.appserviciotecnico.R
```

---

## 💡 TIPS ADICIONALES

1. **Si el emulador es muy lento:** Usa un dispositivo físico conectado por USB con depuración USB activada

2. **Si dice "Waiting for debugger":** Espera 30 segundos, si no responde, detén la app y vuelve a ejecutar

3. **Si el logo aparece distorsionado:** El código ya tiene `ContentScale.Fit` que debería mantener las proporciones

4. **Si quieres cambiar el tamaño del logo:** En `StartScreen.kt`, cambia `.height(200.dp)` por el tamaño que quieras

---

## ✅ CHECKLIST FINAL

Antes de ejecutar, verifica:

- [ ] Cerré y abrí Android Studio
- [ ] Eliminé las carpetas build (o hice Clean)
- [ ] Hice Sync Gradle y terminó correctamente
- [ ] Hice Rebuild Project y no hubo errores
- [ ] Tengo un emulador corriendo O un dispositivo conectado
- [ ] El logo.jpg existe en drawable

Si marcaste todo ✅ y aún no funciona, **comparte el error del Logcat o Build**.

