package appserviciotecnico.model.data

import androidx.room.*
import appserviciotecnico.model.entities.FormularioServicioEntity
import kotlinx.coroutines.flow.Flow

// 📝 DAO para gestionar formularios de servicio técnico
@Dao
interface FormularioServicioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormulario(formulario: FormularioServicioEntity): Long

    @Query("SELECT * FROM formulario_servicio ORDER BY id DESC")
    fun getFormularios(): Flow<List<FormularioServicioEntity>>

    @Query("DELETE FROM formulario_servicio")
    suspend fun deleteAll()
}
