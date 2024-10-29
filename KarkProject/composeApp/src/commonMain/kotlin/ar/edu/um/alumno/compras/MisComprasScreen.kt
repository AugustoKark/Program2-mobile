package ar.edu.um.alumno.compras
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController




@Composable
fun MisComprasScreen(navController: NavController, viewModel: MisComprasViewModel = viewModel()) {
    val ventas by viewModel.ventas.collectAsState()
    val token = viewModel.settings.getString("jwtToken", "")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Mis Compras", style = MaterialTheme.typography.h4, color = Color(0xFF438ea5), fontWeight = FontWeight.Bold)
            LazyColumn {
                items(ventas) { venta ->
                    ExpandableCard(venta, token, viewModel)
                }
            }
        }
        Button(
            onClick = { navController.navigate("logout") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,  // Color de fondo del botón
                contentColor = Color.White
            )
        ) {
            Text("Log out")
        }
    }
}


