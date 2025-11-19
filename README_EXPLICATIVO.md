# README Técnico Completo - App Servicio Técnico PlayStation

## 📚 Guía de Aprendizaje Completa para Desarrollo Android

---

## 1. ¿Qué es Android Studio y cómo se usa?

### 1.1 Introducción a Android Studio
**Android Studio** es el IDE (Integrated Development Environment) oficial para desarrollar aplicaciones Android. Es desarrollado por Google y está basado en IntelliJ IDEA.

**Características principales:**
- **Editor de código inteligente** con autocompletado
- **Emulador integrado** para probar apps sin dispositivo físico
- **Debugger visual** para encontrar errores
- **Layout Editor** para diseñar interfaces gráficamente
- **Gradle** como sistema de construcción

### 1.2 Cómo usar Android Studio en este proyecto

**Estructura de proyecto típica:**
```
📁 App_servicio_tecnico_play/
├── 📁 app/                     # Módulo principal de la app
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/        # Código Kotlin/Java
│   │   │   ├── 📁 res/         # Recursos (imágenes, layouts, strings)
│   │   │   └── AndroidManifest.xml # Configuración de la app
│   │   └── 📁 test/            # Tests unitarios
│   └── build.gradle.kts        # Configuración de dependencias
└── gradle.properties           # Configuración global
```

**Flujo de trabajo básico:**
1. **Escribir código** en archivos `.kt` (Kotlin)
2. **Compilar** con Gradle (Build → Make Project)
3. **Probar** en emulador o dispositivo
4. **Debuggear** si hay errores
5. **Iterar** hasta completar funcionalidad

---

## 2. ¿Qué es Kotlin y por qué se usa en Android?

### 2.1 Introducción a Kotlin
**Kotlin** es un lenguaje de programación moderno desarrollado por JetBrains. Google lo declaró como lenguaje preferido para Android en 2019.

**Ventajas de Kotlin sobre Java:**
- **Sintaxis más concisa** (menos código boilerplate)
- **Null Safety** (evita errores de NullPointerException)
- **Interoperabilidad** 100% con Java
- **Funciones de extensión** para extender clases existentes
- **Coroutines** para manejo asíncrono simple

### 2.2 Ejemplos de Kotlin en nuestro proyecto

**Sintaxis concisa:**
```kotlin
// Java tradicional
public class Usuario {
    private String nombre;
    private String email;
    
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    // ... getters y setters
}

// Kotlin equivalente
data class Usuario(
    val nombre: String,
    val email: String
)
```

**Null Safety:**
```kotlin
// Kotlin previene errores de null
var nombre: String? = null  // Puede ser null
var edad: String = ""       // No puede ser null

// Operador safe call
val longitud = nombre?.length ?: 0  // Si nombre es null, retorna 0
```

**Coroutines para operaciones asíncronas:**
```kotlin
// En nuestro ViewModel
viewModelScope.launch {
    try {
        repository.guardarFormulario(entity)  // Operación en segundo plano
        _estado.update { it.copy(mensajeExito = "Guardado exitosamente") }
    } catch (e: Exception) {
        // Manejar error
    }
}
```

---

## 3. ¿Qué es Jetpack Compose y cómo se estructura la UI?

### 3.1 Introducción a Jetpack Compose
**Jetpack Compose** es el toolkit moderno de Google para crear UI nativas de Android. Reemplaza el sistema tradicional de XML layouts.

**Principios clave:**
- **Declarativo**: Describes cómo debe verse la UI, no cómo construirla
- **Composable**: Funciones que pueden combinarse para crear interfaces complejas
- **Reactividad**: La UI se actualiza automáticamente cuando cambian los datos

### 3.2 Estructura de UI en nuestro proyecto

**Composable básico:**
```kotlin
@Composable
fun Saludo(nombre: String) {
    Text(
        text = "Hola, $nombre!",
        fontSize = 20.sp,
        color = Color.Blue
    )
}
```

**Layout containers:**
```kotlin
@Composable
fun FormularioServicioScreen() {
    Column(  // Organiza elementos verticalmente
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Formulario de Servicio")
        
        OutlinedTextField(
            value = estado.nombreCliente,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre") }
        )
        
        Button(
            onClick = { viewModel.onEnviarFormulario() }
        ) {
            Text("Enviar")
        }
    }
}
```

**State Management:**
```kotlin
@Composable
fun ContadorExample() {
    var contador by remember { mutableStateOf(0) }  // Estado local
    
    Column {
        Text("Contador: $contador")
        Button(onClick = { contador++ }) {
            Text("Incrementar")
        }
    }
}
```

---

## 4. ¿Qué es MVVM y cómo se implementa?

### 4.1 Arquitectura Model-View-ViewModel

**MVVM** separa la aplicación en tres capas:

```
┌─────────────────┐    observa    ┌─────────────────┐
│      VIEW       │ ────────────> │   VIEWMODEL     │
│   (Composables) │               │   (Lógica UI)   │
└─────────────────┘               └─────────────────┘
                                            │
                                    accede datos
                                            ↓
                                  ┌─────────────────┐
                                  │      MODEL      │
                                  │ (Repository/DB) │
                                  └─────────────────┘
```

### 4.2 Implementación en nuestro proyecto

**MODEL (Datos):**
```kotlin
// Entity (Tabla de base de datos)
@Entity(tableName = "formulario_servicio")
data class FormularioServicioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombreCliente: String,
    val correoCliente: String,
    val estadoSolicitud: String
)

// Repository (Abstracción de datos)
class FormularioServicioRepository(private val dao: FormularioServicioDao) {
    suspend fun guardarFormulario(entity: FormularioServicioEntity) = dao.insertFormulario(entity)
    fun obtenerFormularios() = dao.getFormularios()
}
```

**VIEWMODEL (Lógica de negocio):**
```kotlin
class FormularioServicioViewModel(
    private val repository: FormularioServicioRepository
) : ViewModel() {
    
    // Estado privado (mutable)
    private val _estado = MutableStateFlow(FormularioServicioState())
    
    // Estado público (inmutable)
    val estado: StateFlow<FormularioServicioState> = _estado.asStateFlow()
    
    // Función para manejar acciones de UI
    fun onNombreChange(valor: String) {
        _estado.update { currentState ->
            currentState.copy(
                nombreCliente = valor,
                errores = currentState.errores.copy(nombreCliente = null)
            )
        }
    }
    
    // Función para guardar datos
    fun onEnviarFormulario() {
        viewModelScope.launch {
            try {
                val entity = FormularioServicioEntity(/* datos del estado */)
                repository.guardarFormulario(entity)
                _estado.update { it.copy(mensajeExito = "Guardado exitosamente") }
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = e.message) }
            }
        }
    }
}
```

**VIEW (UI):**
```kotlin
@Composable
fun FormularioServicioScreen(
    viewModel: FormularioServicioViewModel = viewModel()
) {
    // Observar estado del ViewModel
    val estado by viewModel.estado.collectAsState()
    
    Column {
        OutlinedTextField(
            value = estado.nombreCliente,
            onValueChange = viewModel::onNombreChange,  // Enviar acción al ViewModel
            label = { Text("Nombre") },
            isError = estado.errores.nombreCliente != null
        )
        
        // Mostrar mensaje de error si existe
        estado.errores.nombreCliente?.let { error ->
            Text(text = error, color = Color.Red)
        }
    }
}
```

---

## 5. Explicación de Carpetas del Proyecto

### 5.1 Estructura de Paquetes

