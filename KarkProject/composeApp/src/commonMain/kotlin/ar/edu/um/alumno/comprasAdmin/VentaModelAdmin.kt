package ar.edu.um.alumno.comprasAdmin


import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*

@Serializable
data class VentaSimpleAdmin(
    val idVenta: Int,
    val id: Int,
    val codigo: String,
    val nombre: String,
    val description: String,
    val precio: Double
)

@Serializable
data class VentaDetalladaAdmin(
    val idVenta: Int,
    val idDispositivo: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val precioBase: Double,
    val moneda: String,
    val caracteristicas: List<CaracteristicaAdmin>,
    val personalizaciones: List<PersonalizacionAdmin>,
    val adicionales: List<AdicionalAdmin>
)

@Serializable
data class CaracteristicaAdmin(
    val id: Int,
    val nombre: String,
    val descripcion: String
)

@Serializable
data class PersonalizacionAdmin(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    @Serializable(with = OpcionListSerializer::class)
    val opciones: List<OpcionAdmin>
)

@Serializable
data class OpcionAdmin(
    val id: Int,
    val codigo: String?,
    val nombre: String,
    val descripcion: String,
    val precioAdicional: Double?
)

@Serializable
data class AdicionalAdmin(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double
)

object OpcionListSerializer : JsonTransformingSerializer<List<OpcionAdmin>>(ListSerializer(OpcionAdmin.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonObject) {
            JsonArray(listOf(element))
        } else {
            element
        }
    }
}