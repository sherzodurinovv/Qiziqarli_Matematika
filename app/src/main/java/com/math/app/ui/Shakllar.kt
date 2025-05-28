package com.math.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.math.app.R

@Composable
fun ShakllarScreen() {

    val shapes = listOf(
        Triple("Doira", R.drawable.doira, MaterialTheme.colorScheme.error),
        Triple("Kesik Piramida", R.drawable.kesik_piramida, Color(0xFF6200EA)),
        Triple("Konus", R.drawable.konus, Color(0xFF0288D1)),
        Triple("Kub", R.drawable.kub, MaterialTheme.colorScheme.primary),
        Triple("Parallapiped", R.drawable.parallapepid, Color(0xFF388E3C)),
        Triple("Piramida", R.drawable.piramida, Color(0xFFD81B60)),
        Triple("Shar", R.drawable.shar, Color(0xFFFFA000)),
        Triple("Silindr", R.drawable.silindr, Color(0xFF455A64)),
        Triple("Trapetsiya", R.drawable.trapetsiya, Color(0xFF1976D2)),
        Triple("To‘rtburchak", R.drawable.turtburchak, MaterialTheme.colorScheme.tertiary),
        Triple("Uchburchak", R.drawable.uchburchak, Color(0xFFF06292))
    )


    val horizontalPadding = when {
        androidx.compose.ui.platform.LocalConfiguration.current.screenWidthDp < 600 -> 16.dp
        else -> 24.dp
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    )
                )
            )
            .padding(horizontal = horizontalPadding, vertical = 16.dp)
            .systemBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            item {
                Text(
                    text = "Shakllarni O‘rganamiz! 📐",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }


            items(shapes.size) { index ->
                val (shapeName, drawableId, shapeColor) = shapes[index]
                ShapeCard(
                    shapeName = shapeName,
                    shapeColor = shapeColor,
                    drawableId = drawableId
                )
            }
        }
    }
}

@Composable
fun ShapeCard(
    shapeName: String,
    shapeColor: Color,
    drawableId: Int
) {
    var isClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable { isClicked = !isClicked },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = shapeName,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(2.dp, RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = shapeName,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = shapeColor
            )
            AnimatedVisibility(
                visible = isClicked,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = "Bu $shapeName shakli!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}