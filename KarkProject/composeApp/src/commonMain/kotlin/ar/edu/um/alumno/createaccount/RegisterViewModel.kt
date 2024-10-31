package ar.edu.um.alumno.createaccount

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*

import io.ktor.serialization.kotlinx.json.*

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


@Serializable
data class RegisterErrorResponse(
    val type: String,
    val title: String,
    val status: Int,
    val detail: String,
    val instance: String,
    val message: String,
    val params: String,
    val path: String
)

class RegisterViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordConfirm = MutableStateFlow("")
    val passwordConfirm: StateFlow<String> = _passwordConfirm

    private val _registerEnable = MutableStateFlow(false)
    val registerEnable: StateFlow<Boolean> = _registerEnable

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()

    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    fun onRegisterChanged(username: String, email: String, password: String, passwordConfirm: String) {
        _username.value = username
        _email.value = email
        _password.value = password
        _passwordConfirm.value = passwordConfirm

        _errorMessage.value = when {
            !isValidPassword(password) -> "La contraseña debe contener al menos 4 caracteres."
            password != passwordConfirm -> "Las contraseñas deben coincidir."
            else -> ""
        }

        _registerEnable.value = isValidEmail(email) && isValidPassword(password) && password == passwordConfirm && username.isNotEmpty()
    }

    private fun isValidPassword(password: String): Boolean = password.length >= 4

    private fun isValidEmail(email: String): Boolean = emailPattern.matches(email)

    suspend fun onRegisterSelected(): Boolean {
        return try {
            logger.info { "LOG: Registering user -------------------------" }

            val response: HttpResponse = client.post("http://192.168.100.71:8080/api/register") {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "login" to _username.value,
                        "email" to _email.value,
                        "password" to _password.value,
                        "langKey" to "es"
                    )
                )
            }
            _isLoading.value = true

            if (response.status == HttpStatusCode.Created) {
                logger.info { "LOG: User registered  -------------------------" }
                true
            } else {
                val errorResponse: RegisterErrorResponse = response.body()

                _errorMessage.value = when (errorResponse.message) {
                    "error.userexists" -> "El nombre de usuario ya está en uso."
                    "error.emailexists" -> "El email ya está en uso."
                    else -> "Error en el registro: ${errorResponse.title}"
                }
                logger.error { "LOG: ERROR: ${errorResponse.message} -------------------------" }
                false
            }
        } catch (e: Exception) {
            _errorMessage.value = "Error en el registro intente nuevamente "
            logger.error { "LOG: ERROR: ${e.message} -------------------------" }
            false
        } finally {
            logger.info { "LOG: Registering user finished -------------------------" }
            _isLoading.value = false
        }
    }
}