package ar.edu.um.alumno.compras




import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*


class MisComprasViewModel : ViewModel() {

private val _ventas = MutableStateFlow<List<VentaSimple>>(emptyList())
val ventas: StateFlow<List<VentaSimple>> = _ventas

init {
    loadProductos()
}

private fun loadProductos() {
    _ventas.value = Json.decodeFromString(ventasJson)
}
}



val ventasJson = """

[
    { 
        "id": 1508,
        "fechaVenta": "2024-10-10T20:15:00Z",
        "precioFinal": 450.00
    },
    {
        "id": 1509,
        "fechaVenta": "2024-10-10T20:15:00Z",
        "precioFinal": 450.00
    },
    {
        "id": 1510,
        "fechaVenta": "2024-10-10T20:15:00Z",
        "precioFinal": 450.00
    }

]
"""

val ventaUnitariaJson= """
{
    "idVenta": 1508,
    "idDispositivo": 1,
    "codigo": "NTB01",
    "nombre": "Lenovo IdeaPad 1 Laptop",
    "descripcion": "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
    "precioBase": 718.5,
    "moneda": "USD",
    "caracteristicas": [
        {
            "id": 1,
            "nombre": "Pantalla",
            "descripcion": "15.6” FHD Display"
        },
        {
            "id": 2,
            "nombre": "Camara",
            "descripcion": "720p Camera w/Privacy Shutter"
        },
        {
            "id": 3,
            "nombre": "Batería",
            "descripcion": "Batería 43Wh"
        }
    ],
    "personalizaciones": [
        {
            "id": 1,
            "nombre": "CPU",
            "descripcion": "Procesadores Disponibles",
            "opciones": {
                "id": 1,
                "codigo": null,
                "nombre": "Ryzen 5 5500U",
                "descripcion": "Procesador 1.8 GHz - 6(12) Cores",
                "precioAdicional": null
            }
        },
        {
            "id": 2,
            "nombre": "Memoria",
            "descripcion": "Memorias Disponibles",
            "opciones": {
                "id": 2,
                "codigo": null,
                "nombre": "Ryzen 5 5700U",
                "descripcion": "Procesador 2.1 GHz - 8(16) Cores",
                "precioAdicional": null
            }
        },
        {
            "id": 5,
            "nombre": "Video",
            "descripcion": "Video Disponible",
            "opciones": {
                "id": 5,
                "codigo": null,
                "nombre": "DDR4-8",
                "descripcion": "Memoria DDR4 - 8GB",
                "precioAdicional": null
            }
        }
    ],
    "adicionales": [
        {
            "id": 1,
            "nombre": "Mouse",
            "descripcion": "Mouse Bluetooth 3 teclas",
            "precio": 40.5
        },
        {
            "id": 2,
            "nombre": "Teclado",
            "descripcion": "Teclado bluetooth",
            "precio": 78
        }
    ]
}
"""
