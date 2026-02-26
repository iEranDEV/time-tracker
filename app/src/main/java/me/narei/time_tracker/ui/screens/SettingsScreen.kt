package me.narei.time_tracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {

    Scaffold() { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding).padding(20.dp)
        ) {

            Text("Settings")

        }
    }

}