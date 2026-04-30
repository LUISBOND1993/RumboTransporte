package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

data class RegistroHistorial(
    val origen: String = "",
    val destino: String = "",
    val fecha: String = "",
    val silla: String = "",
    val total: Long = 0L,
    val estado: String = "Confirmado",
    val metodoPago: String = ""
)

@Composable
fun HistorialScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val verdeFondo = Color(0xFF1B3011)
    val verdeContenedor = Color(0xFF2D4B1E)
    val colorDorado = Color(0xFFD4AF37)
    val colorCremita = Color(0xFFE2E2BD)

    var registros by remember { mutableStateOf<List<RegistroHistorial>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            try {
                val db = FirebaseFirestore.getInstance()
                // Consultamos la colección de reservas que creamos en ProfileViewModel
                val snapshot = db.collection("reservas_viajes")
                    .whereEqualTo("usuarioId", uid)
                    .orderBy("fechaCreacion", Query.Direction.DESCENDING)
                    .get()
                    .await()
                
                registros = snapshot.documents.map { doc ->
                    RegistroHistorial(
                        origen = doc.getString("origen") ?: "",
                        destino = doc.getString("destino") ?: "",
                        fecha = doc.getString("fechaViaje") ?: "",
                        silla = doc.getString("asiento") ?: "",
                        total = doc.getLong("total") ?: 68000L, // Valor por defecto si no está
                        estado = doc.getString("estado") ?: "Confirmado",
                        metodoPago = doc.getString("tipoServicio") ?: ""
                    )
                }
            } catch (e: Exception) {
                // Manejar error
            } finally {
                isLoading = false
            }
        }
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
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
            }
            Text(
                "HISTORIAL DE VIAJES",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier.background(Color.White.copy(0.2f), CircleShape)
            ) {
                Icon(Icons.Default.Home, contentDescription = "Inicio", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = colorDorado)
            }
        } else if (registros.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes viajes registrados aún", color = colorCremita)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
    registro: RegistroHistorial,
    bgColor: Color,
    accentColor: Color,
    textColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = bgColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${registro.origen} → ${registro.destino}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Surface(
                    color = accentColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        registro.estado,
                        color = accentColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Fecha: ${registro.fecha}", color = textColor, fontSize = 13.sp)
                    Text("Silla: ${registro.silla}", color = textColor, fontSize = 13.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Total pagado:", color = textColor, fontSize = 11.sp)
                    Text("$${"%,d".format(registro.total)} COP", color = accentColor, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
            
            if (registro.metodoPago.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Método: ${registro.metodoPago}",
                    color = textColor.copy(alpha = 0.7f),
                    fontSize = 11.sp
                )
            }
        }
    }
}
