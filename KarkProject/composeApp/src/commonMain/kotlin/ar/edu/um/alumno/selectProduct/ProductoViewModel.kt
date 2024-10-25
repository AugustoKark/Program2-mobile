import androidx.lifecycle.ViewModel
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


class ProductoViewModel : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Main)

    fun loadProductos(token: String) {
        fetchDispositivos(token)
    }

    private fun fetchDispositivos(token: String) {
        viewModelScope.launch {
            try {
                val response: HttpResponse = client.get("http://192.168.100.71:8080/api/dispositivos") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
                if (response.status == HttpStatusCode.OK) {
                    val dispositivos: List<Producto> = response.body()
                    _productos.value = dispositivos
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}