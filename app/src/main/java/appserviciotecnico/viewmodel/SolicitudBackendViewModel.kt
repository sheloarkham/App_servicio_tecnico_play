package appserviciotecnico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import appserviciotecnico.network.models.SolicitudDTO
import appserviciotecnico.network.repository.SolicitudRemoteRepository
import appserviciotecnico.network.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel: Gestiona el estado de las solicitudes y la comunicación con el backend
 * Expone estados reactivos (StateFlow) para que la UI los observe
 */
class SolicitudBackendViewModel : ViewModel() {

    private val repository = SolicitudRemoteRepository()

    // Estado de la lista de solicitudes
    private val _solicitudesState = MutableStateFlow<NetworkResult<List<SolicitudDTO>>>(NetworkResult.Idle)
    val solicitudesState: StateFlow<NetworkResult<List<SolicitudDTO>>> = _solicitudesState.asStateFlow()

    // Estado de creación de solicitud
    private val _crearSolicitudState = MutableStateFlow<NetworkResult<SolicitudDTO>>(NetworkResult.Idle)
    val crearSolicitudState: StateFlow<NetworkResult<SolicitudDTO>> = _crearSolicitudState.asStateFlow()

    // Estado de actualización
    private val _actualizarState = MutableStateFlow<NetworkResult<SolicitudDTO>>(NetworkResult.Idle)
    val actualizarState: StateFlow<NetworkResult<SolicitudDTO>> = _actualizarState.asStateFlow()

    // Estado de eliminación
    private val _eliminarState = MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Idle)
    val eliminarState: StateFlow<NetworkResult<Unit>> = _eliminarState.asStateFlow()

    /**
     * Obtener todas las solicitudes del backend
     */
    fun obtenerSolicitudes() {
        viewModelScope.launch {
            _solicitudesState.value = NetworkResult.Loading

            val result = repository.obtenerSolicitudes()

            _solicitudesState.value = result.fold(
                onSuccess = { NetworkResult.Success(it) },
                onFailure = { NetworkResult.Error(it.message ?: "Error desconocido", it as? Exception) }
            )
        }
    }

    /**
     * Crear nueva solicitud
     */
    fun crearSolicitud(solicitud: SolicitudDTO) {
        viewModelScope.launch {
            _crearSolicitudState.value = NetworkResult.Loading

            val result = repository.crearSolicitud(solicitud)

            _crearSolicitudState.value = result.fold(
                onSuccess = { NetworkResult.Success(it) },
                onFailure = { NetworkResult.Error(it.message ?: "Error al crear", it as? Exception) }
            )

            // Recargar la lista después de crear
            if (result.isSuccess) {
                obtenerSolicitudes()
            }
        }
    }

    /**
     * Actualizar solicitud existente
     */
    fun actualizarSolicitud(id: Long, solicitud: SolicitudDTO) {
        viewModelScope.launch {
            _actualizarState.value = NetworkResult.Loading

            val result = repository.actualizarSolicitud(id, solicitud)

            _actualizarState.value = result.fold(
                onSuccess = { NetworkResult.Success(it) },
                onFailure = { NetworkResult.Error(it.message ?: "Error al actualizar", it as? Exception) }
            )

            // Recargar la lista después de actualizar
            if (result.isSuccess) {
                obtenerSolicitudes()
            }
        }
    }

    /**
     * Eliminar solicitud
     */
    fun eliminarSolicitud(id: Long) {
        viewModelScope.launch {
            _eliminarState.value = NetworkResult.Loading

            val result = repository.eliminarSolicitud(id)

            _eliminarState.value = result.fold(
                onSuccess = { NetworkResult.Success(Unit) },
                onFailure = { NetworkResult.Error(it.message ?: "Error al eliminar", it as? Exception) }
            )

            // Recargar la lista después de eliminar
            if (result.isSuccess) {
                obtenerSolicitudes()
            }
        }
    }

    /**
     * Obtener solicitudes por estado
     */
    fun obtenerPorEstado(estado: String) {
        viewModelScope.launch {
            _solicitudesState.value = NetworkResult.Loading

            val result = repository.obtenerPorEstado(estado)

            _solicitudesState.value = result.fold(
                onSuccess = { NetworkResult.Success(it) },
                onFailure = { NetworkResult.Error(it.message ?: "Error", it as? Exception) }
            )
        }
    }

    /**
     * Resetear estados
     */
    fun resetCrearState() {
        _crearSolicitudState.value = NetworkResult.Idle
    }

    fun resetActualizarState() {
        _actualizarState.value = NetworkResult.Idle
    }

    fun resetEliminarState() {
        _eliminarState.value = NetworkResult.Idle
    }
}

