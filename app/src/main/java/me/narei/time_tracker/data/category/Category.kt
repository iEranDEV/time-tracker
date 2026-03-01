package me.narei.time_tracker.data.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class Category(
    val displayName: String,
    val icon: ImageVector,
    val color: Color
) {

    EDUCATION( "Education", Icons.Rounded.School, Color.Companion.Red),
    WORK( "Work", Icons.Rounded.Work, Color.Companion.Red)

}