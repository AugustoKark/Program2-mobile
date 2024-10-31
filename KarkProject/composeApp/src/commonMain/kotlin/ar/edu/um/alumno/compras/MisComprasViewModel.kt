package ar.edu.um.alumno.compras




import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*
import com.russhwolf.settings.Settings
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Job
import io.ktor.client.*
import io.ktor.client.call.*
import androidx.lifecycle.viewModelScope
import mu.KotlinLogging





import kotlinx.coroutines.launch

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
private val logger = KotlinLogging.logger {}

class MisComprasViewModel : ViewModel() {

    private val _ventas = MutableStateFlow<List<VentaSimple>>(emptyList())
    val ventas: StateFlow<List<VentaSimple>> = _ventas

    val settings: Settings = Settings()

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    init {
        loadVentasUsuario()
    }

    private fun loadVentasUsuario() {
        val token = settings.getString("jwtToken", "")
        val userId = settings.getInt("userId", -1)
        if (token.isNotEmpty() && userId != -1) {
            fetchVentasUsuario(userId, token)
            logger.info { "LOG: Ventas cargadas -------------------------" }
        } else {
            logger.info { "LOG: No hay token o ID de usuario -------------------------" }
//            println("No hay token o ID de usuario")
        }
    }

    private fun fetchVentasUsuario(userId: Int, token: String) {
        viewModelScope.launch {
            try {
                logger.info { "LOG: Fetching ventas -------------------------" }
                val response: HttpResponse = client.get("http://192.168.100.71:8080/api/ventas/usuario/$userId") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
                if (response.status == HttpStatusCode.OK) {
                    val ventas: List<VentaSimple> = response.body()
                    _ventas.value = ventas
                    logger.info { "LOG: Ventas cargadas -------------------------" }
//                    println(ventas)
                } else {
                    logger.error { "LOG: Hubo un error: ${response.status}" }
//                    println("Hubo un error: ${response.status}")
                }
            } catch (e: Exception) {
                logger.error { "LOG: Hubo un error -------------------------" }
                logger.error { e }
//                println("Hubo un error")
//                println(e)
            }
        }
    }

    fun fetchVentaDetallada(idVenta: Int, token: String, onResult: (VentaDetallada?) -> Unit) {
        viewModelScope.launch {
            try {
                logger.info { "LOG: Fetching venta detallada -------------------------" }
                val response: HttpResponse = client.get("http://192.168.100.71:8080/api/ventas/profesor/$idVenta") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
                if (response.status == HttpStatusCode.OK) {
                    val ventaDetallada: VentaDetallada = response.body()
                    logger.info { "LOG: Venta detallada cargada -------------------------" }
                    onResult(ventaDetallada)
                } else {
//                    println("Hubo un error: ${response.status}")
                    logger.error { "LOG: Hubo un error: ${response.status}" }
                    onResult(null)
                }
            } catch (e: Exception) {
                logger.error { "LOG: Hubo un error -------------------------" }
                logger.error { e }
//                println("Hubo un error")
//                println(e)
                onResult(null)
            }
        }
    }
}