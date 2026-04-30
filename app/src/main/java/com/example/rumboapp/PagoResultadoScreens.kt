package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.data.MetodoPago
import com.example.rumboapp.ui.theme.RumboAppTheme

private val VerdeFondo      = Color(0xFF1B3011)
private val VerdeContenedor = Color(0xFF2D4B1E)
private val ColorDorado     = Color(0xFFD4AF37)
private val ColorCremita    = Color(0xFFE2E2BD)
private val ColorError      = Color(0xFFCC3333)
private val ColorExito      = Color(0xFF4A7C59)

// ══════════════════════════════════════════════════════════════════
// 5. PANTALLA PAGO RECHAZADO
// ══════════════════════════════════════════════════════════════════
@Composable
fun PagoRechazadoScreen(
    motivo: String = "Fondos insuficientes o tarjeta bloqueada por el banco",
    codigo: String = "Código: ERR_BANK_DECLINED · Ref #4474",
    metodoPago: MetodoPago = MetodoPago.TARJETA,
    onIntentarNuevoClick: () -> Unit = {},
    onSeleccionarOtroClick: () -> Unit = {}
) {
    val mensajeMetodo = when (metodoPago) {
        MetodoPago.TARJETA -> "Tu tarjeta no pudo ser procesada."
        MetodoPago.PSE     -> "Tu transferencia PSE no pudo ser completada."
        MetodoPago.EFECTIVO -> "No se pudo confirmar la reserva en efectivo."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        // ── Círculo con X ─────────────────────────────
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(ColorError),
            contentAlignment = Alignment.Center
        ) {
            Text("✕", color = Color.White, fontSize = 52.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Pago rechazado",
            color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$mensajeMetodo\nVerifica los datos e intenta\ncon otro método de pago.",
            color = Color.White.copy(alpha = 0.75f), fontSize = 14.sp,
            textAlign = TextAlign.Center, lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── Caja de motivo del error ──────────────────
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            color = ColorError.copy(alpha = 0.15f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "MOTIVO DEL ERROR",
                    color = ColorError, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = motivo,
                    color = Color.White, fontSize = 13.sp, lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = codigo,
                    color = ColorCremita, fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // ── Botones ───────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onIntentarNuevoClick,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(6.dp)
            ) {
                Text(
                    "Intentar de nuevo", color = Color.Black,
                    fontWeight = FontWeight.Bold, fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onSeleccionarOtroClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "Seleccionar otro método", color = Color.White,
                    fontWeight = FontWeight.SemiBold, fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// ══════════════════════════════════════════════════════════════════
// 6. PANTALLA PAGO EXITOSO — Tiquete confirmado
// ══════════════════════════════════════════════════════════════════
@Composable
fun PagoExitosoScreen(
    referencia: String  = "#TRX-20240320-8471",
    total: Long         = 68000,
    metodoPago: MetodoPago = MetodoPago.TARJETA,
    tituloPrincipal: String? = null,
    onVerResumenClick: () -> Unit = {},
    onVolverInicioClick: () -> Unit = {}
) {
    val etiquetaMetodo = when (metodoPago) {
        MetodoPago.TARJETA  -> "Tarjeta crédito/débito"
        MetodoPago.PSE      -> "PSE"
        MetodoPago.EFECTIVO -> "Efectivo"
    }

    val mensajeMetodo = when (metodoPago) {
        MetodoPago.EFECTIVO ->
            "Tu reserva ha sido confirmada.\nRevisa tu correo para más detalles."
        else ->
            "Tu tiquete ha sido confirmado.\nRevisa tu correo para más detalles."
    }

    val titulo = tituloPrincipal ?: when (metodoPago) {
        MetodoPago.EFECTIVO -> "¡Reserva exitosa!"
        else                -> "¡Pago exitoso!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        // ── Círculo con ✓ ─────────────────────────────
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(ColorExito),
            contentAlignment = Alignment.Center
        ) {
            Text("✓", color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = titulo,
            color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = mensajeMetodo,
            color = Color.White.copy(alpha = 0.75f), fontSize = 14.sp,
            textAlign = TextAlign.Center, lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── Tarjeta del tiquete ───────────────────────
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            color = VerdeContenedor,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Tiquete confirmado",
                    color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Divider decorativo
                HorizontalDivider(color = Color.White.copy(alpha = 0.15f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(14.dp))

                FilaResumen("Método de pago:", etiquetaMetodo)
                Spacer(modifier = Modifier.height(10.dp))
                FilaResumen("Total:", "$${"%,d".format(total)} COP", esDorado = true)
                Spacer(modifier = Modifier.height(10.dp))
                FilaResumen("Referencia:", referencia)

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.15f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Guarda esta referencia para cualquier\nreclamación o soporte.",
                    color = ColorCremita, fontSize = 12.sp,
                    lineHeight = 16.sp, textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Botones ───────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onVerResumenClick,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(6.dp)
            ) {
                Text(
                    "Ver resumen de viaje", color = Color.Black,
                    fontWeight = FontWeight.Bold, fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onVolverInicioClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "Volver al inicio", color = Color.White,
                    fontWeight = FontWeight.SemiBold, fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// Fila reutilizable para el resumen del tiquete
@Composable
private fun FilaResumen(label: String, valor: String, esDorado: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = ColorCremita, fontSize = 13.sp)
        Text(
            text = valor,
            color = if (esDorado) ColorDorado else Color.White,
            fontSize = if (esDorado) 16.sp else 13.sp,
            fontWeight = if (esDorado) FontWeight.Bold else FontWeight.Normal
        )
    }
}

// ══════════════════════════════════════════════════════════════════
// PREVIEWS
// ══════════════════════════════════════════════════════════════════
@Preview(showSystemUi = true)
@Composable fun PrevRechazado() = RumboAppTheme { PagoRechazadoScreen() }

@Preview(showSystemUi = true)
@Composable fun PrevExitoso() = RumboAppTheme { PagoExitosoScreen() }

@Preview(showSystemUi = true)
@Composable fun PrevExitosoEfectivo() = RumboAppTheme {
    PagoExitosoScreen(metodoPago = MetodoPago.EFECTIVO, tituloPrincipal = "¡Reserva exitosa!")
}
