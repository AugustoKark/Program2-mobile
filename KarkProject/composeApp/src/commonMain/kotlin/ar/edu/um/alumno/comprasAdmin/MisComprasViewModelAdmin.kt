package ar.edu.um.alumno.comprasAdmin

import ar.edu.um.alumno.compras.VentaSimple





import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*


class MisComprasViewModelAdmin : ViewModel() {

    private val _ventas = MutableStateFlow<List<VentaSimpleAdmin>>(emptyList())
    val ventas: StateFlow<List<VentaSimpleAdmin>> = _ventas

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
        "idVenta": 1502,
        "id": 1502,
        "codigo": "NTB02",
        "nombre": "MSI Stealth 18 AI Studio",
        "description": "MSI Stealth 18 AI Studio 18\" 240Hz QHD+ Gaming Laptop: Intel Ultra 9-185H, NVIDIA Geforce RTX 4080, 32GB DDR5, 1TB NVMe SSD",
        "precio": 2847.00
    },
    {
        "idVenta": 1505,
        "id": 1505,
        "codigo": "NTB01",
        "nombre": "Lenovo IdeaPad 1 Laptop",
        "description": "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
        "precio": 568.50
    },
    {
        "idVenta": 1506,
        "id": 1506,
        "codigo": "NTB01",
        "nombre": "Lenovo IdeaPad 1 Laptop",
        "description": "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
        "precio": 568.50
    },
    {
        "idVenta": 1507,
        "id": 1507,
        "codigo": "NTB01",
        "nombre": "Lenovo IdeaPad 1 Laptop",
        "description": "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
        "precio": 568.50
    },
    {
        "idVenta": 1508,
        "id": 1508,
        "codigo": "NTB01",
        "nombre": "Lenovo IdeaPad 1 Laptop",
        "description": "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
        "precio": 718.50
    },
    {
        "idVenta": 1509,
        "id": 1509,
        "codigo": "NTB01",
        "nombre": "Lenovo IdeaPad 1 Laptop",
        "description": "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
        "precio": 718.50
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
