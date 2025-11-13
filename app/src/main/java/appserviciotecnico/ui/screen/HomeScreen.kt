package appserviciotecnico.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import appserviciotecnico.ui.components.*
import appserviciotecnico.viewmodel.HomeViewModel
import appserviciotecnico.viewmodel.HomeViewModelFactory

//  Pantalla de inicio mejorada con dashboard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCatalog: () -> Unit = {},
    onNavigateToAgendar: () -> Unit = {},
    onNavigateToSolicitudes: () -> Unit = {},
    onNavigateToFormulario: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(context.applicationContext as android.app.Application)
    )
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToFormulario,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Solicitar servicio"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Header con saludo
            item {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { -40 }
                    )
                ) {
                    HomeHeader(nombreUsuario = estado.nombreUsuario)
                }
            }

            // Banner informativo
            item {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(durationMillis = 600, delayMillis = 100))
                ) {
                    PromoBanner(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                }
            }

            // Sección de estadísticas
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mis Solicitudes",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    if (!estado.isLoading) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            SummaryCard(
                                title = "Pendientes",
                                value = estado.solicitudesPendientes.toString(),
                                icon = Icons.Default.Warning,
                                iconColor = Color(0xFFFFA726),
                                modifier = Modifier.weight(1f)
                            )
                            SummaryCard(
                                title = "Completadas",
                                value = estado.solicitudesCompletadas.toString(),
                                icon = Icons.Default.CheckCircle,
                                iconColor = Color(0xFF66BB6A),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Próxima cita
            estado.proximaCita?.let { cita ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Próxima cita",
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Próxima Cita",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = cita.servicio,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Text(
                                    text = "${cita.fecha} a las ${cita.hora}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }
                    }
                }
            }

            // Accesos rápidos
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Accesos Rápidos",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    val quickAccessItems = listOf(
                        QuickAccessItem(
                            "Catálogo de Servicios",
                            Icons.Default.Build,
                            listOf(Color(0xFF1E88E5), Color(0xFF1565C0)),
                            onNavigateToCatalog
                        ),
                        QuickAccessItem(
                            "Agendar Cita",
                            Icons.Default.DateRange,
                            listOf(Color(0xFF43A047), Color(0xFF2E7D32)),
                            onNavigateToAgendar
                        ),
                        QuickAccessItem(
                            "Mis Solicitudes",
                            Icons.Default.List,
                            listOf(Color(0xFFFF7043), Color(0xFFE64A19)),
                            onNavigateToSolicitudes
                        ),
                        QuickAccessItem(
                            "Cotización",
                            Icons.Default.Edit,
                            listOf(Color(0xFF8E24AA), Color(0xFF6A1B9A)),
                            onNavigateToFormulario
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        quickAccessItems.take(2).forEach { item ->
                            QuickAccessCard(
                                title = item.title,
                                icon = item.icon,
                                gradientColors = item.colors,
                                onClick = item.onClick,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        quickAccessItems.drop(2).forEach { item ->
                            QuickAccessCard(
                                title = item.title,
                                icon = item.icon,
                                gradientColors = item.colors,
                                onClick = item.onClick,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Botones de contacto
            item {
                ContactButtons(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

private data class QuickAccessItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val colors: List<Color>,
    val onClick: () -> Unit
)

