package ar.edu.um.alumno.createaccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import karkproject.composeapp.generated.resources.Res
import karkproject.composeapp.generated.resources.header
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(300.dp)
                .padding(3.dp)
        ) {
            Register(
                modifier = Modifier.padding(2.dp),
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
fun Register(modifier: Modifier, viewModel: RegisterViewModel, navController: NavController) {
    val username: String by viewModel.username.collectAsState(initial = "")
    val email: String by viewModel.email.collectAsState(initial = "")
    val password: String by viewModel.password.collectAsState(initial = "")
    val passwordConfirm: String by viewModel.passwordConfirm.collectAsState(initial = "")
    val registerEnable: Boolean by viewModel.registerEnable.collectAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.collectAsState(initial = false)
    val errorMessage: String by viewModel.errorMessage.collectAsState(initial = "")
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HeaderImage(Modifier)
                Spacer(modifier = Modifier.height(16.dp))
                UsernameField(username) { viewModel.onRegisterChanged(it, email, password, passwordConfirm) }
                Spacer(modifier = Modifier.height(4.dp))
                EmailField(email) { viewModel.onRegisterChanged(username, it, password, passwordConfirm) }
                Spacer(modifier = Modifier.height(4.dp))
                PasswordField(password) { viewModel.onRegisterChanged(username, email, it, passwordConfirm) }
                Spacer(modifier = Modifier.height(4.dp))
                PasswordConfirmField(passwordConfirm) { viewModel.onRegisterChanged(username, email, password, it) }
                Spacer(modifier = Modifier.height(4.dp))
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    LogInButton(Modifier.align(Alignment.CenterEnd), navController)
                }
                Spacer(modifier = Modifier.height(8.dp))
                RegisterButton(registerEnable) {
                    coroutineScope.launch {
                        val success = viewModel.onRegisterSelected()
                        if (success) {
                            navController.navigate("login")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun RegisterButton(registerEnable: Boolean, onRegisterSelected: () -> Unit) {
    Button(
        onClick = { onRegisterSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF87CEEB),
            disabledBackgroundColor = Color(0xFFB0B0B0),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = registerEnable
    ) {
        Text(text = "Register")
    }
}

@Composable
fun UsernameField(username: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = username, onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = "Username") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email, onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = "Password") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordConfirmField(passwordConfirm: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = passwordConfirm, onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = "Confirm Password") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            style = MaterialTheme.typography.h2.copy(
                color = Color(0xFF438ea5),
                fontSize = 60.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp)) // Agrega espacio entre el texto y la imagen
        Image(
            painter = painterResource(Res.drawable.header),
            contentDescription = "Header",
            modifier = modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun LogInButton(modifier: Modifier, navController: NavController) {
    Text(
        text = "Already have an account?",
        modifier = modifier.clickable { navController.navigate("login") },
        fontSize = 12.sp,
        color = Color(0xFF87CEEB)
    )
}