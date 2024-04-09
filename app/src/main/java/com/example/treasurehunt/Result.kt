package com.example.treasurehunt

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
    data class Loading<T>(val data: T? = null) : Result<T>()
}
