package appserviciotecnico.model.data

import androidx.room.*
import appserviciotecnico.model.entities.FormularioServicioEntity
import kotlinx.coroutines.flow.Flow

// 📝 DAO para gestionar formularios de servicio técnico
@Dao
interface FormularioServicioDao {

    // Insertar un nuevo formulario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formulario: FormularioServicioEntity): Long

    // Actualizar un formulario existente
    @Update
    suspend fun update(formulario: FormularioServicioEntity)

    // Eliminar un formulario
    @Delete
    suspend fun delete(formulario: FormularioServicioEntity)

    // Obtener todos los formularios
    @Query("SELECT * FROM formularios_servicio ORDER BY fecha DESC")
    fun getAllFormularios(): Flow<List<FormularioServicioEntity>>

    // Obtener un formulario por ID
    @Query("SELECT * FROM formularios_servicio WHERE id = :id")
    suspend fun getFormularioById(id: Int): FormularioServicioEntity?

    // Obtener formularios por estado
    @Query("SELECT * FROM formularios_servicio WHERE estado = :estado ORDER BY fecha DESC")
    fun getFormulariosByEstado(estado: String): Flow<List<FormularioServicioEntity>>
}

