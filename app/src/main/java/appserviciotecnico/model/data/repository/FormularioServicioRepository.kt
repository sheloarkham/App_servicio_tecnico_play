package appserviciotecnico.model.data.repository

import appserviciotecnico.model.data.dao.FormularioServicioDao
import appserviciotecnico.model.data.entities.FormularioServicioEntity

// Repositorio para gestionar formularios de servicio técnico
class FormularioServicioRepository(
    private val dao: FormularioServicioDao
) {

    fun obtenerFormularios() = dao.getFormularios()

    suspend fun guardarFormulario(entity: FormularioServicioEntity): Long {
        return dao.insertFormulario(entity)
    }

    suspend fun limpiar() = dao.deleteAll()
}
