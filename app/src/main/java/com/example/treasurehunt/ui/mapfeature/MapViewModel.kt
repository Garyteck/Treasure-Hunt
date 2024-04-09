package com.example.treasurehunt.ui.mapfeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treasurehunt.Result
import com.example.treasurehunt.data.model.PoiItem
import com.example.treasurehunt.domain.GetAllPoisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(getAllPoisUseCase: GetAllPoisUseCase) : ViewModel() {

    private val _pois = MutableStateFlow<Result<List<PoiItem>>>(Result.Loading())
    val pois: StateFlow<Result<List<PoiItem>>> = _pois

    init {

        viewModelScope.launch(context = Dispatchers.IO) {
            getAllPoisUseCase().collect {
                _pois.value = it
            }
        }


    }
}