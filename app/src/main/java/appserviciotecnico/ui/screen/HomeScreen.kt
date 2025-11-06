package appserviciotecnico.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 🏠 Pantalla principal después del login
@Composable
fun HomeScreen() {
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
    }
}

