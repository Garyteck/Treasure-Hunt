package com.example.treasurehunt.ui.congratulations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treasurehunt.Result
import com.example.treasurehunt.domain.GetAllPoisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CongratulationsViewModel @Inject constructor(
    getAllPoisUseCase:
    GetAllPoisUseCase
) : ViewModel() {

    private val _isAllPoiSelected = MutableStateFlow(false)
    val isAllPoiSelected = _isAllPoiSelected.asStateFlow()

    init {
        Log.e("CongratulationsViewModel", "init: ")
        viewModelScope.launch(context = Dispatchers.IO) {
            getAllPoisUseCase().collect {
                Log.e("CongratulationsViewModel", "getAllPoisUseCase: $it")
                _isAllPoiSelected.value = when (it) {
                    is Result.Error -> false
                    is Result.Loading -> false
                    is Result.Success -> it.data.all { poiItem -> poiItem.isFound }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("CongratulationsViewModel", "onCleared: ")
    }
}