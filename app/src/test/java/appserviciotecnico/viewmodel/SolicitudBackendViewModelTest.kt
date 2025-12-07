package appserviciotecnico.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import appserviciotecnico.network.models.SolicitudDTO
import appserviciotecnico.network.repository.SolicitudRemoteRepository
import appserviciotecnico.network.utils.NetworkResult
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para SolicitudBackendViewModel
 * Cubre: CRUD, estados de carga, manejo de errores
 * Objetivo: ≥ 80% cobertura
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SolicitudBackendViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SolicitudBackendViewModel
    private lateinit var repository: SolicitudRemoteRepository
    private val testDispatcher = StandardTestDispatcher()

    // Datos de prueba
    private val solicitudMock = SolicitudDTO(
        id = 1L,
        servicio = "Reparación PS5",
        fechaAgendada = "2024-01-15",
        horaAgendada = "10:00",
        estado = "PENDIENTE",
        clienteNombre = "Juan Pérez",
        descripcion = "Consola no enciende",
        categoriaId = 1,
        fechaCreacion = "2024-01-10"
    )

    private val listaSolicitudes = listOf(
        solicitudMock,
        solicitudMock.copy(id = 2L, clienteNombre = "María López")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Crear mock del repository
        repository = mockk(relaxed = true)

        // Crear ViewModel con el repository mockeado
        viewModel = SolicitudBackendViewModel().apply {
            // Inyectar el repository mockeado usando reflection
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

    // ========== PRUEBAS DE OBTENER SOLICITUDES ==========

    @Test
    fun `obtenerSolicitudes - debe emitir Loading y Success con datos`() = runTest {
        // Given
        coEvery { repository.obtenerSolicitudes() } returns Result.success(listaSolicitudes)

        // When
        viewModel.obtenerSolicitudes()
        advanceUntilIdle()

        // Then
        val state = viewModel.solicitudesState.value
        assertTrue(state is NetworkResult.Success)
        assertEquals(2, (state as NetworkResult.Success).data.size)

        coVerify { repository.obtenerSolicitudes() }
    }

    @Test
    fun `obtenerSolicitudes - debe emitir Error cuando falla`() = runTest {
        // Given
        val errorMsg = "Error de conexión"
        coEvery { repository.obtenerSolicitudes() } returns Result.failure(Exception(errorMsg))

        // When
        viewModel.obtenerSolicitudes()
        advanceUntilIdle()

        // Then
        val state = viewModel.solicitudesState.value
        assertTrue(state is NetworkResult.Error)
        assertEquals(errorMsg, (state as NetworkResult.Error).message)
    }

    @Test
    fun `obtenerSolicitudes - debe manejar lista vacía`() = runTest {
        // Given
        coEvery { repository.obtenerSolicitudes() } returns Result.success(emptyList())

        // When
        viewModel.obtenerSolicitudes()
        advanceUntilIdle()

        // Then
        val state = viewModel.solicitudesState.value
        assertTrue(state is NetworkResult.Success)
        assertTrue((state as NetworkResult.Success).data.isEmpty())
    }

    // ========== PRUEBAS DE CREAR SOLICITUD ==========

    @Test
    fun `crearSolicitud - debe emitir Success y recargar lista`() = runTest {
        // Given
        coEvery { repository.crearSolicitud(any()) } returns Result.success(solicitudMock)
        coEvery { repository.obtenerSolicitudes() } returns Result.success(listaSolicitudes)

        // When
        viewModel.crearSolicitud(solicitudMock)
        advanceUntilIdle()

        // Then
        val createState = viewModel.crearSolicitudState.value
        assertTrue(createState is NetworkResult.Success)
        assertEquals(solicitudMock.id, (createState as NetworkResult.Success).data.id)

        // Verificar que se recargó la lista
        coVerify { repository.crearSolicitud(any()) }
        coVerify { repository.obtenerSolicitudes() }
    }

    @Test
    fun `crearSolicitud - debe emitir Error cuando falla`() = runTest {
        // Given
        val errorMsg = "Error al crear solicitud"
        coEvery { repository.crearSolicitud(any()) } returns Result.failure(Exception(errorMsg))

        // When
        viewModel.crearSolicitud(solicitudMock)
        advanceUntilIdle()

        // Then
        val state = viewModel.crearSolicitudState.value
        assertTrue(state is NetworkResult.Error)
        assertTrue((state as NetworkResult.Error).message.contains("Error al crear"))

        // No debe recargar la lista si falla
        coVerify(exactly = 0) { repository.obtenerSolicitudes() }
    }

    // ========== PRUEBAS DE ACTUALIZAR SOLICITUD ==========

    @Test
    fun `actualizarSolicitud - debe emitir Success y recargar lista`() = runTest {
        // Given
        val solicitudActualizada = solicitudMock.copy(estado = "EN_PROCESO")
        coEvery { repository.actualizarSolicitud(1L, any()) } returns Result.success(solicitudActualizada)
        coEvery { repository.obtenerSolicitudes() } returns Result.success(listaSolicitudes)

        // When
        viewModel.actualizarSolicitud(1L, solicitudActualizada)
        advanceUntilIdle()

        // Then
        val state = viewModel.actualizarState.value
        assertTrue(state is NetworkResult.Success)
        assertEquals("EN_PROCESO", (state as NetworkResult.Success).data.estado)

        coVerify { repository.actualizarSolicitud(1L, any()) }
        coVerify { repository.obtenerSolicitudes() }
    }

    @Test
    fun `actualizarSolicitud - debe manejar error de ID inválido`() = runTest {
        // Given
        coEvery { repository.actualizarSolicitud(999L, any()) } returns
            Result.failure(Exception("ID no encontrado"))

        // When
        viewModel.actualizarSolicitud(999L, solicitudMock)
        advanceUntilIdle()

        // Then
        val state = viewModel.actualizarState.value
        assertTrue(state is NetworkResult.Error)

        coVerify(exactly = 0) { repository.obtenerSolicitudes() }
    }

    // ========== PRUEBAS DE ELIMINAR SOLICITUD ==========

    @Test
    fun `eliminarSolicitud - debe emitir Success y recargar lista`() = runTest {
        // Given
        coEvery { repository.eliminarSolicitud(1L) } returns Result.success(Unit)
        coEvery { repository.obtenerSolicitudes() } returns Result.success(listaSolicitudes)

        // When
        viewModel.eliminarSolicitud(1L)
        advanceUntilIdle()

        // Then
        val state = viewModel.eliminarState.value
        assertTrue(state is NetworkResult.Success)

        coVerify { repository.eliminarSolicitud(1L) }
        coVerify { repository.obtenerSolicitudes() }
    }

    @Test
    fun `eliminarSolicitud - debe emitir Error cuando falla`() = runTest {
        // Given
        coEvery { repository.eliminarSolicitud(1L) } returns
            Result.failure(Exception("No se puede eliminar"))

        // When
        viewModel.eliminarSolicitud(1L)
        advanceUntilIdle()

        // Then
        val state = viewModel.eliminarState.value
        assertTrue(state is NetworkResult.Error)

        coVerify(exactly = 0) { repository.obtenerSolicitudes() }
    }

    // ========== PRUEBAS DE OBTENER POR ESTADO ==========

    @Test
    fun `obtenerPorEstado - debe filtrar por estado PENDIENTE`() = runTest {
        // Given
        val pendientes = listOf(solicitudMock)
        coEvery { repository.obtenerPorEstado("PENDIENTE") } returns Result.success(pendientes)

        // When
        viewModel.obtenerPorEstado("PENDIENTE")
        advanceUntilIdle()

        // Then
        val state = viewModel.solicitudesState.value
        assertTrue(state is NetworkResult.Success)
        assertEquals(1, (state as NetworkResult.Success).data.size)
        assertEquals("PENDIENTE", state.data[0].estado)
    }

    @Test
    fun `obtenerPorEstado - debe manejar estado sin resultados`() = runTest {
        // Given
        coEvery { repository.obtenerPorEstado("COMPLETADO") } returns Result.success(emptyList())

        // When
        viewModel.obtenerPorEstado("COMPLETADO")
        advanceUntilIdle()

        // Then
        val state = viewModel.solicitudesState.value
        assertTrue(state is NetworkResult.Success)
        assertTrue((state as NetworkResult.Success).data.isEmpty())
    }

    // ========== PRUEBAS DE RESET DE ESTADOS ==========

    @Test
    fun `resetCrearState - debe resetear a Idle`() = runTest {
        // Given - crear solicitud primero
        coEvery { repository.crearSolicitud(any()) } returns Result.success(solicitudMock)
        coEvery { repository.obtenerSolicitudes() } returns Result.success(listaSolicitudes)
        viewModel.crearSolicitud(solicitudMock)
        advanceUntilIdle()

        // When
        viewModel.resetCrearState()

        // Then
        val state = viewModel.crearSolicitudState.value
        assertTrue(state is NetworkResult.Idle)
    }

    @Test
    fun `resetActualizarState - debe resetear a Idle`() = runTest {
        // When
        viewModel.resetActualizarState()

        // Then
        val state = viewModel.actualizarState.value
        assertTrue(state is NetworkResult.Idle)
    }

    @Test
    fun `resetEliminarState - debe resetear a Idle`() = runTest {
        // When
        viewModel.resetEliminarState()

        // Then
        val state = viewModel.eliminarState.value
        assertTrue(state is NetworkResult.Idle)
    }

    // ========== PRUEBAS DE ESTADOS INICIALES ==========

    @Test
    fun `estados iniciales deben ser Idle`() {
        // Then
        assertTrue(viewModel.solicitudesState.value is NetworkResult.Idle)
        assertTrue(viewModel.crearSolicitudState.value is NetworkResult.Idle)
        assertTrue(viewModel.actualizarState.value is NetworkResult.Idle)
        assertTrue(viewModel.eliminarState.value is NetworkResult.Idle)
    }

    // ========== PRUEBAS DE MÚLTIPLES OPERACIONES ==========

    @Test
    fun `debe manejar múltiples operaciones secuenciales correctamente`() = runTest {
        // Given
        coEvery { repository.crearSolicitud(any()) } returns Result.success(solicitudMock)
        coEvery { repository.actualizarSolicitud(1L, any()) } returns Result.success(solicitudMock)
        coEvery { repository.obtenerSolicitudes() } returns Result.success(listaSolicitudes)

        // When - Crear y luego actualizar
        viewModel.crearSolicitud(solicitudMock)
        advanceUntilIdle()

        viewModel.actualizarSolicitud(1L, solicitudMock)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.crearSolicitudState.value is NetworkResult.Success)
        assertTrue(viewModel.actualizarState.value is NetworkResult.Success)

        // Debe haber recargado la lista 2 veces
        coVerify(exactly = 2) { repository.obtenerSolicitudes() }
    }
}

