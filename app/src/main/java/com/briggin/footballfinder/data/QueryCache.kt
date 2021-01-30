package com.briggin.footballfinder.data

import com.briggin.footballfinder.domain.ResultError

class QueryCache {

    var query: String = ""

    fun nextPlayersIndex(): Long {
        TODO()
    }

    fun nextTeamsIndex(): Long {
        TODO()
    }

    fun cacheError(error: ResultError) {
        TODO()
    }

    fun getErrors(): Set<ResultError> {
        TODO()
    }
}
