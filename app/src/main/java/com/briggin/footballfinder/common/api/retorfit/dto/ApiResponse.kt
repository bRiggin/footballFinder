package com.briggin.footballfinder.common.api.retorfit.dto

sealed class ApiResponse<T>

data class Success<T>(val items: List<T>) : ApiResponse<T>()

class ApiError<T> : ApiResponse<T>()
