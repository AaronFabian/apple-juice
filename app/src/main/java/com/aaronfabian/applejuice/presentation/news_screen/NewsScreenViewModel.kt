package com.aaronfabian.applejuice.presentation.news_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.use_case.GetCryptoNewsUseCase
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
   private val getCryptoNewsUseCase: GetCryptoNewsUseCase
) : ViewModel() {

   private val _state = mutableStateOf(NewsScreenState())
   val state = _state

   init {
      getNews()
   }

   private fun getNews() {
      getCryptoNewsUseCase().onEach { respond ->
         when (respond) {
            is Resource.Loading -> {
               _state.value = NewsScreenState(isLoading = true)
            }
            is Resource.Success -> {
               _state.value = NewsScreenState(news = respond.data)
            }
            is Resource.Error -> {
               _state.value =
                  NewsScreenState(error = respond.message ?: "An unexpected error occurred !")
            }
         }
      }.launchIn(viewModelScope)
   }
}