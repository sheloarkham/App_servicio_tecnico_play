package appserviciotecnico.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entidad para almacenar solicitudes/citas de servicio técnico
@Entity(tableName = "solicitud_cita")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val servicio: String,
    val fechaAgendada: Long,  // Timestamp
    val horaAgendada: String,
    val estado: String,  // "Pendiente", "En Proceso", "Completado", "Cancelado"
    val clienteNombre: String,
    val descripcion: String,
    val categoriaId: Int
)

