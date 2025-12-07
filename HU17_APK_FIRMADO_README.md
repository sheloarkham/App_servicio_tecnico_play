# 📦 HU17 - Generar APK Firmado con .jks

## 📋 Resumen
Se implementó la generación de APK firmado con archivo `.jks` para asegurar la autenticidad de la aplicación y permitir su instalación en dispositivos Android.

---

## ✅ Criterios de Aceptación Cumplidos

| Criterio | Estado | Evidencia |
|----------|--------|-----------|
| Archivo .jks creado y configurado | ✅ | `app/release-keystore.jks` generado |
| APK firmado generado correctamente | ✅ | `app/build/outputs/apk/release/app-release.apk` |
| Configuración en build.gradle | ✅ | `signingConfigs` agregado |
| Scripts automatizados | ✅ | GENERAR_KEYSTORE.bat, GENERAR_APK_FIRMADO.bat |
| Documentación completa | ✅ | CREDENCIALES_KEYSTORE.md |

---

## 🔐 Archivo Keystore (.jks)

### Información del Keystore:
```
Archivo: app/release-keystore.jks
Alias: appserviciotecnico
Algoritmo: RSA 2048 bits
Validez: 10,000 días
```

### Credenciales (Solo para desarrollo):
```
Keystore Password: servicio123
Key Password: servicio123
```

⚠️ **IMPORTANTE:** El archivo `.jks` NO está incluido en el repositorio Git por razones de seguridad.

---

## 🛠️ Archivos Creados/Modificados

### 1️⃣ **Scripts de Automatización**

#### `GENERAR_KEYSTORE.bat`
Script para generar el archivo `.jks` con todas las configuraciones necesarias.

**Uso:**
```bash
.\GENERAR_KEYSTORE.bat
```

**Características:**
- ✅ Genera keystore RSA 2048 bits
- ✅ Configura alias y contraseñas
- ✅ Validez de 10,000 días
- ✅ Verifica si Java/keytool está disponible
- ✅ Previene sobrescritura accidental

---

#### `GENERAR_APK_FIRMADO.bat`
Script para compilar y generar el APK firmado automáticamente.

**Uso:**
```bash
.\GENERAR_APK_FIRMADO.bat
```

**Proceso:**
1. Limpia el proyecto (`gradlew clean`)
2. Compila APK release firmado (`gradlew assembleRelease`)
3. Muestra ubicación del APK generado
4. Proporciona comando de instalación

---

### 2️⃣ **Configuración en build.gradle.kts**

Se agregó la configuración de firma:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file("release-keystore.jks")
        storePassword = "servicio123"
        keyAlias = "appserviciotecnico"
        keyPassword = "servicio123"
    }
}

buildTypes {
    release {
        isMinifyEnabled = false
        signingConfig = signingConfigs.getByName("release")
        proguardFiles(...)
    }
}
```

---

### 3️⃣ **.gitignore Actualizado**

Se agregaron exclusiones para archivos sensibles:

```gitignore
# Keystore - NO subir al repositorio por seguridad
*.jks
*.keystore
release-keystore.jks

# APK firmado
app-release.apk
*.apk
```

---

### 4️⃣ **Documentación**

- **CREDENCIALES_KEYSTORE.md**: Información completa del keystore y credenciales
- **HU17_APK_FIRMADO_README.md**: Este archivo con toda la documentación

---

## 🚀 Cómo Generar el APK Firmado

### Opción 1: Script Automatizado (Recomendado)

```bash
# Paso 1: Generar keystore (solo primera vez)
.\GENERAR_KEYSTORE.bat

# Paso 2: Generar APK firmado
.\GENERAR_APK_FIRMADO.bat
```

### Opción 2: Gradle Manual

```bash
# Limpiar proyecto
.\gradlew.bat clean

# Generar APK firmado
.\gradlew.bat assembleRelease
```

### Opción 3: Android Studio

1. Menú: **Build** → **Generate Signed Bundle / APK**
2. Seleccionar: **APK**
3. Configurar keystore:
   - Path: `app/release-keystore.jks`
   - Password: `servicio123`
   - Alias: `appserviciotecnico`
   - Key password: `servicio123`
4. Click en **Finish**

---

## 📱 Instalación del APK

### Ubicación del APK Generado:
```
app/build/outputs/apk/release/app-release.apk
```

### Instalar en Emulador:
```bash
adb install app\build\outputs\apk\release\app-release.apk
```

### Instalar en Dispositivo Físico:

1. **Habilitar instalación de fuentes desconocidas:**
   - Ajustes → Seguridad → Fuentes desconocidas ✅

2. **Transferir APK al dispositivo:**
   - USB, Bluetooth, o Email

3. **Instalar:**
   - Abrir explorador de archivos
   - Click en `app-release.apk`
   - Aceptar permisos

---

## 🔍 Verificar Firma del APK

### Verificar que el APK está firmado:

```bash
keytool -printcert -jarfile app\build\outputs\apk\release\app-release.apk
```

**Salida esperada:**
```
Owner: CN=App Servicio Tecnico, OU=AppServicioTecnico, O=AppServicioTecnico, L=Lima, ST=Lima, C=PE
Issuer: CN=App Servicio Tecnico, OU=AppServicioTecnico, O=AppServicioTecnico, L=Lima, ST=Lima, C=PE
Serial number: ...
Valid from: ... until: ...
Certificate fingerprints:
         SHA1: ...
         SHA256: ...
