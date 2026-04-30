package com.example.rumboapp

data class DireccionGuardada(
    val alias: String = "",
    val descripcion: String = ""
)

data class TarjetaGuardada(
    val numero: String = "",
    val nombreTitular: String = "",
    val fechaVencimiento: String = "",
    val cvv: String = ""
)

data class PerfilUsuario(
    val nombre: String = "",
    val telefono: String = "",
    val email: String = "",
    val direcciones: List<DireccionGuardada> = emptyList(),
    val tarjetas: List<TarjetaGuardada> = emptyList(),
    val fotoUrl: String = "",
    val avatarName: String = ""
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

object Constants {
    val CIUDADES = listOf("Acacías", "Granada", "Puerto López", "San Martín", "Villavicencio")
}
