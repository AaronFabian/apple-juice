package com.aaronfabian.applejuice.presentation.tag_detail_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.use_case.GetTagUseCase
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class TagDetailViewModel @Inject constructor(
   private val getTagUseCase: GetTagUseCase,
   savedStateHandle: SavedStateHandle
) : ViewModel() {

   private val _state = mutableStateOf(TagDetailState())
   val state = _state

   init {
      savedStateHandle.get<String>("tagId")?.let { tagId -> getTag(tagId) }
   }

   private fun getTag(tagId: String) {
      getTagUseCase(tagId).onEach { result ->
         when (result) {
            is Resource.Loading -> _state.value = TagDetailState(isLoading = true)
            is Resource.Success -> _state.value = TagDetailState(tag = result.data)
            is Resource.Error -> _state.value = TagDetailState(
               error = result.message ?: "An unexpected error occurred! at TagDetailVM"
            )
         }
      }.launchIn(viewModelScope)
   }
}