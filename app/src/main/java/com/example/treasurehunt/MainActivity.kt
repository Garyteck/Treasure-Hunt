package com.example.treasurehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.treasurehunt.ui.compass.CompassScreen
import com.example.treasurehunt.ui.compass.CompassViewModel
import com.example.treasurehunt.ui.mapfeature.MapScreen
import com.example.treasurehunt.ui.mapfeature.MapViewModel
import com.example.treasurehunt.ui.theme.TreasureHuntTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreasureHuntTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val pagerState = rememberPagerState(pageCount = {
                        2
                    })

                    HorizontalPager(state = pagerState) {
                        when (it) {
                            0 -> {
                                val mapViewModel by viewModels<MapViewModel>()
                                MapScreen(mapViewModel)                            }
                            1 -> {
                                PermissionBox(
                                    permissions = listOf(
                                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                                    ),
                                    requiredPermissions = listOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                                    description = "We need your location to show you the direction to the closest POI"
                                ) {
                                    val compassViewModel by viewModels<CompassViewModel>()
                                    CompassScreen(compassViewModel)
                                }
                            }
                        }
                    }



                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TreasureHuntTheme {
        Greeting("Android")
    }
}