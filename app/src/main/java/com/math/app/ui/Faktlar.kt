package com.math.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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

@Composable
fun FaktlarScreen() {

    val faktlar = listOf(
        Pair("Nol shunday sonki, uni hech narsaga qo‘shmasangiz, o‘zgarmaydi! 😊",
            "Masalan, 3 + 0 = 3. Uyda o‘yinchoqlaringizni sanasangiz, nol qo‘shsangiz, ular soni o‘zgarmaydi!"),
        Pair("1 dan boshlab sanash oson, bu natural sonlar deb ataladi! 🌟",
            "Daraxtdagi mevalarni sanasangiz, 1, 2, 3… deb boshlaysiz. Bu natural sonlar!"),
        Pair("To‘rtburchakning to‘rtta tomoni bor, masalan, kitob shunday shakl! 📚",
            "Kitobingizni qo‘lingizga oling, uning shakli to‘rtburchak. To‘rtta tomonini sanab ko‘ring!"),
        Pair("Uchburchakning uchta burchagi bor, u pizza bo‘lagiga o‘xshaydi! 🍕",
            "Pizzani kesganda uchburchak shakl chiqadi. Uchta burchagini topa olasizmi?"),
        Pair("Doira yumaloq, u quyosh yoki soatga o‘xshaydi! ☀️",
            "Soatingizga qarang, u doira shaklida! Yumaloq narsalarni uyingizda qidirib ko‘ring."),
        Pair("Raqamlar cheksiz, ularga doim birni qo‘shsa bo‘ladi! 🚀",
            "Yulduzlarni sanasangiz, ularga doim yangi yulduz qo‘shsa bo‘ladi!"),
        Pair("Matematika o‘yinlari aqlni charxlaydi va o‘rganishni qiziq qiladi! 🎲",
            "Raqamli o‘yin o‘ynasangiz, matematika oson bo‘lib qoladi. Bugun o‘ynab ko‘ring!")
    )


    var selectedFakt by remember { mutableStateOf<String?>(null) }


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
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                )
            )
            .padding(horizontal = horizontalPadding, vertical = 16.dp)
            .systemBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            item {
                Text(
                    text = "Bu juda qiziq! ✨",
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


            items(faktlar) { (fakt, izoh) ->
                FactCard(
                    fakt = fakt,
                    izoh = izoh,
                    isExpanded = fakt == selectedFakt,
                    onClick = {
                        selectedFakt = if (selectedFakt == fakt) null else fakt
                    }
                )
            }
        }
    }
}

@Composable
fun FactCard(
    fakt: String,
    izoh: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = fakt,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = izoh,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}