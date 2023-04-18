package com.aaronfabian.applejuice.presentation.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.data.remote.dto.Data
import com.aaronfabian.applejuice.data.remote.dto.Stats
import com.aaronfabian.applejuice.domain.model.CoinList
import com.aaronfabian.applejuice.domain.use_case.GetAllCoinsUseCase
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
   private val getAllCoinsUseCase: GetAllCoinsUseCase
) : ViewModel() {
   private val _state = mutableStateOf(HomeScreenState())
   val state = _state

   init {
      getAllCoinsList()
   }

   private fun getAllCoinsList() {
      getAllCoinsUseCase().onEach { response ->

         when (response) {
            is Resource.Success -> {
               i = response.data?.data?.coins?.size!!
               lastCoinName = response.data.data.coins[i - 1].name
               this._state.value = HomeScreenState(coin = response.data)
            }
            is Resource.Error -> {
               this._state.value =
                  HomeScreenState(error = response.message ?: "An unexpected error occurred 32")
            }
            is Resource.Loading -> {
               this._state.value = HomeScreenState(isLoading = true)
            }
         }
      }.launchIn(viewModelScope)
   }

   private val _stateNext = mutableStateOf(HomeScreenState())
   val stateNext: State<HomeScreenState> = _stateNext

   var lastCoinName: String? = ""
   private var page: Int = 0
   private var i = 0
   private var preventRequest = false

   fun getCoinNextList() {

      if (preventRequest) return

      page++
      getAllCoinsUseCase.getCoinNextList((page * 100).toString()).onEach { response ->

         when (response) {
            is Resource.Success -> {
               this._stateNext.value = HomeScreenState(coin = response.data) // del soon
               val prevState = _state.value.coin?.data?.coins
               prevState?.addAll(response.data?.data?.coins!!)!!

               val coinList = CoinList(
                  data = Data(
                     prevState,
                     stats = Stats(
                        total = 0,
                        total24hVolume = "",
                        totalCoins = 0,
                        totalExchanges = 0,
                        totalMarketCap = "",
                        totalMarkets = 0
                     )
                  ), status = ""
               )

               i = prevState.size
               lastCoinName = prevState[i - 1].name
               preventRequest = false
               this._state.value = HomeScreenState(coin = coinList)
            }
            is Resource.Error -> {
               this._stateNext.value =
                  HomeScreenState(error = response.message ?: "An unexpected error occurred")
               preventRequest = false
               page--
            }
            is Resource.Loading -> {
               this._stateNext.value = HomeScreenState(isLoading = true)
               preventRequest = true
            }
         }
      }.launchIn(viewModelScope)
   }
}