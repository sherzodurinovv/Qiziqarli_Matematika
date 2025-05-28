package com.math.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.math.app.R

data class MenuItem(val title: String, val iconRes: Int?, val route: String)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val menuItems = listOf(
        MenuItem("Sanoq", R.drawable.menu1, "sanoq"),
        MenuItem("Misollar", R.drawable.menu2, "misollar"),
        MenuItem("Shakllar", R.drawable.menu3, "shakllar"),
        MenuItem("Eng katta sonni top!", R.drawable.menu4, "oyin"),
        MenuItem("Ranglar", R.drawable.menu5, "ranglar"),
        MenuItem("Bu qiziq", R.drawable.menu6, "faktlar")
    )

    Column(modifier = modifier
        .padding(10.dp)
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Qiziqarli Matematika",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(menuItems) { item ->
                MenuCard(item = item, onClick = { onNavigate(item.route) })
            }
        }
    }
}

@Composable
fun MenuCard(item: MenuItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (item.iconRes != null) {
                Image(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.title,
                    modifier = Modifier.size(64.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = item.title,
                    modifier = Modifier.size(64.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}

