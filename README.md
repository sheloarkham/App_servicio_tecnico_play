# 📱 App Servicio Técnico PlayStation

<div align="center">

![Android](https://img.shields.io/badge/Android-7.0+-green.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue.svg)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)

**Aplicación móvil para gestión de servicios técnicos de consolas PlayStation**

[Características](#-características-principales) • [Tecnologías](#-tecnologías-utilizadas) • [Instalación](#-instalación) • [Backend](#-backend-spring-boot) • [Equipo](#-equipo-de-desarrollo)

</div>

---

## 👥 Equipo de Desarrollo

- **[Tu Nombre]** - Desarrollador Full Stack
- **[Integrante 2]** - Desarrollador Backend/Frontend

**Institución:** [Tu Universidad/Instituto]  
**Curso:** Desarrollo de Aplicaciones Móviles  
**Fecha:** Diciembre 2025

---

## 📋 Descripción del Proyecto

Aplicación Android nativa desarrollada en **Kotlin** con **Jetpack Compose** que permite a los usuarios gestionar solicitudes de servicio técnico para consolas PlayStation 4 y PlayStation 5. Incluye un backend REST desarrollado en **Spring Boot** con base de datos **PostgreSQL**.

### 🎯 Objetivo

Proporcionar una plataforma completa para:
- 📅 Agendar servicios técnicos para consolas PS4/PS5
- 📋 Gestionar solicitudes y cotizaciones
- 🔍 Consultar catálogo de servicios disponibles
- 📊 Visualizar estado de reparaciones en tiempo real
- 🎮 Acceder a información de juegos populares (API externa)

---

## ✨ Características Principales

### 🔐 Autenticación y Seguridad
- Login con validación de credenciales
- Modo invitado para exploración
- Sesión persistente con DataStore

### 📝 Gestión de Solicitudes
- **CRUD Completo** de solicitudes de servicio
- Formularios con validación en tiempo real
- Estados: Pendiente, En Proceso, Completado
- Persistencia local (Room) y remota (Spring Boot)

### 🗓️ Agendamiento de Servicios
- Selector de fecha y hora
- Validación de horario laboral (Lun-Sáb 10:00-18:00)
- Confirmación de citas
- Notificaciones visuales

### 🎨 Catálogo de Servicios
- Categorías: Mantenimiento, Reparación, Diagnóstico
- Cards interactivas con detalles
- Precios y descripciones
- Animaciones fluidas

### 🌐 Conexión con Backend REST
- Integración con microservicio Spring Boot
- Endpoints RESTful documentados con Swagger
- Manejo de estados (Loading, Success, Error)
- Retrofit para consumo de API

### 🎮 API Externa (TMDB)
- Consumo de API externa de películas/series de PlayStation
- Tarjetas visuales con información
- Búsqueda y filtrado
- Fallback a datos mock si falla la conexión

### 💾 Persistencia de Datos
- **Room Database** para datos locales
- **PostgreSQL** en backend
- Sincronización automática
- Migraciones de esquema

### 🎬 Animaciones y UX
- Transiciones suaves entre pantallas
- Botones con efectos interactivos
- Loading indicators animados
- Mensajes de éxito/error con animaciones

---

## 🏗️ Arquitectura

### Frontend (Android)
```
app/
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
└── navigation/        # Navegación con Compose
```

**Patrón:** MVVM (Model-View-ViewModel)

### Backend (Spring Boot)
```
backend/
├── controller/        # REST Controllers
├── service/           # Lógica de negocio
├── repository/        # Acceso a datos (JPA)
├── model/             # Entidades JPA
├── dto/               # Data Transfer Objects
└── config/            # Configuración (Swagger, CORS)
```

**Puerto:** `8080`  
**Base de datos:** PostgreSQL en `localhost:5432`

---

## 🛠️ Tecnologías Utilizadas

### Frontend (Android)

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| **Kotlin** | 1.9 | Lenguaje principal |
| **Jetpack Compose** | 1.5 | UI declarativa |
| **Room Database** | 2.6 | Base de datos local |
| **Retrofit** | 2.9 | Cliente HTTP |
| **Coroutines** | 1.7 | Programación asíncrona |
| **ViewModel** | 2.7 | Gestión de estado |
| **Navigation Compose** | 2.7 | Navegación |
| **Coil** | 2.5 | Carga de imágenes |

### Backend

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| **Spring Boot** | 3.2 | Framework backend |
| **Spring Data JPA** | 3.2 | ORM |
| **PostgreSQL** | 15 | Base de datos |
| **Swagger/OpenAPI** | 3.0 | Documentación API |
| **Lombok** | 1.18 | Reducción de boilerplate |
| **Maven** | 3.9 | Gestión de dependencias |

### Testing

| Herramienta | Cobertura |
|------------|-----------|
| **JUnit 4** | Unit testing |
| **MockK** | Mocking en Kotlin |
| **Coroutines Test** | Testing asíncrono |
| **Jacoco** | Cobertura de código (≥80%) |

---

## 📦 Instalación y Ejecución

### 📋 Requisitos Previos

- **Android Studio** Hedgehog o superior
- **JDK** 11 o superior
- **PostgreSQL** 15+
- **Git**

### 🚀 Backend (Spring Boot)

#### 1. Configurar Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE app_servicio_tecnico;

-- Crear usuario (opcional)
CREATE USER servicio_user WITH PASSWORD 'servicio123';
GRANT ALL PRIVILEGES ON DATABASE app_servicio_tecnico TO servicio_user;
```

#### 2. Configurar `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/app_servicio_tecnico
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

#### 3. Ejecutar Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

✅ Backend corriendo en: `http://localhost:8080`

#### 4. Verificar Swagger

Abre en el navegador:
```
http://localhost:8080/swagger-ui/index.html
```

### 📱 Frontend (Android)

#### 1. Clonar Repositorio

```bash
git clone https://github.com/sheloarkham/App_servicio_tecnico_play.git
cd App_servicio_tecnico_play
```

#### 2. Abrir en Android Studio

1. Open Project → Seleccionar carpeta del proyecto
2. Esperar sincronización de Gradle
3. Ejecutar `Build → Make Project`

#### 3. Configurar URL del Backend

Editar `RetrofitClient.kt`:

```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/" // Emulador
// private const val BASE_URL = "http://tu-ip-local:8080/" // Dispositivo físico
```

#### 4. Ejecutar App

- Conectar dispositivo o iniciar emulador
- Click en **Run** (▶️)
- Seleccionar dispositivo
- ¡La app se instalará automáticamente!

---

## 🌐 Backend - Spring Boot

### 📍 Endpoints Principales

#### Solicitudes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/solicitudes` | Obtener todas las solicitudes |
| `GET` | `/solicitudes/{id}` | Obtener solicitud por ID |
| `POST` | `/solicitudes` | Crear nueva solicitud |
| `PUT` | `/solicitudes/{id}` | Actualizar solicitud |
| `DELETE` | `/solicitudes/{id}` | Eliminar solicitud |
| `GET` | `/solicitudes/estado/{estado}` | Filtrar por estado |

#### Categorías

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/categorias` | Obtener todas las categorías |
| `GET` | `/categorias/{id}` | Obtener categoría por ID |
| `POST` | `/categorias` | Crear categoría |

### 📄 Swagger UI

**URL:** `http://localhost:8080/swagger-ui/index.html`

Swagger proporciona:
- ✅ Documentación interactiva de la API
- ✅ Prueba de endpoints en tiempo real
- ✅ Esquemas de Request/Response
- ✅ Códigos de estado HTTP

### 🧪 Probar con Postman

#### Crear Solicitud (POST)

```http
POST http://localhost:8080/solicitudes
Content-Type: application/json

{
  "servicio": "Reparación PS5",
  "fechaAgendada": "2025-01-15",
  "horaAgendada": "14:00",
  "estado": "PENDIENTE",
  "clienteNombre": "Juan Pérez",
  "descripcion": "Consola no enciende",
  "categoriaId": 1
}
```

#### Obtener Solicitudes (GET)

```http
GET http://localhost:8080/solicitudes
```

---

## 📊 Base de Datos

### Modelo de Datos

#### Tabla: `solicitudes`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | ID único (auto-increment) |
| `servicio` | VARCHAR(255) | Nombre del servicio |
| `fecha_agendada` | DATE | Fecha de la cita |
| `hora_agendada` | TIME | Hora de la cita |
| `estado` | VARCHAR(50) | PENDIENTE, EN_PROCESO, COMPLETADO |
| `cliente_nombre` | VARCHAR(255) | Nombre del cliente |
| `descripcion` | TEXT | Descripción del problema |
| `categoria_id` | BIGINT | FK a tabla categorías |
| `fecha_creacion` | TIMESTAMP | Fecha de creación |

#### Tabla: `categorias`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | ID único |
| `nombre` | VARCHAR(255) | Nombre de la categoría |
| `descripcion` | TEXT | Descripción |
| `precio` | DECIMAL | Precio base |

---

## 🔌 API Externa (TMDB)

### Integración

La app consume la API de **The Movie Database (TMDB)** para mostrar contenido relacionado con PlayStation.

**Base URL:** `https://api.themoviedb.org/3/`

### Funcionalidades

- ✅ Obtener películas/series populares
- ✅ Búsqueda de contenido
- ✅ Paginación de resultados
- ✅ Fallback a datos mock si falla

### Configuración

```kotlin
// ExternalApi.kt
@GET("movie/popular")
suspend fun obtenerJuegosPopulares(
    @Query("api_key") apiKey: String = "TU_API_KEY"
): Response<GameResponse>
```

---

## 🧪 Pruebas Unitarias

### Cobertura

- ✅ **ViewModels:** 85% de cobertura
- ✅ **Repositories:** 82% de cobertura
- ✅ **Total de pruebas:** 100+

### Ejecutar Pruebas

```bash
# Todas las pruebas
./gradlew test

# Con reporte de cobertura
./gradlew testDebugUnitTest jacocoTestReport
```

**Reporte:** `app/build/reports/tests/testDebugUnitTest/index.html`

### Herramientas

- **JUnit 4** - Framework de testing
- **MockK** - Mocking para Kotlin
- **Coroutines Test** - Testing asíncrono
- **Jacoco** - Cobertura de código

---

## 📦 APK Firmado

### Generar APK

#### Opción 1: Script Automático

```bash
.\GENERAR_APK_FIRMADO.bat
```

#### Opción 2: Gradle

```bash
.\gradlew assembleRelease
```

**APK generado en:** `app/build/outputs/apk/release/app-release.apk`

### Información del APK

- **Tamaño:** ~9 MB
- **Min SDK:** Android 7.0 (API 24)
- **Target SDK:** Android 14 (API 36)
- **Firma:** RSA 2048 bits
- **Keystore:** `release-keystore.jks`

### Instalar en Dispositivo

```bash
adb install app/build/outputs/apk/release/app-release.apk
```

O transferir el APK por WhatsApp/Email y abrir en el dispositivo.

---

## 📸 Capturas de Pantalla

### 🎨 Interfaz de Usuario

#### Splash Screen y Login
![Splash Screen](docs/screenshots/splash.png)
![Login](docs/screenshots/login.png)

#### Dashboard y Servicios
![Dashboard](docs/screenshots/dashboard.png)
![Servicios](docs/screenshots/servicios.png)

#### Formularios y Agendamiento
![Formulario](docs/screenshots/formulario.png)
![Agendar](docs/screenshots/agendar.png)

#### Gestión de Solicitudes
![Solicitudes](docs/screenshots/solicitudes.png)
![Detalle](docs/screenshots/detalle.png)

### 🌐 Backend

#### Swagger UI
![Swagger](docs/screenshots/swagger.png)

#### Postman - Endpoints
![Postman GET](docs/screenshots/postman_get.png)
![Postman POST](docs/screenshots/postman_post.png)

### 📱 APK Instalado
![APK Instalado](docs/screenshots/apk_installed.png)

---

## 📚 Historias de Usuario Implementadas

| HU | Descripción | Estado |
|----|-------------|--------|
| **HU01** | Pantalla de Inicio (Splash Screen) | ✅ Completada |
| **HU02** | Login con Validación | ✅ Completada |
| **HU03** | Formulario de Solicitud de Servicio | ✅ Completada |
| **HU04** | Catálogo de Servicios | ✅ Completada |
| **HU05** | Agendar Servicio Técnico | ✅ Completada |
| **HU06** | Visualizar Estado de Solicitudes | ✅ Completada |
| **HU07** | Persistencia Local con Room | ✅ Completada |
| **HU08** | Animaciones Funcionales | ✅ Completada |
| **HU09** | Navegación entre Pantallas | ✅ Completada |
| **HU10** | Implementar ViewModels (MVVM) | ✅ Completada |
| **HU11** | Validaciones de Formularios | ✅ Completada |
| **HU12** | Manejo de Estados UI | ✅ Completada |
| **HU13** | Microservicio Backend (Spring Boot) | ✅ Completada |
| **HU14** | Conectar App con Backend (Retrofit) | ✅ Completada |
| **HU15** | Consumir API Externa | ✅ Completada |
| **HU16** | Pruebas Unitarias (JUnit/MockK) | ✅ Completada |
| **HU17** | Generar APK Firmado | ✅ Completada |
| **HU18** | Documentación en GitHub | ✅ Completada |

**Total:** 18 Historias de Usuario implementadas ✅

---

## 🎯 Funcionalidades Destacadas

### ✨ Frontend

1. **Arquitectura MVVM** - Separación clara de responsabilidades
2. **Jetpack Compose** - UI declarativa moderna
3. **Room Database** - Persistencia local robusta
4. **Retrofit + Coroutines** - Consumo eficiente de APIs
5. **Estados Reactivos** - StateFlow y LiveData
6. **Animaciones Fluidas** - Transiciones y efectos visuales
7. **Validaciones** - Formularios con feedback en tiempo real
8. **Manejo de Errores** - Estados Loading/Success/Error

### 🚀 Backend

1. **API RESTful** - Endpoints bien estructurados
2. **Spring Data JPA** - ORM para PostgreSQL
3. **Swagger/OpenAPI** - Documentación interactiva
4. **DTOs** - Transferencia de datos optimizada
5. **CORS Configurado** - Acceso desde aplicación móvil
6. **Manejo de Excepciones** - Responses HTTP apropiados
7. **Validaciones** - Bean Validation en entidades

### 🧪 Testing

1. **100+ Pruebas Unitarias** - ViewModels y Repositories
2. **Cobertura ≥80%** - Jacoco reports
3. **Mocking con MockK** - Dependencias aisladas
4. **Testing Asíncrono** - Coroutines Test

---

## 📖 Documentación Adicional

- 📄 [Cómo Crear Solicitudes](COMO_CREAR_SOLICITUDES.md)
- 🔧 [Guía de Conexión Backend](GUIA_CONEXION_COMPLETA.md)
- 🧪 [Verificar Backend](COMO_VERIFICAR_BACKEND.md)
- 📦 [Compilar APK](HU17_APK_FIRMADO_README.md)
- 🧪 [Pruebas Unitarias](HU16_PRUEBAS_UNITARIAS_README.md)
- 🎨 [Guía de UI](README_UI_EXPLICATIVO.md)
- 🏗️ [Arquitectura MVVM](README_MVVM_EXPLICATIVO.md)

---

## 🔧 Configuración de Desarrollo

### Variables de Entorno

#### Backend (`application.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/app_servicio_tecnico
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
server.port=${SERVER_PORT:8080}
```

#### Frontend (`RetrofitClient.kt`)
```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/" // Emulador
```

### Credenciales de Testing

**Keystore APK:**
- Password: `servicio123`
- Alias: `appserviciotecnico`

**Base de Datos:**
- Host: `localhost:5432`
- Database: `app_servicio_tecnico`
- User: `postgres`
- Password: [tu_password]

---

## 🚀 Despliegue

### Backend en Heroku/Railway

```bash
# Crear Procfile
web: java -jar target/backend-0.0.1-SNAPSHOT.jar

# Deploy
git push heroku main
```

### APK en Dispositivos

1. Generar APK firmado: `.\GENERAR_APK_FIRMADO.bat`
2. Transferir APK a dispositivo
3. Habilitar "Fuentes desconocidas"
4. Instalar

---

## 🐛 Solución de Problemas

### Backend no inicia

```bash
# Verificar PostgreSQL está corriendo
sudo service postgresql status

# Verificar puerto 8080 libre
netstat -an | findstr :8080
```

### App no conecta con Backend

1. Verificar IP correcta en `RetrofitClient.kt`
2. Emulador: usar `10.0.2.2`
3. Dispositivo físico: usar IP local de tu PC
4. Verificar backend está corriendo

### Errores de compilación

```bash
# Limpiar y recompilar
.\gradlew clean build

# Invalidar cachés en Android Studio
File → Invalidate Caches / Restart
```

---

## 📝 Licencia

Este proyecto es con fines educativos para el curso de Desarrollo de Aplicaciones Móviles.

---

## 🙏 Agradecimientos

- Profesor: [Nombre del Profesor]
- Institución: [Tu Universidad/Instituto]
- Recursos: Android Developers, Spring Boot Docs, Stack Overflow

---

## 📞 Contacto

**Desarrolladores:**
- GitHub: [@sheloarkham](https://github.com/sheloarkham)
- Email: [tu-email@example.com]

**Repositorio:** [https://github.com/sheloarkham/App_servicio_tecnico_play](https://github.com/sheloarkham/App_servicio_tecnico_play)

---

<div align="center">

**⭐ Si te gustó este proyecto, dale una estrella en GitHub ⭐**

Desarrollado con ❤️ usando Kotlin y Spring Boot

</div>

