package com.aaronfabian.applejuice.presentation.search_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.use_case.GetCoinSearchUseCase
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
   private val getCoinBySearchUseCase: GetCoinSearchUseCase,
   savedStateHandle: SavedStateHandle
) : ViewModel() {

   var searchQuery = ""

   private val _state = mutableStateOf(CoinSearchState())
   val state = _state


   init {
      savedStateHandle.get<String>("query")?.let { query -> getCoinByQuery(query) }
      searchQuery = savedStateHandle.get<String>("query") ?: "--"
   }


   private fun getCoinByQuery(query: String) {

      println("test")

      getCoinBySearchUseCase(query).onEach { response ->
         when (response) {
            is Resource.Success -> {
               _state.value = CoinSearchState(coin = response.data)
            }
            is Resource.Error -> {
               _state.value = CoinSearchState(
                  error = response.message ?: "An unexpected error occurred"
               )
            }
            is Resource.Loading -> {
               _state.value = CoinSearchState(isLoading = true)
            }
         }
      }.launchIn(viewModelScope)
   }
}