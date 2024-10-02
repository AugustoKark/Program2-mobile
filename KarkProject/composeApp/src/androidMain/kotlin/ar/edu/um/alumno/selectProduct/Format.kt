package ar.edu.um.alumno.selectProduct

actual fun Double.format(digits: Int): String = String.format("%.${digits}f", this)
