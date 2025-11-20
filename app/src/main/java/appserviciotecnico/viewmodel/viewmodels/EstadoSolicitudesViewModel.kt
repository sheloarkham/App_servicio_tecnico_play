package appserviciotecnico.viewmodel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import appserviciotecnico.model.data.entities.SolicitudEntity
import appserviciotecnico.model.data.repository.SolicitudRepository
import appserviciotecnico.model.domain.models.Solicitud
import appserviciotecnico.model.domain.usecases.ObtenerSolicitudesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel para gestionar estado de solicitudes
class EstadoSolicitudesViewModel(
    private val obtenerSolicitudesUseCase: ObtenerSolicitudesUseCase,
    private val repository: SolicitudRepository  // Solo para eliminar
) : ViewModel() {

    // Flow que obtiene las solicitudes usando el UseCase
    val solicitudes: StateFlow<List<Solicitud>> = obtenerSolicitudesUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 🗑️ Eliminar una solicitud
    fun eliminarSolicitud(solicitud: Solicitud) {
        viewModelScope.launch {
            val entity = SolicitudEntity(
                id = solicitud.id.toLong(),
                servicio = solicitud.servicio,
                fechaAgendada = solicitud.fechaAgendada.time,
                estado = solicitud.estado.texto,
                clienteNombre = solicitud.clienteNombre,
                descripcion = solicitud.descripcion,
                horaAgendada = solicitud.horaAgendada,
                categoriaId = 0  // Valor por defecto si no está disponible
            )
            repository.eliminarSolicitud(entity)
        }
    }
}