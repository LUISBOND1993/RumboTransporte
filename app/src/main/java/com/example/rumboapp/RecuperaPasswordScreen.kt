package com.example.rumboapp

// IMPORTACIÓN DE FIREBASE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RecuperaPasswordScreen(
    onBackClick: () -> Unit,
    onNavigateToConfirmation: () -> Unit // Cambiamos el nombre para que sea más claro
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_cambio_contrasena),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f),
            contentScale = ContentScale.Crop
        )

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

            Text(text = "¡RECUPERA TU", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
            Text(text = "CONTRASEÑA!", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)

            Spacer(modifier = Modifier.height(25.dp))

            Surface(
                color = Color(0xFFD9D9B3),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Para recuperar el acceso a tu cuenta, te enviaremos un enlace a tu correo.",
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    lineHeight = 18.sp,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(text = "Ingresa el correo electrónico asociado a tu cuenta", color = Color.White, textAlign = TextAlign.Center, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("ejemplo@correo.com", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth().height(55.dp),
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

            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFFC4A454))
            } else {
                Button(
                    onClick = {
                        if (email.isNotEmpty()) {
                            isLoading = true
                            // LÓGICA REAL DE FIREBASE
                            auth.sendPasswordResetEmail(email.trim())
                                .addOnSuccessListener {
                                    isLoading = false
                                    onNavigateToConfirmation() // Nos lleva a la vista de "Correo Enviado"
                                }
                                .addOnFailureListener { e ->
                                    isLoading = false
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            Toast.makeText(context, "Por favor ingresa tu correo", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.8f).height(65.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4A454)),
                    shape = RoundedCornerShape(40.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Text("Enviar Enlace", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }

        Surface(
            modifier = Modifier.padding(top = 50.dp, start = 20.dp).size(45.dp),
            color = Color.White.copy(alpha = 0.8f),
            shape = RoundedCornerShape(50.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(painter = painterResource(id = android.R.drawable.ic_menu_revert), contentDescription = "Atrás", tint = Color.Black)
            }
        }
    }
}