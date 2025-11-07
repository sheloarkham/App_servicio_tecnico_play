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
    val descripcionProblema: String
)

