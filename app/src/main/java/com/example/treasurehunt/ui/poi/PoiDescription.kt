package com.example.treasurehunt.ui.poi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.treasurehunt.BuildConfig
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.ui.theme.Typography

@Composable
fun PoiDescription(poi: Result<PoiItem?>, modifier: Modifier = Modifier) {

    when (poi) {
        is Result.Error -> {
            Text(text = "Error")
        }

        is Result.Loading -> {
            Text(text = "Loading")
        }

        is Result.Success -> {
            val poiItem = poi.data!!

            val baseUrl = "https://maps.googleapis.com/maps/api/streetview?"
            val params = listOf(
                "location=${poiItem.latitude},${poiItem.longitude}",
                "size=400x400", // Adjust size as needed
                "fov=120", // Adjust field of view (optional)
                "heading=0", // Adjust heading (optional)
                "pitch=0", // Adjust pitch (optional)
                "key=${BuildConfig.MAPS_API_KEY}"
            )
            val imageUrl = baseUrl + params.joinToString("&")
            Column(modifier = modifier.fillMaxHeight()) {
                Text(text = poiItem.name, style = Typography.titleLarge)
                // Spacer(modifier = Modifier.height(24.dp))
                // add coil image
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .build()
                    ),
                    contentScale = androidx.compose.ui.layout.ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier
                        //.fillMaxHeight()
                        .fillMaxWidth()
                )
                //Spacer(modifier = Modifier.height(24.dp))

                Text(text = poiItem.description, style = Typography.bodyLarge)

            }

        }
    }


}

