package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.data.PagoFirestore
import com.example.rumboapp.data.PagoRepository
import com.example.rumboapp.data.Resultado

@Composable
fun HistorialScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val verdeFondo = Color(0xFF1B3011)
    val verdeContenedor = Color(0xFF2D4B1E)
    val colorDorado = Color(0xFFD4AF37)
    val colorCremita = Color(0xFFE2E2BD)

    val repo = remember { PagoRepository() }
    var registros by remember { mutableStateOf<List<PagoFirestore>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val resultado = repo.obtenerHistorial()
        when (resultado) {
            is Resultado.Exito -> {
                registros = resultado.datos
                errorMsg = null
            }
            is Resultado.Error -> {
                errorMsg = resultado.mensaje
            }
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .padding(top = 40.dp)
    ) {
        // Cabecera
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.background(Color.White.copy(0.2f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
            }
            Text(
                "HISTORIAL DE VIAJES",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier.background(Color.White.copy(0.2f), CircleShape)
            ) {
                Icon(Icons.Default.Home, contentDescription = "Inicio", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = colorDorado)
            }
        } else if (errorMsg != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $errorMsg", color = Color.Red, modifier = Modifier.padding(16.dp))
            }
        } else if (registros.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes viajes registrados aún", color = colorCremita, fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(registros) { registro ->
                    CardHistorial(registro, verdeContenedor, colorDorado, colorCremita)
                }
            }
        }
    }
}

@Composable
fun CardHistorial(
    registro: PagoFirestore,
    bgColor: Color,
    accentColor: Color,
    textColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = bgColor,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 6.dp
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${registro.origen} → ${registro.destino}",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                
                val statusColor = if (registro.estado == "EXITOSO") Color(0xFF00E676) else Color(0xFFFF5252)
                val statusBg = statusColor.copy(alpha = 0.2f)
                
                Surface(
                    color = statusBg,
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, statusColor)
                ) {
                    Text(
                        text = registro.estado,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text("FECHA", color = textColor.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(registro.fecha, color = textColor, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("SILLA", color = textColor.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(registro.silla, color = textColor, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("TOTAL PAGADO", color = textColor.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("$${"%,d".format(registro.total)} COP", color = accentColor, fontWeight = FontWeight.Black, fontSize = 20.sp)
                }
            }
            
            if (registro.metodoPago.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "MÉTODO: ${registro.metodoPago}",
                        color = textColor.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "REF: ${registro.referencia}",
                        color = textColor.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
