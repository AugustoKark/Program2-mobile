package ar.edu.um.alumno

import AdicionalesScreen
import ProductoSeleccionScreen
import ProductoViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.um.alumno.compras.MisComprasScreen
import ar.edu.um.alumno.comprasAdmin.MisComprasScreenAdmin
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
        composable("productos") {
            ProductoSeleccionScreen(navController = navController)
        }
        composable("adicionales/{productoId}") { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId")?.toInt() ?: 0
            AdicionalesScreen(productoId = productoId, viewModel = viewModel(), navController = navController)
        }
        composable("misCompras") {
            MisComprasScreen(navController = navController)

        }
        composable("misComprasAdmin") {
            MisComprasScreenAdmin(navController = navController)
        }

    }
}
