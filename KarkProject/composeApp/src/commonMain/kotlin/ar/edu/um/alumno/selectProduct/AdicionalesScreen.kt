import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdicionalesScreen(productoId: Int, navController: NavController, viewModel: ProductoViewModel = viewModel()) {
    val productos by viewModel.productos.collectAsState()
    val producto = productos.find { it.id == productoId }

    var selectedOptions = remember { mutableStateMapOf<Int, Opcion>() }  // Guardar opciones seleccionadas

    producto?.let { prod ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Título
            Text(
                text = "Selecciona las personalizaciones para ${prod.nombre}",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Para cada personalización, creamos un botón
            LazyColumn {
                items(prod.personalizaciones) { personalizacion ->
                    PersonalizacionButton(
                        personalizacion = personalizacion,
                        selectedOptions = selectedOptions
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para confirmar la selección de personalizaciones
            Button(
                onClick = {
                    // Aquí puedes realizar la lógica de confirmación o pasar al siguiente paso
                    navController.navigate("productos") // Redirige a otra pantalla o confirma la compra
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Confirmar selección")
            }
        }
    } ?: run {
        // Mostrar un texto si el producto no existe
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Producto no encontrado.")
        }
    }
}

@Composable
fun PersonalizacionButton(
    personalizacion: Personalizacion,
    selectedOptions: MutableMap<Int, Opcion>
) {
    var expanded by remember { mutableStateOf(false) }  // Controla si el menú está expandido
    val selectedOption = selectedOptions[personalizacion.id]

    Column {
        // Botón de selección de personalización (por ejemplo, "Procesador")
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${personalizacion.nombre}: ${selectedOption?.nombre ?: "Seleccionar"}"
            )
        }

        // Dropdown con opciones de la personalización
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            personalizacion.opciones.forEach { opcion ->
                DropdownMenuItem(onClick = {
                    selectedOptions[personalizacion.id] = opcion
                    expanded = false
                }) {
                    Text(text = opcion.nombre)
                }
            }
        }
    }
}
