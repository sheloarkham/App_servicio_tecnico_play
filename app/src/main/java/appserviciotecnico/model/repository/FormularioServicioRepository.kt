package appserviciotecnico.model.repository

import appserviciotecnico.model.data.FormularioServicioDao
import appserviciotecnico.model.entities.FormularioServicioEntity
import kotlinx.coroutines.flow.Flow

// 📦 Repositorio para gestionar formularios de servicio técnico
class FormularioServicioRepository(
    private val dao: FormularioServicioDao
) {

    // Obtener todos los formularios como Flow
    val allFormularios: Flow<List<FormularioServicioEntity>> = dao.getAllFormularios()

    // Insertar un nuevo formulario
    suspend fun insertFormulario(formulario: FormularioServicioEntity): Long {
        return dao.insert(formulario)
    }

    // Actualizar un formulario existente
    suspend fun updateFormulario(formulario: FormularioServicioEntity) {
        dao.update(formulario)
    }

    // Eliminar un formulario
    suspend fun deleteFormulario(formulario: FormularioServicioEntity) {
        dao.delete(formulario)
    }

    // Obtener un formulario por ID
    suspend fun getFormularioById(id: Int): FormularioServicioEntity? {
        return dao.getFormularioById(id)
    }

    // Obtener formularios por estado
    fun getFormulariosByEstado(estado: String): Flow<List<FormularioServicioEntity>> {
        return dao.getFormulariosByEstado(estado)
    }
}

