package appserviciotecnico.network.api

import appserviciotecnico.network.models.SolicitudDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * API Service: Endpoints del microservicio de Solicitudes
 * Consume los endpoints REST del backend Spring Boot
 */
interface SolicitudApi {

    /**
     * GET /api/solicitudes
     * Obtener todas las solicitudes
     */
    @GET("solicitudes")
    suspend fun obtenerSolicitudes(): Response<List<SolicitudDTO>>

    /**
     * GET /api/solicitudes/{id}
     * Obtener una solicitud por ID
     */
    @GET("solicitudes/{id}")
    suspend fun obtenerPorId(@Path("id") id: Long): Response<SolicitudDTO>

    /**
     * POST /api/solicitudes
     * Crear nueva solicitud
     */
    @POST("solicitudes")
    suspend fun crearSolicitud(@Body solicitud: SolicitudDTO): Response<SolicitudDTO>

    /**
     * PUT /api/solicitudes/{id}
     * Actualizar solicitud existente
     */
    @PUT("solicitudes/{id}")
    suspend fun actualizarSolicitud(
        @Path("id") id: Long,
        @Body solicitud: SolicitudDTO
    ): Response<SolicitudDTO>

    /**
     * DELETE /api/solicitudes/{id}
     * Eliminar solicitud
     */
    @DELETE("solicitudes/{id}")
    suspend fun eliminarSolicitud(@Path("id") id: Long): Response<ResponseBody>

    /**
     * GET /api/solicitudes/estado/{estado}
     * Obtener solicitudes por estado
     */
    @GET("solicitudes/estado/{estado}")
    suspend fun obtenerPorEstado(@Path("estado") estado: String): Response<List<SolicitudDTO>>
}

