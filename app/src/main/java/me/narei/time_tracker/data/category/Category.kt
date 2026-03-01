package me.narei.time_tracker.data.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.SportsSoccer
import androidx.compose.material.icons.rounded.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class Category(
    val displayName: String,
    val icon: ImageVector,
    val color: Color
) {

    EDUCATION( "Education", Icons.Rounded.School, Color(0xFF5C6BC0) ),
    WORK( "Work", Icons.Rounded.Work, Color(0xFFEF5350) ),
    HOBBY( "Hobby", Icons.Rounded.AutoAwesome, Color(0xFFAB47BC) ),
    SPORT( "Sport", Icons.Rounded.SportsSoccer, Color(0xFF66BB6A) ),
    CODING( "Coding", Icons.Rounded.Code, Color(0xFF29B6F6)),
    SOCIAL( "Social", Icons.Rounded.People, Color(0xFFFFA726)),
    CHORES( "Household chores", Icons.Rounded.House, Color(0xFF8D6E63) ),
    OTHER( "Other", Icons.Rounded.Interests, Color(0xFF78909C) )

}