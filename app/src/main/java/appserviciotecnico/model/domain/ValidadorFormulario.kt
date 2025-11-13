package appserviciotecnico.model.domain


/**
 * 🔍 Validador de formularios de servicio técnico
 * Centraliza toda la lógica de validación
 */
object ValidadorFormulario {

    /**
     * Valida el campo de correo electrónico
     */
    fun validarCorreo(correo: String): String? {
        return when {
            correo.isBlank() -> "El correo es requerido"
            !correo.contains("@") -> "Correo inválido"
            !correo.contains(".") -> "Correo inválido"
            else -> null
        }
    }

    /**
     * Valida el campo de nombre
     */
    fun validarNombre(nombre: String): String? {
        return when {
            nombre.isBlank() -> "El nombre es requerido"
            nombre.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            else -> null
        }
    }

    /**
     * Valida el campo de teléfono
     */
    fun validarTelefono(telefono: String): String? {
        return when {
            telefono.isBlank() -> "El teléfono es requerido"
            telefono.length < 9 -> "Teléfono inválido"
            else -> null
        }
    }

    /**
     * Valida el tipo de consola
     */
    fun validarTipoConsola(tipoConsola: String): String? {
        return if (tipoConsola.isBlank()) {
            "Seleccione el tipo de consola"
        } else null
    }

    /**
     * Valida el modelo de consola
     */
    fun validarModeloConsola(modelo: String): String? {
        return if (modelo.isBlank()) {
            "El modelo es requerido"
        } else null
    }

    /**
     * Valida la descripción del problema
     */
    fun validarDescripcion(descripcion: String): String? {
        return when {
            descripcion.isBlank() -> "Describa el problema"
            descripcion.length < 10 -> "La descripción debe tener al menos 10 caracteres"
            else -> null
        }
    }

    /**
     * Valida todos los campos del formulario y retorna los errores
     */
    fun validarFormularioCompleto(
        nombreCliente: String,
        correoCliente: String,
        telefonoCliente: String,
        tipoConsola: String,
        modeloConsola: String,
        descripcionProblema: String
    ): FormularioServicioErrores {
        return FormularioServicioErrores(
            nombreCliente = validarNombre(nombreCliente),
            emailCliente = validarCorreo(correoCliente),
            telefonoCliente = validarTelefono(telefonoCliente),
            tipoConsola = validarTipoConsola(tipoConsola),
            modeloConsola = validarModeloConsola(modeloConsola),
            descripcionProblema = validarDescripcion(descripcionProblema)
        )
    }
}

