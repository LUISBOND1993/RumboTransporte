package com.example.rumboapp

data class DireccionGuardada(
    val alias: String = "",
    val descripcion: String = ""
)

data class PerfilUsuario(
    val nombre: String = "",
    val telefono: String = "",
    val email: String = "",
    val direcciones: List<DireccionGuardada> = emptyList(),
    val fotoUrl: String = "",
    val avatarName: String = "" // Nueva propiedad para el avatar seleccionado
)

data class VehiculoGuardado(
    val alias: String = "",
    val placa: String = "",
    val modelo: String = "",
    val anio: String = ""
)

data class PerfilConductor(
    val nombre: String = "",
    val vehiculo: String = "",
    val telefono: String = "",
    val email: String = "",
    val licencia: String = "",
    val vehiculos: List<VehiculoGuardado> = emptyList()
)
