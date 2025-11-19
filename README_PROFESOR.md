# README Académico - App Servicio Técnico PlayStation

## 📋 Pauta de Presentación del Proyecto

### 1. Presentación PowerPoint
- **No es obligatoria una presentación PowerPoint** para este proyecto.
- La demostración se realizará ejecutando directamente la aplicación desde Android Studio.

### 2. Inicialización de la App desde Android Studio

#### 2.1 Pasos para Ejecutar la App
1. **Abrir el proyecto** en Android Studio
2. **Sincronizar Gradle**: Hacer clic en "Sync Now" si aparece la notificación
3. **Configurar emulador o dispositivo físico**:
   - **Emulador**: Ir a Device Manager → Crear/Iniciar emulador con API 24+
   - **Dispositivo físico**: Habilitar "Opciones de desarrollador" y "Depuración USB"
4. **Ejecutar la app**: Hacer clic en el botón "Run" (▶️) o presionar Shift+F10

#### 2.2 Identificación de Problemas Comunes

**Error de compilación:**
```
JAVA_HOME is not set
```
**Solución**: Verificar que Android Studio esté usando la JDK correcta en File → Project Structure → SDK Location

**Error de Room Database:**
```
Cannot find implementation for Database
```
**Solución**: El proyecto usa `kapt` para Room. Verificar que las dependencias estén sincronizadas.

**Error de MainActivity no encontrada:**
```
ClassNotFoundException: MainActivity
```
**Solución**: Verificar que el namespace en `build.gradle.kts` coincida con la estructura de paquetes.

**Para diagnosticar errores:**
- Revisar el **Logcat** en Android Studio
- Usar **Build → Clean Project** seguido de **Build → Rebuild Project**
- Verificar que el emulador tenga suficiente RAM (mínimo 2GB recomendado)

### 3. Explicación de la UI

#### 3.a) Componentes del Formulario

**Formulario de Servicio Técnico** (`FormularioServicioScreen.kt`):
- `OutlinedTextField` para nombre, correo, teléfono
- `ExposedDropdownMenuBox` para tipo de consola (PS4/PS5)
- `OutlinedTextField` multibanco para descripción del problema
- `Button` con validación en tiempo real
- **Validaciones implementadas**:
  - Campos obligatorios
  - Formato de correo electrónico
  - Longitud mínima de descripción

**Ejemplo de validación:**
```kotlin
private fun validarFormulario(estado: FormularioServicioState): FormularioServicioErrores {
    return FormularioServicioErrores(
        nombreCliente = if (estado.nombreCliente.isBlank()) "El nombre es requerido" else null,
        emailCliente = if (!estado.correoCliente.contains("@")) "Correo inválido" else null
    )
}
```

#### 3.b) Componentes de Navegación

**Navegación implementada** (`AppNav.kt`):
- `NavHost` con `NavController` para gestionar rutas
- **Rutas principales**:
  - `/start` - Splash screen con logo
  - `/login` - Pantalla de autenticación
  - `/home` - Dashboard principal
  - `/catalog` - Catálogo de servicios
  - `/agendar` - Agendar cita
  - `/solicitudes` - Estado de solicitudes
  - `/form` - Formulario de servicio

**Drawer Navigation**:
- `ModalNavigationDrawer` con menú lateral
- Navegación entre secciones principales
- Estado compartido entre pantallas

#### 3.c) Animaciones

**Animaciones implementadas**:
- **Fade In/Out** en transiciones entre pantallas
- **Scale animation** en botones al presionar
- **Slide animation** para drawer menu
- **Rotating icon** en FAB
- **Card elevation** con animación en hover

**Ejemplo de animación:**
```kotlin
AnimatedVisibility(
    visible = visible,
    enter = fadeIn(animationSpec = tween(500)) + slideInVertically(),
    exit = fadeOut(animationSpec = tween(300)) + slideOutVertically()
) {
    // Contenido animado
}
```

#### 3.d) Comunicación UI-ViewModel

**Patrón MVVM implementado**:
- **UI (Composables)** → observa `StateFlow` del ViewModel
- **ViewModel** → gestiona estado con `MutableStateFlow`
- **Repository** → maneja datos persistentes

**Flujo de comunicación**:
```kotlin
// 1. UI envía acción al ViewModel
onNombreChange = viewModel::onNombreChange

// 2. ViewModel actualiza estado
fun onNombreChange(valor: String) {
    _estado.update { it.copy(nombreCliente = valor) }
}

// 3. UI observa cambios de estado
val estado by viewModel.estado.collectAsState()
```

### 4. Persistencia de Datos (SQLite + Room)

#### 4.a) Configuración de Base de Datos

**AppDatabase.kt**:
```kotlin
@Database(
    entities = [
        FormularioServicioEntity::class,
        SolicitudEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun formularioServicioDao(): FormularioServicioDao
    abstract fun solicitudDao(): SolicitudDao
}
```

#### 4.b) Configuración de Librerías

**build.gradle.kts**:
```kotlin
dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
}
```

#### 4.c) DAO, Repository y Entidades

