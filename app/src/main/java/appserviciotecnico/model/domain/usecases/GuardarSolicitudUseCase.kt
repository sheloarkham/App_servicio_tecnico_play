package appserviciotecnico.model.domain.usecases

import appserviciotecnico.model.entities.SolicitudEntity
import appserviciotecnico.model.repository.SolicitudRepository

/**
 * 📋 Caso de uso: Guardar solicitud de servicio
 * Encapsula la lógica de negocio para crear una solicitud
 */
class GuardarSolicitudUseCase(
    private val repository: SolicitudRepository
) {

    suspend fun execute(
        servicioId: Int,
        servicioNombre: String,
        fecha: String,
        hora: String,
        descripcion: String
    ): Result<Long> {
        return try {
            // Validar datos antes de guardar
            if (servicioNombre.isBlank()) {
                return Result.failure(Exception("El servicio es requerido"))
            }

            if (fecha.isBlank() || hora.isBlank()) {
                return Result.failure(Exception("Fecha y hora son requeridas"))
            }

            // Crear entidad
            val solicitud = SolicitudEntity(
                servicioId = servicioId,
                servicioNombre = servicioNombre,
                fecha = fecha,
                hora = hora,
                estado = "PENDIENTE",
                descripcion = descripcion,
                clienteNombre = "" // Se puede agregar desde sesión
            )

            // Guardar en repositorio
            val id = repository.guardarSolicitud(solicitud)
            Result.success(id)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

