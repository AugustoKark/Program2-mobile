package ar.edu.um.alumno

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.um.alumno.createaccount.RegisterScreen
import ar.edu.um.alumno.createaccount.RegisterViewModel
import ar.edu.um.alumno.login.LoginScreen
import ar.edu.um.alumno.login.LoginViewModel


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(viewModel = LoginViewModel(), navController = navController)
        }
        composable("register") {
            RegisterScreen(viewModel = RegisterViewModel(), navController = navController)
        }
    }
}
