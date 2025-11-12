# 🎮 App Servicio Técnico PlayStation

Aplicación móvil para gestión de servicios técnicos de PlayStation 4 y PlayStation 5.

## 👥 Equipo de Desarrollo
- [Nombre Estudiante 1]
- [Nombre Estudiante 2]

## 📱 Descripción del Proyecto

Aplicación Android desarrollada en Kotlin con Jetpack Compose que permite a los usuarios:
- Agendar servicios técnicos para consolas PS4 y PS5
- Ver catálogo de servicios disponibles
- Gestionar solicitudes de servicio
- Seguimiento del estado de las reparaciones

## ✨ Funcionalidades Implementadas

### HU01 - Pantalla de Inicio (Splash Screen)
✅ Logo animado de la aplicación al iniciar  
✅ Transición automática a pantalla de login  
✅ Animación de fade-in/out

### HU02 - Login con Validación
✅ Formulario de inicio de sesión  
✅ Validación de campos (correo y contraseña)  
✅ Opción de ingresar como invitado  
✅ Mensajes de error visuales  
✅ Animaciones en formularios

### HU03 - Formulario de Solicitud de Servicio
✅ Campos para información del cliente  
✅ Validación de formularios  
✅ Selección de región  
✅ Mensajes de confirmación

### HU04 - Catálogo de Servicios
✅ Listado de categorías de servicios  
✅ Cards con información detallada  
✅ Navegación a agendamiento de servicios  
✅ Animaciones de entrada escalonadas

### HU05 - Agendar Servicio Técnico
✅ Selector de fecha (DatePicker)  
✅ Selector de hora (TimePicker)  
✅ Validación de horario laboral (L-S 10:00-18:00)  
✅ Confirmación de cita  
✅ Persistencia en base de datos local (Room)  
✅ Botones con animaciones de pulsación

### HU06 - Visualizar Estado de Solicitudes
✅ Lista de solicitudes guardadas  
✅ Estados con colores distintivos (Pendiente, En Proceso, Completado)  
✅ Detalles de cada solicitud  
✅ Badges animados para estados

### HU07 - Persistencia Local con Room
✅ Base de datos local con Room  
✅ Repositorio para gestión de datos  
✅ DAOs para operaciones CRUD  
✅ Integración con ViewModels  
✅ Arquitectura MVVM

### HU08 - Animaciones Funcionales ⭐ NUEVO
✅ Animaciones de transición entre pantallas (slide, fade)  
✅ Botones interactivos con efecto de pulsación y escala  
✅ TextFields con animación de shake en errores  
✅ Mensajes animados de éxito/error  
✅ Cards con animación de entrada deslizante  
✅ Loading indicators animados  
✅ Badges con efecto de pulsación  
✅ Animaciones en diálogos y modales  
✅ Transiciones suaves en LazyColumns  
✅ Efectos visuales en navegación drawer

## 🎨 Animaciones Implementadas

### Transiciones entre Pantallas
- **Fade In/Out**: Para splash screen y pantallas principales
- **Slide**: Navegación horizontal y vertical entre vistas
- **Scale**: Zoom suave en elementos destacados

### Componentes Interactivos
- **AnimatedButton**: Botones con efecto bounce al presionar
- **AnimatedTextField**: Campos con shake en errores
- **AnimatedMessage**: Mensajes deslizantes de notificación
- **AnimatedCard**: Cards con entrada animada
- **AnimatedBadge**: Badges pulsantes para estados

### Efectos Visuales
- **Staggered Animation**: Elementos de lista con delay progresivo
- **Spring Animation**: Movimientos naturales con rebote
- **Infinite Pulse**: Pulsación continua para elementos importantes

## 🏗️ Arquitectura del Proyecto

```
app/
├── model/
│   ├── data/
│   │   ├── AppDatabase.kt
│   │   ├── SolicitudDao.kt
│   │   └── FormularioServicioDao.kt
│   ├── entities/
│   │   ├── SolicitudEntity.kt
│   │   └── FormularioServicioEntity.kt
│   ├── repository/
│   │   ├── SolicitudRepository.kt
│   │   └── FormularioServicioRepository.kt
│   └── CategoriaServicio.kt
├── ui/
│   ├── components/
│   │   ├── AnimatedComponents.kt ⭐ NUEVO
│   │   ├── CategoriaCard.kt
│   │   ├── InputText.kt
│   │   └── SolicitudCard.kt
│   ├── screen/
│   │   ├── StartScreen.kt
│   │   ├── LoginScreen.kt ⭐ ACTUALIZADO
│   │   ├── HomeScreen.kt
│   │   ├── CatalogoServiciosScreen.kt ⭐ ACTUALIZADO
│   │   ├── AgendarServicioScreen.kt ⭐ ACTUALIZADO
│   │   ├── EstadoSolicitudesScreen.kt
│   │   └── FormularioServicioScreen.kt
│   ├── theme/
│   │   └── AppServTecnicoTheme.kt
│   └── navigation/
│       ├── AppNav.kt ⭐ ACTUALIZADO
│       └── Routes.kt
└── viewmodel/
    └── HomeViewModel.kt
```

