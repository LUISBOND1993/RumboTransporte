package com.example.rumboapp

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme
// IMPORTACIONES NECESARIAS PARA FIREBASE
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit // Nueva acción para cuando el login sea correcto
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // 1. MANEJO DE ESTADOS (Ahora los campos son editables)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val verdeFondo = Color(0xFF1B3011)
    val verdeContenedor = Color(0xFF2D4B1E)

    Column(modifier = Modifier.fillMaxSize().background(verdeFondo)) {
        // Parte Superior: Imagen
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.White)) {
            Image(
                painter = painterResource(id = R.drawable.fondo_bienvenidaapp),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Parte Inferior: Contenedor Verde
        Surface(
            modifier = Modifier.fillMaxWidth().weight(1.8f).offset(y = (-40).dp),
            color = verdeContenedor,
            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "¡Qué gusto que estés aquí!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(24.dp))

                // Campo Email
                Text(text = "Email:", color = Color.White, modifier = Modifier.align(Alignment.Start), fontSize = 14.sp)
                TextField(
                    value = email, // Estado vinculado
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Contraseña
                Text(text = "Contraseña:", color = Color.White, modifier = Modifier.align(Alignment.Start), fontSize = 14.sp)
                TextField(
                    value = password, // Estado vinculado
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                TextButton(onClick = onForgotPasswordClick, modifier = Modifier.align(Alignment.End)) {
                    Text(text = "¿Olvidaste tu contraseña?", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 2. INYECCIÓN DE LÓGICA DE FIREBASE Y MANEJO DE ERRORES
                if (isLoading) {
                    CircularProgressIndicator(color = Color(0xFFD4AF37))
                } else {
                    Button(
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                isLoading = true
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnSuccessListener {
                                        isLoading = false
                                        onLoginSuccess() // Navegar a la siguiente pantalla
                                    }
                                    .addOnFailureListener { e ->
                                        isLoading = false
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(context, "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Iniciar Sesión", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(onClick = onRegisterClick) {
                    Text(text = "¿No tienes cuenta? Regístrate", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    RumboAppTheme {
        LoginScreen(onForgotPasswordClick = {}, onRegisterClick = {}, onLoginSuccess = {})
    }
}