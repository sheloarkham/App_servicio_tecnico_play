package appserviciotecnico.ui.screen

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import appserviciotecnico.model.domain.Solicitud
import appserviciotecnico.viewmodel.EditarSolicitudViewModel
import appserviciotecnico.viewmodel.EditarSolicitudViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

// ✏️ Pantalla para editar fecha y hora de una solicitud
@Composable
fun EditarSolicitudScreen(
    solicitud: Solicitud,
    onGuardado: () -> Unit,
    onCancelar: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: EditarSolicitudViewModel = viewModel(
        factory = EditarSolicitudViewModelFactory(context.applicationContext as Application, solicitud)
    )

    val fechaSeleccionada by viewModel.fechaSeleccionada.collectAsState()
    val horaSeleccionada by viewModel.horaSeleccionada.collectAsState()
    val errorFecha by viewModel.errorFecha.collectAsState()
    val errorHora by viewModel.errorHora.collectAsState()
    val guardando by viewModel.guardando.collectAsState()

    val dateFormatter = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-ES"))
    val calendar = Calendar.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        Text(
            text = "Editar Cita",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        // Información del servicio
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Servicio: ${solicitud.servicio}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Cliente: ${solicitud.clienteNombre}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Selector de Fecha
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        val selectedCalendar = Calendar.getInstance().apply {
                            set(year, month, day)
                        }

                        // Validar que no sea domingo (SUNDAY = 1)
                        if (selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                            viewModel.setErrorFecha("❌ Los domingos el local está cerrado")
                        } else {
                            viewModel.setFecha(selectedCalendar.time)
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                // No permitir fechas pasadas
                datePickerDialog.datePicker.minDate = System.currentTimeMillis()
                datePickerDialog.show()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "📅 Fecha de Cita",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = dateFormatter.format(fechaSeleccionada).replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.forLanguageTag("es-ES")) else it.toString()
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                if (errorFecha != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = errorFecha!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        // Selector de Hora
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        // Validar horario: Lunes a Sábado 10:00 - 18:00
                        if (hourOfDay < 10 || hourOfDay >= 18) {
                            viewModel.setErrorHora("⏰ Horario de atención: 10:00 - 18:00")
                        } else {
                            val horaFormateada = String.format("%02d:%02d", hourOfDay, minute)
                            viewModel.setHora(horaFormateada)
                        }
                    },
                    10,
                    0,
                    true // Formato 24 horas
                )
                timePickerDialog.show()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "⏰ Hora de Cita",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = horaSeleccionada,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                if (errorHora != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = errorHora!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        // Información de horario
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = "ℹ️ Horario de Atención",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Lunes a Sábado: 10:00 - 18:00",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "Domingo: Cerrado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.weight(1f),
                enabled = !guardando
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = {
                    viewModel.guardarCambios {
                        onGuardado()
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = !guardando && errorFecha == null && errorHora == null
            ) {
                if (guardando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar Cambios")
                }
            }
        }
    }
}