## 🛠️ Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación principal
- **Jetpack Compose**: UI declarativa moderna
- **Compose Animation API**: Sistema completo de animaciones ⭐
- **Room Database**: Persistencia local de datos
- **Navigation Compose**: Navegación entre pantallas
- **Coroutines**: Programación asíncrona
- **ViewModel**: Gestión de estado
- **Material Design 3**: Componentes de UI

## 📦 Dependencias Principales

```kotlin
// Jetpack Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.animation:animation")

// Navigation
implementation("androidx.navigation:navigation-compose:2.8.0")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
```

## 🚀 Pasos para Ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/sheloarkham/App_servicio_tecnico_play.git
   ```

2. **Abrir en Android Studio**
   - Android Studio Hedgehog o superior
   - SDK mínimo: API 24 (Android 7.0)
   - SDK objetivo: API 34 (Android 14)

3. **Sincronizar Gradle**
   ```
   Build → Rebuild Project
   ```

4. **Ejecutar en emulador o dispositivo físico**
   ```
   Run → Run 'app'
   ```

## 📋 Requisitos del Sistema

- **Versión mínima de Android**: 7.0 (API 24)
- **Versión objetivo**: Android 14 (API 34)
- **Compilación**: Kotlin 1.9.0+
- **Gradle**: 8.0+

## 🎯 Funcionalidades Destacadas

### ✅ Interfaz Visual
- Diseño coherente con Material Design 3
- Tema azulado neón personalizado
- Navegación drawer lateral
- TopAppBar en todas las pantallas

### ✅ Validaciones
- Campos requeridos validados
- Mensajes de error claros con animación de shake
- Validación de formato de correo
- Restricciones de horario (L-S 10:00-18:00)

### ✅ Persistencia
- Almacenamiento local con Room
- Operaciones CRUD completas
- Sincronización con UI en tiempo real
- Repository pattern

### ✅ Animaciones
- Transiciones suaves entre pantallas
- Feedback visual inmediato en interacciones
- Efectos de entrada/salida animados
- Animaciones de lista escalonadas
- Spring animations para movimientos naturales

## 🔄 Control de Versiones

- **GitHub**: [App_servicio_tecnico_play](https://github.com/sheloarkham/App_servicio_tecnico_play)
- **Ramas principales**:
  - `main`: Producción estable
  - `dev`: Desarrollo activo
  - `feature/HU08_animaciones`: Implementación de animaciones ⭐ ACTUAL

## 📝 Convenciones de Commits

Formato:
```
feat(scope): breve descripción

- Detalle 1
- Detalle 2

Closes #HU
```

## 📅 Historial de Versiones

### v0.8.0 - HU08: Animaciones ⭐ EN DESARROLLO
- ✅ Transiciones animadas entre pantallas con slide y fade
- ✅ Componentes animados reutilizables (AnimatedButton, AnimatedTextField, etc.)
- ✅ Efectos de shake en campos con error
- ✅ Mensajes animados de confirmación/error
- ✅ Animaciones escalonadas en listas
- ✅ Diálogos con animaciones de entrada
- ✅ Documentación completa en README

### v0.7.0 - HU07: Persistencia Local
- Implementación de Room Database
- Repository pattern
- CRUD completo de solicitudes

### v0.6.0 - HU06: Estado de Solicitudes
- Visualización de solicitudes guardadas
- Estados con colores
- Detalles de solicitudes

### v0.5.0 - HU05: Agendar Servicio
- Date/Time pickers
- Validaciones de horario
- Confirmación de citas

### v0.4.0 - HU04: Catálogo de Servicios
- Categorías de servicios
- Cards informativas
- Navegación a agendamiento

### v0.3.0 - HU03: Formulario de Solicitud
- Formulario completo
- Validaciones
- Selector de región

### v0.2.0 - HU02: Login
- Autenticación básica
- Validaciones
- Modo invitado

### v0.1.0 - HU01: Splash Screen
- Logo animado
- Transición automática

## 📞 Contacto

Para más información sobre el proyecto, contactar a través del repositorio de GitHub.

## 📄 Licencia

Proyecto académico - DUOC UC 2025
