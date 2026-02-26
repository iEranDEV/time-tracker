package me.narei.time_tracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimeListScreen(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit
) {

    Scaffold() { innerPadding ->

        Column(
            modifier = modifier.padding(innerPadding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text("Time list screen")

            Button(
                onClick = onNavigateToSettings,
            ) {
                Text("Go to settings")
            }

        }

    }

}