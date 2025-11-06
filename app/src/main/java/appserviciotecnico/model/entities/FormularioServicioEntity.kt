package appserviciotecnico.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// 📋 Entidad para almacenar formularios de servicio técnico de PlayStation
@Entity(tableName = "formularios_servicio")
data class FormularioServicioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Datos del cliente
    val nombreCliente: String,
    val emailCliente: String,
    val telefonoCliente: String,

    // Datos del servicio
    val tipoConsola: String,  // "PS4" o "PS5"
    val modeloConsola: String,
    val numeroSerie: String?,
    val descripcionProblema: String,
    val tipoServicio: String,  // "Reparación", "Mantenimiento", "Diagnóstico", etc.

    // Estado y fecha
    val estado: String = "Pendiente",  // "Pendiente", "En proceso", "Completado", "Cancelado"
    val fecha: Long = System.currentTimeMillis(),
    val fechaEstimada: Long? = null,

    // Información adicional
    val observaciones: String? = null,
    val precio: Double? = null,
    val usuarioId: String? = null  // ID del usuario que creó el formulario
)

