package com.example.rumboapp

// IMPORTACIONES
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Colores personalizados
private val VerdeOscuro = Color(0xFF1B3011)
private val ColorCremita = Color(0xFFE2E2BD)
private val ColorDorado = Color(0xFFD4AF37)

@Composable
fun RegistroScreen(onBackClick: () -> Unit, onCreateAccountClick: () -> Unit) {
    val context = LocalContext.current

    // Estados para los campos
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmaContrasena by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var identificacion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_registro),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(color = VerdeOscuro, shape = RoundedCornerShape(45.dp))
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "¡REGÍSTRATE", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Text(text = "CON NOSOTROS!", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(24.dp))

                    CampoDeTextoRegistro(titulo = "Nombre Completo:", valor = nombre, alCambiar = { nombre = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoDeTextoRegistro(titulo = "*Ingresa tu email :", valor = email, alCambiar = { email = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoDeTextoRegistro(titulo = "*Ingresa tu contraseña :", valor = contrasena, alCambiar = { contrasena = it }, esContrasena = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoDeTextoRegistro(titulo = "*Confirma nuevamente tu contraseña:", valor = confirmaContrasena, alCambiar = { confirmaContrasena = it }, esContrasena = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoDeTextoRegistro(titulo = "*Ingresa tu Teléfono :", valor = telefono, alCambiar = { telefono = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoDeTextoRegistro(titulo = "Fecha de nacimiento :", valor = fechaNacimiento, alCambiar = { fechaNacimiento = it })
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoDeTextoRegistro(titulo = "¿Cómo te identificas?", valor = identificacion, alCambiar = { identificacion = it })

                    Spacer(modifier = Modifier.height(40.dp))

                    if (isLoading) {
                        CircularProgressIndicator(color = ColorDorado)
                    } else {
                        Button(
                            onClick = {
                                if (email.isEmpty() || contrasena.isEmpty() || nombre.isEmpty()) {
                                    Toast.makeText(context, "Llenar campos obligatorios (*)", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (contrasena != confirmaContrasena) {
                                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                isLoading = true

                                // Lógica de Firebase
                                val auth = FirebaseAuth.getInstance()
                                val db = FirebaseFirestore.getInstance()

                                auth.createUserWithEmailAndPassword(email.trim(), contrasena)
                                    .addOnSuccessListener { authResult ->
                                        val uid = authResult.user?.uid ?: ""
                                        val usuarioMap = hashMapOf(
                                            "uid" to uid,
                                            "nombre" to nombre,
                                            "email" to email.trim(),
                                            "telefono" to telefono,
                                            "fechaNacimiento" to fechaNacimiento,
                                            "identificacion" to identificacion,
                                            "rol" to "viajero",
                                            "fechaCreacion" to com.google.firebase.Timestamp.now()
                                        )

                                        db.collection("usuarios").document(uid)
                                            .set(usuarioMap)
                                            .addOnSuccessListener {
                                                isLoading = false
                                                onCreateAccountClick()
                                            }
                                            .addOnFailureListener { e ->
                                                isLoading = false
                                                Toast.makeText(context, "Error DB: ${e.message}", Toast.LENGTH_LONG).show()
                                            }
                                    }
                                    .addOnFailureListener { e ->
                                        isLoading = false
                                        Toast.makeText(context, "Error Registro: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                            },
                            modifier = Modifier.fillMaxWidth().height(60.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Crear Cuenta", color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                        }
                    }
                }
            }
        }

        // Botón de atrás circular
        Surface(
            modifier = Modifier.padding(top = 40.dp, start = 20.dp).size(45.dp),
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

@Composable
fun CampoDeTextoRegistro(titulo: String, valor: String, alCambiar: (String) -> Unit, esContrasena: Boolean = false) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = titulo, color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(bottom = 4.dp), textAlign = TextAlign.Start)
        TextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ColorCremita,
                unfocusedContainerColor = ColorCremita,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            visualTransformation = if (esContrasena) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true
        )
    }
}

// --- VISTA PREVIA PARA EL PANEL SPLIT ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroScreenPreview() {
    RegistroScreen(onBackClick = {}, onCreateAccountClick = {})
}