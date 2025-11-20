package appserviciotecnico.viewmodel.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import appserviciotecnico.model.data.config.AppDatabase
import appserviciotecnico.model.data.repository.SolicitudRepository
import appserviciotecnico.model.domain.usecases.ObtenerSolicitudesUseCase
import appserviciotecnico.viewmodel.viewmodels.EstadoSolicitudesViewModel

// Factory para crear EstadoSolicitudesViewModel con UseCase
class EstadoSolicitudesViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstadoSolicitudesViewModel::class.java)) {
            val database = AppDatabase.getDatabase(application)
            val repository = SolicitudRepository(database.solicitudDao())
            val obtenerSolicitudesUseCase = ObtenerSolicitudesUseCase(repository)
            return EstadoSolicitudesViewModel(obtenerSolicitudesUseCase, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}