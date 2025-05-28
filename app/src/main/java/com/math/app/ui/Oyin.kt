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
fun OyinScreen() {
    var numbers by remember { mutableStateOf(generateNumbers()) }
    var clickedIndex by remember { mutableStateOf<Int?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    val maxNumber = numbers.maxOrNull()

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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sarlavha
            Text(
                text = "Eng Katta Sonni Toping! 🎯",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                numbers.forEachIndexed { index, number ->
                    val backgroundColor = when {
                        clickedIndex == null -> MaterialTheme.colorScheme.surface
                        index == clickedIndex && number == maxNumber -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                        index == clickedIndex && number != maxNumber -> MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    NumberCard(
                        number = number,
                        index = index,
                        backgroundColor = backgroundColor,
                        clickedIndex = clickedIndex,
                        maxNumber = maxNumber,
                        onClick = {
                            if (clickedIndex == null) {
                                clickedIndex = index
                                isCorrect = (number == maxNumber)
                            }
                        }
                    )
                }
            }


            AnimatedVisibility(
                visible = clickedIndex != null,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut(),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = when {
                            isCorrect == true -> "Barakalla! 🎉"
                            isCorrect == false -> "Noto‘g‘ri. 😕 Yana urinib ko‘ring!"
                            else -> ""
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isCorrect == true) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Button(
                        onClick = {
                            numbers = generateNumbers()
                            clickedIndex = null
                            isCorrect = null
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
                            text = "Yana O‘ynash",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NumberCard(
    number: Int,
    index: Int,
    backgroundColor: Color,
    clickedIndex: Int?,
    maxNumber: Int?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable(enabled = clickedIndex == null) { onClick() },
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
                text = number.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            AnimatedVisibility(
                visible = clickedIndex == index,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    imageVector = if (number == maxNumber) Icons.Default.CheckCircle else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (number == maxNumber) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

fun generateNumbers(): List<Int> {
    val list = mutableSetOf<Int>()
    while (list.size < 4) {
        list += Random.nextInt(1, 100)
    }
    return list.shuffled()
}

data class Question(
    val question: String,
    val correctAnswer: Int,
    val options: List<Int>
)

fun generateQuestion(): Question {
    val a = Random.nextInt(1, 20)
    val b = Random.nextInt(1, 20)
    val correct = a + b
    val options = mutableSetOf(correct)
    while (options.size < 4) {
        options += Random.nextInt(correct - 5, correct + 6)
    }
    return Question(
        question = "$a + $b = ?",
        correctAnswer = correct,
        options = options.shuffled()
    )
}