package appserviciotecnico.network.api

import appserviciotecnico.network.models.external.GameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API Service: Endpoints de la API externa TMDB
 * API de películas/series relacionadas con videojuegos para demostrar consumo de APIs externas
 */
interface ExternalApi {

    /**
     * GET /discover/movie
     * Obtener películas populares relacionadas con videojuegos
     */
    @GET("discover/movie")
    suspend fun obtenerJuegosPopulares(
        @Query("with_keywords") keywords: String = "210024|9715", // videogames, gaming
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1
    ): Response<GameResponse>

    /**
     * GET /search/movie
     * Buscar películas por nombre
     */
    @GET("search/movie")
    suspend fun buscarJuegos(
        @Query("query") query: String
    ): Response<GameResponse>

    /**
     * GET /discover/movie
     * Obtener películas con paginación
     */
    @GET("discover/movie")
    suspend fun obtenerJuegos(
        @Query("with_keywords") keywords: String = "210024",
        @Query("page") page: Int = 1
    ): Response<GameResponse>
}

