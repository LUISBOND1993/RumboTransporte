package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.data.InfoTiquete
import com.example.rumboapp.data.MetodoPago
import com.example.rumboapp.ui.theme.RumboAppTheme

// ── Colores compartidos del módulo ────────────────────────────────
private val VerdeFondo      = Color(0xFF1B3011)
private val VerdeContenedor = Color(0xFF2D4B1E)
private val ColorDorado     = Color(0xFFD4AF37)
private val ColorCremita    = Color(0xFFE2E2BD)
private val ColorError      = Color(0xFFCC3333)
private val ColorExito      = Color(0xFF4A7C59)

// ══════════════════════════════════════════════════════════════════
// COMPONENTES REUTILIZABLES
// ══════════════════════════════════════════════════════════════════

@Composable
private fun EncabezadoPago(titulo: String, onBackClick: () -> Unit, onHomeClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Surface(
            modifier = Modifier.size(36.dp).align(Alignment.CenterStart),
            color = Color.White.copy(alpha = 0.85f),
            shape = CircleShape
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_revert),
                    contentDescription = "Atrás", tint = Color.Black
                )
            }
        }
        Text(
            text = titulo, color = Color.White, fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center)
        )
        Icon(
            painter = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_myplaces),
            contentDescription = "Inicio", tint = Color.White,
            modifier = Modifier.size(28.dp).align(Alignment.CenterEnd).clickable { onHomeClick() }
        )
    }
}

@Composable
private fun ResumenTiquete(tiquete: InfoTiquete) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        color = VerdeContenedor, shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${tiquete.origen} → ${tiquete.destino}",
                    color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${tiquete.fecha} · ${tiquete.pasajeros} pasajero(s) · Silla ${tiquete.silla}",
                    color = ColorCremita, fontSize = 12.sp
                )
            }
            Text(
                text = "$${"%,d".format(tiquete.total)} COP",
                color = ColorDorado, fontSize = 15.sp, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun CampoPago(
    titulo: String,
    valor: String,
    alCambiar: (String) -> Unit,
    teclado: KeyboardType = KeyboardType.Text,
    maxCaracteres: Int = 100,
    placeholder: String = ""
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = titulo, color = Color.White, fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 4.dp))
        TextField(
            value = valor,
            onValueChange = { if (it.length <= maxCaracteres) alCambiar(it) },
            placeholder = { Text(placeholder, color = Color.Gray, fontSize = 13.sp) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = teclado),
            colors = TextFieldDefaults.colors(
                focusedContainerColor   = ColorCremita,
                unfocusedContainerColor = ColorCremita,
                focusedIndicatorColor   = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor        = Color.Black,
                unfocusedTextColor      = Color.Black
            )
        )
    }
}

