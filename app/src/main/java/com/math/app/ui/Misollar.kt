package com.math.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun MisollarScreen() {
    var currentQuestion by remember { mutableStateOf(generateQuestion()) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }


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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sarlavha
            Text(
                text = "Matematika Misollari 🧮",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                currentQuestion.options.forEach { option ->
                    val isCorrect = option == currentQuestion.correctAnswer
                    val backgroundColor = when {
                        !isAnswered -> MaterialTheme.colorScheme.surface
                        selectedAnswer == option && isCorrect -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                        selectedAnswer == option && !isCorrect -> MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    AnswerCard(
                        option = option,
                        selectedAnswer = selectedAnswer,
                        backgroundColor = backgroundColor,
                        isAnswered = isAnswered,
                        isCorrect = isCorrect,
                        onClick = {
                            if (!isAnswered) {
                                selectedAnswer = option
                                isAnswered = true
                            }
                        }
                    )
                }
            }


            AnimatedVisibility(
                visible = isAnswered,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut(),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Button(
                    onClick = {
                        currentQuestion = generateQuestion()
                        selectedAnswer = null
                        isAnswered = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "Keyingi Misol",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerCard(
    option: Int,
    selectedAnswer: Int?,
    backgroundColor: Color,
    isAnswered: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable(enabled = !isAnswered) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = option.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
            AnimatedVisibility(
                visible = isAnswered && (selectedAnswer == option),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isCorrect) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}