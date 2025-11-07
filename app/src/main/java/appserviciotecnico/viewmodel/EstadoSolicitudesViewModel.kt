package appserviciotecnico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import appserviciotecnico.model.Solicitud
import appserviciotecnico.model.EstadoSolicitud
import appserviciotecnico.model.entities.SolicitudEntity
import appserviciotecnico.model.repository.SolicitudRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.*

// 🎮 ViewModel para gestionar estado de solicitudes
class EstadoSolicitudesViewModel(
    repository: SolicitudRepository
) : ViewModel() {

    // Flow que convierte SolicitudEntity a Solicitud para la UI
    val solicitudes: StateFlow<List<Solicitud>> = repository.obtenerSolicitudes()
        .map { entities ->
            entities.map { entity ->
                Solicitud(
                    id = entity.id.toInt(),
                    servicio = entity.servicio,
                    fechaAgendada = Date(entity.fechaAgendada),
                    estado = when (entity.estado) {
                        "Pendiente" -> EstadoSolicitud.PENDIENTE
                        "En Proceso" -> EstadoSolicitud.EN_PROCESO
                        "Completado" -> EstadoSolicitud.COMPLETADO
                        "Cancelado" -> EstadoSolicitud.CANCELADO
                        else -> EstadoSolicitud.PENDIENTE
                    },
                    clienteNombre = entity.clienteNombre,
                    descripcion = entity.descripcion,
                    horaAgendada = entity.horaAgendada
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

