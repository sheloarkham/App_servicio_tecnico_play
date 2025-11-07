package appserviciotecnico.model.repository

import appserviciotecnico.model.data.SolicitudDao
import appserviciotecnico.model.entities.SolicitudEntity

// 📦 Repositorio para gestionar solicitudes/citas de servicio
class SolicitudRepository(
    private val dao: SolicitudDao
) {

    fun obtenerSolicitudes() = dao.getSolicitudes()

    fun obtenerSolicitudesPorEstado(estado: String) = dao.getSolicitudesByEstado(estado)

    suspend fun guardarSolicitud(entity: SolicitudEntity): Long {
        return dao.insertSolicitud(entity)
    }

    suspend fun limpiar() = dao.deleteAll()
}

