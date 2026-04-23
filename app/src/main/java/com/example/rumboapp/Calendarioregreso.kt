package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarioregresoScreen(
    onDiaSeleccionado: (Int) -> Unit,
    onIrADestino: () -> Unit,
    ciudadDestino: String,
    direccionOrigen: String,
    onOrigenCambiado: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val verdeFondoCalendario = Color(0xFF2D461E)
    val verdeBotonesMes = Color(0xFF4C6636)
    val cremaCirculo = Color(0xFFE8D596)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_paisaje),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(24.dp).rotate(180f),
                        tint = Color.Unspecified
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(45.dp).clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "COMPRA TU PASAJE", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Spacer(modifier = Modifier.height(20.dp))

            RegFilaOpcion("SOLO IDA", false, verdeFondoCalendario) { }
            Spacer(modifier = Modifier.height(10.dp))
            RegFilaOpcion("IDA Y REGRESO", true, verdeFondoCalendario) { }

            Spacer(modifier = Modifier.height(15.dp))

            // DIRECCIÓN ORIGEN (Input de texto)
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Text("DIRECCIÓN DE ORIGEN", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Surface(modifier = Modifier.fillMaxWidth().height(35.dp), color = verdeFondoCalendario, shape = RoundedCornerShape(22.dp)) {
                    BasicTextField(
                        value = direccionOrigen,
                        onValueChange = onOrigenCambiado,
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 14.sp),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
                        decorationBox = { innerTextField ->
                            if (direccionOrigen.isEmpty()) Text("Escribe tu dirección...", color = Color.White.copy(0.6f), fontSize = 14.sp)
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // DESTINO (Botón)
            RegCajaDireccionBoton("INGRESA TU DESTINO:", if(ciudadDestino.isEmpty()) "Seleccionar..." else ciudadDestino, verdeFondoCalendario) {
                onIrADestino()
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("FECHA DE REGRESO", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Row(modifier = Modifier.padding(vertical = 10.dp)) {
                RegBotonMes("Enero", false, verdeBotonesMes) { }
                RegBotonMes("Febrero", false, verdeBotonesMes) { }
                RegBotonMes("Marzo", true, verdeBotonesMes) { }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(alpha = 0.95f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom").forEach {
                            Text(it, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    val totalDias = 31
                    val columnas = 7
                    val filas = (totalDias + columnas - 1) / columnas
                    Column {
                        for (fila in 0 until filas) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                for (col in 0 until columnas) {
                                    val dia = fila * columnas + col + 1
                                    if (dia <= totalDias) {
                                        RegItemDia(dia, cremaCirculo, verdeFondoCalendario) { onDiaSeleccionado(dia) }
                                    } else {
                                        Spacer(modifier = Modifier.size(35.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegFilaOpcion(texto: String, seleccionado: Boolean, color: Color, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 4.dp)
    ) {
        Box(modifier = Modifier.size(22.dp).clip(CircleShape).background(if (seleccionado) color else Color.Gray.copy(0.4f)))
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun RegCajaDireccionBoton(label: String, valor: String, color: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Surface(
            modifier = Modifier.fillMaxWidth().height(35.dp).clickable { onClick() },
            color = color,
            shape = RoundedCornerShape(22.dp)
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Text(valor, color = Color.White, modifier = Modifier.padding(start = 16.dp), fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun RegBotonMes(texto: String, activo: Boolean, color: Color, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp).clickable { onClick() },
        color = if (activo) color else color.copy(alpha = 0.4f),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = texto, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RegItemDia(dia: Int, colorCirculo: Color, colorTexto: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(35.dp).clip(CircleShape).background(colorCirculo).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = dia.toString(), color = colorTexto, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}