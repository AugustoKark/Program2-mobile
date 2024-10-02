package ar.edu.um.alumno.compras

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp



@Composable
fun MisComprasScreen() {
    // Contenido de la pantalla "Mis Compras"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mis Compras", style = MaterialTheme.typography.h4)
        // Agrega aquí el contenido de la pantalla "Mis Compras"
    }
}