package appserviciotecnico.model.data

import androidx.room.*
import appserviciotecnico.model.entities.SolicitudEntity
import kotlinx.coroutines.flow.Flow

// 📝 DAO para gestionar solicitudes/citas de servicio
@Dao
interface SolicitudDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSolicitud(solicitud: SolicitudEntity): Long

    @Query("SELECT * FROM solicitud_cita ORDER BY fechaAgendada DESC")
    fun getSolicitudes(): Flow<List<SolicitudEntity>>

    @Query("SELECT * FROM solicitud_cita WHERE estado = :estado ORDER BY fechaAgendada DESC")
    fun getSolicitudesByEstado(estado: String): Flow<List<SolicitudEntity>>

    @Query("SELECT * FROM solicitud_cita WHERE id = :id")
    suspend fun getSolicitudById(id: Long): SolicitudEntity?

    @Update
    suspend fun updateSolicitud(solicitud: SolicitudEntity)

    @Delete
    suspend fun deleteSolicitud(solicitud: SolicitudEntity)

    @Query("DELETE FROM solicitud_cita")
    suspend fun deleteAll()
}

