// File: composeApp/src/commonMain/kotlin/ar/edu/um/alumno/compras/VentaModel.kt

package ar.edu.um.alumno.compras

import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*

@Serializable
data class VentaSimple(
    val id: Int,
    val fechaVenta: String,
    val precioFinal: Double
)

@Serializable
data class VentaDetallada(
    val idVenta: Int,
    val idDispositivo: Int,
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
    @Serializable(with = OpcionListSerializer::class)
    val opciones: List<Opcion>
)

@Serializable
data class Opcion(
    val id: Int,
    val codigo: String?,
    val nombre: String,
    val descripcion: String,
    val precioAdicional: Double?
)

@Serializable
data class Adicional(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double
)

object OpcionListSerializer : JsonTransformingSerializer<List<Opcion>>(ListSerializer(Opcion.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonObject) {
            JsonArray(listOf(element))
        } else {
            element
        }
    }
}