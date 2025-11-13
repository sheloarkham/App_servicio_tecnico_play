package appserviciotecnico.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Botones de contacto (Llamar y WhatsApp)
@Composable
fun ContactButtons(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Botón Llamar
        Button(
            onClick = { llamarSoporte(context) },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Llamar",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Llamar",
                fontWeight = FontWeight.Bold
            )
        }

        // Botón WhatsApp
        Button(
            onClick = { abrirWhatsApp(context) },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF25D366)
            ),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "WhatsApp",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "WhatsApp",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun llamarSoporte(context: Context) {
    val telefono = "56912345678" // Cambia esto por el número real
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$telefono")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No se pudo realizar la llamada", Toast.LENGTH_SHORT).show()
    }
}

private fun abrirWhatsApp(context: Context) {
    val telefono = "56912345678" // Cambia esto por el número real
    val mensaje = "Hola, necesito soporte técnico para mi consola"
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$telefono?text=${Uri.encode(mensaje)}")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
    }
}

