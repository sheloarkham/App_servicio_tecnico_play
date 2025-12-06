package appserviciotecnico.network.api

import appserviciotecnico.network.models.CotizacionDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * API Service: Endpoints del microservicio de Cotizaciones
 */
interface CotizacionApi {

    @GET("cotizaciones")
    suspend fun obtenerCotizaciones(): Response<List<CotizacionDTO>>

    @GET("cotizaciones/{id}")
    suspend fun obtenerPorId(@Path("id") id: Long): Response<CotizacionDTO>

    @POST("cotizaciones")
    suspend fun crearCotizacion(@Body cotizacion: CotizacionDTO): Response<CotizacionDTO>

    @PUT("cotizaciones/{id}")
    suspend fun actualizarCotizacion(
        @Path("id") id: Long,
        @Body cotizacion: CotizacionDTO
    ): Response<CotizacionDTO>

    @DELETE("cotizaciones/{id}")
    suspend fun eliminarCotizacion(@Path("id") id: Long): Response<ResponseBody>

    @GET("cotizaciones/estado/{estado}")
    suspend fun obtenerPorEstado(@Path("estado") estado: String): Response<List<CotizacionDTO>>
}

