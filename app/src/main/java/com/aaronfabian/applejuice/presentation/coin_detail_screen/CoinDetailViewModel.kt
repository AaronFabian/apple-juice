package com.aaronfabian.applejuice.presentation.coin_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.use_case.GetDetailCoinUseCase
import com.aaronfabian.applejuice.utils.Resource
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
   val state: State<CoinDetailState> = _state

   init {
      savedStateHandle.get<String>("coinId")?.let { coinId -> getCoin(coinId) }
   }

   private fun getCoin(coinId: String) {

      getCoinUseCase(coinId).onEach { result ->
         when (result) {
            is Resource.Success -> {
               _state.value = CoinDetailState(coin = result.data)
            }
            is Resource.Error -> {
               _state.value = CoinDetailState(
                  error = result.message ?: "An unexpected error occurred"
               )
            }
            is Resource.Loading -> {
               _state.value = CoinDetailState(isLoading = true)
            }
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