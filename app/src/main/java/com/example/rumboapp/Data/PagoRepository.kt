package com.example.rumboapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
import kotlinx.coroutines.tasks.await

// ══════════════════════════════════════════════════════════════════
// MODELOS DE DATOS — Pagos
// ══════════════════════════════════════════════════════════════════

enum class MetodoPago { TARJETA, PSE, EFECTIVO }

enum class EstadoPago { EXITOSO, RECHAZADO, PENDIENTE}

data class InfoTiquete(
    val origen: String = "",
    val destino: String = "",
    val fecha: String = "",
    val pasajeros: Int = 1,
    val silla: String = "",
    val total: Long = 0L           // En pesos colombianos (COP)
)

data class PagoFirestore(
    val uid: String = "",
    val referencia: String = "",
    val metodoPago: String = "",
    val estado: String = "",
    val motivoRechazo: String = "",
    val origen: String = "",
    val destino: String = "",
    val fecha: String = "",
    val pasajeros: Int = 1,
    val silla: String = "",
    val total: Long = 0L,
    @ServerTimestamp
    val creadoEn: Date? = null
)

// Clase para manejar resultados de operaciones
sealed class Resultado<out T> {
    data class Exito<out T>(val datos: T) : Resultado<T>()
    data class Error(val mensaje: String) : Resultado<Nothing>()
}

// ══════════════════════════════════════════════════════════════════
// PAGO REPOSITORY
// Colección Firestore: "pagos/{uid}/historial/{referenciaId}"
// ══════════════════════════════════════════════════════════════════
class PagoRepository {
    private val db  = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun coleccion(uid: String) =
        db.collection("pagos").document(uid).collection("historial")

    suspend fun guardarPago(pago: PagoFirestore): Resultado<String> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Resultado.Error("Usuario no autenticado")
            val ref = coleccion(uid).document(pago.referencia)
            ref.set(pago.copy(uid = uid)).await()
            Resultado.Exito(pago.referencia)
        } catch (e: Exception) {
            Resultado.Error(e.message ?: "Error al guardar pago")
        }
    }

    suspend fun obtenerHistorial(): Resultado<List<PagoFirestore>> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Resultado.Error("Usuario no autenticado")
            val docs = coleccion(uid)
                .orderBy("creadoEn", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            val lista = docs.mapNotNull { it.toObject(PagoFirestore::class.java) }
            Resultado.Exito(lista)
        } catch (e: Exception) {
            Resultado.Error(e.message ?: "Error al obtener historial")
        }
    }
}

// ══════════════════════════════════════════════════════════════════
// LÓGICA DE SIMULACIÓN DE PAGO
// Valida localmente y decide si el pago es exitoso o rechazado
// ══════════════════════════════════════════════════════════════════
object SimuladorPago {

    // Genera una referencia única tipo #TRX-YYYYMMDD-XXXX
    fun generarReferencia(): String {
        val hoy = java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault())
            .format(Date())
        val aleatorio = (1000..9999).random()
        return "#TRX-$hoy-$aleatorio"
    }

    // Simula el pago con tarjeta:
    // - Rechaza si el número empieza en 0 o tiene menos de 16 dígitos
    // - Rechaza si CVV es "000"
    // - El resto: 80% éxito, 20% rechazo por fondos
    fun procesarTarjeta(
        numero: String,
        nombre: String,
        fechaVencimiento: String,
        cvv: String
    ): ResultadoSimulacion {
        val numeroLimpio = numero.replace(" ", "")

        if (numeroLimpio.length < 16)
            return ResultadoSimulacion.Rechazado(
                "Número de tarjeta inválido",
                "ERR_CARD_INVALID · Ref #4471"
            )
        if (cvv == "000")
            return ResultadoSimulacion.Rechazado(
                "CVV incorrecto",
                "ERR_CVV_MISMATCH · Ref #4472"
            )
        if (nombre.isBlank())
            return ResultadoSimulacion.Rechazado(
                "Nombre del titular requerido",
                "ERR_NAME_MISSING · Ref #4473"
            )

        // Simulación aleatoria: 80% éxito
        return if ((1..10).random() <= 8) {
            ResultadoSimulacion.Exitoso
        } else {
            ResultadoSimulacion.Rechazado(
                "Fondos insuficientes o tarjeta bloqueada por el banco",
                "Código: ERR_BANK_DECLINED · Ref #4474"
            )
        }
    }

    // Simula PSE: valida campos obligatorios, 80% éxito
    fun procesarPSE(
        banco: String,
        tipoCuenta: String,
        tipoPersona: String,
        numeroDocumento: String
    ): ResultadoSimulacion {
        if (banco.isBlank())
            return ResultadoSimulacion.Rechazado(
                "Debes seleccionar un banco",
                "ERR_BANK_2000 · DECLINED · Ref #4962"
            )
        if (numeroDocumento.isBlank())
            return ResultadoSimulacion.Rechazado(
                "Número de documento requerido",
                "ERR_DOC_MISSING · Ref #4963"
            )

        return if ((1..10).random() <= 8) {
            ResultadoSimulacion.Exitoso
        } else {
            ResultadoSimulacion.Rechazado(
                "Transacción rechazada por el banco",
                "Código: ERR_BANK_2008 · DECLINED · Ref #4962"
            )
        }
    }

    // Efectivo siempre es exitoso (reserva confirmada)
    fun procesarEfectivo(): ResultadoSimulacion = ResultadoSimulacion.Exitoso
}

sealed class ResultadoSimulacion {
    object Exitoso : ResultadoSimulacion()
    data class Rechazado(val motivo: String, val codigo: String) : ResultadoSimulacion()
}
