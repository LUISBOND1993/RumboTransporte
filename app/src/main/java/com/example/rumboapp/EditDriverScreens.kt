package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme

// ─────────────────────────────────────────────
// Colores compartidos
// ─────────────────────────────────────────────
private val VerdeFondo      = Color(0xFF1B3011)
private val ColorDorado     = Color(0xFFD4AF37)
private val ColorCremita    = Color(0xFFE2E2BD)

// ─────────────────────────────────────────────
// Encabezado reutilizable para ambas pantallas
// ─────────────────────────────────────────────
@Composable
private fun EncabezadoDriver(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterStart),
            color = Color.White.copy(alpha = 0.85f),
            shape = CircleShape
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_revert),
                    contentDescription = "Atrás",
                    tint = Color.Black
                )
            }
        }

        Text(
            text = "Perfil Conductor",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
            contentDescription = "Inicio",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

// ─────────────────────────────────────────────
// 2. PANTALLA EDITAR DATOS PERSONALES CONDUCTOR
//    Reutiliza CampoEdicionPerfil de EditProfileScreen.kt
// ─────────────────────────────────────────────
@Composable
fun EditDriverProfileScreen(
    nombreInicial:   String = "María Gomez",
    vehiculoInicial: String = "Volkswagen Gatos",
    emailInicial:    String = "anne_admin@gmail.com",
    licenciaInicial: String = "Lic-23456-X",
    onBackClick: () -> Unit = {},
    onGuardarClick: (String, String, String, String) -> Unit = { _, _, _, _ -> }
) {
    var nombre   by remember { mutableStateOf(nombreInicial) }
    var vehiculo by remember { mutableStateOf(vehiculoInicial) }
    var email    by remember { mutableStateOf(emailInicial) }
    var licencia by remember { mutableStateOf(licenciaInicial) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoDriver(onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Reutilizamos CampoEdicionPerfil de EditProfileScreen.kt
            CampoEdicionPerfil(
                titulo = "Nombre:",
                valor = nombre,
                alCambiar = { nombre = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Vehículo:",
                valor = vehiculo,
                alCambiar = { vehiculo = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Email:",
                valor = email,
                alCambiar = { email = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Licencia:",
                valor = licencia,
                alCambiar = { licencia = it },
                colorCremita = ColorCremita
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onGuardarClick(nombre, vehiculo, email, licencia) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                shape = RoundedCornerShape(30.dp),
                elevation = ButtonDefaults.buttonElevation(6.dp)
            ) {
                Text(
                    text = "Guardar Cambios",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Cancelar",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// ─────────────────────────────────────────────
// 3. PANTALLA EDITAR VEHÍCULO
//    Reutiliza CampoEdicionPerfil de EditProfileScreen.kt
// ─────────────────────────────────────────────
@Composable
fun EditVehicleScreen(
    vehiculoInicial: String = "",
    placaInicial:    String = "",
    modeloInicial:   String = "",
    anioInicial:     String = "",
    onBackClick: () -> Unit = {},
    onGuardarClick: (String, String, String, String) -> Unit = { _, _, _, _ -> }
) {
    var vehiculo by remember { mutableStateOf(vehiculoInicial) }
    var placa    by remember { mutableStateOf(placaInicial) }
    var modelo   by remember { mutableStateOf(modeloInicial) }
    var anio     by remember { mutableStateOf(anioInicial) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoDriver(onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Reutilizamos CampoEdicionPerfil de EditProfileScreen.kt
            CampoEdicionPerfil(
                titulo = "Vehículo:",
                valor = vehiculo,
                alCambiar = { vehiculo = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Placa:",
                valor = placa,
                alCambiar = { placa = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Modelo:",
                valor = modelo,
                alCambiar = { modelo = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Año:",
                valor = anio,
                alCambiar = { anio = it },
                colorCremita = ColorCremita
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onGuardarClick(vehiculo, placa, modelo, anio) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                shape = RoundedCornerShape(30.dp),
                elevation = ButtonDefaults.buttonElevation(6.dp)
            ) {
                Text(
                    text = "Guardar Cambios",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Cancelar",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// ── Previews ────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditDriverProfilePreview() {
    RumboAppTheme {
        EditDriverProfileScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditVehiclePreview() {
    RumboAppTheme {
        EditVehicleScreen()
    }
}
