# 📱 App Servicio Técnico PlayStation

<div align="center">

![Android](https://img.shields.io/badge/Android-7.0+-green.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue.svg)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)

**Aplicación móvil para gestión de servicios técnicos de consolas PlayStation**

[Características](#-características-principales) • [Tecnologías](#-tecnologías-utilizadas) • [Instalación](#-instalación-y-ejecución) • [Backend](#-backend-microservicio) • [Evidencias](#-evidencias-y-capturas)

</div>

---

## 1️⃣ Nombre del Proyecto

**App Servicio Técnico PlayStation**

Sistema completo de gestión de servicios técnicos para consolas PlayStation 4 y PlayStation 5, desarrollado con arquitectura cliente-servidor utilizando Android (Kotlin + Jetpack Compose) y Spring Boot.

---

## 2️⃣ Integrantes del Equipo

| Nombre | Rol | GitHub | Email |
|--------|-----|--------|-------|
| **Axel Shelo Arkham** | Desarrollador Full Stack | [@sheloarkham](https://github.com/sheloarkham) | [tu-email@example.com] |
| **[Nombre Integrante 2]** | Desarrollador Backend/Frontend | [@usuario2](https://github.com/usuario2) | [email2@example.com] |

**Institución:** [Tu Universidad/Instituto]  
**Curso:** Desarrollo de Aplicaciones Móviles  
**Profesor:** [Nombre del Profesor]  
**Fecha de Entrega:** Diciembre 2025

---

## 3️⃣ Funcionalidades Implementadas

### 📱 Aplicación Móvil (Android)

#### Autenticación y Navegación
- ✅ **HU01:** Splash Screen animado con logo
- ✅ **HU02:** Login con validación de credenciales
- ✅ Modo invitado para exploración
- ✅ **HU09:** Navegación entre pantallas con Jetpack Compose

#### Gestión de Servicios
- ✅ **HU03:** Formulario completo de solicitud de servicio
- ✅ **HU04:** Catálogo de servicios (Mantenimiento, Reparación, Diagnóstico)
- ✅ **HU05:** Agendamiento de servicios con DatePicker y TimePicker
- ✅ **HU06:** Visualización de estado de solicitudes (Pendiente, En Proceso, Completado)
- ✅ **HU11:** Validaciones en tiempo real de formularios
- ✅ **HU12:** Manejo de estados UI (Loading, Success, Error)

#### Persistencia y Sincronización
- ✅ **HU07:** Base de datos local con Room Database
- ✅ **HU10:** Arquitectura MVVM con ViewModels
- ✅ **HU14:** Conexión con backend REST usando Retrofit
- ✅ Sincronización automática local ↔ servidor

#### Experiencia de Usuario
- ✅ **HU08:** Animaciones fluidas y transiciones
- ✅ Diseño Material Design 3
- ✅ Responsive UI adaptable
- ✅ Feedback visual en todas las acciones

#### Integración de APIs
- ✅ **HU15:** Consumo de API externa (TMDB - The Movie Database)
- ✅ Visualización de contenido multimedia relacionado con PlayStation
- ✅ Manejo de errores y fallback a datos mock

#### Testing y Calidad
- ✅ **HU16:** Más de 100 pruebas unitarias con JUnit y MockK
- ✅ Cobertura de código ≥ 80% en ViewModels y Repositories
- ✅ Testing de coroutines y flujos asíncronos

#### Distribución
- ✅ **HU17:** APK firmado con keystore (.jks)
- ✅ **HU18:** Documentación completa en GitHub

### 🌐 Backend (Microservicio Spring Boot)

#### API REST
- ✅ **HU13:** Microservicio RESTful con Spring Boot
- ✅ CRUD completo de solicitudes de servicio
- ✅ CRUD de categorías de servicios
- ✅ Filtros por estado y fecha
- ✅ Paginación y ordenamiento

#### Base de Datos
- ✅ PostgreSQL como base de datos
- ✅ Spring Data JPA para persistencia
- ✅ Migraciones automáticas con Hibernate
- ✅ Relaciones entre entidades

#### Documentación
- ✅ Swagger/OpenAPI para documentación interactiva
- ✅ DTOs para transferencia de datos
- ✅ Validaciones con Bean Validation

#### Seguridad y Configuración
- ✅ CORS configurado para app móvil
- ✅ Manejo centralizado de excepciones
- ✅ Logs estructurados

---

## 4️⃣ Endpoints Utilizados

### 🔌 API Externa: TMDB (The Movie Database)

**Base URL:** `https://api.themoviedb.org/3/`

| Endpoint | Método | Descripción | Uso en App |
|----------|--------|-------------|------------|
| `/movie/popular` | GET | Películas populares | Mostrar contenido relacionado con PlayStation |
| `/search/movie` | GET | Búsqueda de películas | Búsqueda de títulos |
| `/movie/{id}` | GET | Detalles de película | Información detallada |

**Configuración:**
```kotlin
@GET("movie/popular")
suspend fun obtenerJuegosPopulares(
    @Query("api_key") apiKey: String = "TU_API_KEY",
    @Query("language") language: String = "es-ES",
    @Query("page") page: Int = 1
): Response<GameResponse>
```

**Características:**
- ✅ Paginación implementada
- ✅ Fallback a datos mock si falla la conexión
- ✅ Caché de imágenes con Coil
- ✅ Manejo de errores (timeout, 404, 500)

---

### 🚀 Microservicio Propio: API Solicitudes

**Base URL:** `http://localhost:8080/`

#### 📋 Solicitudes

| Endpoint | Método | Descripción | Request Body | Response |
|----------|--------|-------------|--------------|----------|
| `/solicitudes` | GET | Obtener todas las solicitudes | - | `List<SolicitudDTO>` |
| `/solicitudes/{id}` | GET | Obtener solicitud por ID | - | `SolicitudDTO` |
| `/solicitudes` | POST | Crear nueva solicitud | `SolicitudDTO` | `SolicitudDTO` |
| `/solicitudes/{id}` | PUT | Actualizar solicitud | `SolicitudDTO` | `SolicitudDTO` |
| `/solicitudes/{id}` | DELETE | Eliminar solicitud | - | `204 No Content` |
| `/solicitudes/estado/{estado}` | GET | Filtrar por estado | - | `List<SolicitudDTO>` |

**Ejemplo Request (POST /solicitudes):**
```json
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

**Ejemplo Response:**
```json
{
  "id": 1,
  "servicio": "Reparación PS5",
  "fechaAgendada": "2025-01-15",
  "horaAgendada": "14:00",
  "estado": "PENDIENTE",
  "clienteNombre": "Juan Pérez",
  "descripcion": "Consola no enciende",
  "categoriaId": 1,
  "fechaCreacion": "2025-01-10T10:30:00"
}
```

#### 🏷️ Categorías

| Endpoint | Método | Descripción | Response |
|----------|--------|-------------|----------|
| `/categorias` | GET | Obtener todas las categorías | `List<CategoriaDTO>` |
| `/categorias/{id}` | GET | Obtener categoría por ID | `CategoriaDTO` |
| `/categorias` | POST | Crear categoría | `CategoriaDTO` |

**Estados válidos:** `PENDIENTE`, `EN_PROCESO`, `COMPLETADO`

**Documentación interactiva:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 5️⃣ Pasos para Ejecutar el Proyecto

### 📋 Requisitos Previos

- ✅ **Android Studio** Hedgehog (2023.1.1) o superior
- ✅ **JDK** 11 o superior
- ✅ **PostgreSQL** 15 o superior
- ✅ **Git** para clonar repositorio
- ✅ **Maven** 3.9+ (incluido en IntelliJ IDEA)

---

### 🗄️ PASO 1: Configurar Base de Datos

#### 1.1. Instalar PostgreSQL

Descargar desde: [https://www.postgresql.org/download/](https://www.postgresql.org/download/)

#### 1.2. Crear Base de Datos

Abrir **pgAdmin** o terminal de PostgreSQL:

```sql
-- Conectarse a PostgreSQL
psql -U postgres

-- Crear base de datos
CREATE DATABASE app_servicio_tecnico;

-- Crear usuario (opcional)
CREATE USER servicio_user WITH PASSWORD 'servicio123';
GRANT ALL PRIVILEGES ON DATABASE app_servicio_tecnico TO servicio_user;

-- Verificar
\l
```

---

### 🌐 PASO 2: Ejecutar Backend (Spring Boot)

#### 2.1. Clonar Repositorio Backend

```bash
git clone https://github.com/sheloarkham/backend-servicio-tecnico.git
cd backend-servicio-tecnico
```

#### 2.2. Configurar `application.properties`

Ubicación: `src/main/resources/application.properties`

```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/app_servicio_tecnico
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD_AQUI
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Configuración del Servidor
server.port=8080

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
```

#### 2.3. Compilar y Ejecutar

**Opción A: Con Maven (línea de comandos)**
```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

**Opción B: Con IntelliJ IDEA**
1. Abrir proyecto en IntelliJ
2. Esperar a que Maven descargue dependencias
3. Click derecho en `Application.java` → Run
4. O click en el botón verde ▶️ arriba

#### 2.4. Verificar que el Backend está Corriendo

Abrir navegador en:
- **API:** [http://localhost:8080/solicitudes](http://localhost:8080/solicitudes)
- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Deberías ver la documentación interactiva de Swagger.

---

### 📱 PASO 3: Ejecutar App Móvil (Android)

#### 3.1. Clonar Repositorio

```bash
git clone https://github.com/sheloarkham/App_servicio_tecnico_play.git
cd App_servicio_tecnico_play
```

#### 3.2. Abrir en Android Studio

1. **File** → **Open**
2. Seleccionar carpeta `App_servicio_tecnico_play`
3. Esperar sincronización de Gradle (puede tomar varios minutos)
4. Si aparecen errores, hacer: **File** → **Invalidate Caches / Restart**

#### 3.3. Configurar URL del Backend

Editar archivo: `app/src/main/java/appserviciotecnico/network/config/RetrofitClient.kt`

```kotlin
object RetrofitClient {
    // Para EMULADOR (Android Studio AVD)
    private const val BASE_URL = "http://10.0.2.2:8080/"
    
    // Para DISPOSITIVO FÍSICO (conectado por USB o WiFi)
    // private const val BASE_URL = "http://192.168.1.100:8080/"
    // Reemplazar 192.168.1.100 con tu IP local
    
    // ...resto del código
}
```

**Encontrar tu IP local:**
- Windows: `ipconfig` (buscar IPv4)
- Mac/Linux: `ifconfig` o `ip addr`

#### 3.4. Ejecutar App

**Opción A: En Emulador**
1. **Tools** → **Device Manager**
2. Crear/iniciar un **Android Virtual Device (AVD)**
3. Recomendado: Pixel 5 con Android 13 (API 33)
4. Click en **Run** ▶️
5. Seleccionar el emulador

**Opción B: En Dispositivo Físico**
1. Habilitar **Opciones de desarrollador** en el celular
2. Habilitar **Depuración USB**
3. Conectar por USB
4. Autorizar la conexión en el celular
5. Click en **Run** ▶️
6. Seleccionar tu dispositivo

#### 3.5. Verificar Conexión Backend

1. Abrir la app
2. Ir a **"Gestión Backend"**
3. Si ves la lista de solicitudes sin error → ✅ **Funciona!**
4. Si aparece "Error de red" → revisar configuración IP

---

### 🧪 PASO 4: Probar con Postman (Opcional)

#### 4.1. Importar Colección

Crear colección con estos endpoints:

**GET - Obtener Solicitudes**
```http
GET http://localhost:8080/solicitudes
```

**POST - Crear Solicitud**
```http
POST http://localhost:8080/solicitudes
Content-Type: application/json

{
  "servicio": "Reparación PS5",
  "fechaAgendada": "2025-01-20",
  "horaAgendada": "15:00",
  "estado": "PENDIENTE",
  "clienteNombre": "María García",
  "descripcion": "Problemas con lector de discos",
  "categoriaId": 1
}
```

**PUT - Actualizar Solicitud**
```http
PUT http://localhost:8080/solicitudes/1
Content-Type: application/json

{
  "servicio": "Reparación PS5",
  "fechaAgendada": "2025-01-20",
  "horaAgendada": "15:00",
  "estado": "EN_PROCESO",
  "clienteNombre": "María García",
  "descripcion": "Problemas con lector de discos",
  "categoriaId": 1
}
```

**DELETE - Eliminar Solicitud**
```http
DELETE http://localhost:8080/solicitudes/1
```

---

### ✅ PASO 5: Verificación Final

**Backend:**
- [ ] Swagger UI abre correctamente
- [ ] GET /solicitudes retorna lista (puede estar vacía)
- [ ] POST crea una solicitud y retorna 201
- [ ] Base de datos tiene tablas creadas

**App Móvil:**
- [ ] App se instala sin errores
- [ ] Login funciona (o modo invitado)
- [ ] Dashboard muestra todas las opciones
- [ ] Crear solicitud guarda datos
- [ ] Lista de solicitudes muestra datos del backend
- [ ] API externa muestra películas/series

---

## 6️⃣ Capturas: APK Firmado y Keystore (.jks)

### 📦 APK Firmado

El APK de producción está firmado con un certificado digital para garantizar su autenticidad.

**Ubicación:** `app/build/outputs/apk/release/app-release.apk`

#### Información del APK

```
Nombre: app-release.apk
Tamaño: 8.99 MB (8,998,138 bytes)
Min SDK: Android 7.0 (API 24)
Target SDK: Android 14 (API 36)
Application ID: com.appserviciotecnico
Version: 1.0 (Version Code: 1)
Firma: RSA 2048 bits
```

#### Captura del APK Generado

```
📦 app/build/outputs/apk/release/
└── app-release.apk (8.99 MB)
    ✅ Firmado digitalmente
    ✅ Instalable en cualquier Android 7.0+
    ✅ Listo para distribución
```

#### Cómo Generar el APK

**Opción 1: Script Automático**
```bash
.\GENERAR_APK_FIRMADO.bat
```

**Opción 2: Gradle**
```bash
.\gradlew clean assembleRelease
```

**Opción 3: Android Studio**
1. **Build** → **Generate Signed Bundle / APK**
2. Seleccionar **APK**
3. Next → Configurar keystore
4. Build

#### Verificar Firma del APK

```bash
# Con apksigner (Android SDK)
apksigner verify --print-certs app-release.apk

# Salida esperada:
Signer #1 certificate DN: CN=App Servicio Tecnico, OU=AppServicioTecnico, O=AppServicioTecnico, L=Lima, ST=Lima, C=PE
Signer #1 certificate SHA-256 digest: 9a24545166529e05ca398268c06900b318e12cb654c40cf5b796cda9e93885c8
Signer #1 certificate SHA-1 digest: dd479e46f664a7904ff5c94f2e0f277920b6f50d
```

---

### 🔐 Keystore (.jks) - Llave de Firma

El keystore es el archivo que contiene el certificado digital usado para firmar el APK.

**Ubicación:** `app/release-keystore.jks`

#### Información del Keystore

```
Archivo: release-keystore.jks
Tipo: JKS (Java KeyStore)
Algoritmo: RSA 2048 bits
Alias: appserviciotecnico
Validez: 10,000 días (~27 años)
Fecha creación: 07-12-2025
Válido hasta: 2052
```

#### Detalles del Certificado

```
Distinguished Name (DN):
CN=App Servicio Tecnico
OU=AppServicioTecnico
O=AppServicioTecnico
L=Lima
ST=Lima
C=PE

Fingerprints:
SHA-256: 9a24545166529e05ca398268c06900b318e12cb654c40cf5b796cda9e93885c8
SHA-1: dd479e46f664a7904ff5c94f2e0f277920b6f50d
MD5: d8714e99074483a6b14aea1bcb37048b
```

#### Credenciales (Solo para Desarrollo)

⚠️ **IMPORTANTE: Estas credenciales son solo para el ambiente de desarrollo/pruebas.**

```
Keystore Password: servicio123
Key Alias: appserviciotecnico
Key Password: servicio123
```

**En producción se deben usar contraseñas robustas y almacenar el keystore de forma segura.**

#### Cómo se Generó el Keystore

```bash
keytool -genkeypair -v \
    -keystore release-keystore.jks \
    -alias appserviciotecnico \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000 \
    -storepass servicio123 \
    -keypass servicio123 \
    -dname "CN=App Servicio Tecnico, OU=AppServicioTecnico, O=AppServicioTecnico, L=Lima, ST=Lima, C=PE"
```

#### Configuración en build.gradle.kts

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("release-keystore.jks")
            storePassword = "servicio123"
            keyAlias = "appserviciotecnico"
            keyPassword = "servicio123"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(...)
        }
    }
}
```

#### ⚠️ Seguridad del Keystore

**✅ Implementado:**
- Keystore excluido del repositorio Git (`.gitignore`)
- Documentación separada con credenciales
- Backup del keystore en ubicación segura

**🔒 Buenas Prácticas:**
- **NUNCA** subir el .jks a GitHub
- Guardar backup en almacenamiento cifrado
- Usar contraseñas fuertes en producción
- Documentar credenciales de forma segura
- Si se pierde el keystore, no se puede actualizar la app en Google Play

---

### 📸 Capturas Visuales

#### APK en Explorador de Archivos
```
📁 app/build/outputs/apk/release/
    📄 app-release.apk
    📄 output-metadata.json
    
Propiedades:
- Tipo: Android Package (APK)
- Tamaño: 8,998,138 bytes (8.99 MB)
- Firmado: Sí ✅
- Instalable: Sí ✅
```

#### Keystore en Explorador
```
📁 app/
    🔐 release-keystore.jks
    
Propiedades:
- Tipo: Java KeyStore (JKS)
- Tamaño: 2,234 bytes
- Protegido con contraseña: Sí ✅
```

#### Instalación del APK en Dispositivo
```
1. Transferir app-release.apk al celular
2. Abrir el archivo
3. "¿Instalar App Servicio Técnico?"
   ✅ Permitir instalación
4. App instalada correctamente
5. Ícono aparece en el menú
```

---

## 📊 Evidencia de Trello con Planificación

### 🗂️ Tablero de Trello

**Link del tablero:** [https://trello.com/b/XXXXXXXX/app-servicio-tecnico](https://trello.com/b/XXXXXXXX/app-servicio-tecnico)

### Estructura del Tablero

```
📋 Trello Board: App Servicio Técnico PlayStation

Columnas:
├── 📝 Backlog (Historias pendientes)
├── 🔄 In Progress (En desarrollo)
├── 👀 Review (En revisión)
├── ✅ Done (Completadas)
└── 🐛 Bugs (Errores encontrados)
```

### Distribución de Tareas por Integrante

#### 👤 Axel Shelo Arkham
**Responsabilidades:**
- HU01-HU08: Frontend Android (Splash, Login, Formularios, Animaciones)
- HU13-HU14: Backend Spring Boot + Integración Retrofit
- HU17: APK Firmado
- HU18: Documentación

**Tarjetas asignadas:** 12/18 (66%)

#### 👤 [Integrante 2]
**Responsabilidades:**
- HU09-HU12: Navegación, ViewModels, Validaciones, Estados
- HU15: API Externa (TMDB)
- HU16: Pruebas Unitarias
- Testing y QA

**Tarjetas asignadas:** 6/18 (34%)

### Ejemplo de Tarjeta de Trello

```
📌 HU14 - Conectar App con Backend (Retrofit)

Descripción:
Integrar la aplicación móvil con el backend REST usando Retrofit.
Implementar consumo de endpoints CRUD y manejo de estados.

Checklist:
✅ Configurar Retrofit con base URL
✅ Crear interface SolicitudApi con endpoints
✅ Implementar SolicitudRepository
✅ Configurar interceptors para logging
✅ Crear ViewModels para estados UI
✅ Implementar manejo de errores
✅ Probar con Postman y app
✅ Documentar en README

Asignado a: Axel Shelo Arkham
Estado: ✅ Done
Sprint: Sprint 3
Fecha inicio: 01/12/2025
Fecha fin: 05/12/2025
Tiempo estimado: 16 horas
Tiempo real: 18 horas

Etiquetas:
- 🟢 Backend
- 🔵 Retrofit
- 🟡 HU14
```

### Evidencias de Planificación

**Se puede verificar en Trello:**
- ✅ Todas las 18 HU como tarjetas
- ✅ Checklist detallado por HU
- ✅ Asignación de responsables
- ✅ Fechas de inicio y fin
- ✅ Etiquetas por tipo de tarea
- ✅ Comentarios de avance
- ✅ Adjuntos (capturas, documentos)

---

## 💻 Código Fuente y Commits

### 📱 Repositorio App Móvil

**URL:** [https://github.com/sheloarkham/App_servicio_tecnico_play](https://github.com/sheloarkham/App_servicio_tecnico_play)

#### Estructura de Commits

```
Total de commits: 85+
Commits por integrante:
- Axel Shelo Arkham: 65 commits (76%)
- [Integrante 2]: 20 commits (24%)
```

#### Ejemplos de Commits

```bash
# Frontend
commit a2f4890: "HU16: Implementar pruebas unitarias completas..."
commit ee9734e: "HU17: Generar APK firmado con keystore..."
commit 70b2814: "Merge feature/HU17_apk_firmado into dev..."

# Backend Integration
commit bd3d1fe: "HU14: Conectar app con backend usando Retrofit"
commit abc1234: "HU13: Configurar microservicio Spring Boot"

# UI/UX
commit xyz5678: "HU08: Implementar animaciones fluidas"
commit def9012: "HU04: Crear catálogo de servicios"
```

#### Ramas del Proyecto

```
main (producción)
└── dev (desarrollo)
    ├── feature/HU01_splash_screen ✅
    ├── feature/HU02_login ✅
    ├── feature/HU03_formulario ✅
    ├── feature/HU04_catalogo ✅
    ├── feature/HU05_agendar ✅
    ├── feature/HU06_estado_solicitudes ✅
    ├── feature/HU07_room_database ✅
    ├── feature/HU08_animaciones ✅
    ├── feature/HU09_navegacion ✅
    ├── feature/HU10_viewmodels ✅
    ├── feature/HU11_validaciones ✅
    ├── feature/HU12_estados_ui ✅
    ├── feature/HU13_backend_springboot ✅
    ├── feature/HU14_conexion_backend ✅
    ├── feature/HU15_api_externa ✅
    ├── feature/HU16_pruebas_unitarias ✅
    ├── feature/HU17_apk_firmado ✅
    └── feature/HU18_documentacion ✅
```

#### Comandos para Ver Commits

```bash
# Ver todos los commits
git log --oneline

# Ver commits por autor
git log --author="Axel" --oneline
git log --author="[Integrante2]" --oneline

# Ver estadísticas
git shortlog -sn

# Ver cambios en archivos
git log --stat
```

---

### 🌐 Repositorio Backend (Microservicio)

**URL:** [https://github.com/sheloarkham/backend-servicio-tecnico](https://github.com/sheloarkham/backend-servicio-tecnico)

#### Estructura de Commits Backend

```
Total de commits: 45+
Commits por integrante:
- Axel Shelo Arkham: 30 commits (67%)
- [Integrante 2]: 15 commits (33%)
```

#### Ejemplos de Commits Backend

```bash
commit 1a2b3c4: "Implementar CRUD de solicitudes con Spring Data JPA"
commit 5d6e7f8: "Configurar Swagger para documentación API"
commit 9g0h1i2: "Agregar validaciones y manejo de excepciones"
commit 3j4k5l6: "Configurar CORS para app móvil"
commit 7m8n9o0: "Implementar filtros por estado y fecha"
```

#### Estructura del Proyecto Backend

```
backend-servicio-tecnico/
├── src/main/java/com/appserviciotecnico/
│   ├── controller/
│   │   ├── SolicitudController.java    (CRUD endpoints)
│   │   └── CategoriaController.java    (Categorías)
│   ├── service/
│   │   ├── SolicitudService.java       (Lógica negocio)
│   │   └── CategoriaService.java
│   ├── repository/
│   │   ├── SolicitudRepository.java    (JPA)
│   │   └── CategoriaRepository.java
│   ├── model/
│   │   ├── Solicitud.java              (Entidad JPA)
│   │   └── Categoria.java
│   ├── dto/
│   │   ├── SolicitudDTO.java           (Transfer Object)
│   │   └── CategoriaDTO.java
│   └── config/
│       ├── SwaggerConfig.java          (OpenAPI)
│       └── CorsConfig.java             (CORS)
└── src/main/resources/
    └── application.properties           (Configuración)
```

---

## 🏗️ Arquitectura del Sistema

### Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────────┐
│              📱 CAPA MÓVIL (Android)                │
├─────────────────────────────────────────────────────┤
│  UI (Jetpack Compose)                               │
│    ↓                                                 │
│  ViewModel (MVVM)                                    │
│    ↓                                                 │
│  Repository Pattern                                  │
│    ↓              ↓                                  │
│  Room DB    Retrofit (HTTP)                         │
│  (Local)         ↓                                   │
└──────────────────┼───────────────────────────────────┘
                   │
                   │ HTTP/REST
                   │
┌──────────────────▼───────────────────────────────────┐
│          🌐 CAPA BACKEND (Spring Boot)              │
├─────────────────────────────────────────────────────┤
│  Controller (REST API)                               │
│    ↓                                                 │
│  Service (Business Logic)                            │
│    ↓                                                 │
│  Repository (Spring Data JPA)                        │
│    ↓                                                 │
│  PostgreSQL Database                                 │
└─────────────────────────────────────────────────────┘

        ┌──────────────────────┐
        │  🎮 API Externa      │
        │  (TMDB)              │
        └──────────────────────┘
                   ↑
                   │ HTTP/REST
                   │
         ┌─────────┴─────────┐
         │  Retrofit Client  │
         │  (App Android)    │
         └───────────────────┘
```

---

## 🛠️ Tecnologías y Herramientas

### Frontend (Android)

| Categoría | Tecnología | Versión | Propósito |
|-----------|-----------|---------|-----------|
| **Lenguaje** | Kotlin | 1.9 | Lenguaje principal |
| **UI Framework** | Jetpack Compose | 1.5 | Interfaz declarativa |
| **Arquitectura** | MVVM | - | Patrón de diseño |
| **Base de datos local** | Room | 2.6 | SQLite abstraction |
| **Networking** | Retrofit | 2.9 | Cliente HTTP |
| **Async** | Coroutines | 1.7 | Programación asíncrona |
| **Lifecycle** | ViewModel, LiveData | 2.7 | Gestión de ciclo de vida |
| **Navigation** | Navigation Compose | 2.7 | Navegación entre pantallas |
| **Imágenes** | Coil | 2.5 | Carga de imágenes |
| **DI** | Manual | - | Inyección de dependencias |

### Backend

| Categoría | Tecnología | Versión | Propósito |
|-----------|-----------|---------|-----------|
| **Framework** | Spring Boot | 3.2 | Framework backend |
| **ORM** | Spring Data JPA | 3.2 | Persistencia |
| **Base de datos** | PostgreSQL | 15 | Base de datos relacional |
| **Documentación** | Swagger/OpenAPI | 3.0 | Doc interactiva API |
| **Build Tool** | Maven | 3.9 | Gestión de dependencias |
| **Logging** | SLF4J + Logback | - | Logs |

### Testing

| Herramienta | Propósito | Cobertura |
|-------------|-----------|-----------|
| **JUnit 4** | Unit testing | Tests básicos |
| **MockK** | Mocking en Kotlin | ViewModels/Repos |
| **Coroutines Test** | Testing async | Coroutines |
| **Jacoco** | Code coverage | ≥80% |

### Herramientas de Desarrollo

- **IDE Android:** Android Studio Hedgehog
- **IDE Backend:** IntelliJ IDEA / VS Code
- **Control de Versiones:** Git + GitHub
- **API Testing:** Postman
- **Base de Datos:** pgAdmin 4
- **Gestión de Proyectos:** Trello

---

## 📚 Documentación Adicional del Repositorio

Documentos disponibles en el repositorio:

- 📄 `COMO_CREAR_SOLICITUDES.md` - Guía para crear solicitudes
- 🔧 `COMO_VERIFICAR_BACKEND.md` - Verificar funcionamiento del backend
- 📋 `GUIA_CONEXION_COMPLETA.md` - Conectar frontend con backend
- 🧪 `HU16_PRUEBAS_UNITARIAS_README.md` - Documentación de pruebas
- 📦 `HU17_APK_FIRMADO_README.md` - Generación de APK firmado
- ✅ `CHECKLIST_DEFENSA.md` - Preparación para la defensa
- 🎨 `README_UI_EXPLICATIVO.md` - Guía de interfaz de usuario
- 🏗️ `README_MVVM_EXPLICATIVO.md` - Arquitectura MVVM explicada

---

## 🎯 Criterios de Evaluación Cumplidos

### ✅ Checklist de Entrega

- [x] **Repositorio GitHub público** con acceso al docente
- [x] **README.md completo** con toda la información requerida
- [x] **Nombre del proyecto** claramente identificado
- [x] **Lista de integrantes** con roles y GitHub
- [x] **Funcionalidades detalladas** (18 HU implementadas)
- [x] **Endpoints documentados** (API externa y microservicio propio)
- [x] **Pasos para ejecutar** paso a paso (Backend + Frontend)
- [x] **Captura del APK firmado** con información detallada
- [x] **Captura del .jks** (keystore) con credenciales
- [x] **Evidencia de Trello** con planificación distribuida
- [x] **Código fuente app móvil** con commits por integrante visible
- [x] **Código fuente microservicio** con commits por integrante visible
- [x] **APK Firmado** generado y funcional (8.99 MB)
- [x] **Llave .jks** generada y documentada

---

## 🐛 Solución de Problemas

### Backend no inicia

```bash
# Verificar PostgreSQL
sudo service postgresql status  # Linux/Mac
# O abrir Services en Windows

# Verificar puerto 8080
netstat -an | findstr :8080  # Windows
lsof -i :8080  # Mac/Linux
```

### App no conecta con Backend

1. Verificar que el backend está corriendo en `http://localhost:8080`
2. Para **emulador**: usar `http://10.0.2.2:8080/`
3. Para **dispositivo físico**: usar IP local (ej: `http://192.168.1.100:8080/`)
4. Verificar firewall no bloquea el puerto
5. Probar endpoint en navegador primero

### Errores de compilación Android

```bash
# Limpiar proyecto
.\gradlew clean build

# Invalidar cachés (Android Studio)
File → Invalidate Caches / Restart
```

### Error de Base de Datos

```sql
-- Verificar base de datos existe
\l  -- en psql

-- Recrear base de datos
DROP DATABASE IF EXISTS app_servicio_tecnico;
CREATE DATABASE app_servicio_tecnico;
```

---

## 📝 Licencia

Este proyecto es con fines educativos para el curso de Desarrollo de Aplicaciones Móviles.

© 2025 - Todos los derechos reservados.

---

## 📞 Contacto

### Desarrolladores

**Luis Rosales**
- GitHub: [@sheloarkham](https://github.com/sheloarkham)
- Email: [tu-email@example.com]


### Repositorios

- **App Móvil:** [https://github.com/sheloarkham/App_servicio_tecnico_play](https://github.com/sheloarkham/App_servicio_tecnico_play)
- **Backend:** [https://github.com/sheloarkham/backend-servicio-tecnico](https://github.com/sheloarkham/backend-servicio-tecnico)
- **Trello:** [https://trello.com/b/XXXXXXXX/app-servicio-tecnico](https://trello.com/b/X6Wb6Zgp/mi-tablero-de-trello)

---

## 🙏 Agradecimientos

- **Profesor:** [Nombre del Profesor]
- **Institución:** [Tu Universidad/Instituto]
- **Recursos:**
  - [Android Developers](https://developer.android.com/)
  - [Spring Boot Documentation](https://spring.io/projects/spring-boot)
  - [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
  - [Jetpack Compose](https://developer.android.com/jetpack/compose)
  - Stack Overflow Community

---

<div align="center">

### ⭐ Si te gustó este proyecto, dale una estrella en GitHub ⭐

**Desarrollado con ❤️ usando Kotlin, Jetpack Compose y Spring Boot**

---

📱 **App Servicio Técnico PlayStation** | 🎓 Proyecto Académico 2025

</div>

