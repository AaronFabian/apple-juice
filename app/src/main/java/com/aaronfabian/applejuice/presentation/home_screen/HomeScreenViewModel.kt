package com.aaronfabian.applejuice.presentation.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

   private val _stateAgain = mutableStateOf(HomeScreenState())
   val stateAgain: State<HomeScreenState> = _stateAgain

   fun getCoinAgain() {
      getAllCoinsUseCase().onEach { response ->

         when (response) {
            is Resource.Success -> {
               this._stateAgain.value = HomeScreenState(coin = response.data)
            }
            is Resource.Error -> {
               this._stateAgain.value =
                  HomeScreenState(error = response.message ?: "An unexpected error occurred 32")
            }
            is Resource.Loading -> {
               this._stateAgain.value = HomeScreenState(isLoading = true)
            }
         }
      }.launchIn(viewModelScope)
   }
}