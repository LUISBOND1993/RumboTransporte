package com.example.rumboapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumboapp.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ══════════════════════════════════════════════════════════════════
// ESTADOS DE LA UI
// ══════════════════════════════════════════════════════════════════
sealed class PagoUiState {
    object Idle      : PagoUiState()
    object Procesando: PagoUiState()
    data class Exitoso(
        val referencia: String,
        val total: Long,
        val metodoPago: MetodoPago
    ) : PagoUiState()
    data class Rechazado(
        val motivo: String,
        val codigo: String,
        val metodoPago: MetodoPago
    ) : PagoUiState()
    data class Error(val mensaje: String) : PagoUiState()
}

// ══════════════════════════════════════════════════════════════════
// PAGO VIEWMODEL
// ══════════════════════════════════════════════════════════════════
class PagoViewModel : ViewModel() {

    private val repo = PagoRepository()

    private val _uiState = MutableStateFlow<PagoUiState>(PagoUiState.Idle)
    val uiState: StateFlow<PagoUiState> = _uiState

    // Tiquete que viene de la pantalla de selección de viaje
    var tiqueteActual: InfoTiquete = InfoTiquete()
        private set

    fun setTiquete(tiquete: InfoTiquete) {
        tiqueteActual = tiquete
    }

    // ── Pago con tarjeta ───────────────────────────
    fun pagarConTarjeta(
        numero: String,
        nombre: String,
        fechaVencimiento: String,
        cvv: String,
        cuotas: Int
    ) {
        _uiState.value = PagoUiState.Procesando
        viewModelScope.launch {
            val resultado = SimuladorPago.procesarTarjeta(numero, nombre, fechaVencimiento, cvv)
            manejarResultado(resultado, MetodoPago.TARJETA)
        }
    }

    // ── Pago por PSE ───────────────────────────────
    fun pagarConPSE(
        banco: String,
        tipoCuenta: String,
        tipoPersona: String,
        numeroDocumento: String
    ) {
        _uiState.value = PagoUiState.Procesando
        viewModelScope.launch {
            val resultado = SimuladorPago.procesarPSE(banco, tipoCuenta, tipoPersona, numeroDocumento)
            manejarResultado(resultado, MetodoPago.PSE)
        }
    }

    // ── Pago en efectivo ───────────────────────────
    fun confirmarEfectivo() {
        _uiState.value = PagoUiState.Procesando
        viewModelScope.launch {
            val resultado = SimuladorPago.procesarEfectivo()
            manejarResultado(resultado, MetodoPago.EFECTIVO)
        }
    }

    // ── Lógica compartida ──────────────────────────
    private suspend fun manejarResultado(resultado: ResultadoSimulacion, metodo: MetodoPago) {
        when (resultado) {
            is ResultadoSimulacion.Exitoso -> {
                val referencia = SimuladorPago.generarReferencia()
                // Guardar en Firestore
                val pago = PagoFirestore(
                    referencia  = referencia,
                    metodoPago  = metodo.name,
                    estado      = EstadoPago.EXITOSO.name,
                    origen      = tiqueteActual.origen,
                    destino     = tiqueteActual.destino,
                    fecha       = tiqueteActual.fecha,
                    pasajeros   = tiqueteActual.pasajeros,
                    silla       = tiqueteActual.silla,
                    total       = tiqueteActual.total
                )
                when (val r = repo.guardarPago(pago)) {
                    is Resultado.Exito -> _uiState.value = PagoUiState.Exitoso(
                        referencia = referencia,
                        total      = tiqueteActual.total,
                        metodoPago = metodo
                    )
                    is Resultado.Error -> _uiState.value = PagoUiState.Error(r.mensaje)
                }
            }
            is ResultadoSimulacion.Rechazado -> {
                // También guardamos el rechazo en Firestore para historial
                val referencia = SimuladorPago.generarReferencia()
                repo.guardarPago(
                    PagoFirestore(
                        referencia    = referencia,
                        metodoPago    = metodo.name,
                        estado        = EstadoPago.RECHAZADO.name,
                        motivoRechazo = resultado.motivo,
                        origen        = tiqueteActual.origen,
                        destino       = tiqueteActual.destino,
                        fecha         = tiqueteActual.fecha,
                        pasajeros     = tiqueteActual.pasajeros,
                        silla         = tiqueteActual.silla,
                        total         = tiqueteActual.total
                    )
                )
                _uiState.value = PagoUiState.Rechazado(
                    motivo = resultado.motivo,
                    codigo = resultado.codigo,
                    metodoPago = metodo
                )
            }
        }
    }

    fun resetearEstado() { _uiState.value = PagoUiState.Idle }
}
