package me.narei.time_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.narei.time_tracker.ui.screens.NavigationRoot
import me.narei.time_tracker.ui.theme.TimetrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            TimetrackerTheme {
                NavigationRoot()
            }
        }
    }
}
