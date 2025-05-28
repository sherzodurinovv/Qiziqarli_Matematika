package com.math.app.ui

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RanglarScreen() {
    val ranglar = listOf(
        Triple("Qizil", Color.Red, 1),
        Triple("Yashil", Color.Green, 2),
        Triple("Ko‘k", Color.Blue, 3),
        Triple("Sariq", Color.Yellow, 4),
        Triple("To‘q sariq", Color(0xFFFFA500), 5),
        Triple("Pushti", Color(0xFFF06292), 6),
        Triple("Qora", Color.Black, 7),
        Triple("Oq", Color.White, 8),
        Triple("Kulrang", Color.Gray, 9)
    )

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ranglar orqali o‘rganamiz!",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(ranglar.size) { index ->
                    RangCard(
                        nomi = ranglar[index].first,
                        rangi = ranglar[index].second,
                        raqam = ranglar[index].third
                    )
                }
            }
        }
    }
}

@Composable
fun RangCard(nomi: String, rangi: Color, raqam: Int) {
    var isClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun playSound(number: Int) {
        try {
            val assetFile = context.assets.openFd("numbers/$number.mp3")
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(assetFile.fileDescriptor, assetFile.startOffset, assetFile.length)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Card(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable {
                isClicked = !isClicked
                playSound(raqam)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(rangi)
                    .shadow(2.dp, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$raqam",
                    color = if (rangi == Color.Black || rangi == Color.Blue) Color.White else Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = nomi,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            AnimatedVisibility(
                visible = isClicked,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = "Bu $nomi rangi! Ichidagi raqam: $raqam",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
