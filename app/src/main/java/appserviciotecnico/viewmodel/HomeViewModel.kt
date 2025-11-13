package appserviciotecnico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import appserviciotecnico.model.data.AppDatabase
import appserviciotecnico.model.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//  ViewModel para la pantalla de inicio/dashboard
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SolicitudRepository
    private val _estado = MutableStateFlow(HomeState())
    val estado: StateFlow<HomeState> = _estado.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = SolicitudRepository(database.solicitudDao())
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _estado.update { it.copy(isLoading = true) }

            repository.obtenerSolicitudes().collect { solicitudes ->
                val pendientes = solicitudes.count { it.estado == "PENDIENTE" }
                val enProceso = solicitudes.count { it.estado == "EN_PROCESO" }
                val completadas = solicitudes.count { it.estado == "COMPLETADO" }

                // Obtener próxima cita (la más cercana con estado pendiente)
                val proximaCita = solicitudes
                    .filter { it.estado == "PENDIENTE" }
                    .minByOrNull { it.fechaAgendada }
                    ?.let {
                        ProximaCita(
                            servicio = it.servicio,
                            fecha = formatearFecha(it.fechaAgendada),
                            hora = it.horaAgendada
                        )
                    }

                _estado.update {
                    it.copy(
                        solicitudesPendientes = pendientes,
                        solicitudesEnProceso = enProceso,
                        solicitudesCompletadas = completadas,
                        proximaCita = proximaCita,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun formatearFecha(timestamp: Long): String {
        val formato = SimpleDateFormat("dd 'de' MMMM, yyyy", Locale.forLanguageTag("es-ES"))
        return formato.format(Date(timestamp))
    }

    fun actualizarDatos() {
        cargarDatos()
    }
}

