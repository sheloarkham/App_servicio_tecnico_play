package appserviciotecnico.model.domain.usecases

import appserviciotecnico.model.entities.SolicitudEntity
import appserviciotecnico.model.repository.SolicitudRepository
import kotlinx.coroutines.flow.Flow

/**
 * 📊 Caso de uso: Obtener lista de solicitudes
 * Encapsula la lógica para recuperar solicitudes del usuario
 */
class ObtenerSolicitudesUseCase(
    private val repository: SolicitudRepository
) {

    /**
     * Obtiene todas las solicitudes como Flow (observables)
     */
    fun execute(): Flow<List<SolicitudEntity>> {
        return repository.obtenerSolicitudes()
    }

    /**
     * Obtiene solicitudes filtradas por estado
     */
    fun obtenerPorEstado(estado: String): Flow<List<SolicitudEntity>> {
        return repository.obtenerSolicitudesPorEstado(estado)
    }
}

