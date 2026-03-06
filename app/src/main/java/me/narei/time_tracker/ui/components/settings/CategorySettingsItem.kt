package me.narei.time_tracker.ui.components.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.narei.time_tracker.data.category.Category
import me.narei.time_tracker.ui.components.shared.CategoryIcon
import me.narei.time_tracker.ui.theme.spacing

@Composable
fun CategorySettingsItem(
    modifier: Modifier = Modifier,
    category: Category,
    isHidden: Boolean,
    toggleHidden: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isHidden) 0.5f else 1f)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                if (category != Category.OTHER) toggleHidden()
            }
            .padding(MaterialTheme.spacing.extraSmall),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        CategoryIcon(category = category)

        Text(
            modifier = Modifier.fillMaxWidth().weight(1f),
            text = category.displayName,
            style = MaterialTheme.typography.titleMedium
        )

        if (category != Category.OTHER) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                if (isHidden) {
                    Icon(
                        imageVector = Icons.Outlined.VisibilityOff,
                        contentDescription = "Show category",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Visibility,
                        contentDescription = "Hide category",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }

}