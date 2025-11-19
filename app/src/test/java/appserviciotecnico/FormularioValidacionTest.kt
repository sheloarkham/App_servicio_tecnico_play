package appserviciotecnico

import appserviciotecnico.model.domain.validators.ValidadorFormulario
import appserviciotecnico.viewmodel.states.FormularioServicioState
import org.junit.Assert.*
import org.junit.Test

/**
 * 🧪 Tests unitarios para validaciones del formulario de servicio
 * Usa ValidadorFormulario de la capa de dominio
 */
class FormularioValidacionTest {

    @Test
    fun test_correo_vacio_es_invalido() {
        // Given
        val correoVacio = ""

        // When
        val error = ValidadorFormulario.validarCorreo(correoVacio)

        // Then
        assertNotNull("El correo vacío debe generar error", error)
        assertEquals("El correo es requerido", error)
    }

    @Test
    fun test_correo_sin_arroba_es_invalido() {
        // Given
        val correoInvalido = "correosinvalido.com"

        // When
        val error = ValidadorFormulario.validarCorreo(correoInvalido)

        // Then
        assertNotNull("El correo sin @ debe generar error", error)
        assertEquals("Correo inválido", error)
    }

    @Test
    fun test_correo_con_arroba_es_valido() {
        // Given
        val correoValido = "usuario@example.com"

        // When
        val error = ValidadorFormulario.validarCorreo(correoValido)

        // Then
        assertNull("El correo válido no debe generar error", error)
    }

    @Test
    fun test_nombre_vacio_es_invalido() {
        // Given
        val nombreVacio = ""

        // When
        val error = ValidadorFormulario.validarNombre(nombreVacio)

        // Then
        assertNotNull("El nombre vacío debe generar error", error)
    }

    @Test
    fun test_nombre_con_texto_es_valido() {
        // Given
        val nombreValido = "Juan Pérez"

        // When
        val error = ValidadorFormulario.validarNombre(nombreValido)

        // Then
        assertNull("El nombre válido no debe generar error", error)
    }

    @Test
    fun test_telefono_vacio_es_invalido() {
        // Given
        val telefonoVacio = ""

        // When
        val error = ValidadorFormulario.validarTelefono(telefonoVacio)

        // Then
        assertNotNull("El teléfono vacío debe generar error", error)
    }

    @Test
    fun test_descripcion_problema_vacio_es_invalido() {
        // Given
        val descripcionVacia = ""

        // When
        val error = ValidadorFormulario.validarDescripcion(descripcionVacia)

        // Then
        assertNotNull("La descripción vacía debe generar error", error)
    }

    @Test
    fun test_tipo_consola_vacio_es_invalido() {
        // Given
        val tipoConsolaVacio = ""

        // When
        val error = ValidadorFormulario.validarTipoConsola(tipoConsolaVacio)

        // Then
        assertNotNull("El tipo de consola vacío debe generar error", error)
    }

    @Test
    fun test_validacion_completa_con_errores() {
        // Given
        val errores = ValidadorFormulario.validarFormularioCompleto(
            nombreCliente = "",
            correoCliente = "",
            telefonoCliente = "",
            tipoConsola = "",
            modeloConsola = "",
            descripcionProblema = ""
        )

        // Then
        assertTrue("Debe tener errores", errores.hasErrors())
    }

    @Test
    fun test_estado_inicial_esta_vacio() {
        // Given
        val estadoInicial = FormularioServicioState()

        // Then
        assertTrue("Nombre inicial debe estar vacío", estadoInicial.nombreCliente.isEmpty())
        assertTrue("Correo inicial debe estar vacío", estadoInicial.correoCliente.isEmpty())
        assertTrue("Teléfono inicial debe estar vacío", estadoInicial.telefonoCliente.isEmpty())
        assertTrue("Tipo consola inicial debe estar vacío", estadoInicial.tipoConsola.isEmpty())
        assertTrue("Modelo consola inicial debe estar vacío", estadoInicial.modeloConsola.isEmpty())
        assertTrue("Descripción inicial debe estar vacía", estadoInicial.descripcionProblema.isEmpty())
        assertFalse("No debe estar enviando inicialmente", estadoInicial.enviando)
        assertNull("Mensaje de éxito inicial debe ser null", estadoInicial.mensajeExito)
    }
}

