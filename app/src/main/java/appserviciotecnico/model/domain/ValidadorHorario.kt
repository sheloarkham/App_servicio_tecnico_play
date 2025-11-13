package appserviciotecnico.model.domain

import java.util.Calendar

/**
 * 🕐 Validador de horarios laborales
 * Encapsula la lógica de validación de horarios de atención
 */
object ValidadorHorario {

    // Constantes de configuración
    private const val HORA_APERTURA = 10
    private const val HORA_CIERRE = 18

    /**
     * Valida si una fecha/hora está dentro del horario laboral
     * Horario: Lunes a Sábado, 10:00 - 18:00
     */
    fun validarHorarioLaboral(calendar: Calendar): Boolean {
        val diaSemana = calendar.get(Calendar.DAY_OF_WEEK)
        val hora = calendar.get(Calendar.HOUR_OF_DAY)

        // Domingo no es día laboral
        if (diaSemana == Calendar.SUNDAY) {
            return false
        }

        // Hora debe estar entre 10:00 y 17:59 (antes de las 18:00)
        return hora in HORA_APERTURA until HORA_CIERRE
    }

    /**
     * Valida si un día de la semana es laboral
     */
    fun esDiaLaboral(diaSemana: Int): Boolean {
        return diaSemana != Calendar.SUNDAY
    }

    /**
     * Valida si una hora está dentro del horario de atención
     */
    fun esHoraValida(hora: Int): Boolean {
        return hora in HORA_APERTURA until HORA_CIERRE
    }

    /**
     * Obtiene mensaje de error según el problema
     */
    fun obtenerMensajeError(calendar: Calendar): String? {
        val diaSemana = calendar.get(Calendar.DAY_OF_WEEK)
        val hora = calendar.get(Calendar.HOUR_OF_DAY)

        return when {
            diaSemana == Calendar.SUNDAY ->
                "Los domingos no atendemos. Por favor selecciona otro día."
            hora < HORA_APERTURA ->
                "Nuestro horario de atención comienza a las 10:00 AM"
            hora >= HORA_CIERRE ->
                "Nuestro horario de atención termina a las 6:00 PM"
            else -> null
        }
    }

    /**
     * Obtiene el horario de atención como texto
     */
    fun obtenerHorarioAtencion(): String {
        return "Lunes a Sábado: $HORA_APERTURA:00 - $HORA_CIERRE:00"
    }
}

