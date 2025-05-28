package com.math.app.ui

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.math.app.R

@Composable
fun SanoqScreen() {
    val context = LocalContext.current
    val numbers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    val numberImages = mapOf(
        "1" to R.drawable.one,
        "2" to R.drawable.two,
        "3" to R.drawable.three,
        "4" to R.drawable.four,
        "5" to R.drawable.five,
        "6" to R.drawable.six,
        "7" to R.drawable.seven,
        "8" to R.drawable.eight,
        "9" to R.drawable.nine
    )
    val pagerState = rememberPagerState()
    var isPlaying by remember { mutableStateOf(false) }


    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.release()
        }
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
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Sanoqni O‘rganamiz! 🔢",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )


            HorizontalPager(
                count = numbers.size,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) { page ->
                val number = numbers[page]
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)
                        .shadow(4.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = numberImages[number] ?: R.drawable.one),
                            contentDescription = "Number $number",
                            modifier = Modifier
                                .size(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = number,
                            style = MaterialTheme.typography.displayLarge,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        AnimatedVisibility(
                            visible = isPlaying && pagerState.currentPage == page,
                            enter = scaleIn() + fadeIn(),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = "Ovoz ijro etilmoqda...",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                playNumberSound(context, number, mediaPlayer) { playing ->
                                    isPlaying = playing
                                }
                            },
                            modifier = Modifier
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ovoz",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }


            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(16.dp),
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )
        }
    }
}

private fun playNumberSound(
    context: Context,
    number: String,
    mediaPlayer: MutableState<MediaPlayer?>,
    onPlayingStateChanged: (Boolean) -> Unit
) {
    mediaPlayer.value?.release()
    try {
        val assetFileDescriptor = context.assets.openFd("numbers/$number.mp3")
        val player = MediaPlayer().apply {
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            prepare()
            start()
            onPlayingStateChanged(true)
            setOnCompletionListener {
                onPlayingStateChanged(false)
                release()
                mediaPlayer.value = null
            }
        }
        mediaPlayer.value = player
    } catch (e: Exception) {
        onPlayingStateChanged(false)

    }
}