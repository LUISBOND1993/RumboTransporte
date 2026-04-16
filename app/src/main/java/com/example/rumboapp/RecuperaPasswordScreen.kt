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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme

@Composable
fun RecuperaPasswordScreen(
    onBackClick: () -> Unit,
    onSendCodeClick: (String) -> Unit
) {
    // Estado del campo de texto
    var email by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Imagen de Fondo
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
                    color = Color(0xFF2D4B1E),
                    shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
                )
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "¡RECUPERA TU",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
            Text(
                text = "CONTRASEÑA!",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            Surface(
                color = Color(0xFFD9D9B3),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Para recuperar el acceso a tu cuenta, necesitamos verificar tu identidad.",
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    lineHeight = 18.sp,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Ingresa el correo electrónico asociado a tu cuenta",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("ejemplo@correo.com", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9B3),
                    unfocusedContainerColor = Color(0xFFD9D9B3),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty()) {
                        onSendCodeClick(email)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(65.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4A454)),
                shape = RoundedCornerShape(40.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = "Enviar\nCódigo",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }

        // 3. Botón de Regresar
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecuperaPasswordPreview() {
    RumboAppTheme {
        RecuperaPasswordScreen(
            onBackClick = { },
            onSendCodeClick = { }
        )
    }
}