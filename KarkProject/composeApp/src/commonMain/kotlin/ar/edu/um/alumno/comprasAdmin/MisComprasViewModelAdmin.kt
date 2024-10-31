package ar.edu.um.alumno.comprasAdmin

import ar.edu.um.alumno.compras.VentaSimple





import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Job
import io.ktor.client.*
import io.ktor.client.call.*
import com.russhwolf.settings.Settings


import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class MisComprasViewModelAdmin : ViewModel() {

    private val _ventas = MutableStateFlow<List<VentaSimpleAdmin>>(emptyList())
    val ventas: StateFlow<List<VentaSimpleAdmin>> = _ventas

    val settings : Settings = Settings()


    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    init {
        loadProductos()
    }

    private fun loadProductos() {
        val token = settings.getString("jwtToken", "")
        if (token.isNotEmpty()) {
            fetchVentas(token)
            logger.info { "LOG: Ventas cargadas -------------------------" }
        } else {
            logger.info { "LOG: No hay token -------------------------" }
//            println("No hay token")
        }
    }

    private fun fetchVentas(token: String) {
        viewModelScope.launch {
            try {
                logger.info { "LOG: Fetching ventas -------------------------" }
                val response: HttpResponse = client.get("http://192.168.100.71:8080/api/ventas/admin") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
                if (response.status == HttpStatusCode.OK) {
                    val ventas: List<VentaSimpleAdmin> = response.body()
                    _ventas.value = ventas
                    logger.info { "LOG: Ventas cargadas -------------------------" }
//                    println(ventas)
                } else {
                    logger.info { "LOG: Hubo un error -------------------------" }
//                    println("Hubo un error: ${response.status}")
                }
            } catch (e: Exception) {
                logger.info { "LOG: Hubo un error -------------------------" }
                logger.info { e }
//                println("Hubo un error")
//                println(e)
            }
        }
    }


   fun fetchVentaDetallada(idVenta: Int, token: String, onResult: (VentaDetalladaAdmin?) -> Unit) {
    viewModelScope.launch {
        try {
            logger.info { "LOG: Fetching venta detallada -------------------------" }
            val response: HttpResponse = client.get("http://192.168.100.71:8080/api/ventas/profesor/$idVenta") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            if (response.status == HttpStatusCode.OK) {

                val ventaDetallada: VentaDetalladaAdmin = response.body()
                onResult(ventaDetallada)
                logger.info { "LOG: Venta detallada cargada -------------------------" }
            } else {
//                println("Hubo un error: ${response.status}")
                logger.error { "LOG: Hubo un error -------------------------" }
                onResult(null)
            }
        } catch (e: Exception) {
            logger.error { "LOG: Hubo un error -------------------------" }
            logger.error { e }

//            println("Hubo un error")
//            println(e)
            onResult(null)
        }
    }
}
}


