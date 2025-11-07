package appserviciotecnico.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import appserviciotecnico.model.CategoriaServicio
import appserviciotecnico.ui.components.CategoriaCard

// 📋 Pantalla de catálogo de servicios con categorías
@Composable
fun CatalogoServiciosScreen() {

    // Obtener las categorías de servicios
    val categorias = remember { CategoriaServicio.obtenerCategorias() }

    // Estado para mostrar detalles de una categoría
    var categoriaSeleccionada by remember { mutableStateOf<CategoriaServicio?>(null) }

    // Si hay una categoría seleccionada, mostrar sus detalles
    if (categoriaSeleccionada != null) {
        DetallesCategoriaDialog(
            categoria = categoriaSeleccionada!!,
            onDismiss = { categoriaSeleccionada = null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Encabezado
        Text(
            text = "Catálogo de Servicios",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Servicios técnicos profesionales para PS4 y PS5",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Lista de categorías
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(categorias) { categoria ->
                CategoriaCard(
                    nombre = categoria.nombre,
                    descripcion = categoria.descripcion,
                    icono = categoria.icono,
                    cantidadServicios = categoria.serviciosIncluidos.size,
                    onClick = { categoriaSeleccionada = categoria }
                )
            }

            // Espaciado final
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// 🔍 Dialog para mostrar detalles de una categoría
@Composable
fun DetallesCategoriaDialog(
    categoria: CategoriaServicio,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Text(
                text = categoria.icono,
                style = MaterialTheme.typography.displayMedium
            )
        },
        title = {
            Text(
                text = categoria.nombre,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column {
                Text(
                    text = categoria.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Servicios incluidos:",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                categoria.serviciosIncluidos.forEach { servicio ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "• ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = servicio,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

