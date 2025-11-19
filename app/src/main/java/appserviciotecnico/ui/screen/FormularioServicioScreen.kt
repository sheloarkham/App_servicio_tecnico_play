package appserviciotecnico.ui.screen

import android.app.Application
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import appserviciotecnico.ui.components.InputText
import appserviciotecnico.utils.NativeResourcesHelper
import appserviciotecnico.viewmodel.FormularioServicioViewModel
import appserviciotecnico.viewmodel.FormularioServicioViewModelFactory

// Pantalla de formulario de servicio técnico PlayStation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioServicioScreen() {

    val context = LocalContext.current
    val viewModel: FormularioServicioViewModel = viewModel(
        factory = FormularioServicioViewModelFactory(context.applicationContext as Application)
    )
    val estado by viewModel.estado.collectAsState()

    // Lista de tipos de consolas
    val tiposConsola = listOf("PS4", "PS5")
    var expandedConsola by remember { mutableStateOf(false) }

    // Lista de modelos PS4
    val modelosPS4 = listOf(
        "PS4 Original",
        "PS4 Slim",
        "PS4 Pro"
    )

    // Lista de modelos PS5
    val modelosPS5 = listOf(
        "PS5 Standard",
        "PS5 Digital Edition"
    )

    var expandedModelo by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 16.dp), // Padding extra al final
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
        // Título
        Text(
            text = "Solicitar Cotización",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Text(
            text = "Describe el problema de tu consola y te enviaremos una cotización aproximada del costo de reparación",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Datos del cliente
        Text(
            text = "Datos del cliente",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        InputText(
            valor = estado.nombreCliente,
            error = estado.errores.nombreCliente,
            label = "Nombre completo",
            onChange = viewModel::onNombreChange
        )

        InputText(
            valor = estado.correoCliente,
            error = estado.errores.emailCliente,
            label = "Correo electrónico",
            onChange = viewModel::onCorreoChange
        )

        InputText(
            valor = estado.telefonoCliente,
            error = estado.errores.telefonoCliente,
            label = "Teléfono de contacto",
            onChange = viewModel::onTelefonoChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Datos de la consola
        Text(
            text = "Datos de la consola",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        // ComboBox Tipo de Consola
        ExposedDropdownMenuBox(
            expanded = expandedConsola,
            onExpandedChange = { expandedConsola = !expandedConsola }
        ) {
            OutlinedTextField(
                value = estado.tipoConsola,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de consola") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedConsola)
                },
                isError = estado.errores.tipoConsola != null,
                supportingText = {
                    estado.errores.tipoConsola?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            ExposedDropdownMenu(
                expanded = expandedConsola,
                onDismissRequest = { expandedConsola = false }
            ) {
                tiposConsola.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            viewModel.onTipoConsolaChange(tipo)
                            viewModel.onModeloConsolaChange("") // Limpiar modelo al cambiar tipo
                            expandedConsola = false
                        }
                    )
                }
            }
        }

        // ComboBox Modelo de Consola
        val modelosDisponibles = when (estado.tipoConsola) {
            "PS4" -> modelosPS4
            "PS5" -> modelosPS5
            else -> emptyList()
        }

        ExposedDropdownMenuBox(
            expanded = expandedModelo,
            onExpandedChange = { expandedModelo = !expandedModelo && modelosDisponibles.isNotEmpty() }
        ) {
            OutlinedTextField(
                value = estado.modeloConsola,
                onValueChange = {},
                readOnly = true,
                enabled = modelosDisponibles.isNotEmpty(),
                label = { Text("Modelo") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedModelo)
                },
                isError = estado.errores.modeloConsola != null,
                supportingText = {
                    estado.errores.modeloConsola?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            if (modelosDisponibles.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expandedModelo,
                    onDismissRequest = { expandedModelo = false }
                ) {
                    modelosDisponibles.forEach { modelo ->
                        DropdownMenuItem(
                            text = { Text(modelo) },
                            onClick = {
                                viewModel.onModeloConsolaChange(modelo)
                                expandedModelo = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Descripción del problema
        Text(
            text = "Descripción de la falla",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        InputText(
            valor = estado.descripcionProblema,
            error = estado.errores.descripcionProblema,
            label = "Describa detalladamente la falla o problema que presenta la consola",
            onChange = viewModel::onDescripcionChange,
            maxLines = 5
        )

        // Botón para adjuntar foto
        OutlinedButton(
            onClick = {
                NativeResourcesHelper.vibrar(context)
                NativeResourcesHelper.abrirCamara(context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adjuntar Foto de la Consola")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mensaje de éxito con animación
        AnimatedVisibility(
            visible = estado.mensajeExito != null,
            enter = slideInVertically(
                initialOffsetY = { -it / 2 },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(animationSpec = tween(300)),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(200)
            ) + fadeOut(animationSpec = tween(200))
        ) {
            estado.mensajeExito?.let { mensaje ->
                Column {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text(
                            text = mensaje,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Botón enviar
        Button(
            onClick = {
                viewModel.onEnviarFormulario()
                NativeResourcesHelper.vibrarExito(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !estado.enviando,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            if (estado.enviando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Enviando cotización...",
                    style = MaterialTheme.typography.labelLarge
                )
            } else {
                Text(
                    "Solicitar Cotización",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Espacio adicional al final para asegurar que el botón sea visible al hacer scroll
        Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

