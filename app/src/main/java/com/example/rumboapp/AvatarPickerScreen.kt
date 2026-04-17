package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme

@Composable
fun AvatarPickerScreen(
    onBackClick: () -> Unit = {},
    onAvatarSelected: (String) -> Unit = {}
) {
    val VerdeFondo = Color(0xFF1B3011)
    val ColorDorado = Color(0xFFD4AF37)
    
    // Lista de avatares mejorada (puedes reemplazarlos por tus propios drawables)
    val avatars = listOf(
        "avatar_1" to android.R.drawable.ic_menu_gallery,
        "avatar_2" to android.R.drawable.ic_menu_camera,
        "avatar_3" to android.R.drawable.ic_menu_agenda,
        "avatar_4" to android.R.drawable.ic_menu_call,
        "avatar_5" to android.R.drawable.ic_menu_day,
        "avatar_6" to android.R.drawable.ic_menu_myplaces
    )

    var selectedAvatar by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado
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
                text = "Elegir Avatar",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(avatars) { (name, resId) ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(if (selectedAvatar == name) ColorDorado else Color.White.copy(alpha = 0.1f))
                        .border(
                            width = if (selectedAvatar == name) 3.dp else 1.dp,
                            color = if (selectedAvatar == name) Color.White else Color.White.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .clickable { selectedAvatar = name },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = resId),
                        contentDescription = name,
                        tint = if (selectedAvatar == name) Color.Black else Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }

        Button(
            onClick = { 
                if (selectedAvatar.isNotEmpty()) {
                    onAvatarSelected(selectedAvatar)
                }
            },
            enabled = selectedAvatar.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 32.dp)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Confirmar Avatar", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
