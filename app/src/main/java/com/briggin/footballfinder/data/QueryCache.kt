package com.briggin.footballfinder.data

import com.briggin.footballfinder.domain.ResultError
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

internal const val PAGINATION_SIZE = 10L

class QueryCache {

    private val threadSafeErrors: AtomicReference<Set<ResultError>> = AtomicReference(emptySet())
    private val threadSafeQuery: AtomicReference<String> = AtomicReference("")
    private val threadSafePlayersIndex: AtomicLong = AtomicLong(0)
    private val threadSafeTeamsIndex: AtomicLong = AtomicLong(0)

    var query: String
        get() = threadSafeQuery.get()
        set(value) {
            threadSafePlayersIndex.set(0)
            threadSafeTeamsIndex.set(0)
            threadSafeQuery.set(value)
        }

    fun nextPlayersIndex(): Long = threadSafePlayersIndex.getAndAdd(PAGINATION_SIZE)

    fun nextTeamsIndex(): Long = threadSafeTeamsIndex.getAndAdd(PAGINATION_SIZE)

    fun cacheError(error: ResultError) {
        val new = threadSafeErrors.get().toMutableSet().apply { add(error) }
        threadSafeErrors.set(new)
    }

    fun getErrors(): Set<ResultError> = threadSafeErrors.getAndSet(emptySet())
}
