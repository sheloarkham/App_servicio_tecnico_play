package appserviciotecnico.model.domain.models

import java.util.*

// Modelo de datos para representar una solicitud de servicio
data class Solicitud(
    val id: Int,
    val servicio: String,
    val fechaAgendada: Date,
    val estado: EstadoSolicitud,
    val clienteNombre: String,
    val descripcion: String,
    val horaAgendada: String
)

// Estados posibles de una solicitud
@Suppress("unused")
enum class EstadoSolicitud(
    val texto: String,
    val icono: String,
    val color: Long // Color en formato 0xFFRRGGBB
) {
    PENDIENTE("Pendiente", "P", 0xFFFFA726),
    EN_PROCESO("En Proceso", "E", 0xFF42A5F5),
    COMPLETADO("Completado", "C", 0xFF66BB6A),
    CANCELADO("Cancelado", "X", 0xFFEF5350)
}

// Companion object con datos de ejemplo
@Suppress("unused")
object SolicitudesData {

    @Suppress("unused")
    fun obtenerSolicitudesEjemplo(): List<Solicitud> {
        val hoy = Calendar.getInstance().time
        val manana = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.time
        val ayer = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }.time
        val hace3Dias = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -3) }.time
        val enUnaSemana = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 7) }.time

        return listOf(
            Solicitud(
                id = 1,
                servicio = "Limpieza Profunda PS4",
                fechaAgendada = manana,
                estado = EstadoSolicitud.PENDIENTE,
                clienteNombre = "Juan Pérez",
                descripcion = "Limpieza interna completa con cambio de pasta térmica",
                horaAgendada = "14:00"
            ),
            Solicitud(
                id = 2,
                servicio = "Reparación Puerto HDMI PS5",
                fechaAgendada = hoy,
                estado = EstadoSolicitud.EN_PROCESO,
                clienteNombre = "María González",
                descripcion = "Reemplazo de puerto HDMI dañado",
                horaAgendada = "11:30"
            ),
            Solicitud(
                id = 3,
                servicio = "Cambio de Disco Duro PS4",
                fechaAgendada = ayer,
                estado = EstadoSolicitud.COMPLETADO,
                clienteNombre = "Pedro Ramírez",
                descripcion = "Instalación de SSD 1TB",
                horaAgendada = "15:00"
            ),
            Solicitud(
                id = 4,
                servicio = "Diagnóstico Completo PS5",
                fechaAgendada = hace3Dias,
                estado = EstadoSolicitud.COMPLETADO,
                clienteNombre = "Ana Silva",
                descripcion = "Revisión completa del sistema",
                horaAgendada = "10:00"
            ),
            Solicitud(
                id = 5,
                servicio = "Reparación Ventilador PS4 Pro",
                fechaAgendada = enUnaSemana,
                estado = EstadoSolicitud.PENDIENTE,
                clienteNombre = "Carlos Torres",
                descripcion = "Reemplazo de ventilador con ruido",
                horaAgendada = "16:00"
            ),
            Solicitud(
                id = 6,
                servicio = "Mantenimiento Preventivo PS5",
                fechaAgendada = hace3Dias,
                estado = EstadoSolicitud.CANCELADO,
                clienteNombre = "Laura Martínez",
                descripcion = "Cliente canceló la cita",
                horaAgendada = "12:00"
            )
        )
    }
}

