package appserviciotecnico.ui.screen

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import appserviciotecnico.model.data.AppDatabase
import appserviciotecnico.model.entities.SolicitudEntity
import appserviciotecnico.model.repository.SolicitudRepository
import appserviciotecnico.utils.NativeResourcesHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// 📅 Pantalla para agendar servicio técnico
@Suppress("unused")
@Composable
fun AgendarServicioScreen(
    categoriaId: Int,
    categoriaNombre: String
) {
    var fechaSeleccionada by remember { mutableStateOf<Date?>(null) }
    var horaSeleccionada by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var mostrarConfirmacion by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }
    var errorHora by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Repository para guardar en Room
    val repository = remember {
        val database = AppDatabase.getDatabase(context.applicationContext as Application)
        SolicitudRepository(database.solicitudDao())
    }
    val scope = rememberCoroutineScope()

    // Formatters
    val dateFormatter = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-ES"))

    // DatePicker Dialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            // Validar que sea de lunes (2) a sábado (7)
            if (dayOfWeek == Calendar.SUNDAY) {
                // Si es domingo, mostrar error
                errorFecha = true
                fechaSeleccionada = null
            } else {
                fechaSeleccionada = calendar.time
                errorFecha = false
            }
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = System.currentTimeMillis() // No permitir fechas pasadas
    }

    // TimePicker Dialog con restricción de 10:00 a 18:00
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            // Validar que esté dentro del horario laboral (10:00 - 18:00)
            if (hourOfDay < 10 || hourOfDay >= 18) {
                errorHora = true
                horaSeleccionada = null
            } else {
                horaSeleccionada = Pair(hourOfDay, minute)
                errorHora = false
            }
        },
        10, // Hora inicial sugerida: 10:00
        0,  // Minuto inicial: 00
        true // Formato 24 horas
    )

    // Dialog de confirmación
    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            icon = {
                Text("✅", style = MaterialTheme.typography.displayMedium)
            },
            title = {
                Text(
                    "Cita Confirmada",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column {
                    Text(
                        "Tu cita ha sido agendada exitosamente:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text("📦 Servicio:", fontWeight = FontWeight.Bold)
                    Text(categoriaNombre, modifier = Modifier.padding(bottom = 8.dp))

                    Text("📅 Fecha:", fontWeight = FontWeight.Bold)
                    Text(
                        fechaSeleccionada?.let { dateFormatter.format(it) } ?: "",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text("⏰ Hora:", fontWeight = FontWeight.Bold)
                    horaSeleccionada?.let { (hour, minute) ->
                        Text(String.format(Locale.getDefault(), "%02d:%02d", hour, minute))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Nos pondremos en contacto contigo para confirmar los detalles.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = {
                            // Agregar al calendario
                            fechaSeleccionada?.let { fecha ->
                                horaSeleccionada?.let { (hora, minuto) ->
                                    val calendar = Calendar.getInstance().apply {
                                        time = fecha
                                        set(Calendar.HOUR_OF_DAY, hora)
                                        set(Calendar.MINUTE, minuto)
                                    }

                                    NativeResourcesHelper.agregarCitaAlCalendario(
                                        context = context,
                                        titulo = "Servicio Técnico PlayStation - $categoriaNombre",
                                        descripcion = "Cita para servicio técnico de PlayStation en nuestro taller.",
                                        fechaInicio = calendar.timeInMillis,
                                        duracionMinutos = 60
                                    )
                                    NativeResourcesHelper.vibrarExito(context)
                                }
                            }
                        }
                    ) {
                        Text("📅 Agregar al Calendario")
                    }

                    Button(onClick = {
                        NativeResourcesHelper.vibrarExito(context)
                        mostrarConfirmacion = false
                    }) {
                        Text("Aceptar")
                    }
                }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título
            Text(
                text = "Agendar Servicio",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Selecciona fecha y hora para tu servicio técnico",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card del servicio seleccionado
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Servicio seleccionado:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = categoriaNombre,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Selector de fecha
            Text(
                text = "Fecha de la visita",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            OutlinedButton(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (errorFecha)
                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = fechaSeleccionada?.let {
                        val fecha = dateFormatter.format(it).replaceFirstChar { char ->
                            if (char.isLowerCase()) char.titlecase(Locale.forLanguageTag("es-ES")) else char.toString()
                        }
                        "📅 $fecha"
                    } ?: "Seleccionar fecha",
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (errorFecha) {
                Text(
                    text = "Por favor selecciona una fecha válida (Lunes a Sábado)",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Selector de hora
            Text(
                text = "Hora de la visita",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            OutlinedButton(
                onClick = { timePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (errorHora)
                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = horaSeleccionada?.let { (hour, minute) ->
                        "⏰ ${String.format(Locale.getDefault(), "%02d:%02d", hour, minute)}"
                    } ?: "Seleccionar hora",
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (errorHora) {
                Text(
                    text = "Por favor selecciona una hora entre las 10:00 y 18:00",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información adicional
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "ℹ️ Información importante",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "• Horario de atención: Lunes a Sábado\n" +
                        "• Hora: 10:00 AM - 6:00 PM (18:00)\n" +
                        "• Domingos cerrado\n" +
                        "• Recibirás confirmación por correo\n" +
                        "• Un técnico te contactará 24h antes\n" +
                        "• Duración estimada: 2-4 horas\n" +
                        "• Servicio a domicilio disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón confirmar
            Button(
                onClick = {
                    // Validar
                    if (fechaSeleccionada == null) {
                        errorFecha = true
                    }
                    if (horaSeleccionada == null) {
                        errorHora = true
                    }

                    // Si todo está bien, guardar en Room y mostrar confirmación
                    if (fechaSeleccionada != null && horaSeleccionada != null) {
                        val horaFormateada = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            horaSeleccionada!!.first,
                            horaSeleccionada!!.second
                        )

                        val solicitud = SolicitudEntity(
                            servicio = categoriaNombre,
                            fechaAgendada = fechaSeleccionada!!.time,
                            horaAgendada = horaFormateada,
                            estado = "Pendiente",
                            clienteNombre = "Usuario",
                            descripcion = "Servicio de $categoriaNombre agendado",
                            categoriaId = categoriaId
                        )

                        // Guardar usando coroutine scope
                        scope.launch {
                            try {
                                repository.guardarSolicitud(solicitud)
                                mostrarConfirmacion = true
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Confirmar Cita",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

