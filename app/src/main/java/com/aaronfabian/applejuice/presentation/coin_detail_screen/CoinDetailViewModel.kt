package com.aaronfabian.applejuice.presentation.coin_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.use_case.GetDetailCoinUseCase
import com.aaronfabian.applejuice.utils.Resource
import com.aaronfabian.applejuice.utils.ResourceDoubleHtpp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
   private val getCoinUseCase: GetDetailCoinUseCase,
   savedStateHandle: SavedStateHandle
) : ViewModel() {

   private val _state = mutableStateOf(CoinDetailState())
   private val _state2 = mutableStateOf(CoinTickerState())
   val state: State<CoinDetailState> = _state
   val state2: State<CoinTickerState> = _state2

   private val _state3 = mutableStateOf(CoinGraphState())
   val state3: State<CoinGraphState> = _state3

   init {
      savedStateHandle.get<String>("coinId")?.let { coinId -> getCoin(coinId) }
      savedStateHandle.get<String>("coinId")?.let { coinId -> getCoinGraph(coinId) }
   }

   private fun getCoin(coinId: String) {

      getCoinUseCase(coinId).onEach { result ->
         when (result) {
            is ResourceDoubleHtpp.Success -> {
               _state.value = CoinDetailState(coin = result.data)
               _state2.value = CoinTickerState(coin = result.data2)
            }
            is ResourceDoubleHtpp.Error -> {
               _state.value = CoinDetailState(
                  error = result.message ?: "An unexpected error occurred"
               )
            }
            is ResourceDoubleHtpp.Loading -> {
               _state.value = CoinDetailState(isLoading = true)
               _state2.value = CoinTickerState(isLoading = true)
            }
         }
      }.launchIn(viewModelScope)
   }

   private fun getCoinGraph(coinId: String) {

      val symbol = coinId.split('-').first()

      getCoinUseCase.getGraph(symbol).onEach { response ->
         when (response) {
            is Resource.Loading -> _state3.value = CoinGraphState(isLoading = true)
            is Resource.Success -> _state3.value = CoinGraphState(coin = response.data)
            is Resource.Error -> _state3.value =
               CoinGraphState(error = response.message ?: "An unexpected error occurred !")
         }
      }.launchIn(viewModelScope)
   }

//   private fun getTicker() {
//      getCoinTicker(this.storedCoinId).onEach { result ->
//         when (result) {
//            is Resource.Success -> {
//               _stateTicker.value = CoinTickerState(coin = result.data)
//            }
//            is Resource.Error -> {
//               _stateTicker.value = CoinTickerState(
//                  error = result.message ?: "An unexpected error occurred"
//               )
//            }
//            is Resource.Loading -> {
//               _stateTicker.value = CoinTickerState(isLoading = true)
//            }
//         }
//      }.launchIn(viewModelScope)
//   }
}