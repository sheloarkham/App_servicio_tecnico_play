package appserviciotecnico.model.domain.usecases

import appserviciotecnico.model.data.entities.SolicitudEntity
import appserviciotecnico.model.data.repository.SolicitudRepository

/**
 * Caso de uso: Guardar solicitud de servicio
 * Encapsula la lógica de negocio para crear una solicitud
 */
class GuardarSolicitudUseCase(
    private val repository: SolicitudRepository
) {

    suspend fun execute(
        servicio: String,
        categoriaId: Int,
        fechaAgendada: Long,  // Timestamp
        horaAgendada: String,
        descripcion: String,
        clienteNombre: String = ""
    ): Result<Long> {
        return try {
            // Validar datos antes de guardar
            if (servicio.isBlank()) {
                return Result.failure(Exception("El servicio es requerido"))
            }

            if (horaAgendada.isBlank()) {
                return Result.failure(Exception("La hora es requerida"))
            }

            // Crear entidad
            val solicitud = SolicitudEntity(
                servicio = servicio,
                categoriaId = categoriaId,
                fechaAgendada = fechaAgendada,
                horaAgendada = horaAgendada,
                estado = "PENDIENTE",
                descripcion = descripcion,
                clienteNombre = clienteNombre
            )

            // Guardar en repositorio
            val id = repository.guardarSolicitud(solicitud)
            Result.success(id)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

