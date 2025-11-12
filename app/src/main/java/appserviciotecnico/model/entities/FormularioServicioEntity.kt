package appserviciotecnico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// 📋 Entidad para almacenar formularios de servicio técnico de PlayStation
@Entity(tableName = "formulario_servicio")
data class FormularioServicioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombreCliente: String,
    val correoCliente: String,
    val telefonoCliente: String,
    val tipoConsola: String,
    val modeloConsola: String,
    val descripcionProblema: String,
    // Agregar estado y fecha de solicitud para gestionar revisiones
    val estadoSolicitud: String,
    val fechaSolicitud: String,
    // Campos adicionales para HU8
    val motivoEdicion: String? = null,
    val fechaReagendada: String? = null
)