```

---

## 🔒 Seguridad y Mejores Prácticas

### ✅ Implementado:

1. **Exclusión del keystore del repositorio Git**
   - El archivo `.jks` está en `.gitignore`
   
2. **Documentación de credenciales**
   - Archivo separado con información del keystore
   
3. **Scripts automatizados**
   - Reduce errores manuales
   - Proceso reproducible

### ⚠️ Para Producción:

1. **Contraseñas más seguras:**
   - Usar contraseñas de al menos 16 caracteres
   - Combinar letras, números y símbolos
   
2. **Almacenamiento seguro:**
   - Guardar keystore en bóveda cifrada
   - Hacer múltiples backups
   
3. **Variables de entorno:**
   ```kotlin
   storePassword = System.getenv("KEYSTORE_PASSWORD")
   keyPassword = System.getenv("KEY_PASSWORD")
   ```

4. **Nunca perder el keystore:**
   - ⚠️ Si pierdes el keystore, no podrás actualizar la app
   - Hacer backup inmediatamente después de crearlo

---

## 📊 Información Técnica

### Detalles del APK:

| Propiedad | Valor |
|-----------|-------|
| Application ID | com.appserviciotecnico |
| Version Code | 1 |
| Version Name | 1.0 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 (Android 14+) |
| Firma | RSA 2048 bits |

### Tamaño Aproximado:
- APK Release: ~15-25 MB (dependiendo de las dependencias)

---

## 🧪 Pruebas de Instalación

### Checklist de Pruebas:

- [ ] APK se genera correctamente sin errores
- [ ] APK está firmado (verificado con keytool)
- [ ] Instalación exitosa en emulador
- [ ] Instalación exitosa en dispositivo físico
- [ ] La app se abre sin crashes
- [ ] Todas las funcionalidades operan correctamente

---

## 📝 Checklist de Implementación

- [x] Crear rama feature/HU17_apk_firmado desde dev
- [x] Generar archivo .jks con keytool
- [x] Configurar signingConfigs en build.gradle.kts
- [x] Actualizar .gitignore para excluir keystore
- [x] Crear script GENERAR_KEYSTORE.bat
- [x] Crear script GENERAR_APK_FIRMADO.bat
- [x] Documentar credenciales en CREDENCIALES_KEYSTORE.md
- [x] Generar APK firmado
- [ ] Probar instalación en emulador/dispositivo
- [ ] Capturar evidencias (screenshots)
- [ ] Commit y push a GitHub
- [ ] Merge a dev

---

## 🎓 Conceptos Aprendidos

1. ✅ **Keystore (.jks):** Archivo que almacena claves y certificados
2. ✅ **Firma de APK:** Proceso de autenticación de la aplicación
3. ✅ **signingConfigs:** Configuración de Gradle para firma automática
4. ✅ **keytool:** Herramienta Java para gestión de keystores
5. ✅ **Seguridad:** Importancia de proteger el keystore

---

## 🔗 Referencias

- [Android Developers - Sign your app](https://developer.android.com/studio/publish/app-signing)
- [Gradle - Sign your app](https://developer.android.com/build/building-cmdline#sign_cmdline)
- [Keytool Documentation](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/keytool.html)

---

## 📞 Soporte

Si tienes problemas:

1. **Keystore no se genera:**
   - Verifica que Java JDK esté instalado
   - Busca keytool en: `C:\Program Files\Android\Android Studio\jbr\bin\`

2. **Error al generar APK:**
   - Ejecuta: `.\gradlew.bat clean`
   - Verifica que el keystore existe en `app/release-keystore.jks`

3. **APK no se instala:**
   - Verifica que el dispositivo permita fuentes desconocidas
   - Desinstala versiones anteriores de la app

---

**✅ HU17 - APK Firmado Implementado Exitosamente**

*Fecha: 2025-01-07*  
*Rama: feature/HU17_apk_firmado*  
*Keystore: app/release-keystore.jks*  
*APK: app/build/outputs/apk/release/app-release.apk*

