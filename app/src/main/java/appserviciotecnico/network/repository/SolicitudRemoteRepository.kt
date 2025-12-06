package appserviciotecnico.network.repository

import android.util.Log
import appserviciotecnico.network.config.RetrofitClient
import appserviciotecnico.network.models.SolicitudDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository: Maneja la comunicación con el backend para Solicitudes
 * Implementa el patrón Repository para separar la lógica de red de la UI
 */
class SolicitudRemoteRepository {

    private val api = RetrofitClient.solicitudApi

    /**
     * Obtener todas las solicitudes del backend
     */
    suspend fun obtenerSolicitudes(): Result<List<SolicitudDTO>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.obtenerSolicitudes()

                if (response.isSuccessful) {
                    val solicitudes = response.body() ?: emptyList()
                    Result.success(solicitudes)
                } else {
                    Result.failure(Exception("Error del servidor: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("SolicitudRemoteRepo", "Error al obtener solicitudes: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Obtener una solicitud por ID
     */
    suspend fun obtenerPorId(id: Long): Result<SolicitudDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.obtenerPorId(id)

                if (response.isSuccessful) {
                    val solicitud = response.body()
                    if (solicitud != null) {
                        Result.success(solicitud)
                    } else {
                        Result.failure(Exception("Solicitud no encontrada"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("SolicitudRemoteRepo", "Error al obtener solicitud: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Crear nueva solicitud en el backend
     */
    suspend fun crearSolicitud(solicitud: SolicitudDTO): Result<SolicitudDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.crearSolicitud(solicitud)

                if (response.isSuccessful) {
                    val solicitudCreada = response.body()
                    if (solicitudCreada != null) {
                        Result.success(solicitudCreada)
                    } else {
                        Result.failure(Exception("Respuesta vacía del servidor"))
                    }
                } else {
                    Result.failure(Exception("Error al crear: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("SolicitudRemoteRepo", "Error al crear solicitud: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Actualizar solicitud existente
     */
    suspend fun actualizarSolicitud(id: Long, solicitud: SolicitudDTO): Result<SolicitudDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.actualizarSolicitud(id, solicitud)

                if (response.isSuccessful) {
                    val actualizada = response.body()
                    if (actualizada != null) {
                        Result.success(actualizada)
                    } else {
                        Result.failure(Exception("Respuesta vacía"))
                    }
                } else {
                    Result.failure(Exception("Error al actualizar: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("SolicitudRemoteRepo", "Error al actualizar: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Eliminar solicitud
     */
    suspend fun eliminarSolicitud(id: Long): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.eliminarSolicitud(id)

                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Error al eliminar: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("SolicitudRemoteRepo", "Error al eliminar: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Obtener solicitudes por estado
     */
    suspend fun obtenerPorEstado(estado: String): Result<List<SolicitudDTO>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.obtenerPorEstado(estado)

                if (response.isSuccessful) {
                    val solicitudes = response.body() ?: emptyList()
                    Result.success(solicitudes)
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("SolicitudRemoteRepo", "Error al obtener por estado: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}

