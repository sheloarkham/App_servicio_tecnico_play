Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  VERIFICACIÓN COMPLETA DEL SISTEMA" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 1. Verificar Backend
Write-Host "1️⃣ BACKEND (Puerto 8080)" -ForegroundColor Yellow
Write-Host "   Verificando..." -NoNewline
$result = Test-NetConnection -ComputerName localhost -Port 8080 -WarningAction SilentlyContinue -InformationLevel Quiet

if ($result) {
    Write-Host " ✅ CORRIENDO" -ForegroundColor Green
    $backendOK = $true

    # Probar endpoint
    Write-Host "   Probando endpoint /api/solicitudes..." -NoNewline
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:8080/api/solicitudes" -Method Get -TimeoutSec 3 -ErrorAction Stop
        Write-Host " ✅ OK ($($response.Count) solicitudes)" -ForegroundColor Green
        $endpointOK = $true
    } catch {
        Write-Host " ❌ Error" -ForegroundColor Red
        Write-Host "      $($_.Exception.Message)" -ForegroundColor Gray
        $endpointOK = $false
    }
} else {
    Write-Host " ❌ NO ESTÁ CORRIENDO" -ForegroundColor Red
    $backendOK = $false
    $endpointOK = $false
}

Write-Host ""

# 2. Verificar Swagger
Write-Host "2️⃣ SWAGGER UI" -ForegroundColor Yellow
if ($backendOK) {
    Write-Host "   📄 http://localhost:8080/swagger-ui/index.html" -ForegroundColor Cyan
    Write-Host "   ✅ Disponible" -ForegroundColor Green
} else {
    Write-Host "   ❌ No disponible (backend detenido)" -ForegroundColor Red
}

Write-Host ""

# 3. Verificar proyecto Android
Write-Host "3️⃣ PROYECTO ANDROID" -ForegroundColor Yellow

$gradleFile = Test-Path ".\app\build.gradle.kts"
$manifestFile = Test-Path ".\app\src\main\AndroidManifest.xml"
$retrofitFile = Test-Path ".\app\src\main\java\appserviciotecnico\network\config\RetrofitClient.kt"

if ($gradleFile) {
    Write-Host "   ✅ build.gradle.kts presente" -ForegroundColor Green
} else {
    Write-Host "   ❌ build.gradle.kts NO encontrado" -ForegroundColor Red
}

if ($manifestFile) {
    Write-Host "   ✅ AndroidManifest.xml presente" -ForegroundColor Green
} else {
    Write-Host "   ❌ AndroidManifest.xml NO encontrado" -ForegroundColor Red
}

if ($retrofitFile) {
    Write-Host "   ✅ RetrofitClient.kt presente" -ForegroundColor Green

    # Verificar URL configurada
    $content = Get-Content $retrofitFile -Raw
    if ($content -match 'BASE_URL.*=.*"([^"]+)"') {
        $url = $matches[1]
        Write-Host "   📡 URL configurada: $url" -ForegroundColor Cyan
    }
} else {
    Write-Host "   ❌ RetrofitClient.kt NO encontrado" -ForegroundColor Red
}

Write-Host ""

# 4. Resumen
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  RESUMEN" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

if ($backendOK -and $endpointOK) {
    Write-Host "✅ BACKEND: Funcionando correctamente" -ForegroundColor Green
    Write-Host "✅ API REST: Respondiendo en /api/solicitudes" -ForegroundColor Green
    Write-Host "✅ SWAGGER: Disponible para pruebas" -ForegroundColor Green
    Write-Host ""
    Write-Host "🎯 SIGUIENTE PASO:" -ForegroundColor Yellow
    Write-Host "   1. Ejecuta la app en el EMULADOR Android" -ForegroundColor White
    Write-Host "   2. Ve a 'Gestión Backend' en el menú" -ForegroundColor White
    Write-Host "   3. Crea una solicitud de prueba" -ForegroundColor White
    Write-Host "   4. Verifica que aparezca en Swagger" -ForegroundColor White
    Write-Host ""
    Write-Host "📱 RECUERDA:" -ForegroundColor Cyan
    Write-Host "   - Usa el EMULADOR (no dispositivo físico)" -ForegroundColor White
    Write-Host "   - La app usa http://10.0.2.2:8080/api/" -ForegroundColor White
    Write-Host ""
} elseif ($backendOK -and -not $endpointOK) {
    Write-Host "⚠️ BACKEND corriendo pero endpoint con errores" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "🔧 SOLUCIÓN:" -ForegroundColor Yellow
    Write-Host "   1. Abre Swagger: http://localhost:8080/swagger-ui/index.html" -ForegroundColor White
    Write-Host "   2. Verifica la ruta exacta del endpoint" -ForegroundColor White
    Write-Host "   3. Ajusta RetrofitClient.kt si es necesario" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host "❌ BACKEND NO está corriendo" -ForegroundColor Red
    Write-Host ""
    Write-Host "🔧 SOLUCIÓN:" -ForegroundColor Yellow
    Write-Host "   1. Abre el proyecto backend en VS Code" -ForegroundColor White
    Write-Host "   2. Ejecuta: .\mvnw.cmd spring-boot:run" -ForegroundColor White
    Write-Host "   3. Espera el mensaje: 'Tomcat started on port(s): 8080'" -ForegroundColor White
    Write-Host "   4. Vuelve a ejecutar este script" -ForegroundColor White
    Write-Host ""
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

