package me.narei.time_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.narei.time_tracker.ui.components.ActionButton
import me.narei.time_tracker.ui.components.SwipeableWithActions
import me.narei.time_tracker.ui.components.TimeEntryCard
import me.narei.time_tracker.ui.theme.TimetrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimetrackerTheme {
                Scaffold() { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(12.dp)
                    ) {
                        SwipeableWithActions(
                            content = {
                                TimeEntryCard()
                            },
                            actions = {
                                ActionButton(
                                    onClick = {},
                                    icon = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    backgroundColor = MaterialTheme.colorScheme.error,
                                    tint = MaterialTheme.colorScheme.onError
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
