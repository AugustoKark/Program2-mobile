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


class ProductoViewModel : ViewModel() {

    val settings : Settings = Settings()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }


    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Main)

    fun loadProductos() {
        val token = settings.getString("jwtToken" , "")
        if (token != null){
            println("Token: $token")
            fetchDispositivos(token)
    }else{
            println("No hay token")
    }
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
                    println(dispositivos)
                } else {
                    println("hubo un error")
                    // Handle error response
                }
            } catch (e: Exception) {
                println("hubo un error")
                println(e)
                // Handle exception
            }
        }
    }
}