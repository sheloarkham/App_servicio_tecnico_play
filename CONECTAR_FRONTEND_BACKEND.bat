@echo off
echo ============================================
echo GUIA: Conectar Frontend Android con Backend
echo ============================================
echo.
echo PASO 1: Iniciar el Backend
echo ---------------------------
echo 1. Abre VS Code con el proyecto backend
echo 2. Abre la terminal en VS Code
echo 3. Ejecuta: mvnw spring-boot:run (Windows)
echo 4. Espera a ver: "Tomcat started on port(s): 8080"
echo.
echo PASO 2: Verificar Backend
echo -------------------------
echo Abre tu navegador y ve a: http://localhost:8080/api/solicitudes
echo Deberia mostrar: [] (lista vacia) o datos JSON
echo.
echo PASO 3: Ejecutar la App Android
echo --------------------------------
echo 1. En Android Studio, sincroniza el proyecto (Sync Project)
echo 2. Ejecuta la app en el emulador
echo 3. Abre el menu lateral (hamburguesa)
echo 4. Selecciona "Gestion Backend"
echo.
echo PASO 4: Probar CRUD
echo -------------------
echo - Presiona el boton flotante (+) para crear solicitud
echo - Llena el formulario y guarda
echo - Verifica que aparezca en la lista
echo - Prueba editar y eliminar
echo.
echo ============================================
echo URL del Backend (emulador): http://10.0.2.2:8080/api/
echo URL del Backend (navegador): http://localhost:8080/api/
echo ============================================
pause

