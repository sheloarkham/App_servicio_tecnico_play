@echo off
echo ===============================================
echo   COMPILANDO CON LOGO CORRECTO: logojuega.png
echo ===============================================
echo.

cd /d "%~dp0"

echo [1/4] Limpiando cache anterior...
call gradlew.bat clean
if errorlevel 1 (
    echo ERROR: Fallo al limpiar
    pause
    exit /b 1
)

echo.
echo [2/4] Compilando proyecto...
echo (Esto tomara 2-5 minutos, por favor espera)
echo.
call gradlew.bat assembleDebug
if errorlevel 1 (
    echo.
    echo ===============================================
    echo   ERROR AL COMPILAR
    echo ===============================================
    echo.
    echo Revisa los errores arriba.
    echo Si dice "Unresolved reference: R" haz lo siguiente:
    echo 1. Abre Android Studio
    echo 2. File - Invalidate Caches / Restart
    echo 3. Espera a que reinicie
    echo 4. Ejecuta este script de nuevo
    echo.
    pause
    exit /b 1
)

echo.
echo [3/4] Verificando APK...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✓ APK generado correctamente
) else (
    echo ✗ No se encontro el APK
    pause
    exit /b 1
)

echo.
echo [4/4] Listo para instalar
echo.
echo ===============================================
echo   ✅ COMPILACION EXITOSA
echo ===============================================
echo.
echo El logo correcto (logojuega.png) esta configurado.
echo.
echo AHORA EJECUTA LA APP:
echo 1. Abre Android Studio
echo 2. Click en el boton verde ▶️ Run
echo 3. Selecciona tu dispositivo/emulador
echo.
echo DEBERIAS VER:
echo - Barra superior con menu
echo - Tu logo (logojuega.png) en el centro
echo - Texto "Bienvenido"
echo - Texto "App Servicio Tecnico"
echo.
echo Si la app crashea:
echo - Abre Logcat en Android Studio
echo - Busca lineas ROJAS
echo - Copia el error completo
echo.
pause

