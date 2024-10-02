package ar.edu.um.alumno.selectProduct

import kotlin.js.JsName

@JsName("toFixed")
external fun toFixed(number: Double, digits: Int): String

actual fun Double.format(digits: Int): String {
    return toFixed(this, digits)
}