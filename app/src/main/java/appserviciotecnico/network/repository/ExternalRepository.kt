package appserviciotecnico.network.repository

import android.util.Log
import appserviciotecnico.network.api.ExternalApi
import appserviciotecnico.network.models.external.Game
import appserviciotecnico.network.models.external.Genre
import appserviciotecnico.network.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio para manejar el consumo de la API externa (TMDB - The Movie Database)
 * Encapsula la lógica de red y manejo de errores
 */
class ExternalRepository(private val api: ExternalApi) {

    private val TAG = "ExternalRepository"

    /**
     * Obtener lista de juegos populares
     */
    suspend fun obtenerJuegosPopulares(): NetworkResult<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Intentando obtener juegos populares desde TMDB...")
                val response = api.obtenerJuegosPopulares()

                Log.d(TAG, "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Log.d(TAG, "Éxito: ${body.results.size} juegos obtenidos")
                        NetworkResult.Success(body.results)
                    } else {
                        Log.w(TAG, "Body es null, usando datos mock")
                        NetworkResult.Success(getMockGames())
                    }
                } else {
                    val errorMsg = "Error ${response.code()}: ${response.message()}"
                    Log.e(TAG, errorMsg)
                    // Usar datos mock como fallback
                    NetworkResult.Success(getMockGames())
                }
            } catch (e: Exception) {
                val errorMsg = "Error de conexión: ${e.localizedMessage ?: "Desconocido"}"
                Log.e(TAG, errorMsg, e)
                // Usar datos mock como fallback
                NetworkResult.Success(getMockGames())
            }
        }
    }

    /**
     * Buscar juegos por nombre
     */
    suspend fun buscarJuegos(query: String): NetworkResult<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Buscando juegos con query: $query")
                val response = api.buscarJuegos(query)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Log.d(TAG, "Búsqueda exitosa: ${body.results.size} resultados")
                        NetworkResult.Success(body.results)
                    } else {
                        NetworkResult.Success(getMockGames())
                    }
                } else {
                    Log.e(TAG, "Error en búsqueda: ${response.code()}")
                    NetworkResult.Success(getMockGames())
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción en búsqueda", e)
                NetworkResult.Success(getMockGames())
            }
        }
    }

    /**
     * Obtener lista de juegos con paginación
     */
    suspend fun obtenerJuegos(page: Int = 1): NetworkResult<List<Game>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Obteniendo juegos, página: $page")
                val response = api.obtenerJuegos(page = page)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Log.d(TAG, "Página $page: ${body.results.size} resultados")
                        NetworkResult.Success(body.results)
                    } else {
                        Log.w(TAG, "Body es null en paginación")
                        NetworkResult.Success(getMockGames())
                    }
                } else {
                    Log.e(TAG, "Error en paginación: ${response.code()}")
                    NetworkResult.Success(getMockGames())
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción en paginación", e)
                NetworkResult.Success(getMockGames())
            }
        }
    }

    /**
     * Datos mock para cuando la API externa falle
     * Útil para desarrollo y demostración
     */
    private fun getMockGames(): List<Game> {
        return listOf(
            Game(
                id = 1,
                name = "PlayStation 5",
                released = "2020-11-12",
                backgroundImage = null,
                rating = 4.8,
                ratingsCount = 15000,
                metacritic = 95,
                platforms = null,
                genres = listOf(Genre(1, "Consola"), Genre(2, "Gaming"))
            ),
            Game(
                id = 2,
                name = "The Last of Us",
                released = "2023-01-15",
                backgroundImage = null,
                rating = 4.7,
                ratingsCount = 8500,
                metacritic = 88,
                platforms = null,
                genres = listOf(Genre(1, "Serie"), Genre(2, "Aventura"))
            ),
            Game(
                id = 3,
                name = "Uncharted",
                released = "2022-02-18",
                backgroundImage = null,
                rating = 4.4,
                ratingsCount = 6200,
                metacritic = 82,
                platforms = null,
                genres = listOf(Genre(1, "Película"), Genre(2, "Aventura"))
            ),
            Game(
                id = 4,
                name = "God of War",
                released = "2022-03-25",
                backgroundImage = null,
                rating = 4.9,
                ratingsCount = 12000,
                metacritic = 92,
                platforms = null,
                genres = listOf(Genre(1, "Juego"), Genre(2, "Acción"))
            ),
            Game(
                id = 5,
                name = "Spider-Man",
                released = "2021-12-15",
                backgroundImage = null,
                rating = 4.6,
                ratingsCount = 9500,
                metacritic = 87,
                platforms = null,
                genres = listOf(Genre(1, "Película"), Genre(2, "Acción"))
            ),
            Game(
                id = 6,
                name = "Horizon Zero Dawn",
                released = "2022-08-20",
                backgroundImage = null,
                rating = 4.5,
                ratingsCount = 7800,
                metacritic = 85,
                platforms = null,
                genres = listOf(Genre(1, "Juego"), Genre(2, "RPG"))
            )
        )
    }
}

