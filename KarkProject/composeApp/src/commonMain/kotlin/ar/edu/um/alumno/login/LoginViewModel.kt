package ar.edu.um.alumno.login

import androidx.lifecycle.ViewModel
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


    suspend fun authenticate(username: String, password: String): AuthResponse? {
        val response: HttpResponse = client.post("http://192.168.100.71:8080/api/authenticate") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(username, password))
        }
        return if (response.status == HttpStatusCode.OK) {
            val authResponse: AuthResponse = response.body()
            _jwtToken.value = authResponse.id_token
            _userId.value = authResponse.userId
            _roles.value = authResponse.roles
            authResponse
        } else {
            null
        }
    }
}