// ══════════════════════════════════════════════════════════════════
// 1. PANTALLA SELECCIÓN DE MÉTODO DE PAGO
// ══════════════════════════════════════════════════════════════════
@Composable
fun PagoTiqueteScreen(
    tiquete: InfoTiquete = InfoTiquete("Bogotá", "Villavicencio", "Mar 20", 1, "14A", 68000),
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onContinuarClick: (MetodoPago) -> Unit = {}
) {
    var metodoSeleccionado by remember { mutableStateOf<MetodoPago?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoPago("Pago de tiquete", onBackClick, onHomeClick)

        Spacer(modifier = Modifier.height(8.dp))
        ResumenTiquete(tiquete)
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Elige cómo pagar",
            color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        // ── Tarjeta ──────────────────────────────────
        OpcionMetodoPago(
            nombre    = "Tarjeta crédito/débito",
            subtitulo = "Visa, Mastercard, Amex",
            seleccionado = metodoSeleccionado == MetodoPago.TARJETA,
            icono = "💳",
            onClick   = { metodoSeleccionado = MetodoPago.TARJETA }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // ── PSE ──────────────────────────────────────
        OpcionMetodoPago(
            nombre    = "PSE",
            subtitulo = "Transferencia desde tu banco",
            seleccionado = metodoSeleccionado == MetodoPago.PSE,
            icono = "🏦",
            onClick   = { metodoSeleccionado = MetodoPago.PSE }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // ── Efectivo ──────────────────────────────────
        OpcionMetodoPago(
            nombre    = "Efectivo",
            subtitulo = "Paga al abordar",
            seleccionado = metodoSeleccionado == MetodoPago.EFECTIVO,
            icono = "💵",
            onClick   = { metodoSeleccionado = MetodoPago.EFECTIVO }
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { metodoSeleccionado?.let { onContinuarClick(it) } },
            enabled = metodoSeleccionado != null,
            modifier = Modifier.fillMaxWidth(0.8f).height(55.dp).align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorDorado, disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(28.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Text("Continuar", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun OpcionMetodoPago(
    nombre: String, subtitulo: String, seleccionado: Boolean, icono: String, onClick: () -> Unit
) {
    val borderColor = if (seleccionado) ColorDorado else Color.Transparent
    val bgColor     = if (seleccionado) VerdeContenedor.copy(alpha = 0.9f) else VerdeContenedor

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = bgColor, shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icono, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = nombre, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                if (subtitulo.isNotBlank())
                    Text(text = subtitulo, color = ColorCremita, fontSize = 12.sp)
            }
            if (seleccionado) {
                Box(
                    modifier = Modifier.size(20.dp).clip(CircleShape)
                        .background(ColorDorado),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════
// 2. PANTALLA DATOS DE TARJETA
// ══════════════════════════════════════════════════════════════════
@Composable
fun DatosTarjetaScreen(
    tiquete: InfoTiquete = InfoTiquete("Bogotá", "Villavicencio", "Mar 20", 1, "14A", 68000),
    isLoading: Boolean = false,
    tarjetasGuardadas: List<com.example.rumboapp.TarjetaGuardada> = emptyList(),
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onPagarClick: (numero: String, nombre: String, fecha: String, cvv: String, cuotas: Int) -> Unit = { _, _, _, _, _ -> }
) {
    var numero   by remember { mutableStateOf("") }
    var nombre   by remember { mutableStateOf("") }
    var fecha    by remember { mutableStateOf("") }
    var cvv      by remember { mutableStateOf("") }
    var cuotas   by remember { mutableStateOf(1) }

    // Formato automático del número de tarjeta: XXXX XXXX XXXX XXXX
    fun formatearNumero(raw: String): String {
        val solo = raw.filter { it.isDigit() }.take(16)
        return solo.chunked(4).joinToString(" ")
    }

    Column(
        modifier = Modifier.fillMaxSize().background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoPago("Datos de tarjeta", onBackClick, onHomeClick)

        Spacer(modifier = Modifier.height(8.dp))
        ResumenTiquete(tiquete)

        if (tarjetasGuardadas.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Tus tarjetas guardadas:",
                color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                tarjetasGuardadas.forEach { tarjeta ->
                    Surface(
                        modifier = Modifier
                            .width(140.dp)
                            .height(80.dp)
                            .clickable {
                                numero = tarjeta.numero
                                nombre = tarjeta.nombreTitular
                                fecha = tarjeta.fechaVencimiento
                                cvv = tarjeta.cvv
                            },
                        color = if (numero == tarjeta.numero) ColorDorado else VerdeContenedor,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "**** ${tarjeta.numero.takeLast(4)}",
                                color = if (numero == tarjeta.numero) Color.Black else Color.White,
                                fontWeight = FontWeight.Bold, fontSize = 14.sp
                            )
                            Text(
                                text = tarjeta.nombreTitular,
                                color = if (numero == tarjeta.numero) Color.Black.copy(0.7f) else ColorCremita,
                                fontSize = 10.sp, maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Vista previa de la tarjeta ─────────────────
        Surface(
            modifier = Modifier.fillMaxWidth().height(180.dp).padding(horizontal = 20.dp),
            color = VerdeContenedor, shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Text(
                    text = if (numero.isBlank()) "•••• •••• •••• ••••" else formatearNumero(numero),
                    color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                Row(modifier = Modifier.align(Alignment.BottomStart)) {
                    Column {
                        Text("TITULAR", color = ColorCremita, fontSize = 10.sp)
                        Text(
                            nombre.uppercase().ifBlank { "NOMBRE APELLIDO" },
                            color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text("VENCE", color = ColorCremita, fontSize = 10.sp)
                        Text(
                            fecha.ifBlank { "MM/AA" },
                            color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {

            CampoPago(
                titulo = "Número de la tarjeta:",
                valor  = formatearNumero(numero),
                alCambiar = { nuevo ->
                    val solo = nuevo.filter { it.isDigit() }
                    if (solo.length <= 16) numero = solo
                },
                teclado = KeyboardType.Number,
                placeholder = "0000 0000 0000 0000"
            )
            Spacer(modifier = Modifier.height(14.dp))

            CampoPago(
                titulo = "Nombre del titular:",
                valor  = nombre, alCambiar = { nombre = it },
                placeholder = "Como aparece en la tarjeta"
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    CampoPago(
                        titulo = "Fecha de vencimiento:",
                        valor  = fecha,
                        alCambiar = { nuevo ->
                            val solo = nuevo.filter { it.isDigit() }.take(4)
                            fecha = when {
                                solo.length > 2 -> "${solo.take(2)}/${solo.drop(2)}"
                                else -> solo
                            }
                        },
                        teclado = KeyboardType.Number, placeholder = "MM/AA", maxCaracteres = 5
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    CampoPago(
                        titulo = "CVV:", valor = cvv,
                        alCambiar = { if (it.length <= 3) cvv = it },
                        teclado = KeyboardType.Number, placeholder = "000", maxCaracteres = 3
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))

            // ── Selector de cuotas ──────────────────────
            Text("Número de cuotas:", color = Color.White, fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 4.dp))
            Surface(color = ColorCremita, shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)) {
                    listOf(1, 3, 6, 12).forEach { n ->
                        val sel = cuotas == n
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (sel) ColorDorado else Color.Transparent)
                                .border(1.dp, if (sel) ColorDorado else Color.Gray, RoundedCornerShape(8.dp))
                                .clickable { cuotas = n }
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("$n", color = if (sel) Color.Black else Color.DarkGray,
                                fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(if (cuotas == 1) "Sin intereses" else "$cuotas cuotas",
                        color = Color.DarkGray, fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = ColorDorado,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Button(
                    onClick = { onPagarClick(numero, nombre, fecha, cvv, cuotas) },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(6.dp)
                ) {
                    Text("Pagar $${"%,d".format(tiquete.total)} COP",
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// ══════════════════════════════════════════════════════════════════
// 3. PANTALLA PAGO POR PSE
// ══════════════════════════════════════════════════════════════════
@Composable
fun PagoPSEScreen(
    tiquete: InfoTiquete = InfoTiquete("Bogotá", "Villavicencio", "Mar 20", 1, "14A", 68000),
    isLoading: Boolean = false,
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onIrAlBancoClick: (banco: String, tipoCuenta: String, tipoPersona: String, doc: String) -> Unit = { _, _, _, _ -> }
) {
    var banco          by remember { mutableStateOf("") }
    var tipoCuenta     by remember { mutableStateOf("") }
    var tipoPersona    by remember { mutableStateOf("") }
    var numDocumento   by remember { mutableStateOf("") }

    val bancos = listOf(
        "Bancolombia", "Banco de Bogotá", "Davivienda", "BBVA", "Scotiabank Colpatria",
        "Banco Popular", "Banco Agrario", "Nequi", "Daviplata"
    )
    var expandirBancos by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoPago("Pago por PSE", onBackClick, onHomeClick)

        Spacer(modifier = Modifier.height(8.dp))
        ResumenTiquete(tiquete)
        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {

            // ── Selección de banco ────────────────────────
            Text("Selecciona tu banco:", color = Color.White, fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 4.dp))
            Box {
                Surface(
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                        .clickable { expandirBancos = !expandirBancos },
                    color = ColorCremita, shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            banco.ifBlank { "Seleccionar banco" },
                            color = if (banco.isBlank()) Color.Gray else Color.Black,
                            fontSize = 14.sp
                        )
                        Text(if (expandirBancos) "▲" else "▼", color = Color.DarkGray)
                    }
                }
                DropdownMenu(
                    expanded = expandirBancos,
                    onDismissRequest = { expandirBancos = false },
                    modifier = Modifier.background(ColorCremita)
                ) {
                    bancos.forEach { b ->
                        DropdownMenuItem(
                            text = { Text(b, color = Color.Black) },
                            onClick = { banco = b; expandirBancos = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            CampoPago(
                titulo = "Tipo de cuenta:",
                valor  = tipoCuenta, alCambiar = { tipoCuenta = it },
                placeholder = "Ej: Ahorros o Corriente"
            )
            Spacer(modifier = Modifier.height(14.dp))
            CampoPago(
                titulo = "Tipo de persona:",
                valor  = tipoPersona, alCambiar = { tipoPersona = it },
                placeholder = "Ej: Natural o Jurídica"
            )
            Spacer(modifier = Modifier.height(14.dp))
            CampoPago(
                titulo = "Número de documento:",
                valor  = numDocumento, alCambiar = { numDocumento = it },
                teclado = KeyboardType.Number, placeholder = "Cédula o NIT"
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = ColorDorado,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Button(
                    onClick = { onIrAlBancoClick(banco, tipoCuenta, tipoPersona, numDocumento) },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(6.dp)
                ) {
                    Text("Ir al banco", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// ══════════════════════════════════════════════════════════════════
// 4. PANTALLA PAGO EN EFECTIVO
// ══════════════════════════════════════════════════════════════════
@Composable
fun PagoEfectivoScreen(
    tiquete: InfoTiquete = InfoTiquete("Bogotá", "Villavicencio", "Mar 20", 1, "14A", 68000),
    isLoading: Boolean = false,
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onConfirmarClick: () -> Unit = {},
    onSeleccionarOtroClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoPago("Pago en efectivo", onBackClick, onHomeClick)

        Spacer(modifier = Modifier.height(8.dp))
        ResumenTiquete(tiquete)
        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            color = VerdeContenedor, shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Entrégale al conductor",
                    color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${"%,d".format(tiquete.total)} COP",
                    color = ColorDorado, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${tiquete.origen} – ${tiquete.destino} · Silla ${tiquete.silla}",
                    color = ColorCremita, fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = ColorDorado.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("⚠️", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Lleva el valor exacto. El conductor podría no tener sencillo disponible.",
                            color = ColorDorado, fontSize = 12.sp, lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("¿Cómo funciona?", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                listOf(
                    "1" to "Reserva tu silla — El conductor reservará la silla evitando la necesidad de pago inmediato.",
                    "2" to "Preséntate al abordaje — El conductor verificará tu nombre en el registro.",
                    "3" to "Paga al subir — Entrega los $${"%,d".format(tiquete.total)} directamente al abordar el vehículo."
                ).forEach { (num, texto) ->
                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Box(
                            modifier = Modifier.size(22.dp).clip(CircleShape)
                                .background(ColorDorado),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(num, color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(texto, color = ColorCremita, fontSize = 12.sp,
                            lineHeight = 16.sp, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = ColorDorado,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Button(
                    onClick = onConfirmarClick,
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(6.dp)
                ) {
                    Text("Confirmar reserva", color = Color.Black,
                        fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onSeleccionarOtroClick,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Seleccionar otro método", color = Color.White,
                        fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

// ══════════════════════════════════════════════════════════════════
// PREVIEWS
// ══════════════════════════════════════════════════════════════════
@Preview(showSystemUi = true)
@Composable fun PrevSeleccion() = RumboAppTheme { PagoTiqueteScreen() }

@Preview(showSystemUi = true)
@Composable fun PrevTarjeta() = RumboAppTheme { DatosTarjetaScreen() }

@Preview(showSystemUi = true)
@Composable fun PrevPSE() = RumboAppTheme { PagoPSEScreen() }

@Preview(showSystemUi = true)
@Composable fun PrevEfectivo() = RumboAppTheme { PagoEfectivoScreen() }
