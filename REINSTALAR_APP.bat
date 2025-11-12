@echo off
echo ========================================
echo   LIMPIAR CACHE Y REINSTALAR APP
echo ========================================
echo.

echo [1/4] Limpiando proyecto Gradle...
call gradlew clean

echo.
echo [2/4] Desinstalando app del emulador...
adb uninstall com.appserviciotecnico

echo.
echo [3/4] Compilando APK Debug...
call gradlew assembleDebug

echo.
echo [4/4] Instalando nueva version...
call gradlew installDebug

echo.
echo ========================================
echo   APP LIMPIA E INSTALADA
echo ========================================
echo Ahora puedes abrir la app en el emulador
pause

