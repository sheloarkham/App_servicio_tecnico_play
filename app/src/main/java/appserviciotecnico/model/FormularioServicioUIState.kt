package appserviciotecnico.model

// 🎯 Estados de UI para el formulario de servicio
sealed class FormularioServicioUIState {
    // Estado inicial/inactivo
    object Idle : FormularioServicioUIState()

    // Cargando datos
    object Loading : FormularioServicioUIState()

    // Éxito al guardar/actualizar
    data class Success(val message: String) : FormularioServicioUIState()

    // Error al procesar
    data class Error(val message: String) : FormularioServicioUIState()

    // Validación de campos
    data class ValidationError(val errors: Map<String, String>) : FormularioServicioUIState()
}

