package appserviciotecnico.viewmodel

import appserviciotecnico.model.domain.FormularioServicioErrores

// 🎯 Estado del formulario de servicio
data class FormularioServicioState(
    // Datos del cliente
    val nombreCliente: String = "",
    val correoCliente: String = "",
    val telefonoCliente: String = "",

    // Datos del servicio
    val tipoConsola: String = "",
    val modeloConsola: String = "",
    val descripcionProblema: String = "",

    // Errores
    val errores: FormularioServicioErrores = FormularioServicioErrores(),

    // Estado de envío
    val enviando: Boolean = false,
    val mensajeExito: String? = null
)

