package com.example.trackstack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackStackApp()
        }
    }
}

@Composable
fun TrackStackApp() {
    MaterialTheme {
        Surface {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {
    Text("Hello, TrackStack!")
}

@Preview
@Composable
fun GreetingPreview() {
    TrackStackApp()
}
