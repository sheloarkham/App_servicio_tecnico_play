package appserviciotecnico.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import appserviciotecnico.model.domain.EstadoSolicitud
import appserviciotecnico.model.domain.Solicitud
import java.text.SimpleDateFormat
import java.util.*

//  Card para mostrar una solicitud de servicio
@Suppress("unused")
@Composable
fun SolicitudCard(
    solicitud: Solicitud,
    modifier: Modifier = Modifier,
    onVerDetalles: (Solicitud) -> Unit = {},
    onEditar: ((Solicitud) -> Unit)? = null,
    onEliminar: ((Solicitud) -> Unit)? = null
) {
    val dateFormatter = SimpleDateFormat("EEEE, dd 'de' MMMM", Locale.forLanguageTag("es-ES"))

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Servicio + Estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Nombre del servicio
                Text(
                    text = solicitud.servicio,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Badge de estado
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = Color(solicitud.estado.color).copy(alpha = 0.2f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = solicitud.estado.icono,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = solicitud.estado.texto,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color(solicitud.estado.color)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Descripción
            Text(
                text = solicitud.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Información de fecha y hora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Fecha
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📅",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = dateFormatter.format(solicitud.fechaAgendada).replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.forLanguageTag("es-ES")) else it.toString()
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Hora
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⏰",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = solicitud.horaAgendada,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ID de solicitud
            Text(
                text = "ID: #${solicitud.id.toString().padStart(4, '0')}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botón Ver Detalles
                OutlinedButton(
                    onClick = { onVerDetalles(solicitud) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Detalles")
                }

                // Botón Editar (solo si está habilitado)
                if (onEditar != null && solicitud.estado != EstadoSolicitud.CANCELADO && solicitud.estado != EstadoSolicitud.COMPLETADO) {
                    OutlinedButton(
                        onClick = { onEditar(solicitud) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Editar")
                    }
                }

                // Botón Eliminar (solo si está habilitado)
                if (onEliminar != null) {
                    OutlinedButton(
                        onClick = { onEliminar(solicitud) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}

