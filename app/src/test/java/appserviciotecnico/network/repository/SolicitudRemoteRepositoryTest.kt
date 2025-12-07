package appserviciotecnico.network.repository

import appserviciotecnico.network.api.SolicitudApi
import appserviciotecnico.network.models.SolicitudDTO
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
 * Pruebas unitarias para SolicitudRemoteRepository
 * Cubre: consumo de API, manejo de respuestas exitosas y errores
 * Objetivo: ≥ 80% cobertura
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SolicitudRemoteRepositoryTest {

    private lateinit var repository: SolicitudRemoteRepository
    private lateinit var mockApi: SolicitudApi

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
        mockApi = mockk(relaxed = true)

        // Crear repository con API mockeada
        repository = SolicitudRemoteRepository().apply {
            val apiField = this::class.java.getDeclaredField("api")
            apiField.isAccessible = true
            apiField.set(this, mockApi)
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    // ========== PRUEBAS DE OBTENER SOLICITUDES ==========

    @Test
    fun `obtenerSolicitudes - debe retornar Success con lista de solicitudes`() = runTest {
        // Given
        coEvery { mockApi.obtenerSolicitudes() } returns Response.success(listaSolicitudes)

        // When
        val result = repository.obtenerSolicitudes()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        coVerify { mockApi.obtenerSolicitudes() }
    }

    @Test
    fun `obtenerSolicitudes - debe retornar lista vacía cuando no hay datos`() = runTest {
        // Given
        coEvery { mockApi.obtenerSolicitudes() } returns Response.success(emptyList())

        // When
        val result = repository.obtenerSolicitudes()

        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `obtenerSolicitudes - debe manejar error 404`() = runTest {
        // Given
        coEvery { mockApi.obtenerSolicitudes() } returns
            Response.error(404, "Not found".toResponseBody())

        // When
        val result = repository.obtenerSolicitudes()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("404") == true)
    }

    @Test
    fun `obtenerSolicitudes - debe manejar error 500`() = runTest {
        // Given
        coEvery { mockApi.obtenerSolicitudes() } returns
            Response.error(500, "Internal server error".toResponseBody())

        // When
        val result = repository.obtenerSolicitudes()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("500") == true)
    }

    @Test
    fun `obtenerSolicitudes - debe manejar excepción de red`() = runTest {
        // Given
        coEvery { mockApi.obtenerSolicitudes() } throws Exception("Network timeout")

        // When
        val result = repository.obtenerSolicitudes()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network timeout", result.exceptionOrNull()?.message)
    }

    // ========== PRUEBAS DE OBTENER POR ID ==========

    @Test
    fun `obtenerPorId - debe retornar Success con solicitud`() = runTest {
        // Given
        coEvery { mockApi.obtenerPorId(1L) } returns Response.success(solicitudMock)

        // When
        val result = repository.obtenerPorId(1L)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1L, result.getOrNull()?.id)
        assertEquals("Juan Pérez", result.getOrNull()?.clienteNombre)
    }

    @Test
    fun `obtenerPorId - debe manejar solicitud no encontrada`() = runTest {
        // Given
        coEvery { mockApi.obtenerPorId(999L) } returns
            Response.error(404, "Not found".toResponseBody())

        // When
        val result = repository.obtenerPorId(999L)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("404") == true)
    }

    @Test
    fun `obtenerPorId - debe manejar body null`() = runTest {
        // Given
        coEvery { mockApi.obtenerPorId(1L) } returns Response.success(null)

        // When
        val result = repository.obtenerPorId(1L)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("no encontrada") == true)
    }

    // ========== PRUEBAS DE CREAR SOLICITUD ==========

    @Test
    fun `crearSolicitud - debe retornar Success con solicitud creada`() = runTest {
        // Given
        coEvery { mockApi.crearSolicitud(any()) } returns Response.success(solicitudMock)

        // When
        val result = repository.crearSolicitud(solicitudMock)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1L, result.getOrNull()?.id)
        coVerify { mockApi.crearSolicitud(any()) }
    }

    @Test
    fun `crearSolicitud - debe manejar error de validación (400)`() = runTest {
        // Given
        coEvery { mockApi.crearSolicitud(any()) } returns
            Response.error(400, "Bad request".toResponseBody())

        // When
        val result = repository.crearSolicitud(solicitudMock)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("400") == true)
    }

    @Test
    fun `crearSolicitud - debe manejar respuesta vacía del servidor`() = runTest {
        // Given
        coEvery { mockApi.crearSolicitud(any()) } returns Response.success(null)

        // When
        val result = repository.crearSolicitud(solicitudMock)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("vacía") == true)
    }

    @Test
    fun `crearSolicitud - debe manejar excepción durante creación`() = runTest {
        // Given
        coEvery { mockApi.crearSolicitud(any()) } throws Exception("Connection refused")

        // When
        val result = repository.crearSolicitud(solicitudMock)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Connection refused", result.exceptionOrNull()?.message)
    }

    // ========== PRUEBAS DE ACTUALIZAR SOLICITUD ==========

    @Test
    fun `actualizarSolicitud - debe retornar Success con solicitud actualizada`() = runTest {
        // Given
        val actualizada = solicitudMock.copy(estado = "EN_PROCESO")
        coEvery { mockApi.actualizarSolicitud(1L, any()) } returns Response.success(actualizada)

        // When
        val result = repository.actualizarSolicitud(1L, actualizada)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("EN_PROCESO", result.getOrNull()?.estado)
    }

    @Test
    fun `actualizarSolicitud - debe manejar ID no existente`() = runTest {
        // Given
        coEvery { mockApi.actualizarSolicitud(999L, any()) } returns
            Response.error(404, "Not found".toResponseBody())

        // When
        val result = repository.actualizarSolicitud(999L, solicitudMock)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `actualizarSolicitud - debe manejar conflicto (409)`() = runTest {
        // Given
        coEvery { mockApi.actualizarSolicitud(1L, any()) } returns
            Response.error(409, "Conflict".toResponseBody())

        // When
        val result = repository.actualizarSolicitud(1L, solicitudMock)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("409") == true)
    }

    // ========== PRUEBAS DE ELIMINAR SOLICITUD ==========

    @Test
    fun `eliminarSolicitud - debe retornar Success`() = runTest {
        // Given
        val emptyBody = "".toResponseBody()
        coEvery { mockApi.eliminarSolicitud(1L) } returns Response.success(emptyBody)

        // When
        val result = repository.eliminarSolicitud(1L)

        // Then
        assertTrue(result.isSuccess)
        coVerify { mockApi.eliminarSolicitud(1L) }
    }

    @Test
    fun `eliminarSolicitud - debe manejar ID no existente`() = runTest {
        // Given
        coEvery { mockApi.eliminarSolicitud(999L) } returns
            Response.error(404, "Not found".toResponseBody())

        // When
        val result = repository.eliminarSolicitud(999L)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `eliminarSolicitud - debe manejar error de permisos (403)`() = runTest {
        // Given
        coEvery { mockApi.eliminarSolicitud(1L) } returns
            Response.error(403, "Forbidden".toResponseBody())

        // When
        val result = repository.eliminarSolicitud(1L)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("403") == true)
    }

    // ========== PRUEBAS DE OBTENER POR ESTADO ==========

    @Test
    fun `obtenerPorEstado - debe filtrar por estado PENDIENTE`() = runTest {
        // Given
        val pendientes = listOf(solicitudMock)
        coEvery { mockApi.obtenerPorEstado("PENDIENTE") } returns Response.success(pendientes)

        // When
        val result = repository.obtenerPorEstado("PENDIENTE")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("PENDIENTE", result.getOrNull()?.get(0)?.estado)
    }

    @Test
    fun `obtenerPorEstado - debe retornar lista vacía para estado sin resultados`() = runTest {
        // Given
        coEvery { mockApi.obtenerPorEstado("COMPLETADO") } returns Response.success(emptyList())

        // When
        val result = repository.obtenerPorEstado("COMPLETADO")

        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `obtenerPorEstado - debe manejar estados inválidos`() = runTest {
        // Given
        coEvery { mockApi.obtenerPorEstado("ESTADO_INVALIDO") } returns
            Response.error(400, "Bad request".toResponseBody())

        // When
        val result = repository.obtenerPorEstado("ESTADO_INVALIDO")

        // Then
        assertTrue(result.isFailure)
    }
}

