package appserviciotecnico.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import appserviciotecnico.network.models.external.Game
import appserviciotecnico.network.models.external.Genre
import appserviciotecnico.network.repository.ExternalRepository
import appserviciotecnico.network.utils.NetworkResult
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para ExternalViewModel
 * Cubre: carga de juegos, búsqueda, manejo de errores, estados de UI
 * Objetivo: ≥ 80% cobertura
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExternalViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ExternalViewModel
    private lateinit var repository: ExternalRepository
    private val testDispatcher = StandardTestDispatcher()

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

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk(relaxed = true)

        // Mock del init() que carga juegos populares
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        viewModel = ExternalViewModel().apply {
            val repoField = this::class.java.getDeclaredField("repository")
            repoField.isAccessible = true
            repoField.set(this, repository)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    // ========== PRUEBAS DE CARGA INICIAL ==========

    @Test
    fun `init - debe cargar juegos populares al iniciar`() = runTest {
        // When (init se ejecuta automáticamente)
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        val juegos = viewModel.juegos.value

        assertTrue(uiState is ExternalUiState.Success)
        assertEquals(2, juegos.size)
        assertEquals("The Last of Us", juegos[0].name)

        coVerify(atLeast = 1) { repository.obtenerJuegosPopulares() }
    }

    @Test
    fun `cargarJuegosPopulares - debe emitir Loading y luego Success`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        // When
        viewModel.cargarJuegosPopulares()

        // Verificar estado Loading
        assertEquals(ExternalUiState.Loading, viewModel.uiState.value)

        advanceUntilIdle()

        // Then - Verificar estado Success
        assertTrue(viewModel.uiState.value is ExternalUiState.Success)
        assertEquals(2, viewModel.juegos.value.size)
    }

    @Test
    fun `cargarJuegosPopulares - debe manejar Error de red`() = runTest {
        // Given
        val errorMsg = "Error de conexión"
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Error(errorMsg)

        // When
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertTrue(uiState is ExternalUiState.Error)
        assertEquals(errorMsg, (uiState as ExternalUiState.Error).message)
    }

    @Test
    fun `cargarJuegosPopulares - debe manejar lista vacía`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(emptyList())

        // When
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is ExternalUiState.Success)
        assertTrue(viewModel.juegos.value.isEmpty())
    }

    // ========== PRUEBAS DE BÚSQUEDA ==========

    @Test
    fun `buscarJuegos - debe buscar y retornar resultados`() = runTest {
        // Given
        val query = "The Last"
        val resultados = listOf(gamesMock[0])
        coEvery { repository.buscarJuegos(query) } returns NetworkResult.Success(resultados)

        // When
        viewModel.buscarJuegos(query)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is ExternalUiState.Success)
        assertEquals(1, viewModel.juegos.value.size)
        assertEquals("The Last of Us", viewModel.juegos.value[0].name)

        coVerify { repository.buscarJuegos(query) }
    }

    @Test
    fun `buscarJuegos - debe manejar búsqueda sin resultados`() = runTest {
        // Given
        coEvery { repository.buscarJuegos("NoExiste") } returns NetworkResult.Success(emptyList())

        // When
        viewModel.buscarJuegos("NoExiste")
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is ExternalUiState.Success)
        assertTrue(viewModel.juegos.value.isEmpty())
    }

    @Test
    fun `buscarJuegos - debe cargar populares si query está vacío`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        // When - búsqueda con string vacío
        viewModel.buscarJuegos("")
        advanceUntilIdle()

        // Then - debe llamar a obtenerJuegosPopulares
        coVerify { repository.obtenerJuegosPopulares() }
        assertEquals(2, viewModel.juegos.value.size)
    }

    @Test
    fun `buscarJuegos - debe cargar populares si query es solo espacios`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        // When
        viewModel.buscarJuegos("   ")
        advanceUntilIdle()

        // Then
        coVerify { repository.obtenerJuegosPopulares() }
    }

    @Test
    fun `buscarJuegos - debe emitir Loading antes de buscar`() = runTest {
        // Given
        coEvery { repository.buscarJuegos(any()) } returns NetworkResult.Success(gamesMock)

        // When
        viewModel.buscarJuegos("test")

        // Then - inmediatamente debe estar Loading
        assertEquals(ExternalUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `buscarJuegos - debe manejar error durante búsqueda`() = runTest {
        // Given
        val errorMsg = "Error al buscar"
        coEvery { repository.buscarJuegos(any()) } returns NetworkResult.Error(errorMsg)

        // When
        viewModel.buscarJuegos("test")
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertTrue(uiState is ExternalUiState.Error)
        assertEquals(errorMsg, (uiState as ExternalUiState.Error).message)
    }

    // ========== PRUEBAS DE RECARGAR ==========

    @Test
    fun `recargar - debe llamar a cargarJuegosPopulares`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        // When
        viewModel.recargar()
        advanceUntilIdle()

        // Then
        coVerify(atLeast = 1) { repository.obtenerJuegosPopulares() }
        assertTrue(viewModel.uiState.value is ExternalUiState.Success)
    }

    @Test
    fun `recargar - debe actualizar estado después de error`() = runTest {
        // Given - primero un error
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Error("Error")
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is ExternalUiState.Error)

        // When - recargar con éxito
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)
        viewModel.recargar()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is ExternalUiState.Success)
        assertEquals(2, viewModel.juegos.value.size)
    }

    // ========== PRUEBAS DE ESTADOS REACTIVOS ==========

    @Test
    fun `estados deben ser reactivos - múltiples actualizaciones`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        // When - cargar varias veces
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        val primeraCarga = viewModel.juegos.value.size

        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        val segundaCarga = viewModel.juegos.value.size

        // Then - debe mantener coherencia
        assertEquals(primeraCarga, segundaCarga)
        assertEquals(2, segundaCarga)
    }

    // ========== PRUEBAS DE MANEJO DE NetworkResult.Idle ==========

    @Test
    fun `debe ignorar NetworkResult_Idle sin cambiar estado`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Idle

        // When
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then - el estado Loading debe permanecer (no cambiar a Idle)
        // O dependiendo de la implementación, no hacer nada
        assertTrue(viewModel.uiState.value is ExternalUiState.Loading)
    }

    @Test
    fun `debe ignorar NetworkResult_Loading sin afectar UI`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Loading

        // When
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then
        assertEquals(ExternalUiState.Loading, viewModel.uiState.value)
    }

    // ========== PRUEBAS DE DATOS ==========

    @Test
    fun `juegos deben tener estructura correcta`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        // When
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then
        val juego = viewModel.juegos.value[0]
        assertNotNull(juego.id)
        assertNotNull(juego.name)
        assertNotNull(juego.rating)
        assertTrue(juego.genres?.isNotEmpty() == true)
    }

    @Test
    fun `debe mantener orden de juegos del repository`() = runTest {
        // Given
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Success(gamesMock)

        // When
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then
        val juegos = viewModel.juegos.value
        assertEquals("The Last of Us", juegos[0].name)
        assertEquals("God of War", juegos[1].name)
    }

    // ========== PRUEBAS DE CASOS EXTREMOS ==========

    @Test
    fun `debe manejar cambios rápidos de búsqueda`() = runTest {
        // Given
        coEvery { repository.buscarJuegos(any()) } returns NetworkResult.Success(gamesMock)

        // When - búsquedas rápidas consecutivas
        viewModel.buscarJuegos("The")
        viewModel.buscarJuegos("God")
        viewModel.buscarJuegos("Last")
        advanceUntilIdle()

        // Then - debe completar sin errores
        assertTrue(viewModel.uiState.value is ExternalUiState.Success)
        coVerify(atLeast = 3) { repository.buscarJuegos(any()) }
    }

    @Test
    fun `debe manejar timeout de red`() = runTest {
        // Given
        val timeoutError = "Timeout: no se pudo conectar"
        coEvery { repository.obtenerJuegosPopulares() } returns NetworkResult.Error(timeoutError)

        // When
        viewModel.cargarJuegosPopulares()
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is ExternalUiState.Error)
        assertTrue((state as ExternalUiState.Error).message.contains("Timeout"))
    }
}

