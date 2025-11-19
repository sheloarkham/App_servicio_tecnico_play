package appserviciotecnico.viewmodel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import appserviciotecnico.model.data.entities.SolicitudEntity
import appserviciotecnico.model.data.repository.SolicitudRepository
import appserviciotecnico.model.domain.models.Solicitud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

// ✏️ ViewModel para editar fecha y hora de una solicitud
class EditarSolicitudViewModel(
    private val repository: SolicitudRepository,
    private val solicitudOriginal: Solicitud
) : ViewModel() {

    private val _fechaSeleccionada = MutableStateFlow(solicitudOriginal.fechaAgendada)
    val fechaSeleccionada: StateFlow<Date> = _fechaSeleccionada.asStateFlow()

    private val _horaSeleccionada = MutableStateFlow(solicitudOriginal.horaAgendada)
    val horaSeleccionada: StateFlow<String> = _horaSeleccionada.asStateFlow()

    private val _errorFecha = MutableStateFlow<String?>(null)
    val errorFecha: StateFlow<String?> = _errorFecha.asStateFlow()

    private val _errorHora = MutableStateFlow<String?>(null)
    val errorHora: StateFlow<String?> = _errorHora.asStateFlow()

    private val _guardando = MutableStateFlow(false)
    val guardando: StateFlow<Boolean> = _guardando.asStateFlow()

    fun setFecha(fecha: Date) {
        _fechaSeleccionada.value = fecha
        _errorFecha.value = null
    }

    fun setHora(hora: String) {
        _horaSeleccionada.value = hora
        _errorHora.value = null
    }

    fun setErrorFecha(error: String) {
        _errorFecha.value = error
    }

    fun setErrorHora(error: String) {
        _errorHora.value = error
    }

    fun guardarCambios(onExito: () -> Unit) {
        if (_errorFecha.value != null || _errorHora.value != null) {
            return
        }

        _guardando.value = true

        viewModelScope.launch {
            try {
                // Actualizar la solicitud con la nueva fecha y hora
                val solicitudActualizada = SolicitudEntity(
                    id = solicitudOriginal.id.toLong(),
                    servicio = solicitudOriginal.servicio,
                    fechaAgendada = _fechaSeleccionada.value.time,
                    horaAgendada = _horaSeleccionada.value,
                    estado = solicitudOriginal.estado.texto,
                    clienteNombre = solicitudOriginal.clienteNombre,
                    descripcion = solicitudOriginal.descripcion,
                    categoriaId = 0
                )

                repository.actualizarSolicitud(solicitudActualizada)

                _guardando.value = false
                onExito()
            } catch (e: Exception) {
                _guardando.value = false
                _errorFecha.value = "Error al guardar: ${e.message}"
            }
        }
    }
}