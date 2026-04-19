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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme

// ─────────────────────────────────────────────
// 2. PANTALLA EDITAR DATOS PERSONALES
// ─────────────────────────────────────────────
@Composable
fun EditProfileScreen(
    nombreInicial: String = "María Gomez",
    telefonoInicial: String = "3200001740",
    emailInicial: String = "anne_admin@gmail.com",
    onBackClick: () -> Unit = {},
    onGuardarClick: (String, String, String) -> Unit = { _, _, _ -> }
) {
    val VerdeFondo      = Color(0xFF1B3011)
    val VerdeContenedor = Color(0xFF2D4B1E)
    val ColorDorado     = Color(0xFFD4AF37)
    val ColorCremita    = Color(0xFFE2E2BD)

    var nombre   by remember { mutableStateOf(nombreInicial) }
    var telefono by remember { mutableStateOf(telefonoInicial) }
    var email    by remember { mutableStateOf(emailInicial) }

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
                        painter = androidx.compose.ui.res.painterResource(
                            id = android.R.drawable.ic_menu_revert
                        ),
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

        // ── Formulario ───────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            CampoEdicionPerfil(
                titulo = "Nombre:",
                valor = nombre,
                alCambiar = { nombre = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Teléfono:",
                valor = telefono,
                alCambiar = { telefono = it },
                colorCremita = ColorCremita
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoEdicionPerfil(
                titulo = "Email:",
                valor = email,
                alCambiar = { email = it },
                colorCremita = ColorCremita
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón Guardar Cambios
            Button(
                onClick = { onGuardarClick(nombre, telefono, email) },
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

            // Botón Cancelar
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

// Campo reutilizable para los formularios de edición de perfil
@Composable
fun CampoEdicionPerfil(
    titulo: String,
    valor: String,
    alCambiar: (String) -> Unit,
    colorCremita: Color = Color(0xFFE2E2BD),
    esContrasena: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = titulo,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorCremita,
                unfocusedContainerColor = colorCremita,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            visualTransformation = if (esContrasena) PasswordVisualTransformation()
                                   else androidx.compose.ui.text.input.VisualTransformation.None
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfilePreview() {
    RumboAppTheme {
        EditProfileScreen()
    }
}
