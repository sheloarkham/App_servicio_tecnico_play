package appserviciotecnico.network.config

import appserviciotecnico.network.api.CotizacionApi
import appserviciotecnico.network.api.ExternalApi
import appserviciotecnico.network.api.SolicitudApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Configuración centralizada de Retrofit para consumir el backend Spring Boot
 */
object RetrofitClient {

    // URL base del backend
    // Para emulador Android Studio: "http://10.0.2.2:8080/api/"
    // Para dispositivo físico en la misma red: "http://192.168.100.141:8080/api/"
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    // URL base de la API externa (TMDB - The Movie Database)
    private const val EXTERNAL_API_BASE_URL = "https://api.themoviedb.org/3/"

    // API Key para TMDB (gratis, pública para desarrollo)
    private const val TMDB_API_KEY = "8d9b0d49e6b50e637cf6bcffeccd5e7c"

    // Cliente HTTP con logging para debug
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Instancia de Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Service para Solicitudes
    val solicitudApi: SolicitudApi by lazy {
        retrofit.create(SolicitudApi::class.java)
    }

    // API Service para Cotizaciones
    val cotizacionApi: CotizacionApi by lazy {
        retrofit.create(CotizacionApi::class.java)
    }

    // ===== CONFIGURACIÓN PARA API EXTERNA =====

    // Interceptor para agregar API Key a las peticiones externas
    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Agregar el parámetro 'api_key' a la URL (para TMDB)
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("api_key", TMDB_API_KEY)
            .build()

        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        chain.proceed(requestWithApiKey)
    }

    // Cliente HTTP específico para la API externa
    private val externalOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Instancia de Retrofit para API Externa (TMDB)
    private val externalRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(EXTERNAL_API_BASE_URL)
            .client(externalOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Service para API Externa
    val externalApi: ExternalApi by lazy {
        externalRetrofit.create(ExternalApi::class.java)
    }
}

