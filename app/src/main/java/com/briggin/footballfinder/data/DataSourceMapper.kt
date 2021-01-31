package com.briggin.footballfinder.data

import com.briggin.footballfinder.api.retorfit.dto.ApiError
import com.briggin.footballfinder.api.retorfit.dto.ApiResponse
import com.briggin.footballfinder.api.retorfit.dto.Success
import com.briggin.footballfinder.domain.*

class DataSourceMapper {

    fun mapPlayerResponse(response: ApiResponse<PlayerDomain>) = mapApiResponse(response)

    fun mapTeamResponse(response: ApiResponse<TeamDomain>) = mapApiResponse(response)

    private fun <T> mapApiResponse(response: ApiResponse<T>) = when (response) {
        is Success -> response.items.let {
            when {
                it.isEmpty() -> NoDomainsFound()
                it.size.rem(PAGINATION_SIZE) == 0L -> Partial(it)
                else -> Complete(it)
            }
        }
        is ApiError -> NoDomainsFound()
    }
}
