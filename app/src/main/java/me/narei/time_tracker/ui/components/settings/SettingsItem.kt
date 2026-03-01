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
import androidx.compose.ui.unit.dp
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
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        ) {
            content()
        }
    }
}