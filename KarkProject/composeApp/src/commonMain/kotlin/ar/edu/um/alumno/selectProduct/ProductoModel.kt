import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    val id: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val precioBase: Double,
    val moneda: String,
    val caracteristicas: List<Caracteristica>,
    val personalizaciones: List<Personalizacion>,
    val adicionales: List<Adicional>
)

@Serializable
data class Caracteristica(
    val id: Int,
    val nombre: String,
    val descripcion: String
)

@Serializable
data class Personalizacion(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val opciones: List<Opcion>
)

@Serializable
data class Opcion(
    val id: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val precioAdicional: Double
)

@Serializable
data class Adicional(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val precioGratis: Double
)


@Serializable
data class VentaRequest(
    val idDispositivo: Int,
    val personalizaciones: List<OpcionSeleccionada>,
    val adicionales: List<AdicionalRequest>,
    val precioFinal: Double,
    val fechaVenta: String
)

@Serializable
data class AdicionalRequest(
    val id: Int,
    val precio: Double
)

@Serializable
data class OpcionSeleccionada(
    val id: Int,
    val precioAdicional: Double
)