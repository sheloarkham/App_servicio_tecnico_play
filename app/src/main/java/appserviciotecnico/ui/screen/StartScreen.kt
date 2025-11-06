package appserviciotecnico.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import appserviciotecnico.R
import kotlinx.coroutines.delay

@Composable
fun StartScreen(onNavigateToLogin: () -> Unit = {}) {
    // Navegar automáticamente al login después de 2 segundos
    LaunchedEffect(Unit) {
        delay(2000) // 2 segundos mostrando el logo
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logojuega),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}