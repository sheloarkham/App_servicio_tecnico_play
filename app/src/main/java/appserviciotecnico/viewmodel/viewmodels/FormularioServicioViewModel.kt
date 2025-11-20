package appserviciotecnico.viewmodel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import appserviciotecnico.model.domain.usecases.GuardarCotizacionUseCase
import appserviciotecnico.model.domain.validators.FormularioServicioErrores
import appserviciotecnico.viewmodel.states.FormularioServicioState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel para gestionar el formulario de servicio técnico PlayStation
class FormularioServicioViewModel(
    private val guardarCotizacionUseCase: GuardarCotizacionUseCase
) : ViewModel() {

    private val _estado = MutableStateFlow(FormularioServicioState())
    val estado: StateFlow<FormularioServicioState> = _estado.asStateFlow()

    // ✏️ Funciones para actualizar cada campo
    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombreCliente = valor,
                errores = it.errores.copy(nombreCliente = null)
            )
        }
    }

    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correoCliente = valor,
                errores = it.errores.copy(emailCliente = null)
            )
        }
    }

    fun onTelefonoChange(valor: String) {
        _estado.update {
            it.copy(
                telefonoCliente = valor,
                errores = it.errores.copy(telefonoCliente = null)
            )
        }
    }

    fun onTipoConsolaChange(valor: String) {
        _estado.update {
            it.copy(
                tipoConsola = valor,
                errores = it.errores.copy(tipoConsola = null)
            )
        }
    }

    fun onModeloConsolaChange(valor: String) {
        _estado.update {
            it.copy(
                modeloConsola = valor,
                errores = it.errores.copy(modeloConsola = null)
            )
        }
    }

    fun onDescripcionChange(valor: String) {
        _estado.update {
            it.copy(
                descripcionProblema = valor,
                errores = it.errores.copy(descripcionProblema = null)
            )
        }
    }

    //  Validar y enviar formulario
    fun onEnviarFormulario() {
        val estadoActual = _estado.value
        val errores = validarFormulario(estadoActual)

        if (errores.hasErrors()) {
            _estado.update { it.copy(errores = errores) }
            return
        }

        // Guardar usando UseCase
        _estado.update {
            it.copy(
                enviando = true,
                errores = FormularioServicioErrores()
            )
        }

        viewModelScope.launch {
            val resultado = guardarCotizacionUseCase.execute(
                nombreCliente = estadoActual.nombreCliente,
                correoCliente = estadoActual.correoCliente,
                telefonoCliente = estadoActual.telefonoCliente,
                tipoConsola = estadoActual.tipoConsola,
                modeloConsola = estadoActual.modeloConsola,
                descripcionProblema = estadoActual.descripcionProblema
            )

            resultado.fold(
                onSuccess = {
                    _estado.update {
                        it.copy(
                            enviando = false,
                            mensajeExito = "✅ Cotización solicitada exitosamente. Te contactaremos pronto con el presupuesto."
                        )
                    }

                    // Limpiar formulario después de 2 segundos
                    delay(2000)
                    limpiarFormulario()
                },
                onFailure = { e ->
                    _estado.update {
                        it.copy(
                            enviando = false,
                            mensajeExito = "❌ Error al guardar: ${e.message}"
                        )
                    }
                }
            )
        }
    }

    // 🔍 Validar todos los campos
    private fun validarFormulario(estado: FormularioServicioState): FormularioServicioErrores {
        return FormularioServicioErrores(
            nombreCliente = if (estado.nombreCliente.isBlank())
                "El nombre es requerido" else null,

            emailCliente = when {
                estado.correoCliente.isBlank() -> "El correo es requerido"
                !estado.correoCliente.contains("@") -> "Correo inválido"
                else -> null
            },

            telefonoCliente = if (estado.telefonoCliente.isBlank())
                "El teléfono es requerido" else null,

            tipoConsola = if (estado.tipoConsola.isBlank())
                "Seleccione el tipo de consola" else null,

            modeloConsola = if (estado.modeloConsola.isBlank())
                "El modelo es requerido" else null,

            descripcionProblema = if (estado.descripcionProblema.isBlank())
                "Describa el problema" else null
        )
    }

    // 🧹 Limpiar formulario
    private fun limpiarFormulario() {
        _estado.update { FormularioServicioState() }
    }
}