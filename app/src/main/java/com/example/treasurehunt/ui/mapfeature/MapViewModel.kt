package com.example.treasurehunt.ui.mapfeature

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.data.model.UserLocation
import com.example.treasurehunt.domain.GetClosestUnselectedPoiUseCase
import com.google.android.gms.maps.LocationSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    getClosestUnselectedPoiUseCase: GetClosestUnselectedPoiUseCase,
    userLocationFlow: Flow<UserLocation>
) : ViewModel() {

    private val _closestPoiItem = MutableStateFlow<Result<PoiItem?>>(Result.Loading())
    val closestPoiItem: StateFlow<Result<PoiItem?>> = _closestPoiItem

    val mapLocationSource: LocationSource = object : LocationSource {
        override fun activate(onLocationChangedListener: LocationSource.OnLocationChangedListener) {
            viewModelScope.launch(context = Dispatchers.IO) {
                userLocationFlow
                    .shareIn(viewModelScope + Dispatchers.IO, started = SharingStarted.Eagerly)
                    .collect {
                        onLocationChangedListener.onLocationChanged(Location("").apply {
                            latitude = it.latitude
                            longitude = it.longitude
                        })
                    }
            }
        }

        override fun deactivate() {}
    }

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            getClosestUnselectedPoiUseCase().collect {
                _closestPoiItem.value = it
            }
        }

    }
}