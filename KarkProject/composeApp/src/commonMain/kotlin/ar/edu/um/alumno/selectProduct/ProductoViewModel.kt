import androidx.lifecycle.ViewModel
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Job
import io.ktor.client.*
import io.ktor.client.call.*

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}



class ProductoViewModel : ViewModel() {

    val settings: Settings = Settings()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos


    private val _roles = MutableStateFlow<List<String>>(emptyList())
    val roles: StateFlow<List<String>> = _roles

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    init {
        loadRoles()
    }

    private fun loadRoles() {
        val rolesString = settings.getString("roles", "")
        _roles.value = rolesString.split(",").filter { it.isNotEmpty() }
        logger.info { "LOG: Roles cargados -------------------------" }
    }

    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Main)

    fun loadProductos() {
        val token = settings.getString("jwtToken", "")
        if (token != null) {
            logger.info { "LOG: Token cargado -------------------------" }
//            println("Token: $token")
            fetchDispositivos(token)
        } else {
            logger.info { "LOG: No hay token -------------------------" }
//            println("No hay token")
        }
    }

    private fun fetchDispositivos(token: String) {
        viewModelScope.launch {
            logger.info { "LOG: fetchDispositivos -------------------------" }
            try {
                val response: HttpResponse =
                    client.get("http://192.168.100.71:8080/api/dispositivos") {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $token")
                        }
                    }
                if (response.status == HttpStatusCode.OK) {
                    val dispositivos: List<Producto> = response.body()
                    _productos.value = dispositivos
                    logger.info { "LOG: Dispositivos cargados -------------------------" }
//                    println(dispositivos)
                } else {
//                    println("hubo un error")
                    // Handle error response
                    logger.error { "LOG: Hubo un error -------------------------" }
                }
            } catch (e: Exception) {
                logger.error { "LOG: Hubo un error -------------------------" }
                logger.error { e }
//                println("hubo un error")
//                println(e)
                // Handle exception
            }
        }
    }


    fun realizarVenta(venta: VentaRequest, onResult: (Boolean) -> Unit) {
        val token = settings.getString("jwtToken", "")
        viewModelScope.launch {
            logger.info { "LOG: Realizando venta -------------------------" }
            try {
                val response: HttpResponse =
                    client.post("http://192.168.100.71:8080/api/ventas/vender") {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $token")
                            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())

                        }
                        setBody(venta)

                        println(setBody(venta))
                        println("----------------------------------------")
                        println(venta)
                    }
                if (response.status == HttpStatusCode.OK) {
                    logger.info { "LOG: Venta realizada con exito -------------------------" }
//                    println("estoy pasando por aqui OK")
                    onResult(true)
                } else {
                    logger.error { "LOG: No se hizo la compra -------------------------" }
//                    println("no se hizo la compra macho")
                    onResult(false)
                }
            } catch (e: Exception) {
                logger.error { "LOG: Hubo un error -------------------------" }
                logger.error { e }
//                println("hubo un error"+e)
                onResult(false)
            }
        }
    }
}
