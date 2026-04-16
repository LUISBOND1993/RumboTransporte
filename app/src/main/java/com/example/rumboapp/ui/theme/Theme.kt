package com.example.rumboapp.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 1. Configuración del Esquema Oscuro (El que usas por defecto)
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    // --- SOLUCIÓN GLOBAL PARA TEXTO EN INPUTS ---
    // onSurface controla el color del texto que escribes en los TextField
    onSurface = Color.Black,
    // onSurfaceVariant controla el color de los placeholders (ej. "ejemplo@correo.com")
    onSurfaceVariant = Color.DarkGray,
    // surface controla el fondo por defecto si no lo especificas
    surface = Color(0xFFE2E2BD)
)

// 2. Configuración del Esquema Claro (Opcional)
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSurface = Color.Black
)

@Composable
fun RumboAppTheme(
    // Forzamos a que sea siempre oscuro según tu preferencia
    darkTheme: Boolean = true,
    // Desactivamos dynamicColor para que el sistema no sobrescriba tus colores en Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Si quisieras colores dinámicos de Android 12+, esto los activaría
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}