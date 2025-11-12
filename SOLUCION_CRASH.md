# 🔧 SOLUCIÓN AL CRASH DE LA APP

## 📋 CAUSA DEL PROBLEMA
La app se crasheaba porque la estructura de la base de datos cambió (agregamos campos nuevos a `FormularioServicioEntity`), pero la versión anterior de la base de datos seguía en el emulador.

## ✅ SOLUCIÓN APLICADA

### 1. **Cambio en el Código**
✅ Se agregó `.fallbackToDestructiveMigration()` en `AppDatabase.kt`
- Esto permite que Room borre y recree la base de datos automáticamente cuando detecta cambios

### 2. **Limpiar e Reinstalar la App**

#### Opción A: Usando el script automático (RECOMENDADO)
1. Ejecuta el archivo: `REINSTALAR_APP.bat`
2. Espera a que termine el proceso
3. Abre la app en el emulador

#### Opción B: Manual desde Android Studio
1. **Build** → **Clean Project**
2. **Build** → **Rebuild Project**
3. En el emulador:
   - Settings → Apps → App Servicio Técnico
   - Storage → Clear Storage
   - Clear Cache
4. **Run** → **Run 'app'**

#### Opción C: Desinstalar del emulador
1. En el emulador, mantén presionado el ícono de la app
2. Arrastra a "Desinstalar" o "Uninstall"
3. En Android Studio: **Run** → **Run 'app'**

## 🎯 QUÉ ESPERAR DESPUÉS

Después de reinstalar, la app debería:
- ✅ Iniciar sin crashes
- ✅ Mostrar el logo de inicio (2 segundos)
- ✅ Pasar automáticamente al login
- ✅ Permitir navegar normalmente
- ✅ Base de datos limpia (sin datos anteriores)

## ⚠️ IMPORTANTE

**Los datos anteriores se perderán** porque la base de datos se recrea. Esto es normal en desarrollo. En producción usaríamos migraciones, pero para desarrollo es más rápido recrear la DB.

## 📱 SI AÚN CRASHEA

Si después de limpiar aún crashea:
1. **Cold Boot del emulador**:
   - Device Manager → Click derecho en tu emulador
   - "Cold Boot Now"
   - Espera a que reinicie completamente
   - Vuelve a ejecutar la app

2. **Wipe Data del emulador**:
   - Device Manager → Click derecho en tu emulador
   - "Wipe Data"
   - Confirmar
   - Reiniciar emulador

---
**Fecha:** 2025-11-11
**Versión de DB:** 1 (con fallbackToDestructiveMigration)

