package appserviciotecnico.network.models

import com.google.gson.annotations.SerializedName

/**
 * DTO: Solicitud de servicio técnico del backend
 * Representa la respuesta JSON del microservicio Spring Boot
 */
data class SolicitudDTO(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("servicio")
    val servicio: String,

    @SerializedName("fechaAgendada")
    val fechaAgendada: String,

    @SerializedName("horaAgendada")
    val horaAgendada: String,

    @SerializedName("estado")
    val estado: String = "PENDIENTE",

    @SerializedName("clienteNombre")
    val clienteNombre: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("categoriaId")
    val categoriaId: Int,

    @SerializedName("fechaCreacion")
    val fechaCreacion: String? = null
)

