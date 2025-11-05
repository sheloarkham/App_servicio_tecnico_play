♠@echo off
echo ===============================================
echo   COMPILANDO PROYECTO (SIN LOGO)
echo ===============================================
echo.

cd /d "%~dp0"

echo [PASO 1/3] Limpiando proyecto anterior...
call gradlew.bat clean
if errorlevel 1 (
    echo.
    echo ERROR al limpiar proyecto
    pause
    exit /b 1
)

echo.
echo [PASO 2/3] Compilando proyecto completo...
echo (Esto puede tardar 2-5 minutos)
echo.
call gradlew.bat assembleDebug
if errorlevel 1 (
    echo.
    echo ===============================================
    echo   ERROR AL COMPILAR
    echo ===============================================
    echo.
    echo Revisa los errores arriba.
    echo.
    pause
    exit /b 1
)

echo.
echo [PASO 3/3] Verificando APK...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo.
    echo ===============================================
    echo   ✅ COMPILACION EXITOSA
    echo ===============================================
    echo.
    echo APK generado en:
    echo app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo AHORA:
    echo 1. Abre Android Studio
    echo 2. Click en el boton verde Play ▶️
    echo 3. La app deberia abrir MOSTRANDO SOLO TEXTO
    echo.
    echo Si ves el texto "Bienvenido" = El problema era el logo
    echo Si crashea = Copia el error del Logcat
    echo.
) else (
    echo.
    echo ERROR: No se genero el APK
    echo.
)

pause