**Entidad** (`FormularioServicioEntity.kt`):
```kotlin
@Entity(tableName = "formulario_servicio")
data class FormularioServicioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombreCliente: String,
    val correoCliente: String,
    val estadoSolicitud: String,
    val fechaSolicitud: String
)
```

**DAO** (`FormularioServicioDao.kt`):
```kotlin
@Dao
interface FormularioServicioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormulario(formulario: FormularioServicioEntity): Long
    
    @Query("SELECT * FROM formulario_servicio ORDER BY id DESC")
    fun getFormularios(): Flow<List<FormularioServicioEntity>>
}
```

**Repository** (`FormularioServicioRepository.kt`):
```kotlin
class FormularioServicioRepository(private val dao: FormularioServicioDao) {
    fun obtenerFormularios() = dao.getFormularios()
    suspend fun guardarFormulario(entity: FormularioServicioEntity) = dao.insertFormulario(entity)
}
```

#### 4.d) Comunicación ViewModel-Persistencia

**Operaciones CRUD demostradas**:

**INSERCIÓN:**
```kotlin
viewModelScope.launch {
    val entity = FormularioServicioEntity(/* datos */)
    repository.guardarFormulario(entity)
}
```

**OBTENCIÓN:**
```kotlin
val solicitudes = repository.obtenerFormularios().collectAsState(initial = emptyList())
```

**ACTUALIZACIÓN:**
```kotlin
suspend fun actualizarSolicitud(id: Long, nuevoEstado: String) {
    repository.actualizarEstado(id, nuevoEstado)
}
```

**ELIMINACIÓN:**
```kotlin
suspend fun eliminarSolicitud(id: Long) {
    repository.eliminarSolicitud(id)
}
```

### 5. Arquitectura MVVM

#### 5.1 Implementación del Patrón

**Model (Modelo)**:
- **Entities**: Clases de datos para Room (`FormularioServicioEntity`)
- **Repository**: Abstracción para acceso a datos
- **DAO**: Interfaz para operaciones SQLite

**View (Vista)**:
- **Composables**: Pantallas de UI (`HomeScreen`, `FormularioServicioScreen`)
- **Theme**: Configuración de colores y tipografía
- **Components**: Componentes reutilizables

**ViewModel**:
- **ViewModels**: Lógica de negocio (`FormularioServicioViewModel`)
- **States**: Estados de UI (`FormularioServicioState`)
- **Factory**: Creación de ViewModels con dependencias

#### 5.2 Beneficios de MVVM en este Proyecto

- **Separación de responsabilidades**: UI no conoce la lógica de datos
- **Testabilidad**: ViewModels pueden probarse independientemente
- **Reutilización**: Repository puede usarse en múltiples ViewModels
- **Mantenibilidad**: Cambios en UI no afectan lógica de negocio

### 6. Pruebas con Validaciones

#### Ejemplo 1: Validación de Correo Electrónico
```kotlin
@Test
fun test_correo_vacio_es_invalido() {
    // Given
    val correoVacio = ""
    
    // When
    val error = ValidadorFormulario.validarCorreo(correoVacio)
    
    // Then
    assertNotNull("El correo vacío debe generar error", error)
    assertEquals("El correo es requerido", error)
}
```

#### Ejemplo 2: Validación de Estado de Solicitud
```kotlin
@Test
fun test_transicion_estado_valida() {
    // Given
    val estadoInicial = "Pendiente"
    
    // When
    val puedeTransicionar = ValidadorEstado.puedeTransicionarA(estadoInicial, "En Proceso")
    
    // Then
    assertTrue("Debe poder transicionar de Pendiente a En Proceso", puedeTransicionar)
}
```

### 7. Propuesta de Funcionalidad Adicional

#### 7.1 Notificaciones Push
**Dónde agregar**: Crear nuevo módulo `notifications/`
- **Componente**: `NotificationManager` para gestionar notificaciones
- **Persistencia**: Tabla `notificaciones` en Room
- **UI**: Badge con contador en el drawer menu

#### 7.2 Sistema de Rating/Calificación
**Dónde agregar**: Extender `FormularioServicioEntity`
- **Nuevo campo**: `calificacionServicio: Float`
- **UI**: `RatingBar` en pantalla post-servicio
- **ViewModel**: `CalificacionViewModel` para gestionar ratings

#### 7.3 Geolocalización para Técnicos
**Dónde agregar**: Nuevo paquete `location/`
- **Componente**: `LocationHelper` usando GPS
- **Persistencia**: Campos lat/lng en `SolicitudEntity`
- **UI**: Mapa en pantalla de seguimiento

---

## 🎯 Conclusión

La aplicación implementa correctamente:
- ✅ **Arquitectura MVVM** con separación clara de responsabilidades
- ✅ **Persistencia local** con Room/SQLite
- ✅ **UI moderna** con Jetpack Compose
- ✅ **Validaciones completas** con feedback visual
- ✅ **Navegación fluida** entre pantallas
- ✅ **Animaciones funcionales** que mejoran UX
- ✅ **Pruebas unitarias** para lógica crítica

El proyecto está listo para demostración y cumple con todos los requisitos académicos establecidos.