```
📁 appserviciotecnico/
├── 📁 model/                   # CAPA DE DATOS
│   ├── 📁 data/               # Acceso a datos
│   │   ├── AppDatabase.kt     # Configuración Room
│   │   ├── FormularioServicioDao2.kt  # Queries SQL para formularios
│   │   └── SolicitudDao.kt    # Queries SQL para solicitudes
│   ├── 📁 entities/           # Entidades de base de datos (Tablas)
│   │   ├── FormularioServicioEntity.kt  # Tabla formularios principales
│   │   ├── FormularioServicioEntity2.kt # Tabla formularios v2
│   │   └── SolicitudEntity.kt # Tabla solicitudes/citas
│   ├── 📁 repository/         # Abstracción de datos
│   │   ├── FormularioServicioRepository.kt # Repository formularios
│   │   └── SolicitudRepository.kt # Repository solicitudes
│   └── 📁 domain/             # Lógica de negocio y modelos UI
│       ├── FormularioServicioUIState.kt # Estados UI formularios
│       ├── FormularioServicioErrores.kt # Errores de validación
│       ├── LoginUIState.kt    # Estado UI del login
│       ├── Servicio.kt        # Modelo de servicios técnicos
│       └── Solicitud.kt       # Modelo de solicitudes
├── 📁 viewmodel/              # CAPA DE PRESENTACIÓN (MVVM)
│   ├── FormularioServicioViewModel.kt      # ViewModel formularios
│   ├── FormularioServicioViewModelFactory.kt # Factory formularios
│   ├── FormularioServicioState.kt          # Estado formularios
│   ├── HomeViewModel.kt                    # ViewModel pantalla principal
│   ├── HomeViewModelFactory.kt             # Factory home
│   ├── HomeState.kt                        # Estado home
│   ├── EstadoSolicitudesViewModel.kt       # ViewModel lista solicitudes
│   ├── EstadoSolicitudesViewModelFactory.kt # Factory solicitudes
│   ├── EditarSolicitudViewModel.kt         # ViewModel editar/eliminar
│   └── EditarSolicitudViewModelFactory.kt  # Factory editar
├── 📁 ui/                     # CAPA DE INTERFAZ DE USUARIO
│   ├── 📁 screen/            # Pantallas principales (Screens)
│   │   ├── StartScreen.kt     # Splash screen con logo
│   │   ├── LoginScreen.kt     # Pantalla de inicio de sesión
│   │   ├── HomeScreen.kt      # Dashboard principal
│   │   ├── FormularioServicioScreen.kt # Formulario cotización
│   │   ├── CatalogoServiciosScreen.kt  # Catálogo de servicios
│   │   ├── AgendarServicioScreen.kt    # Agendar cita
│   │   ├── EstadoSolicitudesScreen.kt  # Ver mis solicitudes
│   │   └── EditarSolicitudScreen.kt    # Editar/eliminar solicitudes
│   ├── 📁 components/        # Componentes reutilizables
│   │   ├── InputText.kt       # Campo de texto con validación
│   │   ├── SolicitudCard.kt   # Card para mostrar solicitudes
│   │   ├── CategoriaCard.kt   # Card para categorías de servicios
│   │   ├── QuickAccessCard.kt # Cards de acceso rápido
│   │   ├── SummaryCard.kt     # Cards de resumen/estadísticas
│   │   ├── HomeHeader.kt      # Header del home con saludo
│   │   ├── ContactButtons.kt  # Botones de contacto
│   │   ├── PromoBanner.kt     # Banner promocional
│   │   └── AnimatedComponents.kt # Componentes con animaciones
│   └── 📁 theme/            # Configuración de tema y colores
│       └── theme.kt          # Colores, tipografía, formas
├── 📁 navigation/            # SISTEMA DE NAVEGACIÓN
│   ├── AppNav.kt             # Configuración principal de rutas
│   └── Routes.kt             # Definición de rutas constantes
├── 📁 utils/                # UTILIDADES Y HELPERS
│   └── NativeResourcesHelper.kt # Acceso a recursos nativos (cámara, vibración, teléfono, calendario)
├── MainActivity.kt           # ACTIVIDAD PRINCIPAL
└── 📁 test/                 # TESTS UNITARIOS
    ├── FormularioValidacionTest.kt # Tests de validaciones
    ├── EstadoSolicitudTest.kt      # Tests de estados
    └── HorarioLaboralTest.kt       # Tests de horarios
```

### 5.2 Responsabilidades detalladas de cada carpeta

