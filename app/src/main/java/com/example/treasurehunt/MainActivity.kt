package com.example.treasurehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.treasurehunt.data.PoiRepositoryInterface
import com.example.treasurehunt.ui.compass.CompassScreen
import com.example.treasurehunt.ui.compass.CompassViewModel
import com.example.treasurehunt.ui.congratulations.CongratulationsScreen
import com.example.treasurehunt.ui.congratulations.CongratulationsViewModel
import com.example.treasurehunt.ui.poiselector.TakePictureViewModel
import com.example.treasurehunt.ui.theme.TreasureHuntTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var poiRepository: PoiRepositoryInterface

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
                    val congratulationsViewModel by viewModels<CongratulationsViewModel>()
                    val alldone = congratulationsViewModel.isAllPoiSelected.collectAsState()
                    if (alldone.value) {
                        CongratulationsScreen(true)
                    } else {
                        PermissionBox(
                            permissions = listOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            requiredPermissions = listOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                            description = "We need your location to show you the direction to the closest POI"
                        ) {
                            val compassViewModel by viewModels<CompassViewModel>()
                            val takePictureViewModel by viewModels<TakePictureViewModel>()
                            CompassScreen(compassViewModel, takePictureViewModel)
                        }
                    }


                }
            }
        }
    }
}

