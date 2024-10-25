import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import karkproject.composeapp.generated.resources.Res
import karkproject.composeapp.generated.resources.laptop
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import karkproject.composeapp.generated.resources.header
import org.jetbrains.compose.resources.painterResource



@Composable
fun ProductoSeleccionScreen(navController: NavController, viewModel: ProductoViewModel = viewModel()) {
    val productos by viewModel.productos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProductos(token)
    }

    Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(productos) { producto ->
            ProductoCard(producto = producto, navController = navController)
            Spacer(modifier = Modifier.height(16.dp))  // Espacio entre cada producto
        }
    }
    Button(
        onClick = { navController.navigate("misCompras") },
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF438ea5),  // Color de fondo del botón
            contentColor = Color.White
        )

    ) {
        Text("Mis Compras")
    }
        Button(
            onClick = { navController.navigate("misComprasAdmin") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF438ea5),  // Color de fondo del botón
                contentColor = Color.White
            )

        ) {
            Text("Mis ComprasAdmin")
        }

}
}

@Composable
fun ProductoCard(producto: Producto, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Image(
                painter = painterResource(Res.drawable.laptop),
                contentDescription = "Laptop",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            )



            // Nombre del producto
            Text(
                text = producto.nombre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Descripción del producto
            Text(text = producto.descripcion)
            Spacer(modifier = Modifier.height(8.dp))

            // Precio base
            Text(
                text = "Precio: ${producto.moneda} ${producto.precioBase}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF438ea5)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Características del producto
            Text(
                text = "Características:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            producto.caracteristicas.forEach { caracteristica ->
                CaracteristicaItem(caracteristica = caracteristica)
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Buy"
            Button(
                onClick = {
                    // Redirige a la pantalla de adicionales con el producto seleccionado
                    navController.navigate("adicionales/${producto.id}")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF438ea5),  // Cambiar color del botón "Buy"
                contentColor = Color.White
            )){
                Text("Buy")
            }
        }
    }
}
@Composable
fun CaracteristicaItem(caracteristica: Caracteristica) {
    Column {
        Text(
            text = caracteristica.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(text = caracteristica.descripcion)
    }
}

