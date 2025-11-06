package appserviciotecnico.model

// Modelo para representar el estado de autenticación del usuario
data class UserSession(
    val isAuthenticated: Boolean = false,
    val isGuest: Boolean = false,
    val email: String? = null
) {
    companion object {
        // Sesión de invitado
        fun guest() = UserSession(
            isAuthenticated = false,
            isGuest = true,
            email = null
        )

        // Sesión autenticada
        fun authenticated(email: String) = UserSession(
            isAuthenticated = true,
            isGuest = false,
            email = email
        )

        // Sin sesión
        fun none() = UserSession(
            isAuthenticated = false,
            isGuest = false,
            email = null
        )
    }
}

