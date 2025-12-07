package appserviciotecnico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import appserviciotecnico.network.config.RetrofitClient
import appserviciotecnico.network.models.external.Game
import appserviciotecnico.network.repository.ExternalRepository
import appserviciotecnico.network.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar datos de la API externa (RAWG.io)
 * Estados: Loading, Success, Error
 */
class ExternalViewModel : ViewModel() {

    private val repository = ExternalRepository(RetrofitClient.externalApi)

    // Estado de UI
    private val _uiState = MutableStateFlow<ExternalUiState>(ExternalUiState.Loading)
    val uiState: StateFlow<ExternalUiState> = _uiState.asStateFlow()

    // Lista de juegos
    private val _juegos = MutableStateFlow<List<Game>>(emptyList())
    val juegos: StateFlow<List<Game>> = _juegos.asStateFlow()

    init {
        cargarJuegosPopulares()
    }

    /**
     * Cargar juegos populares
     */
    fun cargarJuegosPopulares() {
        viewModelScope.launch {
            _uiState.value = ExternalUiState.Loading

            when (val result = repository.obtenerJuegosPopulares()) {
                is NetworkResult.Success -> {
                    _juegos.value = result.data
                    _uiState.value = ExternalUiState.Success
                }
                is NetworkResult.Error -> {
                    _uiState.value = ExternalUiState.Error(result.message)
                }
                is NetworkResult.Loading -> {
                    _uiState.value = ExternalUiState.Loading
                }
                is NetworkResult.Idle -> {
                    // No hacer nada
                }
            }
        }
    }

    /**
     * Buscar juegos por nombre
     */
    fun buscarJuegos(query: String) {
        if (query.isBlank()) {
            cargarJuegosPopulares()
            return
        }

        viewModelScope.launch {
            _uiState.value = ExternalUiState.Loading

            when (val result = repository.buscarJuegos(query)) {
                is NetworkResult.Success -> {
                    _juegos.value = result.data
                    _uiState.value = ExternalUiState.Success
                }
                is NetworkResult.Error -> {
                    _uiState.value = ExternalUiState.Error(result.message)
                }
                is NetworkResult.Loading -> {
                    _uiState.value = ExternalUiState.Loading
                }
                is NetworkResult.Idle -> {
                    // No hacer nada
                }
            }
        }
    }

    /**
     * Recargar datos
     */
    fun recargar() {
        cargarJuegosPopulares()
    }
}

/**
 * Estados de la UI para la API externa
 */
sealed class ExternalUiState {
    data object Loading : ExternalUiState()
    data object Success : ExternalUiState()
    data class Error(val message: String) : ExternalUiState()
}

