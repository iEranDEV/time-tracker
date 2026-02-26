package me.narei.time_tracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.narei.time_tracker.ui.theme.spacing
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeEntryCard(
    modifier: Modifier = Modifier,
    name: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime
) {

    val cardShape = RoundedCornerShape(12.dp)

    val hourFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    val durationText = remember(startTime, endTime) {
        val diff = Duration.between(startTime, endTime)
        val hours = diff.toHours()
        val minutes = diff.toMinutes()

        "${hours}:${minutes.toString().padStart(2, '0')}"
    }

    val rangeText = remember(startTime, endTime) { "${startTime.format(hourFormatter)} - ${endTime.format(hourFormatter)}" }

    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, cardShape)
            .border(1.dp, Color.LightGray, cardShape)
            .padding(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .background(Color(0xFF2a9d8f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Category icon",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).height(40.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                )
            )
            Text(
                text = rangeText,
                color = Color.Gray,
                fontSize = 12.sp,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                )
            )
        }

        Text(
            text = durationText,
            fontWeight = FontWeight.Bold
        )
    }

}
