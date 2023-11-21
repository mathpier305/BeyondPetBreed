package com.example.beyondpetbreed.util


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class ValidError<out T>(val data: T) : Result<T>()
}

fun <T> Result<T>.successOr(fallback: T) : T {
    return (this as? Result.Success<T>)?.data ?: fallback
}