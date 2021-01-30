package com.briggin.footballfinder.api.dto

sealed class ApiResponse<T>

data class Success<T>(val items: List<T>): ApiResponse<T>()

class Error<T>: ApiResponse<T>()