#### 🗃️ MODEL (Capa de Datos)
**model/data/**: 
- `AppDatabase.kt` - Configuración principal de Room Database, define versiones y entidades
- `FormularioServicioDao2.kt` - Interface con queries SQL para operaciones CRUD de formularios
- `SolicitudDao.kt` - Interface con queries SQL para operaciones CRUD de solicitudes/citas

**model/entities/** (Representan tablas de SQLite):
- `FormularioServicioEntity.kt` - Tabla principal de formularios de cotización
- `FormularioServicioEntity2.kt` - Versión actualizada de la tabla de formularios
- `SolicitudEntity.kt` - Tabla de solicitudes de servicio y citas agendadas

**model/repository/** (Abstracción de acceso a datos):
- `FormularioServicioRepository.kt` - Gestiona operaciones de formularios, abstrae el DAO
- `SolicitudRepository.kt` - Gestiona operaciones de solicitudes, abstrae el DAO

**model/domain/** (Lógica de negocio y modelos):
- `FormularioServicioUIState.kt` - Define estados de UI para formularios
- `FormularioServicioErrores.kt` - Define tipos de errores de validación
- `LoginUIState.kt` - Estados específicos para la pantalla de login
- `Servicio.kt` - Modelo de datos para servicios técnicos (PS4/PS5)
- `Solicitud.kt` - Modelo de datos para solicitudes de servicio

#### 🧠 VIEWMODEL (Capa de Presentación)
**Patrón ViewModel + Factory**:
- `*ViewModel.kt` - Contiene lógica de presentación, gestiona estado, coordina con Repository
- `*ViewModelFactory.kt` - Factory pattern para crear ViewModels con dependencias
- `*State.kt` - Data classes inmutables que representan el estado de cada pantalla

Ejemplos específicos:
- `FormularioServicioViewModel.kt` - Maneja lógica del formulario, validaciones, envío
- `HomeViewModel.kt` - Gestiona dashboard principal, estadísticas, accesos rápidos
- `EstadoSolicitudesViewModel.kt` - Lista y filtra solicitudes del usuario
- `EditarSolicitudViewModel.kt` - Permite editar o eliminar solicitudes existentes

#### 🎨 UI (Capa de Interfaz)
**ui/screen/** (Pantallas completas):
- `StartScreen.kt` - Splash screen inicial con logo de la empresa
- `LoginScreen.kt` - Autenticación de usuarios (simulada)
- `HomeScreen.kt` - Dashboard principal con resumen y accesos rápidos
- `FormularioServicioScreen.kt` - Formulario para solicitar cotización de reparación
- `CatalogoServiciosScreen.kt` - Lista de servicios disponibles (diagnóstico, reparación, etc.)
- `AgendarServicioScreen.kt` - Calendario para agendar citas de servicio técnico
- `EstadoSolicitudesScreen.kt` - Lista de solicitudes del usuario con estados
- `EditarSolicitudScreen.kt` - Editar o cancelar solicitudes existentes

**ui/components/** (Componentes reutilizables):
- `InputText.kt` - Campo de texto con validación visual y mensajes de error
- `SolicitudCard.kt` - Card que muestra información de cada solicitud (estado, fecha, etc.)
- `CategoriaCard.kt` - Card para mostrar categorías de servicios con iconos
- `QuickAccessCard.kt` - Cards de acceso rápido en el dashboard
- `SummaryCard.kt` - Cards de resumen con estadísticas (total solicitudes, pendientes, etc.)
- `HomeHeader.kt` - Header personalizado del home con saludo al usuario
- `ContactButtons.kt` - Botones para contactar (teléfono, WhatsApp, etc.)
- `PromoBanner.kt` - Banner promocional rotativo
- `AnimatedComponents.kt` - Componentes con animaciones personalizadas

**ui/theme/**:
- `theme.kt` - Configuración completa de Material Design 3 (colores, tipografía, formas)

#### 🧭 NAVIGATION (Sistema de Navegación)
- `AppNav.kt` - NavHost principal con todas las rutas y configuración de drawer
- `Routes.kt` - Constantes para todas las rutas de navegación

#### 🛠️ UTILS (Utilidades)
- `NativeResourcesHelper.kt` - Funciones para acceder a recursos nativos del dispositivo:
  - Marcador telefónico
  - Cámara para fotos
  - Vibración del dispositivo
  - Calendario para agregar citas

#### ✅ TESTING
- `FormularioValidacionTest.kt` - Tests unitarios para validaciones de formularios
- `EstadoSolicitudTest.kt` - Tests para transiciones de estados de solicitudes  
- `HorarioLaboralTest.kt` - Tests para validación de horarios de servicio

---

## 6. Flujos de Datos Completos

### 6.1 Flujo: Usuario llena formulario → Se guarda en BD

```
1. 👤 Usuario escribe en campo de texto
   ↓
2. 🎯 UI llama viewModel.onNombreChange("Juan")
   ↓
3. 🧠 ViewModel actualiza estado:
   _estado.update { it.copy(nombreCliente = "Juan") }
   ↓
4. 🎨 UI observa cambio de estado y re-renderiza
   val estado by viewModel.estado.collectAsState()
   ↓
5. 👤 Usuario presiona "Enviar"
   ↓
6. 🎯 UI llama viewModel.onEnviarFormulario()
   ↓
7. 🧠 ViewModel valida datos y crea Entity:
   val entity = FormularioServicioEntity(nombreCliente = "Juan", ...)
   ↓
8. 🧠 ViewModel llama repository.guardarFormulario(entity)
   ↓
9. 📊 Repository llama dao.insertFormulario(entity)
   ↓
10. 💾 Room ejecuta SQL: INSERT INTO formulario_servicio VALUES(...)
    ↓
11. ✅ Datos guardados en SQLite
    ↓
12. 🧠 ViewModel actualiza estado con mensaje de éxito
    ↓
13. 🎨 UI muestra mensaje de confirmación
```

### 6.2 Flujo: Usuario consulta solicitudes

```
1. 👤 Usuario navega a "Mis Solicitudes"
   ↓
2. 🎨 EstadoSolicitudesScreen se inicializa
   ↓
3. 🧠 ViewModel se crea y ejecuta init:
   init { cargarSolicitudes() }
   ↓
4. 🧠 ViewModel llama repository.obtenerFormularios()
   ↓
5. 📊 Repository llama dao.getFormularios()
   ↓
6. 💾 Room ejecuta: SELECT * FROM formulario_servicio ORDER BY id DESC
   ↓
7. 📊 DAO retorna Flow<List<FormularioServicioEntity>>
   ↓
8. 🧠 ViewModel mapea entities a UI models
   ↓
9. 🎨 UI observa Flow y muestra lista:
   LazyColumn { items(solicitudes) { ... } }
```

---

## 7. Navegación entre Pantallas

### 7.1 Sistema de Navegación

**Configuración básica:**
```kotlin
@Composable
fun AppNav() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Routes.Start  // Pantalla inicial
    ) {
        // Definir rutas y pantallas
        composable(Routes.Start) {
            StartScreen(onNavigateToLogin = {
                navController.navigate(Routes.Login)
            })
        }
        
        composable(Routes.Login) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Routes.Home) {
                    popUpTo(Routes.Login) { inclusive = true }  // Limpiar back stack
                }
            })
        }
    }
}
```

**Routes.kt - Definición de rutas:**
```kotlin
object Routes {
    const val Start = "start"
    const val Login = "login"
    const val Home = "home"
    const val Form = "form"
    const val Catalog = "catalog"
    const val Agendar = "agendar/{servicioId}"  // Con parámetros
    const val Solicitudes = "solicitudes"
}
```

**Navegación con argumentos:**
```kotlin
// Navegar pasando parámetro
navController.navigate("agendar/diagnostico-general")

// Recibir parámetro en destino
composable(
    route = "agendar/{servicioId}",
    arguments = listOf(navArgument("servicioId") { type = NavType.StringType })
) { backStackEntry ->
    val servicioId = backStackEntry.arguments?.getString("servicioId")
    AgendarServicioScreen(servicioId = servicioId)
}
```

### 7.2 Drawer Navigation (Menú lateral)

```kotlin
@Composable
fun DrawerScaffold() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", style = MaterialTheme.typography.titleMedium)
                
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = currentRoute == Routes.Home,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Routes.Home)
                    }
                )
            }
        }
    ) {
        // Contenido principal
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("App Servicio Técnico") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { /* contenido */ }
    }
}
```

---

## 8. Sistema de Validaciones

### 8.1 Validaciones en tiempo real

```kotlin
// FormularioServicioState.kt
data class FormularioServicioState(
    val nombreCliente: String = "",
    val correoCliente: String = "",
    val errores: FormularioServicioErrores = FormularioServicioErrores(),
    val enviando: Boolean = false
)

// FormularioServicioErrores.kt
data class FormularioServicioErrores(
    val nombreCliente: String? = null,
    val emailCliente: String? = null,
    val telefonoCliente: String? = null
) {
    fun hasErrors(): Boolean = listOf(nombreCliente, emailCliente, telefonoCliente).any { it != null }
}
```

**Validación en ViewModel:**
```kotlin
private fun validarFormulario(estado: FormularioServicioState): FormularioServicioErrores {
    return FormularioServicioErrores(
        nombreCliente = when {
            estado.nombreCliente.isBlank() -> "El nombre es requerido"
            estado.nombreCliente.length < 2 -> "Nombre muy corto"
            else -> null
        },
        emailCliente = when {
            estado.correoCliente.isBlank() -> "El correo es requerido"
            !Patterns.EMAIL_ADDRESS.matcher(estado.correoCliente).matches() -> "Correo inválido"
            else -> null
        }
    )
}
```

**Mostrar errores en UI:**
```kotlin
@Composable
fun InputText(
    valor: String,
    error: String?,
    label: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = {
            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}
```

---

## 9. Configuración de Dependencias (Gradle)

### 9.1 build.gradle.kts (Module: app)

```kotlin
dependencies {
    // ✅ COMPOSE BOM - Maneja versiones de Compose automáticamente
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    
    // ✅ COMPOSE CORE
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics") 
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // ✅ ACTIVITY COMPOSE - Para usar setContent en MainActivity
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // ✅ NAVIGATION COMPOSE - Para navegar entre pantallas
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // ✅ LIFECYCLE - Para ViewModels y StateFlow
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // ✅ ROOM DATABASE - Para persistencia local
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")  // Para Coroutines
    kapt("androidx.room:room-compiler:2.6.1")      // Procesador de anotaciones
    
    // ✅ TESTING
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

### 9.2 Configuración de plugins

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")  // ⚠️ Necesario para Room
}

android {
    namespace = "appserviciotecnico"
    compileSdk = 36
    
    defaultConfig {
        applicationId = "com.appserviciotecnico"
        minSdk = 24    // Android 7.0+
        targetSdk = 36 // Android más reciente
        versionCode = 1
        versionName = "1.0"
    }
    
    // ✅ Habilitar Compose
    buildFeatures {
        compose = true
    }
    
    // ✅ Configuración Java 11 (requerido por Compose)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}
```

---

## 10. Conexión con SQLite (Room Database)

### 10.1 Configuración completa de Room

**Paso 1: Definir Entity (Tabla)**
```kotlin
@Entity(tableName = "formulario_servicio")
data class FormularioServicioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "nombre_cliente")
    val nombreCliente: String,
    
    @ColumnInfo(name = "correo_cliente")  
    val correoCliente: String,
    
    @ColumnInfo(name = "fecha_solicitud")
    val fechaSolicitud: String,
    
    @ColumnInfo(name = "estado_solicitud")
    val estadoSolicitud: String = "Pendiente"
)
```

**Paso 2: Crear DAO (Data Access Object)**
```kotlin
@Dao
interface FormularioServicioDao {
    // ➕ CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormulario(formulario: FormularioServicioEntity): Long
    
