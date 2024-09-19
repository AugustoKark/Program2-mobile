import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ProductoViewModel : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    init {
        loadProductos()
    }

    private fun loadProductos() {
        _productos.value = Json.decodeFromString(productosJson)
    }
}

val productosJson = """
[
    {
        "id": 1,
        "codigo": "NTB01",
        "nombre": "Lenovo IdeaPad 1 Laptop",
        "descripcion": "Lenovo IdeaPad 1 Laptop, 15.6\" FHD Display, AMD Ryzen 5 5500U, 8GB RAM, 512GB SSD, Windows 11 Home, 720p Camera w/Privacy Shutter, Smart Noise Cancelling, Cloud Grey",
        "precioBase": 450.00,
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
                "opciones": [
                    {
                        "id": 1,
                        "codigo": "PROC01",
                        "nombre": "Ryzen 5 5500U",
                        "descripcion": "Procesador 1.8 GHz - 6(12) Cores",
                        "precioAdicional": 0.00
                    },
                    {
                        "id": 2,
                        "codigo": "PROC02",
                        "nombre": "Ryzen 5 5700U",
                        "descripcion": "Procesador 2.1 GHz - 8(16) Cores",
                        "precioAdicional": 120.00
                    }
                ]
            },
            {
                "id": 2,
                "nombre": "Memoria",
                "descripcion": "Memorias Disponibles",
                "opciones": [
                    {
                        "id": 5,
                        "codigo": "MEM01",
                        "nombre": "DDR4-8",
                        "descripcion": "Memoria DDR4 - 8GB",
                        "precioAdicional": 0.00
                    },
                    {
                        "id": 6,
                        "codigo": "MEM02",
                        "nombre": "DDR4-16",
                        "descripcion": "Memoria DDR4 - 16GB",
                        "precioAdicional": 150.00
                    }
                ]
            },
            {
                "id": 5,
                "nombre": "Video",
                "descripcion": "Video Disponible",
                "opciones": [
                    {
                        "id": 10,
                        "codigo": "VID01",
                        "nombre": "Integrado",
                        "descripcion": "GCN 5th generation GPU",
                        "precioAdicional": 0.00
                    }
                ]
            }
        ],
        "adicionales": [
            {
                "id": 1,
                "nombre": "Mouse",
                "descripcion": "Mouse Bluetooth 3 teclas",
                "precio": 40.50,
                "precioGratis": 2000.00
            },
            {
                "id": 2,
                "nombre": "Teclado",
                "descripcion": "Teclado bluetooth",
                "precio": 78.00,
                "precioGratis": -1.00
            },
            {
                "id": 3,
                "nombre": "Funda",
                "descripcion": "Funda de silicona",
                "precio": 30.00,
                "precioGratis": 2650.00
            }
        ]
    },
    {
        "id": 2,
        "codigo": "NTB02",
        "nombre": "MSI Stealth 18 AI Studio",
        "descripcion": "MSI Stealth 18 AI Studio 18\" 240Hz QHD+ Gaming Laptop: Intel Ultra 9-185H, NVIDIA Geforce RTX 4080, 32GB DDR5, 1TB NVMe SSD",
        "precioBase": 2899.00,
        "moneda": "USD",
        "caracteristicas": [
            {
                "id": 4,
                "nombre": "Pantalla",
                "descripcion": "18\" 240Hz QHD+\""
            },
            {
                "id": 5,
                "nombre": "Camara",
                "descripcion": "WebCam 1080p"
            },
            {
                "id": 6,
                "nombre": "Batería",
                "descripcion": "Batería 80Wh"
            },
            {
                "id": 7,
                "nombre": "Adicional",
                "descripcion": "SD Card Reader"
            }
        ],
        "personalizaciones": [
            {
                "id": 3,
                "nombre": "CPU",
                "descripcion": "Procesadores Disponibles",
                "opciones": [
                    {
                        "id": 3,
                        "codigo": "PROC03",
                        "nombre": "Core 9 185H",
                        "descripcion": "Procesador 2.3 GHz - 8 Cores",
                        "precioAdicional": 0.00
                    },
                    {
                        "id": 4,
                        "codigo": "PROC04",
                        "nombre": "Core 9 195H",
                        "descripcion": "Procesador 2.7 GHz - 12 Cores",
                        "precioAdicional": 300.00
                    }
                ]
            },
            {
                "id": 4,
                "nombre": "Memoria",
                "descripcion": "Memorias Disponibles",
                "opciones": [
                    {
                        "id": 7,
                        "codigo": "MEM03",
                        "nombre": "DDR6-16",
                        "descripcion": "Memoria DDR5 - 16GB",
                        "precioAdicional": 0.00
                    },
                    {
                        "id": 8,
                        "codigo": "MEM04",
                        "nombre": "DDR4-32",
                        "descripcion": "Memoria DDR5 - 32GB",
                        "precioAdicional": 120.00
                    },
                    {
                        "id": 9,
                        "codigo": "MEM05",
                        "nombre": "DDR4-64",
                        "descripcion": "Memoria DDR5 - 64GB",
                        "precioAdicional": 400.00
                    }
                ]
            },
            {
                "id": 6,
                "nombre": "Video",
                "descripcion": "Video Disponible",
                "opciones": [
                    {
                        "id": 12,
                        "codigo": "VID01",
                        "nombre": "RTX 4080",
                        "descripcion": "NVIDIA GeForce RTX 4080 - 12GB",
                        "precioAdicional": 0.00
                    },
                    {
                        "id": 13,
                        "codigo": "VID02",
                        "nombre": "RTX 4090",
                        "descripcion": "NVIDIA GeForce RTX 4080 - 16GB",
                        "precioAdicional": 600.00
                    }
                ]
            }
        ],
        "adicionales": [
            {
                "id": 1,
                "nombre": "Mouse",
                "descripcion": "Mouse Bluetooth 3 teclas",
                "precio": 40.50,
                "precioGratis": 2000.00
            },
            {
                "id": 2,
                "nombre": "Teclado",
                "descripcion": "Teclado bluetooth",
                "precio": 78.00,
                "precioGratis": -1.00
            },
            {
                "id": 3,
                "nombre": "Funda",
                "descripcion": "Funda de silicona",
                "precio": 30.00,
                "precioGratis": 2650.00
            },
            {
                "id": 4,
                "nombre": "Cargador",
                "descripcion": "Cargador rápido",
                "precio": 189.00,
                "precioGratis": 2400.00
            }
        ]
    }
]
"""
