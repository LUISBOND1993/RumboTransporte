package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.rumboapp.ui.theme.RumboAppTheme

// Colores del proyecto
private val VerdeFondo       = Color(0xFF1B3011)
private val VerdeContenedor  = Color(0xFF2D4B1E)
private val ColorDorado      = Color(0xFFD4AF37)
private val ColorCremita     = Color(0xFFE2E2BD)

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onEditAddressClick: () -> Unit = {},
    onAddAddressClick: () -> Unit = {},
    onAddPhotoClick: () -> Unit = {},
    onSelectAvatarClick: () -> Unit = {},
    onHistorialClick: () -> Unit = {}
) {
    val usuario = viewModel.usuario

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
                text = "Perfil",
                color = Color.White,
                fontSize = 22.sp,
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
        } else if (usuario != null) {
            // ── Foto de perfil ────────────────────────────
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
                        if (usuario.fotoUrl.isNotEmpty()) {
                            AsyncImage(
                                model = usuario.fotoUrl,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else if (usuario.avatarName.isNotEmpty()) {
                            // Mapeo de avatarName a recurso drawable
                            val resId = when (usuario.avatarName) {
                                "avatar_1" -> android.R.drawable.ic_menu_gallery
                                "avatar_2" -> android.R.drawable.ic_menu_camera
                                "avatar_3" -> android.R.drawable.ic_menu_agenda
                                "avatar_4" -> android.R.drawable.ic_menu_call
                                "avatar_5" -> android.R.drawable.ic_menu_day
                                "avatar_6" -> android.R.drawable.ic_menu_myplaces
                                else -> android.R.drawable.ic_menu_myplaces
                            }
                            Icon(
                                painter = painterResource(id = resId),
                                contentDescription = "Avatar",
                                tint = Color.White,
                                modifier = Modifier.size(50.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                                contentDescription = "Avatar por defecto",
                                tint = Color.White,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = ColorDorado,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.clickable { onAddPhotoClick() }
                        ) {
                            Text(
                                text = "Cambiar Foto",
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                            )
                        }

                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.clickable { onSelectAvatarClick() }
                        ) {
                            Text(
                                text = "Elegir Avatar",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Botón Historial de Viajes ──────────────
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clickable { onHistorialClick() },
                color = VerdeContenedor,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.History, contentDescription = null, tint = ColorDorado)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Ver Historial de Viajes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_media_play),
                        contentDescription = null,
                        tint = Color.White.copy(0.5f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Tarjeta con datos personales ──────────────
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                color = VerdeContenedor,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    InfoFila(label = "Nombre:", valor = usuario.nombre)
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoFila(label = "Teléfono:", valor = usuario.telefono)
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoFila(label = "Email:", valor = usuario.email)
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

            // ── Sección Tarjetas Guardadas ──────────────
            Text(
                text = "Tarjetas Guardadas",
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
                    if (usuario.tarjetas.isEmpty()) {
                        Text("No hay tarjetas guardadas.", color = ColorCremita, fontSize = 13.sp)
                    } else {
                        usuario.tarjetas.forEachIndexed { index, tarjeta ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "**** **** **** ${tarjeta.numero.takeLast(4)}",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = tarjeta.nombreTitular,
                                        color = ColorCremita,
                                        fontSize = 12.sp
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        val nuevasTarjetas = usuario.tarjetas.toMutableList()
                                        nuevasTarjetas.removeAt(index)
                                        viewModel.updateTarjetas(nuevasTarjetas) {}
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar tarjeta",
                                        tint = Color.White.copy(alpha = 0.7f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            if (index < usuario.tarjetas.lastIndex) {
                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f), thickness = 0.8.dp)
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Sección Direcciones Guardadas ─────────────
            Text(
                text = "Direcciones Guardadas",
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
                    if (usuario.direcciones.isEmpty()) {
                        Text("No hay direcciones guardadas.", color = ColorCremita, fontSize = 13.sp)
                    } else {
                        usuario.direcciones.forEachIndexed { index, dir ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = dir.alias,
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = dir.descripcion,
                                        color = ColorCremita,
                                        fontSize = 13.sp
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        val nuevasDirs = usuario.direcciones.toMutableList()
                                        nuevasDirs.removeAt(index)
                                        viewModel.updateDirecciones(nuevasDirs) {}
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar dirección",
                                        tint = Color.White.copy(alpha = 0.7f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            if (index < usuario.direcciones.lastIndex) {
                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f), thickness = 0.8.dp)
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        Button(
                            onClick = onEditAddressClick,
                            modifier = Modifier
                                .weight(1f)
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

                        Button(
                            onClick = onAddAddressClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Agregar dirección",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No se pudo cargar el perfil", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun InfoFila(label: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label ",
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = valor,
            color = ColorCremita,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview() {
    RumboAppTheme {
        ProfileScreen()
    }
}