    // 📖 READ
    @Query("SELECT * FROM formulario_servicio ORDER BY id DESC")
    fun getFormularios(): Flow<List<FormularioServicioEntity>>
    
    @Query("SELECT * FROM formulario_servicio WHERE id = :id")
    suspend fun getFormularioById(id: Long): FormularioServicioEntity?
    
    // ✏️ UPDATE  
    @Query("UPDATE formulario_servicio SET estado_solicitud = :nuevoEstado WHERE id = :id")
    suspend fun updateEstado(id: Long, nuevoEstado: String)
    
    // ❌ DELETE
    @Query("DELETE FROM formulario_servicio WHERE id = :id")
    suspend fun deleteFormulario(id: Long)
    
    @Query("DELETE FROM formulario_servicio")
    suspend fun deleteAll()
    
    // 📊 CONSULTAS PERSONALIZADAS
    @Query("SELECT * FROM formulario_servicio WHERE estado_solicitud = :estado")
    fun getFormulariosByEstado(estado: String): Flow<List<FormularioServicioEntity>>
    
    @Query("SELECT COUNT(*) FROM formulario_servicio WHERE estado_solicitud = 'Pendiente'")
    suspend fun countPendientes(): Int
}
```

**Paso 3: Configurar Database**
```kotlin
@Database(
    entities = [FormularioServicioEntity::class, SolicitudEntity::class],
    version = 2,  // Incrementar cuando cambies esquema
    exportSchema = false
)
@TypeConverters(Converters::class)  // Para tipos complejos
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun formularioServicioDao(): FormularioServicioDao
    abstract fun solicitudDao(): SolicitudDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            // Patrón Singleton para evitar múltiples instancias
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_servicio_tecnico_db"
                )
                .fallbackToDestructiveMigration()  // ⚠️ Recrear DB si hay cambios
                .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}
```

**Paso 4: Crear Repository**
```kotlin
class FormularioServicioRepository(private val dao: FormularioServicioDao) {
    
    // Obtener todos los formularios (reactivo)
    fun obtenerFormularios(): Flow<List<FormularioServicioEntity>> = dao.getFormularios()
    
    // Guardar formulario
    suspend fun guardarFormulario(formulario: FormularioServicioEntity): Long {
        return dao.insertFormulario(formulario)
    }
    
    // Actualizar estado
    suspend fun actualizarEstado(id: Long, nuevoEstado: String) {
        dao.updateEstado(id, nuevoEstado)
    }
    
