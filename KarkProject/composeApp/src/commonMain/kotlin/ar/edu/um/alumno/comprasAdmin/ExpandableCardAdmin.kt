package ar.edu.um.alumno.comprasAdmin
import  io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import kotlinx.coroutines.flow.StateFlow

import ar.edu.um.alumno.compras.VentaDetallada
import ar.edu.um.alumno.compras.VentaSimple
import androidx.lifecycle.ViewModel
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Job
import io.ktor.client.*
import io.ktor.client.call.*



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ExpandableCardAdmin(venta: VentaSimpleAdmin, token: String, viewModel: MisComprasViewModelAdmin) {
    var expanded by remember { mutableStateOf(false) }
    var ventaUnitaria by remember { mutableStateOf<VentaDetalladaAdmin?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                expanded = !expanded
                if (expanded && ventaUnitaria == null) {
                    isLoading = true
                    viewModel.fetchVentaDetallada(venta.idVenta, token) { result ->
                        ventaUnitaria = result
                        isLoading = false
                    }
                }
            },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Venta ID: ${venta.idVenta}")
            Text(text = "Codigo: ${venta.codigo}")
            Text(text = "Nombre: ${venta.nombre}")
            Text(text = "Descripcion: ${venta.description}")
            Text(text = "Precio: ${venta.precio}")

            if (expanded) {
                if (isLoading) {
                    Text(text = "Cargando detalles...")
                } else {
                    ventaUnitaria?.let { venta ->
                        Text(text = "Detalles de la venta unitaria:")
                        Row {
                            Text(text = "Nombre: ", fontWeight = FontWeight.Bold)
                            Text(text = venta.nombre)
                        }
                        Row {
                            Text(text = "Descripción: ", fontWeight = FontWeight.Bold)
                            Text(text = venta.descripcion)
                        }
                        Row {
                            Text(text = "Precio: ", fontWeight = FontWeight.Bold)
                            Text(text = "${venta.precioBase} ${venta.moneda}")
                        }

                        Text(text = "Características:", fontWeight = FontWeight.Bold)
                        venta.caracteristicas?.forEach { caracteristica ->
                            Text(text = "${caracteristica.nombre}: ${caracteristica.descripcion}")
                        }

                        Text(text = "Personalizaciones:", fontWeight = FontWeight.Bold)
                        venta.personalizaciones?.forEach { personalizacion ->
                            Column {
                                Text(text = "${personalizacion.nombre}: ", fontWeight = FontWeight.Bold)
                                personalizacion.opcion?.let { opcion ->
                                    Text(text = "${opcion.nombre} - ${opcion.descripcion}")
                                }
                            }
                        }

                        Text(text = "Adicionales:", fontWeight = FontWeight.Bold)
                        venta.adicionales?.forEach { adicional ->
                            Row {
                                Text(text = "${adicional.nombre}: ", fontWeight = FontWeight.Bold)
                                Text(text = "${adicional.descripcion} (${adicional.precio} ${venta.moneda})")
                            }
                        }
                    } ?: run {
                        Text(text = "No se encontraron detalles.")
                    }
                }
            }
        }
    }
}