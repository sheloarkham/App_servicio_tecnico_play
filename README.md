📱 App Servicio Técnico PlayStation ![Android](https://img.shields.io/badge/Android-7.0+-green.svg)   ![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue.svg)   ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5-orange.svg)   ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg) Aplicación móvil para gestión de servicios técnicos de consolas PlayStationCaracterísticas • Tecnologías • Instalación • Backend • Equipo👥 Equipo de Desarrollo•[Tu Nombre] - Desarrollador Full Stack•[Integrante 2] - Desarrollador Backend/FrontendInstitución: [Tu Universidad/Instituto]
Curso: Desarrollo de Aplicaciones Móviles
Fecha: Diciembre 2025📋 Descripción del ProyectoAplicación Android nativa desarrollada en Kotlin con Jetpack Compose que permite a los usuarios gestionar solicitudes de servicio técnico para consolas PlayStation 4 y PlayStation 5. Incluye un backend REST desarrollado en Spring Boot con base de datos PostgreSQL.🎯 ObjetivoProporcionar una plataforma completa para:•📅 Agendar servicios técnicos para consolas PS4/PS5•📋 Gestionar solicitudes y cotizaciones•🔍 Consultar catálogo de servicios disponibles•📊 Visualizar estado de reparaciones en tiempo real•🎮 Acceder a información de juegos populares (API externa)✨ Características Principales🔐 Autenticación y Seguridad•Login con validación de credenciales•Modo invitado para exploración•Sesión persistente con DataStore📝 Gestión de Solicitudes•CRUD Completo de solicitudes de servicio•Formularios con validación en tiempo real•Estados: Pendiente, En Proceso, Completado•Persistencia local (Room) y remota (Spring Boot)🗓️ Agendamiento de Servicios•Selector de fecha y hora•Validación de horario laboral (Lun-Sáb 10:00-18:00)•Confirmación de citas•Notificaciones visuales🎨 Catálogo de Servicios•Categorías: Mantenimiento, Reparación, Diagnóstico•Cards interactivas con detalles•Precios y descripciones•Animaciones fluidas🌐 Conexión con Backend REST•Integración con microservicio Spring Boot•Endpoints RESTful documentados con Swagger•Manejo de estados (Loading, Success, Error)•Retrofit para consumo de API🎮 API Externa (TMDB)•Consumo de API externa de películas/series de PlayStation•Tarjetas visuales con información•Búsqueda y filtrado•Fallback a datos mock si falla la conexión💾 Persistencia de Datos•Room Database para datos locales•PostgreSQL en backend•Sincronización automática•Migraciones de esquema🎬 Animaciones y UX•Transiciones suaves entre pantallas•Botones con efectos interactivos•Loading indicators animados•Mensajes de éxito/error con animaciones🏗️ ArquitecturaFrontend (Android)app/
├── model/
│   ├── data/          # Room Database, DAOs, Entities
│   └── entities/      # Modelos de datos
├── viewmodel/         # ViewModels (MVVM)
├── network/
│   ├── api/           # Interfaces de Retrofit
│   ├── models/        # DTOs
│   ├── repository/    # Repositorios
│   └── config/        # Configuración de Retrofit
├── ui/
│   ├── screens/       # Pantallas de la app
│   ├── components/    # Componentes reutilizables
│   └── theme/         # Theming y estilos
└── navigation/        # Navegación con ComposePatrón: MVVM (Model-View-ViewModel)Backend (Spring Boot)backend/
├── controller/        # REST Controllers
├── service/           # Lógica de negocio
├── repository/        # Acceso a datos (JPA)
├── model/             # Entidades JPA
├── dto/               # Data Transfer Objects
└── config/            # Configuración (Swagger, CORS)Puerto: 8080
Base de datos: PostgreSQL en localhost:5432🛠️ Tecnologías UtilizadasFrontend (Android)TecnologíaVersiónUsoKotlin1.9Lenguaje principalJetpack Compose1.5UI declarativaRoom Database2.6Base de datos localRetrofit2.9Cliente HTTPCoroutines1.7Programación asíncronaViewModel2.7Gestión de estadoNavigation Compose2.7NavegaciónCoil2.5Carga de imágenesBackendTecnologíaVersiónUsoSpring Boot3.2Framework backendSpring Data JPA3.2ORMPostgreSQL15Base de datosSwagger/OpenAPI3.0Documentación APILombok1.18Reducción de boilerplateMaven3.9Gestión de dependenciasTestingHerramientaCoberturaJUnit 4Unit testingMockKMocking en KotlinCoroutines TestTesting asíncronoJacocoCobertura de código (≥80%)📦 Instalación y Ejecución📋 Requisitos Previos•Android Studio Hedgehog o superior•JDK 11 o superior•PostgreSQL 15+•Git🚀 Backend (Spring Boot)1. Configurar Base de Datos-- Crear base de datos
CREATE DATABASE app_servicio_tecnico;

-- Crear usuario (opcional)
CREATE USER servicio_user WITH PASSWORD 'servicio123';
GRANT ALL PRIVILEGES ON DATABASE app_servicio_tecnico TO servicio_user;2. Configurar application.propertiesspring.datasource.url=jdbc:postgresql://localhost:5432/app_servicio_tecnico
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
server.port=80803. Ejecutar Backendcd backend
mvn clean install
mvn spring-boot:run✅ Backend corriendo en: http://localhost:80804. Verificar SwaggerAbre en el navegador:http://localhost:8080/swagger-ui/index.html📱 Frontend (Android)1. Clonar Repositoriogit clone https://github.com/sheloarkham/App_servicio_tecnico_play.git
cd App_servicio_tecnico_play2. Abrir en Android Studio1.Open Project → Seleccionar carpeta del proyecto2.Esperar sincronización de Gradle3.Ejecutar Build → Make Project3. Configurar URL del BackendEditar RetrofitClient.kt:private const val BASE_URL = "http://10.0.2.2:8080/" // Emulador
// private const val BASE_URL = "http://tu-ip-local:8080/" // Dispositivo físico4. Ejecutar App•Conectar dispositivo o iniciar emulador•Click en Run (▶️)•Seleccionar dispositivo•¡La app se instalará automáticamente!🌐 Backend - Spring Boot📍 Endpoints PrincipalesSolicitudesMétodoEndpointDescripciónGET/solicitudesObtener todas las solicitudesGET/solicitudes/{id}Obtener solicitud por IDPOST/solicitudesCrear nueva solicitudPUT/solicitudes/{id}Actualizar solicitudDELETE/solicitudes/{id}Eliminar solicitudGET/solicitudes/estado/{estado}Filtrar por estadoCategoríasMétodoEndpointDescripciónGET/categoriasObtener todas las categoríasGET/categorias/{id}Obtener categoría por IDPOST/categoriasCrear categoría📄 Swagger UIURL: http://localhost:8080/swagger-ui/index.htmlSwagger proporciona:•✅ Documentación interactiva de la API•✅ Prueba de endpoints en tiempo real•✅ Esquemas de Request/Response•✅ Códigos de estado HTTP🧪 Probar con PostmanCrear Solicitud (POST)POST http://localhost:8080/solicitudes
Content-Type: application/json

{
  "servicio": "Reparación PS5",
  "fechaAgendada": "2025-01-15",
  "horaAgendada": "14:00",
  "estado": "PENDIENTE",
  "clienteNombre": "Juan Pérez",
  "descripcion": "Consola no enciende",
  "categoriaId": 1
}Obtener Solicitudes (GET)GET http://localhost:8080/solicitudes📊 Base de DatosModelo de DatosTabla: solicitudesCampoTipoDescripciónidBIGINTID único (auto-increment)servicioVARCHAR(255)Nombre del serviciofecha_agendadaDATEFecha de la citahora_agendadaTIMEHora de la citaestadoVARCHAR(50)PENDIENTE, EN_PROCESO, COMPLETADOcliente_nombreVARCHAR(255)Nombre del clientedescripcionTEXTDescripción del problemacategoria_idBIGINTFK a tabla categoríasfecha_creacionTIMESTAMPFecha de creaciónTabla: categoriasCampoTipoDescripciónidBIGINTID úniconombreVARCHAR(255)Nombre de la categoríadescripcionTEXTDescripciónprecioDECIMALPrecio base🔌 API Externa (TMDB)IntegraciónLa app consume la API de The Movie Database (TMDB) para mostrar contenido relacionado con PlayStation.Base URL: https://api.themoviedb.org/3/Funcionalidades•✅ Obtener películas/series populares•✅ Búsqueda de contenido•✅ Paginación de resultados•✅ Fallback a datos mock si fallaConfiguración// ExternalApi.kt
@GET("movie/popular")
suspend fun obtenerJuegosPopulares(
    @Query("api_key") apiKey: String = "TU_API_KEY"
): Response<GameResponse>🧪 Pruebas UnitariasCobertura•✅ ViewModels: 85% de cobertura•✅ Repositories: 82% de cobertura•✅ Total de pruebas: 100+Ejecutar Pruebas# Todas las pruebas
./gradlew test

# Con reporte de cobertura
./gradlew testDebugUnitTest jacocoTestReportReporte: app/build/reports/tests/testDebugUnitTest/index.htmlHerramientas•JUnit 4 - Framework de testing•MockK - Mocking para Kotlin•Coroutines Test - Testing asíncrono•Jacoco - Cobertura de código📦 APK FirmadoGenerar APKOpción 1: Script Automático.\GENERAR_APK_FIRMADO.batOpción 2: Gradle.\gradlew assembleReleaseAPK generado en: app/build/outputs/apk/release/app-release.apkInformación del APK•Tamaño: ~9 MB•Min SDK: Android 7.0 (API 24)•Target SDK: Android 14 (API 36)•Firma: RSA 2048 bits•Keystore: release-keystore.jksInstalar en Dispositivoadb install app/build/outputs/apk/release/app-release.apkO transferir el APK por WhatsApp/Email y abrir en el dispositivo.📸 Capturas de Pantalla🎨 Interfaz de UsuarioSplash Screen y Login ![Splash Screen](docs/screenshots/splash.png)   ![Login](docs/screenshots/login.png) Dashboard y Servicios ![Dashboard](docs/screenshots/dashboard.png)   ![Servicios](docs/screenshots/servicios.png) Formularios y Agendamiento ![Formulario](docs/screenshots/formulario.png)   ![Agendar](docs/screenshots/agendar.png) Gestión de Solicitudes ![Solicitudes](docs/screenshots/solicitudes.png)   ![Detalle](docs/screenshots/detalle.png) 🌐 BackendSwagger UI ![Swagger](docs/screenshots/swagger.png) Postman - Endpoints ![Postman GET](docs/screenshots/postman_get.png)   ![Postman POST](docs/screenshots/postman_post.png) 📱 APK Instalado ![APK Instalado](docs/screenshots/apk_installed.png) 📚 Historias de Usuario ImplementadasHUDescripciónEstadoHU01Pantalla de Inicio (Splash Screen)✅ CompletadaHU02Login con Validación✅ CompletadaHU03Formulario de Solicitud de Servicio✅ CompletadaHU04Catálogo de Servicios✅ CompletadaHU05Agendar Servicio Técnico✅ CompletadaHU06Visualizar Estado de Solicitudes✅ CompletadaHU07Persistencia Local con Room✅ CompletadaHU08Animaciones Funcionales✅ CompletadaHU09Navegación entre Pantallas✅ CompletadaHU10Implementar ViewModels (MVVM)✅ CompletadaHU11Validaciones de Formularios✅ CompletadaHU12Manejo de Estados UI✅ CompletadaHU13Microservicio Backend (Spring Boot)✅ CompletadaHU14Conectar App con Backend (Retrofit)✅ CompletadaHU15Consumir API Externa✅ CompletadaHU16Pruebas Unitarias (JUnit/MockK)✅ CompletadaHU17Generar APK Firmado✅ CompletadaHU18Documentación en GitHub✅ CompletadaTotal: 18 Historias de Usuario implementadas ✅🎯 Funcionalidades Destacadas✨ Frontend1.Arquitectura MVVM - Separación clara de responsabilidades2.Jetpack Compose - UI declarativa moderna3.Room Database - Persistencia local robusta4.Retrofit + Coroutines - Consumo eficiente de APIs5.Estados Reactivos - StateFlow y LiveData6.Animaciones Fluidas - Transiciones y efectos visuales7.Validaciones - Formularios con feedback en tiempo real8.Manejo de Errores - Estados Loading/Success/Error🚀 Backend1.API RESTful - Endpoints bien estructurados2.Spring Data JPA - ORM para PostgreSQL3.Swagger/OpenAPI - Documentación interactiva4.DTOs - Transferencia de datos optimizada5.CORS Configurado - Acceso desde aplicación móvil6.Manejo de Excepciones - Responses HTTP apropiados7.Validaciones - Bean Validation en entidades🧪 Testing1.100+ Pruebas Unitarias - ViewModels y Repositories2.Cobertura ≥80% - Jacoco reports3.Mocking con MockK - Dependencias aisladas4.Testing Asíncrono - Coroutines Test📖 Documentación Adicional•📄 Cómo Crear Solicitudes•🔧 Guía de Conexión Backend•🧪 Verificar Backend•📦 Compilar APK•🧪 Pruebas Unitarias•🎨 Guía de UI•🏗️ Arquitectura MVVM🔧 Configuración de DesarrolloVariables de EntornoBackend (application.properties)spring.datasource.url=jdbc:postgresql://localhost:5432/app_servicio_tecnico
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
server.port=${SERVER_PORT:8080}Frontend (RetrofitClient.kt)private const val BASE_URL = "http://10.0.2.2:8080/" // EmuladorCredenciales de TestingKeystore APK:•Password: servicio123•Alias: appserviciotecnicoBase de Datos:•Host: localhost:5432•Database: app_servicio_tecnico•User: postgres•Password: [tu_password]🚀 DespliegueBackend en Heroku/Railway# Crear Procfile
web: java -jar target/backend-0.0.1-SNAPSHOT.jar

# Deploy
git push heroku mainAPK en Dispositivos1.Generar APK firmado: .\GENERAR_APK_FIRMADO.bat2.Transferir APK a dispositivo3.Habilitar "Fuentes desconocidas"4.Instalar🐛 Solución de ProblemasBackend no inicia# Verificar PostgreSQL está corriendo
sudo service postgresql status

# Verificar puerto 8080 libre
netstat -an | findstr :8080App no conecta con Backend1.Verificar IP correcta en RetrofitClient.kt2.Emulador: usar 10.0.2.23.Dispositivo físico: usar IP local de tu PC4.Verificar backend está corriendoErrores de compilación# Limpiar y recompilar
.\gradlew clean build

# Invalidar cachés en Android Studio
File → Invalidate Caches / Restart📝 LicenciaEste proyecto es con fines educativos para el curso de Desarrollo de Aplicaciones Móviles.🙏 Agradecimientos•Profesor: [Nombre del Profesor]•Institución: [Tu Universidad/Instituto]•Recursos: Android Developers, Spring Boot Docs, Stack Overflow📞 ContactoDesarrolladores:•GitHub: @sheloarkham•Email: [tu-email@example.com]Repositorio: https://github.com/sheloarkham/App_servicio_tecnico_play⭐ Si te gustó este proyecto, dale una estrella en GitHub ⭐Desarrollado con ❤️ usando Kotlin y Spring Boot
