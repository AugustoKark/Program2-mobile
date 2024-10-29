package ar.edu.um.alumno.login

import androidx.lifecycle.ViewModel
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import com.russhwolf.settings.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*


private val settings: Settings = Settings()

@Serializable
data class AuthRequest(val username: String, val password: String)


@Serializable
data class AuthResponse(
    val id_token: String,
    val userId: Int,
    val roles: List<String>
)

class LoginViewModel : ViewModel() {


    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginEnable = MutableStateFlow(false)
    val loginEnable: StateFlow<Boolean> = _loginEnable

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _jwtToken = MutableStateFlow<String?>(null)
    val jwtToken: StateFlow<String?> = _jwtToken

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId

    private val _roles = MutableStateFlow<List<String>>(emptyList())
    val roles: StateFlow<List<String>> = _roles

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


//    init {
//
//        _jwtToken.value = settings.getString("jwtToken", null.toString())
//        _userId.value = settings.getInt("userId", -1).takeIf { it != -1 }
//        _roles.value = settings.getString("roles", "").split(",").filter { it.isNotEmpty() }
//    }
    fun onLoginChanged(username: String, password: String) {
        _username.value = username
        _password.value = password
        _loginEnable.value = isValidUsername(username) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 3

    private fun isValidUsername(username: String): Boolean = username.isNotEmpty()

    suspend fun onLoginSelected(): Boolean {
        _isLoading.value = true
        val response = authenticate(username.value, password.value)
        _isLoading.value = false

        return response != null
    }

    fun logout() {
        settings.putString("jwtToken", "")
        settings.putInt("userId", -1)
        settings.putString("roles", "")
        _jwtToken.value = null
        _userId.value = null
        _roles.value = emptyList()
    }


    suspend fun authenticate(username: String, password: String): AuthResponse? {
        val response: HttpResponse = client.post("http://192.168.100.71:8080/api/authenticate") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(username, password))
        }
        return if (response.status == HttpStatusCode.OK) {
            val authResponse: AuthResponse = response.body()
            _jwtToken.value = authResponse.id_token
            settings.putString("jwtToken", authResponse.id_token)
            _userId.value = authResponse.userId
            settings.putInt("userId", authResponse.userId)
            _roles.value = authResponse.roles
            settings.putString("roles", authResponse.roles.joinToString(","))
            println(jwtToken.value)
            println(userId.value)
            println(roles.value)
            _errorMessage.value = null


            authResponse
        } else {
            println("Error: ${response.status}")
            _errorMessage.value = "Username o contraseña incorrectos"

            null
        }
    }
}
