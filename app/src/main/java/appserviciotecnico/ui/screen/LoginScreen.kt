package appserviciotecnico.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

// Pantalla de Login con validación básica
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 🎛 Estados para los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // 🎨 Gradiente azulado neón
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A2342),  // Azul profundo
            Color(0xFF16213E),  // Azul medio
            Color(0xFF0F3460)   // Azul oscuro
        )
    )

    // 📐 Diseño en columna centrada
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 📝 Título
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF00D9FF), // Azul neón brillante
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 📧 Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = "" // Limpiar error al escribir
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = errorMessage.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00D9FF),
                unfocusedBorderColor = Color(0xFF66E6FF),
                focusedLabelColor = Color(0xFF00D9FF),
                unfocusedLabelColor = Color(0xFF66E6FF),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFF00D9FF)
            )
        )

        // 🔒 Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = "" // Limpiar error al escribir
            },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = errorMessage.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF00D9FF),
                unfocusedBorderColor = Color(0xFF66E6FF),
                focusedLabelColor = Color(0xFF00D9FF),
                unfocusedLabelColor = Color(0xFF66E6FF),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFF00D9FF)
            )
        )

        // ⚠️ Mensaje de error
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // 🔘 Botón Ingresar
        Button(
            onClick = {
                // Validación de campos vacíos
                when {
                    email.isBlank() && password.isBlank() -> {
                        errorMessage = "Por favor completa todos los campos"
                    }
                    email.isBlank() -> {
                        errorMessage = "Por favor ingresa tu correo electrónico"
                    }
                    password.isBlank() -> {
                        errorMessage = "Por favor ingresa tu contraseña"
                    }
                    else -> {
                        //  Validación exitosa - navegar a pantalla de inicio
                        errorMessage = ""
                        onLoginSuccess()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00D9FF),
                contentColor = Color.Black
            )
        ) {
            Text("Ingresar", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 👤 Botón Ingresar como invitado
        OutlinedButton(
            onClick = {
                // Ingresar sin autenticación
                onLoginSuccess()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF66E6FF)
            ),
            border = androidx.compose.foundation.BorderStroke(
                2.dp,
                Color(0xFF66E6FF)
            )
        ) {
            Text("Ingresar como invitado", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ℹ️ Texto informativo
        Text(
            text = "Nota: Se requerirá iniciar sesión para solicitar servicios técnicos",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF66E6FF),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

