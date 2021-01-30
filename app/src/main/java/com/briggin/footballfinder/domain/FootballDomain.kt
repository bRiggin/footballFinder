package com.briggin.footballfinder.domain

sealed class FootballDomain<T>(domains: List<T>)

data class Complete<T>(private val allDomains: List<T>): FootballDomain<T>(allDomains)

data class Partial<T>(private val partialDomains: List<T>): FootballDomain<T>(partialDomains)

class NoDomainsFound<T>: FootballDomain<T>(emptyList())

class DomainError<T>(): FootballDomain<T>(emptyList())
