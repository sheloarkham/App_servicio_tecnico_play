package appserviciotecnico.model.domain.usecases

import appserviciotecnico.model.data.repository.SolicitudRepository
import appserviciotecnico.model.domain.models.EstadoSolicitud
import appserviciotecnico.model.domain.models.Solicitud
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

/**
 * Caso de uso: Obtener lista de solicitudes
 * Encapsula la lógica para recuperar y transformar solicitudes del usuario
 */
class ObtenerSolicitudesUseCase(
    private val repository: SolicitudRepository
) {

    /**
     * Obtiene todas las solicitudes como Flow, convertidas a modelo de dominio
     */
    operator fun invoke(): Flow<List<Solicitud>> {
        return repository.obtenerSolicitudes()
            .map { entities ->
                entities.mapNotNull { entity ->
                    try {
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
                    } catch (_: Exception) {
                        null
                    }
                }
            }
    }
}

