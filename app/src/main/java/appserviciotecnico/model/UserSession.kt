package appserviciotecnico.model

// Modelo para representar el estado de autenticación del usuario
@Suppress("unused")
data class UserSession(
    val isAuthenticated: Boolean = false,
    val isGuest: Boolean = false,
    val email: String? = null
) {
    @Suppress("unused")
    companion object {
        // Sesión de invitado - Preparado para uso futuro
        fun guest() = UserSession(
            isAuthenticated = false,
            isGuest = true,
            email = null
        )

        // Sesión autenticada - Preparado para uso futuro
        fun authenticated(email: String) = UserSession(
            isAuthenticated = true,
            isGuest = false,
            email = email
        )

        // Sin sesión - Preparado para uso futuro
        fun none() = UserSession(
            isAuthenticated = false,
            isGuest = false,
            email = null
        )
    }
}

