package ar.edu.um.alumno.login

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
import org.jetbrains.compose.resources.painterResource
import karkproject.composeapp.generated.resources.Res
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import karkproject.composeapp.generated.resources.header

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel, navController = navController)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {
    val username: String by viewModel.username.collectAsState()
    val password: String by viewModel.password.collectAsState()
    val loginEnable: Boolean by viewModel.loginEnable.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderImage(Modifier)
            Spacer(modifier = Modifier.padding(16.dp))
            UsernameField(username, { viewModel.onLoginChanged(it, password) })
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password, { viewModel.onLoginChanged(username, it) })
            Spacer(modifier = Modifier.padding(4.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                ForgotPassword(Modifier.align(Alignment.CenterEnd))
            }
            Spacer(modifier = Modifier.padding(4.dp))
            LoginButton(loginEnable, navController, viewModel)

            Spacer(modifier = Modifier.padding(6.dp))
            TextOr()
            Spacer(modifier = Modifier.padding(6.dp))
            CreateAccountButton(Modifier, navController)
        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean, navController: NavController, viewModel: LoginViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch {
                val success = viewModel.onLoginSelected()
                if (success) {
                    navController.navigate("productos")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (loginEnable) Color(0xFF438ea5) else Color(0xFFB0B0B0),
            contentColor = Color.White,
        ),
        enabled = loginEnable,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Login", color = Color.White)
    }
}

@Composable
fun TextOr() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "OR",
            fontSize = 8.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CreateAccountButton(modifier: Modifier, navController: NavController) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = { navController.navigate("register") },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF438ea5),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Create Account", color = Color.White)
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Forgot password?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        color = Color(0xFF87CEEB)
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = password, onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
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
fun UsernameField(username: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = username, onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Username") },
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
fun HeaderImage(modifier: Modifier) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TechMarket",
            style = MaterialTheme.typography.h2.copy(
                color = Color(0xFF438ea5),
                fontSize = 60.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Image(
            painter = painterResource(Res.drawable.header),
            contentDescription = "Header",
            modifier = modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}