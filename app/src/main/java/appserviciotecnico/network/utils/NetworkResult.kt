package appserviciotecnico.network.utils

/**
 * Clase sellada para representar los diferentes estados de las operaciones de red
 * Permite manejar loading, success y error de forma elegante en la UI
 */
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val exception: Exception? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
    object Idle : NetworkResult<Nothing>()
}

