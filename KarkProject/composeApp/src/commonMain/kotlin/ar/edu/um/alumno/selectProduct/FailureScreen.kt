package ar.edu.um.alumno.selectProduct

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import karkproject.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import karkproject.composeapp.generated.resources.cruz


@Composable
fun FailureScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Espera 2 segundos
        navController.navigate("productos")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(Res.drawable.cruz),
                contentDescription = "Cruz",
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "No se pudo realizar la compra",
                color = Color.Red,
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}