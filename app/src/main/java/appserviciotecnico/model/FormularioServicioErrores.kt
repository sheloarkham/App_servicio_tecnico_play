package appserviciotecnico.model

// ⚠️ Clase para manejar errores del formulario de servicio
data class FormularioServicioErrores(
    val nombreCliente: String? = null,
    val emailCliente: String? = null,
    val telefonoCliente: String? = null,
    val tipoConsola: String? = null,
    val modeloConsola: String? = null,
    val descripcionProblema: String? = null,
    val tipoServicio: String? = null
) {
    // Función para verificar si hay algún error
    fun hasErrors(): Boolean {
        return nombreCliente != null ||
                emailCliente != null ||
                telefonoCliente != null ||
                tipoConsola != null ||
                modeloConsola != null ||
                descripcionProblema != null ||
                tipoServicio != null
    }

    // Función para obtener el primer error
    fun getFirstError(): String? {
        return nombreCliente
            ?: emailCliente
            ?: telefonoCliente
            ?: tipoConsola
            ?: modeloConsola
            ?: descripcionProblema
            ?: tipoServicio
    }
}

