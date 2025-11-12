package appserviciotecnico.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import appserviciotecnico.utils.NativeResourcesHelper

// 🏠 Pantalla principal después del login
@Composable
fun HomeScreen() {
    val context = LocalContext.current

    // 🎨 Gradiente azulado suave para el fondo
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE3F2FD),  // Azul muy claro
            Color(0xFFBBDEFB),  // Azul claro
            Color(0xFF90CAF9)   // Azul medio claro
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Card principal de bienvenida
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Text(
                        text = "🎮 Bienvenido",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color(0xFF0099CC),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Soporte Técnico PlayStation",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0A2342),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "PS4 • PS5",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF00D9FF),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Text(
                        text = "Navega por el menú para explorar nuestros servicios",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // Botones de recursos nativos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón Llamar a Soporte
                Button(
                    onClick = {
                        NativeResourcesHelper.vibrar(context)
                        NativeResourcesHelper.llamarSoporteTecnico(context)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Llamar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Llamar")
                }

                // Botón Ver Ubicación
                Button(
                    onClick = {
                        NativeResourcesHelper.vibrar(context)
                        NativeResourcesHelper.abrirUbicacionEnMaps(context)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ubicación",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ubicación")
                }
            }
        }
    }
}

