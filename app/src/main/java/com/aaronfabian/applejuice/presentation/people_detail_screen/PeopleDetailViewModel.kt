package com.aaronfabian.applejuice.presentation.people_detail_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.use_case.GetPeopleDetailUseCase
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class PeopleDetailViewModel @Inject constructor(
   private val getPeopleDetailUseCase: GetPeopleDetailUseCase,
   savedStateHandle: SavedStateHandle
) : ViewModel() {

   private val _state = mutableStateOf(PeopleDetailState())
   val state = _state

   init {
      savedStateHandle.get<String>("peopleId")?.let { peopleId -> getPeopleDetail(peopleId) }
   }

   private fun getPeopleDetail(peopleId: String) {
      getPeopleDetailUseCase(peopleId).onEach { result ->
         when (result) {
            is Resource.Success -> {
               _state.value = PeopleDetailState(coin = result.data)
            }
            is Resource.Error -> {
               _state.value = PeopleDetailState(
                  error = result.message ?: "An unexpected error occurred"
               )
            }
            is Resource.Loading -> {
               _state.value = PeopleDetailState(isLoading = true)
            }
         }
      }.launchIn(viewModelScope)
   }
}