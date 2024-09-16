package ar.edu.um.alumno

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform