package appserviciotecnico.viewmodel.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import appserviciotecnico.model.data.config.AppDatabase
import appserviciotecnico.model.data.repository.FormularioServicioRepository
import appserviciotecnico.model.domain.usecases.GuardarCotizacionUseCase
import appserviciotecnico.viewmodel.viewmodels.FormularioServicioViewModel

// Factory para crear FormularioServicioViewModel con UseCase
class FormularioServicioViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormularioServicioViewModel::class.java)) {
            val database = AppDatabase.getDatabase(application)
            val repository = FormularioServicioRepository(database.formularioServicioDao())
            val guardarCotizacionUseCase = GuardarCotizacionUseCase(repository)
            return FormularioServicioViewModel(guardarCotizacionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}