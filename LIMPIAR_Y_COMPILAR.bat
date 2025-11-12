@echo off
echo ========================================
echo   LIMPIEZA Y RECOMPILACION DEL PROYECTO
echo ========================================
echo.

echo [1/4] Deteniendo daemon de Gradle...
call gradlew --stop

echo.
echo [2/4] Limpiando proyecto...
call gradlew clean

echo.
echo [3/4] Limpiando cache de build...
rd /s /q .gradle 2>nul
rd /s /q app\build 2>nul
rd /s /q build 2>nul

echo.
echo [4/4] Recompilando proyecto...
call gradlew assembleDebug

echo.
echo ========================================
echo   PROCESO COMPLETADO
echo ========================================
pause

