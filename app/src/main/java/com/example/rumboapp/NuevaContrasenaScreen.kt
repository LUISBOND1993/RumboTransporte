package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme

@Composable
fun NuevaContrasenaScreen(onBackClick: () -> Unit, onConfirmPasswordClick: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Imagen de Fondo (La ciudad/pueblo)
        Image(
            painter = painterResource(id = R.drawable.fondo_cambio_contrasena),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.Crop
        )

        // 2. Contenedor Verde Oscuro
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 350.dp)
                .offset(y = (-30).dp)
                .background(
                    color = Color(0xFF2D4B1E), // Verde oscuro
                    shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
                )
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Título
            Text(
                text = "CONTRASEÑA",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
            Text(
                text = "NUEVA",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Burbuja informativa (Cremita)
            Surface(
                color = Color(0xFFD9D9B3),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Ahora puedes crear una nueva contraseña segura para tu cuenta.",
                    modifier = Modifier.padding(15.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    lineHeight = 18.sp,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Campo: Nueva Contraseña
            Text(
                text = "Nueva contraseña:",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start),
                fontSize = 14.sp
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9B3),
                    unfocusedContainerColor = Color(0xFFD9D9B3),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Campo: Confirmar Contraseña
            Text(
                text = "Confirmar contraseña:",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start),
                fontSize = 14.sp
            )
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9B3),
                    unfocusedContainerColor = Color(0xFFD9D9B3),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(35.dp))

            // Botón Confirmar (Dorado/Mostaza)
            Button(
                onClick = onConfirmPasswordClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(65.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4A454)),
                shape = RoundedCornerShape(40.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = "Confirmar\nContraseña",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }

        // 3. Botón Regresar
        Surface(
            modifier = Modifier
                .padding(top = 50.dp, start = 20.dp)
                .size(45.dp),
            color = Color.White.copy(alpha = 0.8f),
            shape = RoundedCornerShape(50.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_revert),
                    contentDescription = "Atrás",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NuevaContrasenaPreview() {
    RumboAppTheme {
        NuevaContrasenaScreen(onBackClick = {}, onConfirmPasswordClick = {})
    }
}