package appserviciotecnico.ui.screen

import android.app.Application
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import appserviciotecnico.ui.components.*
import appserviciotecnico.viewmodel.ExternalUiState
import appserviciotecnico.viewmodel.ExternalViewModel
import appserviciotecnico.viewmodel.viewmodels.HomeViewModel
import appserviciotecnico.viewmodel.factories.HomeViewModelFactory
import coil.compose.AsyncImage

//  Pantalla de inicio mejorada con dashboard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCatalog: () -> Unit = {},
    onNavigateToAgendar: () -> Unit = {},
    onNavigateToSolicitudes: () -> Unit = {},
    onNavigateToFormulario: () -> Unit = {},
    onNavigateToExternalInfo: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(context.applicationContext as Application)
    )
    val estado by viewModel.estado.collectAsState()

    // ViewModel para API externa
    val externalViewModel: ExternalViewModel = viewModel()
    val externalUiState by externalViewModel.uiState.collectAsState()
    val juegos by externalViewModel.juegos.collectAsState()

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

            // Sección de Novedades en Gaming (API Externa)
            item {
                GamingNewsSection(
                    uiState = externalUiState,
                    juegos = juegos.take(5),
                    onVerMas = onNavigateToExternalInfo,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
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

/**
 * Sección de novedades en gaming usando API externa
 */
@Composable
private fun GamingNewsSection(
    uiState: ExternalUiState,
    juegos: List<appserviciotecnico.network.models.external.Game>,
    onVerMas: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Header de la sección
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "🎮 Novedades en Gaming",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            TextButton(onClick = onVerMas) {
                Text("Ver más")
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Contenido según el estado
        when (uiState) {
            is ExternalUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                }
            }
            is ExternalUiState.Error -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⚠️ No se pudieron cargar las novedades",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            is ExternalUiState.Success -> {
                if (juegos.isEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No hay juegos disponibles")
                        }
                    }
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(juegos) { juego ->
                            GameMiniCard(
                                juego = juego,
                                onClick = onVerMas
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Card compacta para mostrar un juego en el dashboard
 */
@Composable
private fun GameMiniCard(
    juego: appserviciotecnico.network.models.external.Game,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(220.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de fondo
            val imageUrl = juego.getFullImageUrl()
            if (!imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = juego.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🎮",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }

            // Gradiente oscuro para mejor legibilidad del texto
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 100f
                        )
                    )
            )

            // Información del juego
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Rating en la esquina superior
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFD700).copy(alpha = 0.9f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = juego.rating.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Nombre del juego
                Column {
                    Text(
                        text = juego.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!juego.released.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = juego.released,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}


