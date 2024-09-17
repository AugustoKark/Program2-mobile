package ar.edu.um.alumno

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import ar.edu.um.alumno.login.LoginScreen
import ar.edu.um.alumno.login.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigation()

    }
}

