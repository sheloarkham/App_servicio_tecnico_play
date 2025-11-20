package appserviciotecnico.model.domain.usecases

import appserviciotecnico.model.data.entities.FormularioServicioEntity
import appserviciotecnico.model.data.repository.FormularioServicioRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Caso de uso: Guardar cotización de servicio técnico
 * Encapsula la lógica de negocio para crear una solicitud de cotización
 */
class GuardarCotizacionUseCase(
    private val repository: FormularioServicioRepository
) {

    suspend fun execute(
        nombreCliente: String,
        correoCliente: String,
        telefonoCliente: String,
        tipoConsola: String,
        modeloConsola: String,
        descripcionProblema: String
    ): Result<Long> {
        return try {
            // Validar datos básicos
            if (nombreCliente.isBlank()) {
                return Result.failure(Exception("El nombre del cliente es requerido"))
            }

            if (correoCliente.isBlank() || !correoCliente.contains("@")) {
                return Result.failure(Exception("El correo electrónico es inválido"))
            }

            // Crear la entidad con la lógica de negocio
            val entity = FormularioServicioEntity(
                nombreCliente = nombreCliente,
                correoCliente = correoCliente,
                telefonoCliente = telefonoCliente,
                tipoConsola = tipoConsola,
                modeloConsola = modeloConsola,
                descripcionProblema = descripcionProblema,
                estadoSolicitud = "Pendiente",
                fechaSolicitud = SimpleDateFormat(
                    "dd 'de' MMMM 'de' yyyy HH:mm",
                    Locale.forLanguageTag("es-ES")
                ).format(Date())
            )

            // Guardar en el repositorio
            val id = repository.guardarFormulario(entity)
            Result.success(id)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

