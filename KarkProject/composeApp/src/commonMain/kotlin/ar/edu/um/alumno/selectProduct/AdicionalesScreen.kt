import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.um.alumno.selectProduct.format


@Composable
fun AdicionalesScreen(productoId: Int, navController: NavController, viewModel: ProductoViewModel = viewModel()) {
    val productos by viewModel.productos.collectAsState()
    val producto = productos.find { it.id == productoId }

    var selectedOptions = remember { mutableStateMapOf<Int, Opcion>() }
    var selectedAdicionales = remember { mutableStateListOf<Adicional>() }
    var totalPrice by remember { mutableStateOf(producto?.precioBase ?: 0.0) }

    // Función para actualizar el precio total
    fun actualizarPrecioTotal() {
        val personalizacionPrecio = selectedOptions.values.sumOf { it.precioAdicional }
        val basePriceWithPersonalizations = (producto?.precioBase ?: 0.0) + personalizacionPrecio

        val adicionalesPrecio = selectedAdicionales.sumOf { adicional ->
            if (adicional.precioGratis != -1.0 && basePriceWithPersonalizations > adicional.precioGratis) {
                0.0
            } else {
                adicional.precio
            }
        }
        totalPrice = basePriceWithPersonalizations + adicionalesPrecio
    }

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
                text = "Precio Total: ${producto.moneda} ${totalPrice.format(2)}",
                style = MaterialTheme.typography.h4.copy(
                    color = Color(0xFF438ea5),
                    fontSize = 22.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            )

            // Para cada personalización, creamos un botón
            LazyColumn {
                items(prod.personalizaciones) { personalizacion ->
                    PersonalizacionButton(
                        personalizacion = personalizacion,
                        selectedOptions = selectedOptions,
                        onOptionSelected = { actualizarPrecioTotal() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Adicionales con CheckBoxes
            Text(
                text = "Adicionales:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            prod.adicionales.forEach { adicional ->
                // Verificar si el adicional es gratis según la condición de precio
                val esGratis = adicional.precioGratis != -1.0 && totalPrice > adicional.precioGratis
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = selectedAdicionales.contains(adicional),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedAdicionales.add(adicional)
                            } else {
                                selectedAdicionales.remove(adicional)
                            }
                            actualizarPrecioTotal()  // Actualiza el precio al seleccionar adicionales
                        }
                    )
                    // Mostrar el texto con el precio o "Gratis" según la condición
                    Text(
                        text = "${adicional.nombre} - ${ adicional.precio.format(2)} ${prod.moneda}" +
                                if (esGratis) " (Gratis)" else ""
                    )
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
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF438ea5),  // Cambiar color del botón "Buy"
                    contentColor = Color.White
                )
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
    selectedOptions: MutableMap<Int, Opcion>,
    onOptionSelected: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }  // Controla si el menú está expandido
    val selectedOption = selectedOptions[personalizacion.id]

    Column {
        // Botón de selección de personalización (por ejemplo, "Procesador")
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF438ea5),  // Cambiar color del botón "Buy"
                contentColor = Color.White
            )
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
                    onOptionSelected()
                }) {
                    Text(text = "${opcion.nombre} (+${opcion.precioAdicional.format(2)} USD)")
                }
            }
        }
    }
}