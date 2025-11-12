package appserviciotecnico.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.CalendarContract
import android.provider.MediaStore
import android.widget.Toast

/**
 * 📱 Utilidades para acceder a recursos nativos del dispositivo
 */
object NativeResourcesHelper {

    /**
     * 📞 Abrir el marcador de teléfono con un número específico
     */
    fun llamarSoporteTecnico(context: Context, numeroTelefono: String = "+56912345678") {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$numeroTelefono")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir el marcador: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 📅 Agregar evento al calendario del dispositivo
     */
    fun agregarCitaAlCalendario(
        context: Context,
        titulo: String,
        descripcion: String,
        fechaInicio: Long,
        duracionMinutos: Int = 60
    ) {
        try {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, titulo)
                putExtra(CalendarContract.Events.DESCRIPTION, descripcion)
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fechaInicio)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fechaInicio + (duracionMinutos * 60 * 1000))
                putExtra(CalendarContract.Events.EVENT_LOCATION, "Taller de Reparación PlayStation")
                putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            }
            context.startActivity(intent)
            Toast.makeText(context, "✅ Abriendo calendario...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir calendario: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 🗺️ Abrir Google Maps con la ubicación del local
     */
    fun abrirUbicacionEnMaps(
        context: Context,
        latitud: Double = -33.4489,
        longitud: Double = -70.6693,
        nombreLugar: String = "Taller Servicio Técnico PlayStation"
    ) {
        try {
            // Crear URI para Google Maps
            val gmmIntentUri = Uri.parse("geo:$latitud,$longitud?q=$latitud,$longitud($nombreLugar)")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                setPackage("com.google.android.apps.maps")
            }

            // Verificar si Google Maps está instalado
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                // Si no está instalado, abrir en navegador
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitud,$longitud")
                )
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir mapas: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 📧 Enviar correo electrónico (Intent)
     */
    fun enviarCorreo(
        context: Context,
        destinatario: String = "soporte@playstationrepair.cl",
        asunto: String,
        cuerpo: String
    ) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(destinatario))
                putExtra(Intent.EXTRA_SUBJECT, asunto)
                putExtra(Intent.EXTRA_TEXT, cuerpo)
            }
            context.startActivity(Intent.createChooser(intent, "Enviar correo usando:"))
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir correo: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 📤 Compartir texto (Intent de compartir)
     */
    fun compartirSolicitud(
        context: Context,
        texto: String
    ) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, texto)
            }
            context.startActivity(Intent.createChooser(intent, "Compartir solicitud usando:"))
        } catch (e: Exception) {
            Toast.makeText(context, "Error al compartir: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 📷 Abrir cámara para tomar foto
     */
    fun abrirCamara(context: Context) {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No se encontró aplicación de cámara", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir cámara: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 📳 Hacer vibrar el dispositivo
     * @param duracionMs Duración en milisegundos (por defecto 200ms)
     */
    @SuppressLint("MissingPermission")
    fun vibrar(context: Context, duracionMs: Long = 200) {
        try {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

            if (vibrator?.hasVibrator() == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Para Android O (API 26) y superior
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            duracionMs,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    // Para versiones anteriores
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(duracionMs)
                }
            }
        } catch (_: Exception) {
            // Silenciosamente falla si no hay vibrador o permisos
        }
    }

    /**
     * 📳 Vibración de éxito (patrón corto-pausa-corto)
     */
    @SuppressLint("MissingPermission")
    fun vibrarExito(context: Context) {
        try {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

            if (vibrator?.hasVibrator() == true && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val pattern = longArrayOf(0, 100, 50, 100)
                val amplitudes = intArrayOf(0, 255, 0, 255)
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1))
            } else if (vibrator != null) {
                @Suppress("DEPRECATION")
                vibrator.vibrate(longArrayOf(0, 100, 50, 100), -1)
            }
        } catch (_: Exception) {
            // Silenciosamente falla
        }
    }

    /**
     * 📳 Vibración de error (patrón largo)
     */
    @SuppressLint("MissingPermission")
    fun vibrarError(context: Context) {
        try {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

            if (vibrator?.hasVibrator() == true && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE))
            } else if (vibrator != null) {
                @Suppress("DEPRECATION")
                vibrator.vibrate(400)
            }
        } catch (_: Exception) {
            // Silenciosamente falla
        }
    }
}

