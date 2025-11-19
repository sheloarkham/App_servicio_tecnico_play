package appserviciotecnico.model.data.repository

import appserviciotecnico.model.data.dao.SolicitudDao
import appserviciotecnico.model.data.entities.SolicitudEntity

// Repositorio para gestionar solicitudes/citas de servicio
class SolicitudRepository(
    private val dao: SolicitudDao
) {

    fun obtenerSolicitudes() = dao.getSolicitudes()

    fun obtenerSolicitudesPorEstado(estado: String) = dao.getSolicitudesByEstado(estado)

    suspend fun obtenerSolicitudPorId(id: Long): SolicitudEntity? {
        return dao.getSolicitudById(id)
    }

    suspend fun guardarSolicitud(entity: SolicitudEntity): Long {
        return dao.insertSolicitud(entity)
    }

    suspend fun actualizarSolicitud(entity: SolicitudEntity) {
        dao.updateSolicitud(entity)
    }

    suspend fun eliminarSolicitud(entity: SolicitudEntity) {
        dao.deleteSolicitud(entity)
    }

    suspend fun limpiar() = dao.deleteAll()
}

