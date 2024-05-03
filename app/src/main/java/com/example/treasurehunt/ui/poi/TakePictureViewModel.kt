package com.example.treasurehunt.ui.poi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treasurehunt.LocationUtils
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.domain.GetDistanceToClosestUnselectedPoi
import com.example.treasurehunt.domain.SelectPoiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakePictureViewModel @Inject constructor(
    private val selectPoiUseCase: SelectPoiUseCase,
    private val getDistanceToClosestUnselectedPoi: GetDistanceToClosestUnselectedPoi
) : ViewModel() {

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled = _isButtonEnabled

    private val _isPoiFound = MutableStateFlow(false)
    val isPoiFound = _isPoiFound

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDistanceToClosestUnselectedPoi()
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _isButtonEnabled.value = it.data < LocationUtils.DISTANCE_THRESHOLD
                        }

                        else -> {
                            _isButtonEnabled.value = false
                        }
                    }
                }
        }

    }
    fun selectPoi(poi: Result<PoiItem?>) : Unit =
        takeIf { _isButtonEnabled.value }
            .takeIf { poi is Result.Success }
            .let { poi as Result.Success }
            .let { it.data as PoiItem }
            .let {
                viewModelScope.launch(Dispatchers.IO) {
                    selectPoiUseCase(it).collect {
                        when (it) {
                            is Result.Success -> {
                                _isPoiFound.value = false
                            }

                            else -> {
                                _isPoiFound.value = true
                            }
                        }
                    }
                }
            }
}