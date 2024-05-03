package com.example.treasurehunt.ui.congratulations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treasurehunt.Result
import com.example.treasurehunt.domain.GetAllPoisUseCase
import com.example.treasurehunt.domain.GetGameProgressUseCase
import com.example.treasurehunt.domain.UnselectAllPoisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CongratulationsViewModel @Inject constructor(
    val getAllPoisUseCase: GetAllPoisUseCase,
    val unselectAllPoisUseCase : UnselectAllPoisUseCase,
    val getGameProgressUseCase: GetGameProgressUseCase
) : ViewModel() {

    private val _isAllPoiSelected = MutableStateFlow(false)
    val isAllPoiSelected = _isAllPoiSelected.asStateFlow()

    private val _gameProgress = MutableStateFlow(Pair(1, 10))
    val gameProgress = _gameProgress.asStateFlow()

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

        viewModelScope.launch(context = Dispatchers.IO) {
            getGameProgressUseCase().collect {
                Log.e("CongratulationsViewModel", "getGameProgressUseCase: $it")
                _gameProgress.value = when (it) {
                    is Result.Error -> Pair(1, 10)
                    is Result.Loading -> Pair(1, 10)
                    is Result.Success -> it.data
                }
            }
        }
    }

    fun restartGame() {
        viewModelScope.launch(context = Dispatchers.IO) {
            unselectAllPoisUseCase().collect {
                Log.e("CongratulationsViewModel", "unselectAllPoisUseCase: $it")
            }
        }
    }
}