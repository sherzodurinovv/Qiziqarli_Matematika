package com.math.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Yorug' rejim uchun zamonaviy ranglar
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3F51B5), // Chuqur indigo - asosiy rang
    onPrimary = Color.White,
    secondary = Color(0xFFFF7043), // Yorqin to'q sariq - ikkinchi darajali rang
    onSecondary = Color.White,
    tertiary = Color(0xFF26A69A), // Zamonaviy yashil - uchinchi darajali rang
    onTertiary = Color.White,
    background = Color(0xFFECEFF1), // Yumshoq kulrang fon
    onBackground = Color(0xFF212121),
    surface = Color.White,
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFE0E0E0), // Yumshoq kulrang yuzalar
    onSurfaceVariant = Color(0xFF424242)
)

// Tungi rejim uchun zamonaviy ranglar
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5C6BC0), // Ochiqroq indigo - asosiy rang
    onPrimary = Color.White,
    secondary = Color(0xFFFF8A65), // Yumshoq to'q sariq - ikkinchi darajali rang
    onSecondary = Color.Black,
    tertiary = Color(0xFF4DB6AC), // Ochiq yashil - uchinchi darajali rang
    onTertiary = Color.Black,
    background = Color(0xFF121212), // Chuqur qora fon
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E), // Qorong'i yuzasi
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF2C2C2C), // Qorong'i yuzalar uchun variant
    onSurfaceVariant = Color(0xFFB0BEC5)
)