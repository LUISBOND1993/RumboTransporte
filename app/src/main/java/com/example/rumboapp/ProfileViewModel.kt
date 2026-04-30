package com.example.rumboapp

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    var usuario by mutableStateOf<PerfilUsuario?>(null)
    var conductor by mutableStateOf<PerfilConductor?>(null)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        fetchProfileData()
    }

    // --- NUEVA FUNCIÓN PARA GUARDAR RESERVAS EN FIREBASE ---
    fun guardarReservaFirebase(
        origen: String,
        destino: String,
        fecha: String,
        silla: String,
        tipo: String
    ) {
        val userId = auth.currentUser?.uid ?: "anonimo"

        viewModelScope.launch {
            try {
                val reserva = hashMapOf(
                    "usuarioId" to userId,
                    "nombrePasajero" to (usuario?.nombre ?: "Sin nombre"),
                    "origen" to origen,
                    "destino" to destino,
                    "fechaViaje" to fecha,
                    "asiento" to silla,
                    "tipoServicio" to tipo,
                    "estado" to "Confirmado",
                    "fechaCreacion" to com.google.firebase.Timestamp.now()
                )

                // Guarda la información en una nueva colección llamada "reservas_viajes"
                db.collection("reservas_viajes").add(reserva).await()

            } catch (e: Exception) {
                errorMessage = "Error al guardar reserva: ${e.message}"
            }
        }
    }

    fun fetchProfileData() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                val userDoc = db.collection("usuarios").document(userId).get().await()
                if (userDoc.exists()) {
                    val nombre = userDoc.getString("nombre") ?: ""
                    val telefono = userDoc.getString("telefono") ?: ""
                    val email = userDoc.getString("email") ?: ""
                    val fotoUrl = userDoc.getString("fotoUrl") ?: ""
                    val avatarName = userDoc.getString("avatarName") ?: ""

                    val direccionesRaw = userDoc.get("direcciones") as? List<Map<String, String>>
                    val direcciones = direccionesRaw?.map {
                        DireccionGuardada(it["alias"] ?: "", it["descripcion"] ?: "")
                    } ?: emptyList()

                    val tarjetasRaw = userDoc.get("tarjetas") as? List<Map<String, String>>
                    val tarjetas = tarjetasRaw?.map {
                        TarjetaGuardada(it["numero"] ?: "", it["nombreTitular"] ?: "", it["fechaVencimiento"] ?: "", it["cvv"] ?: "")
                    } ?: emptyList()

                    usuario = PerfilUsuario(nombre, telefono, email, direcciones, tarjetas, fotoUrl, avatarName)
                }

                val driverDoc = db.collection("conductores").document(userId).get().await()
                if (driverDoc.exists()) {
                    val nombre = driverDoc.getString("nombre") ?: usuario?.nombre ?: ""
                    val vehiculo = driverDoc.getString("vehiculo") ?: ""
                    val telefono = driverDoc.getString("telefono") ?: usuario?.telefono ?: ""
                    val email = driverDoc.getString("email") ?: usuario?.email ?: ""
                    val licencia = driverDoc.getString("licencia") ?: ""

                    val vehiculosRaw = driverDoc.get("vehiculos") as? List<Map<String, String>>
                    val vehiculos = vehiculosRaw?.map {
                        VehiculoGuardado(it["alias"] ?: "", it["placa"] ?: "", it["modelo"] ?: "", it["anio"] ?: "")
                    } ?: emptyList()

                    conductor = PerfilConductor(nombre, vehiculo, telefono, email, licencia, vehiculos)
                }
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun updateUsuario(nombre: String, telefono: String, email: String, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                val updates = mapOf(
                    "nombre" to nombre,
                    "telefono" to telefono,
                    "email" to email
                )
                db.collection("usuarios").document(userId).update(updates).await()
                usuario = usuario?.copy(nombre = nombre, telefono = telefono, email = email)
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun updateDirecciones(direcciones: List<DireccionGuardada>, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                db.collection("usuarios").document(userId).update("direcciones", direcciones).await()
                usuario = usuario?.copy(direcciones = direcciones)
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun updateTarjetas(tarjetas: List<TarjetaGuardada>, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                db.collection("usuarios").document(userId).update("tarjetas", tarjetas).await()
                usuario = usuario?.copy(tarjetas = tarjetas)
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun updateAvatar(avatarName: String, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                val updates = mapOf(
                    "avatarName" to avatarName,
                    "fotoUrl" to ""
                )
                db.collection("usuarios").document(userId).update(updates).await()
                usuario = usuario?.copy(avatarName = avatarName, fotoUrl = "")
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun uploadProfilePhoto(uri: Uri, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                val photoRef = storage.reference.child("perfiles/$userId.jpg")
                photoRef.putFile(uri).await()
                val downloadUrl = photoRef.downloadUrl.await().toString()

                db.collection("usuarios").document(userId).update(
                    mapOf("fotoUrl" to downloadUrl, "avatarName" to "")
                ).await()
                usuario = usuario?.copy(fotoUrl = downloadUrl, avatarName = "")
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun updateConductor(nombre: String, vehiculo: String, email: String, licencia: String, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                val updates = mapOf(
                    "nombre" to nombre,
                    "vehiculo" to vehiculo,
                    "email" to email,
                    "licencia" to licencia
                )
                db.collection("conductores").document(userId).update(updates).await()
                conductor = conductor?.copy(nombre = nombre, vehiculo = vehiculo, email = email, licencia = licencia)
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun updateVehiculos(vehiculos: List<VehiculoGuardado>, onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            isLoading = true
            try {
                db.collection("conductores").document(userId).update("vehiculos", vehiculos).await()
                conductor = conductor?.copy(vehiculos = vehiculos)
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}