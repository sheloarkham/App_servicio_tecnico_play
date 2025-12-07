@echo off
echo ========================================
echo EJECUTANDO PRUEBAS UNITARIAS HU16
echo ========================================
echo.

cd /d "%~dp0"

echo [1/3] Limpiando proyecto...
call gradlew.bat clean

echo.
echo [2/3] Ejecutando pruebas unitarias...
call gradlew.bat :app:testDebugUnitTest --info

echo.
echo [3/3] Generando reporte de cobertura...
call gradlew.bat :app:testDebugUnitTest jacocoTestReport

echo.
echo ========================================
echo PRUEBAS COMPLETADAS
echo ========================================
echo Reporte en: app\build\reports\tests\testDebugUnitTest\index.html
echo Cobertura en: app\build\reports\jacoco\testDebugUnitTest\html\index.html
echo.
pause

