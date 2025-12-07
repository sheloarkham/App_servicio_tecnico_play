package appserviciotecnico.network.models.external

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para la respuesta de la API externa (TMDB - Películas/Series de videojuegos)
 * Representa contenido de entretenimiento relacionado con gaming
 */
data class GameResponse(
    @SerializedName("page")
    val page: Int = 1,

    @SerializedName("results")
    val results: List<Game>,

    @SerializedName("total_pages")
    val totalPages: Int = 1,

    @SerializedName("total_results")
    val totalResults: Int = 0
)

/**
 * Modelo de un juego/película/serie individual
 */
data class Game(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title", alternate = ["name"])
    val name: String,

    @SerializedName("release_date", alternate = ["first_air_date"])
    val released: String? = null,

    @SerializedName("poster_path")
    val backgroundImage: String? = null,

    @SerializedName("vote_average")
    val rating: Double = 0.0,

    @SerializedName("vote_count")
    val ratingsCount: Int = 0,

    @SerializedName("popularity")
    val metacritic: Int? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerializedName("overview")
    val overview: String? = null,

    // Campos adicionales
    val platforms: List<PlatformInfo>? = null,
    val genres: List<Genre>? = null
) {
    // Método helper para obtener la URL completa de la imagen
    fun getFullImageUrl(): String? {
        return backgroundImage?.let { "https://image.tmdb.org/t/p/w500$it" }
    }
}

/**
 * Información de plataforma (para compatibilidad)
 */
data class PlatformInfo(
    @SerializedName("platform")
    val platform: Platform
)

data class Platform(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)

/**
 * Género del juego
 */
data class Genre(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)

