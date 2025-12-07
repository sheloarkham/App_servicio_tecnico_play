package appserviciotecnico.network.repository

import appserviciotecnico.network.api.ExternalApi
import appserviciotecnico.network.models.external.Game
import appserviciotecnico.network.models.external.GameResponse
import appserviciotecnico.network.models.external.Genre
import appserviciotecnico.network.utils.NetworkResult
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Response

/**
 * Pruebas unitarias para ExternalRepository
 * Cubre: consumo API externa, fallback a datos mock, manejo de errores
 * Objetivo: ≥ 80% cobertura
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExternalRepositoryTest {

    private lateinit var repository: ExternalRepository
    private lateinit var mockApi: ExternalApi

    // Datos de prueba
    private val genresMock = listOf(Genre(1, "Acción"), Genre(2, "Aventura"))

    private val gamesMock = listOf(
        Game(
            id = 1,
            name = "The Last of Us",
            released = "2023-01-15",
            backgroundImage = "https://image1.jpg",
            rating = 4.8,
            ratingsCount = 1000,
            metacritic = 95,
            platforms = null,
            genres = genresMock
        ),
        Game(
            id = 2,
            name = "God of War",
            released = "2022-03-20",
            backgroundImage = "https://image2.jpg",
            rating = 4.9,
            ratingsCount = 1500,
            metacritic = 98,
            platforms = null,
            genres = genresMock
        )
    )

    private val gameResponseMock = GameResponse(results = gamesMock)

    @Before
    fun setup() {
        mockApi = mockk(relaxed = true)
        repository = ExternalRepository(mockApi)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    // ========== PRUEBAS DE OBTENER JUEGOS POPULARES ==========

    @Test
    fun `obtenerJuegosPopulares - debe retornar Success con lista de juegos`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } returns Response.success(gameResponseMock)

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success)
        assertEquals(2, (result as NetworkResult.Success).data.size)
        assertEquals("The Last of Us", result.data[0].name)

        coVerify { mockApi.obtenerJuegosPopulares() }
    }

    @Test
    fun `obtenerJuegosPopulares - debe usar mock data cuando body es null`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } returns Response.success(null)

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success)
        val data = (result as NetworkResult.Success).data
        assertTrue(data.isNotEmpty()) // Mock data tiene elementos
        assertTrue(data.any { it.name.contains("PlayStation") })
    }

    @Test
    fun `obtenerJuegosPopulares - debe usar mock data cuando falla con 404`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } returns
            Response.error(404, "Not found".toResponseBody())

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback a mock
        val data = (result as NetworkResult.Success).data
        assertTrue(data.isNotEmpty())
    }

    @Test
    fun `obtenerJuegosPopulares - debe usar mock data cuando falla con 500`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } returns
            Response.error(500, "Internal server error".toResponseBody())

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback a mock
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
    }

    @Test
    fun `obtenerJuegosPopulares - debe usar mock data cuando hay excepción de red`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } throws Exception("Network timeout")

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback a mock
        val data = (result as NetworkResult.Success).data
        assertTrue(data.isNotEmpty())
    }

    // ========== PRUEBAS DE BÚSQUEDA ==========

    @Test
    fun `buscarJuegos - debe retornar Success con resultados de búsqueda`() = runTest {
        // Given
        val query = "The Last"
        val searchResult = GameResponse(results = listOf(gamesMock[0]))
        coEvery { mockApi.buscarJuegos(query) } returns Response.success(searchResult)

        // When
        val result = repository.buscarJuegos(query)

        // Then
        assertTrue(result is NetworkResult.Success)
        assertEquals(1, (result as NetworkResult.Success).data.size)
        assertEquals("The Last of Us", result.data[0].name)

        coVerify { mockApi.buscarJuegos(query) }
    }

    @Test
    fun `buscarJuegos - debe usar mock data cuando búsqueda falla`() = runTest {
        // Given
        coEvery { mockApi.buscarJuegos(any()) } returns
            Response.error(500, "Error".toResponseBody())

        // When
        val result = repository.buscarJuegos("test")

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
    }

    @Test
    fun `buscarJuegos - debe manejar búsqueda vacía`() = runTest {
        // Given
        val emptyResponse = GameResponse(results = emptyList())
        coEvery { mockApi.buscarJuegos(any()) } returns Response.success(emptyResponse)

        // When
        val result = repository.buscarJuegos("nonexistent")

        // Then
        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.isEmpty())
    }

    @Test
    fun `buscarJuegos - debe usar mock data cuando body es null`() = runTest {
        // Given
        coEvery { mockApi.buscarJuegos(any()) } returns Response.success(null)

        // When
        val result = repository.buscarJuegos("test")

        // Then
        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
    }

    @Test
    fun `buscarJuegos - debe manejar excepción durante búsqueda`() = runTest {
        // Given
        coEvery { mockApi.buscarJuegos(any()) } throws Exception("Connection error")

        // When
        val result = repository.buscarJuegos("test")

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
    }

    // ========== PRUEBAS DE PAGINACIÓN ==========

    @Test
    fun `obtenerJuegos - debe retornar juegos con paginación página 1`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegos(page = 1) } returns Response.success(gameResponseMock)

        // When
        val result = repository.obtenerJuegos(page = 1)

        // Then
        assertTrue(result is NetworkResult.Success)
        assertEquals(2, (result as NetworkResult.Success).data.size)

        coVerify { mockApi.obtenerJuegos(page = 1) }
    }

    @Test
    fun `obtenerJuegos - debe retornar juegos con paginación página 2`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegos(page = 2) } returns Response.success(gameResponseMock)

        // When
        val result = repository.obtenerJuegos(page = 2)

        // Then
        assertTrue(result is NetworkResult.Success)
        assertEquals(2, (result as NetworkResult.Success).data.size)

        coVerify { mockApi.obtenerJuegos(page = 2) }
    }

    @Test
    fun `obtenerJuegos - debe usar página por defecto (1) si no se especifica`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegos(page = 1) } returns Response.success(gameResponseMock)

        // When
        val result = repository.obtenerJuegos() // sin parámetro

        // Then
        assertTrue(result is NetworkResult.Success)
        coVerify { mockApi.obtenerJuegos(page = 1) }
    }

    @Test
    fun `obtenerJuegos - debe usar mock data cuando paginación falla`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegos(page = any()) } returns
            Response.error(404, "Not found".toResponseBody())

        // When
        val result = repository.obtenerJuegos(page = 1)

        // Then
        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
    }

    @Test
    fun `obtenerJuegos - debe manejar body null en paginación`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegos(page = any()) } returns Response.success(null)

        // When
        val result = repository.obtenerJuegos(page = 1)

        // Then
        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
    }

    @Test
    fun `obtenerJuegos - debe manejar excepción en paginación`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegos(page = any()) } throws Exception("Network error")

        // When
        val result = repository.obtenerJuegos(page = 1)

        // Then
        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
    }

    // ========== PRUEBAS DE DATOS MOCK ==========

    @Test
    fun `mock games - debe tener estructura correcta`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } throws Exception("Force mock")

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success)
        val games = (result as NetworkResult.Success).data

        // Verificar estructura
        assertTrue(games.isNotEmpty())
        games.forEach { game ->
            assertNotNull(game.id)
            assertNotNull(game.name)
            assertNotNull(game.rating)
            assertNotNull(game.genres)
            assertTrue(game.genres?.isNotEmpty() == true)
        }
    }

    @Test
    fun `mock games - debe incluir juegos específicos`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } throws Exception("Force mock")

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success)
        val games = (result as NetworkResult.Success).data

        // Verificar juegos específicos del mock
        assertTrue(games.any { it.name.contains("PlayStation") })
        assertTrue(games.any { it.name.contains("The Last of Us") })
        assertTrue(games.any { it.name.contains("God of War") })
    }

    @Test
    fun `mock games - debe tener al menos 5 juegos`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } throws Exception("Force mock")

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success)
        val games = (result as NetworkResult.Success).data
        assertTrue(games.size >= 5)
    }

    // ========== PRUEBAS DE ERRORES HTTP ==========

    @Test
    fun `debe manejar error 401 Unauthorized`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } returns
            Response.error(401, "Unauthorized".toResponseBody())

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback
    }

    @Test
    fun `debe manejar error 403 Forbidden`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } returns
            Response.error(403, "Forbidden".toResponseBody())

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback
    }

    @Test
    fun `debe manejar error 503 Service Unavailable`() = runTest {
        // Given
        coEvery { mockApi.buscarJuegos(any()) } returns
            Response.error(503, "Service unavailable".toResponseBody())

        // When
        val result = repository.buscarJuegos("test")

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback
    }

    // ========== PRUEBAS DE TIMEOUT Y CONECTIVIDAD ==========

    @Test
    fun `debe manejar timeout de conexión`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } throws
            Exception("Timeout: failed to connect")

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback
    }

    @Test
    fun `debe manejar IOException - sin internet`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } throws
            java.io.IOException("Unable to resolve host")

        // When
        val result = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result is NetworkResult.Success) // Fallback
    }

    // ========== PRUEBAS DE CASOS EXTREMOS ==========

    @Test
    fun `debe manejar múltiples llamadas consecutivas`() = runTest {
        // Given
        coEvery { mockApi.obtenerJuegosPopulares() } returns Response.success(gameResponseMock)

        // When - llamadas consecutivas
        val result1 = repository.obtenerJuegosPopulares()
        val result2 = repository.obtenerJuegosPopulares()
        val result3 = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result1 is NetworkResult.Success)
        assertTrue(result2 is NetworkResult.Success)
        assertTrue(result3 is NetworkResult.Success)

        coVerify(exactly = 3) { mockApi.obtenerJuegosPopulares() }
    }

    @Test
    fun `debe manejar cambios entre éxito y error`() = runTest {
        // Given - primera llamada exitosa
        coEvery { mockApi.obtenerJuegosPopulares() } returns Response.success(gameResponseMock)
        val result1 = repository.obtenerJuegosPopulares()

        // When - segunda llamada con error
        coEvery { mockApi.obtenerJuegosPopulares() } throws Exception("Error")
        val result2 = repository.obtenerJuegosPopulares()

        // Then
        assertTrue(result1 is NetworkResult.Success)
        assertTrue(result2 is NetworkResult.Success) // Fallback
    }
}

