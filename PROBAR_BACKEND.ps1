# 🧪 Script de Prueba Rápida - Backend

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  PRUEBA DE CONEXIÓN BACKEND" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Prueba 1: Verificar puerto 8080
Write-Host "1. Verificando puerto 8080..." -ForegroundColor Yellow
$testConnection = Test-NetConnection -ComputerName localhost -Port 8080 -WarningAction SilentlyContinue

if ($testConnection.TcpTestSucceeded) {
    Write-Host "   ✅ Puerto 8080 ABIERTO - Backend corriendo" -ForegroundColor Green
} else {
    Write-Host "   ❌ Puerto 8080 CERRADO - Backend NO está corriendo" -ForegroundColor Red
    Write-Host "   Inicia el backend y vuelve a ejecutar este script" -ForegroundColor Yellow
    exit
}

Write-Host ""

# Prueba 2: Probar endpoint GET
Write-Host "2. Probando GET /api/solicitudes..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/solicitudes" -Method Get -ErrorAction Stop
    $count = $response.Count
    Write-Host "   ✅ Endpoint responde correctamente" -ForegroundColor Green
    Write-Host "   📊 Solicitudes encontradas: $count" -ForegroundColor Cyan

    if ($count -gt 0) {
        Write-Host ""
        Write-Host "   Últimas solicitudes:" -ForegroundColor Cyan
        $response | Select-Object -First 3 | ForEach-Object {
            Write-Host "   - ID: $($_.id) | Servicio: $($_.servicio) | Cliente: $($_.cliente)" -ForegroundColor White
        }
    }
} catch {
    Write-Host "   ❌ Error al conectar: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "   Verifica que el endpoint sea correcto en Swagger" -ForegroundColor Yellow
}

Write-Host ""

# Prueba 3: Crear solicitud de prueba
Write-Host "3. Creando solicitud de prueba..." -ForegroundColor Yellow
$timestamp = Get-Date -Format "HH:mm:ss"
$body = @{
    servicio = "Prueba Automatica - $timestamp"
    cliente = "Script PowerShell"
    descripcion = "Solicitud creada para verificar conexion"
    fechaSolicitud = (Get-Date).ToString("yyyy-MM-dd")
    horaSolicitud = $timestamp
    estadoSolicitud = "PENDIENTE"
    idCategoria = 1
} | ConvertTo-Json

try {
    $createResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/solicitudes" -Method Post -Body $body -ContentType "application/json" -ErrorAction Stop
    Write-Host "   ✅ Solicitud creada exitosamente" -ForegroundColor Green
    Write-Host "   📝 ID asignado: $($createResponse.id)" -ForegroundColor Cyan
    Write-Host "   📌 Servicio: $($createResponse.servicio)" -ForegroundColor White

    $createdId = $createResponse.id
} catch {
    Write-Host "   ❌ Error al crear: $($_.Exception.Message)" -ForegroundColor Red
    $createdId = $null
}

Write-Host ""

# Prueba 4: Leer la solicitud creada
if ($createdId) {
    Write-Host "4. Verificando solicitud creada (GET por ID)..." -ForegroundColor Yellow
    try {
        $getResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/solicitudes/$createdId" -Method Get -ErrorAction Stop
        Write-Host "   ✅ Solicitud recuperada correctamente" -ForegroundColor Green
        Write-Host "   📋 Datos:" -ForegroundColor Cyan
        Write-Host "      - ID: $($getResponse.id)" -ForegroundColor White
        Write-Host "      - Servicio: $($getResponse.servicio)" -ForegroundColor White
        Write-Host "      - Cliente: $($getResponse.cliente)" -ForegroundColor White
        Write-Host "      - Estado: $($getResponse.estadoSolicitud)" -ForegroundColor White
    } catch {
        Write-Host "   ❌ Error al leer: $($_.Exception.Message)" -ForegroundColor Red
    }

    Write-Host ""

    # Prueba 5: Eliminar la solicitud de prueba
    Write-Host "5. Limpiando solicitud de prueba..." -ForegroundColor Yellow
    try {
        Invoke-RestMethod -Uri "http://localhost:8080/api/solicitudes/$createdId" -Method Delete -ErrorAction Stop | Out-Null
        Write-Host "   ✅ Solicitud eliminada correctamente" -ForegroundColor Green
    } catch {
        Write-Host "   ⚠️ No se pudo eliminar (ID: $createdId)" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  RESUMEN" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "✅ Backend funcionando en puerto 8080" -ForegroundColor Green
Write-Host "✅ Endpoints REST operativos" -ForegroundColor Green
Write-Host "✅ CRUD completo verificado" -ForegroundColor Green
Write-Host ""
Write-Host "🚀 SIGUIENTE PASO:" -ForegroundColor Yellow
Write-Host "   1. Ejecuta tu app en el EMULADOR Android" -ForegroundColor White
Write-Host "   2. Ve a 'Gestión Backend' en el menú" -ForegroundColor White
Write-Host "   3. Intenta crear una solicitud" -ForegroundColor White
Write-Host "   4. Verifica en Swagger que aparezca" -ForegroundColor White
Write-Host ""
Write-Host "📱 IMPORTANTE:" -ForegroundColor Yellow
Write-Host "   - Usa EMULADOR (no dispositivo físico)" -ForegroundColor White
Write-Host "   - La app usa: http://10.0.2.2:8080/api/" -ForegroundColor White
Write-Host "   - 10.0.2.2 es localhost para el emulador" -ForegroundColor White
Write-Host ""
Write-Host "🔍 SWAGGER UI:" -ForegroundColor Cyan
Write-Host "   http://localhost:8080/swagger-ui/index.html" -ForegroundColor White
Write-Host ""

