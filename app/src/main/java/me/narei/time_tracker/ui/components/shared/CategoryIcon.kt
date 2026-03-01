package me.narei.time_tracker.ui.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.narei.time_tracker.data.category.Category

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    category: Category
) {

    Box(
        modifier = modifier
            .size(40.dp)
            .background(category.color, MaterialTheme.shapes.small),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = category.icon,
            contentDescription = "Category ${category.displayName}",
            tint = Color.White
        )
    }

}