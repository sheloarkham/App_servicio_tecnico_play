package appserviciotecnico.navigation

// Objeto que define todas las rutas de navegación de la aplicación
object Routes {
    const val Start = "start"        // Logo inicial
    const val Login = "login"        // Pantalla de login
    const val Home = "home"          // Pantalla principal después del login
    const val Form = "form"          // Formulario de servicio técnico
    const val Catalogo = "catalogo"  // Catálogo de servicios disponibles
    const val Agendar = "agendar"    // Agendar servicio técnico
    const val Estado = "estado"      // Estado de solicitudes
    const val SolicitudBackend = "solicitud_backend"  // Gestión de solicitudes con backend

    // Función helper para crear ruta con argumentos
    fun agendarConCategoria(categoriaId: Int, categoriaNombre: String): String {
        return "$Agendar/$categoriaId/$categoriaNombre"
    }
}

