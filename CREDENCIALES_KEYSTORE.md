# 🔐 CREDENCIALES DEL KEYSTORE - HU17

## ⚠️ INFORMACIÓN CONFIDENCIAL - NO COMPARTIR

Este archivo contiene las credenciales del keystore para firmar el APK.
**NO debe ser compartido públicamente ni subido a GitHub.**

---

## 📋 Información del Keystore

**Archivo:** `app/release-keystore.jks`

### Credenciales:
- **Keystore Password:** `servicio123`
- **Key Alias:** `appserviciotecnico`
- **Key Password:** `servicio123`

### Información del Certificado:
- **Nombre:** App Servicio Tecnico
- **Organización:** AppServicioTecnico
- **Ciudad:** Lima
- **Estado:** Lima
- **País:** PE

### Detalles Técnicos:
- **Algoritmo:** RSA
- **Tamaño de clave:** 2048 bits
- **Validez:** 10,000 días (aproximadamente 27 años)

---

## 🔨 Cómo Usar

### Generar Keystore (primera vez):
```bash
.\GENERAR_KEYSTORE.bat
```

### Generar APK Firmado:
```bash
.\GENERAR_APK_FIRMADO.bat
```

### Verificar Firma del APK:
```bash
keytool -printcert -jarfile app\build\outputs\apk\release\app-release.apk
```

---

## 📦 Ubicación del APK Firmado

Después de generar el APK firmado, lo encontrarás en:
```
app/build/outputs/apk/release/app-release.apk
```

---

## 📱 Instalación

### En Emulador:
```bash
adb install app\build\outputs\apk\release\app-release.apk
```

### En Dispositivo Físico:
1. Habilitar "Fuentes desconocidas" en Ajustes
2. Transferir el APK al dispositivo
3. Instalar desde el explorador de archivos

---

## 🔒 Seguridad

- ✅ El archivo `.jks` está excluido del repositorio Git
- ✅ Las contraseñas son solo para desarrollo/demostración
- ⚠️ En producción, usar contraseñas más seguras
- ⚠️ Guardar el keystore en un lugar seguro (backup)

---

## 📝 Notas Importantes

1. **NO PERDER EL KEYSTORE:** Si pierdes el keystore, no podrás actualizar la app en el futuro
2. **BACKUP:** Hacer backup del archivo `.jks` en un lugar seguro
3. **CONTRASEÑAS:** En producción, usar contraseñas más robustas
4. **GITHUB:** El keystore NO debe ser subido a GitHub

---

**Fecha de generación:** 2025-01-07  
**HU17 - APK Firmado**

