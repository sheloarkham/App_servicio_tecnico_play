package appserviciotecnico.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import appserviciotecnico.network.models.SolicitudDTO
import appserviciotecnico.network.utils.NetworkResult
import appserviciotecnico.viewmodel.SolicitudBackendViewModel

/**
 * Pantalla principal para gestionar solicitudes conectadas al backend
 * Muestra lista de solicitudes y permite crear, editar y eliminar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudBackendScreen(
    viewModel: SolicitudBackendViewModel = viewModel()
) {
    val solicitudesState by viewModel.solicitudesState.collectAsState()
    val crearState by viewModel.crearSolicitudState.collectAsState()
    val eliminarState by viewModel.eliminarState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var solicitudAEditar by remember { mutableStateOf<SolicitudDTO?>(null) }

    // Cargar solicitudes al inicio
    LaunchedEffect(Unit) {
        viewModel.obtenerSolicitudes()
    }

    // Mostrar mensaje de éxito al crear
    LaunchedEffect(crearState) {
        if (crearState is NetworkResult.Success) {
            viewModel.resetCrearState()
            showDialog = false
        }
    }

    // Mostrar mensaje de éxito al eliminar
    LaunchedEffect(eliminarState) {
        if (eliminarState is NetworkResult.Success) {
            viewModel.resetEliminarState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Solicitudes (Backend)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    solicitudAEditar = null
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear Solicitud")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Indicador de estado
            when (solicitudesState) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is NetworkResult.Success -> {
                    val solicitudes = (solicitudesState as NetworkResult.Success).data

                    if (solicitudes.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay solicitudes. Crea una nueva.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(solicitudes) { solicitud ->
                                SolicitudCard(
                                    solicitud = solicitud,
                                    onEdit = {
                                        solicitudAEditar = solicitud
                                        showDialog = true
                                    },
                                    onDelete = {
                                        solicitud.id?.let { viewModel.eliminarSolicitud(it) }
                                    }
                                )
                            }
                        }
                    }
                }

                is NetworkResult.Error -> {
                    val error = (solicitudesState as NetworkResult.Error).message

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Error: $error",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.obtenerSolicitudes() }) {
                            Text("Reintentar")
                        }
                    }
                }

                is NetworkResult.Idle -> {
                    // Estado inicial
                }
            }
        }

        // Diálogo para crear/editar solicitud
        if (showDialog) {
            SolicitudFormDialog(
                solicitud = solicitudAEditar,
                onDismiss = { showDialog = false },
                onSave = { solicitud ->
                    if (solicitudAEditar == null) {
                        viewModel.crearSolicitud(solicitud)
                    } else {
                        solicitud.id?.let { viewModel.actualizarSolicitud(it, solicitud) }
                    }
                }
            )
        }
    }
}

/**
 * Card para mostrar cada solicitud
 */
@Composable
fun SolicitudCard(
    solicitud: SolicitudDTO,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = solicitud.servicio,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Cliente: ${solicitud.clienteNombre}")
            Text("Fecha: ${solicitud.fechaAgendada} - ${solicitud.horaAgendada}")
            Text("Estado: ${solicitud.estado}")
            Text("Descripción: ${solicitud.descripcion}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

/**
 * Diálogo para crear o editar una solicitud
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudFormDialog(
    solicitud: SolicitudDTO?,
    onDismiss: () -> Unit,
    onSave: (SolicitudDTO) -> Unit
) {
    var servicio by remember { mutableStateOf(solicitud?.servicio ?: "") }
    var clienteNombre by remember { mutableStateOf(solicitud?.clienteNombre ?: "") }
    var descripcion by remember { mutableStateOf(solicitud?.descripcion ?: "") }
    var fechaAgendada by remember { mutableStateOf(solicitud?.fechaAgendada ?: "") }
    var horaAgendada by remember { mutableStateOf(solicitud?.horaAgendada ?: "") }
    var estado by remember { mutableStateOf(solicitud?.estado ?: "PENDIENTE") }
    var categoriaId by remember { mutableStateOf(solicitud?.categoriaId?.toString() ?: "1") }

    val estados = listOf("PENDIENTE", "EN_PROCESO", "COMPLETADO", "CANCELADO")
    var expandedEstado by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (solicitud == null) "Nueva Solicitud" else "Editar Solicitud")
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = servicio,
                    onValueChange = { servicio = it },
                    label = { Text("Servicio") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = clienteNombre,
                    onValueChange = { clienteNombre = it },
                    label = { Text("Cliente") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                OutlinedTextField(
                    value = fechaAgendada,
                    onValueChange = { fechaAgendada = it },
                    label = { Text("Fecha (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = horaAgendada,
                    onValueChange = { horaAgendada = it },
                    label = { Text("Hora (HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expandedEstado,
                    onExpandedChange = { expandedEstado = !expandedEstado }
                ) {
                    OutlinedTextField(
                        value = estado,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Estado") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEstado)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEstado,
                        onDismissRequest = { expandedEstado = false }
                    ) {
                        estados.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    estado = item
                                    expandedEstado = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = categoriaId,
                    onValueChange = { categoriaId = it },
                    label = { Text("Categoría ID") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val nuevaSolicitud = SolicitudDTO(
                        id = solicitud?.id,
                        servicio = servicio,
                        clienteNombre = clienteNombre,
                        descripcion = descripcion,
                        fechaAgendada = fechaAgendada,
                        horaAgendada = horaAgendada,
                        estado = estado,
                        categoriaId = categoriaId.toIntOrNull() ?: 1
                    )
                    onSave(nuevaSolicitud)
                },
                enabled = servicio.isNotBlank() && clienteNombre.isNotBlank()
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

