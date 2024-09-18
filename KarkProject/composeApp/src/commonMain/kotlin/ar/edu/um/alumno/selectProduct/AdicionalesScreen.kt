package ar.edu.um.alumno.selectProduct

import ProductoViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun AdicionalesScreen(productoId: Int, viewModel: ProductoViewModel = viewModel()) {
    val productos by viewModel.productos.collectAsState()
    val producto = productos.find { it.id == productoId }

    producto?.let { prod ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = "Adicionales para ${prod.nombre}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Lista de adicionales
            prod.adicionales.forEach { adicional ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = adicional.nombre,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "${adicional.precio} ${prod.moneda}")
                }
            }
        }
    } ?: run {
        // Si el producto no se encuentra
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Producto no encontrado.")
        }
    }
}
