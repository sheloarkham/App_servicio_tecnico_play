package appserviciotecnico.model.repository

import appserviciotecnico.model.data.FormularioServicioDao
import appserviciotecnico.model.entities.FormularioServicioEntity

// 📦 Repositorio para gestionar formularios de servicio técnico
class FormularioServicioRepository(
    private val dao: FormularioServicioDao
) {

    fun obtenerFormularios() = dao.getFormularios()

    suspend fun guardarFormulario(entity: FormularioServicioEntity): Long {
        return dao.insertFormulario(entity)
    }

    suspend fun limpiar() = dao.deleteAll()
}
