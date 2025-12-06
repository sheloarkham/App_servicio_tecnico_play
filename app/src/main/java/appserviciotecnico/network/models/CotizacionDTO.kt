package appserviciotecnico.network.models

import com.google.gson.annotations.SerializedName

/**
 * DTO: Cotización de servicio técnico del backend
 */
data class CotizacionDTO(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("nombreCliente")
    val nombreCliente: String,

    @SerializedName("correoCliente")
    val correoCliente: String,

    @SerializedName("telefonoCliente")
    val telefonoCliente: String,

    @SerializedName("tipoConsola")
    val tipoConsola: String,

    @SerializedName("modeloConsola")
    val modeloConsola: String,

    @SerializedName("descripcionProblema")
    val descripcionProblema: String,

    @SerializedName("estadoSolicitud")
    val estadoSolicitud: String = "PENDIENTE",

    @SerializedName("fechaSolicitud")
    val fechaSolicitud: String,

    @SerializedName("motivoEdicion")
    val motivoEdicion: String? = null,

    @SerializedName("fechaReagendada")
    val fechaReagendada: String? = null,

    @SerializedName("fechaCreacion")
    val fechaCreacion: String? = null
)

