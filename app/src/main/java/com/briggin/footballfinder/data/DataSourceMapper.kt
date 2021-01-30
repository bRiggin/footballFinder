package com.briggin.footballfinder.data

import com.briggin.footballfinder.api.dto.ApiError
import com.briggin.footballfinder.api.dto.ApiResponse
import com.briggin.footballfinder.api.dto.Success
import com.briggin.footballfinder.domain.*

class DataSourceMapper {

    fun mapPlayerResponse(response: ApiResponse<PlayerDomain>) = mapApiResponse(response)

    fun mapTeamResponse(response: ApiResponse<TeamDomain>) = mapApiResponse(response)

    private fun mapApiResponse(response: ApiResponse<*>) = when (response) {
        is Success -> response.items.let {
            if (it.size.rem(PAGINATION_SIZE) == 0L) Partial(it) else Complete(it)
        }
        is ApiError -> NoDomainsFound()
    }
}
