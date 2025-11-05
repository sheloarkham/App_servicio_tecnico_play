@echo off
echo ========================================
echo   LIMPIEZA Y COMPILACION DEL PROYECTO
echo ========================================
echo.

cd /d "%~dp0"

echo [1/5] Limpiando proyecto...
call gradlew.bat clean
if errorlevel 1 (
    echo ERROR: Fallo al limpiar el proyecto
    pause
    exit /b 1
)

echo.
echo [2/5] Compilando proyecto (esto puede tardar)...
call gradlew.bat assembleDebug
if errorlevel 1 (
    echo ERROR: Fallo al compilar el proyecto
    echo Revisa los errores arriba
    pause
    exit /b 1
)

echo.
echo [3/5] Verificando APK generado...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✓ APK generado correctamente
    echo Ubicacion: app\build\outputs\apk\debug\app-debug.apk
) else (
    echo ✗ No se encontro el APK
    pause
    exit /b 1
)

echo.
echo ========================================
echo   COMPILACION EXITOSA
echo ========================================
echo.
echo Para instalar en tu dispositivo:
echo 1. Conecta tu dispositivo Android con depuracion USB
echo 2. Ejecuta: adb install -r app\build\outputs\apk\debug\app-debug.apk
echo.
echo O simplemente ejecuta desde Android Studio con el boton Play
echo.
pause