    // Eliminar formulario
    suspend fun eliminarFormulario(id: Long) {
        dao.deleteFormulario(id)
    }
}
```

### 10.2 Uso en ViewModel

```kotlin
class FormularioServicioViewModel(
    private val repository: FormularioServicioRepository
) : ViewModel() {
    
    private val _solicitudes = MutableStateFlow<List<FormularioServicioEntity>>(emptyList())
    val solicitudes = _solicitudes.asStateFlow()
    
    init {
        // Observar cambios en la base de datos
        viewModelScope.launch {
            repository.obtenerFormularios().collect { formularios ->
                _solicitudes.value = formularios
            }
        }
    }
    
    fun guardarFormulario(datos: FormularioServicioState) {
        viewModelScope.launch {
            try {
                val entity = FormularioServicioEntity(
                    nombreCliente = datos.nombreCliente,
                    correoCliente = datos.correoCliente,
                    fechaSolicitud = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
                    estadoSolicitud = "Pendiente"
                )
                
                val id = repository.guardarFormulario(entity)
                Log.d("Database", "Formulario guardado con ID: $id")
                
            } catch (e: Exception) {
                Log.e("Database", "Error al guardar: ${e.message}")
            }
        }
    }
}
```

---

## 11. Componentes Reutilizables Explicados

### 11.1 ¿Por qué usar componentes reutilizables?

En Jetpack Compose, **la reutilización de componentes** es fundamental para:
- **DRY Principle** (Don't Repeat Yourself) - No repetir código
- **Consistencia visual** en toda la app
- **Mantenibilidad** - cambiar en un lugar afecta toda la app
- **Testing más fácil** - probar componentes aislados

### 11.2 Componentes implementados en nuestro proyecto

#### 📝 InputText.kt - Campo de texto con validación

**Propósito**: Campo de texto estándar con validación visual automática

```kotlin
@Composable
fun InputText(
    valor: String,                    // Texto actual
    error: String?,                   // Mensaje de error (null si es válido)
    label: String,                    // Etiqueta del campo
    onChange: (String) -> Unit,       // Callback cuando cambia el texto
    modifier: Modifier = Modifier,
    maxLines: Int = 1                 // Líneas máximas (1 = single line)
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        isError = error != null,      // ✅ Automático: rojo si hay error
        supportingText = {
            error?.let {              // ✅ Mostrar mensaje de error
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        maxLines = maxLines,
        singleLine = maxLines == 1
    )
}
```

**Uso en FormularioServicioScreen:**
```kotlin
InputText(
    valor = estado.nombreCliente,           // Desde ViewModel
    error = estado.errores.nombreCliente,   // Error específico del campo
    label = "Nombre completo",
    onChange = viewModel::onNombreChange    // Función del ViewModel
)
```

**Beneficios:**
- ✅ Validación visual automática (rojo + mensaje)
- ✅ Consistencia en todos los formularios
- ✅ Menos código repetitivo
- ✅ Fácil personalización con parámetros

#### 🃏 SolicitudCard.kt - Card para mostrar solicitudes

**Propósito**: Mostrar información de una solicitud con acciones (ver, editar, eliminar)

```kotlin
@Composable
fun SolicitudCard(
    solicitud: Solicitud,                    // Datos de la solicitud
    onVerDetalles: (Solicitud) -> Unit = {}, // Callback ver detalles
    onEditar: ((Solicitud) -> Unit)? = null, // Callback editar (opcional)
    onEliminar: ((Solicitud) -> Unit)? = null // Callback eliminar (opcional)
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            // 📋 Header: Servicio + Estado
            Row {
                Text(
                    text = solicitud.servicio,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                
                // 🎨 Badge de estado con color dinámico
                Badge(
                    containerColor = when (solicitud.estado) {
                        EstadoSolicitud.PENDIENTE -> Color.Orange
                        EstadoSolicitud.EN_PROCESO -> Color.Blue  
                        EstadoSolicitud.COMPLETADO -> Color.Green
                        EstadoSolicitud.CANCELADO -> Color.Red
                    }
                ) {
                    Text(solicitud.estado.displayName)
                }
            }
            
            // 📅 Fecha y detalles
            Text("Fecha: ${formatearFecha(solicitud.fechaCreacion)}")
            
            // ⚡ Botones de acción dinámicos
            Row {
                TextButton(onClick = { onVerDetalles(solicitud) }) {
                    Text("Ver Detalles")
                }
                
                // Solo mostrar si el callback existe
                onEditar?.let { callback ->
                    IconButton(onClick = { callback(solicitud) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                }
                
                onEliminar?.let { callback ->
                    IconButton(onClick = { callback(solicitud) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")  
                    }
                }
            }
        }
    }
}
```

**Uso en EstadoSolicitudesScreen:**
```kotlin
LazyColumn {
    items(solicitudes) { solicitud ->
        SolicitudCard(
            solicitud = solicitud,
            onVerDetalles = { /* navegar a detalles */ },
            onEditar = { viewModel.editarSolicitud(it) },
            onEliminar = { viewModel.eliminarSolicitud(it) }
        )
    }
}
```

#### 🎯 QuickAccessCard.kt - Accesos rápidos del dashboard

**Propósito**: Cards clicables para acciones rápidas en el home

```kotlin
@Composable  
fun QuickAccessCard(
    titulo: String,
    descripcion: String,
    icon: ImageVector,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },               // ✅ Clicable
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🎨 Ícono con fondo circular
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(12.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 📝 Texto informativo
            Column {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
```

#### 📊 SummaryCard.kt - Cards de estadísticas

**Propósito**: Mostrar números/estadísticas importantes

```kotlin
@Composable
fun SummaryCard(
    titulo: String,
    valor: String,                    // Ej: "12", "$50.000"
    subtitulo: String? = null,        // Ej: "solicitudes pendientes"  
    icon: ImageVector,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 🔢 Número/valor principal (grande)
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineLarge,
                color = color,
                fontWeight = FontWeight.Bold
            )
            
            // 📝 Título
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            
            // 📝 Subtítulo opcional
            subtitulo?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
```

### 11.3 Patrón de composición en HomeScreen

**Ejemplo de cómo se combinan todos los componentes:**

```kotlin
@Composable
fun HomeScreen() {
    LazyColumn {
        // 👋 Header personalizado
        item {
            HomeHeader(nombreUsuario = "Juan Pérez")
        }
        
        // 📊 Resumen estadísticas
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SummaryCard(
                    titulo = "Solicitudes",
                    valor = "5",
                    subtitulo = "pendientes",
                    icon = Icons.Default.Assignment,
                    modifier = Modifier.weight(1f)
                )
                
                SummaryCard(
                    titulo = "Reparaciones",
                    valor = "2", 
                    subtitulo = "en proceso",
                    icon = Icons.Default.Build,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // 🎯 Accesos rápidos
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickAccessCard(
                    titulo = "Solicitar Cotización",
                    descripcion = "Describe el problema de tu consola",
                    icon = Icons.Default.RequestQuote,
                    onClick = { /* navegar a formulario */ }
                )
                
                QuickAccessCard(
                    titulo = "Ver Servicios",
                    descripcion = "Explora nuestros servicios disponibles", 
                    icon = Icons.Default.Build,
                    onClick = { /* navegar a catálogo */ }
                )
            }
        }
        
        // 🃏 Lista de solicitudes recientes
        items(solicitudesRecientes) { solicitud ->
            SolicitudCard(
                solicitud = solicitud,
                onVerDetalles = { /* acción */ }
            )
        }
    }
}
```

### 11.4 Beneficios de esta arquitectura de componentes

✅ **Reutilización**: `SolicitudCard` se usa en HomeScreen, EstadoSolicitudesScreen, etc.
✅ **Consistencia**: Todos los cards tienen el mismo estilo y comportamiento  
✅ **Mantenibilidad**: Cambiar `InputText` afecta todos los formularios automáticamente
✅ **Testing**: Cada componente se puede testear independientemente
✅ **Flexibilidad**: Parámetros opcionales permiten personalización sin duplicar código

**Ejemplo de reutilización:**
```kotlin
// En FormularioServicioScreen
InputText(
    valor = estado.nombreCliente,
    error = estado.errores.nombreCliente, 
    label = "Nombre completo",
    onChange = viewModel::onNombreChange
)

// En LoginScreen (mismo componente, diferentes datos)
InputText(
    valor = estado.email,
    error = estado.errores.email,
    label = "Correo electrónico", 
    onChange = viewModel::onEmailChange
)

// En EditarSolicitudScreen (mismo componente, caso multilinea)
InputText(
    valor = estado.motivo,
    error = null,
    label = "Motivo de la edición",
    onChange = viewModel::onMotivoChange,
    maxLines = 3  // 📝 Personalización sin duplicar código
)
```

---

## 11.5 Análisis Específico: FormularioServicioScreen.kt

Vamos a analizar **línea por línea** tu `FormularioServicioScreen.kt` para entender cómo funciona todo:

### 🎯 Estructura principal del Composable

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioServicioScreen() {
    // 🏭 CONFIGURACIÓN INICIAL
    val context = LocalContext.current
    val viewModel: FormularioServicioViewModel = viewModel(
        factory = FormularioServicioViewModelFactory(context.applicationContext as Application)
    )
    val estado by viewModel.estado.collectAsState()
```

**¿Qué está pasando aquí?**

1. **`@OptIn(ExperimentalMaterial3Api::class)`** - Permite usar APIs experimentales de Material 3
2. **`LocalContext.current`** - Obtiene el contexto de Android (necesario para ViewModel)
3. **`viewModel(factory = ...)`** - Crea el ViewModel usando Factory pattern (porque necesita Application como dependencia)
4. **`estado by viewModel.estado.collectAsState()`** - **CLAVE**: Observa cambios de estado reactivamente

### 📱 Lógica de Estado Local (remember)

```kotlin
// Lista de tipos de consolas
val tiposConsola = listOf("PS4", "PS5")
var expandedConsola by remember { mutableStateOf(false) }

// Lista de modelos PS4/PS5
val modelosPS4 = listOf("PS4 Original", "PS4 Slim", "PS4 Pro")
val modelosPS5 = listOf("PS5 Standard", "PS5 Digital Edition")
var expandedModelo by remember { mutableStateOf(false) }
```

**¿Por qué usar `remember`?**
- **`remember`** guarda el estado local entre recomposiciones
- **`mutableStateOf`** hace que el estado sea reactivo
- **`by`** delegate permite usar `expandedConsola` directamente en vez de `expandedConsola.value`

### 🏗️ Layout principal con scroll

```kotlin
Box(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())  // ✅ Scroll automático
            .padding(16.dp)
            .padding(bottom = 16.dp),               // ✅ Padding extra al final
        verticalArrangement = Arrangement.spacedBy(12.dp)  // ✅ Espaciado entre elementos
    ) {
        // ... contenido
    }
}
```

**Layout strategy:**
- **`Box`** como contenedor principal
- **`Column`** organiza elementos verticalmente
- **`verticalScroll`** permite scroll cuando el contenido es más grande que la pantalla
- **`Arrangement.spacedBy(12.dp)`** espacio uniforme entre todos los elementos

### 🎨 Secciones organizadas con títulos

```kotlin
// 👤 Datos del cliente
Text(
    text = "Datos del cliente",
    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
)

InputText(
    valor = estado.nombreCliente,           // 📊 Desde ViewModel
    error = estado.errores.nombreCliente,   // ❌ Error específico
    label = "Nombre completo",
    onChange = viewModel::onNombreChange    // 🔄 Callback al ViewModel
)
```

**Patrón de organización:**
1. **Título de sección** con tipografía consistente
2. **Componentes relacionados** agrupados
3. **Estado reactivo** (valor desde ViewModel)
4. **Validación visual** (error desde ViewModel)
5. **Comunicación unidireccional** (onChange hacia ViewModel)

### 🎮 Dropdown dinámico con lógica

```kotlin
// ComboBox Tipo de Consola
ExposedDropdownMenuBox(
    expanded = expandedConsola,                          // 📊 Estado local
    onExpandedChange = { expandedConsola = !expandedConsola }
) {
    OutlinedTextField(
        value = estado.tipoConsola,                      // 📊 Estado ViewModel  
        onValueChange = {},                              // ✅ Solo lectura
        readOnly = true,
        label = { Text("Tipo de consola") },
        modifier = Modifier.menuAnchor().fillMaxWidth(), // 🎯 menuAnchor() requerido para dropdown
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedConsola)
        },
        isError = estado.errores.tipoConsola != null,    // ❌ Validación visual
        supportingText = {                               // 💬 Mensaje de error
            estado.errores.tipoConsola?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    )

    ExposedDropdownMenu(
        expanded = expandedConsola,
        onDismissRequest = { expandedConsola = false }   // ✅ Cerrar al tocar fuera
    ) {
        tiposConsola.forEach { tipo ->                   // 🔄 Iterar opciones
            DropdownMenuItem(
                text = { Text(tipo) },
                onClick = {
                    viewModel.onTipoConsolaChange(tipo)  // 📤 Enviar al ViewModel
                    viewModel.onModeloConsolaChange("") // 🧹 Limpiar modelo dependiente
                    expandedConsola = false              // ✅ Cerrar dropdown
                }
            )
        }
    }
}
```

**Lógica del dropdown:**
1. **Estado mixto**: `expandedConsola` (local) + `estado.tipoConsola` (ViewModel)
2. **Dependencia entre dropdowns**: Cambiar PS4→PS5 limpia el modelo
3. **Validación integrada**: Misma lógica que InputText
4. **UX pulida**: Auto-cerrar, indicador visual, touch outside

### ⚙️ Dropdown dependiente dinámico

```kotlin
// ComboBox Modelo de Consola
val modelosDisponibles = when (estado.tipoConsola) {     // 🧠 Lógica condicional
    "PS4" -> modelosPS4
    "PS5" -> modelosPS5  
    else -> emptyList()
}

ExposedDropdownMenuBox(
    expanded = expandedModelo,
    onExpandedChange = { 
        expandedModelo = !expandedModelo && modelosDisponibles.isNotEmpty() // ✅ Solo si hay opciones
    }
) {
    OutlinedTextField(
        enabled = modelosDisponibles.isNotEmpty(),       // ✅ Deshabilitar si no hay opciones
        // ... resto igual
    )
    
    if (modelosDisponibles.isNotEmpty()) {              // ✅ Solo mostrar menú si hay opciones
        ExposedDropdownMenu(/* ... */) {
            modelosDisponibles.forEach { modelo ->      // 🔄 Lista dinámica
                // ...
            }
        }
    }
}
```

**Lógica de dependencia:**
- **Modelos disponibles** calculados según tipo de consola
- **Habilitación condicional** del dropdown
- **Lista dinámica** que cambia automáticamente

### 📷 Integración con recursos nativos

```kotlin
// 📷 Botón para adjuntar foto
OutlinedButton(
    onClick = {
        NativeResourcesHelper.vibrar(context)           // 📳 Vibración feedback
        NativeResourcesHelper.abrirCamara(context)      // 📸 Abrir cámara
    },
    modifier = Modifier.fillMaxWidth()
) {
    Text("📷 Adjuntar Foto de la Consola")
}
```

**Recursos nativos integrados:**
- **Vibración** como feedback háptico  
- **Cámara** para adjuntar fotos
- **Contexto Android** pasado a helpers

### ✨ Animaciones con estado

```kotlin
// ✅ Mensaje de éxito con animación
AnimatedVisibility(
    visible = estado.mensajeExito != null,               // 👀 Visible cuando hay mensaje
    enter = slideInVertically(                           // 🎬 Animación de entrada
        initialOffsetY = { -it / 2 },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    ) + fadeIn(animationSpec = tween(300)),
    exit = slideOutVertically(                           // 🎬 Animación de salida
        targetOffsetY = { -it },
        animationSpec = tween(200)
    ) + fadeOut(animationSpec = tween(200))
) {
    estado.mensajeExito?.let { mensaje ->               // 💬 Mostrar mensaje si existe
        Column {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
```

**Animación reactiva:**
- **Trigger**: `estado.mensajeExito != null`
- **Entrada**: Slide down + fade in con bounce
- **Salida**: Slide up + fade out  
- **Automática**: Se activa cuando ViewModel cambia el estado

### 🚀 Botón con estado de loading

```kotlin
// 🔘 Botón enviar
Button(
    onClick = {
        viewModel.onEnviarFormulario()                   // 📤 Acción ViewModel
        NativeResourcesHelper.vibrarExito(context)       // 📳 Feedback háptico
    },
    modifier = Modifier.fillMaxWidth().height(56.dp),
    enabled = !estado.enviando,                          // ✅ Deshabilitar durante envío
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary
    )
) {
    if (estado.enviando) {                               // 🔄 Estado loading
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 2.dp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text("Enviando cotización...")
    } else {                                             // ✅ Estado normal
        Text(
            "Solicitar Cotización",
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}
```

**Estados del botón:**
1. **Normal**: "Solicitar Cotización" + habilitado
2. **Loading**: Spinner + "Enviando..." + deshabilitado  
3. **Feedback**: Vibración al presionar
4. **Reactivo**: Cambia automáticamente según `estado.enviando`

### 🏗️ Flujo completo de datos en este screen:

```
1. 👤 Usuario escribe en campo
   ↓
2. 🎯 InputText llama onChange = viewModel::onNombreChange
   ↓  
3. 🧠 ViewModel actualiza _estado.update { it.copy(nombreCliente = valor) }
   ↓
4. 🔄 estado by viewModel.estado.collectAsState() detecta cambio
   ↓
5. 🎨 Composable se re-renderiza con nuevo valor
   ↓
6. 👤 Usuario presiona "Enviar"
   ↓
7. 🎯 Button llama viewModel.onEnviarFormulario()
   ↓
8. 🧠 ViewModel valida, guarda en DB, actualiza estado con loading/éxito
   ↓
9. 🎨 UI muestra loading, luego mensaje con animación
```

**Este FormularioServicioScreen demuestra:**
✅ **MVVM pattern** perfecto (separación View-ViewModel)
✅ **Estado reactivo** con StateFlow + collectAsState  
✅ **Componentes reutilizables** (InputText)
✅ **Validación visual** integrada
✅ **Navegación condicional** (dropdowns dependientes)  
✅ **Animaciones funcionales** (entrada/salida mensajes)
✅ **Recursos nativos** (cámara, vibración)
✅ **UX pulida** (loading states, feedback háptico)
✅ **Responsive design** (scroll automático)

---

## 12. Ejemplos de Código Comentado

### 11.1 ViewModel completo comentado

```kotlin
/**
 * 🎮 ViewModel para gestionar el formulario de servicio técnico PlayStation
 * 
 * Responsabilidades:
 * - Mantener el estado de la UI
 * - Validar datos de entrada
 * - Coordinar con Repository para persistencia
 * - Manejar operaciones asíncronas
 */
class FormularioServicioViewModel(
    private val repository: FormularioServicioRepository
) : ViewModel() {

    // 🔒 Estado privado (solo este ViewModel puede modificarlo)
    private val _estado = MutableStateFlow(FormularioServicioState())
    
    // 🌐 Estado público (UI solo puede observarlo, no modificarlo)
    val estado: StateFlow<FormularioServicioState> = _estado.asStateFlow()

    /**
     * 📝 Función llamada desde UI cuando usuario escribe nombre
     * 
     * @param valor Texto ingresado por el usuario
     * 
     * Flujo:
     * 1. Actualiza el estado con el nuevo valor
     * 2. Limpia error de nombre (si había uno)
     * 3. UI se re-renderiza automáticamente por observar estado
     */
    fun onNombreChange(valor: String) {
        _estado.update { estadoActual ->
            estadoActual.copy(
                nombreCliente = valor,
                errores = estadoActual.errores.copy(nombreCliente = null)
            )
        }
    }

    /**
     * ✅ Función llamada cuando usuario presiona "Enviar"
     * 
     * Flujo:
     * 1. Valida todos los campos
     * 2. Si hay errores, actualiza estado y termina
     * 3. Si no hay errores, guarda en base de datos
     * 4. Muestra mensaje de éxito o error
     */
    fun onEnviarFormulario() {
        val estadoActual = _estado.value
        val errores = validarFormulario(estadoActual)

        // Si hay errores, mostrarlos y no continuar
        if (errores.hasErrors()) {
            _estado.update { it.copy(errores = errores) }
            return
        }

        // Indicar que se está enviando (para mostrar loading)
        _estado.update { 
            it.copy(
                enviando = true,
                errores = FormularioServicioErrores() // Limpiar errores anteriores
            ) 
        }

        // 🚀 Operación asíncrona en segundo plano
        viewModelScope.launch {
            try {
                // Crear entity para base de datos
                val entity = FormularioServicioEntity(
                    nombreCliente = estadoActual.nombreCliente,
                    correoCliente = estadoActual.correoCliente,
                    telefonoCliente = estadoActual.telefonoCliente,
                    // ... otros campos
                    fechaSolicitud = obtenerFechaActual()
                )

                // Guardar en Room Database
                val id = repository.guardarFormulario(entity)
                
                // ✅ Éxito: actualizar estado
                _estado.update {
                    it.copy(
                        enviando = false,
                        mensajeExito = "✅ Cotización solicitada exitosamente. ID: $id"
                    )
                }

                // Auto-limpiar después de 2 segundos
                kotlinx.coroutines.delay(2000)
                limpiarFormulario()

            } catch (e: Exception) {
                // ❌ Error: mostrar mensaje
                _estado.update {
                    it.copy(
                        enviando = false,
                        mensajeExito = "❌ Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * 🔍 Valida todos los campos del formulario
     * 
     * @param estado Estado actual del formulario
     * @return Objeto con errores (null si campo es válido)
     */
    private fun validarFormulario(estado: FormularioServicioState): FormularioServicioErrores {
        return FormularioServicioErrores(
            nombreCliente = when {
                estado.nombreCliente.isBlank() -> "El nombre es requerido"
                estado.nombreCliente.length < 2 -> "Nombre muy corto"
                else -> null
            },
            emailCliente = when {
                estado.correoCliente.isBlank() -> "El correo es requerido"
                !estado.correoCliente.contains("@") -> "Correo inválido"
                !estado.correoCliente.contains(".") -> "Correo inválido"
                else -> null
            }
            // ... más validaciones
        )
    }

    /**
     * 🧹 Resetea el formulario a su estado inicial
     */
    private fun limpiarFormulario() {
        _estado.update { FormularioServicioState() }
    }
    
    /**
     * 📅 Obtiene fecha actual formateada en español
     */
    private fun obtenerFechaActual(): String {
        return SimpleDateFormat(
            "dd 'de' MMMM 'de' yyyy HH:mm", 
            Locale.forLanguageTag("es-ES")
        ).format(Date())
    }
}
```

### 11.2 Composable completo comentado

```kotlin
/**
 * 📋 Pantalla del formulario de servicio técnico
 * 
 * Esta función Composable:
 * - Observa el estado del ViewModel
 * - Muestra campos de entrada con validación
 * - Maneja interacciones del usuario
 * - Se re-renderiza automáticamente cuando cambia el estado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioServicioScreen(
    modifier: Modifier = Modifier
) {
    // 🏭 Crear ViewModel con Factory pattern
    val context = LocalContext.current
    val viewModel: FormularioServicioViewModel = viewModel(
        factory = FormularioServicioViewModelFactory(
            context.applicationContext as Application
        )
    )

    // 👀 Observar estado del ViewModel (se actualiza automáticamente)
    val estado by viewModel.estado.collectAsState()

    // 📱 Layout principal con scroll
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 🏷️ Título de la pantalla
        item {
            Text(
                text = "Solicitud de Cotización",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // 📝 Campo nombre con validación en tiempo real
        item {
            OutlinedTextField(
                value = estado.nombreCliente,
                onValueChange = viewModel::onNombreChange, // Llamar función del ViewModel
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                isError = estado.errores.nombreCliente != null,
                supportingText = {
                    // Mostrar error si existe
                    estado.errores.nombreCliente?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            )
        }

        // 📧 Campo email con validación
        item {
            OutlinedTextField(
                value = estado.correoCliente,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                isError = estado.errores.emailCliente != null,
                supportingText = {
                    estado.errores.emailCliente?.let { error ->
                        Text(text = error, color = MaterialTheme.colorScheme.error)
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
        }

        // 🎮 Selector de tipo de consola
        item {
            var expandido by remember { mutableStateOf(false) }
            val tiposConsola = listOf("PlayStation 4", "PlayStation 5")

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = { expandido = it }
            ) {
                OutlinedTextField(
                    value = estado.tipoConsola,
                    onValueChange = {},  // Solo lectura
                    readOnly = true,
                    label = { Text("Tipo de consola") },
                    modifier = Modifier
                        .menuAnchor()  // Importante para dropdown
                        .fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                    },
                    isError = estado.errores.tipoConsola != null
                )

                // Menu desplegable
                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {
                    tiposConsola.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo) },
                            onClick = {
                                viewModel.onTipoConsolaChange(tipo)
                                expandido = false
                            }
                        )
                    }
                }
            }
        }

        // 📝 Área de descripción del problema
        item {
            OutlinedTextField(
                value = estado.descripcionProblema,
                onValueChange = viewModel::onDescripcionChange,
                label = { Text("Describe el problema") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                isError = estado.errores.descripcionProblema != null,
                supportingText = {
                    estado.errores.descripcionProblema?.let { error ->
                        Text(text = error, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }

        // ✅ Botón de envío
        item {
            Button(
                onClick = viewModel::onEnviarFormulario,
                modifier = Modifier.fillMaxWidth(),
                enabled = !estado.enviando, // Deshabilitar mientras se envía
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (estado.enviando) {
                    // 🔄 Indicador de carga
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Enviando...")
                } else {
                    Text("Solicitar Cotización")
                }
            }
        }

        // 💬 Mensaje de éxito/error
        estado.mensajeExito.takeIf { it.isNotBlank() }?.let { mensaje ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (mensaje.contains("✅")) {
                            Color.Green.copy(alpha = 0.1f)
                        } else {
                            Color.Red.copy(alpha = 0.1f)
                        }
                    )
                ) {
                    Text(
                        text = mensaje,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
```

---

## 12. Buenas Prácticas Aplicadas

### 12.1 Arquitectura y Organización

✅ **Separación de responsabilidades (MVVM)**
- UI solo se encarga de mostrar datos
- ViewModel maneja lógica de presentación
- Repository abstrae acceso a datos
- Entities representan estructura de datos

✅ **Principio de responsabilidad única**
- Cada clase tiene una sola responsabilidad
- Funciones pequeñas y enfocadas
- Composables reutilizables

✅ **Inyección de dependencias manual**
- ViewModelFactory para crear ViewModels con dependencias
- Repository recibe DAO en constructor
- Fácil testing y mantenimiento

### 12.2 Gestión de Estado

✅ **Estado inmutable**
- StateFlow de solo lectura para UI
- MutableStateFlow privado en ViewModel
- Uso de copy() para actualizaciones

✅ **Estado reactivo**
- Flow para observar cambios de base de datos
- collectAsState() en Composables
- Actualizaciones automáticas de UI

### 12.3 Manejo de Errores

✅ **Validaciones centralizadas**
- ValidadorFormulario en capa domain
- Errores específicos por campo
- Feedback visual inmediato

✅ **Try-catch para operaciones async**
- Captura errores de base de datos
- Mensajes informativos al usuario
- Logging para debugging

### 12.4 Rendimiento

✅ **LazyColumn para listas grandes**
- Solo renderiza elementos visibles
- Mejor rendimiento que Column

✅ **remember() para estado local**
- Evita recrear estado en recomposiciones
- Mejora performance

✅ **viewModelScope para corrutinas**
- Se cancela automáticamente cuando ViewModel muere
- Evita memory leaks

### 12.5 UI/UX

✅ **Feedback visual**
- Estados de loading
- Mensajes de error claros
- Animaciones suaves

✅ **Accesibilidad**
- contentDescription en iconos
- Labels descriptivos
- Navegación con teclado

---

## 13. Testing y Debugging

### 13.1 Tests Unitarios

```kotlin
/**
 * 🧪 Tests para validaciones del formulario
 */
class FormularioValidacionTest {

    @Test
    fun `correo vacío debe generar error`() {
        // Given - Datos de prueba
        val correoVacio = ""

        // When - Ejecutar función a testear
        val error = ValidadorFormulario.validarCorreo(correoVacio)

        // Then - Verificar resultado
        assertNotNull("El correo vacío debe generar error", error)
        assertEquals("El correo es requerido", error)
    }

    @Test
    fun `correo válido no debe generar error`() {
        // Given
        val correoValido = "usuario@ejemplo.com"

        // When
        val error = ValidadorFormulario.validarCorreo(correoValido)

        // Then
        assertNull("Correo válido no debe generar error", error)
    }

    @Test
    fun `nombre muy corto debe generar error`() {
        // Given
        val nombreCorto = "A"

        // When
        val error = ValidadorFormulario.validarNombre(nombreCorto)

        // Then
        assertEquals("Nombre muy corto", error)
    }
}
```

### 13.2 Debugging Tips

**Usar Logcat efectivamente:**
```kotlin
companion object {
    private const val TAG = "FormularioViewModel"
}

fun onEnviarFormulario() {
    Log.d(TAG, "Iniciando envío de formulario")
    
    viewModelScope.launch {
        try {
            val entity = FormularioServicioEntity(...)
            Log.d(TAG, "Entity creada: $entity")
            
            val id = repository.guardarFormulario(entity)
            Log.i(TAG, "Formulario guardado con ID: $id")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error al guardar formulario", e)
        }
    }
}
```

**Compose Inspector:**
- Tools → Layout Inspector (para debugging de UI)
- Ver jerarquía de Composables
- Inspeccionar propiedades en tiempo real

---

## 14. Posibles Mejoras Futuras

### 14.1 Características Avanzadas

🚀 **Autenticación con Firebase**
```kotlin
// Implementar login real
class AuthRepository {
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Result.success(User(result.user?.uid ?: "", email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

🔔 **Notificaciones Push**
```kotlin
// Notificar cuando cambie estado de solicitud
class NotificationManager(private val context: Context) {
    fun enviarNotificacionEstado(titulo: String, mensaje: String) {
        val notificacion = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
            
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notificacion)
    }
}
```

📍 **Geolocalización**
```kotlin
// Encontrar técnico más cercano
@Composable
fun UbicacionTecnico() {
    var ubicacion by remember { mutableStateOf<LatLng?>(null) }
    
    LaunchedEffect(Unit) {
        ubicacion = obtenerUbicacionActual()
    }
    
    ubicacion?.let { coords ->
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(coords, 15f)
            )
        ) {
            Marker(position = coords, title = "Técnico más cercano")
        }
    }
}
```

### 14.2 Optimizaciones Técnicas

⚡ **Caché y Offline Support**
```kotlin
@Dao
interface CacheDao {
    @Query("SELECT * FROM cache WHERE key = :key")
    suspend fun getCachedData(key: String): CacheEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(cache: CacheEntity)
}

// Implementar estrategia cache-first
class RepositoryWithCache(
    private val remoteApi: ApiService,
    private val cacheDao: CacheDao
) {
    suspend fun getData(): List<Servicio> {
        // 1. Intentar cache primero
        val cached = cacheDao.getCachedData("servicios")
        if (cached != null && cached.isValid()) {
            return cached.toServicios()
        }
        
        // 2. Si no hay cache, obtener de API
        return try {
            val remote = remoteApi.getServicios()
            cacheDao.insertCache(CacheEntity("servicios", remote))
            remote
        } catch (e: Exception) {
            // 3. Si falla API, usar cache expirado
            cached?.toServicios() ?: emptyList()
        }
    }
}
```

🎨 **Theming Dinámico**
```kotlin
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColor: Boolean = true, // Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

---

## 📖 Conclusión

Este proyecto implementa una **aplicación Android moderna** siguiendo las mejores prácticas actuales de la industria:

### ✅ Tecnologías Clave Aprendidas:
- **Kotlin**: Lenguaje moderno y conciso para Android
- **Jetpack Compose**: UI declarativa y reactiva
- **MVVM**: Arquitectura escalable y mantenible
- **Room**: Base de datos local robusta
- **Coroutines**: Programación asíncrona simplificada
- **Navigation Compose**: Sistema de navegación moderno

### ✅ Conceptos Fundamentales:
- **Estado reactivo** con StateFlow
- **Separación de responsabilidades** en capas
- **Inyección de dependencias** manual
- **Validaciones centralizadas**
- **Testing unitario** para lógica crítica

### 🎯 Próximos Pasos de Aprendizaje:
1. **Hilt/Dagger** para inyección de dependencias automática
2. **Retrofit** para comunicación con APIs REST
3. **Firebase** para autenticación y base de datos cloud
4. **Testing avanzado** con MockK y pruebas de UI
5. **CI/CD** con GitHub Actions para automatización

### 🏆 Habilidades Desarrolladas:
- Arquitectura de aplicaciones móviles
- Gestión de estado en UI reactiva
- Persistencia de datos local
- Patrones de diseño (Repository, Factory, Observer)
- Debugging y testing de aplicaciones Android

Esta aplicación sirve como **base sólida** para desarrollar aplicaciones Android más complejas y escalables en el futuro.

---

*"El mejor código es aquel que es fácil de leer, mantener y extender. Este proyecto demuestra esos principios en acción."* 🚀

