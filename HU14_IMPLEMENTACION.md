# HU14 - Integración con Backend mediante Retrofit

## Resumen de Implementación

Se ha implementado la conexión de la aplicación móvil Android con el backend Spring Boot usando Retrofit.

## Archivos Creados

### 1. Modelos DTO (Data Transfer Objects)
- `network/models/SolicitudDTO.kt` - Modelo para solicitudes
- `network/models/CotizacionDTO.kt` - Modelo para cotizaciones

### 2. API Services (Interfaces de Retrofit)
- `network/api/SolicitudApi.kt` - Endpoints para Solicitudes
- `network/api/CotizacionApi.kt` - Endpoints para Cotizaciones

### 3. Configuración de Retrofit
- `network/config/RetrofitClient.kt` - Configuración centralizada de Retrofit con OkHttp

### 4. Repository
- `network/repository/SolicitudRemoteRepository.kt` - Gestiona la comunicación con el backend

### 5. Utils
- `network/utils/NetworkResult.kt` - Clase sellada para manejar estados (Loading, Success, Error, Idle)

### 6. ViewModel
- `viewmodel/SolicitudBackendViewModel.kt` - Expone estados reactivos para la UI

### 7. UI Screen
- `ui/screen/SolicitudBackendScreen.kt` - Pantalla completa con CRUD de solicitudes

## Archivos Modificados

### 1. `app/build.gradle.kts`
- Agregadas dependencias de Retrofit, Gson y OkHttp
- Agregadas coroutines para operaciones asíncronas

### 2. `app/src/main/AndroidManifest.xml`
- Agregado permiso de INTERNET
- Agregado permiso de ACCESS_NETWORK_STATE
- Habilitado `usesCleartextTraffic` para desarrollo local

### 3. `navigation/Routes.kt`
- Agregada ruta `SolicitudBackend`

### 4. `navigation/AppNav.kt`
- Agregado composable para `SolicitudBackendScreen`
- Agregada opción "Gestión Backend" en el menú drawer
- Agregado título en el TopBar

## Funcionalidades Implementadas

✅ **Retrofit configurado** con base URL `http://10.0.2.2:8080/api/`
✅ **CRUD completo** para Solicitudes:
   - GET /solicitudes (Listar todas)
   - GET /solicitudes/{id} (Obtener por ID)
   - POST /solicitudes (Crear)
   - PUT /solicitudes/{id} (Actualizar)
   - DELETE /solicitudes/{id} (Eliminar)
   - GET /solicitudes/estado/{estado} (Filtrar por estado)

✅ **Manejo de estados** mediante `NetworkResult`:
   - Loading (Cargando)
   - Success (Éxito)
   - Error (Error con mensaje)
   - Idle (Inicial)

✅ **UI completa** con:
   - Lista de solicitudes
   - Botón flotante para crear
   - Cards con opciones de editar/eliminar
   - Diálogo para crear/editar con validaciones
   - Indicadores de carga
   - Manejo de errores con botón de reintentar

✅ **Arquitectura limpia**:
   - Separación de responsabilidades
   - Repository pattern
   - MVVM con StateFlow
   - Coroutines para operaciones asíncronas

## Configuración de Red

### Para Emulador Android Studio:
```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/api/"
```

### Para Dispositivo Físico (misma red WiFi):
```kotlin
private const val BASE_URL = "http://192.168.100.141:8080/api/"
```

## Próximos Pasos

1. **Sincronizar proyecto** en Android Studio (Build → Sync Project with Gradle Files)
2. **Asegurar que el backend esté corriendo** en `http://localhost:8080`
3. **Ejecutar la app** en el emulador
4. **Navegar** a "Gestión Backend" desde el menú lateral
5. **Probar el CRUD** completo:
   - Crear solicitudes
   - Ver lista actualizada
   - Editar solicitudes existentes
   - Eliminar solicitudes
6. **Capturar evidencias** para documentación

## Notas Técnicas

- Los datos se obtienen del backend en tiempo real
- La UI se actualiza automáticamente usando `StateFlow`
- Los errores de red se manejan elegantemente
- Logging completo en Logcat para debugging
- Timeouts configurados: 30 segundos

## Testing

Para probar en emulador, asegúrate de:
1. Backend corriendo en puerto 8080
2. Emulador Android Studio ejecutándose
3. Revisar Logcat para ver las peticiones HTTP

## Dependencias Agregadas

```kotlin
// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

