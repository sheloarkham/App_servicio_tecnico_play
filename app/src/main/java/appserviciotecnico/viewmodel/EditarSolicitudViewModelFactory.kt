package appserviciotecnico.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import appserviciotecnico.model.domain.models.Solicitud
import appserviciotecnico.model.data.config.AppDatabase
import appserviciotecnico.model.data.repository.SolicitudRepository

// 🏭 Factory para crear EditarSolicitudViewModel
class EditarSolicitudViewModelFactory(
    private val application: Application,
    private val solicitud: Solicitud
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditarSolicitudViewModel::class.java)) {
            val database = AppDatabase.getDatabase(application)
            val repository = SolicitudRepository(database.solicitudDao())
            return EditarSolicitudViewModel(repository, solicitud) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

