package appserviciotecnico

import appserviciotecnico.model.domain.ValidadorHorario
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

/**
 * 🕐 Tests unitarios para validación de horario laboral
 * Usa ValidadorHorario de la capa de dominio
 * Horario: Lunes a Sábado, 10:00 - 18:00
 */
class HorarioLaboralTest {

    @Test
    fun test_hora_antes_de_10am_es_invalida() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)

        // Then
        assertFalse("Hora antes de 10 AM debe ser inválida", esValido)
    }

    @Test
    fun test_hora_despues_de_6pm_es_invalida() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 18)
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)

        // Then
        assertFalse("Hora después de 6 PM debe ser inválida", esValido)
    }

    @Test
    fun test_domingo_es_invalido() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 14)
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)
        val esDiaLaboral = ValidadorHorario.esDiaLaboral(Calendar.SUNDAY)

        // Then
        assertFalse("Domingo debe ser inválido", esValido)
        assertFalse("Domingo no debe ser día laboral", esDiaLaboral)
    }

    @Test
    fun test_lunes_2pm_es_valido() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 14)
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)

        // Then
        assertTrue("Lunes a las 2 PM debe ser válido", esValido)
    }

    @Test
    fun test_sabado_10am_es_valido() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)

        // Then
        assertTrue("Sábado a las 10 AM debe ser válido", esValido)
    }

    @Test
    fun test_martes_5pm_es_valido() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 17) // 5 PM
            set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)

        // Then
        assertTrue("Martes a las 5 PM debe ser válido", esValido)
    }

    @Test
    fun test_viernes_mediodia_es_valido() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)

        // Then
        assertTrue("Viernes al mediodía debe ser válido", esValido)
    }

    @Test
    fun test_hora_9am_exacta_es_invalida() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)
        val mensajeError = ValidadorHorario.obtenerMensajeError(calendar)

        // Then
        assertFalse("9 AM exacta debe ser inválida", esValido)
        assertNotNull("Debe haber mensaje de error", mensajeError)
    }

    @Test
    fun test_hora_10am_exacta_es_valida() {
        // Given
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 0)
            set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        }

        // When
        val esValido = ValidadorHorario.validarHorarioLaboral(calendar)

        // Then
        assertTrue("10 AM exacta debe ser válida", esValido)
    }

    @Test
    fun test_sabado_es_dia_laboral() {
        // Given & When
        val esDiaLaboral = ValidadorHorario.esDiaLaboral(Calendar.SATURDAY)

        // Then
        assertTrue("Sábado debe ser día laboral", esDiaLaboral)
    }

    @Test
    fun test_obtener_horario_atencion() {
        // When
        val horario = ValidadorHorario.obtenerHorarioAtencion()

        // Then
        assertNotNull("Debe retornar horario de atención", horario)
        assertTrue("Debe contener información de horario", horario.contains("10:00"))
    }
}

