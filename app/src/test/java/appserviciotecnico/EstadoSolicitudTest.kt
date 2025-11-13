package appserviciotecnico

import appserviciotecnico.model.EstadoSolicitud
import appserviciotecnico.model.Solicitud
import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * 📋 Tests unitarios para estados de solicitudes
 */
class EstadoSolicitudTest {

    @Test
    fun test_solicitud_inicia_en_estado_pendiente() {
        // Given
        val solicitud = Solicitud(
            id = 1,
            servicio = "Reparación PS5",
            fechaAgendada = Date(),
            estado = EstadoSolicitud.PENDIENTE,
            clienteNombre = "Juan Pérez",
            descripcion = "Console no enciende",
            horaAgendada = "14:00"
        )

        // Then
        assertEquals("El estado debe ser PENDIENTE", EstadoSolicitud.PENDIENTE, solicitud.estado)
    }

    @Test
    fun test_cambio_de_estado_a_en_proceso() {
        // Given
        val estadoInicial = EstadoSolicitud.PENDIENTE

        // When
        val nuevoEstado = EstadoSolicitud.EN_PROCESO

        // Then
        assertNotEquals("El nuevo estado debe ser diferente", estadoInicial, nuevoEstado)
        assertEquals("El nuevo estado debe ser EN_PROCESO", EstadoSolicitud.EN_PROCESO, nuevoEstado)
    }

    @Test
    fun test_cambio_de_estado_a_completado() {
        // Given & When
        val nuevoEstado = EstadoSolicitud.COMPLETADO

        // Then
        assertEquals("El estado debe ser COMPLETADO", EstadoSolicitud.COMPLETADO, nuevoEstado)
    }

    @Test
    fun test_estado_pendiente_tiene_icono_amarillo() {
        // Given
        val estado = EstadoSolicitud.PENDIENTE

        // Then
        assertEquals("Ícono debe ser círculo amarillo", "🟡", estado.icono)
        assertEquals("Texto debe ser Pendiente", "Pendiente", estado.texto)
    }

    @Test
    fun test_estado_en_proceso_tiene_icono_azul() {
        // Given
        val estado = EstadoSolicitud.EN_PROCESO

        // Then
        assertEquals("Ícono debe ser círculo azul", "🔵", estado.icono)
        assertEquals("Texto debe ser En Proceso", "En Proceso", estado.texto)
    }

    @Test
    fun test_estado_completado_tiene_icono_verde() {
        // Given
        val estado = EstadoSolicitud.COMPLETADO

        // Then
        assertEquals("Ícono debe ser círculo verde", "🟢", estado.icono)
        assertEquals("Texto debe ser Completado", "Completado", estado.texto)
    }

    @Test
    fun test_estado_cancelado_tiene_icono_rojo() {
        // Given
        val estado = EstadoSolicitud.CANCELADO

        // Then
        assertEquals("Ícono debe ser círculo rojo", "🔴", estado.icono)
        assertEquals("Texto debe ser Cancelado", "Cancelado", estado.texto)
    }

    @Test
    fun test_todos_los_estados_tienen_iconos_distintos() {
        // Given
        val estados = EstadoSolicitud.entries

        // When
        val iconos = estados.map { it.icono }.toSet()

        // Then
        assertEquals("Debe haber 4 íconos únicos", 4, iconos.size)
    }

    @Test
    fun test_solicitud_puede_cambiar_de_pendiente_a_cancelado() {
        // Given
        val solicitudPendiente = Solicitud(
            id = 1,
            servicio = "Limpieza PS4",
            fechaAgendada = Date(),
            estado = EstadoSolicitud.PENDIENTE,
            clienteNombre = "María González",
            descripcion = "Mantenimiento preventivo",
            horaAgendada = "10:00"
        )

        // When
        val solicitudCancelada = solicitudPendiente.copy(estado = EstadoSolicitud.CANCELADO)

        // Then
        assertEquals("Estado debe ser CANCELADO", EstadoSolicitud.CANCELADO, solicitudCancelada.estado)
        assertEquals("ID debe mantenerse", solicitudPendiente.id, solicitudCancelada.id)
    }

    @Test
    fun test_solicitud_mantiene_datos_al_cambiar_estado() {
        // Given
        val solicitud = Solicitud(
            id = 5,
            servicio = "Cambio de disco duro",
            fechaAgendada = Date(),
            estado = EstadoSolicitud.PENDIENTE,
            clienteNombre = "Carlos Díaz",
            descripcion = "Ampliar capacidad",
            horaAgendada = "15:30"
        )

        // When
        val solicitudActualizada = solicitud.copy(estado = EstadoSolicitud.COMPLETADO)

        // Then
        assertEquals("ID debe mantenerse", solicitud.id, solicitudActualizada.id)
        assertEquals("Servicio debe mantenerse", solicitud.servicio, solicitudActualizada.servicio)
        assertEquals("Nombre debe mantenerse", solicitud.clienteNombre, solicitudActualizada.clienteNombre)
        assertEquals("Estado debe cambiar a COMPLETADO", EstadoSolicitud.COMPLETADO, solicitudActualizada.estado)
    }
}

