package appserviciotecnico.network.config

import appserviciotecnico.network.api.CotizacionApi
import appserviciotecnico.network.api.SolicitudApi
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
}

