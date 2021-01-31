package com.briggin.footballfinder.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.briggin.footballfinder.domain.FavoritesDataSource
import com.briggin.footballfinder.domain.PlayerDomain
import com.briggin.footballfinder.main.presentation.ModelMapper
import com.briggin.footballfinder.main.presentation.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val dataSource: FavoritesDataSource
): ViewModel() {

    private val _state: MutableLiveData<List<Player>> = MutableLiveData()
    val state: LiveData<List<Player>> = _state

    init {
        CoroutineScope(Dispatchers.Default).launch {
            loadFavoritesData { dataSource.getLikedPlayers() }
        }
    }

    suspend fun unlikePlayer(player: Player) {
        loadFavoritesData { dataSource.unlikeFavouritePlayer(player.id) }
    }

    private suspend fun loadFavoritesData(block: suspend () -> List<PlayerDomain>) {
        val models = block().map { ModelMapper.playerToModel(it) }
        _state.postValue(models)
    }
}