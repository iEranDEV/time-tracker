package me.narei.time_tracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimeEntryCard(
    modifier: Modifier = Modifier
) {

    val cardShape = RoundedCornerShape(8.dp)


    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Blue, cardShape)
            .border(1.dp, Color.LightGray, cardShape)
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .background(Color.Green)
            )
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
            Text("Time entry card")
        }
    }

}