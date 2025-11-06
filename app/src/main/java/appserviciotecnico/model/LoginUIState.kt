package appserviciotecnico.model

// 🔐 Estados de UI para el login
sealed class LoginUIState {
    // Estado inicial
    object Idle : LoginUIState()

    // Procesando login
    object Loading : LoginUIState()

    // Login exitoso
    data class Success(val session: UserSession) : LoginUIState()

    // Error en login
    data class Error(val message: String) : LoginUIState()

    // Errores de validación de campos
    data class ValidationError(val errors: Map<String, String>) : LoginUIState()

    // Usuario ingresó como invitado
    object GuestMode : LoginUIState()
}

