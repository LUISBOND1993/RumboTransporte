package com.example.rumboapp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rumboapp.ui.theme.RumboAppTheme

// ─────────────────────────────────────────────
// 4. PANTALLA SELECTOR DE FOTO
// ─────────────────────────────────────────────
@Composable
fun PhotoPickerScreen(
    isLoading: Boolean = false,
    onBackClick: () -> Unit = {},
    onPhotoSelected: (Uri?) -> Unit = {}
) {
    val VerdeFondo   = Color(0xFF1B3011)
    val ColorDorado  = Color(0xFFD4AF37)

    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo),
        horizontalAlignment = Alignment.CenterHorizontally
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
        }

        Spacer(modifier = Modifier.height(40.dp))

        // ── Vista previa de la foto ────────────────────
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF4A7C59))
                .border(2.dp, Color.White, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (selectedUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedUri),
                    contentDescription = "Foto seleccionada",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                    contentDescription = "Galería",
                    tint = Color.White,
                    modifier = Modifier.size(72.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        if (isLoading) {
            CircularProgressIndicator(color = ColorDorado)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Subiendo foto...", color = Color.White, fontSize = 14.sp)
        } else {
            Text(
                text = "Selecciona tu foto",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Elige una imagen de tu galería\npara actualizar tu foto de perfil.",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Botón abrir galería
        Button(
            onClick = { galleryLauncher.launch("image/*") },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
            shape = RoundedCornerShape(28.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Text(
                text = "Seleccionar foto",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón confirmar (solo activo si hay foto)
        Button(
            onClick = { onPhotoSelected(selectedUri) },
            enabled = selectedUri != null && !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A7C59),
                disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(28.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Text(
                text = "Confirmar foto",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Cancelar
        OutlinedButton(
            onClick = onBackClick,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Cancelar",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}

// ─────────────────────────────────────────────
// 5. PANTALLA DATOS ACTUALIZADOS (Confirmación)
// ─────────────────────────────────────────────
@Composable
fun ProfileUpdatedScreen(
    onContinueClick: () -> Unit = {}
) {
    val VerdeFondo  = Color(0xFF1B3011)
    val ColorDorado = Color(0xFFD4AF37)
    val VerdeCheck  = Color(0xFF4A7C59)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Círculo con check
        Surface(
            modifier = Modifier.size(110.dp),
            shape = CircleShape,
            color = VerdeCheck
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = android.R.drawable.checkbox_on_background),
                    contentDescription = "Datos actualizados",
                    tint = Color.White,
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "DATOS ACTUALIZADOS",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.5.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Tu información ha sido guardada\nexitosamente.",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(44.dp))

        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
            shape = RoundedCornerShape(28.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Text(
                text = "Continuar",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

// ── Previews ───────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PhotoPickerPreview() {
    RumboAppTheme {
        PhotoPickerScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileUpdatedPreview() {
    RumboAppTheme {
        ProfileUpdatedScreen()
    }
}
