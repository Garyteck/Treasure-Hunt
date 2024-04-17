package com.example.treasurehunt.ui.compass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.domain.GetClosestUnselectedPoiUseCase
import com.example.treasurehunt.domain.GetDirectionToClosestUnselectedPoi
import com.example.treasurehunt.domain.GetDistanceToClosestUnselectedPoi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class CompassViewModel @Inject constructor(
    private val getClosestUnselectedPoiUseCase: GetClosestUnselectedPoiUseCase,
    private val getDistanceToClosestUnselectedPoi: GetDistanceToClosestUnselectedPoi,
    private val getDirectionToClosestUnselectedPoi: GetDirectionToClosestUnselectedPoi
) : ViewModel() {

    private val _direction = MutableStateFlow<Result<Double?>>(Result.Loading())
    val direction: StateFlow<Result<Double?>> = _direction

    private val _distance = MutableStateFlow<Result<Double?>>(Result.Loading())
    val distance: StateFlow<Result<Double?>> = _distance

    private val _closestPoiItem = MutableStateFlow<Result<PoiItem?>>(Result.Loading())
    val closestPoiItem: StateFlow<Result<PoiItem?>> = _closestPoiItem
    init {

        viewModelScope.launch(context = Dispatchers.IO) {

            getClosestUnselectedPoiUseCase()
                .shareIn(viewModelScope + Dispatchers.IO, started = SharingStarted.Eagerly)
                .collect {

                    _closestPoiItem.value = when(it) {
                        is Result.Success -> Result.Success(it.data)
                        is Result.Error -> Result.Error(it.message)
                        is Result.Loading -> Result.Loading()
                    }
                }
        }

        viewModelScope.launch(context = Dispatchers.IO) {

            getDirectionToClosestUnselectedPoi()
                .shareIn(viewModelScope + Dispatchers.IO, started = SharingStarted.Eagerly)
                .collect {

                    _direction.value = when (it) {
                        is Result.Success -> Result.Success(it.data ?: 0.0)
                        is Result.Error -> Result.Error(it.message)
                        is Result.Loading -> Result.Loading()
                    }
                }
        }

        viewModelScope.launch(context = Dispatchers.IO) {

            getDistanceToClosestUnselectedPoi()
                .shareIn(viewModelScope + Dispatchers.IO, started = SharingStarted.Eagerly)
                .collect {

                    _distance.value = when (it) {
                        is Result.Success -> Result.Success(it.data)
                        is Result.Error -> Result.Error(it.message)
                        is Result.Loading -> Result.Loading()
                    }
                }
        }
    }


}