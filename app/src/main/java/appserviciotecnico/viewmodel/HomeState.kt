package appserviciotecnico.viewmodel

// 🏠 Estado de la pantalla de inicio
data class HomeState(
    val nombreUsuario: String = "Invitado",
    val isAuthenticated: Boolean = false,
    val solicitudesPendientes: Int = 0,
    val solicitudesEnProceso: Int = 0,
    val solicitudesCompletadas: Int = 0,
    val proximaCita: ProximaCita? = null,
    val isLoading: Boolean = false
)

data class ProximaCita(
    val servicio: String,
    val fecha: String,
    val hora: String
)
