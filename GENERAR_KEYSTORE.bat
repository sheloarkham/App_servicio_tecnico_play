@echo off
echo ========================================
echo GENERAR KEYSTORE (.jks) PARA APK FIRMADO
echo HU17 - App Servicio Tecnico
echo ========================================
echo.

cd /d "%~dp0"

set KEYSTORE_PATH=app\release-keystore.jks
set KEY_ALIAS=appserviciotecnico
set VALIDITY_DAYS=10000

echo Generando keystore en: %KEYSTORE_PATH%
echo Alias: %KEY_ALIAS%
echo Validez: %VALIDITY_DAYS% dias
echo.
echo IMPORTANTE: Anota las siguientes credenciales
echo --------------------------------------------
echo Keystore password: servicio123
echo Key password: servicio123
echo Nombre: App Servicio Tecnico
echo Organizacion: AppServicioTecnico
echo Ciudad: Lima
echo Estado: Lima
echo Pais: PE
echo --------------------------------------------
echo.

REM Verificar si Java está disponible
where keytool >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: keytool no encontrado. Asegurate de tener Java JDK instalado.
    echo Busca keytool en: C:\Program Files\Android\Android Studio\jbr\bin\
    pause
    exit /b 1
)

REM Verificar si el keystore ya existe
if exist "%KEYSTORE_PATH%" (
    echo.
    echo ADVERTENCIA: El archivo %KEYSTORE_PATH% ya existe.
    set /p OVERWRITE="Deseas sobrescribirlo? (S/N): "
    if /I not "%OVERWRITE%"=="S" (
        echo Operacion cancelada.
        pause
        exit /b 0
    )
    del "%KEYSTORE_PATH%"
)

echo.
echo Generando keystore...
echo.

keytool -genkeypair -v ^
    -keystore "%KEYSTORE_PATH%" ^
    -alias %KEY_ALIAS% ^
    -keyalg RSA ^
    -keysize 2048 ^
    -validity %VALIDITY_DAYS% ^
    -storepass servicio123 ^
    -keypass servicio123 ^
    -dname "CN=App Servicio Tecnico, OU=AppServicioTecnico, O=AppServicioTecnico, L=Lima, ST=Lima, C=PE"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo KEYSTORE GENERADO EXITOSAMENTE
    echo ========================================
    echo.
    echo Ubicacion: %KEYSTORE_PATH%
    echo Alias: %KEY_ALIAS%
    echo.
    echo CREDENCIALES (GUARDAR DE FORMA SEGURA):
    echo - Keystore password: servicio123
    echo - Key password: servicio123
    echo.
    echo Proximo paso: Configurar build.gradle con estas credenciales
    echo.
) else (
    echo.
    echo ERROR: No se pudo generar el keystore
    echo Verifica que Java JDK este instalado correctamente
    echo.
)

pause

