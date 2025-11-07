package appserviciotecnico.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import appserviciotecnico.model.data.AppDatabase
import appserviciotecnico.model.repository.FormularioServicioRepository

// 🏭 Factory para crear FormularioServicioViewModel con Repository
class FormularioServicioViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormularioServicioViewModel::class.java)) {
            val database = AppDatabase.getDatabase(application)
            val repository = FormularioServicioRepository(database.formularioServicioDao())
            return FormularioServicioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

