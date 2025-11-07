package appserviciotecnico.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import appserviciotecnico.model.EstadoSolicitud
import appserviciotecnico.model.Solicitud
import appserviciotecnico.ui.components.SolicitudCard
import appserviciotecnico.viewmodel.EstadoSolicitudesViewModel
import appserviciotecnico.viewmodel.EstadoSolicitudesViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

// 📊 Pantalla para visualizar estado de solicitudes
@Composable
fun EstadoSolicitudesScreen() {

    val context = LocalContext.current
    val viewModel: EstadoSolicitudesViewModel = viewModel(
        factory = EstadoSolicitudesViewModelFactory(context.applicationContext as Application)
    )

    // Obtener solicitudes desde Room Database
    val solicitudes by viewModel.solicitudes.collectAsState()

    // Estado para filtro
    var filtroSeleccionado by remember { mutableStateOf<EstadoSolicitud?>(null) }

    // Solicitud seleccionada para ver detalles
    var solicitudSeleccionada by remember { mutableStateOf<Solicitud?>(null) }

    // Filtrar solicitudes
    val solicitudesFiltradas = if (filtroSeleccionado == null) {
        solicitudes
    } else {
        solicitudes.filter { it.estado == filtroSeleccionado }
    }

    // Dialog de detalles
    if (solicitudSeleccionada != null) {
        DetallesSolicitudDialog(
            solicitud = solicitudSeleccionada!!,
            onDismiss = { solicitudSeleccionada = null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Encabezado
        Text(
            text = "Mis Solicitudes",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Revisa el estado de tus servicios técnicos",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Filtros por estado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Botón "Todas"
            FilterChip(
                selected = filtroSeleccionado == null,
                onClick = { filtroSeleccionado = null },
                label = { Text("Todas (${solicitudes.size})") }
            )

            // Filtros por estado
            EstadoSolicitud.entries.forEach { estado ->
                val cantidad = solicitudes.count { it.estado == estado }
                if (cantidad > 0) {
                    FilterChip(
                        selected = filtroSeleccionado == estado,
                        onClick = {
                            filtroSeleccionado = if (filtroSeleccionado == estado) null else estado
                        },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(estado.icono)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("$cantidad")
                            }
                        }
                    )
                }
            }
        }

        // Lista de solicitudes
        if (solicitudesFiltradas.isEmpty()) {
            // Estado vacío
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "📭",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No hay solicitudes",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = if (filtroSeleccionado != null)
                            "No tienes solicitudes ${filtroSeleccionado!!.texto.lowercase()}"
                        else
                            "Aún no has realizado ninguna solicitud",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(solicitudesFiltradas) { solicitud ->
                    SolicitudCard(
                        solicitud = solicitud,
                        onVerDetalles = { solicitudSeleccionada = it }
                    )
                }

                // Espaciado final
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// 🔍 Dialog para mostrar detalles de una solicitud
@Composable
fun DetallesSolicitudDialog(
    solicitud: Solicitud,
    onDismiss: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Text(
                text = solicitud.estado.icono,
                style = MaterialTheme.typography.displayMedium
            )
        },
        title = {
            Text(
                text = "Detalles de Solicitud",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ID
                DetailRow("ID:", "#${solicitud.id.toString().padStart(4, '0')}")

                HorizontalDivider()

                // Servicio
                DetailRow("Servicio:", solicitud.servicio)

                // Estado
                DetailRow("Estado:", "${solicitud.estado.icono} ${solicitud.estado.texto}")

                HorizontalDivider()

                // Fecha
                DetailRow("Fecha:", dateFormatter.format(solicitud.fechaAgendada))

                // Hora
                DetailRow("Hora:", solicitud.horaAgendada)

                HorizontalDivider()

                // Descripción
                Column {
                    Text(
                        text = "Descripción:",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = solicitud.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Mensaje según estado
                when (solicitud.estado) {
                    EstadoSolicitud.PENDIENTE -> {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "ℹ️ Tu solicitud está en espera. Te contactaremos pronto.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    EstadoSolicitud.EN_PROCESO -> {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "🔧 Nuestro técnico está trabajando en tu equipo.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    EstadoSolicitud.COMPLETADO -> {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "✅ Servicio completado exitosamente.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    EstadoSolicitud.CANCELADO -> {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "❌ Esta solicitud fue cancelada.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
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

// Helper para mostrar filas de detalles
@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.6f)
        )
    }
}

