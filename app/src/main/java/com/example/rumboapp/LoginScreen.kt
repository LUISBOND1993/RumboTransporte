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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val verdeFondo = Color(0xFF1B3011)
    val verdeContenedor = Color(0xFF2D4B1E)
    val colorDorado = Color(0xFFD4AF37)

    Column(modifier = Modifier.fillMaxSize().background(verdeFondo)) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            Image(
                painter = painterResource(id = R.drawable.fondo_bienvenidaapp),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

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
                Text("¡Qué gusto que estés aquí!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(24.dp))

                // Campo Email
                Text("Email:", color = Color.White, modifier = Modifier.align(Alignment.Start), fontSize = 14.sp)
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Contraseña
                Text("Contraseña:", color = Color.White, modifier = Modifier.align(Alignment.Start), fontSize = 14.sp)
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )

                TextButton(onClick = onForgotPasswordClick, modifier = Modifier.align(Alignment.End)) {
                    Text("¿Olvidaste tu contraseña?", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = colorDorado)
                } else {
                    Button(
                        onClick = {
                            if (email.trim().isNotEmpty() && password.isNotEmpty()) {
                                isLoading = true
                                auth.signInWithEmailAndPassword(email.trim(), password)
                                    .addOnSuccessListener {
                                        isLoading = false
                                        onLoginSuccess()
                                    }
                                    .addOnFailureListener { e ->
                                        isLoading = false
                                        // Manejo de errores amigable
                                        val errorMsg = when {
                                            e.message?.contains("network") == true -> "Sin conexión a internet"
                                            e.message?.contains("password") == true -> "Contraseña incorrecta"
                                            else -> "Error: ${e.localizedMessage}"
                                        }
                                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                                    }
                            } else {
                                Toast.makeText(context, "Ingresa email y contraseña", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorDorado),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Iniciar Sesión", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(onClick = onRegisterClick) {
                    Text("¿No tienes cuenta? Regístrate", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}