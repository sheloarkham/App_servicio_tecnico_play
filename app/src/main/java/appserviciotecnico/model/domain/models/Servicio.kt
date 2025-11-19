package appserviciotecnico.model.domain.models

// 🛠️ Modelo de datos para representar un servicio técnico
@Suppress("unused")
data class Servicio(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precioDesde: Double,
    val duracionEstimada: String,
    val categoria: String
)

// Categoría de servicios
data class CategoriaServicio(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val icono: String,
    val serviciosIncluidos: List<String>
) {
    @Suppress("unused")
    companion object {
        // Categorías principales de servicios
        fun obtenerCategorias(): List<CategoriaServicio> = listOf(
            CategoriaServicio(
                id = 1,
                nombre = "Diagnóstico y Limpieza",
                descripcion = "Revisión completa, limpieza interna y prevención de sobrecalentamiento",
                icono = "D",
                serviciosIncluidos = listOf(
                    "Diagnóstico general de la consola",
                    "Limpieza interna completa",
                    "Eliminación de polvo y suciedad",
                    "Limpieza de ventilación",
                    "Cambio de pasta térmica",
                    "Prevención de sobrecalentamiento"
                )
            ),
            CategoriaServicio(
                id = 2,
                nombre = "Reparación de Hardware",
                descripcion = "Solución de problemas físicos y componentes dañados",
                icono = "H",
                serviciosIncluidos = listOf(
                    "Reparación de encendido",
                    "Reparación de fuente de alimentación",
                    "Reparación puerto HDMI",
                    "Solución luz azul de la muerte",
                    "Reballing GPU/CPU",
                    "Reparación de componentes internos"
                )
            ),
            CategoriaServicio(
                id = 3,
                nombre = "Lector y Almacenamiento",
                descripcion = "Reparación de lectores de discos y actualización de almacenamiento",
                icono = "A",
                serviciosIncluidos = listOf(
                    "Reparación lector Blu-ray",
                    "Cambio de disco duro HDD",
                    "Instalación SSD",
                    "Expansión de almacenamiento PS5",
                    "Rescate de datos",
                    "Backup de partidas guardadas"
                )
            ),
            CategoriaServicio(
                id = 4,
                nombre = "Sistema de Refrigeración",
                descripcion = "Solución de sobrecalentamiento y mejora de ventilación",
                icono = "R",
                serviciosIncluidos = listOf(
                    "Reparación de ventiladores",
                    "Reemplazo de disipadores",
                    "Tratamiento de sobrecalentamiento",
                    "Mejora de sistema de refrigeración",
                    "Instalación de placas de refrigeración",
                    "Optimización de ventilación"
                )
            ),
            CategoriaServicio(
                id = 5,
                nombre = "Software y Firmware",
                descripcion = "Actualización, configuración y solución de problemas de software",
                icono = "S",
                serviciosIncluidos = listOf(
                    "Actualización de firmware",
                    "Instalación limpia del sistema",
                    "Solución de problemas de software",
                    "Configuración de red WiFi/Ethernet",
                    "Configuración de PlayStation Network",
                    "Optimización del sistema"
                )
            ),
            CategoriaServicio(
                id = 6,
                nombre = "Controles y Accesorios",
                descripcion = "Reparación de mandos DualShock y DualSense",
                icono = "C",
                serviciosIncluidos = listOf(
                    "Reparación de drift en joysticks",
                    "Cambio de botones y gatillos",
                    "Reparación de batería interna",
                    "Solución de conectividad inalámbrica",
                    "Reparación de puertos de carga",
                    "Reemplazo de componentes"
                )
            ),
            CategoriaServicio(
                id = 7,
                nombre = "Conectividad y Puertos",
                descripcion = "Reparación de puertos USB, HDMI y otros conectores",
                icono = "P",
                serviciosIncluidos = listOf(
                    "Reparación puerto HDMI",
                    "Reparación puertos USB",
                    "Reparación puerto de red",
                    "Reparación salida de audio",
                    "Reemplazo de conectores externos",
                    "Solución de problemas de señal"
                )
            ),
            CategoriaServicio(
                id = 8,
                nombre = "Mantenimiento Preventivo",
                descripcion = "Revisión periódica y mantenimiento programado",
                icono = "M",
                serviciosIncluidos = listOf(
                    "Mantenimiento semestral/anual",
                    "Revisión de componentes",
                    "Testeo post-reparación",
                    "Informe técnico completo",
                    "Recomendaciones de uso",
                    "Garantía extendida"
                )
            ),
            CategoriaServicio(
                id = 9,
                nombre = "Personalización",
                descripcion = "Modificaciones estéticas y mejoras de rendimiento",
                icono = "E",
                serviciosIncluidos = listOf(
                    "Cambio de carcasa",
                    "Instalación de LED personalizados",
                    "Modificación estética",
                    "Mejoras de rendimiento",
                    "Venta de accesorios",
                    "Customización completa"
                )
            ),
            CategoriaServicio(
                id = 10,
                nombre = "Servicios Premium",
                descripcion = "Servicios express, domicilio y atención prioritaria",
                icono = "P+",
                serviciosIncluidos = listOf(
                    "Servicio urgente/express",
                    "Recogida a domicilio",
                    "Entrega a domicilio",
                    "Soporte prioritario",
                    "Garantía extendida",
                    "Programa de fidelidad"
                )
            )
        )
    }
}


