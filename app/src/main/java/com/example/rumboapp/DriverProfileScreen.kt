package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rumboapp.ui.theme.RumboAppTheme

// Colores
private val VerdeFondo      = Color(0xFF1B3011)
private val VerdeContenedor = Color(0xFF2D4B1E)
private val ColorDorado     = Color(0xFFD4AF37)
private val ColorCremita    = Color(0xFFE2E2BD)

@Composable
fun DriverProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onEditVehicleClick: () -> Unit = {},
    onAddPhotoClick: () -> Unit = {}
) {
    val conductor = viewModel.conductor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Encabezado ──────────────────────────────
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
                    .clickable { onHomeClick() }
            )
        }

        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxWidth().padding(50.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = ColorDorado)
            }
        } else if (conductor != null) {
            // ── Avatar + botón foto ────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4A7C59))
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                            contentDescription = "Avatar conductor",
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = ColorDorado,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.clickable { onAddPhotoClick() }
                    ) {
                        Text(
                            text = "Añadir Foto",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Tarjeta datos personales ───────────────────
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                color = VerdeContenedor,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    InfoFilaConductor(label = "Nombre:",   valor = conductor.nombre,   cremita = ColorCremita)
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoFilaConductor(label = "Vehículo:", valor = conductor.vehiculo,  cremita = ColorCremita)
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoFilaConductor(label = "Teléfono:", valor = conductor.telefono,  cremita = ColorCremita)
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoFilaConductor(label = "Email:",    valor = conductor.email,     cremita = ColorCremita)
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoFilaConductor(label = "Licencia:", valor = conductor.licencia,  cremita = ColorCremita)

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onEditProfileClick,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterHorizontally)
                            .height(38.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Editar",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Sección Vehículos Conductor ────────────────
            Text(
                text = "Vehículos Conductor",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                color = VerdeContenedor,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    if (conductor.vehiculos.isEmpty()) {
                        Text("No hay vehículos guardados.", color = ColorCremita, fontSize = 13.sp)
                    } else {
                        conductor.vehiculos.forEachIndexed { index, vehiculo ->
                            Text(
                                text = vehiculo.alias,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${vehiculo.placa}  ·  ${vehiculo.modelo}  ·  ${vehiculo.anio}",
                                color = ColorCremita,
                                fontSize = 13.sp
                            )
                            if (index < conductor.vehiculos.lastIndex) {
                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f), thickness = 0.8.dp)
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onEditVehicleClick,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterHorizontally)
                            .height(38.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Editar",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        } else {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No se pudo cargar el perfil de conductor", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// Fila auxiliar local
@Composable
private fun InfoFilaConductor(label: String, valor: String, cremita: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label ",
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = valor,
            color = cremita,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverProfilePreview() {
    RumboAppTheme {
        DriverProfileScreen()
    }
}
