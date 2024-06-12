package com.example.treasurehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.treasurehunt.data.PoiRepositoryInterface
import com.example.treasurehunt.ui.congratulations.CongratulationsScreen
import com.example.treasurehunt.ui.congratulations.CongratulationsViewModel
import com.example.treasurehunt.ui.mapfeature.MapScreen
import com.example.treasurehunt.ui.mapfeature.MapViewModel
import com.example.treasurehunt.ui.poi.PoiDescription
import com.example.treasurehunt.ui.poi.TakePictureViewModel
import com.example.treasurehunt.ui.theme.TreasureHuntTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
                    val gameProgress = congratulationsViewModel.gameProgress.collectAsState().value
                    val alldone = congratulationsViewModel.isAllPoiSelected.collectAsState()
                    if (alldone.value) {
                        CongratulationsScreen { congratulationsViewModel.restartGame() }
                    } else {
                        PermissionBox(
                            permissions = listOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            requiredPermissions = listOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                            description = "We need your location to show you the direction to the closest POI"
                        ) {
                            val mapViewModel by viewModels<MapViewModel>()
                            val takePictureViewModel by viewModels<TakePictureViewModel>()

                            val closestPoiItem = mapViewModel.closestPoiItem.collectAsState().value

                            val scaffoldState = rememberBottomSheetScaffoldState(
                                bottomSheetState = rememberStandardBottomSheetState(
                                    initialValue = SheetValue.Expanded
                                )
                            )

                            Box {
                                BottomSheetScaffold(
                                    modifier = Modifier.fillMaxSize(),
                                    sheetContent = {
                                        Box(
                                            modifier = Modifier
                                                .height((LocalConfiguration.current.screenHeightDp / 2).dp)
                                        ) {
                                            MapScreen(mapViewModel)
                                        }
                                    },
                                    scaffoldState = scaffoldState
                                ) {
                                    Scaffold(
                                        // show the progress bar of the remaining POIS
                                        topBar = {
                                            LinearProgressIndicator(
                                                progress = {
                                                    if (gameProgress.second == 0) 0f
                                                    else
                                                    (gameProgress.first.toFloat() / gameProgress.second.toFloat())
                                                }, modifier = Modifier
                                                    .height(24.dp)
                                                    .fillMaxWidth(),
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    ) {

                                        PoiDescription(
                                            closestPoiItem,
                                            Modifier.padding(it)
                                        )

                                    }
                                }
                                FloatingActionButton(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.BottomEnd),
                                    onClick = { takePictureViewModel.selectPoi(closestPoiItem) }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.camera_marker),
                                        contentDescription = "Take Photo"
                                    )
                                }

                            }
                        }
                    }

                }
            }
        }
    }
}

