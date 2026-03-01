package me.narei.time_tracker.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.narei.time_tracker.ui.theme.spacing

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.medium)
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium)
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium
        )

        if (description != null) {
            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        ) {
            content()
        }
    }
}