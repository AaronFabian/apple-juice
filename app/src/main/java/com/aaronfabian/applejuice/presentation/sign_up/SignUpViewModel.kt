package com.aaronfabian.applejuice.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.data.repository.AuthRepository
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
   private val repository: AuthRepository
) : ViewModel() {
//   private val _state = MutableStateFlow(SignInState())
//   val state = _state.asStateFlow()

   private val _state = Channel<SignUpState>()
   val state = _state.receiveAsFlow()

   fun registerUser(email: String, password: String) = viewModelScope.launch {
      repository.registerUser(email, password).collect { result ->

         when (result) {
            is Resource.Success -> {
               _state.send(SignUpState(isSuccess = "Sign up success"))
            }
            is Resource.Loading -> {
               _state.send(SignUpState(isLoading = true))
            }
            is Resource.Error -> {
               _state.send(SignUpState(isError = result.message))
            }
         }
      }
   }
}