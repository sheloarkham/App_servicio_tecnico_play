@echo off
echo ========================================
echo GENERAR APK FIRMADO - HU17
echo App Servicio Tecnico
echo ========================================
echo.

cd /d "%~dp0"

REM Verificar que el keystore existe
if not exist "app\release-keystore.jks" (
    echo ERROR: No se encontro el archivo release-keystore.jks
    echo Por favor ejecuta GENERAR_KEYSTORE.bat primero
    pause
    exit /b 1
)

echo [1/3] Limpiando proyecto...
call gradlew.bat clean

echo.
echo [2/3] Generando APK Release firmado...
call gradlew.bat assembleRelease

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo APK FIRMADO GENERADO EXITOSAMENTE
    echo ========================================
    echo.
    echo Ubicacion del APK:
    echo app\build\outputs\apk\release\app-release.apk
    echo.
    echo Puedes instalar este APK en cualquier dispositivo Android
    echo.

    REM Mostrar información del APK
    echo [3/3] Informacion del APK:
    if exist "app\build\outputs\apk\release\app-release.apk" (
        dir "app\build\outputs\apk\release\app-release.apk" | findstr "app-release.apk"
        echo.
        echo Para instalar en emulador:
        echo adb install app\build\outputs\apk\release\app-release.apk
        echo.
    )
) else (
    echo.
    echo ERROR: No se pudo generar el APK
    echo Revisa los errores anteriores
    echo.
)

pause

