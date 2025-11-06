package appserviciotecnico.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎨 Colores azulados neón personalizados
private val NeonBlue = Color(0xFF00D9FF)        // Azul neón brillante
private val DarkNeonBlue = Color(0xFF0099CC)   // Azul neón oscuro
private val LightNeonBlue = Color(0xFF66E6FF)  // Azul neón claro
private val ElectricBlue = Color(0xFF1E90FF)   // Azul eléctrico
private val DeepBlue = Color(0xFF0A2342)       // Azul profundo
private val CyberBlue = Color(0xFF00FFFF)      // Azul cibernético

private val DarkColorScheme = darkColorScheme(
    primary = NeonBlue,
    onPrimary = Color.Black,
    primaryContainer = DarkNeonBlue,
    onPrimaryContainer = Color.White,
    secondary = CyberBlue,
    onSecondary = Color.Black,
    secondaryContainer = ElectricBlue,
    onSecondaryContainer = Color.White,
    tertiary = LightNeonBlue,
    onTertiary = Color.Black,
    background = DeepBlue,
    onBackground = Color.White,
    surface = Color(0xFF1A1A2E),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF16213E),
    onSurfaceVariant = LightNeonBlue,
    error = Color(0xFFFF6B9D)
)

private val LightColorScheme = lightColorScheme(
    primary = DarkNeonBlue,
    onPrimary = Color.White,
    primaryContainer = LightNeonBlue,
    onPrimaryContainer = DeepBlue,
    secondary = ElectricBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB3E5FC),
    onSecondaryContainer = DeepBlue,
    tertiary = NeonBlue,
    onTertiary = Color.White,
    background = Color.White,
    onBackground = DeepBlue,
    surface = Color(0xFFF5F5F5),
    onSurface = DeepBlue,
    surfaceVariant = Color(0xFFE3F2FD),
    onSurfaceVariant = DarkNeonBlue,
    error = Color(0xFFD32F2F)
)

@Composable
fun AppServTecnicoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

