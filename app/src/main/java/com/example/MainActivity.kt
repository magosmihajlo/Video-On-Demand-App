package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.presentation.viewmodel.VodViewModel
import com.example.data.remote.RetrofitInstance
import com.example.presentation.navigation.AppNavigationGraph
import com.example.vod.BuildConfig
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    AppNavigationGraph(navController = navController)
                }
            }
        }
    }
}
